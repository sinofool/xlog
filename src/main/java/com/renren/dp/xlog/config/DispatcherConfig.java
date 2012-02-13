package com.renren.dp.xlog.config;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

import xlog.DispatcherPrx;

import com.renren.dp.xlog.common.Xlog2zkFormat;
import com.renren.dp.xlog.common.ZkConn;
import com.renren.dp.xlog.dispatcher.DispatcherI;

public class DispatcherConfig<T> {
    public static DispatcherConfig<DispatcherPrx> create(ZkConn conn, Ice.Communicator ic)
            throws IOException {
        DispatcherConfig<DispatcherPrx> cfg = new DispatcherConfig<DispatcherPrx>(
                new DispatcherProxyFactory(ic));
        cfg.initialize(conn);
        return cfg;
    }

    private ZkConn conn;
    private NodeCluster<String[], T> dispatcher_cluster;

    private final ProxyFactory<T> proxy_factory;

    public DispatcherConfig(ProxyFactory<T> proxyFactory) {
        this.proxy_factory = proxyFactory;
    }

    private void initialize(ZkConn conn) throws IOException {
        this.conn = conn;
        boolean retry = false;
        do {
            retry = false;
            dispatcher_cluster = new NodeCluster<String[], T>(conn, "/dispatchers",
                    new DispatcherHashKey(), proxy_factory);
            try {
                dispatcher_cluster.initialize();
            } catch (KeeperException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } while (retry);
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

    public T getDispatcher(String[] category) {
        return dispatcher_cluster.getNode(category);
    }
}
