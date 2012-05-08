package dp.election;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;

import xlog.proto.Xlog.ItemInfo;
import dp.election.impl.DefaultWatcherManager;
import dp.election.impl.DispatcherWatcher;
import dp.election.impl.RandomChooseAlgorithm;
import dp.zk.ZkConn;

public class HAClusterManager<K, V> extends ClusterManager<K, V>{

  private ItemInfo item;
  //标记自己是否为master节点
  private boolean isMaster=false;
  
  public HAClusterManager(ZkConn conn, String clusterpath, GenericHashBuilder<K> kb, GenericProxyBuilder<V> vb) {
    this.conn = conn;
    this.clusterpath = clusterpath;
    this.keyBuilder = kb;
    this.valueBuilder = vb;
    chooseAlgorithm = new RandomChooseAlgorithm();
    watcherManager=new DefaultWatcherManager(conn,clusterpath);
  }

  public void add(ItemInfo item) throws KeeperException, InterruptedException, IOException {
    this.item=item;
    int pos=chooseSlot();
    currentZnode=conn.get().create(this.clusterpath + "/" + pos+"/zn_"+pos, item.toByteArray(), Ids.OPEN_ACL_UNSAFE,
        CreateMode.EPHEMERAL_SEQUENTIAL);
    if(!isMasterExists()){
      isMaster=true;
      conn.get().create(this.clusterpath + "/master",currentZnode.getBytes(), Ids.OPEN_ACL_UNSAFE,
          CreateMode.EPHEMERAL);
    }
  }
  private int chooseSlot(){
    int pos=0;
    int size=slots.length;
    int count=Integer.MAX_VALUE;
    int tmp;
    for(int i=0;i<size;i++){
      tmp=slots[i].size();
      if(count>tmp){
        count=tmp;
        pos=i;
      }
    }
    return pos;
  }
  public boolean isMasterExists() throws KeeperException, InterruptedException, IOException{
    if(conn.get().exists(this.clusterpath + "/master",false)==null){
      return false;
    }
    return true;
  }
  
  public void createZNode(int slot) throws KeeperException, InterruptedException, IOException{
    String logicZnode=conn.get().create(this.clusterpath + "/" + slot+"/zn_"+slot,this.item.toByteArray(), Ids.OPEN_ACL_UNSAFE,
        CreateMode.EPHEMERAL_SEQUENTIAL);
    watcherManager.setDispatcherWatcher(logicZnode,new DispatcherWatcher(this,logicZnode));
  }
  
  public void addWatchers(long delayTime) throws KeeperException, InterruptedException, IOException{
    if(isMaster){
      watcherManager.setSlotWatcher(slots.length,System.currentTimeMillis()+delayTime);
    }else{
      watcherManager.setDispatcherWatcher(currentZnode,new DispatcherWatcher(this,currentZnode));
    }
  }
  
  public void electMasterAndUpdateWatcher(Watcher watcher,String znode) throws KeeperException, InterruptedException, IOException{
    synchronized(this){
      int size=slots.length;
      String master="";
      String path;
      for(int i=0;i<size;i++){
        path=clusterpath + "/" + i;
        List<String> childrenList = conn.get().getChildren(path, false);
        if(childrenList==null || childrenList.isEmpty()){
          continue;
        }
        Collections.sort(childrenList);
        master=path+"/"+childrenList.get(0);
        break;
      }
      if(master.equals(znode)){
        conn.get().create(this.clusterpath + "/master",master.getBytes(), Ids.OPEN_ACL_UNSAFE,
            CreateMode.EPHEMERAL);
        watcherManager.setSlotWatcher(slots.length,0L);
      }else{
        while(conn.get().exists(master,watcher)==null){
           Thread.sleep(2000);
        };
      }
    }
  }
  
  public void updateDispatcherWatcher(Watcher watcher,String znode) throws KeeperException, InterruptedException, IOException{
    watcherManager.setDispatcherWatcher(znode,watcher);
  }
}
