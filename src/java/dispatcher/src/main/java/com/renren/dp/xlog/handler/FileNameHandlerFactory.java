package com.renren.dp.xlog.handler;

import com.renren.dp.xlog.config.Configuration;
import com.renren.dp.xlog.handler.impl.FileNameHandlerOf10Min;
import com.renren.dp.xlog.handler.impl.FileNameHandlerOf5Min;

public class FileNameHandlerFactory {

	private static AbstractFileNameHandler fileNameHandler=null;
	
	public static AbstractFileNameHandler getInstance(){
		if(fileNameHandler==null){
			int fileNameFormat=Configuration.getInt("cache.file.name.format",0);
			if(fileNameFormat == 0){
				fileNameHandler=new FileNameHandlerOf5Min();
			}else if(fileNameFormat == 1){
				fileNameHandler=new FileNameHandlerOf10Min();
			}
		}
		return fileNameHandler;
	}
}
