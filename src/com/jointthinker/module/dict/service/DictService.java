package com.jointthinker.module.dict.service;


import org.springframework.stereotype.Service;

import com.jointthinker.framework.bean.JsonBean;
import com.jointthinker.framework.business.ManagerBase;
import com.jointthinker.framework.common.exceptions.PersistentException;
@Service
public class DictService extends ManagerBase{
	public JsonBean getCorecodeList(String parentid) throws PersistentException{
		String query;
		if (parentid==null||"".equals(parentid)){
			query = "select a.*,'closed' as state from core_code a where grade=1 order by sequence";
		}else{
			query = "select a.*,case isleaf when 1 then 'open' else 'closed' end as state from core_code a where parentid="+parentid+" order by sequence";
		}
		JsonBean vb = getContextStrategy().getJsonBean(query);
		return vb;
	}
	public JsonBean getCorecodeById(String corecodeid) throws PersistentException{
		//String query = "select a.*,f_get_codenamesall(a.fullcode) as parentname,b.fullcode as parentfullcode,b.fullpath as parentfullpath from core_code a left join core_code b on a.parentid=b.id where a.id="+corecodeid;
		
		String query = "select a.* from core_code a where a.id=" + corecodeid;
		JsonBean vb = getContextStrategy().getJsonBean(query);
		return vb;
	}
	
	
	public JsonBean getSelectItemById(String id) throws PersistentException {
		String sql = "select * from SelectItem a where a.id=" + id;
		return getContextStrategy().getJsonBean(sql);
	}
	
	public JsonBean listType() throws PersistentException {
		String query = "select distinct type,shortname from selectitem t";
		JsonBean vb = this.getContextStrategy().getJsonBean(query);
		return vb;
	}
	public JsonBean listType(String sqlwhere) throws PersistentException {
//		String query = "select distinct type,shortname from selectitem t" + sqlwhere;
		String query = "select type from selectitem t " + sqlwhere+ " group by type";
		JsonBean vb = this.getContextStrategy().getJsonBean(query);
		return vb;
	}
	
	
	public JsonBean listSelectItem(String type,String itemcode) throws PersistentException {
		String query = "select * from selectitem where isview=1 and type='"+type+"'";
		if(itemcode!=null&&!"".equals(itemcode)){
			query +=" and itemcode='"+itemcode+"'";
		}
		query +=" order by sequence";
		JsonBean vb = this.getContextStrategy().getJsonBean(query);
		return vb;
	}
	
	
}
