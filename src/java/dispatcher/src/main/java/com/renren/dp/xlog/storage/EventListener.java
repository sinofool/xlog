package com.renren.dp.xlog.storage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.renren.dp.xlog.io.LogWriter;
import com.renren.dp.xlog.logger.LogMeta;
import com.renren.dp.xlog.util.Constants;
import com.renren.dp.xlog.util.LogDataFormat;

public class EventListener extends Thread{

	private LogWriter logWriter=null;
	private BlockingQueue<LogMeta> logBQ=null;
	private int queueCapacity;
	private StorageAdapter sa=null;
	private String rootPath=null;
	private String currentLogFileNum=null;
	
	private long successCount=0;
	private long failureCount=0;
	
	private final static Logger logger = LoggerFactory.getLogger(EventListener.class);

	public EventListener(LogWriter logWriter,int queueCapacity,String rootPath){
		this.logWriter=logWriter;
		this.queueCapacity=queueCapacity;
		this.rootPath=rootPath;
	}
	
	public void initialize() throws IOException{
		logBQ=new ArrayBlockingQueue<LogMeta>(queueCapacity);
		this.sa=StorageAdapterFactory.getInstance();
		try {
			sa.initialize();
		} catch (IOException e) {
			logger.error("fail to open storage adapter and eventlistener exist ! the error message is ",e.getMessage());
		}
	}
	
	public void add(LogMeta logMeta){
		if(logBQ.size()>=queueCapacity){
			if(logger.isDebugEnabled()){
				logger.debug("categories :" + LogDataFormat.transformCatefories(logMeta.getLogData().categories)+",queue is full!");
			}
			logWriter.write(logMeta.getLogFileNum(),logMeta.getLogData().logs,true);
			logMeta.free();
			logMeta=null;
		}else{
			logBQ.add(logMeta);
		}
	}
	
	public int getLogQueueSize(){
		return logBQ.size();
	}
	
	public long getSuccessCount(){
		return successCount;
	}
	
	public long getFailureCount(){
		return failureCount;
	}
	
	public void checkExpiredLogFile(String logFileNum){
		if((logBQ.size()==0) && (!logFileNum.equals(currentLogFileNum))){
			currentLogFileNum=logFileNum;
			logWriter.close();
        	logWriter.rename(Constants.LOG_WRITE_FINISHED_SUFFIX);
        	
        	boolean res=logWriter.createFile(new File(rootPath+"/"+currentLogFileNum));
        	if(!res){
        		logger.error("fail to create log file!");
        	}
		}
	}
	
	public void run(){
		LogMeta logMeta=null;
		currentLogFileNum=logWriter.getLogFileName();
		while(true){
			try {
				logMeta=logBQ.take();
				boolean res=sa.store(logMeta);
				
				if(res){
					successCount++;
					if(!currentLogFileNum.equals(logMeta.getLogFileNum())){
						currentLogFileNum=logMeta.getLogFileNum();
						logWriter.close();
			        	logWriter.rename(Constants.LOG_WRITE_FINISHED_SUFFIX);
			        	
			        	res=logWriter.createFile(new File(rootPath+"/"+currentLogFileNum));
			        	if(!res){
			        		logger.error("fail to create log file!");
			        	}
					}
				}else{
					failureCount++;
					if(currentLogFileNum.equals(logMeta.getLogFileNum())){
						logWriter.write(logMeta.getLogFileNum(),logMeta.getLogData().logs,true);
					}else{
						logWriter.close();
			        	logWriter.rename(Constants.LOG_WRITE_FINISHED_SUFFIX);
			        	
			        	currentLogFileNum=logMeta.getLogFileNum();
			        	res=logWriter.createFile(new File(rootPath+"/"+currentLogFileNum));
			        	if(res){
			        		logWriter.write(currentLogFileNum,logMeta.getLogData().logs, true);
			        	}else{
			        		logger.error("fail to create log file!");
			        	}
					}
				}
				
				logMeta.free();
				logMeta=null;
			} catch (InterruptedException e) {
				logger.error("fail to store logdata!");
				e.printStackTrace();
				continue;
			} catch (IOException e) {
				logger.error("fail to store logdata!");
				e.printStackTrace();
				continue;
			} 
		}
	}
	
	public void close(){
		while(logBQ.size()>0){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		logWriter.close();
    	logWriter.rename(Constants.LOG_WRITE_FINISHED_SUFFIX);
    	logWriter.close();
		Thread.interrupted();
	}
	
}
