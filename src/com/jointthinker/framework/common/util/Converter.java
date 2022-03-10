package com.jointthinker.framework.common.util;

import java.io.File;

public class Converter {
	/**
	   * 填充字符串
	   * @param str String 待填充的字符串
	   * @param iLen int 最终长度
	   * @param cPadding char 填充字符
	   * @param bDir boolean 填充位置
	   * @return String 填充后的字符串
	   */
	  public static String padding(String str, int iLen, char cPadding,
	                               boolean bDir) {
	    String sRet = str;
	    if (str.length() < iLen) {
	      for (int i = 0; i < iLen - str.length(); i++) {
	        sRet = (bDir ? String.valueOf(cPadding) : "") + sRet +
	            (bDir ? "" : String.valueOf(cPadding));
	      }
	    }
	    return sRet;
	  }
	  
	  
	  public static String getString(String strIn)
	  {
	     if(strIn == null)
	       return "";
	     return strIn;
	  }
	  
	  
	  public static String getString(Object strIn)
	  {
	     if(strIn == null) return null;
	     return String.valueOf(strIn);
	  }
	  
	  
	  public static String getPureFileName(String fullPathName) {
	    File f = new File(fullPathName);
	    return f.getName();
	  }
	  
	  
	  public static String getPureFileName(String pathName, String spliter) {
		String [] temp = pathName.split(spliter);
		return temp[temp.length-1];
	  } 
	  
	  
	  public static Double saveGetDouble(String s, Double d){
			try{
				return new Double(s);
			}catch(Exception ex){
				return d;
			}
		}
	  
	  
	  public static Integer saveGetInteger(String s, Integer i){
			try{
				return new Integer(s);
			}catch(Exception ex){
				return i;
			}
		}
	  
	  /**
	   * 将数据库中取出字段的NULL值转换为""串
	   * @param obj
	   * @return
	   */
	  public static Object getConvertString(Object obj) {
		  
		 Object object = "";
		 if(obj!=null && !"null".equals(obj)) {
			 
			 object = obj;
		 } 
		 return object;
	  }
	  /**
	   * 将数据库中取出字段的NULL值转换为""串,并删除\r\n
	   * @param obj
	   * @return
	   */
	  public static String getConvertStringRN(String obj) {
		  
		  String object = "";
		 if(obj!=null && !"null".equals(obj)) {
			 
			 object = obj;
		 }
		 object=object.replace("\r", "\\r");
		 object=object.replace("\n", "\\n");
		 
		 return object;
	  }
	  public static String getConvertFilename(String obj){
		  String result = obj.replace("'", "");
		  result = result.replace(" ", "");
		  return result;
	  }
	  
	  public static String getFormatString(String obj) {
		  
		  String object = obj;
		  if(object == null) {
			  
			  return "";
		  }else {
			  
			  	 String regEx="<.+?>"; //  "&lt;.+?&gt;"  这是字符形式的<>  
		         java.util.regex.Pattern ppp=java.util.regex.Pattern.compile(regEx);
		         java.util.regex.Matcher mmm=ppp.matcher(object==null?"":object.toString()); 
		         String tempstr =mmm.replaceAll("");
		         tempstr = tempstr.replace("&nbsp;", " ");
		         tempstr = tempstr.replace("&ldquo;", "\\“");
		         tempstr = tempstr.replace("&rdquo;", "\\”");
		         tempstr = tempstr.replace("&times;", "×");
		         tempstr = tempstr.replace("&mdash;", "—");
		         object = tempstr;
		         //System.out.println("----------替代后的字符串-----" + tempstr);
		         return object;
		  }
	  }
	  
	  public static void main(String args[]) {
		  
		  Integer a = new Integer(22);
		  String b = "abc";
		  String c = "";
		  System.out.println(Converter.getConvertFilename("20111031220816_问题？Freetao'sBlog.mht"));
	  }
}
