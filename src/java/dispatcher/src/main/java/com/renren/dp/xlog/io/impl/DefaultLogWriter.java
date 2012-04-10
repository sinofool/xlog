package com.renren.dp.xlog.io.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.renren.dp.xlog.io.LogWriter;
import com.renren.dp.xlog.logger.LogMeta;
import com.renren.dp.xlog.util.Constants;

public class DefaultLogWriter implements LogWriter{

	private SimpleDateFormat sdf=null;
	private String currentLogFileNum=null;
	private FileWriter fw=null;
	private BufferedWriter br=null;
	private String logFilePath=null;
	private boolean isAutoGenerateFileName;
	
	private final ReentrantLock lock = new ReentrantLock();
	
	public DefaultLogWriter(String logFilePath){
		this.logFilePath=logFilePath;
		isAutoGenerateFileName=true;
		sdf=new SimpleDateFormat(Constants.FILE_NAME_FORMAT);
		currentLogFileNum=sdf.format(new Date());
		File f=new File(logFilePath+File.separator+currentLogFileNum);
		if(!f.exists()){
			File p=f.getParentFile();
			if(!p.exists()){
				p.mkdirs();
			}
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			fw=new FileWriter(f,true);
			br=new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public DefaultLogWriter(File targetFile){
		isAutoGenerateFileName=false;
		try {
			fw=new FileWriter(targetFile,true);
			br=new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean write(List<LogMeta> logMetas,boolean isAllowPartSuccess) {
		boolean res;
		for(LogMeta log:logMetas){
			res=write(log);
			if(!isAllowPartSuccess && !res){
				return false;
			}
		}
		logMetas.removeAll(logMetas);
		return true;
	}
	
	public boolean write(LogMeta logMeta){
		lock.lock();
		try{
			if(isAutoGenerateFileName){
				String tmpLogFileNum=sdf.format(new Date());
				if(!tmpLogFileNum.equals(currentLogFileNum)){
					currentLogFileNum=tmpLogFileNum;
					try {
						fw.close();
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					File f=new File(logFilePath+File.separator+currentLogFileNum);
						f.createNewFile();
						fw=new FileWriter(f,true);
						br=new BufferedWriter(fw);
				}
			}
			
			br.write(logMeta.toString()+"\n");
			br.flush();
		}catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally{
			lock.unlock();
		}
		
		return true;
	}
	
	public void close(){
		if(br!=null){
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(fw!=null){
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

