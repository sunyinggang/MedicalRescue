package com.jointthinker.framework.common.util;

import java.sql.SQLException;

import com.jointthinker.framework.business.BusinessManager;
import com.jointthinker.framework.common.exceptions.PersistentException;
import com.jointthinker.framework.web.bean.ViewBean;

public class ToolsUtil {
	
	public static String getCallLoaction(String callno) throws PersistentException, SQLException {
		String prefix = "";
		boolean isAreaNumber = false;
		if(callno!=null&&!"".equals(callno)){
			if(callno.startsWith("+86")||callno.startsWith("086")){
				callno = callno.substring(3);
				if (callno.startsWith("10")||callno.startsWith("11")||callno.startsWith("68")||callno.startsWith("2")){
					callno = "0"+callno.substring(3);
				}
			}
			if(callno.startsWith("0086")){
				callno = callno.substring(3);
				if (callno.startsWith("10")||callno.startsWith("11")||callno.startsWith("68")||callno.startsWith("2")){
					callno = "0"+callno.substring(4);
				}
			}
			if(callno.startsWith("0")){
				if (callno.startsWith("010")||callno.startsWith("011")||callno.startsWith("068")){
					prefix = callno.substring(0,3);
					isAreaNumber = true;
				}else if(callno.startsWith("02")){
					prefix = callno.substring(0,3);
					isAreaNumber = true;
				}else if(callno.startsWith("01")) {
					prefix = callno.substring(1,8);
				}else if(callno.startsWith("080")) {
					prefix = callno.substring(0,5);
					isAreaNumber = true;
				}else if(callno.startsWith("04887")||callno.startsWith("04888")||callno.startsWith("09455")||callno.startsWith("09477")||callno.startsWith("09498")||callno.startsWith("09848")) {
					prefix = callno.substring(0,5);
					isAreaNumber = true;
				}else{
					prefix = callno.substring(0,4);
					isAreaNumber = true;
				}
			}else {
				if(callno.length()>8) {
					prefix = callno.substring(0,7);
				}else{
					prefix = "010";
					isAreaNumber = true;
				}
			}
		}
		String sql = "select concat(areadesc,'-',city) as area from phone_areaname where nativenumberhead='"+prefix+"'";
		if (isAreaNumber){
			sql = "select distinct concat(areadesc,'-',city) as area from phone_areaname where areanumber='"+prefix+"'";
		}
		BusinessManager business = new BusinessManager();
		ViewBean vb = business.getViewBean(sql);
		if (vb.getSize()>0){
			return vb.getViewBeanValue("area", 0);
		}else{
			return "";
		}
	}
}
