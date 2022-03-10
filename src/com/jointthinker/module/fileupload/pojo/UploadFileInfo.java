package com.jointthinker.module.fileupload.pojo;

import com.jointthinker.framework.common.exceptions.BusinessLogicException;
import com.jointthinker.framework.persistence.PersistentObject;



public class UploadFileInfo extends PersistentObject {

	private String uploadtime;
	private String category;
	private Long categoryid;

	private String username;
	private Long userid;

	private String srcfilename;
	private String newfilename;
	private String filepath;

	public String getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(String uploadtime) {
		this.uploadtime = uploadtime;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getSrcfilename() {
		return srcfilename;
	}

	public void setSrcfilename(String srcfilename) {
		this.srcfilename = srcfilename;
	}

	public String getNewfilename() {
		return newfilename;
	}

	public void setNewfilename(String newfilename) {
		this.newfilename = newfilename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	@Override
	public void setData(PersistentObject vo) throws BusinessLogicException {
		copyProperties(vo);
	}

}
