package com.renren.dp.xlog.storage;

import java.io.IOException;

import com.renren.dp.xlog.config.Configuration;
import com.renren.dp.xlog.storage.impl.HDFSAdapter;

public class StorageAdapterFactory {

	private static StorageAdapter storageAdapter=null;
	
	public static StorageAdapter getInstance(){
		if(storageAdapter==null){
			String storageType=Configuration.getString("storage.type");
			if(storageType==null || "hdfs".equals(storageType)){
				try {
					storageAdapter=new HDFSAdapter();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return storageAdapter;
	}
}
