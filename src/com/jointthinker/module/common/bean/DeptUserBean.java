package com.jointthinker.module.common.bean;

public class DeptUserBean {

	private Long id;
	private String name;
	private Long parentid;
	
	public DeptUserBean() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}
	
}
