package com.renren.dp.xlog.sync;

import java.util.Timer;

import com.renren.dp.xlog.config.Configuration;
import com.renren.dp.xlog.storage.StorageRepositoryInitialization;
import com.renren.dp.xlog.storage.impl.CSRInitialization;
import com.renren.dp.xlog.storage.impl.DSRInitialization;
import com.renren.dp.xlog.storage.impl.FSRInitialization;

public class LogSyncInitialization {

	public void initialise(){
		StorageRepositoryInitialization init=null;
		//the cache time unit is min at config file
		int oplogCacheTime=Configuration.getInt("oplog.cache.time", 0)*60*1000;
		int storageRepositoryMode=Configuration.getInt("storage.repository.mode",1);
		//unit is second
		long syncInterval=Configuration.getLong("oplog.sync.interval",3600);
		//convert to ms
		syncInterval=syncInterval*1000;
		if(storageRepositoryMode==0){
			init=new DSRInitialization();
			init.initialise();
			
			Timer t=new Timer();
			t.schedule(new SyncTimer(null),syncInterval,syncInterval);
		}else if(storageRepositoryMode==1){
			init=new FSRInitialization();
			init.initialise();
		}else if(storageRepositoryMode==2){
			init=new CSRInitialization();
			init.initialise();
			
			Timer t=new Timer();
			String categories=Configuration.getString("memory.cache.categories","").replaceAll("\\|","/"); 
			
			t.schedule(new SyncTimer(categories.split(",")),syncInterval,syncInterval);
		}
		
		long clearInterval=Configuration.getLong("oplog.clear.interval",1000);
		clearInterval=clearInterval*1000;
		Timer t1=new Timer();
		t1.schedule(new ClearTimer(oplogCacheTime),0,clearInterval);
	}
}
