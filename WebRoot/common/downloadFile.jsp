<%@page import="com.jointthinker.framework.business.InitialManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.net.URLDecoder"%>
<%
	request.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
	String redURL = path + "/fff.jsp";
	String enclosurePath = request.getParameter("enclosurePath");
	String attachmentPath = (String)InitialManager.getConfig().getFile().get("attachmentPath");
	//enclosurePath = getServletContext().getRealPath("")+enclosurePath.replace("/","\\");
	//enclosurePath = attachmentPath + enclosurePath.replace("/","\\");
	enclosurePath = attachmentPath + enclosurePath;
	String returnPath = request.getParameter("returnPath");
	//String filename = URLDecoder.decode(request.getParameter("filename"),"UTF-8");
	String filename = request.getParameter("filename");
	System.out.println("----文件URL:" + enclosurePath+File.separator+filename);
	FileInputStream fis = null;
	if(enclosurePath != null && !"".equals(enclosurePath)) {
		File f = new File(enclosurePath+File.separator+filename);
    	if(!f.exists()) {
    		if(returnPath != null && !"".equals(returnPath) && !"null".equals(returnPath)) {
    			if(returnPath.contains("&")) {
    				returnPath = returnPath.replaceAll("&",",");
    			}
    			redURL = redURL + "?returnPath=" + returnPath;
    		}
    		response.sendRedirect(redURL);
    		return;
   		}
    	fis  = new FileInputStream(f);
    	response.reset();
    	response.setHeader("Accept-Ranges","bytes");
   		long p = 0;
   		long l = 0;
   		l = f.length();
   		if(request.getHeader("Range") != null) {
   			response.setStatus(response.SC_PARTIAL_CONTENT);
   			p = Long.parseLong(request.getHeader("Range").replaceAll("bytes=","").replaceAll("-",""));
   	
   		}
   		
   		//response.setCharacterEncoding("utf-8");
   		response.setHeader("Content-Length",new Long(l-p).toString());
   		if(p != 0) {
   		
   			response.setHeader("Content-Range","bytes" + new Long(p).toString() + new Long(l-1).toString() + "/" + new Long(l).toString());
   		}
   		response.setContentType("aplication/octet-stream");
   		response.setHeader("Content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(filename,"UTF-8"));
   		fis.skip(p);
   		byte[] b = new byte[1024]; 
   		int fileRead;
	    try {
    		while ( (fileRead = fis.read(b)) != -1 ){
    	    	response.getOutputStream().write(b,0,fileRead);
    	    } 
    	} catch(IOException e) {
	    	System.out.println("error:");
    	}
	    finally {
	    	if(fis != null) {
	    		fis.close();
	    	}
	    	out.clear(); 
	    	out = pageContext.pushBody(); 
	    	b=null;
	    }
	}
%>