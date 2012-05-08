package com.renren.dp.xlog.cache;

import java.io.File;

import com.renren.dp.xlog.config.Configuration;
import com.renren.dp.xlog.logger.LogMeta;
import com.renren.dp.xlog.util.FileUtil;

public abstract class CacheManager {

   protected String cacheRootDir=null;
	
   public static final String CACHE_TYPE="localcache";
  
	public abstract void initialize();
	
	//public boolean writeCache(LogData[] logDatas,String logFileNum);
	public CacheManager(){
		cacheRootDir=Configuration.getString("oplog.store.path")+"/"+CACHE_TYPE;
	}
	
	public abstract boolean writeCache(LogMeta logMeta);
	
	public abstract void checkCache();
	
	public long getCacheFilesSize() {
		File cacheRootFile=new File(cacheRootDir);
		if(!cacheRootFile.exists()){
			return 0;
		}
		return FileUtil.computeDirectorySize(cacheRootFile);
	}
}
