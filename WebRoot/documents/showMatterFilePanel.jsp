<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
java.util.Random r = new java.util.Random();
String nameSpaceId = "Matterinfo" + r.nextInt();
nameSpaceId = nameSpaceId.replace("-", "");
System.out.println(basePath);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'showRanksFielPanel.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	Ext.define('documents.showMatterFilePanel', {
		extend: 'Ext.panel.Panel',
		xtype: 'showmatterfilepanel',
		layout:'fit',
		items:[{
			 html: '<iframe src="documents/3.pdf" width="100%" height="100%"></iframe>'
		}],
		bbar: {
	        items: ['->', {
	            text:'下载',
	            handler: function(){
	        		var form = $("<form>");
	                form.attr('style','display:none');
	                form.attr('target','');
	                form.attr('method','post');
	                form.attr('action','upload/downloadServerfile?name=3.doc');
	                $("body").append(form);    
	                form.submit();    
	                form.remove();  
	            	
	            }
	        },{
	        	 text: '关闭',
	        	 handler: function(){
	        		 var curPanel = contentPanel.getActiveTab();
					 curPanel.destroy();//调用销毁 
	        	 }
	        },'->']
	    }
	});
	</script>
  </head>
  
  <body>
    
  </body>
</html>
