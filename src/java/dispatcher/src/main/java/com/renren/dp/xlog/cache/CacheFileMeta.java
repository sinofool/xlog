package com.renren.dp.xlog.cache;

import java.io.File;

public class CacheFileMeta {

	private String categories=null;
	private File cacheFile=null;
	
	public CacheFileMeta(String categories, File cacheFile) {
		super();
		this.categories = categories;
		this.cacheFile = cacheFile;
	}
	
	public String getCategories() {
		return categories;
	}
	public void setCategories(String categories) {
		this.categories = categories;
	}
	public File getCacheFile() {
		return cacheFile;
	}
	public void setCacheFile(File cacheFile) {
		this.cacheFile = cacheFile;
	}
}
