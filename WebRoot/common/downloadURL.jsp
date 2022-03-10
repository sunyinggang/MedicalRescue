<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*"  %>
<%@ page import="javax.sound.sampled.*" %>
<%
	request.setCharacterEncoding("UTF-8");
	String filename = request.getParameter("filename");//文件path
	filename=filename.replace("\\","/");
	String filename2 = request.getParameter("filename2");//文件名
	filename2=filename2.replace("\\","/");
	String s2=java.net.URLEncoder.encode(filename2,"UTF-8");
	URL url = new URL(filename + s2);
	System.out.println("url" + url);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    //conn.setRequestProperty("Accept-Language","en-us,en;q-0.7,zh-cn;q-0.3");
    //conn.setRequestProperty("Accept-Encoding","utf-8");
    //conn.setRequestProperty("Accept-Charset","ISO-8859-1,utf-8;q=0.7,*;q=0.7");
    //conn.setRequestProperty("If-None-Match","\"1261d8-4290-df64d224\"");
    BufferedInputStream fis= new BufferedInputStream(conn.getInputStream());
	response.reset();
	response.setHeader("Accept-Ranges","bytes");
    response.setContentType("aplication/octet-stream");   	
    response.setHeader("Content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(filename2,"UTF-8"));
    byte[] b = new byte[1024]; 
    int fileRead;   			
	try {
	    while ( (fileRead = fis.read(b)) != -1 ){
	    	response.getOutputStream().write(b,0,fileRead);
	    	//response.getOutputStream().write(fileRead);
    	} 
    } catch(IOException e) {
    	out.clear(); 
   		out = pageContext.pushBody(); 
	    return;
    }		
	if(fis != null) {			
		fis.close();
	}
	out.clear(); 
   	out = pageContext.pushBody();
   	//out.flush(); 
%>