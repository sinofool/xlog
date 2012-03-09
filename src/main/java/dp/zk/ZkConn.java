package dp.zk;

import java.io.IOException;
import java.util.Properties;

import org.apache.zookeeper.ZooKeeper;

public class ZkConn {
    private ZooKeeper _conn;

    public ZkConn() {
    }

    private synchronized void initialize() throws IOException {
        Properties props = new Properties();
        props.load(ZkConn.class.getResourceAsStream("/xlog.properties"));
        String connstr = props.getProperty("zookeeper.connstr");
        _conn = new ZooKeeper(connstr, 2 * 1000, null);
    }

    public ZooKeeper get() throws IOException {
        if (_conn == null) {
            initialize();
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

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ZkConn conn = new ZkConn();
        ZooKeeper c = conn.get();
        System.out.println(c);
    }

}
