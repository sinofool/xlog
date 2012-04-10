package com.renren.dp.xlog.client;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.renren.dp.xlog.agent.AgentAdapter;
import com.renren.dp.xlog.agent.ProtocolType;
import com.renren.dp.xlog.agent.impl.DefaultAgentAdapter;

import xlog.slice.LogData;

public abstract class Client{

  private BlockingQueue<LogData> logDataBQ=null;
  private int queueSize=10000;
  private AgentAdapter agentAdapter=null;
  
  private String[] agents=null;
  
  protected Client(String[] agents,int queueSize){
    logDataBQ=new ArrayBlockingQueue<LogData>(queueSize);
    this.agents = agents;
    if(queueSize>0){
      this.queueSize=queueSize;
    }
    init();
  }
  
  protected Client(String[] agents){
    logDataBQ=new ArrayBlockingQueue<LogData>(queueSize);
    this.agents = agents;
    init();
  }
  
  public boolean append(LogData[] logDatas){
    if(logDatas==null){
      return false;
    }   
    if(logDataBQ.size()+logDatas.length>queueSize){
      return false;
    }
    for(LogData data:logDatas){
      logDataBQ.add(data);
    }
    return true;
  }
  
  private void init(){
    agentAdapter=new DefaultAgentAdapter();
    if(agentAdapter.init(agents, getProtocol())){
      new Sender().start();
    }
  }
  
  protected abstract ProtocolType getProtocol();
  
  private class Sender extends Thread{
    public void  run(){
      while(true){
        try {
          LogData logData=logDataBQ.take();
          agentAdapter.send(new LogData[]{logData});
        } catch (InterruptedException e) {
          e.printStackTrace();
          continue;
        } 
      }
    }
  }
  
  
}

