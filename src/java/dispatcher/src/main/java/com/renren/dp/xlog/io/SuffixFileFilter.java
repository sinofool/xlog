package com.renren.dp.xlog.io;

import java.io.File;
import java.io.FileFilter;

public class SuffixFileFilter implements FileFilter{

	private String[] suffixs=null;
	
	public SuffixFileFilter(String[] suffixs){
		this.suffixs=suffixs;
	}
	
	public SuffixFileFilter(){
	}
	
	@Override
	public boolean accept(File oplogFile) {
		if(oplogFile.isDirectory()){
			return true;
		}
		if(suffixs==null){
			if(!oplogFile.getName().contains(".")){
				return true;
			}
		}else{
			String fn=oplogFile.getName();
			for(String suffix:suffixs){
				if(fn.endsWith(suffix)){
					return true;
				}
			}
		}
		
		return false;
	}
}

