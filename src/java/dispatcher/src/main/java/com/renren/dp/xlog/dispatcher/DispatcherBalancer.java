package com.renren.dp.xlog.dispatcher;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

import dp.election.HABalancer;
import dp.zk.ZkConn;

public class DispatcherBalancer {

    public static void main(String[] args) throws KeeperException, InterruptedException,
            IOException {
        ZkConn conn = new ZkConn();
        HABalancer b = new HABalancer(conn, "/dispatchers", 19);
        b.initialize();
        Thread.sleep(120 * 1000);
    }

}
