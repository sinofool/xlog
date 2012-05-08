package dp.election;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;

import dp.election.impl.DefaultWatcherManager;
import dp.election.impl.RandomChooseAlgorithm;
import dp.zk.ZkConn;

public class DispatcherClientManager<K, V> extends ClusterManager<K, V> {

  public DispatcherClientManager(ZkConn conn, String clusterpath, GenericHashBuilder<K> kb, GenericProxyBuilder<V> vb) {
    this.conn = conn;
    this.clusterpath = clusterpath;
    this.keyBuilder = kb;
    this.valueBuilder = vb;
    chooseAlgorithm = new RandomChooseAlgorithm();
    watcherManager=new DefaultWatcherManager(conn,clusterpath);
  }
  
  @Override
  public void addWatchers(long delayTime) throws KeeperException, InterruptedException, IOException {
    watcherManager.setClientWatcher(this.slots.length,this);
  }
  
  public void updateSlots(String slotZnode,Watcher watcher) throws KeeperException, InterruptedException, IOException{
    int index=slotZnode.lastIndexOf("/");
    int pos=Integer.parseInt(slotZnode.substring(index+1));
    List<String> children=conn.get().getChildren(slotZnode,watcher);
    List<ServingItem> siList=this.slots[pos];
    siList.removeAll(siList);
    ServingItem si;
    String rootPath=slotZnode.substring(0,index+1);
    for(String childNode:children){
      si = new ServingItem(conn, rootPath+childNode);
      si.initialize();

      siList.add(si);
    }
  }
}
