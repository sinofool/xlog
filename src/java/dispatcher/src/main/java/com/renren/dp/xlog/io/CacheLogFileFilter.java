package com.renren.dp.xlog.io;

import java.io.File;
import java.io.FileFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.renren.dp.xlog.util.Constants;

public class CacheLogFileFilter implements FileFilter{

	private long threshold;
	private SimpleDateFormat sdf=null;
	private int len;
	
	public CacheLogFileFilter(int oplogCacheTime){
		sdf=new SimpleDateFormat(Constants.FILE_NAME_FORMAT_MIN);
		len=Constants.FILE_NAME_FORMAT_MIN.length();
		threshold=System.currentTimeMillis()-oplogCacheTime;
	} 
	
	@Override
	public boolean accept(File oplogFile) {
		if(oplogFile.isDirectory()){
			return true;
		}
		String fileName=oplogFile.getName();
		if(fileName.endsWith(Constants.LOG_SYNC_FINISHED_SUFFIX)){
			Date d=null;
			try {
				d = sdf.parse(fileName.substring(0,len));
			} catch (ParseException e) {
				return false;
			}
			long logFileNum=d.getTime();
			if(logFileNum<=threshold){
				return true;
			}
		}
		
		return false;
	}
}
