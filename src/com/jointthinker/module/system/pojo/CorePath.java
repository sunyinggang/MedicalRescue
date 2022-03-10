package com.jointthinker.module.system.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jointthinker.framework.common.exceptions.BusinessLogicException;
import com.jointthinker.framework.persistence.PersistentObject;
@Entity
@Table(name = "core_path")
public class CorePath extends PersistentObject{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String urlpath;
	@Column
	private String keyword;
	@Column
	private String must_keyword;
	
	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getUrlpath() {
		return urlpath;
	}



	public void setUrlpath(String urlpath) {
		this.urlpath = urlpath;
	}



	public String getKeyword() {
		return keyword;
	}



	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	

	public String getMust_keyword() {
		return must_keyword;
	}



	public void setMust_keyword(String must_keyword) {
		this.must_keyword = must_keyword;
	}



	@Override
	public void setData(PersistentObject vo) throws BusinessLogicException {
		// TODO Auto-generated method stub
		copyProperties(vo);
	}

}
