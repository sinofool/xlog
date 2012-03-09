package dp.election;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

import xlog.proto.Xlog.ClusterInfo;
import xlog.proto.Xlog.ItemInfo;
import dp.zk.ZkConn;

public class HACluster<K, V> {

    private final ZkConn conn;
    private final String clusterpath;
    private final GenericHashBuilder<K> keyBuilder;
    private final GenericProxyBuilder<V> valueBuilder;

    private ServingItem[] slots;

    public HACluster(ZkConn conn, String clusterpath, GenericHashBuilder<K> kb,
            GenericProxyBuilder<V> vb) {
        this.conn = conn;
        this.clusterpath = clusterpath;
        this.keyBuilder = kb;
        this.valueBuilder = vb;
    }

    public void initialize() throws KeeperException, InterruptedException, IOException {
        byte[] data = conn.get().getData(clusterpath, false, null);
        ClusterInfo clusterInfo = ClusterInfo.parseFrom(data);
        int size = clusterInfo.getSize();
        slots = new ServingItem[size];
        for (int i = 0; i < size; ++i) {
            slots[i] = new ServingItem(conn, clusterpath, i);
            slots[i].initialize();
        }
    }

    public int size() {
        return slots.length;
    }

    public V get(K key) throws InterruptedException {
        int size = size();
        int kid = keyBuilder.hash(key, size);
        String config = slots[Math.abs(kid % size)].getLocation();
        return valueBuilder.cast(config);
    }

    public void add(ItemInfo item) throws KeeperException, InterruptedException, IOException {
        conn.get().create(this.clusterpath + "/" + HABalancer.WAIT_LOOP, item.toByteArray(),
                Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    }
}
