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

import com.renren.dp.xlog.config.Configuration;
import com.renren.dp.xlog.config.DispatcherCluster;
import com.renren.dp.xlog.logger.LoggerI;

import dp.election.impl.DefaultWatcher;
import dp.zk.ZkConn;

public class DispatcherI extends _DispatcherDisp {
	private static final long serialVersionUID = 5776184542612999955L;

	// private String uuid;
	private ObjectPrx myprx;
	private DispatcherCluster<DispatcherPrx> cfg;
	private LoggerI logger;
	private ZkConn conn=null;

	protected boolean initialize(ObjectAdapter adapter) {
		myprx = adapter.add(this,
				adapter.getCommunicator().stringToIdentity("D"));
		logger = new LoggerI();
		logger.initialize(adapter);

		long delayTime=Configuration.getLong("master.start.delay",300)*1000;
		int zkSessionTimeOut=Configuration.getInt("zk.session.timeout",2)*1000;
		conn=new ZkConn(Configuration.getString("zookeeper.connstr"),zkSessionTimeOut,new DefaultWatcher(this));
		try {
			cfg = DispatcherCluster.create(conn, adapter.getCommunicator());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return initCluster(delayTime);
	}

	public void close(){
		conn.close();
	}
	
	private boolean initCluster(long delayTime){
		cfg.addDispatcher(this);
		
		try {
			cfg.addWatcher(delayTime);
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
	
	public boolean reinitialize(){
		try {
			conn.reconnect();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return initCluster(0);
	}
	@Override
	public boolean register(LoggerPrx subscriber, int frequence,
			Current __current) {
		return false;
	}

	@Override
	public void add(LogData[] datas, Current __current) {
		logger.add(datas);
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
		cfg.createZNode(slot);
	}

	@Override
	public void addLogData(LogData data, Current __current) {
		logger.addLogData(data);
	}
}
