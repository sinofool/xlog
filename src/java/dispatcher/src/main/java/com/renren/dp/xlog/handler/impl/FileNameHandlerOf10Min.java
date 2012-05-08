package com.renren.dp.xlog.handler.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.renren.dp.xlog.handler.AbstractFileNameHandler;
import com.renren.dp.xlog.util.Constants;

public class FileNameHandlerOf10Min extends AbstractFileNameHandler{

	public FileNameHandlerOf10Min(){
		sdf = new SimpleDateFormat(Constants.FILE_NAME_FORMAT_MIN);
	}
	
	public String getCacheLogFileNum(){
		return sdf.format(new Date()).substring(0,15)+"0";
	}
}
