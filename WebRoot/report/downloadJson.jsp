<%@page import="com.jointthinker.module.common.service.HSSFManager"%>
<%@page import="com.jointthinker.framework.common.util.DateUtil"%>
<%@page import="org.json.JSONArray"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.io.*" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFWorkbook"%>
<%@ page import="org.apache.poi.xssf.usermodel.XSSFWorkbook"%>  
<%
	request.setCharacterEncoding("UTF-8");
	String excelhead = (String)request.getAttribute("excelhead");
	JSONArray jsonarray = (JSONArray)request.getAttribute("jsonarray");
	String filename = (String)request.getAttribute("filename");
	System.out.println("---------------page----downloadJson.jsp----excelhead-------" + excelhead);
	System.out.println("---------------page----downloadJson.jsp----filename-------" + filename);
	//String ffname = System.currentTimeMillis() + "";
	String cudate = DateUtil.getSystemCurrentDate();
	if(excelhead != null && !"".equals(excelhead) && jsonarray != null) {
		XSSFWorkbook book = new XSSFWorkbook();
		//HSSFWorkbook book = new HSSFWorkbook();
		HSSFManager manager = new HSSFManager();
		manager.generateExcel2007Data(book, excelhead, filename, jsonarray);
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(filename+ "_" + cudate,"UTF-8")+".xlsx");
		OutputStream outstream = response.getOutputStream();
		try{	
			book.write(outstream);
			outstream.flush();
			if(outstream != null) {
				outstream.close();
			}
		}catch(IOException e) {
			
			out.clear(); 
	 		out = pageContext.pushBody(); 
 			return;
		}
	
		 out.clear(); 
   		 out = pageContext.pushBody(); 
		
	}
%>