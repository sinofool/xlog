package com.renren.dp.xlog.dispatcher;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.thread.BoundedThreadPool;

public class WebServer {

	private String host=null;
	private int port;
	private int maxThreadCount;
	private int minThreadCount;
	
	public WebServer(String host,int port,int maxThreadCount,int minThreadCount){
		this.host=host;
		this.port=port;
		this.maxThreadCount=maxThreadCount;
		this.minThreadCount=minThreadCount;
	}
	
	public void start(){
		String applicationHome = System.getProperty("user.dir");
		org.mortbay.jetty.Server server = new org.mortbay.jetty.Server(); // 新建一个jetty服务器
		Connector conn = new SelectChannelConnector(); // 新建Connector组件
		conn.setHost(host);
		conn.setPort(port);
		server.setConnectors(new Connector[] { conn }); // 插入服务器主件中

		WebAppContext context = new WebAppContext();// 新建Handler组件
		context.setContextPath("/"); // Web应用的上下文根路径
		context.setWar(applicationHome + "/webapp");// web应用所在的路径或者是war包路径
		server.setHandler(context);// 插入服务器主件中

		BoundedThreadPool pool = new BoundedThreadPool(); // 创建线程池组件
		pool.setMaxThreads(maxThreadCount); // 设置最大线程数
		pool.setMinThreads(minThreadCount); // 设置最小线程数
		server.setThreadPool(pool); // 插入服务器主件中

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
