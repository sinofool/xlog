package com.renren.dp.xlog.config;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.apache.zookeeper.KeeperException;

import com.renren.dp.xlog.common.ZkConn;

public class NodeItem implements Comparable<NodeItem> {
    private final ZkConn conn;
    private final String path;
    private xlog.Xlog.NodeItem item;

    public NodeItem(ZkConn conn, String path) {
        this.conn = conn;
        this.path = path;
    }

    public void initialize() throws KeeperException, InterruptedException, IOException {
        byte[] data = conn.get().getData(path, false, null);
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
        xlog.Xlog.NodeItem.Builder itemBuilder = xlog.Xlog.NodeItem.newBuilder();
        itemBuilder.setType(in.readInt());
        itemBuilder.setLocation(in.readUTF());
        item = itemBuilder.build();
    }

    public String getLocation() {
        return item.getLocation();
    }

    @Override
    public int compareTo(NodeItem o) {
        return this.path.compareTo(o.path);
    }

}
