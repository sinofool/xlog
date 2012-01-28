package com.renren.dp.xlog.logger;

import Ice.Current;
import Ice.ObjectAdapter;
import xlog.LogData;
import xlog._LoggerDisp;

public class LoggerI extends _LoggerDisp {
    private static final long serialVersionUID = -3117295957500314988L;

    protected boolean initialize(ObjectAdapter adapter) {
        adapter.add(this, adapter.getCommunicator().stringToIdentity("L"));
        // TODO register somewhere
        return true;
    }

    @Override
    public void add(LogData[] data, Current __current) {
        // TODO Auto-generated method stub

    }

}
