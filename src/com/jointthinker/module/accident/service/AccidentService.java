package com.jointthinker.module.accident.service;

import java.io.File;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.jointthinker.framework.bean.JsonBean;
import com.jointthinker.framework.business.Config;
import com.jointthinker.framework.business.ManagerBase;
import com.jointthinker.framework.common.exceptions.PersistentException;
import com.jointthinker.framework.common.util.StringUtil;



@Service
public class AccidentService extends ManagerBase{
	
	public JsonBean getAccidentMap(String id) throws PersistentException {
		// TODO Auto-generated method stub
		String sql ="select * from accident where 1=1";
		if(id!=null&&!"".equals(id)){
			sql += " and id="+id;
		}
		JsonBean json = this.getContextStrategy().getJsonBean(sql);
		if(json.getSize()>0){
			for(int i=0;i<json.getSize();i++){
				JSONObject obj = json.getJsonarray().getJSONObject(i);
				JSONArray arr = new JSONArray();
				if(!"".equals(obj.getString("photopath"))){
					 String str = "<div><img src=\"accident/getPhoto?path="+obj.getString("photopath")+"\" align='right' style='float:right;margin:4px' width='139' height='104'/>";
					 arr.add(str+obj.getString("content")+"<div>");
				}else{
					arr.add(obj.getString("content"));
				}
				
				obj.put("content", arr);
			}
		}
		return json;
	}

	public JsonBean getAccidentList(Map para) throws PersistentException {
		// TODO Auto-generated method stub
		JsonBean json = null;
		String value;
		StringBuffer query = new StringBuffer("select a.* ");
		query.append(" from accident a ");
		query.append(" where 1=1");
		Integer limit = StringUtil.getMapkeyValue2Int(para, "limit");
		Integer start = StringUtil.getMapkeyValue2Int(para, "start");
		value = StringUtil.getMapkeyValue2Str(para, "title");
		if (value!=null&&!"".equals(value)){
			query.append(" and a.title like '%"+value+"%'");
		}
		value = StringUtil.getMapkeyValue2Str(para, "createtime");
		if (value!=null&&!"".equals(value)){
			query.append(StringUtil.getSqlWhereDateMin(value, " - ", "a", "createtime"));
		}
	
		value = StringUtil.getMapkeyValue2Str(para, "sort");
		if (value!=null&&!"".equals(value)){
			query.append(" order by "+value);
			value = StringUtil.getMapkeyValue2Str(para, "order");
			if (value!=null&&!"".equals(value)){
				query.append(" "+value);
			}
		}else{
			query.append(" order by createtime desc ");
		}
		
		if (limit!=null&&start!=null){
			json = this.getContextStrategy().getJsonBeanPaging(query.toString(), start.intValue(), limit.intValue());
		}else{
			json = this.getContextStrategy().getJsonBean(query.toString());
		}
		return json;
	}
	
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
	public JSONObject getCorePath() throws PersistentException {
		String sql ="select * from core_path";
		JsonBean json = this.getContextStrategy().getJsonBean(sql);
		if(json.getSize()>0){
			return json.getJsonarray().getJSONObject(0);
		}else{
			return null;
		}
	}
}
