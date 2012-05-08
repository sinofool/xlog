package com.renren.dp.xlog.cache.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import xlog.slice.LogData;

import com.renren.dp.xlog.cache.CacheManager;
import com.renren.dp.xlog.config.Configuration;
import com.renren.dp.xlog.io.LogWriter;
import com.renren.dp.xlog.io.impl.DefaultLogWriter;
import com.renren.dp.xlog.logger.LogMeta;
import com.renren.dp.xlog.util.LogDataFormat;

public class DefaultCacheManager implements CacheManager{

	private String cacheRootDir=null;
	private Map<String,LogWriter> categoriesLogWriter=null;
	
	@Override
	public void initialize() {
		cacheRootDir=Configuration.getString("oplog.store.path")+"/"+CACHE_TYPE;
		categoriesLogWriter=new ConcurrentHashMap<String,LogWriter>();
	}
	@Override
	public boolean writeCache(LogData[] logDatas){
		if(logDatas==null){
			return false;
		}
		
		List<LogMeta> logMetas=LogDataFormat.transformLogDataToMeta(logDatas);
		if(logMetas==null || logMetas.isEmpty()){
			return false;
		}
		LogWriter logWriter=null;
		String category=logMetas.get(0).getCategories();
		if(categoriesLogWriter.containsKey(category)){
			logWriter=categoriesLogWriter.get(category);
		}else{
			logWriter=new DefaultLogWriter(cacheRootDir+"/"+category);
			categoriesLogWriter.put(category, logWriter);
		}
		return logWriter.write(logMetas,false);
	}
  @Override
  public boolean writeCache(LogData logData) {
    if(logData==null){
      return false;
    }
    
    List<LogMeta> logMetas=LogDataFormat.transformLogDataToMeta(logData);
    if(logMetas==null || logMetas.isEmpty()){
      return false;
    }
    LogWriter logWriter=null;
    String category=logMetas.get(0).getCategories();
    if(categoriesLogWriter.containsKey(category)){
      logWriter=categoriesLogWriter.get(category);
    }else{
      logWriter=new DefaultLogWriter(cacheRootDir+"/"+category);
      categoriesLogWriter.put(category, logWriter);
    }
    return logWriter.write(logMetas,false);
  }


}
