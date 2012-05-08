package com.renren.dp.xlog.dispacher.client;

import com.renren.dp.xlog.storage.StorageRepository;
import com.renren.dp.xlog.storage.StorageRepositoryFactory;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StorageRepository sr=StorageRepositoryFactory.getInstance();
		long size=sr.getCacheFilesSize();
		System.out.println(size);
	}

}
