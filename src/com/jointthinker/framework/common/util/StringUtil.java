package com.jointthinker.framework.common.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jointthinker.framework.business.Config;


public class StringUtil {

	
	public static String getSqlWhereInt(String str,String sp,String area,String field){
		if(str != null&&!"".equals(str)&&!"null".equals(str)) {
			StringBuffer buffer=new StringBuffer();
			String[] strsp = str.split(sp);
			if(strsp.length>0){
				buffer.append(" and (");
				for(int k=0; k<strsp.length; k++) {
					if (k>0)	buffer.append(" or ");
					buffer.append(area+"."+field+" = "+strsp[k]);
				}
				buffer.append(")");
				
			}else {
				buffer.append(" and "+area+"."+field+" = "+str);
			}
			return buffer.toString();
			
		}
		else{
			return "";
		}
	}
	
	public static String[] getParamValue(String params, String sp) {
		
		String[] retval = null;
		if(params != null && !"".equals(params) && !"null".equals(params)) {
			retval = params.split(sp);
			return retval;
		}else {
			
			return null;
		}
	}

	public static String[] getFilenamePath(String filename) {
		
		String[] retval = new String[2];
		if(filename == null) {
			
			return null;
		}else{
			
			if(filename.contains(".") || filename.contains("．")) {
				
				filename = filename.replaceAll("．", ".");
				String suffix =  filename.substring(filename.lastIndexOf(".")+1,filename.length());
				suffix = suffix.toLowerCase();
				retval = toPutPath(suffix);
			}else {
				
				return null;
			}
		}
		
		return retval;
	}
	
	public static String contString(String [] strings,String spli){
		if (strings==null||strings.length==0)	return null;
		if (spli==null) return strings[0];
		StringBuffer string=new StringBuffer();
		for (int i=0;i<strings.length;i++){
			if (i>0)	string.append(spli);
			string.append(strings[i]);
		}
		return string.toString();
	}
	
	public static String[]  toPutPath(String suffix) {
		
		String[] suffixstr = new String[2];
		if("doc".equals(suffix) || "docx".equals(suffix)) {
			suffixstr[0] = "affair/images/word.png";
			suffixstr[1] = "Word文档";
		}else if("rar".equals(suffix)) {
			suffixstr[0] = "affair/images/rar.png";
			suffixstr[1] = "rar文件";
		}else if("html".equals(suffix)) {
			suffixstr[0] = "affair/images/html.png";
			suffixstr[1] = "html文件";
		}else if("txt".equals(suffix)) {
			suffixstr[0] = "affair/images/txt.png";
			suffixstr[1] = "文本文档";
		}else if("pdf".equals(suffix)) {
			suffixstr[0] = "affair/images/pdf.png";
			suffixstr[1] = "pdf文档";
		}else if("vsd".equals(suffix)) {
			suffixstr[0] = "affair/images/vsd.png";
			suffixstr[1] = "Visio文件";
		}else if("chm".equals(suffix)) {
			suffixstr[0] = "affair/images/chm.png";
			suffixstr[1] = "chm文档";
		}else if("xml".equals(suffix)) {
			suffixstr[0] = "affair/images/xml.png";
			suffixstr[1] = "xml文件";
		}else if("ppt".equals(suffix) || "pptx".equals(suffix)) {
			suffixstr[0] = "affair/images/ppt.png";
			suffixstr[1] = "PPT文档";
		}else if("zip".equals(suffix)) {
			suffixstr[0] = "affair/images/zip.png";
			suffixstr[1] = "zip文件";
		}else if("bat".equals(suffix)) {
			suffixstr[0] = "affair/images/bat.png";
			suffixstr[1] = "bat文件";
		}else if("jar".equals(suffix)) {
			suffixstr[0] = "affair/images/jar.png";
			suffixstr[1] = "jar文件";
		}else if("xls".equals(suffix) || "xlsx".equals(suffix)) {
			suffixstr[0] = "affair/images/excel.png";
			suffixstr[1] = "Excel文档";
		}else if("gif".equals(suffix) || "png".equals(suffix) || "jpg".equals(suffix) || "jpeg".equals(suffix)) {
			suffixstr[0] = "affair/images/excel.png";
			suffixstr[1] = "图片";
		}else if("wps".equals(suffix)) {
			suffixstr[0] = "affair/images/word.png";
			suffixstr[1] = "wps文档";
		}else if("exe".equals(suffix)) {
			suffixstr[0] = "affair/images/exe.png";
			suffixstr[1] = "exe文件";
		}else {
			suffixstr[0] = "affair/images/text.png";
			suffixstr[1] = "其他文档";
		}
		return suffixstr;
	}
	public static String getSqlWhereStr(String str,String sp,String area,String field){
		if(str != null&&!"".equals(str)&&!"null".equals(str)&&!"[]".equals(str)) {
			str=str.replace("'", "");
			str=str.replace("\"", "");
			
			if (str.charAt(0)=='[')	str=str.substring(1);
			if (str.charAt(str.length()-1)==']')	str=str.substring(0,str.length()-1);
			StringBuffer buffer=new StringBuffer();
			String[] strsp = str.split(sp);
			if(strsp.length>0){
				buffer.append(" and (");
				for(int k=0; k<strsp.length; k++) {
					if (k>0)	buffer.append(" or ");
					buffer.append(area+"."+field+" like '%"+strsp[k].trim()+"%'");
				}
				buffer.append(")");
				
			}else {
				buffer.append(" and "+area+"."+field+" like '%"+str.trim()+"%'");
			}
			return buffer.toString();
			
		}
		else{
			return "";
		}
	}

