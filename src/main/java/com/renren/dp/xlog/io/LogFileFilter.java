package com.renren.dp.xlog.io;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.renren.dp.xlog.util.Constants;

public class LogFileFilter implements FileFilter{

	private SimpleDateFormat sdf=null;
	
	public LogFileFilter(){
		sdf=new SimpleDateFormat(Constants.FILE_NAME_FORMAT);
	}
	
	@Override
	public boolean accept(File oplogFile) {
		String logFileNum=sdf.format(new Date());
		if(oplogFile.getName().endsWith(".done")||oplogFile.getName().equals(logFileNum)){
			return false;
		}
		return true;
	}

}

