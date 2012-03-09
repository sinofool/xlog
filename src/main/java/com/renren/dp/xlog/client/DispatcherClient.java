package com.renren.dp.xlog.client;

import java.io.IOException;

import xlog.slice.DispatcherPrx;
import xlog.slice.LogData;

import com.renren.dp.xlog.config.DispatcherCluster;

import dp.zk.ZkConn;

public class DispatcherClient {
    private static final String[] CATEGORIES = new String[] { "xlog", "example", "level3", "file" };

    /**
     * @param args
     * @throws IOException
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        ZkConn conn = new ZkConn();
        Ice.Communicator ic = Ice.Util.initialize();
        DispatcherCluster<DispatcherPrx> cfg = DispatcherCluster.create(conn, ic);
        DispatcherPrx prx = cfg.getDispatcher(CATEGORIES);
        prx.add(new LogData[] { new LogData(), new LogData() });

    }

}
