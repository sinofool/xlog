package com.renren.dp.xlog.storage.impl;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import xlog.slice.LogData;

import com.renren.dp.xlog.storage.StorageAdapter;
import com.renren.dp.xlog.util.Constants;
import com.renren.dp.xlog.util.LogDataFormat;

public class HDFSAdapter implements StorageAdapter{

	private FileSystem fs=null;
	private SimpleDateFormat sdf=null;
	public HDFSAdapter() throws IOException{
		String hdfsURI=com.renren.dp.xlog.config.Configuration.getString("storage.uri");
		Configuration conf=new Configuration();
		fs=FileSystem.get(URI.create(hdfsURI),conf);
		
		sdf=new SimpleDateFormat(Constants.FILE_NAME_FORMAT);
	}
	
	@Override
	public boolean store(LogData logData){
		FSDataOutputStream hdfsOutput=null;
		try {
			Path p=new Path(getFilePath(LogDataFormat.transformCatefories(logData.categories)));
			if(fs.exists(p)){
				hdfsOutput=fs.append(p);
			}else{
				hdfsOutput=fs.create(p);
			}
			for(String log:logData.logs){
				hdfsOutput.writeBytes((log+"\n"));
			}
			
			hdfsOutput.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				if(hdfsOutput!=null){
					hdfsOutput.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	private String getFilePath(String path){
		String strDate=sdf.format(new Date());
		
		return path+"/"+strDate;
	}
}
