package dp.zk;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.renren.dp.xlog.config.Configuration;

public class ZkConn {
  
  private ZooKeeper _conn;

  private final static Logger logger = LoggerFactory.getLogger(ZkConn.class);

  public ZkConn() {
  }

  private synchronized void connect() throws IOException {
    _conn = new ZooKeeper(Configuration.getString("zookeeper.connstr"), 2 * 1000, new DefaultWatcher());
  }

  public ZooKeeper get() throws IOException {
    if (_conn == null) {
      connect();
    }
    return _conn;
  }

  public void close() {
    try {
      _conn.close();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private class DefaultWatcher implements Watcher {
    @Override
    public void process(WatchedEvent event) {
      if (event.getState() == KeeperState.Expired) {
        logger.info("zookeeper reconnect ......");
        try {
          connect();
          logger.info("success to reconnect zookeeper!");
        } catch (IOException e) {
          logger.error("fail to reconnect zookeeper!");
        }
      }
    }

  }
}
