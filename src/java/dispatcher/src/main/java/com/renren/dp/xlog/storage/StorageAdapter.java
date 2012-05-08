package com.renren.dp.xlog.storage;

import java.io.IOException;

public interface StorageAdapter {

	public void initialize() throws IOException;
	
	public boolean store(Object o) throws IOException;
	
	public void destory();
}
