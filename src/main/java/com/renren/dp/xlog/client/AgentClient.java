package com.renren.dp.xlog.client;

import java.io.IOException;

import xlog.DispatcherPrx;
import xlog.DispatcherPrxHelper;
import xlog.LogData;

import com.renren.dp.xlog.common.ZkConn;
import com.renren.dp.xlog.config.DispatcherConfig;

public class AgentClient {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ZkConn conn = new ZkConn();
        DispatcherConfig cfg = DispatcherConfig.create(conn);
        String[] dispatchers = cfg.listDispatcher();
        for (String dispatcher : dispatchers) {
            Ice.Communicator ic = Ice.Util.initialize();
            DispatcherPrx prx = DispatcherPrxHelper.uncheckedCast(ic.stringToProxy(cfg
                    .getDispatcher(dispatcher)));
            prx.add(new LogData[] { new LogData(), new LogData() });
        }
    }

}
