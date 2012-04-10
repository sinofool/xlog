package com.renren.dp.xlog.sync;

import java.util.Timer;

import com.renren.dp.xlog.config.Configuration;

public class SyncMain {

	public static void main(String[] args){
		long interval=Configuration.getLong("log.sync.interval",1000*60*60);
		Timer t=new Timer();
		t.schedule(new SyncThread(),0,interval);
	}
}
