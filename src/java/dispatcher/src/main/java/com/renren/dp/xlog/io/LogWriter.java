package com.renren.dp.xlog.io;

import java.io.File;


public interface LogWriter{

	/**
	 * 
	 * @param logMetas writer
	 * @param isAllowPartSuccess 是否允许写部分成功
	 * @return
	 */
	public boolean write(String logFileNum, String[] logs,boolean isAllowPartSuccess);
	
	public boolean createFile(File logFile);
	
	public String getLogFileName();
	
	public void rename(String suffix);
	
	public void close();
}
