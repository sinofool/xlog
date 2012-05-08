package com.renren.dp.xlog.sync;

import java.io.File;
import java.io.FileFilter;
import java.util.TimerTask;

import com.renren.dp.xlog.cache.CacheManager;
import com.renren.dp.xlog.config.Configuration;
import com.renren.dp.xlog.io.CacheLogFileFilter;

public class ClearTimer extends TimerTask{

	private int oplogCacheTime;
	private File slaveLogDir;
	private File cacheLogDir;
	public ClearTimer(int oplogCacheTime){
		this.oplogCacheTime=oplogCacheTime;
		String storePath=Configuration.getString("oplog.store.path");
		slaveLogDir=new File(storePath+"/"+Configuration.getString("storage.type"));
		cacheLogDir=new File(storePath+"/"+CacheManager.CACHE_TYPE);
	}
	@Override
	public void run() {
		CacheLogFileFilter clff=new CacheLogFileFilter(oplogCacheTime);
		if(cacheLogDir.exists()){
			clearUp(cacheLogDir,clff);
		}
		
		if(slaveLogDir.exists()){
			clearUp(slaveLogDir,clff);
		}
	}

	private void clearUp(File logFile,FileFilter fileFilter){
		if(logFile.isFile()){
			logFile.delete();
		}else{
			File[] subFiles=logFile.listFiles(fileFilter);
			for(File f:subFiles){
				clearUp(f,fileFilter);
			}
		}
	}
}
