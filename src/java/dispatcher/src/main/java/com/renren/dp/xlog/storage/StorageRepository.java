package com.renren.dp.xlog.storage;

import java.util.List;

import com.renren.dp.xlog.logger.LogMeta;

public interface StorageRepository {

	public void addToRepository(LogMeta logMeta);
	
	public void checkRepository();
	
	public List<NamespaceInfo> getNamespaceInfo();
	
	public long getCacheFilesSize();
	
	public void close();
}
