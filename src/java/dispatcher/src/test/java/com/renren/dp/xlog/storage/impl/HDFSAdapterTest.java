package com.renren.dp.xlog.storage.impl;

import java.io.IOException;

import xlog.slice.LogData;

import com.renren.dp.xlog.config.Configuration;
import com.renren.dp.xlog.logger.LogMeta;
import com.renren.dp.xlog.storage.StorageAdapter;

import junit.framework.TestCase;

public class HDFSAdapterTest extends TestCase{

	private StorageAdapter sa=null;
	private LogData logData=null;
	public void setUp(){
		sa=new HDFSAdapter(Configuration.getString("xlog.uuid"),Configuration.getInt("hdfs.buffer.size", 4000));
		try {
			sa.initialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logData=new LogData();
       logData.categories=new String[]{"www","renren","com"};
       logData.checkSum="123";
       String log="AdEngineU36@10.3.19.184||123.147.249.163||2012-02-07 17:25:00||GET /show?userid=247361632&tt=0&type=.js&adzoneid=100000000065&count=1&next_load_time=&refresh_idx=&rowoffset=0&ip=123.147.249.163&jbc=cookie|a|b&ubc=1000000_247361632|1|1990-01-01|22|2|0086510100000000|400000015004_0086510100000000|42|0|0|0086500000000000HTTP/1.0||200||undefined||1393145099059607590||undefined||10||36||none||http%3A%2F%2Fwww%2Erenren%2Ecom%2Fhome%3Frefresh%5Fsource%3D0%26refresh%5Fidx%3D0||100000000065^B^B^B^B^B^B^B||1000037785003200001^B100000017330^B1000037785003200002^B0^B150^Bhttp%3A%2F%2Fwww%2Erenren%2Ecom%2Fhome%3Frefresh%5Fsource%3D0%26refresh%5Fidx%3D0^B1^B100001^B2^B-1||4||rr_REMAIN_2_98";
       String[] logs=new String[3000];
       for(int i=0;i<3000;i++){
    	   logs[i]=log;
       }
       logData.logs=logs;
	}
	
	public void testStore(){
		long currentTime=System.currentTimeMillis();
		long lastTime=currentTime+1000*60*5;
		while(currentTime<lastTime){
			try {
				sa.store(new LogMeta("2012-12-18-15",logData));
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			currentTime=System.currentTimeMillis();
		}
	}
}
