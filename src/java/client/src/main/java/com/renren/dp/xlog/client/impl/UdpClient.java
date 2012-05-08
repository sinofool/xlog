package com.renren.dp.xlog.client.impl;

import com.renren.dp.xlog.agent.ProtocolType;
import com.renren.dp.xlog.client.Client;
import com.renren.dp.xlog.exception.XlogClientException;

public class UdpClient extends Client{

  public UdpClient(String[] agents,int queueSize,boolean isSync) throws XlogClientException{
    super(agents,queueSize,isSync);
  }
  
  public UdpClient(String[] agents,boolean isSync) throws XlogClientException{
    super(agents,isSync);
  }
  
  @Override
  protected ProtocolType getProtocol() {
    return ProtocolType.UDP;
  }

}
