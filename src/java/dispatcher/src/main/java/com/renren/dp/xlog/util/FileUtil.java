package com.renren.dp.xlog.util;

import java.io.File;

public class FileUtil {

	//unit KB
	public static long computeDirectorySize(File dir){
		long size=0;
		if(dir.isDirectory()){
			File[] files=dir.listFiles();
			for(File file:files){
				if(file.isDirectory()){
					size=size+computeDirectorySize(file);
				}else{
					size=size+file.length()/1024;
				}
			}
		}
		return size;
	}
}
