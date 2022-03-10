 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>用户注册-中国工业强基信息网</title>
        <meta name="description" content=""></meta>
        <meta name="keywords" content=""></meta>
		<link rel="stylesheet" type="text/css" href="../css/public.css" />
		<link rel="stylesheet" type="text/css" href="../css/style2.css" /> 
   <script src="../js/userinfo.js" type="text/javascript"></script>
    </head>
 
 
 
 
 
<body>
<form method="post" action="registerInfo.aspx" name="form1" id="form1" onsubmit="return checkedAll();">
<div class="aspNetHidden">
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="/wEPDwUKLTE5NDc2MTUyNg9kFgICAQ9kFgICAQ9kFgRmDxYCHgVzdHlsZQUZZmxvYXQ6bGVmdDtkaXNwbGF5OmJsb2NrO2QCAQ8WAh8ABRhmbG9hdDpsZWZ0O2Rpc3BsYXk6bm9uZTtkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYCBQlyYWR1c2VyR1IFCXJhZHVzZXJRWXrvfWiSp4uG0v/JbOdKmPr7ezS3yvPgUgK8ya0gxW8D" />
</div> 
<div class="aspNetHidden">
	<input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="/wEWCALqsLqKDwKl1bKiCALG8eCzCgLXjdPNCgLlw/zFCAKKx93RCwKXx5XhAgLhkrW/DEQfY7qBKTBl0Q9BgAha44qIZQqCm6Pw2f3DBZahobaC" />
</div>
 <div class="box2">
        <div class="wrap960">
    		<div class="box-inner2 clearfix">
        		<div class="regist-box">
                  <div class="info-tips" style="font-size:24px; color: #F30;">企业用户密码修改
                    </div>
                    <div class="login-box">
                    	<ul class="InforBox">
                			<!-- 待修改 -->               			 
                			
                			 <li>
                    			<div class="InforTitle InforTitle3 left"><span class="cf60"></span>新密码：</div>
                    			<input name="txtuserPwd" type="password" id="txtuserPwd" class="InforText InforText3 left"  onblur="vcUserPwd()"/>                                           			                   		
                                <span class="txt-sis" id="emUserPwd">密码由6—30个字母、数字或下划线组成</span>  
                			</li>                			                    
                			<li>
                			<div class="InforTitle InforTitle3 left"><span class="cf60"></span>确认新密码：</div>
                    			<input name="txtuserPwdQ" type="password" id="txtuserPwdQ" class="InforText InforText3 left" onblur="vcUserPwdQ()"/>               			
                			    <span class="txt-sis" id="emUserPwdQ">请确保与登录密码设置一致</span>
                			</li>                			                      
                        </ul>
                        
                        <div class="login-opr1">
                            <a onclick="return vcOnClick();" id="btn_OK" class="login-btn">确定</a>
                    </div>
                </div>

        	</div>
        </div>
</div>
</form>
</body>
</html>
