package com.renren.dp.xlog.cache.impl;

import java.io.File;
import java.util.Map;
import java.util.Set;
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

public class DefaultCacheManager extends CacheManager{

  private Map<String,LogWriter> categoriesMap=null;

  private final static Logger logger = LoggerFactory.getLogger(DefaultCacheManager.class);
 
  public DefaultCacheManager(){
	  super();
  }
  
  @Override
  public void initialize() {
    categoriesMap=new ConcurrentHashMap<String,LogWriter>();
  }
  @Override
  public boolean writeCache(LogMeta logMeta) {
    LogWriter logWriter=null;
    String category=LogDataFormat.transformCatefories(logMeta.getLogData().categories);
    if(categoriesMap.containsKey(category)){
      logWriter=categoriesMap.get(category);
      if(logWriter.getLogFileName().equals(logMeta.getLogFileNum())){
    	  return logWriter.write(logMeta.getLogFileNum(),logMeta.getLogData().logs,false);
      }else{
    	  logWriter.close();
    	  logWriter.rename(Constants.LOG_WRITE_FINISHED_SUFFIX);
    	  
    	  boolean res=logWriter.createFile(new File(cacheRootDir+category+"/"+logMeta.getLogFileNum()));
    	  if(res){
    		  return logWriter.write(logMeta.getLogFileNum(),logMeta.getLogData().logs,false);
    	  }else{
    		  return false;
    	  }
      }
    }else{
    	logWriter=new DefaultLogWriter();
       logWriter.createFile(new File(cacheRootDir+category+"/"+FileNameHandlerFactory.getInstance().getCacheLogFileNum()));
        
       categoriesMap.put(category, logWriter);
       return logWriter.write(logMeta.getLogFileNum(),logMeta.getLogData().logs,false);
    }
  }
  @Override
  public void checkCache() {
	String logFileNum=FileNameHandlerFactory.getInstance().getCacheLogFileNum();
	Set<Map.Entry<String,LogWriter>> set=categoriesMap.entrySet();
	for(Map.Entry<String,LogWriter> me:set){
		LogWriter logWriter=me.getValue();
		if(!logFileNum.equals(logWriter.getLogFileName())){
    		logWriter.close();
        	logWriter.rename(Constants.LOG_WRITE_FINISHED_SUFFIX);
        	
        	boolean res=logWriter.createFile(new File(cacheRootDir+me.getKey()+"/"+logFileNum));
        	if(!res){
        		logger.error("fail to create log file!");
        	}
    	}
	}
  }
}
