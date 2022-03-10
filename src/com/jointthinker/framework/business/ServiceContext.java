package com.jointthinker.framework.business;

import com.jointthinker.framework.persistence.IPersistentStrategy;

public class ServiceContext {
	
	protected String contextId;//线程ID
	protected IPersistentStrategy pStrategy;//持久化对象Transaction

	public String getContextId(){
		return contextId;
	}
	public void setContextId(String s){
		contextId = s;
	}
    public IPersistentStrategy getContextPersistentStrategy(){
    	return pStrategy;
    }
    public void setContextPersistentStrategy(IPersistentStrategy ips){
    	pStrategy = ips;
    }
}
