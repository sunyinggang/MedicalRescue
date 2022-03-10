<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename="+request.getAttribute("file");
String realfilename = (String) request.getParameter("realfilename");

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>Playwav.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript"> 
		function getQueryStringRegExp(name) 
		{     
			var reg = new RegExp("(^|\\?|&)"+ name +"=([^&]*)(\\s|&|$)", "i");
			if (reg.test(location.href)) 
			{
//				alert(unescape(RegExp.$2.replace(/\+/g, " ")));
				return unescape(RegExp.$2.replace(/\+/g, " "));
			} 
			return ""; 
		};  
		//http://localhost/test.html?aa=bb&test=cc+dd&ee=ff 
	</script> 

  </head>
  
  <body>
  <table width="100%" >
  <tr>
   </tr>
   <tr>
   </tr>
  <tr >
    <td colspan="4" class="titlebig" align="center">录音话后监听</td>
  </tr>
  <tr>
   </tr>
   <tr>
   </tr>
	  <tr>
	    <td colspan="4" align="center"><p>
		<SCRIPT LANGUAGE="JavaScript">
				var WMP7;
				if(window.ActiveXObject)
				{
				    WMP7 = new ActiveXObject("WMPlayer.OCX.7");
				}
				else if (window.GeckoActiveXObject)
				{
				     WMP7 = new GeckoActiveXObject("WMPlayer.OCX.7");
				}
				// Windows Media Player 7 Code
				if ( WMP7 )
				{
				     document.write ('<OBJECT ID=MediaPlayer ');
				     document.write (' CLASSID=CLSID:6BF52A52-394A-11D3-B153-00C04F79FAA6');
				     document.write (' codebase=http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701');
				     document.write (' standby="Loading Microsoft Windows Media Player components..."');
				     document.write (' TYPE="application/x-oleobject" width="286" height="66">');
				     document.write ('<PARAM NAME="url" VALUE="<%=realfilename%>">');
				     document.write ('<PARAM NAME="AutoStart" VALUE="false">');
				     document.write ('<param name="defaultFrame" value="false"> ');
				     document.write ('<PARAM NAME="ShowControls" VALUE="true">');
				     document.write ('<PARAM NAME="uiMode" VALUE="full">');
				     document.write ('<param name="Loop" value="true">');
				     document.write ('<param name="Enabled" value="true">');
				     document.write ('<param name="enableContextMenu" value="false">');
				     document.write ('</OBJECT>');
				}
				// Windows Media Player 6.4 Code
				else
				{
				     //IE Code
				     document.write ('<OBJECT ID=MediaPlayer ');
				     document.write ('CLASSID=CLSID:22d6f312-b0f6-11d0-94ab-0080c74c7e95 ');
				     document.write ('CODEBASE=http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=6,4,5,715 ');
				     document.write ('standby="Loading Microsoft Windows Media Player components..." ');
				     document.write ('TYPE="application/x-oleobject" width="286" height="66">');
				     document.write ('<PARAM NAME="url" VALUE="<%=realfilename%>">');
				     document.write ('<PARAM NAME="AutoStart" VALUE="false">');
				     document.write ('<PARAM NAME="ShowControls" VALUE="true">');
				     document.write ('<PARAM NAME="uiMode" VALUE="full">');
				     document.write ('<param name="Loop" value="true">');
				     document.write ('<param name="enableContextMenu" value="false">');
				     //Netscape code
				     document.write ('    <Embed type="application/x-mplayer2"');
				     document.write ('        pluginspage="http://www.microsoft.com/windows/windowsmedia/"');
				     document.write ('        filename=url');
				     document.write ('        src=url');
				     document.write ('        Name=MediaPlayer');
				     document.write ('        ShowControls=1');
				     document.write ('        ShowDisplay=1');
				     document.write ('        ShowStatusBar=1');
				     document.write ('        width=290');
				     document.write ('        height=320>');
				     document.write ('    </embed>');
				
				     document.write ('</OBJECT>');
				}
		</SCRIPT></p></td>
	  </tr>
   <tr>
   </tr>
   <tr>
   </tr>
   <tr>
   </tr>
   <tr>
    <td colspan="4" align="center">
    <input type="button" name="Submit" value="关闭窗口" class="button_blue" onclick="window.close();">
    </td>
  </tr>
</table>
</body>
</html:html>
