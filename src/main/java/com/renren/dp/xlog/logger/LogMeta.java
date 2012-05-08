package com.renren.dp.xlog.logger;

public class LogMeta {

	private String categories=null;
	private String log=null;
	private String checkSum=null;
	public String getCategories() {
		return categories;
	}
	public void setCategories(String categories) {
		this.categories = categories;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public String getCheckSum() {
		return checkSum;
	}
	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}
	
	public String toString(){
		return "["+categories+"],["+log+"],["+checkSum+"]";
	}
}
