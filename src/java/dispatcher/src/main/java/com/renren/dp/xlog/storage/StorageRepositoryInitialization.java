package com.renren.dp.xlog.storage;

import java.io.File;
import java.io.FileFilter;

public abstract class StorageRepositoryInitialization {

	public abstract void initialise();
	
	protected void check(File dir,String logFileNum,FileFilter fileFilter,String fileSuffix){
		File[] logFiles = dir.listFiles(fileFilter);
		if (logFiles == null || logFiles.length == 0) {
			return;
		}
		for (File logFile : logFiles) {
			if (logFile.isFile()) {
				if(!logFile.getName().equals(logFileNum)){
					logFile.renameTo(new File(logFile.getAbsolutePath()+fileSuffix));
				}
			} else {
				check(logFile,logFileNum,fileFilter,fileSuffix);
			}
		}
	}
	
}
