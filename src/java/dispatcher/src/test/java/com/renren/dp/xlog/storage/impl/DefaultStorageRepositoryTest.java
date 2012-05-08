package com.renren.dp.xlog.storage.impl;

import com.renren.dp.xlog.storage.StorageRepository;
import com.renren.dp.xlog.storage.StorageRepositoryFactory;

import junit.framework.TestCase;

public class DefaultStorageRepositoryTest extends TestCase{

	private StorageRepository sr=null;
	
	public void setUp(){
		sr=StorageRepositoryFactory.getInstance();
	}
	
	public void testGetCacheFilesSize(){
		long size=sr.getCacheFilesSize();
		System.out.println(size);
		assertEquals(true,size>0);
	}
}
