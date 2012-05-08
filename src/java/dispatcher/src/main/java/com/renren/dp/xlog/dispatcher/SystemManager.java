package com.renren.dp.xlog.dispatcher;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.renren.dp.xlog.cache.CacheManagerFactory;
import com.renren.dp.xlog.config.Configuration;
import com.renren.dp.xlog.dispatcher.DispatcherApp;
import com.renren.dp.xlog.storage.NamespaceInfo;
import com.renren.dp.xlog.storage.StorageRepositoryFactory;
import com.renren.dp.xlog.util.Constants;

public class SystemManager {

	public String getVersion(){
		return Constants.VERSION;
	}
	
	public String getStartDate(){
		SimpleDateFormat sdf=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		Date d=DispatcherApp.getInstance().getStartDate();
		if(d==null){
			return null;
		}
		return sdf.format(d);
	}
	
	public int getDispatcherStatus(){
		return DispatcherApp.getInstance().getStatus(); 
	}
	
	public List<NamespaceInfo> getNamespaceInfo(){
		return StorageRepositoryFactory.getInstance().getNamespaceInfo();
	}
	
	public boolean start(){
		try{
			DispatcherApp.getInstance().start();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean stop(){
		try{
			DispatcherApp.getInstance().stop();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public String getParam(String key){
		return Configuration.getString(key);
	}
	
	public String getCacheFilesSize(){
		return fileSizeFormat(CacheManagerFactory.getInstance().getCacheFilesSize());
	}
	public String getSendFailureFileSize(){
		return fileSizeFormat(StorageRepositoryFactory.getInstance().getCacheFilesSize());
	}
	
	private String fileSizeFormat(long size){
		if(size<1024){
			return size+"KB";
		}else if(size<1048576){
			size=size/1024;
			return size+"MB";
		}else{
			size=size/1048576;
			return size+"MB";
		}
	}
}
