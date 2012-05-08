package com.renren.dp.xlog.config;

import java.io.IOException;
import java.util.Properties;

public class Configuration {
	private static Properties props =null;
	
	static {
		props = new Properties();
        try {
			props.load(Configuration.class.getResourceAsStream("/conf/xlog.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getString(String paramName){
		return props.getProperty(paramName);
	}
	
	public static void setString(String key,String value){
	  props.setProperty(key, value);
	}
	public static String getString(String paramName,String defaultValue){
		String strValue=props.getProperty(paramName);
		if(strValue==null||"".equals(strValue)){
			return defaultValue;
		}
		
		return strValue;
	}
	
	public static int getInt(String paramName,int defaultValue){
		String strValue=props.getProperty(paramName);
		if(strValue==null||"".equals(strValue)){
			return defaultValue;
		}
		return Integer.parseInt(strValue);
	}
	
	public static long getLong(String paramName,long defaultValue){
		String strValue=props.getProperty(paramName);
		if(strValue==null||"".equals(strValue)){
			return defaultValue;
		}
		return Long.parseLong(strValue);
	}
}
