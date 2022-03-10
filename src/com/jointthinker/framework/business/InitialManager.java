package com.jointthinker.framework.business;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.jointthinker.framework.bean.ItemBean;
import com.jointthinker.framework.common.exceptions.PersistentException;
import com.jointthinker.module.base.bean.SystemConfig;
import com.jointthinker.module.dict.bean.SelectItemTextBean;


public class InitialManager {

	private static SystemConfig config = new SystemConfig();
	private static Map itemMap = new HashMap();//字典表数据  type--list
	
	private static ExecutorService pool = null;//线程池
	
	
	public static ExecutorService getPool() {
		return pool;
	}
	

	
	public static Map getItemMap() {
		return itemMap;
	}



	public static void setItemMap(Map itemMap) {
		InitialManager.itemMap = itemMap;
	}



	public static void initPool() {
		pool = Executors.newCachedThreadPool();
	}
	
	

	@SuppressWarnings("unchecked")
	private static SystemConfig getSystemConfig(InputStream stream) {
		
		SystemConfig config = new SystemConfig();
		try{
	       DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance(); 
		   DocumentBuilder builder=factory.newDocumentBuilder(); 
		   Document doc = builder.parse(stream);
		   NodeList nl = doc.getElementsByTagName("config");
    	}	
    	catch(Exception e){
    		return null;
    	}  
	    return config;
	}
	
	
	public static void init() {

		InputStream stream = null;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader!=null) {
			stream = classLoader.getResourceAsStream( "config.xml" );
		}
		if(stream==null){
			//System.out.println("-------------文件不能正常读入---------------");
		}else {
			System.out.println("文件可以正常读入");
			config = getSystemConfig(stream);
		}
	}
	
	public static void init2() throws PersistentException{
		BusinessManager buss = new BusinessManager();
		//数据字典
		String sql = "select id,name,type,itemcode,shortname,fullcode from selectitem where isview=1 order by type,sequence ";
		List list = buss.jdbcQuery(sql, SelectItemTextBean.class);
		List resultList = new ArrayList();
		for(int i=0; i<list.size(); i++) {
			SelectItemTextBean item = (SelectItemTextBean)list.get(i);
			String type = item.getType();
			String shortname = item.getShortname();
			String itemcode = item.getItemcode();
			list.remove(item);
			i--;
			ItemBean itempart = new ItemBean();
			itempart.setShortname(shortname);
			itempart.setType(type);
			itempart.setItemcode(itemcode);
			itempart.getItemList().add(item);
			for(int j=0; j<list.size();) {
				SelectItemTextBean new_item = (SelectItemTextBean)list.get(j);
				if(new_item.getType().equals(item.getType())) {
					itempart.getItemList().add(new_item);
					list.remove(new_item);
				}else {
					j++;
				}
			}
			resultList.add(itempart);	
		}
	}
	
}
