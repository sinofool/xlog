package com.renren.dp.xlog.storage;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import xlog.slice.LogData;

import com.renren.dp.xlog.io.LogWriter;
import com.renren.dp.xlog.util.LogDataFormat;

public class EventListener extends Thread{

	private LogWriter logWriter=null;
	private BlockingQueue<LogData> logBQ=null;
	
	public EventListener(LogWriter logWriter,BlockingQueue<LogData> logBQ){
		this.logWriter=logWriter;
		this.logBQ=logBQ;
	}
	
	public void run(){
		
		while(true){
			try {
				LogData logData=logBQ.take();
				boolean res=StorageAdapterFactory.getInstance().store(logData);
				
				if(res){
					logWriter.write(LogDataFormat.transformLogDataToMeta(logData),true);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
}
