package com.jointthinker.module.base.bean;

import java.util.HashMap;
import java.util.Map;

public class SystemConfig {
	
	@SuppressWarnings("unchecked")
	private Map database = new HashMap();
	private Map mail = new HashMap();
	private Map file = new HashMap();

	public SystemConfig(){}

	@SuppressWarnings("unchecked")
	public Map getMail() {
		return mail;
	}

	@SuppressWarnings("unchecked")
	public void setMail(Map mail) {
		this.mail.putAll(mail);
	}

	public Map getDatabase() {
		return database;
	}

	public void setDatabase(Map database) {
		this.database = database;
	}
	
	public Map getFile() {
		return file;
	}

	public void setFile(Map file) {
		this.file.putAll(file);
	}

}
