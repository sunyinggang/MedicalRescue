package com.jointthinker.module.common.pojo;

import java.lang.reflect.Field;
import java.util.Date;

import com.jointthinker.framework.common.exceptions.BusinessLogicException;
import com.jointthinker.framework.persistence.PersistentObject;

public class NoticeEmployee extends PersistentObject{
	private Long noticeid;
	private Long readuserid; //查看人id
	private Integer isread; //是否已读
	private Date readtime; // 查看时间
	
	@Override
	public void setData(PersistentObject vo) throws BusinessLogicException {
		
		copyProperties(vo);
	}
	
	public Long getNoticeid() {
		return noticeid;
	}

	public void setNoticeid(Long noticeid) {
		this.noticeid = noticeid;
	}

	public Long getReaduserid() {
		return readuserid;
	}

	public void setReaduserid(Long readuserid) {
		this.readuserid = readuserid;
	}

	public Integer getIsread() {
		return isread;
	}

	public void setIsread(Integer isread) {
		this.isread = isread;
	}


	public Date getReadtime() {
		return readtime;
	}


	public void setReadtime(Date readtime) {
		this.readtime = readtime;
	}
	
	public static void main(String[] args) throws ClassNotFoundException {
		Class<?> cls = Class.forName("com.jointthinker.module.common.pojo.NoticeEmployee");
		//Class<?> cls = Class.forName("com.jointthinker.module.sheetresult.pojo.Attachment");
		Field[] field = cls.getDeclaredFields();
		//Annotation[] annotation = cls.getAnnotations();
		System.out.println(field.length);
		for (Field f: field) {
			System.out.print("\'" + f.getName() + "\',");
		}
		
	}
}
