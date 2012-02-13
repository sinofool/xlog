package com.renren.dp.xlog.config;

import xlog.DispatcherPrx;
import xlog.DispatcherPrxHelper;

public class DispatcherProxyFactory implements ProxyFactory<DispatcherPrx> {
    private final Ice.Communicator ic;

    public DispatcherProxyFactory(Ice.Communicator ic) {
        this.ic = ic;
    }

    @Override
    public DispatcherPrx get(String config) {
        return DispatcherPrxHelper.uncheckedCast(ic.stringToProxy(config));
    }
}
