package com.jointthinker.module.medical.controller;

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
import com.jointthinker.module.medical.pojo.Medical;
import com.jointthinker.module.medical.service.MedicalService;
@RequestMapping("/medical")
@Controller
public class MedicalController extends BaseController{

	@Autowired
	private MedicalService medicalService;
	@RequestMapping("/getMedicalMap")
	public void getMedicalMap(HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		JsonBean json = medicalService.getMedicalMap();
		sendResponse(response, "{\"success\":true,\"json\":"+json.getJsonarray()+"}");
	}
	@RequestMapping("/getMedicalList")
	public void getMedicalList(HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		Map map=new HashMap();
		map = request.getParameterMap();
		JsonBean json = medicalService.getMedicalList(map);
		sendResponse(response, "{\"total\":"+json.getSize()+",\"rows\":"+json.getJsonarray().toString()+"}");
	}
	@RequestMapping("/getMedicalInfo")
	public void getMedicalInfo(HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		String id = request.getParameter("id");
		if(!"".equals(id)){
			Medical medical = (Medical) medicalService.getByPk(Long.parseLong(id), Medical.class);
			sendResponse(response, "{\"success\":true,\"json\":"+JSONutil.bean2json(medical)+"}");
		}
	}
	
	@RequestMapping("/delMedicalInfo")
	public void delMedicalInfo(HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		String id = request.getParameter("id");
		medicalService.removePO(Long.parseLong(id), Medical.class);
		sendResponse(response, "{\"success\":true}");
	}
	
	@RequestMapping("/save")
	public void save(HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		Map map=new HashMap();
		map = request.getParameterMap();
		Medical medical = (Medical) JSONutil.mapToBean(map, Medical.class);
		medicalService.storePO(medical);
		sendResponse(response, "{\"success\":true}");
	}
	
}
