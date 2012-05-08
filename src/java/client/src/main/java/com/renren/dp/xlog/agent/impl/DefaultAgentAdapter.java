package com.renren.dp.xlog.agent.impl;

import java.util.ArrayList;
import java.util.List;

import xlog.slice.AgentPrx;
import xlog.slice.AgentPrxHelper;
import xlog.slice.LogData;

import com.renren.dp.xlog.agent.AgentAdapter;
import com.renren.dp.xlog.agent.ProtocolType;
import com.renren.dp.xlog.exception.XlogClientException;

public class DefaultAgentAdapter implements AgentAdapter{

  private List<AgentPrx> prxList=null;
  private int currrentAgentPrxNumber=0;
  //unit bytes
  private static final String UDP_MESSAGE_SIZE="10485760";
  //unit kilo bytes
  private static final String ICE_MESSAGE_SIZE_MAX="10240";
  
  public DefaultAgentAdapter(){
    prxList=new ArrayList<AgentPrx>();
  }
  //agents host:port
  @Override
  public boolean init(String[] agents, ProtocolType protocolType) {
    if(agents==null){
      return false;
    }
    Ice.Properties prop = Ice.Util.createProperties();
    if(protocolType==ProtocolType.UDP){
    	//10M udp unit is bytes 
    	prop.setProperty("Ice.UDP.SndSize", UDP_MESSAGE_SIZE);
    	prop.setProperty("Ice.UDP.RcvSize", UDP_MESSAGE_SIZE);
    }
    //prop.setProperty("Ice.MessageSizeMax", ICE_MESSAGE_SIZE_MAX);
    Ice.InitializationData initData = new Ice.InitializationData();  
    initData.properties = prop;  
    Ice.Communicator ic = Ice.Util.initialize(initData);
    
    AgentPrx prx = null;
    String[] tmp;
    for(String agent:agents){
      tmp=agent.split(":");
      if(protocolType==ProtocolType.UDP){
        prx = AgentPrxHelper.uncheckedCast(ic
            .stringToProxy("A:udp -h "+tmp[0]+" -p "+tmp[1]).ice_locatorCacheTimeout(60).ice_compress(true).ice_datagram());
      }else if(protocolType==ProtocolType.TCP){
        prx = AgentPrxHelper.uncheckedCast(ic
            .stringToProxy("A:tcp -h "+tmp[0]+" -p "+tmp[1]));
      }
      prxList.add(prx);
    }
    
    return true;
  }

  @Override
  public boolean send(LogData[] data) throws XlogClientException{
    if(data==null){
      return false;
     }
    AgentPrx prx=null;
    int size=prxList.size();
    if(size==0){
    	throw new XlogClientException("It can't find agent proxy!");
     }
    for(int i=0;i<size;i++){
      try{
        prx=getAgentPrx(size);
        prx.add(data);
        
        return true;
      }catch(Exception e){
        System.err.println("fail to send data,but try send data again!try count is :"+(i+1));
        e.printStackTrace();
        continue;
      }
     }
    return false;
  }

  private AgentPrx getAgentPrx(int size){
    if(currrentAgentPrxNumber==size){
    	currrentAgentPrxNumber=0;
    }
    AgentPrx prx=prxList.get(currrentAgentPrxNumber);
    currrentAgentPrxNumber++;
    return prx;
  }
}
