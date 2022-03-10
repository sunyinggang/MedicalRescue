package com.jointthinker.module.system.pojo;

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
@Table(name = "core_menu")
public class Menu extends PersistentObject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private Long parentid;

	@Column
	private String menuname;

	@Column
	private String entrypoint;

	@Column
	private Integer ispopwindow;

	@Column
	private String icon;

	@Column
	private Integer isview;

	@Column
	private Integer isiframe;

	@Column
	private String xtype;

	@Column
	private String menudesc;

	@Column
	private Integer grade;

	@Column
	private Long sequence;

	@Column
	private boolean leaf;

	@Column
	private Date deltime;

	@Column
	private Long deluserid;

	@Column
	private Date createtime;

	@Column
	private Long createuserid;

	@Column
	private Date lastupdatetime;

	@Column
	private Long lastupdateuserid;
	@Column
	private String accidenttype;//事故标识
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}


	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getEntrypoint() {
		return entrypoint;
	}

	public void setEntrypoint(String entrypoint) {
		this.entrypoint = entrypoint;
	}

	public Integer getIspopwindow() {
		return ispopwindow;
	}

	public void setIspopwindow(Integer ispopwindow) {
		this.ispopwindow = ispopwindow;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getIsview() {
		return isview;
	}

	public void setIsview(Integer isview) {
		this.isview = isview;
	}

	public Integer getIsiframe() {
		return isiframe;
	}

	public void setIsiframe(Integer isiframe) {
		this.isiframe = isiframe;
	}

	public String getXtype() {
		return xtype;
	}

	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

	public String getMenudesc() {
		return menudesc;
	}

	public void setMenudesc(String menudesc) {
		this.menudesc = menudesc;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public Date getDeltime() {
		return deltime;
	}

	public void setDeltime(Date deltime) {
		this.deltime = deltime;
	}

	public Long getDeluserid() {
		return deluserid;
	}

	public void setDeluserid(Long deluserid) {
		this.deluserid = deluserid;
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

	public Date getLastupdatetime() {
		return lastupdatetime;
	}

	public void setLastupdatetime(Date lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}

	public Long getLastupdateuserid() {
		return lastupdateuserid;
	}

	public void setLastupdateuserid(Long lastupdateuserid) {
		this.lastupdateuserid = lastupdateuserid;
	}
	
	

	public String getAccidenttype() {
		return accidenttype;
	}

	public void setAccidenttype(String accidenttype) {
		this.accidenttype = accidenttype;
	}

	@Override
	public void setData(PersistentObject vo) throws BusinessLogicException {
		copyProperties(vo);
	}

}
