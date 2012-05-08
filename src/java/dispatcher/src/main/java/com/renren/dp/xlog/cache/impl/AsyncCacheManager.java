package com.renren.dp.xlog.cache.impl;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.renren.dp.xlog.cache.CacheManager;
import com.renren.dp.xlog.handler.FileNameHandlerFactory;
import com.renren.dp.xlog.io.LogWriter;
import com.renren.dp.xlog.io.impl.DefaultLogWriter;
import com.renren.dp.xlog.logger.LogMeta;
import com.renren.dp.xlog.util.Constants;
import com.renren.dp.xlog.util.LogDataFormat;

public class AsyncCacheManager extends CacheManager{

  private Map<String,TaskWriter> taskWriterMap=null;

  private final static Logger logger = LoggerFactory.getLogger(AsyncCacheManager.class);
  
  public AsyncCacheManager(){
	  super();
  }
  
  public void initialize() {
    taskWriterMap=new ConcurrentHashMap<String,TaskWriter>();
  }

  public boolean writeCache(LogMeta logMeta) {
    String category=LogDataFormat.transformCatefories(logMeta.getLogData().categories);
    TaskWriter tw=null;
    if(taskWriterMap.containsKey(category)){
    	tw=taskWriterMap.get(category);
    	tw.add(logMeta);
    }else{
      LogWriter logWriter=new DefaultLogWriter();
      logWriter.createFile(new File(cacheRootDir+category+"/"+FileNameHandlerFactory.getInstance().getCacheLogFileNum()));
      BlockingQueue<LogMeta> bq=new ArrayBlockingQueue<LogMeta>(Constants.CACHE_MANAGER_QUEUE_SIZE);
      
      tw=new TaskWriter(bq,logWriter,cacheRootDir+category);
      tw.setDaemon(true);
      tw.start();
      
      tw.add(logMeta);
      taskWriterMap.put(category, tw);
    }
    return true;
  }

  class TaskWriter extends Thread{
    private BlockingQueue<LogMeta> bq=null;
    private LogWriter logWriter=null;
    private String rootPath=null;
    private String currentLogFileNum=null;
    
    TaskWriter(BlockingQueue<LogMeta> bq,LogWriter logWriter,String rootPath){
      this.bq=bq;
      this.logWriter=logWriter;
      this.rootPath=rootPath;
     }
    
    public void add(LogMeta logMeta){
    	if(bq.size()==Constants.CACHE_MANAGER_QUEUE_SIZE){
    		logger.error("cache memory queue is full!log file rootpath is :"+rootPath+",file name is : "+currentLogFileNum);
    	}else{
    		bq.add(logMeta);
    	}
    }
    
    public void checkCacheFile(String logFileNum) {
    	if((bq.size()==0) && (!logFileNum.equals(currentLogFileNum))){
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
      boolean res;
      currentLogFileNum=logWriter.getLogFileName();
      while(true){
        try {
          logMeta=bq.take();
          
          if(currentLogFileNum.equals(logMeta.getLogFileNum())){
        	  logWriter.write(logMeta.getLogFileNum(),logMeta.getLogData().logs, true);
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
          logMeta.free();
          logMeta=null;
        } catch (InterruptedException e) {
          logger.error("fail to write data to cache file!the exception is : "+e.getMessage());
          continue;
         }
	   }
	}
  }

	public void checkCache() {
		String logFileNum=FileNameHandlerFactory.getInstance().getCacheLogFileNum();
		Collection<TaskWriter> coll=taskWriterMap.values();
		for(TaskWriter tw:coll){
			tw.checkCacheFile(logFileNum);
		}
	}
}
