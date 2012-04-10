package dp.election.impl;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class DefaultWatcher implements Watcher{

  @Override
  public void process(WatchedEvent event) {
    if(event.getState() == KeeperState.Expired) {
      
    }
  }

}
