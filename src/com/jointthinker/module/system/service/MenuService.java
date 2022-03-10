package com.jointthinker.module.system.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.jointthinker.framework.bean.JsonBean;
import com.jointthinker.framework.business.ManagerBase;
import com.jointthinker.framework.common.exceptions.PersistentException;
import com.jointthinker.framework.common.util.Converter;
import com.jointthinker.module.system.pojo.Menu;

@Service
public class MenuService extends ManagerBase{
	
	public JSONArray getMenuByParentId(Long id) throws PersistentException{
		String sql = "select a.*,a.menuname as text from core_menu a where a.isview=1 and a.parentid="+id +" order by a.sequence";
		JsonBean jb = this.getContextStrategy().getJsonBean(sql);
		if(jb!=null&&jb.getSize()>0){
			return jb.getJsonarray();
		}
		return new JSONArray();
	}
	

	
	public JSONArray getAuthMenuByUser(Long id) throws PersistentException{
		StringBuffer finalRights = new StringBuffer("select DISTINCT m.*,m.menuname as text from core_menu m ");
		finalRights.append("order by m.sequence");
		JsonBean jb = this.getContextStrategy().getJsonBean(finalRights.toString());
		if(jb!=null&&jb.getSize()>0){
			return jb.getJsonarray();
		}
		return new JSONArray();
	}
	
	public Menu getMenuByID(Long menuid) throws PersistentException{
		String hql = "select a from Menu a where a.id=" + Converter.getConvertString(menuid);
        @SuppressWarnings("unchecked")
		List<Menu> list = getContextStrategy().query(hql);
        Menu resultData = list.size() > 0 ? (Menu) list.get(0) : null;
        return resultData;
	}
	
	public void removeMenuById(Long menuid) throws PersistentException{
		Menu menu = (Menu) this.getByPk(menuid, Menu.class);
		menu.setDeltime(new Date());
		menu.setIsview(0);
		this.getContextStrategy().store(menu);
	}
	
	public void removeMenuByParentId(Long menuid) throws PersistentException{
		@SuppressWarnings("unchecked")
		List<Menu> menus = this.getContextStrategy().findByProperty(Menu.class, null, "parentid", menuid);
		Date p = new Date();
		for(Menu m : menus){
			m.setDeltime(p);
			m.setIsview(0);
			if(!m.isLeaf()){
				removeMenuByParentId(m.getId());
			}
		}
	}
	
	public List<Menu> getMenuNotLeaf() throws PersistentException {
		String sql = "select id,menuname from core_menu where isview=1 and leaf=0 order by sequence "; 
		@SuppressWarnings("unchecked")
		List<Menu> li = this.getContextStrategy().jdbcQuery(sql, Menu.class);
		return li;
	}
	
	public JsonBean getMenuById(String id) throws PersistentException{
		String sql = "select a.*,b.menuname as parentmenuname from core_menu a inner join core_menu b on a.parentid=b.id where a.id="+id;
		JsonBean vb=getContextStrategy().getJsonBean(sql);
		return vb;
	}
	
	public JSONArray getMenu() throws PersistentException{
		StringBuffer finalRights = new StringBuffer("select m.*,m.menuname as text from core_menu m where m.isview=1 and m.id not in(1,2) and m.parentid<>2 order by m.parentid,m.sequence");
		JsonBean jb = this.getContextStrategy().getJsonBean(finalRights.toString());
		JSONObject root = JSONObject.fromObject("{\"id\":1,\"children\":[]}");
		if(jb!=null&&jb.getSize()>0){
			constructMenuTree(jb.getJsonarray(),root);
			return root.getJSONArray("children").getJSONArray(0);
		}
		return root.getJSONArray("children");
	}
	private void constructMenuTree(JSONArray ja,JSONObject jo) throws PersistentException{
		List<JSONObject> nodes = new ArrayList<JSONObject>();
		for(int i=0;i<ja.size();i++){
			JSONObject jo2 = ja.getJSONObject(i);
			if(jo.getString("id").equals(jo2.getString("parentid"))){
				nodes.add(jo2);
				if(jo2.getInt("leaf")==0){
					constructMenuTree(ja,jo2);
				}
			}
			if("27914".equals(jo2.getString("id"))){//事故概况
				StringBuffer query = new StringBuffer("select a.*,a.id mid,CONCAT('accident_',a.id) m_id,CONCAT('accident/AccidentMapPenel.jsp?id=',a.id) entrypoint,'accidentmappanel' xtype,a.title text,1 leaf,true isaccident from accident a where 1=1");
				JSONArray accidentArr = this.getContextStrategy().getJsonBean(query.toString()).getJsonarray();
				jo2.put("children", accidentArr);
			}
		}
		jo.accumulate("children", nodes);
	}



	public JSONArray getSysMenu() throws PersistentException {
		// TODO Auto-generated method stub
		StringBuffer finalRights = new StringBuffer("select m.*,m.menuname as text,'yes' issys from core_menu m where m.isview=1 and m.parentid=2 order by m.parentid,m.sequence");
		JsonBean jb = this.getContextStrategy().getJsonBean(finalRights.toString());
		return jb.getJsonarray();
	}
	
}
