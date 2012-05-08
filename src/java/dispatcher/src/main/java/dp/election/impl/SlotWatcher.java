package dp.election.impl;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dp.election.HABalancerI;
import dp.zk.ZkConn;

public class SlotWatcher implements Watcher {

  private long startTime;
  private final ZkConn conn;
  private final HABalancerI haBalancer;
  private int slot;
  private boolean isStartWatch = false;

  private final static Logger logger = LoggerFactory.getLogger(SlotWatcher.class);

  public SlotWatcher(ZkConn conn, String clusterPath, int slot, HABalancerI haBalancer, long startTime) {
    this.conn = conn;
    this.haBalancer = haBalancer;
    this.startTime = startTime;
    this.slot = slot;

    new DelayThead(this, clusterPath).start();
  }

  @Override
  public void process(WatchedEvent event) {
    if (!isStartWatch) {
      return;
    }
    EventType type = event.getType();
    String znode = event.getPath();
    if(znode == null){
    	logger.info("slot watcher does not get the event path!");
    	return ;
    }
    synchronized (this) {
      if (type == EventType.NodeChildrenChanged) {
        try {
          if (!haBalancer.checkBalanceCondition(slot)) {
            haBalancer.doBalance(slot);
            }
         } catch (KeeperException e) {
           logger.error("fail to rebalance of slot " + slot, e);
         } catch (InterruptedException e) {
           logger.error("fail to rebalance of slot " + slot, e);
         } catch (IOException e) {
           logger.error("fail to rebalance of slot " + slot, e);
          }
       }
      try {
        conn.get().getChildren(znode, this);
      } catch (KeeperException e) {
        logger.error("fail to set watcher for " + znode, e);
      } catch (InterruptedException e) {
        logger.error("fail to set watcher for " + znode, e);
      } catch (IOException e) {
        logger.error("fail to set watcher for " + znode, e);
      }
    }

  }

  class DelayThead extends Thread {
    private SlotWatcher slotWatcher = null;
    private String clusterPath = null;

    public DelayThead(SlotWatcher slotWatcher, String clusterPath) {
      this.slotWatcher = slotWatcher;
      this.clusterPath = clusterPath;
    }

    public void run() {
      while (System.currentTimeMillis() < startTime) {
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      isStartWatch = true;
      slotWatcher.process(new WatchedEvent(EventType.NodeChildrenChanged, null, clusterPath + "/" + slot));
    }
  }
}
