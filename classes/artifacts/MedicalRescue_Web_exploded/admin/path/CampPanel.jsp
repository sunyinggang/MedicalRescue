<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
java.util.Random r = new java.util.Random();
String nameSpaceId = "camppath" + r.nextInt();
nameSpaceId = nameSpaceId.replace("-", "");
String id = request.getParameter("id");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'StartPanel.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	var slng,slat,elng,elat;
	Ext.define('admin.path.CampPanel', {
		extend: 'Ext.panel.Panel',
		xtype: 'camppanel',
		layout:'fit',
		html:'<div class="box"><div id="<%=nameSpaceId%>_container" style="height:100%" class="container fl"></div></div>',
		listeners: {  
            'afterrender': function(btn) {
            	// 创建地图实例
              	var map = new BMap.Map("<%=nameSpaceId%>_container");
              	var point = new BMap.Point(116.331398,39.897445);
                map.centerAndZoom(point,15);
                map.enableScrollWheelZoom();
                map.enableContinuousZoom();
                map.addControl(new BMap.NavigationControl());
                
                if(JSON.stringify(accPoint)!= "{}"&&accID!=''){//如果选择了事故  则默认显示当前位置到事故位置的路线
                	
                   
                    Ext.Ajax.request({
    					url:'<%=path%>/accident/getAccidentMap',
    					params:{
    						id:accID
    					},
    					success:function(o){
    						var ret = Ext.decode(o.responseText);
    		            	var accArr = ret.json;
    		            	if(accArr.length>0){
    		            		
    		            		var cpoint = new BMap.Point(accPoint.lng,accPoint.lat);
    		            		map.centerAndZoom(cpoint,15);
    		            		var cmarker= new BMap.Marker(cpoint); // 创建点
    		            		map.addOverlay(cmarker);
    		            		var label1 = new BMap.Label(accArr[0].title,{offset:new BMap.Size(20,-10)});
                    			cmarker.setLabel(label1);
    		            		cmarker.addEventListener("click", function(e){
                        			elng=e.point.lng;
                        			elat=e.point.lat;
                    				var searchInfoWindow1 = new BMapLib.SearchInfoWindow(map,accArr[0].content,{
                        				title  :accArr[0].title,      //标题
                        				enableSendToPhone:false,
                        				enableAutoPan : true,    //自动平移
                        				searchTypes :[
                        				      		]
                        			});
                    				searchInfoWindow1.open(cpoint);
            				    });
    		            	}
    		            
    		            	
    					},
    					failure:function(o){
    						Ext.MessageBox.alert("错误信息","操作有误!");
    					}
    				});

        		}
                
                map.addEventListener("click", function(e){
                	var bool=true;
                    var pt = e.point;
                    slng=pt.lng;
                    slat=pt.lat;
                    var point =new BMap.Point(pt.lng,pt.lat);
                	var myIcon = new BMap.Icon("js/map/css/makert.png", new BMap.Size(30,30));
            		var marker= new BMap.Marker(point ,{icon:myIcon}); // 创建点
            		if(slng!=elng&&slat!=elat){
            			map.addOverlay(marker);
                		var searchInfoWindow = null;
                		var content= '<div class="userSignIw"><div style="z-index:100;height:35px;"><span style="float:left;margin:3px 5px 0 0;">名称</span><input type="text" style="color:#8C8C8C;float:left;width:248px;height:25px;padding:3px 3px;font:12px/1.5 tahoma,arial,宋体;line-height:18px;border:#b5b5b5 solid 1px;" name="title"  id="us_infoWnd_title"><div class="userSignTip hide" id="JuserSignTiptitle"></div></div>'
                		+'<div class="userTagCont"><span style="float:left;margin:3px 5px 0 0;">备注</span><textarea style="border:#b5b5b5 solid 1px;color:#8C8C8C;float:left;font:12px/1.5 tahoma,arial,宋体;height:64px;overflow-y:auto;padding:3px;width:248px" name="content" id="us_infoWnd_content" value="我的备注"></textarea><div class="userSignTip hide" id="JuserSignTipContent"></div></div>'
                		+'<div style="overflow:hidden;clear:both;height:0;"></div>'
                		+'<div style="text-align:right;margin:5px -1px 15px 0;*margin:3px -1px 18px 0;"><input type="button" value="保存" id="btnSignSave" style="cursor:pointer;margin-right:5px;"   class="iw_bt">'
                		+'<input type="button" value="删除" id="btnSignDelete" style="cursor:pointer" class="iw_bt"></div></div>';
                		
                		searchInfoWindow = new BMapLib.SearchInfoWindow(map, content,{
                				title  : "添加标注",      //标题
                				width  : 290,             //宽度
                				enableSendToPhone:false,
                				enableAutoPan : true,    //自动平移
                				searchTypes :[
                				      		]
                			});
                		searchInfoWindow.open(marker);
                		searchInfoWindow.addEventListener('close', function(){
                			if(bool){
                				map.removeOverlay(marker);
                			}
                		})
                		$('#btnSignSave').click(function(){
                			bool=false;
                			var title = $('#us_infoWnd_title').val()==''?'我的标记':$('#us_infoWnd_title').val();
                			var content = $('#us_infoWnd_content').val()==''?'我的备注':$('#us_infoWnd_content').val();
                			searchInfoWindow.close();
                			var label1 = new BMap.Label(title,{offset:new BMap.Size(20,-10)});
                			marker.setLabel(label1);
                			marker.addEventListener("click", function(e){
                    			elng=e.point.lng;
                    			elat=e.point.lat;
                				var searchInfoWindow1 = new BMapLib.SearchInfoWindow(map,content+'<br/><input type="button" value="删除" id="btnDelete" style="float:right;cursor:pointer" class="iw_bt">',{
                    				title  :title,      //标题
                    				width  : 290,             //宽度
                    				enableSendToPhone:false,
                    				enableAutoPan : true,    //自动平移
                    				searchTypes :[
                    				      		]
                    			});
                				searchInfoWindow1.open(new BMap.Point(e.point.lng,e.point.lat));
                				$('#btnDelete').click(function(){
                					searchInfoWindow1.close();
                        			map.removeOverlay(marker);
                        		})
        				    });
                		})
                		$('#btnSignDelete').click(function(){
                			searchInfoWindow.close();
                			map.removeOverlay(marker);
                		})
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
