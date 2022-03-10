package com.jointthinker.framework.persistence;

public abstract class UnDeletetableObject extends PersistentObject{

	private Long isdeleted;
	
	public Long getIsdeleted(){
		return isdeleted;
	}
	public void setIsdeleted(Long i){
		isdeleted = i;
	}
}
