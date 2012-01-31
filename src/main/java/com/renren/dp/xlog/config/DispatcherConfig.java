package com.renren.dp.xlog.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

import com.renren.dp.xlog.common.Xlog2zkFormat;
import com.renren.dp.xlog.common.ZkConn;
import com.renren.dp.xlog.dispatcher.DispatcherI;

public class DispatcherConfig {
    public static DispatcherConfig create(ZkConn conn) throws IOException {
        DispatcherConfig cfg = new DispatcherConfig();
        cfg.initialize(conn);
        return cfg;
    }

    private ZkConn conn;
    private List<String> children;

    private void initialize(ZkConn conn) throws IOException {
        this.conn = conn;
        try {
            boolean retry = false;
            do {
                retry = false;
                try {
                    children = conn.get().getChildren("/dispatchers", false);
                } catch (KeeperException e) {
                    // TODO log this
                    retry = true;
                }
            } while (retry);
        } catch (InterruptedException e) {
            // TODO log this
            e.printStackTrace();
        }
    }

    public void addDispatcher(DispatcherI obj) {
        Xlog2zkFormat format = obj.toZk();
        try {
            System.out.println("Creating " + format.path + " data: " + new String(format.data));
            conn.get().create(format.path, format.data, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (KeeperException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String[] listDispatcher() {
        return children.toArray(new String[children.size()]);
    }

    public String getDispatcher(String uuid) {
        try {
            return new String(conn.get().getData("/dispatchers/" + uuid, false, null));
        } catch (KeeperException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        ZkConn conn = new ZkConn();
        DispatcherConfig cfg = new DispatcherConfig();
        cfg.initialize(conn);
        System.out.println(Arrays.toString(cfg.listDispatcher()));

        conn.close();
    }
}
