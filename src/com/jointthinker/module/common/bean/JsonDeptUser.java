package com.jointthinker.module.common.bean;

import java.util.List;

public class JsonDeptUser {
	
	 private Long id;
	 private String text;
	 private Integer sequence;
	 private boolean leaf;
	 private Long parentid;
	 private Integer grade;
	 private List<JsonDeptUser> children;
	 
	 public JsonDeptUser() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public List<JsonDeptUser> getChildren() {
		return children;
	}

	public void setChildren(List<JsonDeptUser> children) {
		this.children = children;
	}
}
