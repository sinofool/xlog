package dp.election.impl;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;

import dp.election.DispatcherClientManager;
import dp.election.WatcherManager;
import dp.zk.ZkConn;

public class DefaultWatcherManager implements WatcherManager{

  private final ZkConn conn;
  private final String clusterPath;
  
  public DefaultWatcherManager(ZkConn conn,String clusterPath){
    this.conn=conn;
    this.clusterPath=clusterPath;
  }
  

  @Override
  public void setSlotWatcher(int soltSize,long startTime) throws KeeperException, InterruptedException, IOException {
    for(int i=0;i<soltSize;i++){
      conn.get().getChildren(clusterPath+"/"+i,new SlotWatcher(conn,clusterPath,i,new DefaultHABalancer(conn,clusterPath,soltSize),startTime));
    }
  }

  @Override
  public void setDispatcherWatcher(String currentZnode,Watcher watcher) throws KeeperException, InterruptedException, IOException{
    int pos=currentZnode.lastIndexOf("/");
    String s1=currentZnode.substring(0, pos);
    pos=s1.lastIndexOf("/");
    int slot=Integer.parseInt(s1.substring(pos+1));
    
    List<String> childrenList = conn.get().getChildren(clusterPath + "/" +slot, false);
    Map<Long,String> map=new TreeMap<Long,String>();
    pos=currentZnode.indexOf("_");
    long currentNodeSEQ=Long.parseLong(currentZnode.substring(pos+1));
    for(String str:childrenList){
      pos=str.indexOf("_");
      map.put(Long.parseLong(str.substring(pos+1)),str);
    }
    childrenList.retainAll(childrenList);
    childrenList=null;
    Iterator<Long> it=map.keySet().iterator();
    
    String path=null;
    long key;
    while(it.hasNext()){
      key=it.next();
      if(key==currentNodeSEQ){
        break;
      }
      path=map.get(key);
    }
    if(path==null){
      conn.get().exists(clusterPath + "/master",watcher);
    }else{
      conn.get().exists(clusterPath + "/" +slot+"/"+path,watcher);
    }
  }


  @Override
  public void setClientWatcher(int soltSize,DispatcherClientManager clientManager) throws KeeperException, InterruptedException, IOException {
    String path;
    for(int i=0;i<soltSize;i++){
      path=clusterPath+"/"+i;
      conn.get().getChildren(path,new ClientWatcher(clientManager,path));
    }
  }
}