	public static String getSqlMuWhereStr(String str,String sp,String area,String field){
		if(str != null&&!"".equals(str)&&!"null".equals(str)&&!"[]".equals(str)) {
			str=str.replace("'", "");
			str=str.replace("\"", "");
			
			if (str.charAt(0)=='[')	str=str.substring(1);
			if (str.charAt(str.length()-1)==']')	str=str.substring(0,str.length()-1);
			StringBuffer buffer=new StringBuffer();
			String[] strsp = str.split(sp);
			if(strsp.length>0){
				buffer.append(" and (");
				for(int k=0; k<strsp.length; k++) {
					if (k>0)	buffer.append(" or ");
					buffer.append(area+"."+field+" like '%,"+strsp[k].trim()+",%'");
				}
				buffer.append(")");
				
			}else {
				buffer.append(" and "+area+"."+field+" like '%"+str.trim()+"%'");
			}
			return buffer.toString();
			
		}
		else{
			return "";
		}
	}

	public static String ConvertHtmlKonwlege(String str){
		String returnstr=str;
		System.out.println("str:" + str);
		returnstr = returnstr.replaceAll("</?[^>]+>","");
		returnstr = returnstr.replaceAll("\"\\w*?\\W??\\w*?\":\"\",?","");
		returnstr = returnstr.replaceAll("\'","\\\\'");
		returnstr = returnstr.replace("&lt;", "<");
		returnstr = returnstr.replace("&gt;", ">");
		returnstr = returnstr.replace("&quot;", "\"");
		returnstr = returnstr.replace("&nbsp;", "");
		returnstr = returnstr.replaceAll(",}$", "}");
		returnstr = returnstr.replace("\\r", "\\\\r");
		returnstr = returnstr.replace("\\n", "\\\\n");
		return returnstr;
	}

	public static String ConverString(String str){
		if (str==null)	return "";
		String returnstr=str;
		returnstr = returnstr.replaceAll("\"\\w*?\\W??\\w*?\":\"\",?","");
		returnstr = returnstr.replaceAll("\'","\\\\'");
		returnstr = returnstr.replaceAll(",}$", "}");
		returnstr = returnstr.replace("\r", "\\r");
		returnstr = returnstr.replace("\n", "\\n");
		return returnstr;
	}

