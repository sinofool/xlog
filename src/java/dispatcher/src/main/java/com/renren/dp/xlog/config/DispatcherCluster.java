package com.renren.dp.xlog.config;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xlog.slice.DispatcherPrx;

import com.renren.dp.xlog.dispatcher.DispatcherI;

import dp.election.GenericProxyBuilder;
import dp.election.HAClusterManager;
import dp.zk.ZkConn;

public class DispatcherCluster<T> {

  private final static Logger logger = LoggerFactory.getLogger(DispatcherCluster.class);

  public static DispatcherCluster<DispatcherPrx> create(ZkConn conn, Ice.Communicator ic) throws IOException {
    DispatcherCluster<DispatcherPrx> cfg = new DispatcherCluster<DispatcherPrx>(new DispatcherProxyFactory(ic));
    cfg.initialize(conn);
    return cfg;
  }

  // private ZkConn conn;
  private HAClusterManager<String[], T> haCluster;

  private final GenericProxyBuilder<T> proxy_factory;

  public DispatcherCluster(GenericProxyBuilder<T> proxyFactory) {
    this.proxy_factory = proxyFactory;
  }

  private void initialize(ZkConn conn) throws IOException {
    // this.conn = conn;
    boolean retry = false;
    do {
      retry = false;
      haCluster = new HAClusterManager<String[], T>(conn, "/dispatchers", new DispatcherHashKey(), proxy_factory);
      try {
        haCluster.initialize();
      } catch (KeeperException e) {
        retry = true;
        logger.error("fail to initialize hacluster,it will be retrying!",e);
      } catch (InterruptedException e) {
        retry = true;
        logger.error("fail to initialize hacluster,it will be retrying!",e);
      }
    } while (retry);
  }

  public void addDispatcher(DispatcherI obj) {
    try {
      haCluster.add(obj.toItemInfo());
    } catch (KeeperException e) {
      logger.error("fail to add znode and throw KeeperException!",e);
    } catch (InterruptedException e) {
      logger.error("fail to add znode and throw InterruptedException!",e);
    } catch (IOException e) {
      logger.error("fail to add znode and throw IOException!",e);
    }
  }

  public void createZNode(int slot) {
    try {
      haCluster.createZNode(slot);
    } catch (KeeperException e) {
      logger.error("fail to create znode that is at slot "+slot+",throw KeeperException!",e);
    } catch (InterruptedException e) {
      logger.error("fail to create znode that is at slot "+slot+",throw InterruptedException!",e);
    } catch (IOException e) {
      logger.error("fail to create znode that is at slot "+slot+",throw IOException!",e);
    }
  }

  public void addWatcher(long delayTime) throws KeeperException, InterruptedException, IOException {
    haCluster.addWatchers(delayTime);
  }
}
