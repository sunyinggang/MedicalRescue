<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="java.io.File" %>
<%@ page import="java.nio.channels.FileChannel" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.net.URLDecoder"%>
<%
	request.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
	String filename = request.getParameter("filename");
	//String outfilename = (String)InitialManager.getConfig().getFile().get("attachmentPath")+File.separator+"down.text";
	String outfilename = getServletContext().getRealPath("")+File.separator+"down.text";;
	//enclosurePath = attachmentPath + enclosurePath.replace("/","\\");
	FileInputStream fi = null;
    FileOutputStream fo = null;
    FileChannel in = null;
    FileChannel outfile = null; 
    try {
		fi = new FileInputStream(filename);
    	fo = new FileOutputStream(outfilename);
    	in = fi.getChannel();//得到对应的文件通道
    	outfile = fo.getChannel();//得到对应的文件通道
    	in.transferTo(0, in.size(), outfile);//连接两个通道，并且从in通道读取，然后写入out通道
    } catch (IOException e) {
            e.printStackTrace();
    } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
%>