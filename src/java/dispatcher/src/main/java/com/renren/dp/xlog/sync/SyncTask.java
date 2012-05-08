package com.renren.dp.xlog.sync;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.renren.dp.xlog.cache.CacheManagerFactory;
import com.renren.dp.xlog.handler.FileNameHandlerFactory;
import com.renren.dp.xlog.logger.LogMeta;
import com.renren.dp.xlog.storage.StorageRepository;
import com.renren.dp.xlog.storage.StorageRepositoryFactory;
import com.renren.dp.xlog.util.Constants;
import com.renren.dp.xlog.util.LogDataFormat;

import xlog.slice.LogData;

public class SyncTask extends Thread{

	private String cacheLogDir=null;
	private File slaveLogFile=null;
	private int batchCommitSize;
	private int slaveLogRootDirLen;
	
	private final static Logger logger = LoggerFactory.getLogger(SyncTask.class);
	
	public  SyncTask(String cacheLogDir,File slaveLogFile,int slaveLogRootDirLen,int batchCommitSize){
		this.cacheLogDir=cacheLogDir;
		this.slaveLogFile=slaveLogFile;
		this.batchCommitSize=batchCommitSize;
		this.slaveLogRootDirLen=slaveLogRootDirLen;
	}
	
	@Override
	public void run() {
		String cacheLogStr=cacheLogDir+"/"+slaveLogFile.getAbsolutePath().substring(slaveLogRootDirLen);
		String categories=slaveLogFile.getParent().substring(slaveLogRootDirLen);
		boolean res=false;
		File cacheLogFile=null;
		if(slaveLogFile.getName().endsWith(Constants.LOG_WRITE_ERROR_SUFFIX)){
			cacheLogFile=new File(cacheLogStr.replaceFirst(Constants.LOG_WRITE_ERROR_SUFFIX, Constants.LOG_WRITE_FINISHED_SUFFIX));
			//保证数据的一致性,
			if(cacheLogFile.exists()){
				res=store(cacheLogFile,categories);
			}
		}else{
			cacheLogFile=new File(cacheLogStr);
			if(cacheLogFile.exists()){
				res=store(slaveLogFile,categories);
			}
		}
		if(res){
			if(rename(cacheLogFile)){
				rename(slaveLogFile);
			}
		}
	}
	
	private boolean store(File sourceFile,String categories){
		StorageRepository sr=StorageRepositoryFactory.getInstance();
		FileReader fr=null;
		try {
			fr = new FileReader(sourceFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		
		BufferedReader br=new BufferedReader(fr);
		String line=null;
		int i=0;
		String[] logs=new String[batchCommitSize];
		LogData logData=null;
		String logFileNum=null;
		boolean res=true;
		try {
			while((line=br.readLine())!=null){
				logs[i++]=line;
				
				if(i==batchCommitSize){
					i=0;
					logData=LogDataFormat.transformToLogData(categories,logs);
					logFileNum=FileNameHandlerFactory.getInstance().getCacheLogFileNum();
					LogMeta logMeta=new LogMeta(logFileNum,logData,2);
					if(CacheManagerFactory.getInstance().writeCache(logMeta)){
						sr.addToRepository(logMeta);
					}
					logs=new String[batchCommitSize];
				}
			}
			if(i>0){
				logData=LogDataFormat.transformToLogData(categories,logs);
				logFileNum=FileNameHandlerFactory.getInstance().getCacheLogFileNum();
				LogMeta logMeta=new LogMeta(logFileNum,logData,2);
				if(CacheManagerFactory.getInstance().writeCache(logMeta)){
					sr.addToRepository(logMeta);
				}
			}
		} catch (IOException e) {
			res=false;
			logger.error("fail to sync file! the file_name : "+slaveLogFile.getAbsolutePath()+",the exception is : {} ",e.getMessage());
		}finally{
			try {
				br.close();
			} catch (IOException e) {
			}
			try {
				fr.close();
			} catch (IOException e) {
			}
		}
		return res;
	}
	private boolean rename(File file){
		return file.renameTo(new File(file.getAbsolutePath()+Constants.LOG_SYNC_FINISHED_SUFFIX));
	}
}
