package com.renren.dp.xlog.handler.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.renren.dp.xlog.handler.AbstractFileNameHandler;
import com.renren.dp.xlog.util.Constants;

public class FileNameHandlerOf5Min extends AbstractFileNameHandler{

	private SimpleDateFormat sdf =null;
	
	public FileNameHandlerOf5Min(){
		sdf = new SimpleDateFormat(Constants.FILE_NAME_FORMAT_MIN);
	}
	
	public String getCacheLogFileNum() {
		String strDate=sdf.format(new Date());
		int min=Integer.parseInt(strDate.substring(15,16));
		if(min >= 5){
			return strDate.substring(0,15)+"5";
		}else{
			return strDate.substring(0,15)+"0";
		}
	}
}
