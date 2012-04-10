package com.renren.dp.xlog.dispatcher;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

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

    //private String uuid;
    private ObjectPrx myprx;
    private DispatcherCluster<DispatcherPrx> cfg;
    private LoggerI logger;

    protected boolean initialize(ObjectAdapter adapter, ZkConn conn) {
        //uuid = UUID.randomUUID().toString();
        myprx = adapter.add(this, adapter.getCommunicator().stringToIdentity("D"));
        logger = new LoggerI();
        logger.initialize(adapter);
        
        try {
            cfg = DispatcherCluster.create(conn, adapter.getCommunicator());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        cfg.addDispatcher(this);
        try {
          cfg.addWatcher();
        } catch (KeeperException e) {
          e.printStackTrace();
          return false;
        } catch (InterruptedException e) {
          e.printStackTrace();
          return false;
        } catch (IOException e) {
          e.printStackTrace();
          return false;
        }
        return true;
    }

    @Override
    public boolean register(LoggerPrx subscriber, int frequence, Current __current) {
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

    @Override
    public void createZNode(int slot, Current __current) {
      // TODO Auto-generated method stub
      cfg.createZNode(slot);
    }

    @Override
    public void addLogData(LogData data, Current __current) {
      // TODO Auto-generated method stub
      logger.addLogData(data);
    }
}
