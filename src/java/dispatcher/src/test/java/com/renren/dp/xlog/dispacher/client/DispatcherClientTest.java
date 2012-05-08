package com.renren.dp.xlog.dispacher.client;

import java.io.IOException;

import junit.framework.TestCase;

import com.renren.dp.xlog.config.Configuration;
import com.renren.dp.xlog.dispatcher.adapter.DispatcherAdapter;

import xlog.slice.DispatcherPrx;
import xlog.slice.LogData;

import dp.zk.ZkConn;

public class DispatcherClientTest extends TestCase{
    private final String[] CATEGORIES = new String[] { "xlog", "example", "level3", "file" };

    private DispatcherPrx prx =null;
    public void setUp(){
      ZkConn conn = new ZkConn(Configuration.getString("zookeeper.connstr"),
    		  Configuration.getInt("zk.session.timeout",5),null);
        
      Ice.Communicator ic = Ice.Util.initialize();
      DispatcherAdapter<DispatcherPrx> cfg=null;
      try {
        cfg = DispatcherAdapter.create(conn, ic);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      try {
        prx = cfg.getDispatcher(CATEGORIES);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  
    public void testAddLogData(){
      try{
        LogData logData=new LogData();
        logData.categories=new String[]{"www","renren","com"};
        logData.checkSum="123";
        logData.logs=new String[]{"test3","test4"};
        
        prx.addLogData(logData);
      }catch(Exception e){
        e.printStackTrace();
        fail();
      }
    }
}
