package com.renren.dp.xlog.agent;

import com.renren.dp.xlog.exception.XlogClientException;

import xlog.slice.LogData;

public interface AgentAdapter {

  public boolean init(String[] agents ,ProtocolType protocolType);
  
  public boolean send(LogData[] data) throws XlogClientException; 
  
}
