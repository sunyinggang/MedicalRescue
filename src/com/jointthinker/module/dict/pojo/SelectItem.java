package com.jointthinker.module.dict.pojo;

import com.jointthinker.framework.common.exceptions.BusinessLogicException;
import com.jointthinker.framework.persistence.PersistentObject;



public class SelectItem extends PersistentObject {
	
	private String name;//名称
	private String type;//类型
	private Integer sequence = 1;//排序
	private Integer isview = 0;//是否显示  0--显示,1--不显示  默认为显示0
	private String parentcode;//上级类型编码 为空则为类型
	private String itemcode;//编码
	private String fullcode;//全码  类型码+编码
	private String shortname;

	public SelectItem() {}
	
	

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public Integer getSequence() {
		return sequence;
	}



	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}



	public Integer getIsview() {
		return isview;
	}



	public void setIsview(Integer isview) {
		this.isview = isview;
	}



	public String getParentcode() {
		return parentcode;
	}



	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}



	public String getItemcode() {
		return itemcode;
	}



	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}



	public String getFullcode() {
		return fullcode;
	}



	public void setFullcode(String fullcode) {
		this.fullcode = fullcode;
	}



	public String getShortname() {
		return shortname;
	}



	public void setShortname(String shortname) {
		this.shortname = shortname;
	}



	@Override
	public void setData(PersistentObject vo) throws BusinessLogicException {
		// TODO Auto-generated method stub
		copyProperties(vo);	
	}




}
