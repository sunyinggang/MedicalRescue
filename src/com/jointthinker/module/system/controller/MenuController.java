package com.jointthinker.module.system.controller;

import java.beans.IntrospectionException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jointthinker.framework.bean.JsonBean;
import com.jointthinker.framework.common.exceptions.PersistentException;
import com.jointthinker.framework.controller.BaseController;
import com.jointthinker.module.system.pojo.Menu;
import com.jointthinker.module.system.service.MenuService;

@RequestMapping("/menu")
@Controller
public class MenuController extends BaseController {
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping("/tree")
	public void orgTree(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, PersistentException {
		String node = request.getParameter("node");
		JSONArray ja = new JSONArray();
		ja = menuService.getMenuByParentId(Long.parseLong(node));
		sendResponse(response, ja.toString());
	}
	
	@RequestMapping("/record")
	public void listMenuTreeRecord(HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		String menuid=request.getParameter("menuid");
		if (menuid!=null&&!"".equals(menuid)){
			JsonBean menu=menuService.getMenuById(menuid);
			sendResponse(response, menu.getJsonarray().getJSONObject(0).toString());
		}
	}
	
	@RequestMapping("/save")
	public void saveMenu(Menu menu, HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		Date present = new Date();
		Menu parent = menuService.getMenuByID(menu.getParentid());
		menu.setGrade(parent.getGrade()+1);
		if(menu.getId()==null||"".equals(menu.getId())){
			menu.setCreatetime(present);
		}
		menu.setLastupdatetime(present);
		menuService.storePO(menu);
		sendResponse(response, true);
	}
	
	@RequestMapping("/notleaf")
	public void listMenuTreeNotleaf(HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		List<Menu> menuList=menuService.getMenuNotLeaf();
		sendResponse(response, JSONArray.fromObject(menuList).toString());
	}
	
	@RequestMapping("/remove")
	public void removeMenu(Long menuid, HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		menuService.removeMenuByParentId(menuid);
		menuService.removeMenuById(menuid);
		sendResponse(response, true);
	}
	@RequestMapping("/getMenu")
	public void getMenu(Long menuid, HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		
		sendResponse(response, menuService.getMenu().toString());
	}
	@RequestMapping("/getSysMenu")
	public void getSysMenu(Long menuid, HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		
		sendResponse(response, menuService.getSysMenu().toString());
	}
	
}
