package com.renren.dp.xlog.storage.impl;

import java.io.File;

import com.renren.dp.xlog.cache.CacheManager;
import com.renren.dp.xlog.config.Configuration;
import com.renren.dp.xlog.handler.FileNameHandlerFactory;
import com.renren.dp.xlog.io.SuffixFileFilter;
import com.renren.dp.xlog.storage.StorageRepositoryInitialization;
import com.renren.dp.xlog.util.Constants;

public class DSRInitialization extends StorageRepositoryInitialization{

	@Override
	public void initialise() {
		String storePath = Configuration.getString("oplog.store.path");
		File cacheLogDir = new File(storePath + "/" + CacheManager.CACHE_TYPE);
		File slaveLogDir=new File(storePath + "/" +Configuration.getString("storage.type"));
		String logFileNum=FileNameHandlerFactory.getInstance().getCacheLogFileNum();
		SuffixFileFilter suffixFileFilter=new SuffixFileFilter();
		
		check(cacheLogDir,logFileNum,suffixFileFilter,Constants.LOG_WRITE_FINISHED_SUFFIX);
		check(slaveLogDir,logFileNum,suffixFileFilter,Constants.LOG_WRITE_ERROR_SUFFIX);
	}
}
