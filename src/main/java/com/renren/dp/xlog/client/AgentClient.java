package com.renren.dp.xlog.client;

import java.io.IOException;

import xlog.slice.AgentPrx;
import xlog.slice.AgentPrxHelper;
import xlog.slice.LogData;

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
