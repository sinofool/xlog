package dp.election;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;


public interface WatcherManager {

  public final static int CLIENT_WATCHER=0;
  
  public final static int SERVER_WATCHER=1;
  
  public void setSlotWatcher(int soltsize,long startTime) throws KeeperException, InterruptedException, IOException;
  
  public void setClientWatcher(int soltsize,DispatcherClientManager clientManager) throws KeeperException, InterruptedException, IOException;
  
  public void setDispatcherWatcher(String currentZnode,Watcher watcher) throws KeeperException, InterruptedException, IOException;
}
