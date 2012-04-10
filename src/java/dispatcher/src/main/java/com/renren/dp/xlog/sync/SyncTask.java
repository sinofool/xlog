package com.renren.dp.xlog.sync;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.renren.dp.xlog.io.LogWriter;
import com.renren.dp.xlog.io.impl.DefaultLogWriter;
import com.renren.dp.xlog.storage.StorageAdapterFactory;
import com.renren.dp.xlog.util.LogDataFormat;

import xlog.slice.LogData;

public class SyncTask extends Thread{

	private File srcFile=null;
	private File slaveLogFile=null;
	private int batchCommitSize;
	private LogWriter logWriter=null;
	
	public  SyncTask(File srcFile,File slaveLogFile,int batchCommitSize){
		this.srcFile=srcFile;
		this.slaveLogFile=slaveLogFile;
		this.batchCommitSize=batchCommitSize;
		this.logWriter=new DefaultLogWriter(slaveLogFile);
	}
	
	public  SyncTask(File srcFile,String slaveLogPath,int batchCommitSize){
		this.srcFile=srcFile;
		this.batchCommitSize=batchCommitSize;
		File f=new File(slaveLogPath);
		this.logWriter=new DefaultLogWriter(f);
	}
	
	@Override
	public void run() {
		if(this.slaveLogFile==null){
			loadCacheData();
		}else{
			compareAndLoadData();
		}
	}
	
	private void loadCacheData(){
		FileReader fr=null;
		try {
			fr = new FileReader(srcFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return ;
		}
		BufferedReader br=new BufferedReader(fr);
		String line=null;
		LogData[] logDatas=new LogData[this.batchCommitSize];
		int i=0;
		try {
			while((line=br.readLine())!=null){
				logDatas[i++]=LogDataFormat.transformStringToLogData(line);
				if(i==batchCommitSize){
					i=0;
					doStore(logDatas,batchCommitSize);
				}
			}
			if(i>0){
				doStore(logDatas,i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			logWriter.close();
		}
	}
	
	public void compareAndLoadData(){
		FileReader srcFR=null;
		FileReader targetFR=null;
		try {
			srcFR = new FileReader(srcFile);
			targetFR = new FileReader(slaveLogFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return ;
		}
		if(srcFile.getTotalSpace()==slaveLogFile.length()){
			srcFile.renameTo(new File(srcFile.getAbsoluteFile()+".done"));
			slaveLogFile.renameTo(new File(slaveLogFile.getAbsoluteFile()+".done"));
			return ;
		}
		BufferedReader srcBR=new BufferedReader(srcFR);
		BufferedReader targetBR=new BufferedReader(targetFR);
		String srcLine=null;
		String targetLine=null;
		int i=0;
		LogData[] logDatas=new LogData[this.batchCommitSize];
		
		try {
			while((targetLine=targetBR.readLine())!=null){
				while((srcLine=srcBR.readLine())!=null){
					if(srcLine.equals(targetLine)){
						break;
					}else{
						logDatas[i++]=LogDataFormat.transformStringToLogData(srcLine);
						if(i==batchCommitSize){
							i=0;
							doStore(logDatas,batchCommitSize);
						}
					}
				}
			}
			while((srcLine=srcBR.readLine())!=null){
				logDatas[i++]=LogDataFormat.transformStringToLogData(srcLine);
				if(i==batchCommitSize){
					i=0;
					doStore(logDatas,batchCommitSize);
				}
			}
			if(i>0){
				doStore(logDatas,i);
			}
			
			srcFile.renameTo(new File(srcFile.getAbsoluteFile()+".done"));
			slaveLogFile.renameTo(new File(slaveLogFile.getAbsoluteFile()+".done"));
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			logWriter.close();
		}
	}
	
	private void doStore(LogData[] logDatas,int len){
		boolean res=true;
		List<LogData> list=new ArrayList<LogData>();
		for(int i=0;i<len;i++){
			try {
				res=StorageAdapterFactory.getInstance().store(logDatas[i]);
				if(res){
					list.add(logDatas[i]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logWriter.write(LogDataFormat.transformLogDataToMeta(list.toArray(new LogData[list.size()])),true);
		
		list.removeAll(list);
		list=null;
	}
}
