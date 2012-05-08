package com.renren.dp.xlog.storage.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.renren.dp.xlog.config.Configuration;
import com.renren.dp.xlog.handler.FileNameHandlerFactory;
import com.renren.dp.xlog.io.LogWriter;
import com.renren.dp.xlog.io.impl.DefaultLogWriter;
import com.renren.dp.xlog.logger.LogMeta;
import com.renren.dp.xlog.storage.EventListener;
import com.renren.dp.xlog.storage.NamespaceInfo;
import com.renren.dp.xlog.storage.StorageRepository;
import com.renren.dp.xlog.util.FileUtil;
import com.renren.dp.xlog.util.LogDataFormat;

public class DefaultStorageRepository implements StorageRepository{

	private ConcurrentHashMap<String,EventListener> map=null;
	private int queueCapacity;
	
	private String slaveRootDir=null;
	
	private final static Logger logger = LoggerFactory.getLogger(DefaultStorageRepository.class);

	public DefaultStorageRepository(){
		map=new ConcurrentHashMap<String,EventListener>();
		queueCapacity=Configuration.getInt("storage.repository.queue.capacity", 1000);
		slaveRootDir=Configuration.getString("oplog.store.path")+"/"+Configuration.getString("storage.type");
	}
	
	public void addToRepository(LogMeta logMeta) {
     String categories=LogDataFormat.transformCatefories(logMeta.getLogData().categories);
     if(map.containsKey(categories)){
    	EventListener el=map.get(categories);
		el.add(logMeta);
	  }else{
		LogWriter logWriter=new DefaultLogWriter();
		String rootPath=slaveRootDir+categories;
		logWriter.createFile(new File(rootPath+"/"+logMeta.getLogFileNum()));
		
		EventListener el=new EventListener(logWriter,queueCapacity,rootPath);
		try {
			el.initialize();
			el.setDaemon(true);
			el.start();
		} catch (IOException e) {
			logger.error("fail to initialize categories event listener,the category is : "+categories);
			return ;
		}
		map.put(categories, el);
		el.add(logMeta);
	  }
    }
	
	public void checkRepository(){
		String logFileNum=FileNameHandlerFactory.getInstance().getCacheLogFileNum();
		Collection<EventListener> coll=map.values();
		for(EventListener el:coll){
			el.checkExpiredLogFile(logFileNum);
		}
	}

	@Override
	public List<NamespaceInfo> getNamespaceInfo() {
		List<NamespaceInfo> list=new ArrayList<NamespaceInfo>();
		Collection<String> c=map.keySet();
		Iterator<String> it=c.iterator();
		String category=null;
		EventListener el=null;
		while(it.hasNext()){
			category=it.next();
			el=map.get(category);
			list.add(new NamespaceInfo(category,el.getLogQueueSize(),el.getSuccessCount(),el.getFailureCount()));
		}
		
		return list;
	}

	@Override
	public void close() {
		Collection<String> c=map.keySet();
		Iterator<String> it=c.iterator();
		String category=null;
		EventListener el=null;
		while(it.hasNext()){
			category=it.next();
			el=map.get(category);
			el.close();
		}
	}

	@Override
	public long getCacheFilesSize() {
		File slaveFile=new File(slaveRootDir);
		if(!slaveFile.exists()){
			return 0;
		}
		return FileUtil.computeDirectorySize(slaveFile);
	}
}
