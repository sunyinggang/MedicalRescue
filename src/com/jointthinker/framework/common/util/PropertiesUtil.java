package com.jointthinker.framework.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 按序读取.properties文件
 * @author kw
 *
 */
public class PropertiesUtil{
	private Logger logger = Logger.getLogger(PropertiesUtil.class);
	private Map<String,String> map = null;
	
	public PropertiesUtil(){
		map = new LinkedHashMap<String,String>();
	}
	
	public void load(InputStreamReader in) throws IOException{
		logger.debug("开始读取properties文件......");
		BufferedReader br = new BufferedReader(in);
		setKeyValue(br);
		if(br!=null){
			br.close();
			br = null;
		}
		logger.debug("读取properties文件结束......");
	}
	
	public void setKeyValue(BufferedReader br) throws IOException{
		String row = null;
		String[] entry = null;
		while((row = br.readLine())!=null){
			if(row.trim().equals("")){
				continue;
			}
			if(row.startsWith("#")){
				continue;
			}
			entry = row.split("=");
			logger.debug(row);
			if(entry.length==2){
				map.put(entry[0], entry[1]);
			}else if(entry.length==1){
				if(entry[0]!=null&&!entry[0].trim().equals("")){
					map.put(entry[0], "");
				}else{
					logger.error("文件读取失败！请检查.properties文件格式。");
					throw new IOException("文件读取失败！请检查.properties文件格式。");
				}
			}else{
				logger.error("文件读取失败！请检查.properties文件格式。");
				throw new IOException("文件读取失败！请检查.properties文件格式。");
			}
		}
	}
	
	public String[] getKeys(){
		return (String[]) (map.keySet().toArray(new String[]{}));
	}
	
	public String[] getValues(){
		return (String[])(map.values().toArray(new String[]{}));
	}
	
	public String getProperty(String key){
		return map.get(key);
	}
	
	public void put(String key,String value){
		map.put(key, value);
	}
}
