package com.renren.dp.xlog.client;

import java.io.IOException;

import xlog.AgentPrx;
import xlog.AgentPrxHelper;
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

    Ice.Communicator ic = Ice.Util.initialize();
    AgentPrx prx = AgentPrxHelper.uncheckedCast(ic
        .stringToProxy("A:tcp -h 127.0.0.1 -p 10000"));
    prx.add(new LogData[] { new LogData(), new LogData() });

    ic.destroy();

  }

}
