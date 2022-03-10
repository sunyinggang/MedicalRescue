package com.jointthinker.module.common.pojo;

import java.util.Date;

import com.jointthinker.framework.common.exceptions.BusinessLogicException;
import com.jointthinker.framework.persistence.PersistentObject;

public class Notice extends PersistentObject {
	
	private Long fromsysid;
	
	private String fromsys;//来自系统名称

	private String noticetitle;//通知标题

	private String noticedesc;//通知内容

	private Date createtime;//通知时间

	private Long createuserid;//通知人id
	
	private String createusername;//通知人


	private Integer isdeleted;//是否删除

	private Date deletetime;//删除时间
	
	private Integer status;

	private Date expiredate; // 截止时间
	
	private String url;//链接
	
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getFromsysid() {
		return fromsysid;
	}

	public void setFromsysid(Long fromsysid) {
		this.fromsysid = fromsysid;
	}

	public String getFromsys() {
		return fromsys;
	}

	public void setFromsys(String fromsys) {
		this.fromsys = fromsys;
	}

	public String getNoticetitle() {
		return noticetitle;
	}

	public void setNoticetitle(String noticetitle) {
		this.noticetitle = noticetitle;
	}

	public String getNoticedesc() {
		return noticedesc;
	}

	public void setNoticedesc(String noticedesc) {
		this.noticedesc = noticedesc;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Long getCreateuserid() {
		return createuserid;
	}

	public void setCreateuserid(Long createuserid) {
		this.createuserid = createuserid;
	}

	public String getCreateusername() {
		return createusername;
	}

	public void setCreateusername(String createusername) {
		this.createusername = createusername;
	}

	

	public Integer getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

	public Date getDeletetime() {
		return deletetime;
	}

	public void setDeletetime(Date deletetime) {
		this.deletetime = deletetime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getExpiredate() {
		return expiredate;
	}

	public void setExpiredate(Date expiredate) {
		this.expiredate = expiredate;
	}

	@Override
	public void setData(PersistentObject vo) throws BusinessLogicException {
		// TODO Auto-generated method stub
		copyProperties(vo);
	}

	
}
