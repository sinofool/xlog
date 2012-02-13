package com.renren.dp.xlog.config;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import xlog.Xlog.NodeConfig;

import com.renren.dp.xlog.common.ZkConn;

public class NodeQueue implements Watcher {
    private final ZkConn conn;
    private final String parent;

    private NodeItem currentNode;
    private NodeItem nextNode;

    public NodeQueue(ZkConn conn, String parent) {
        this.conn = conn;
        this.parent = parent;
    }

    public boolean initialize() throws KeeperException, InterruptedException, IOException {
        byte[] data = conn.get().getData(parent, false, null);
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
        NodeConfig.Builder cfgBuilder = NodeConfig.newBuilder();
        cfgBuilder.setType(in.readInt());
        cfgBuilder.setPrefix(in.readUTF());
        NodeConfig cfg = cfgBuilder.build();
        assert (cfg.getType() == 1);// FIFO

        ArrayList<NodeItem> items = new ArrayList<NodeItem>();
        List<String> children = conn.get().getChildren(parent, this);
        for (String child : children) {
            if (!child.startsWith(parent + "/" + cfg.getPrefix())) {
                continue;
            }
            NodeItem item = new NodeItem(conn, child);
            item.initialize();
            items.add(item);
        }
        Collections.sort(items);
        if (items.size() < 2) {
            // TODO fire a re-balance. and wait.
            return false;
        }
        currentNode = items.get(0);
        nextNode = items.get(1);
        return true;
    }

    public NodeItem getCurrent() {
        return currentNode;
    }

    @Override
    public void process(WatchedEvent event) {
        // TODO Auto-generated method stub

    }

}