	public static String getMapkeyValue2Str(Map map,String key){
		if (map==null)	return null;
		if (map.containsKey(key)){
			if (map.get(key).getClass().isArray()){
				String values[]=(String [])map.get(key);
				return (String)values[0];
    	    }else{
    	    	return (String)map.get(key);
    	    }
		}else{
			return null;
		}
	}

	public static Integer getMapkeyValue2Int(Map map,String key){
		if (map==null)	return null;
		if (map.containsKey(key)){
			Integer val = null;
			try{
				if (map.get(key).getClass().isArray()){
					String values[]=(String [])map.get(key);
					val = Integer.valueOf((String)values[0]);
	    	    }else{
	    	    	val = Integer.valueOf((String)map.get(key));
	    	    }
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}finally {
				return val;
			}
		}else{
			return null;
		}
	}

	public static String getSqlWhereDate(String str,String sp,String area,String field){
		if(str != null&&!"".equals(str)&&!"null".equals(str)&&!"[]".equals(str)) {
			str=str.replace("'", "");
			str=str.replace("\"", "");
			
			if (str.charAt(0)=='[')	str=str.substring(1);
			if (str.charAt(str.length()-1)==']')	str=str.substring(0,str.length()-1);
			StringBuffer buffer=new StringBuffer();
			String[] strsp = str.split(sp);
			String dbType = Config.getProp("DbType");
			if(strsp.length>0){
				if("oracle".equals(dbType)) {
					buffer.append(" and "+area+"."+field+" >= to_date('"+strsp[0].trim()+" 00:00:00','yyyy-mm-dd HH24:mi:ss') and "+area+"."+field+" <= to_date('"+strsp[1].trim()+" 23:59:59','yyyy-mm-dd HH24:mi:ss')");
				}else if("mysql".equals(dbType)) {
					buffer.append(" and "+area+"."+field+" >= str_to_date('"+strsp[0].trim()+" 00:00:00','%Y-%m-%d %H:%i:%s') and "+area+"."+field+" <= str_to_date('"+strsp[1].trim()+" 23:59:59','%Y-%m-%d %H:%i:%s')");
				}
			}else {
				if("oracle".equals(dbType)) {
					buffer.append(" and "+area+"."+field+" = to_date('"+str.trim()+"','yyyy-mm-dd')");
				}else if("mysql".equals(dbType)) {
					buffer.append(" and "+area+"."+field+" = str_to_date('"+strsp[0].trim()+"','%Y-%m-%d')");
				}
			}
			return buffer.toString();
			
		}
		else{
			return "";
		}
	}

	public static String getIdFieldByDbType() {
		String dbType = Config.getProp("DbType");
		if(StringUtils.isEmpty(dbType)) {
			return "null";
		}else if("oracle".equals(dbType)) { 
			return "flow_sequence.nextval";
		}else if("mysql".equals(dbType) || "sqlserver".equals(dbType) || "db2".equals(dbType)) { 
			return "null";
		}
		return null;
	}

	public static int indexOfArray(String[] sa, String s){
		if (s==null){
			return -1;
		}
		int j = -1;
		for (int i=0; i<sa.length;i++){
			if (s.equals(sa[i])){
				j = i;
				break;
			}
		}
		return j;
	}

	
	public static String getLogIdFieldByDbType() {
		String dbType = Config.getProp("DbType");
		if(StringUtils.isEmpty(dbType)) {
			return "null";
		}else if("oracle".equals(dbType)) { 
			return "log_sequence.nextval";
		}else if("mysql".equals(dbType) || "sqlserver".equals(dbType) || "db2".equals(dbType)) { 
			return "null";
		}
		return null;
	}
   
