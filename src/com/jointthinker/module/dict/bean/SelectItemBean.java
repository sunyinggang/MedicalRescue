package com.jointthinker.module.dict.bean;

import java.util.Date;
import java.util.List;

public class SelectItemBean {

	private Long id;
	private String text;
	private String type;
	private String code;
	private String fullcode;
	private String path;
	private Integer grade;
	private Integer parentid;
	private boolean leaf;
	private Integer sequence;
	private Integer addpersonid;
	private Date addtime;
	//private boolean checked = false;
	//private boolean maxdepth = false;
	private List<SelectItemBean> children;
	
	private String bindpage;
	private String handleaction;
	private Integer send;
	

	/*public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}*/


	public String getHandleaction() {
		return handleaction;
	}

	public Integer getSend() {
		return send;
	}

	public void setSend(Integer send) {
		this.send = send;
	}

	public void setHandleaction(String handleaction) {
		this.handleaction = handleaction;
	}

	public String getBindpage() {
		return bindpage;
	}

	public void setBindpage(String bindpage) {
		this.bindpage = bindpage;
	}

	/*public boolean isMaxdepth() {
		return maxdepth;
	}

	public void setMaxdepth(boolean maxdepth) {
		this.maxdepth = maxdepth;
	}*/

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFullcode() {
		return fullcode;
	}

	public void setFullcode(String fullcode) {
		this.fullcode = fullcode;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getAddpersonid() {
		return addpersonid;
	}

	public void setAddpersonid(Integer addpersonid) {
		this.addpersonid = addpersonid;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public List<SelectItemBean> getChildren() {
		return children;
	}

	public void setChildren(List<SelectItemBean> children) {
		this.children = children;
	}

}
