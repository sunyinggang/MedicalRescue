package com.jointthinker.module.medical.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jointthinker.framework.common.exceptions.BusinessLogicException;
import com.jointthinker.framework.persistence.PersistentObject;
@Entity
@Table(name="medical")
public class Medical extends PersistentObject{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String hospitalname;//名称
	@Column
	private String haddress;//地址
	@Column
	private String telephone;//电话
	@Column
	private Integer bed;//床位
	@Column
	private String advantage;//技术优势
	@Column
	private String position;//地理位置
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



	public String getHospitalname() {
		return hospitalname;
	}



	public void setHospitalname(String hospitalname) {
		this.hospitalname = hospitalname;
	}



	public String getHaddress() {
		return haddress;
	}



	public void setHaddress(String haddress) {
		this.haddress = haddress;
	}



	public String getTelephone() {
		return telephone;
	}



	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}



	public Integer getBed() {
		return bed;
	}



	public void setBed(Integer bed) {
		this.bed = bed;
	}



	public String getAdvantage() {
		return advantage;
	}



	public void setAdvantage(String advantage) {
		this.advantage = advantage;
	}



	public String getPosition() {
		return position;
	}



	public void setPosition(String position) {
		this.position = position;
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
