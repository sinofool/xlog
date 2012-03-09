package com.renren.dp.xlog.config;

import xlog.slice.DispatcherPrx;
import xlog.slice.DispatcherPrxHelper;
import dp.election.GenericProxyBuilder;

public class DispatcherProxyFactory implements GenericProxyBuilder<DispatcherPrx> {
    private final Ice.Communicator ic;

    public DispatcherProxyFactory(Ice.Communicator ic) {
        this.ic = ic;
    }

    @Override
    public DispatcherPrx cast(String config) {
        return DispatcherPrxHelper.uncheckedCast(ic.stringToProxy(config));
    }
}
