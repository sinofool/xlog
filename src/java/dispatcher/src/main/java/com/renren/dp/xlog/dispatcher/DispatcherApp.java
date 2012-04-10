package com.renren.dp.xlog.dispatcher;

import org.apache.log4j.PropertyConfigurator;

import com.renren.dp.xlog.config.Configuration;

import dp.zk.ZkConn;

public class DispatcherApp extends Ice.Application {

  @Override
  public int run(String[] args) {
    initLog4j();
    initParams(args);
    
    shutdownOnInterrupt();

    Ice.ObjectAdapter adapter = communicator().createObjectAdapterWithEndpoints("XlogDispatcher", "default");
    ZkConn conn = new ZkConn();
    DispatcherI obj = new DispatcherI();
    obj.initialize(adapter, conn);
    adapter.activate();
    communicator().waitForShutdown();
    return 0;
  }

  public static void main(String[] args) {
    DispatcherApp app = new DispatcherApp();
    System.exit(app.main("Dispatcher", args));
  }

  private void initLog4j() {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    PropertyConfigurator.configure(classLoader.getResource("conf/log4j.properties"));
  }
  
  private void initParams(String[] args){
    //处理参数，输入的参数以K=V形式输入
    String[] param=null;
    for(String arg:args){
      param=arg.split("=");
      Configuration.setString(param[0],param[1]);
    }
  }
}
