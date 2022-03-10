package com.jointthinker.framework.common.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.jspsmart.upload.SmartUpload;

public class SmartUploadMap extends SmartUpload {
	private Map parameters=new HashMap();
	public Map getParameterMap(){
		if (!parameters.isEmpty()) return parameters;
		Enumeration names=this.getRequest().getParameterNames();
		while (names.hasMoreElements()){			
			String name = (String)names.nextElement();
			parameters.put(name, this.getRequest().getParameterValues(name));
		}
//		parameters.isEmpty();
		return parameters;
	}
}