package com.renren.dp.xlog.config;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;

import com.renren.dp.xlog.common.ZkConn;

public class NodeCluster<K, V> {

    private final ZkConn conn;
    private final String clusterpath;
    private final HashableKey<K> keyfactory;
    private final ProxyFactory<V> valuefactory;
    private NodeQueue[] nodes;

    public NodeCluster(ZkConn conn, String clusterpath, HashableKey<K> keyfactory,
            ProxyFactory<V> valuefactory) {
        this.conn = conn;
        this.clusterpath = clusterpath;
        this.keyfactory = keyfactory;
        this.valuefactory = valuefactory;
    }

    public void initialize() throws KeeperException, InterruptedException, IOException {
        byte[] rawsize = conn.get().getData(clusterpath, false, null);
        assert (rawsize.length == 1);
        int size = rawsize[0];
        List<String> children = conn.get().getChildren(clusterpath, false);
        assert (children.size() == size);
        nodes = new NodeQueue[size];
        for (int i = 0; i < size; ++i) {
            nodes[i] = new NodeQueue(conn, clusterpath + i);
            nodes[i].initialize();
        }
    }

    public int size() {
        return nodes.length;
    }

    public V getNode(K key) {
        int size = size();
        int kid = keyfactory.hash(key, size);
        String config = nodes[Math.abs(kid % size)].getCurrent().getLocation();
        return valuefactory.get(config);
    }
}
