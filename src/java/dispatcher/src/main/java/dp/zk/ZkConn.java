package dp.zk;

import java.io.IOException;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZkConn {
  
  private ZooKeeper _conn;
  private Watcher watcher=null;
  private String strConn=null;
  private int sessionTimeOut;

 // private final static Logger logger = LoggerFactory.getLogger(ZkConn.class);

  public ZkConn(String strConn,int sessionTimeOut,Watcher watcher) {
	  this.strConn=strConn;
	  this.sessionTimeOut=sessionTimeOut;
	  this.watcher=watcher;
  }

  private synchronized void connect() throws IOException {
    _conn = new ZooKeeper(strConn, sessionTimeOut, this.watcher);
  }

  public ZooKeeper get() throws IOException {
    if (_conn == null) {
      connect();
	 }
	 return _conn;
  }
  
  public void reconnect() throws IOException {
    close();
    connect();
  }

  public void close() {
    try {
      _conn.close();
      _conn=null;
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
