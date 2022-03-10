package com.jointthinker.module.medical.service;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.jointthinker.framework.bean.JsonBean;
import com.jointthinker.framework.business.ManagerBase;
import com.jointthinker.framework.common.exceptions.PersistentException;
import com.jointthinker.framework.common.util.StringUtil;
@Service
public class MedicalService extends ManagerBase{
	
	public JsonBean getMedicalMap() throws PersistentException {
		// TODO Auto-generated method stub
		String sql ="select *,hospitalname as title from medical";
		JsonBean json = this.getContextStrategy().getJsonBean(sql);
		if(json.getSize()>0){
			for(int i=0;i<json.getSize();i++){
				JSONObject obj = json.getJsonarray().getJSONObject(i);
				JSONArray arr = new JSONArray();
				arr.add("地址: "+obj.getString("haddress"));
				arr.add("<br/>电话: "+obj.getString("telephone"));
				arr.add("<br/>床位: "+obj.getString("bed"));
				arr.add("<br/>技术优势: "+obj.getString("advantage"));
				arr.add("<br/>地理位置: "+obj.getString("position"));
				arr.add("<br/><button  style='float: right;background-color: rgb(243, 241, 236); color: rgb(0, 0, 0);' onclick=\"choiceMedical("+obj.getLong("id")+",\'"+obj.getString("lat")+"\',\'"+obj.getString("lng")+"\')\">选择该资源</button>");
				obj.put("content", arr);
			}
		}
		return json;
	}

	public JsonBean getMedicalList(Map para) throws PersistentException {
		// TODO Auto-generated method stub
		JsonBean json = null;
		String value;
		StringBuffer query = new StringBuffer("select a.* ");
		query.append(" from medical a ");
		query.append(" where 1=1");
		Integer limit = StringUtil.getMapkeyValue2Int(para, "limit");
		Integer start = StringUtil.getMapkeyValue2Int(para, "start");
		value = StringUtil.getMapkeyValue2Str(para, "hospitalname");
		if (value!=null&&!"".equals(value)){
			query.append(" and a.hospitalname like '%"+value+"%'");
		}
		value = StringUtil.getMapkeyValue2Str(para, "haddress");
		if (value!=null&&!"".equals(value)){
			query.append(" and a.haddress like '%"+value+"%'");
		}
		value = StringUtil.getMapkeyValue2Str(para, "telephone");
		if (value!=null&&!"".equals(value)){
			query.append(" and a.telephone like '%"+value+"%'");
		}
		value = StringUtil.getMapkeyValue2Str(para, "advantage");
		if (value!=null&&!"".equals(value)){
			query.append(" and a.advantage like '%"+value+"%'");
		}
		value = StringUtil.getMapkeyValue2Str(para, "position");
		if (value!=null&&!"".equals(value)){
			query.append(StringUtil.getSqlWhereStr(value, ",", "a", "position"));
		}
		value = StringUtil.getMapkeyValue2Str(para, "bed");
		if (value!=null&&!"".equals(value)){
			query.append(" and a.bed like '%"+value+"%'");
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
