package com.renren.dp.xlog.logger;

import java.util.Arrays;

import com.renren.dp.xlog.cache.CacheManager;
import com.renren.dp.xlog.cache.impl.DefaultCacheManager;
import com.renren.dp.xlog.storage.StorageRepositoryFactory;
import com.renren.dp.xlog.sync.SyncMain;

import xlog.slice.LogData;
import xlog.slice._LoggerDisp;
import Ice.Current;
import Ice.ObjectAdapter;

public class LoggerI extends _LoggerDisp {
  private static final long serialVersionUID = -3117295957500314988L;

  private CacheManager cacheManager = null;

  public boolean initialize(ObjectAdapter adapter) {
    adapter.add(this, adapter.getCommunicator().stringToIdentity("L"));

    cacheManager = new DefaultCacheManager();
    cacheManager.initialize();

    SyncMain.main(null);

    return true;
  }

  @Override
  public void add(LogData[] data, Current __current) {
    cacheManager = new DefaultCacheManager();
    cacheManager.initialize();
    
    for (LogData logdata : data) {
//      System.out.println("=========================" + Arrays.toString(logdata.categories)
//          + "=========================");
      for (String log : logdata.logs) {
        System.out.println("=> " + log);
      }
    }

    boolean res = cacheManager.writeCache(data);
    if (res) {
      StorageRepositoryFactory.getInstance().addToRepository(data);
    }
  }

  @Override
  public void addLogData(LogData data, Current __current) {
    // TODO Auto-generated method stub
    boolean res = cacheManager.writeCache(data);
    if (res) {
      StorageRepositoryFactory.getInstance().addToRepository(data);
    }
  }

}
