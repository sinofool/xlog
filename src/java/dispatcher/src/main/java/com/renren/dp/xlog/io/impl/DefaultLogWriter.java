package com.renren.dp.xlog.io.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

import com.renren.dp.xlog.io.LogWriter;

public class DefaultLogWriter implements LogWriter {

	private FileWriter fw = null;
	private BufferedWriter br = null;
	private File logFile=null;

	private final ReentrantLock lock = new ReentrantLock();

	public DefaultLogWriter() {
	}
	
	public boolean createFile(File logFile){
		this.logFile=logFile;
		if (!logFile.exists()) {
			File p = logFile.getParentFile();
			if (!p.exists()) {
				p.mkdirs();
			}
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		try {
			fw = new FileWriter(logFile, true);
			br = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean write(String logFileNum, String[] logs,
			boolean isAllowPartSuccess) {
		lock.lock();
		for (String log : logs) {
			if(log==null){
				continue;
			}
			try {
				br.write(log + "\n");
			} catch (IOException e) {
				e.printStackTrace();
				if (isAllowPartSuccess) {
					continue;
				}else{
					break;
				}
			}
		}
		try {
			br.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			lock.unlock();
		}

		return true;
	}

	public String getLogFileName(){
		return logFile.getName();
	}
	
	public void rename(String suffix){
		File destFile=new File(logFile.getAbsolutePath()+suffix);
		if(!destFile.exists()){
			logFile.renameTo(destFile);
		}
	}

	public void close() {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (fw != null) {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
