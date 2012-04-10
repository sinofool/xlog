package com.renren.dp.xlog.storage;

import java.io.IOException;

import xlog.slice.LogData;

public interface StorageAdapter {

	public boolean store(LogData logData) throws IOException;
}
