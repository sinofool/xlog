package com.renren.dp.xlog.storage;

import com.renren.dp.xlog.config.Configuration;
import com.renren.dp.xlog.storage.impl.DefaultStorageRepository;
import com.renren.dp.xlog.storage.impl.MultiQueueStorageRepository;

public class StorageRepositoryFactory {

	private static StorageRepository storageRepository=null;
	
	public static StorageRepository getInstance(){
		if(storageRepository==null){
			String storageRepositoryMode=Configuration.getString("storage.repository.mode");
			if(storageRepositoryMode==null || "0".equals(storageRepositoryMode)){
					storageRepository=new DefaultStorageRepository();
			}else{
				storageRepository=new MultiQueueStorageRepository();
			}
		}
		
		return storageRepository;
	}
}
