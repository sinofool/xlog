package dp.election;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;

import dp.zk.ZkConn;

import xlog.proto.Xlog.ClusterInfo;

public abstract class ClusterManager<K, V> {
  
  protected ZkConn conn;
  protected String clusterpath;
  protected GenericHashBuilder<K> keyBuilder;
  protected GenericProxyBuilder<V> valueBuilder;

  protected List<ServingItem>[] slots;
  protected String currentZnode;
  protected WatcherManager watcherManager;
  protected NodeChooseAlgorithm chooseAlgorithm;
  
  public void initialize() throws KeeperException, InterruptedException, IOException {
    byte[] data = conn.get().getData(clusterpath, false, null);
    ClusterInfo clusterInfo = ClusterInfo.parseFrom(data);
    int size = clusterInfo.getSize();
    slots = new List[size];
    ServingItem si = null;
    String path;
    List<ServingItem> siList = null;
    for (int i = 0; i < size; ++i) {
      path=clusterpath + "/" + i;
      List<String> childrenList = conn.get().getChildren(path, false);
      siList = new ArrayList<ServingItem>();
      for (String znode : childrenList) {
        si = new ServingItem(conn, path+"/"+znode);
        si.initialize();

        siList.add(si);
      }
      slots[i] = siList;
    }
  }

  public int size() {
    return slots.length;
  }
  
  public V get(K key) throws InterruptedException {
    int size = size();
    int kid = keyBuilder.hash(key, size);
    List<ServingItem> siList = slots[Math.abs(kid % size)];
    if (siList.isEmpty()) {
      return null;
    }
    String config = chooseAlgorithm.doChoose(siList).getLocation();

    return valueBuilder.cast(config);
  }
  
  public abstract void addWatchers(long delayTime) throws KeeperException, InterruptedException, IOException;
}
