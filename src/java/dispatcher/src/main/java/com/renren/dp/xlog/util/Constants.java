package com.renren.dp.xlog.util;

public interface Constants {

  public static final String FILE_NAME_FORMAT_MIN="yyyy-MM-dd-HH-mm";
  
  //public static final String FILE_NAME_FORMAT_HOUR="yyyy-MM-dd-HH";
  //1024*1024
  public static int HDFS_BATCH_COMMIT_SIZE=1048576;
  //1024*1024
  public static int LOCAL_CACHE_FILE_BATCH_COMMIT_SIZE=1048576;
  
  public static int CACHE_MANAGER_QUEUE_SIZE=10000;
  
  public static final String LOG_WRITE_FINISHED_SUFFIX=".done";
  
  public static final String LOG_WRITE_ERROR_SUFFIX=".err";
  
  public static final String LOG_SYNC_FINISHED_SUFFIX=".del";
  
  public static final String VERSION="1.0";
}
