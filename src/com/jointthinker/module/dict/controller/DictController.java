package com.jointthinker.module.dict.controller;

import java.beans.IntrospectionException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jointthinker.framework.bean.JsonBean;
import com.jointthinker.framework.business.InitialManager;
import com.jointthinker.framework.common.exceptions.BusinessLogicException;
import com.jointthinker.framework.common.exceptions.PersistentException;
import com.jointthinker.framework.common.util.JSONutil;
import com.jointthinker.framework.controller.BaseController;
import com.jointthinker.module.dict.pojo.SelectItem;
import com.jointthinker.module.dict.service.DictService;

@RequestMapping("/dict")
@Controller
public class DictController extends BaseController{
	@Autowired
	private DictService dictService;
	
	@RequestMapping("/saveOrmodify")
	public void saveOrmodify(HttpServletRequest request,HttpServletResponse response) throws PersistentException, BusinessLogicException, IntrospectionException{
		Map map = request.getParameterMap();
		SelectItem selectItem = (SelectItem) JSONutil.mapToBean(map, SelectItem.class);
		dictService.storePO(selectItem);
		String str = "{\"success\":\"true\"}";
		sendResponse(response, str);
	}
	
	@RequestMapping("/listType")
	public void listType(HttpServletRequest request,HttpServletResponse response) throws PersistentException, BusinessLogicException, IntrospectionException{
		String stype = request.getParameter("stype");
		String str = "";
		String sqlwhere = " where 1=1 ";
		if (stype!=null && !"".equals(stype) && !"null".equals(stype)) {
			sqlwhere += "and type = '" + stype +"'";
		}
		JsonBean vb = dictService.listType(sqlwhere);
		str = "{\"total\":" + vb.getSize() + ", \"rows\":" +vb.getJsonarray().toString()+"}";
		sendResponse(response, str);
	}
	
	@RequestMapping("/list")
	public void list(HttpServletRequest request,HttpServletResponse response) throws PersistentException, BusinessLogicException, IntrospectionException{
		String name = request.getParameter("name");
		String stype = request.getParameter("type");
		String isview = request.getParameter("isview");
		String itemcode = request.getParameter("itemcode");
		String values = "";
		String sqlwhere = " 1=1 ";
		if (name!=null && !"".equals(name)) {
			sqlwhere += "and name like '%"+name.trim()+"%'";
		}
		if (stype!=null && !"".equals(stype)) {
			sqlwhere += "and type in(" + stype +")";
		}
		if(isview!=null && !"".equals(isview)){
			sqlwhere += "and isview="+isview;
		}
		if(itemcode!=null && !"".equals(itemcode)){
			sqlwhere += "and itemcode='"+itemcode+"'";
		}
		List<SelectItem> selectItemList = dictService.getContextStrategy().findByProperty(SelectItem.class, sqlwhere, null, null, "order by type asc, sequence asc");
		values += "{\"total\":" + selectItemList.size() + ", \"rows\":"+JSONutil.list2json(selectItemList)+"}";
		sendResponse(response, values);
	}
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response) throws PersistentException, BusinessLogicException, IntrospectionException{
		String id = request.getParameter("id");
		dictService.removePO(Long.parseLong(id), SelectItem.class);
		String str =  "{\"success\":\"true\"}";
		sendResponse(response, str);
	}
	@RequestMapping("/reload")
	public void reload(HttpServletRequest request,HttpServletResponse response) throws PersistentException, BusinessLogicException, IntrospectionException, ClassNotFoundException, SQLException{
		//内存初始化
		String reloadtype = request.getParameter("reloadtype");
		InitialManager.init2();
		String str =  "{\"success\":\"true\"}";
		sendResponse(response, str);
	}
	@RequestMapping("/dictreload")
	public void dictreload(HttpServletRequest request,HttpServletResponse response) throws PersistentException, BusinessLogicException, IntrospectionException, ClassNotFoundException, SQLException{
		String id = request.getParameter("id");
		if(id != null && !"".equals(id)) {
			JsonBean si = dictService.getSelectItemById(id);
			sendResponse(response, si.getJsonarray().getJSONObject(0).toString());
		} else {
			sendResponse(response, "{success: false}");
		}
		
	}
	
	@RequestMapping("/listSelectItem")
	public void listSelectItem(HttpServletRequest request,HttpServletResponse response) throws PersistentException, BusinessLogicException, IntrospectionException, ClassNotFoundException, SQLException{
		String type = request.getParameter("type")==null?null:request.getParameter("type").trim();
		String itemcode = request.getParameter("itemcode")==null?null:request.getParameter("itemcode").trim();
		JsonBean bean = dictService.listSelectItem(type,itemcode);
		String values = bean.getSize()>0?bean.getJsonarray().toString():"";
		sendResponse(response, values);
	}
	
	
	@RequestMapping("/setState")
	public void setState(HttpServletRequest request,HttpServletResponse response) throws PersistentException, BusinessLogicException, IntrospectionException, ClassNotFoundException, SQLException{
		String state = request.getParameter("state");
		String ids = request.getParameter("ids");
		dictService.getContextStrategy().jdbcupdate("update selectitem set isview="+state+" where id in("+ids+")");
		sendResponse(response, true);
	}
	

	
}
