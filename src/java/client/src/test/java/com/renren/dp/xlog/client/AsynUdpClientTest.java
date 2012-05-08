package com.renren.dp.xlog.client;

import xlog.slice.LogData;

import com.renren.dp.xlog.client.impl.UdpClient;
import com.renren.dp.xlog.exception.XlogClientException;

import junit.framework.TestCase;

public class AsynUdpClientTest extends TestCase{

  private Client client=null;
  private  LogData logData1,logData2,logData3;
  
  protected void setUp(){
    try {
		client=new UdpClient(new String[]{"10.22.198.189:10000"},false);
	} catch (XlogClientException e) {
		e.printStackTrace();
	}
    
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
  
  public void testSend(){
    try {
      boolean res=client.send(new LogData[]{logData1,logData2,logData3});
      assertEquals(true,res);
      Thread.sleep(1000*10);
    } catch (Exception e) {
    	fail();
    	e.printStackTrace();
    }
  }
}
