package com.renren.dp.xlog.client;

import xlog.slice.LogData;

import com.renren.dp.xlog.client.impl.TcpClient;

import junit.framework.TestCase;

public class TcpClientTest extends TestCase{

  private Client client=null;
  private  LogData logData1,logData2,logData3;
  
  protected void setUp(){
    client=new TcpClient(new String[]{"127.0.0.1:10000"});
    
    logData1=new LogData();
    logData1.categories=new String[]{"www","renren","com"};
    logData1.checkSum="123";
    logData1.logs=new String[]{"hello21","hi21"};
    
    logData2=new LogData();
    logData2.categories=new String[]{"www","renren","com"};
    logData2.checkSum="222";
    logData2.logs=new String[]{"hello31","hi31"};
    
    logData3=new LogData();
    logData3.categories=new String[]{"www","renren","com"};
    logData3.checkSum="333";
    logData3.logs=new String[]{"hello41","hi41"};
  }
  
  public void testAppend(){
    boolean res=client.append(new LogData[]{logData1,logData2,logData3});
    
    assertEquals(true,res);
    
    try {
      Thread.sleep(1000*60);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
