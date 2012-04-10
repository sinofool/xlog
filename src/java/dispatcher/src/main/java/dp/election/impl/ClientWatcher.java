package dp.election.impl;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import dp.election.DispatcherClientManager;
import dp.zk.ZkConn;

public class ClientWatcher implements Watcher{

  private String path;
  private DispatcherClientManager clientManager;
  public ClientWatcher(DispatcherClientManager clientManager,String path){
    this.path=path;
    this.clientManager=clientManager;
  }
  @Override
  public void process(WatchedEvent arg0) {
    try {
      clientManager.updateSlots(path,this);
    } catch (KeeperException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
