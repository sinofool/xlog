package com.renren.dp.xlog.logger;

import java.util.Arrays;

import xlog.slice.LogData;
import xlog.slice._LoggerDisp;
import Ice.Current;
import Ice.ObjectAdapter;

public class LoggerI extends _LoggerDisp {
    private static final long serialVersionUID = -3117295957500314988L;

    public boolean initialize(ObjectAdapter adapter) {
        adapter.add(this, adapter.getCommunicator().stringToIdentity("L"));
        // TODO register somewhere
        return true;
    }

    @Override
    public void add(LogData[] data, Current __current) {
        for (LogData logdata : data) {
            System.out.println("=========================" + Arrays.toString(logdata.categories)
                    + "=========================");
            for (String log : logdata.logs) {
                System.out.println("=> " + log);
            }
        }
    }

}
