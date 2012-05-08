package com.renren.dp.xlog.client;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.renren.dp.xlog.agent.AgentAdapter;
import com.renren.dp.xlog.agent.ProtocolType;
import com.renren.dp.xlog.agent.impl.DefaultAgentAdapter;
import com.renren.dp.xlog.exception.XlogClientException;

import xlog.slice.LogData;

public abstract class Client{

  private BlockingQueue<LogData[]> logDataBQ=null;
  private int queueSize=10000;
  private AgentAdapter agentAdapter=null;
  private boolean isSync;
  
  private String[] agents=null;
  
  protected Client(String[] agents,int queueSize,boolean isSync) throws XlogClientException{
	if(!isSync){
	  logDataBQ=new ArrayBlockingQueue<LogData[]>(queueSize);
    }
    this.isSync=isSync;
    this.agents = agents;
    if(queueSize>0){
      this.queueSize=queueSize;
     }
    init(isSync);
  }
  
  protected Client(String[] agents,boolean isSync) throws XlogClientException{
	if(!isSync){
		logDataBQ=new ArrayBlockingQueue<LogData[]>(queueSize);
	}
    this.isSync=isSync;
    this.agents = agents;
    init(isSync);
  }
  
  public boolean send(LogData[] logDatas) throws XlogClientException{
    if(logDatas==null){
      return false;
     }   
    if(isSync){
    	return sendBySync(logDatas);
    }else{
    	return sendByAsync(logDatas);
    }
  }
  
  private boolean sendByAsync(LogData[] logDatas){
    if(logDataBQ.size()==queueSize){
	   return false;
	 }
	 logDataBQ.add(logDatas);
	 return true;
  }
  
  private boolean sendBySync(LogData[] logDatas) throws XlogClientException{
	  
    return agentAdapter.send(logDatas);
  }
  
  private void init(boolean isSync) throws XlogClientException{
    agentAdapter=new DefaultAgentAdapter();
    if(!agentAdapter.init(agents, getProtocol())){
    	throw new XlogClientException("Fail to initialise agent adapter,please check agents parameter!");
    }
    if(!isSync){
    	new Sender().start();
    }
  }
  
  protected abstract ProtocolType getProtocol();
  
  private class Sender extends Thread{
    public void  run(){
      while(true){
        try {
          LogData[] logDatas=logDataBQ.take();
          agentAdapter.send(logDatas);
        } catch (InterruptedException e) {
          e.printStackTrace();
          continue;
        } catch (XlogClientException e) {
			e.printStackTrace();
			continue;
		} 
      }
    }
  }
}
