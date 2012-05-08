package com.renren.dp.xlog.io;

import java.util.List;

import com.renren.dp.xlog.logger.LogMeta;

public interface LogWriter{

	/**
	 * 
	 * @param logMetas writer
	 * @param isAllowPartSuccess �Ƿ����?�ֳɹ�
	 * @return
	 */
	public boolean write(List<LogMeta> logMetas,boolean isAllowPartSuccess);
	
	public boolean write(LogMeta logMeta);
	
	public void close();
}
