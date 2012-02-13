package com.renren.dp.xlog.client;

import java.io.IOException;

import xlog.DispatcherPrx;
import xlog.DispatcherPrxHelper;
import xlog.LogData;

import com.renren.dp.xlog.common.ZkConn;
import com.renren.dp.xlog.config.DispatcherConfig;

public class DispatcherClient {
    private static final String[] CATEGORIES = new String[] { "xlog", "example", "level3", "file" };

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ZkConn conn = new ZkConn();
        Ice.Communicator ic = Ice.Util.initialize();
        DispatcherConfig<DispatcherPrx> cfg = DispatcherConfig.create(conn, ic);
        DispatcherPrx prx = cfg.getDispatcher(CATEGORIES);
        prx.add(new LogData[] { new LogData(), new LogData() });

    }

}
