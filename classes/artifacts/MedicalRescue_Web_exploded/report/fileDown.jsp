<%@page import="java.util.zip.ZipEntry"%>
<%@page import="java.util.zip.ZipOutputStream"%>
<%@page import="com.jointthinker.framework.common.util.JSONutil"%>
<%@page import="com.jointthinker.framework.business.Config"%>
<%@page import="com.jointthinker.framework.web.bean.ViewBean"%>
<%@page import="com.jointthinker.framework.business.BusinessManager"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.File"%>
<%@ page import="org.apache.commons.io.FileUtils"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="net.sf.json.JSONArray" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%


//String business = request.getParameter("business");
String hyid = request.getParameter("hyid");
String hyname = request.getParameter("hyname");
String sql="select DISTINCT b.huizhiid businessId,b.shouwenname businessName from upload_file_info a LEFT JOIN meeting_hynotice b on a.CATEGORYID=b.HUIZHIID and a.CATEGORY='report_file' where b.hyid="+hyid;
BusinessManager manager = new BusinessManager();
StringBuffer buffer=new StringBuffer(sql);
JSONArray businessArray = new JSONArray();
try {
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
	
	ViewBean bean =manager.getViewBean(buffer.toString());
	businessArray = JSONutil.list2json(bean.getLabellist(), bean.getValuelist());
} catch (Exception e) {
	e.printStackTrace();
};
//JSONArray businessArray = JSONArray.fromObject(business);

UUID uuid = UUID.randomUUID();
String tmpDir = System.getProperty("java.io.tmpdir")+File.separator+uuid;
File fdir = new File(tmpDir);

if(fdir.exists()) {
	fdir.delete();
}
fdir.mkdirs();

File zipfileAll = new File(tmpDir+File.separator+hyname+".zip");
ZipOutputStream zipoutAll = new ZipOutputStream(new FileOutputStream(zipfileAll));

for (int j=0;j<businessArray.size();j++){
	String businessId = businessArray.getJSONObject(j).getString("businessid");
	String businessName = businessArray.getJSONObject(j).getString("businessname");
	
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
	
	File zipfile = new File(tmpDir+File.separator+businessName+".zip");
	
	//Config.getProp("upfileRoot")
	byte[] buf = new byte[1024];
	
	String filename=businessName;
	
	try {
	    ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream(zipfile));
	
		String query = "";
		ViewBean vb = null;
	
		query= "select * from Upload_File_Info where category='report_file' and categoryid="+businessId +" order by uploadtime desc ";
		vb=manager.getViewBean(query);
	    
		//List fileList = docMan.findByProperties(new String[]{"businessId"}, new Object[]{businessId});
		for (int i=0;i<vb.getSize();i++){
			//Document doc = (Document)fileList.get(i);
			String oldfile = Config.getProp("upfileRoot")+vb.getViewBeanValue("filepath", i);
			String newfile = tmpDir+File.separator+vb.getViewBeanValue("srcfilename", i);
			
			File from = new File(oldfile);
		    File to = new File(newfile);
		
		    try {
		        FileUtils.copyFile(from, to);
		    } catch (IOException e) {
		        e.printStackTrace();
		        out.println("<script>alert('生成导出包错误，公文文件拷贝失败！');window.close();</script>");
		    	return;
		    }
		    FileInputStream zipin = new FileInputStream(to);
		    zipout.putNextEntry(new ZipEntry(to.getName()));
	        String str = to.getName();
	        int len;
	        while ((len = zipin.read(buf)) > 0) {
	        	zipout.write(buf, 0, len);
	        }
	        zipout.closeEntry();
	        zipin.close();
		}
		
		query="select '' as receive,b.shouwenname as title,b.huizhitime as no,'' as org from meeting_huizhi a left join meeting_hynotice b on a.id = b.huizhiid where a.id="+businessId;
		vb=manager.getViewBean(query);
		filename = vb.getViewBeanValue("no", 0);
		if (vb==null){
			out.println("<script>alert('生成导出包错误，公文信息获取失败！');window.close();</script>");
			return;
		}
		String content = "<?xml version='1.0' encoding='utf-8'?><TranData>"
				+"<DocumentType>"+businessName+"</DocumentType>"
				+"<DocumentNo>"+vb.getViewBeanValue("no", 0)+"</DocumentNo>"
				+"<DocumentTitle>"+vb.getViewBeanValue("title", 0)+"</DocumentTitle>"
				+"<DocumentOrg>"+vb.getViewBeanValue("org", 0)+"</DocumentOrg>"
				+"<DocumentReceive>"+vb.getViewBeanValue("receive", 0)+"</DocumentReceive>"
				+"</TranData>";
		
		FileOutputStream o = new FileOutputStream(tmpDir+File.separator+"信息_"+businessId+".xml");  
		o.write(content.getBytes("UTF-8"));  
		o.close();  
		
	    FileInputStream zipin = new FileInputStream(tmpDir+File.separator+"信息_"+businessId+".xml");
	    zipout.putNextEntry(new ZipEntry("信息_"+businessId+".xml"));
	    int len;
	    while ((len = zipin.read(buf)) > 0) {
	    	zipout.write(buf, 0, len);
	    }
	    //zipout.setEncoding("gbk");
	    zipout.closeEntry();
	    zipin.close();
	
	    zipout.close();
	    System.out.println("压缩完成.");
	    
	    zipoutAll.putNextEntry(new ZipEntry(businessName+".zip"));
	    FileInputStream zipinBAll = new FileInputStream(zipfile);
	    while ((len = zipinBAll.read(buf)) > 0) {
	    	zipoutAll.write(buf, 0, len);
	    }
	    zipinBAll.close();
	    zipoutAll.closeEntry();
	    
	} catch (IOException e) {
	    e.printStackTrace();
		out.println("<script>alert('生成导出包错误，压缩文件创建失败！');window.close();</script>");
	}
}
zipoutAll.close();

// 设置响应头和下载保存的文件名
response.reset();
//response.setContentType("application/octet-stream; charset=UTF-8");       //windows
response.setContentType("application/x-msdownload;");       //windows
response.addHeader("Content-Disposition", "attachment; filename=\"" + uuid +".zip" + "\"");      //windows
//response.setContentType("application/octet-stream; charset=UTF-8");     //linux
//response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(hyname.getBytes("UTF-8"),"iso8859-1") + "\"");      //linux

//新建文件输入输出流
OutputStream output = null;
FileInputStream fis = null;
try{
  //新建文件输入输出流对象
  output = response.getOutputStream();
  fis = new FileInputStream(zipfileAll);
  //设置每次写入缓存大小
  byte[] b = new byte[1024];
  //out.print(f.length());
  //把输出流写入客户端
  int i = 0;
  while((i = fis.read(b)) > 0){
    output.write(b, 0, i);
  }
  output.flush();
}
catch(Exception e){
  e.printStackTrace();
}
finally{
  if(fis != null){
    fis.close();
    fis = null;
  }
  if(output != null){
    output.close();
    output = null;
  }
 out.clear(); 
 out = pageContext.pushBody();
}
 

%>

