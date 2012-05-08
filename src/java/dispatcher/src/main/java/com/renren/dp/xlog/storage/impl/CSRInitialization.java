package com.renren.dp.xlog.storage.impl;

import java.io.File;
import java.io.FileFilter;

import xlog.slice.LogData;

import com.renren.dp.xlog.cache.CacheManager;
import com.renren.dp.xlog.config.Configuration;
import com.renren.dp.xlog.handler.FileNameHandlerFactory;
import com.renren.dp.xlog.io.SuffixFileFilter;
import com.renren.dp.xlog.logger.LogMeta;
import com.renren.dp.xlog.storage.StorageRepositoryInitialization;
import com.renren.dp.xlog.storage.StorageRepository;
import com.renren.dp.xlog.storage.StorageRepositoryFactory;
import com.renren.dp.xlog.util.Constants;

public class CSRInitialization extends StorageRepositoryInitialization{

	private StorageRepository storageRepository=null;
	private File cacheLogDir=null;
	private int cacheLogDirLen;
	private String[] memoryStorageCategories=null;
	
	@Override
	public void initialise() {
		// TODO Auto-generated method stub
		this.storageRepository= StorageRepositoryFactory.getInstance();
		String tmp=Configuration.getString("memory.cache.categories","").replaceAll("\\|","/"); 
		if(!tmp.equals("")){
			memoryStorageCategories=tmp.split(",");
		}
		String storePath = Configuration.getString("oplog.store.path");
		cacheLogDir = new File(storePath + "/" + CacheManager.CACHE_TYPE);
		cacheLogDirLen=cacheLogDir.getAbsolutePath().length();
		String logFileNum=FileNameHandlerFactory.getInstance().getCacheLogFileNum();
		FileFilter noSuffixFF=new SuffixFileFilter();
		
		check(cacheLogDir,logFileNum,noSuffixFF,Constants.LOG_WRITE_FINISHED_SUFFIX);
		
		FileFilter suffixFF=new SuffixFileFilter(new String[]{Constants.LOG_WRITE_FINISHED_SUFFIX});
		initStorageRepository(cacheLogDir,suffixFF);
	}
	
	private void initStorageRepository(File dir,FileFilter suffixFF) {
		File[] logFiles = dir.listFiles(suffixFF);
		if (logFiles == null || logFiles.length == 0) {
			return;
		}
		
		LogData logData=null;
		for (File logFile : logFiles) {
			if (logFile.isFile()) {
				logData=new LogData();
				logData.categories=new String[]{logFile.getParent().substring(cacheLogDirLen)};
				
				storageRepository.addToRepository(new LogMeta(logFile.getName(),logData));
			} else {
				if(!isExcludeCategories(logFile.getAbsolutePath())){
					initStorageRepository(logFile,suffixFF);
				}
			}
		}
	}
	
	private boolean isExcludeCategories(String path){
		if(memoryStorageCategories == null){
			return false;
		}
		
		for(String s:memoryStorageCategories){
			if(path.endsWith(s)){
				return true;
			}
		}
		return false;
	}
}
