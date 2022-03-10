package com.jointthinker.framework.bean;

import net.sf.json.JSONArray;

public class JsonBean {

	private int size;
	private JSONArray jsonarray;
	
	public JsonBean() {}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public JSONArray getJsonarray() {
		return jsonarray;
	}

	public void setJsonarray(JSONArray jsonarray) {
		this.jsonarray = jsonarray;
	}
	
}
