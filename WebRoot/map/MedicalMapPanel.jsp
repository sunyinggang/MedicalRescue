<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
java.util.Random r = new java.util.Random();
String nameSpaceId = "medicalinfo" + r.nextInt();
nameSpaceId = nameSpaceId.replace("-", "");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MedicalMapPanel.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	Ext.define('map.MedicalMapPanel', {
		extend: 'Ext.panel.Panel',
		xtype: 'medicalmappanel',
		layout:'fit',
		html:'<div class="box"><div id="<%=nameSpaceId%>_container" style="height:100%" class="container fl"></div></div>',
		listeners: {  
            'afterrender': function(btn) { 
            	Ext.Ajax.request({
					url:'<%=path%>/medical/getMedicalMap',
					success:function(o){
						var ret = Ext.decode(o.responseText);
						var points = ret.json;
		            	var bmap = new BaiduMap({
		    				id: "<%=nameSpaceId%>_container",
		    				level: 14, //  选填--地图级别--(默认15)
		    				zoom: true, // 选填--是否启用鼠标滚轮缩放功能--(默认false)
		    				type: ["地图", "卫星", "三维"], // 选填--显示地图类型--(默认不显示)
		    				//width: 320, // 选填--信息窗口width--(默认自动调整)
		    				//height: 70, // 选填--信息窗口height--(默认自动调整)
		    				titleClass: "maptitle",
		    				contentClass: "mapcontent",
		    				showPanorama: false, // 是否显示全景控件(默认false)
		    				showMarkPanorama: false, // 是否显示标注点全景图(默认false)
		    				showLabel: true, // 是否显示文本标注(默认false)
		    				mapStyle: "normal", // 默认normal,可选dark,light
		    				icon: { // 选填--自定义icon图标
		    					url: "js/map/css/medicine.png",
		    					width: 34,
		    					height: 34
		    				},
		    				centerPoint: { // 中心点经纬度
		    					lng: 117.24968,
		    					lat: 39.10976
		    				},
		    				index: 3, // 开启对应的信息窗口，默认-1不开启
		    				animate: true, // 是否开启坠落动画，默认false
		    				points: points,
		    				callback: function(id) {
		    				}
		    			});
		            	new mp4Popup("js/mp4/5.mp4",2);
					},
					failure:function(o){
						Ext.MessageBox.alert("错误信息","操作有误!");
					}
				});
            	

            }
        }
	});
	</script>
  </head>
  
  <body>
    
  </body>
</html>
