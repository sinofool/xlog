package com.renren.dp.xlog.storage.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import xlog.slice.LogData;

import com.renren.dp.xlog.config.Configuration;
import com.renren.dp.xlog.io.LogWriter;
import com.renren.dp.xlog.io.impl.DefaultLogWriter;
import com.renren.dp.xlog.storage.EventListener;
import com.renren.dp.xlog.storage.StorageRepository;
import com.renren.dp.xlog.util.LogDataFormat;

public class MultiQueueStorageRepository implements StorageRepository{

	private Map<String,BlockingQueue<LogData>> map=null;
	private int queueCapacity;
	
	private String slaveRootDir=null;
	
	public MultiQueueStorageRepository(){
		map=new HashMap<String,BlockingQueue<LogData>>();
		queueCapacity=Configuration.getInt("storage.repository.queue.capacity", 1000);
		slaveRootDir=Configuration.getString("oplog.store.path")+"/"+Configuration.getString("storage.type");
		
	}
	
	@Override
	public void addToRepository(LogData[] logDatas) {
		if(logDatas==null || logDatas.length==0){
			return ;
		}
		for(LogData logData:logDatas){
			String categories=LogDataFormat.transformCatefories(logData.categories);
			if(map.containsKey(categories)){
				map.get(categories).add(logData);
			}else{
				BlockingQueue<LogData> bq=new ArrayBlockingQueue<LogData>(queueCapacity);
				bq.add(logData);
				map.put(categories, bq);
				
				LogWriter logWriter=new DefaultLogWriter(slaveRootDir+categories);
				Thread t=new EventListener(logWriter,bq);
				t.setDaemon(true);
				t.start();
			}
		}
		
	}

	public void addToRepository(LogData logData) {
    if(logData==null){
      return ;
    }
    String categories=LogDataFormat.transformCatefories(logData.categories);
    if(map.containsKey(categories)){
      map.get(categories).add(logData);
    }else{
      BlockingQueue<LogData> bq=new ArrayBlockingQueue<LogData>(queueCapacity);
      bq.add(logData);
      map.put(categories, bq);
      
      LogWriter logWriter=new DefaultLogWriter(slaveRootDir+categories);
      Thread t=new EventListener(logWriter,bq);
      t.setDaemon(true);
      t.start();
    }
    }
}
