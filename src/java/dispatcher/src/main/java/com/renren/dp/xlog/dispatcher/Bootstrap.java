package com.renren.dp.xlog.dispatcher;

import com.renren.dp.xlog.config.Configuration;

public class Bootstrap {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port=Configuration.getInt("web.server.port",10000);
		String ip=Configuration.getString("web.server.host");
		if(args==null||args.length==0){
			System.err.println("please type start parameter!");
			return ;
		}else if(args[0].equals("webserver")){
			new WebServer(ip,port,10,5).start();
		}else if(args[0].equals("dispatcher")){
			new SystemManager().start();
		}else if(args[0].equals("all")){
			new SystemManager().start();
			new WebServer(ip,port,10,5).start();
		}
	}

}
