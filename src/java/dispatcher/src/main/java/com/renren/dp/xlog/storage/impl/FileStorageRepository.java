package com.renren.dp.xlog.storage.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.renren.dp.xlog.logger.LogMeta;
import com.renren.dp.xlog.storage.FileListener;
import com.renren.dp.xlog.storage.NamespaceInfo;
import com.renren.dp.xlog.storage.StorageRepository;
import com.renren.dp.xlog.util.LogDataFormat;

public class FileStorageRepository implements StorageRepository{

	private List<String> categoriesList=new ArrayList<String>();
	private ReentrantLock lock=new ReentrantLock();
	
	@Override
	public void addToRepository(LogMeta logMeta) {
		lock.lock();
		
		String categories=LogDataFormat.transformCatefories(logMeta.getLogData().categories);
		if(!categoriesList.contains(categories)){
			categoriesList.add(categories);
			Thread t=new FileListener(categories);
			t.setDaemon(true);
			t.start();
		}
		logMeta.free();
		logMeta=null;
		lock.unlock();
	}

	@Override
	public void checkRepository() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<NamespaceInfo> getNamespaceInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getCacheFilesSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}
