package com.renren.dp.xlog.storage;

import xlog.slice.LogData;

public interface StorageRepository {

	public void addToRepository(LogData[]logDatas); 
	
	public void addToRepository(LogData logData); 
}
