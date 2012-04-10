package com.renren.dp.xlog.client.impl;

import com.renren.dp.xlog.agent.ProtocolType;
import com.renren.dp.xlog.client.Client;

public class UdpClient extends Client{

  public UdpClient(String[] agents,int queueSize){
    super(agents,queueSize);
  }
  
  public UdpClient(String prxStr,String[] agents){
    super(agents);
  }
  
  @Override
  protected ProtocolType getProtocol() {
    // TODO Auto-generated method stub
    return ProtocolType.UDP;
  }

}
