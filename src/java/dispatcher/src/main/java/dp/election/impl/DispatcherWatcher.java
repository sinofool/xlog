package dp.election.impl;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dp.election.HAClusterManager;

public class DispatcherWatcher implements Watcher{

  private HAClusterManager haCluster=null;
  private String currentZNode=null;
  
  private final static Logger logger = LoggerFactory.getLogger(DispatcherWatcher.class);

  public DispatcherWatcher(HAClusterManager haCluster,String currentZNode){
    this.haCluster=haCluster;
    this.currentZNode=currentZNode;
  }
  @Override
  public void process(WatchedEvent arg0) {
    try {
      if(haCluster.isMasterExists()){
        haCluster.updateDispatcherWatcher(this,currentZNode);
      }else{
        haCluster.electMasterAndUpdateWatcher(this,currentZNode);
      }
    } catch (KeeperException e) {
      logger.error("fail to process znode "+this.currentZNode,e);
    } catch (InterruptedException e) {
      logger.error("fail to process znode "+this.currentZNode,e);
    } catch (IOException e) {
      logger.error("fail to process znode "+this.currentZNode,e);
    }
    
  }

}
