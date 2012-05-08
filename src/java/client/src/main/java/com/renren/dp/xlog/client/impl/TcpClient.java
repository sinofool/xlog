package com.renren.dp.xlog.client.impl;

import com.renren.dp.xlog.agent.ProtocolType;
import com.renren.dp.xlog.client.Client;
import com.renren.dp.xlog.exception.XlogClientException;

public class TcpClient extends Client{

  public TcpClient(String[] agents,int queueSize,boolean isSync) throws XlogClientException{
    super(agents,queueSize,isSync);
  }
  
  public TcpClient(String[] agents,boolean isSync) throws XlogClientException{
    super(agents,isSync);
  }
  
  @Override
  protected ProtocolType getProtocol() {
    // TODO Auto-generated method stub
    return ProtocolType.TCP;
  }

}
