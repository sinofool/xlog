package com.renren.dp.xlog.dispatcher;

import dp.zk.ZkConn;

public class DispatcherApp extends Ice.Application {

    @Override
    public int run(String[] args) {
        shutdownOnInterrupt();

        Ice.ObjectAdapter adapter = communicator().createObjectAdapterWithEndpoints(
                "XlogDispatcher", "default");
        ZkConn conn = new ZkConn();
        DispatcherI obj = new DispatcherI();
        obj.initialize(adapter, conn);
        adapter.activate();
        communicator().waitForShutdown();
        return 0;
    }

    public static void main(String[] args) {
        DispatcherApp app = new DispatcherApp();
        System.exit(app.main("Dispatcher", args));
    }

}
