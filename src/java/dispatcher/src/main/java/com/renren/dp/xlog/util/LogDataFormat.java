package com.renren.dp.xlog.util;

import xlog.slice.LogData;

//import java.util.ArrayList;
//import java.util.List;
//
//import com.renren.dp.xlog.logger.LogMeta;
//
//import xlog.slice.LogData;

public class LogDataFormat {

//	public static List<LogMeta> transformLogDataToMeta(LogData[] datas){
//		List<LogMeta> list=new ArrayList<LogMeta>();
//		for(LogData data:datas){
//			list.addAll(transformLogDataToMeta(data));
//		}
//		
//		return list;
//	}
	
//	public static List<LogMeta> transformLogDataToMeta(LogData logData){
//		List<LogMeta> list=new ArrayList<LogMeta>();
//		String category=transformCatefories(logData.categories);
//		for(String log:logData.logs){
//			LogMeta logMeta=new LogMeta();
//			logMeta.setCategories(category);
//			logMeta.setCheckSum(logData.checkSum);
//			logMeta.setLog(log);
//			
//			list.add(logMeta);
//		}
//		
//		return list;
//	}
	
	public static String transformCatefories(String[] categories){
		if(categories.length==1){
			return categories[0];
		}
		StringBuilder sb=new StringBuilder();
		for(String category:categories){
			sb.append("/");
			sb.append(category);
		}
		String str=sb.toString();
		sb.delete(0,sb.length());
		sb=null;
		return str;
	}
	
	public static LogData transformToLogData(String categories,String[] logs){
		LogData ld=new LogData();
		
		ld.categories=new String[]{categories};
		ld.logs = logs;
		
		return ld;
	}
	
//	public static LogData transformStringToLogData(String strLog){
//		LogData ld=new LogData();
//		int[] poses=indexOf(strLog);
//		
//		ld.categories=new String[]{strLog.substring(1,poses[0])};
//		ld.logs=new String[]{strLog.substring(poses[0]+3,poses[1])};
//		ld.checkSum=strLog.substring(poses[1]+3,strLog.length()-1);
//		
//		return ld;
//	}
//	
//	private static int[] indexOf(String line){
//		int len=line.length()-2;
//		char[] cArr=new char[len];
//		int[] poses=new int[2];
//		cArr=line.toCharArray();
//		int pos=0;
//		for(int i=0;i<len;i++){
//			if(cArr[i]==']' && cArr[i+1]==',' && cArr[i+2]=='['){
//				poses[pos++]=i;
//			}
//		}
//	    
//	    return poses;
//	}
	
//	public static void main(String[] args){
//		String line="[/www/renren/com],[This is a test logger],[112312312312312]";
//		LogData logData=transformStringToLogData(line);
//		System.out.println(logData.categories);
//		System.out.println(logData.logs);
//		System.out.println(logData.checkSum);
//	}
}
