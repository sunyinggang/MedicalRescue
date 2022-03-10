package com.jointthinker.framework.business;

import java.util.List;

import com.jointthinker.framework.common.exceptions.PersistentException;
import com.jointthinker.framework.persistence.IPersistentStrategy;
import com.jointthinker.framework.persistence.PersistentObject;
import com.jointthinker.framework.persistence.UnDeletetableObject;

public class ManagerBase {

	public ManagerBase(){
		
	}
	public IPersistentStrategy getContextStrategy(){
		return ServiceContextManager.getServiceContext().getContextPersistentStrategy();
	}
	
	
	public void flushOperation() throws PersistentException{
		getContextStrategy().flush();
	}
	
	public void storePO(PersistentObject po)throws PersistentException{
		this.getContextStrategy().store(po);
	}
	
	public PersistentObject storePORefresh(PersistentObject po, Class clazz)throws PersistentException{
		return this.getContextStrategy().storeRefresh(po, clazz);
	}
	
	public void removePO(PersistentObject po)throws PersistentException{
		if(po instanceof UnDeletetableObject){
			((UnDeletetableObject)po).setIsdeleted(new Long(1));
		}else{
			this.getContextStrategy().remove(po);
		}
	}
	public void removePO(Long id, Class clazz)throws PersistentException{
		this.getContextStrategy().remove(getContextStrategy().load(id, clazz));
	}
	public PersistentObject findByPk(Long id, Class clazz)throws PersistentException{
		return this.getContextStrategy().load(id, clazz);
	}
	public PersistentObject getByPk(Long id, Class clazz)throws PersistentException{
		return this.getContextStrategy().get(id, clazz);
	}
	
	public List findByProperty(Class clazz,String wherestr,String propertyName,Object value){
		return this.getContextStrategy().findByProperty(clazz,wherestr,propertyName,value);
	}
	
}
