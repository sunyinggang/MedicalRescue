package com.jointthinker.module.system.service;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.jointthinker.framework.bean.JsonBean;
import com.jointthinker.framework.business.ManagerBase;
import com.jointthinker.framework.common.exceptions.PersistentException;

@Service
public class PathService extends ManagerBase{
	
	public JSONObject getCorePath() throws PersistentException {
		String sql = "select * from core_path";
		JsonBean json = this.getContextStrategy().getJsonBean(sql);
		if(json.getSize()>0){
			return json.getJsonarray().getJSONObject(0);
		}else{
			return new JSONObject();
		}
	}

}
