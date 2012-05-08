package com.renren.dp.xlog.sync;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.renren.dp.xlog.cache.CacheManager;
import com.renren.dp.xlog.cache.CacheManagerFactory;
import com.renren.dp.xlog.config.Configuration;
import com.renren.dp.xlog.io.SuffixFileFilter;
import com.renren.dp.xlog.storage.StorageRepository;
import com.renren.dp.xlog.storage.StorageRepositoryFactory;
import com.renren.dp.xlog.util.Constants;

public class SyncTimer extends TimerTask {

	private String cacheLogDir = null;
	private int slaveLogRootDirLen;
	private String slaveLogDir = null;
	private String[] categoriesPath=null;
	private int batchCommitSize;

	 private static Logger logger = LoggerFactory.getLogger(SyncTimer.class);

	public SyncTimer(String[] categoriesPath) {
		String storePath = Configuration.getString("oplog.store.path");
		this.categoriesPath=categoriesPath;
		slaveLogDir = storePath + "/" + Configuration.getString("storage.type");
		slaveLogRootDirLen=slaveLogDir.length();
		batchCommitSize = Configuration.getInt("batch.commit.size", 1000);
		cacheLogDir = storePath + "/" + CacheManager.CACHE_TYPE;
	}

	@Override
	public void run() {
		CacheManager cm=CacheManagerFactory.getInstance();
		cm.checkCache();
		StorageRepository sr=StorageRepositoryFactory.getInstance();
		sr.checkRepository();
		
		List<SyncTask> stList = new ArrayList<SyncTask>();
		FileFilter ff=new SuffixFileFilter(new String[]{Constants.LOG_WRITE_FINISHED_SUFFIX,Constants.LOG_WRITE_ERROR_SUFFIX});
		buildSyncTask(stList, new File(slaveLogDir),ff);
		if(logger.isDebugEnabled()){
			logger.debug("it start "+stList.size()+" threads to sync data!");
		}
		
		for (SyncTask st : stList) {
			try {
				st.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	private void buildSyncTask(List<SyncTask> stList, File dir,FileFilter ff) {
		File[] logFiles = dir.listFiles(ff);
		if (logFiles == null || logFiles.length == 0) {
			return;
		}
		SyncTask st = null;
		for (File logFile : logFiles) {
			if (logFile.isFile()) {
				st = new SyncTask(cacheLogDir, logFile,slaveLogRootDirLen, batchCommitSize);
				st.setDaemon(true);
				st.start();

				stList.add(st);
			} else {
				if(isDemandSync(logFile.getAbsolutePath())){
					buildSyncTask(stList, logFile,ff);
				}
			}
		}
	}
	
	private boolean isDemandSync(String cachePath){
		if(categoriesPath==null){
			return true;
		}
		for(String s:categoriesPath){
			if(cachePath.endsWith(s)){
				return true;
			}
		}
		return false;
	}
}
