package com.renren.dp.xlog.dispatcher.adapter;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

import xlog.slice.DispatcherPrx;

import com.renren.dp.xlog.config.DispatcherHashKey;
import com.renren.dp.xlog.config.DispatcherProxyFactory;

import dp.election.DispatcherClientManager;
import dp.election.GenericProxyBuilder;
import dp.zk.ZkConn;

public class DispatcherAdapter<T> {
    
    public static DispatcherAdapter<DispatcherPrx> create(ZkConn conn, Ice.Communicator ic)
            throws IOException {
        DispatcherAdapter<DispatcherPrx> cfg = new DispatcherAdapter<DispatcherPrx>(
                new DispatcherProxyFactory(ic));
        cfg.initialize(conn);
        return cfg;
    }

    private DispatcherClientManager<String[], T> clientManager;

    private final GenericProxyBuilder<T> proxy_factory;

    public DispatcherAdapter(GenericProxyBuilder<T> proxyFactory) {
        this.proxy_factory = proxyFactory;
    }

    private void initialize(ZkConn conn) throws IOException {
        //this.conn = conn;
        boolean retry = false;
        do {
            retry = false;
            clientManager = new DispatcherClientManager<String[], T>(conn, "/dispatchers",
                  new DispatcherHashKey(), proxy_factory);
            try {
              clientManager.initialize();
            } catch (KeeperException e) {
                retry=true;
                e.printStackTrace();
            } catch (InterruptedException e) {
                retry=true;
                e.printStackTrace();
            }
        } while (retry);
        try {
          clientManager.addWatchers();
        } catch (KeeperException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
    }

    public T getDispatcher(String[] category) throws InterruptedException {
      return clientManager.get(category);
    }
}
