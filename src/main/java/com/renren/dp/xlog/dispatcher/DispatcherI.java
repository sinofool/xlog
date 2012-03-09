package com.renren.dp.xlog.dispatcher;

import java.io.IOException;
import java.util.UUID;

import xlog.proto.Xlog.ItemInfo;
import xlog.proto.Xlog.ItemInfo.Builder;
import xlog.slice.DispatcherPrx;
import xlog.slice.LogData;
import xlog.slice.LoggerPrx;
import xlog.slice._DispatcherDisp;
import Ice.Current;
import Ice.Endpoint;
import Ice.ObjectAdapter;
import Ice.ObjectPrx;

import com.renren.dp.xlog.config.DispatcherCluster;
import com.renren.dp.xlog.logger.LoggerI;

import dp.zk.ZkConn;

public class DispatcherI extends _DispatcherDisp {
    private static final long serialVersionUID = 5776184542612999955L;

    private String uuid;
    private ObjectPrx myprx;
    private DispatcherCluster<DispatcherPrx> cfg;
    private LoggerI logger;

    protected boolean initialize(ObjectAdapter adapter, ZkConn conn) {
        uuid = UUID.randomUUID().toString();
        myprx = adapter.add(this, adapter.getCommunicator().stringToIdentity("D"));
        try {
            logger = new LoggerI();
            logger.initialize(adapter);
            cfg = DispatcherCluster.create(conn, adapter.getCommunicator());
            cfg.addDispatcher(this);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean register(LoggerPrx subscriber, int frequence, Current __current) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void add(LogData[] data, Current __current) {
        System.out.println("DispatcherI::add called " + data.length);
        logger.add(data);
    }

    public ItemInfo toItemInfo() {
        StringBuffer endpointline = new StringBuffer("D");
        Endpoint[] es = myprx.ice_getEndpoints();
        for (Endpoint e : es) {
            endpointline.append(":").append(e._toString());
        }
        Builder ib = ItemInfo.newBuilder();
        ib.setType(1);
        ib.setLocation(endpointline.toString());
        return ib.build();
    }
}
