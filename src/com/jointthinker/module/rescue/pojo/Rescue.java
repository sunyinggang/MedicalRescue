package com.jointthinker.module.rescue.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jointthinker.framework.common.exceptions.BusinessLogicException;
import com.jointthinker.framework.persistence.PersistentObject;
@Entity
@Table(name="rescue")
public class Rescue extends PersistentObject{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String name;//名称
	@Column
	private String address;//地址
	@Column
	private String telephone;//电话
	@Column
	private String lat;
	@Column
	private String lng;
	
	

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



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getTelephone() {
		return telephone;
	}



	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}



	public String getLat() {
		return lat;
	}



	public void setLat(String lat) {
		this.lat = lat;
	}



	public String getLng() {
		return lng;
	}



	public void setLng(String lng) {
		this.lng = lng;
	}



	@Override
	public void setData(PersistentObject vo) throws BusinessLogicException {
		// TODO Auto-generated method stub
		copyProperties(vo);
	}

}
