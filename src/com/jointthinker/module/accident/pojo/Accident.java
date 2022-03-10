package com.jointthinker.module.accident.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jointthinker.framework.common.exceptions.BusinessLogicException;
import com.jointthinker.framework.persistence.PersistentObject;
@Entity
@Table(name="accident")
public class Accident extends PersistentObject{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String title;//标题
	@Column
	private String content;//内容
	@Column
	private String lat;
	@Column
	private String lng;
	@Column
	private Date createtime;
	@Column
	private String site;//地址
	@Column
	private String photopath;//图片
	@Column
	private String radius1;//严重区域半径
	@Column
	private String radius2;//中度区域半径
	@Column
	private String radius3;//轻度区域半径
	
	

	

	public String getRadius1() {
		return radius1;
	}





	public void setRadius1(String radius1) {
		this.radius1 = radius1;
	}





	public String getRadius2() {
		return radius2;
	}





	public void setRadius2(String radius2) {
		this.radius2 = radius2;
	}





	public String getRadius3() {
		return radius3;
	}





	public void setRadius3(String radius3) {
		this.radius3 = radius3;
	}





	public String getPhotopath() {
		return photopath;
	}





	public void setPhotopath(String photopath) {
		this.photopath = photopath;
	}





	public Long getId() {
		return id;
	}





	public void setId(Long id) {
		this.id = id;
	}





	public String getTitle() {
		return title;
	}





	public void setTitle(String title) {
		this.title = title;
	}





	public String getContent() {
		return content;
	}





	public void setContent(String content) {
		this.content = content;
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





	public Date getCreatetime() {
		return createtime;
	}





	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}





	public String getSite() {
		return site;
	}





	public void setSite(String site) {
		this.site = site;
	}





	@Override
	public void setData(PersistentObject vo) throws BusinessLogicException {
		// TODO Auto-generated method stub
		copyProperties(vo);
	}

}
