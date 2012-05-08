package com.renren.dp.xlog.dispatcher;

import java.util.Date;

import org.apache.log4j.PropertyConfigurator;

import com.renren.dp.xlog.config.Configuration;
import com.renren.dp.xlog.storage.StorageRepositoryFactory;

public class DispatcherApp {

	private Ice.Communicator ic = null;
	private DispatcherI dispatcher = null;
	private int status = 0;
	private Date startDate;

	private static DispatcherApp da = null;

	private DispatcherApp() {
	}

	public static DispatcherApp getInstance() {
		if (da == null) {
			da = new DispatcherApp();
		}
		return da;
	}

	public int getStatus() {
		return status;
	}

	public Date getStartDate() {
		return startDate;
	}
	
	public void start() {
		if(status==1){
			return ;
		}
		startDate = new Date();
		status = 1;
		initLog4j();
		Ice.Properties prop = Ice.Util.createProperties();
		prop.setProperty("Ice.MessageSizeMax",
				Configuration.getString("ice.message.size.max"));
		Ice.InitializationData initData = new Ice.InitializationData();
		initData.properties = prop;
		ic = Ice.Util.initialize(initData);

		Ice.ObjectAdapter adapter = ic.createObjectAdapterWithEndpoints(
				"XlogDispatcher", "default");
		dispatcher = new DispatcherI();
		dispatcher.initialize(adapter);
		adapter.activate();
	}

	public void stop() {
		status = 0;
		dispatcher.close();
		StorageRepositoryFactory.getInstance().close();
		ic.shutdown();
		System.exit(0);
	}

	private void initLog4j() {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		PropertyConfigurator.configure(classLoader
				.getResource("conf/log4j.properties"));
	}
}
