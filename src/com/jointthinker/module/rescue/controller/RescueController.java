package com.jointthinker.module.rescue.controller;

import java.beans.IntrospectionException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jointthinker.framework.bean.JsonBean;
import com.jointthinker.framework.common.exceptions.PersistentException;
import com.jointthinker.framework.common.util.JSONutil;
import com.jointthinker.framework.controller.BaseController;
import com.jointthinker.module.rescue.pojo.Rescue;
import com.jointthinker.module.rescue.service.RescueService;
@RequestMapping("/rescue")
@Controller
public class RescueController extends BaseController{

	@Autowired
	private RescueService rescueService;
	@RequestMapping("/getRescueMap")
	public void getRescueMap(HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		JsonBean json = rescueService.getRescueMap();
		sendResponse(response, "{\"success\":true,\"json\":"+json.getJsonarray()+"}");
	}
	@RequestMapping("/getRescueList")
	public void getRescueList(HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		Map map=new HashMap();
		map = request.getParameterMap();
		JsonBean json = rescueService.getRescueList(map);
		sendResponse(response, "{\"total\":"+json.getSize()+",\"rows\":"+json.getJsonarray().toString()+"}");
	}
	@RequestMapping("/getRescueInfo")
	public void getMedicalInfo(HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		String id = request.getParameter("id");
		if(!"".equals(id)){
			Rescue rescue = (Rescue) rescueService.getByPk(Long.parseLong(id), Rescue.class);
			sendResponse(response, "{\"success\":true,\"json\":"+JSONutil.bean2json(rescue)+"}");
		}
	}
	
	@RequestMapping("/delRescueInfo")
	public void delRescueInfo(HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		String id = request.getParameter("id");
		rescueService.removePO(Long.parseLong(id), Rescue.class);
		sendResponse(response, "{\"success\":true}");
	}
	
	@RequestMapping("/save")
	public void save(HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		Map map=new HashMap();
		map = request.getParameterMap();
		Rescue rescue = (Rescue) JSONutil.mapToBean(map, Rescue.class);
		rescueService.storePO(rescue);
		sendResponse(response, "{\"success\":true}");
	}
	
}
