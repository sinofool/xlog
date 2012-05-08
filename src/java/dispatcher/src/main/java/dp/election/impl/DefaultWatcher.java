package dp.election.impl;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.renren.dp.xlog.dispatcher.DispatcherI;

public class DefaultWatcher implements Watcher{

  private final static Logger logger = LoggerFactory.getLogger(DefaultWatcher.class);
  
  private DispatcherI dispatcher=null;
  
  public DefaultWatcher(DispatcherI dispatcher){
	  this.dispatcher=dispatcher;
  }
  
  @Override
  public void process(WatchedEvent event) {
    logger.info("watcher event had happen,the event state is : "+event.getState());
    if(event.getState() == KeeperState.Expired) {
    	boolean res=dispatcher.reinitialize();
    	if(res){
    		logger.info("success to reinitialize dispatcher  to zookeeper!");
    	}else{
    		logger.error("fail to reinitialize dispatcher cluster to zookeeper!");
    	}
    }
  }
}
