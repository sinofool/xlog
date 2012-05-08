package com.renren.dp.xlog.cache;

import xlog.slice.LogData;

public interface CacheManager {

  public static final String CACHE_TYPE="localcache";
  
	public void initialize();
	
	public boolean writeCache(LogData[] logDatas);
	
	public boolean writeCache(LogData logData);
}
