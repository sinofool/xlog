package com.renren.dp.xlog.storage;

import com.renren.dp.xlog.config.Configuration;
import com.renren.dp.xlog.storage.impl.HDFSAdapter;

public class StorageAdapterFactory {

	public static StorageAdapter getInstance(){
		StorageAdapter storageAdapter=null;
		String storageType=Configuration.getString("storage.type");
		int bufferSize=Configuration.getInt("hdfs.buffer.size", 4000);
		if(storageType==null || "hdfs".equals(storageType)){
			storageAdapter=new HDFSAdapter(Configuration.getString("xlog.uuid"),bufferSize);
		}
		return storageAdapter;
	}
}
