package com.jointthinker.framework.business;

import java.util.Enumeration;
import java.util.Hashtable;

import net.sf.json.JSONArray;

public class ServiceContextManager { 
	private static Hashtable contexts = new Hashtable();
	
	public static ServiceContext createServiceContext(){
		return new ServiceContext();
	}
	
	public static ServiceContext getServiceContext(){
		return (ServiceContext)contexts.get(Thread.currentThread());
	}
	
	public static void storeServiceContext(ServiceContext sc){
		if(sc != null){
			contexts.put(Thread.currentThread(), sc);
		}else{
			contexts.remove(Thread.currentThread());
		}
	}
}
