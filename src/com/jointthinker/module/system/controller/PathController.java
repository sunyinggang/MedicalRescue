package com.jointthinker.module.system.controller;

import java.beans.IntrospectionException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jointthinker.framework.common.exceptions.PersistentException;
import com.jointthinker.framework.common.util.JSONutil;
import com.jointthinker.framework.controller.BaseController;
import com.jointthinker.module.medical.pojo.Medical;
import com.jointthinker.module.system.pojo.CorePath;
import com.jointthinker.module.system.service.PathService;

@RequestMapping("/corepath")
@Controller
public class PathController extends BaseController{

	@Autowired
	private PathService pathService;
	
	@RequestMapping("/save")
	public void save(HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		Map map=new HashMap();
		map = request.getParameterMap();
		CorePath path = (CorePath) JSONutil.mapToBean(map, CorePath.class);
		pathService.storePO(path);
		sendResponse(response, "{\"success\":true}");
	}
	
	@RequestMapping("/getCorePath")
	public void getCorePath(HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		JSONObject obj = pathService.getCorePath();
		
		sendResponse(response, "{\"success\":true,\"pojo\":"+obj+"}");
	}
}
