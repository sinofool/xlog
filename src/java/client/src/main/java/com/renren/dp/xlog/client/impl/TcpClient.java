package com.renren.dp.xlog.client.impl;

import com.renren.dp.xlog.agent.ProtocolType;
import com.renren.dp.xlog.client.Client;

public class TcpClient extends Client{

  public TcpClient(String[] agents,int queueSize){
    super(agents,queueSize);
  }
  
  public TcpClient(String[] agents){
    super(agents);
  }
  
  @Override
  protected ProtocolType getProtocol() {
    // TODO Auto-generated method stub
    return ProtocolType.TCP;
  }

}
