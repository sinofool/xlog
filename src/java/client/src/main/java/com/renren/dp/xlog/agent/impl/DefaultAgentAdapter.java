package com.renren.dp.xlog.agent.impl;

import java.util.ArrayList;
import java.util.List;

import xlog.slice.AgentPrx;
import xlog.slice.AgentPrxHelper;
import xlog.slice.LogData;

import com.renren.dp.xlog.agent.AgentAdapter;
import com.renren.dp.xlog.agent.ProtocolType;

public class DefaultAgentAdapter implements AgentAdapter{

  private List<AgentPrx> prxList=null;
  private int currrentAgentPrxNumber=0;
  
  public DefaultAgentAdapter(){
    prxList=new ArrayList<AgentPrx>();
  }
  //agents host:port
  @Override
  public boolean init(String[] agents, ProtocolType protocolType) {
    if(agents==null){
      return false;
    }
    Ice.Communicator ic = Ice.Util.initialize();
    AgentPrx prx = null;
    String[] tmp;
    for(String agent:agents){
      tmp=agent.split(":");
      if(protocolType==ProtocolType.UDP){
        prx = AgentPrxHelper.uncheckedCast(ic
            .stringToProxy("A:tcp -h "+tmp[0]+" -p "+tmp[1]).ice_datagram());
      }else if(protocolType==ProtocolType.TCP){
        prx = AgentPrxHelper.uncheckedCast(ic
            .stringToProxy("A:tcp -h "+tmp[0]+" -p "+tmp[1]));
      }
      prxList.add(prx);
    }
    
    return true;
  }

  @Override
  public void send(LogData[] data) {
    if(data==null){
      return ;
    }
    AgentPrx prx=null;
    int size=prxList.size();
    for(int i=0;i<size;i++){
      try{
        prx=getAgentPrx();
        prx.add(data);
        
        return ;
      }catch(Exception e){
        System.err.println("fail to send data,but try send data again!try count is :"+(i+1));
        e.printStackTrace();
        continue;
      }
      
    }
    
  }

  private AgentPrx getAgentPrx(){
    int size=prxList.size();
    int index= currrentAgentPrxNumber++ % size;
    
    return prxList.get(index);
  }
}
