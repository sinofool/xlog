package com.renren.dp.xlog.logger;

public class LoggerApp extends Ice.Application {

    @Override
    public int run(String[] args) {
        shutdownOnInterrupt();

        Ice.ObjectAdapter adapter = communicator().createObjectAdapterWithEndpoints(
                "XlogLogger", "default -p 10002");
        LoggerI obj = new LoggerI();
        obj.initialize(adapter);
        adapter.activate();
        communicator().waitForShutdown();
        return 0;
    }

    public static void main(String[] args) {
        LoggerApp app = new LoggerApp();
        System.exit(app.main("Logger", args));
    }

}
