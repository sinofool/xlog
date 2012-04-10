package com.renren.dp.xlog.agent;

import xlog.slice.LogData;

public interface AgentAdapter {

  public boolean init(String[] agents ,ProtocolType protocolType);
  
  public void send(LogData[] data); 
  
}
