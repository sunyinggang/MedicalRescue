package com.jointthinker.framework.bean;

import java.util.ArrayList;
import java.util.List;

public class ItemBean {
	private String type;
	private String shortname;
	private String itemcode;
	private List itemList = new ArrayList();
	
	public ItemBean() {}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List getItemList() {
		return itemList;
	}

	public void setItemList(List itemList) {
		this.itemList = itemList;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getItemcode() {
		return itemcode;
	}

	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}
	

}
