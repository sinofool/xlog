package com.renren.dp.xlog.client;

import junit.framework.TestCase;
import xlog.slice.LogData;

import com.renren.dp.xlog.client.impl.TcpClient;
import com.renren.dp.xlog.exception.XlogClientException;

public class SyncTcpClientTest extends TestCase{
  private Client client=null;
  private  LogData logData1,logData2,logData3;
	  
  protected void setUp(){
    try {
		client=new TcpClient(new String[]{"10.22.198.189:10000"},true);
	} catch (XlogClientException e) {
		e.printStackTrace();
	}
    
    logData1=new LogData();
    logData1.categories=new String[]{"www","renren","com","tt1"};
    logData1.checkSum="123";
    logData1.logs=new String[]{"hello21","hi21","tt"};
    
    logData2=new LogData();
    logData2.categories=new String[]{"www","renren","com","tt1"};
    logData2.checkSum="222";
    logData2.logs=new String[]{"hello31","hi31","tt"};
    
    logData3=new LogData();
    logData3.categories=new String[]{"www","renren","com","tt1"};
    logData3.checkSum="333";
    logData3.logs=new String[]{"hello41","hi41"};
  }
	  
  public void testSend(){
    try {
      boolean res=client.send(new LogData[]{logData1,logData2,logData3});
      assertEquals(true,res);
      Thread.sleep(1000*5);
     } catch (Exception e) {
    	e.printStackTrace();
    	fail();
     }
  }
  
//  public void testSend(){
//    String str="www/renren/com/access,www/renren/com/page,www/renren/com/3g,www/renren/com/feed,www/renren/com/api";
//    String[] cates=str.split(",");
//    String[][] categories=new String[cates.length][10];
//    String[] tmp;
//    for(int i=0;i<cates.length;i++){
//      tmp=cates[i].split("\\/");
//      for(int j=0;j<tmp.length;j++){
//        categories[i][j]=tmp[j];
//       }
//     }
//    int commitSize=1000;
//    String datas="AdEngineU36@10.3.19.184||123.147.249.163||2012-02-07 17:25:00||GET /show?userid=247361632&tt=0&type=.js&adzoneid=100000000065&count=1&next_load_time=&refresh_idx=&rowoffset=0&ip=123.147.249.163&jbc=cookie|a|b&ubc=1000000_247361632|1|1990-01-01|22|2|0086510100000000|400000015004_0086510100000000|42|0|0|0086500000000000HTTP/1.0||200||undefined||1393145099059607590||undefined||10||36||none||http%3A%2F%2Fwww%2Erenren%2Ecom%2Fhome%3Frefresh%5Fsource%3D0%26refresh%5Fidx%3D0||100000000065^B^B^B^B^B^B^B||1000037785003200001^B100000017330^B1000037785003200002^B0^B150^Bhttp%3A%2F%2Fwww%2Erenren%2Ecom%2Fhome%3Frefresh%5Fsource%3D0%26refresh%5Fidx%3D0^B1^B100001^B2^B-1||4||rr_REMAIN_2_98"+
//			   "AdEngineU36@10.3.19.184||123.147.249.163||2012-02-07 17:25:00||GET /show?userid=247361632&tt=0&type=.js&adzoneid=100000000065&count=1&next_load_time=&refresh_idx=&rowoffset=0&ip=123.147.249.163&jbc=cookie|a|b&ubc=1000000_247361632|1|1990-01-01|22|2|0086510100000000|400000015004_0086510100000000|42|0|0|0086500000000000HTTP/1.0||200||undefined||1393145099059607590||undefined||10||36||none||http%3A%2F%2Fwww%2Erenren%2Ecom%2Fhome%3Frefresh%5Fsource%3D0%26refresh%5Fidx%3D0||100000000065^B^B^B^B^B^B^B||1000037785003200001^B100000017330^B1000037785003200002^B0^B150^Bhttp%3A%2F%2Fwww%2Erenren%2Ecom%2Fhome%3Frefresh%5Fsource%3D0%26refresh%5Fidx%3D0^B1^B100001^B2^B-1||4||rr_REMAIN_2_98";
//    int categoriesLen=categories.length;
//    LogData[] logDataArr=new LogData[categoriesLen];
//    LogData logData=null;
//    String[] logs=new String[commitSize];
//    for(int i=0;i<categoriesLen;i++){
//      logData=new LogData();
//      logData.categories=categories[i];
//      logData.checkSum="123";
//      long l=System.nanoTime();
//      for(int j=0;j<commitSize;j++){
//        logs[j]=l+"_"+j+":"+datas;
//       }
//      logData.logs=logs;
//      logDataArr[i]=logData;
//     }
//    try{
//      client.send(logDataArr);
//    }catch(Exception e){
//      e.printStackTrace();
//     }
//  }
}