	public static String join(String[] s){
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<s.length;i++){
			sb.append(s[i]);
		}
		return sb.toString();
	}
	

	public static String upperFirstLetter(String word){
		char c = word.charAt(0);
		c = Character.toUpperCase(c) ;
		String s = c+ word.substring(1);
		return s;
	}
	
	
	public static String lowerFirstLetter(String word){
		char c = word.charAt(0);
		c = Character.toLowerCase(c);
		String s = c+ word.substring(1);
		return s;
	}
	
	public static String simpleClassName(String className){
		int i = className.lastIndexOf('.');
		if (i>=0){
			return className.substring(i+1);
		}else{
			return className;
		}
	}
	
	public static String packageName(String className){
		int i = className.lastIndexOf('.');
		if (i>0){
			return className.substring(0, i);
		}else{
			return "";
		}
	}
	
	public static String packagePath(String packageName){
		return packageName.replace('.','/');
	}
	
	public static String extractFilename(String fullpath){
		int i = fullpath.lastIndexOf('/');
		if (i<0){
			i = fullpath.lastIndexOf('\\');
		}
		if (i>0){
			return fullpath.substring(i+1);
		}else{
			return "";
		}
	}

	public static String getTitleLen(String s, int l){
		if (s == null){
			return "";
		}else if (s.length()>l){
			return (s.substring(0,l-1)+"…");
		}else{
			return s;
		}
	}

	public static String getMaxLen(String s, int l){
		if (s == null){
			return "";
		}else if (s.length()>l){
			return (s.substring(0,l));
		}else{
			return s;
		}
	}

	public static String getTipLen(String s, int l){
		if (s == null){
			return "";
		}else if (s.length()>l){
			return s;
		}else{
			return "";
		}
	}
	
	public static String getStringEncode(String inputString){
		String[] encode={"UTF-8","GBK","GB2312","ISO-8859-1"};
		int i;
		for (i=0;i<encode.length;i++){
			try {      
	          if (inputString.equals(new String(inputString.getBytes(encode[i]), encode[i]))) {      //判断是不是对应的编码格式
	              break;
	          }      
	       } catch (Exception exception) {
	    	   
	       } finally {
	    	   break;
	       }
		}
		return i>=encode.length?"":encode[i];
	}
	public static String convertStringByEncode(String inputString,String encode) throws UnsupportedEncodingException{
		String resEncode = getStringEncode(inputString);
		if ("".equals(resEncode)){
			return inputString;
		}else{
			return new String(inputString.getBytes(resEncode), encode);
		}
	}
	
	
	
	public static String getSqlWhereString2Date(String str,String sp,String area,String field){
		if(str != null&&!"".equals(str)&&!"null".equals(str)&&!"[]".equals(str)) {
			str=str.replace("'", "");
			str=str.replace("\"", "");
			
			if (str.charAt(0)=='[')	str=str.substring(1);
			if (str.charAt(str.length()-1)==']')	str=str.substring(0,str.length()-1);
			StringBuffer buffer=new StringBuffer();
			String[] strsp = str.split(sp);
			String dbType = Config.getProp("DbType");
			if(strsp.length>0){
				buffer.append(" and "+area+"."+field+"  >= '"+strsp[0].trim()+"' and "+area+"."+field+" <= '"+strsp[1].trim()+"'");
			}else {
				buffer.append(" and("+area+"."+field+" = '"+str.trim()+"'");
			}
			return buffer.toString();
			
		}
		else{
			return "";
		}
	}

	public static String getSqlWhereDateMin(String str,String sp,String area,String field){
		if(str != null&&!"".equals(str)&&!"null".equals(str)&&!"[]".equals(str)) {
			str=str.replace("'", "");
			str=str.replace("\"", "");
			
			if (str.charAt(0)=='[')	str=str.substring(1);
			if (str.charAt(str.length()-1)==']')	str=str.substring(0,str.length()-1);
			StringBuffer buffer=new StringBuffer();
			String[] strsp = str.split(sp);
			if(strsp.length>0){
				buffer.append(" and "+area+"."+field+"  >= '"+strsp[0].trim()+" 00:00' and "+area+"."+field+" <= '"+strsp[1].trim()+" 23:59'");
			}else {
				buffer.append(" and("+area+"."+field+" = '"+str.trim()+"'");
			}
			return buffer.toString();
			
		}
		else{
			return "";
		}
	}
	
}
