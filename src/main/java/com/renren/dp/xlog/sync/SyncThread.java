package com.renren.dp.xlog.sync;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import com.renren.dp.xlog.cache.CacheManager;
import com.renren.dp.xlog.config.Configuration;
import com.renren.dp.xlog.io.LogFileFilter;

public class SyncThread extends TimerTask{

	private File cacheLogDir=null;
	private int cacheLogDirLen;
	private String slaveLogDir=null;
	private int batchCommitSize;
//	private static Logger logger = LoggerFactory.getLogger(SyncThread.class);
	
	public SyncThread(){
		String storePath=Configuration.getString("oplog.store.path");
		slaveLogDir=storePath+"/"+Configuration.getString("storage.type");
		batchCommitSize=Configuration.getInt("batch.commit.size",1000);
		cacheLogDir=new File(storePath+"/"+CacheManager.CACHE_TYPE);
		cacheLogDirLen=cacheLogDir.getAbsolutePath().length();
		
		if(!cacheLogDir.exists()){
			cacheLogDir.mkdirs();
		}
	}
	
	@Override
	public void run() {
	  List<SyncTask> stList=new ArrayList<SyncTask> ();
	  
		doSchedule(stList,cacheLogDir);
		
		for(SyncTask st:stList){
      try {
        st.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
        continue;
      }
    }
	}

	private void doSchedule(List<SyncTask> stList ,File dir){
		File[] logFiles=dir.listFiles(new LogFileFilter());
		if(logFiles==null || logFiles.length==0){
			return ;
		}
		SyncTask st=null;
		File slaveLogFile=null;
		String slaveLogPath=null;
		for(File logFile:logFiles){
		  if(logFile.isFile()){
		    slaveLogPath=slaveLogDir+"/"+logFile.getAbsolutePath().substring(cacheLogDirLen);
		    slaveLogFile=new File(slaveLogPath);
	      if(slaveLogFile.exists()){
	        st=new SyncTask(logFile,slaveLogFile,batchCommitSize);
	      }else{
	        st=new SyncTask(logFile,slaveLogPath,batchCommitSize);
	      }
	      st.setDaemon(true);
	      st.start();
	      
	      stList.add(st);
		  }else{
		    doSchedule(stList,logFile);
		  }
		}
	}
}
