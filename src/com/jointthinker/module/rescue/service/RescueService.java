package com.jointthinker.module.rescue.service;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.jointthinker.framework.bean.JsonBean;
import com.jointthinker.framework.business.ManagerBase;
import com.jointthinker.framework.common.exceptions.PersistentException;
import com.jointthinker.framework.common.util.StringUtil;
@Service
public class RescueService extends ManagerBase{
	
	public JsonBean getRescueMap() throws PersistentException {
		// TODO Auto-generated method stub
		String sql ="select *,name as title from rescue";
		JsonBean json = this.getContextStrategy().getJsonBean(sql);
		if(json.getSize()>0){
			for(int i=0;i<json.getSize();i++){
				JSONObject obj = json.getJsonarray().getJSONObject(i);
				JSONArray arr = new JSONArray();
				arr.add("地址: "+obj.getString("address"));
				arr.add("电话: "+obj.getString("telephone"));
				obj.put("content", arr);
			}
		}
		return json;
	}

	public JsonBean getRescueList(Map para) throws PersistentException {
		// TODO Auto-generated method stub
		JsonBean json = null;
		String value;
		StringBuffer query = new StringBuffer("select a.* ");
		query.append(" from rescue a ");
		query.append(" where 1=1");
		Integer limit = StringUtil.getMapkeyValue2Int(para, "limit");
		Integer start = StringUtil.getMapkeyValue2Int(para, "start");
		value = StringUtil.getMapkeyValue2Str(para, "name");
		if (value!=null&&!"".equals(value)){
			query.append(" and a.name like '%"+value+"%'");
		}
		value = StringUtil.getMapkeyValue2Str(para, "telephone");
		if (value!=null&&!"".equals(value)){
			query.append(" and a.telephone like '%"+value+"%'");
		}
		value = StringUtil.getMapkeyValue2Str(para, "sort");
		if (value!=null&&!"".equals(value)){
			query.append(" order by "+value);
			value = StringUtil.getMapkeyValue2Str(para, "order");
			if (value!=null&&!"".equals(value)){
				query.append(" "+value);
			}
		}else{
			query.append(" order by id desc ");
		}
		
		if (limit!=null&&start!=null){
			json = this.getContextStrategy().getJsonBeanPaging(query.toString(), start.intValue(), limit.intValue());
		}else{
			json = this.getContextStrategy().getJsonBean(query.toString());
		}
		return json;
	}
}
