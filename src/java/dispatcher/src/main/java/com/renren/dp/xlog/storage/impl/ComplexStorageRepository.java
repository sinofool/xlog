package com.renren.dp.xlog.storage.impl;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.renren.dp.xlog.config.Configuration;
import com.renren.dp.xlog.logger.LogMeta;
import com.renren.dp.xlog.storage.NamespaceInfo;
import com.renren.dp.xlog.storage.StorageRepository;

public class ComplexStorageRepository implements StorageRepository{

	private StorageRepository memoryStorageRepository=null;
	private StorageRepository fileStorageRepository=null;
	private String[] memoryCacheCategories=null;
	private StringBuffer sb=new StringBuffer();
	
	private ReentrantLock lock=new ReentrantLock();
	
	public ComplexStorageRepository(StorageRepository memoryStorageRepository, StorageRepository fileStorageRepository){
		this.memoryStorageRepository=memoryStorageRepository;
		this.fileStorageRepository=fileStorageRepository;
		String categories=Configuration.getString("memory.cache.categories","");
		if(!"".equals(categories)){
			memoryCacheCategories=categories.split(",");
		}
	}
	
	@Override
	public void addToRepository(LogMeta logMeta) {
		// TODO Auto-generated method stub
		if(isMemoryStorage(logMeta.getLogData().categories)){
			memoryStorageRepository.addToRepository(logMeta);
		}else{
			fileStorageRepository.addToRepository(logMeta);
		}
	}
	
	private boolean isMemoryStorage(String[] categories){
		lock.lock();
		if(memoryCacheCategories==null||categories==null){
			return false;
		}
		int len=categories.length;
		for(int i=0;i<len;i++){
			if(i>0) sb.append("|");
			
			sb.append(categories[i]);
		}
		String tmp=sb.toString();
		sb.delete(0, sb.length());
		for(String s:memoryCacheCategories){
			if(tmp.equals(s)){
				return true;
			}
		}
		lock.unlock();
		return false;
	}

	@Override
	public void checkRepository() {
		// TODO Auto-generated method stub
		fileStorageRepository.checkRepository();
		memoryStorageRepository.checkRepository();
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
