package com.renren.dp.xlog.storage.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import xlog.slice.LogData;

import com.renren.dp.xlog.config.Configuration;
import com.renren.dp.xlog.io.LogWriter;
import com.renren.dp.xlog.io.impl.DefaultLogWriter;
import com.renren.dp.xlog.storage.EventListener;
import com.renren.dp.xlog.storage.StorageRepository;

public class DefaultStorageRepository implements StorageRepository{

	private BlockingQueue<LogData> logBQ=null;
	
	public DefaultStorageRepository(){
		logBQ=new ArrayBlockingQueue<LogData>(Configuration.getInt("storage.repository.queue.capacity", 1000));
		LogWriter logWriter=new DefaultLogWriter(Configuration.getString("oplog.store.path")+"/"+Configuration.getString("storage.type"));
		
		Thread t=new EventListener(logWriter,logBQ);
		t.setDaemon(true);
		t.start();
	}
	public void addToRepository(LogData[]logDatas){
		for(LogData logData:logDatas){
		  addToRepository(logData);
		}
	}
  @Override
  public void addToRepository(LogData logData) {
    logBQ.add(logData);
  }
	
}
