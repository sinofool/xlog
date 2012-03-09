package com.renren.dp.xlog.config;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

import xlog.slice.DispatcherPrx;

import com.renren.dp.xlog.dispatcher.DispatcherI;

import dp.election.GenericProxyBuilder;
import dp.election.HACluster;
import dp.zk.ZkConn;

public class DispatcherCluster<T> {
    
    public static DispatcherCluster<DispatcherPrx> create(ZkConn conn, Ice.Communicator ic)
            throws IOException {
        DispatcherCluster<DispatcherPrx> cfg = new DispatcherCluster<DispatcherPrx>(
                new DispatcherProxyFactory(ic));
        cfg.initialize(conn);
        return cfg;
    }

    private ZkConn conn;
    private HACluster<String[], T> dispatcher_cluster;

    private final GenericProxyBuilder<T> proxy_factory;

    public DispatcherCluster(GenericProxyBuilder<T> proxyFactory) {
        this.proxy_factory = proxyFactory;
    }

    private void initialize(ZkConn conn) throws IOException {
        this.conn = conn;
        boolean retry = false;
        do {
            retry = false;
            dispatcher_cluster = new HACluster<String[], T>(conn, "/dispatchers",
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
        try {
            dispatcher_cluster.add(obj.toItemInfo());
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

    public T getDispatcher(String[] category) throws InterruptedException {
        return dispatcher_cluster.get(category);
    }
}
