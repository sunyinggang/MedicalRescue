<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
java.util.Random r = new java.util.Random();
String nameSpaceId = "startpath" + r.nextInt();
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
	var startmap,startpoint,endpoint;
	var inputval=0;
	var routePolicy = [BMAP_DRIVING_POLICY_LEAST_TIME,BMAP_DRIVING_POLICY_LEAST_DISTANCE,BMAP_DRIVING_POLICY_AVOID_HIGHWAYS];
	Ext.define('admin.path.StartPanel', {
		extend: 'Ext.panel.Panel',
		xtype: 'startpanel',
		layout:'fit',
		items:[{
			xtype:'form',
			layout: 'absolute',
			width: '100%',
		    height: '100%',
		    items:[{
		        	layout:'fit',
			        frame: true,
			        height: windowHeight-140,
			        html:'<div class="box"><div id="<%=nameSpaceId%>_container" style="height:100%" class="container fl"></div></div>'
		        },
		        {
		        	xtype:'panel',
		        	x:5,
		        	y:5,
		        	width:300,
		        	items:[{
		        		xtype:'form',
		        		defaults: {
			    			margin: '5',
			    			anchor: '100%'
			    		},
		        		items:[{
		        			xtype:'textfield',
		        			name:'startname',
		        			triggers: {
                                mytrigger: {
                                	cls:"x-fa fa-circle-o green"

                                }
                            },
                            id:'<%=nameSpaceId%>_startinput',
                            enableKeyEvents:true,
		        			emptyText: '请输入起点或在图区上选点',
		        			listeners: {
		        				focus:function(ep, event, eOpts ){
		        					inputval=1;
		        				},
		        				blur:function(ep, event, eOpts ){
		        					inputval=0;
		        				},
		        				change:function(ep,newvalue,oldvalue,opt){
		        					var form = ep.up('form');
		        					var q = new BMap.Autocomplete({
		        			            input: '<%=nameSpaceId%>_startinput',
		        			            location: startmap
		        			        });
		        			        q.setInputValue(ep.getValue());
		        			        q.addEventListener("onconfirm",
		        			                function(v) {
		        			        	 		var elm = Array.prototype.slice.call(document.getElementsByClassName('tangram-suggestion-main'));
			        			                elm.forEach(function (v, i) {
			        			                    v.style.zIndex = -1;
			        			                    v.style.visibility = 'hidden';
			        			                }); 
		        			                    var t = v.item.value;
		        			                    myValue = t.province + t.city + t.district + t.street + t.business;
		        			                    form.getForm().findField("startname").setValue(myValue);
		        			                    function s() {
		        			                        var w = u.getResults().getPoi(0).point;
		        			                        startmap.centerAndZoom(w, 15);
		        			                        startmap.clearOverlays();
		        		                        	startpoint =new BMap.Point(w.lng, w.lat);
		        		                        	var myIcon = new BMap.Icon("js/map/css/start.png", new BMap.Size(30,30));
		        		                    		var markerstart = new BMap.Marker(startpoint ,{icon:myIcon}); // 创建点
		        		                    		startmap.addOverlay(markerstart); 
		        		                    		if(endpoint){
		        		                        		var markerend = new BMap.Marker(endpoint ,{icon:new BMap.Icon("js/map/css/end.png", new BMap.Size(30,30))}); // 创建点
		        		                        		startmap.addOverlay(markerend);
		        		                        		ep.up('form').down('button').click();
		        		                    		}
		        			                    }
		        			                    
		        			                    var u = new BMap.LocalSearch(startmap, {
		        			                        onSearchComplete: s
		        			                    });
		        			                    u.search(myValue)
		        			                })
		        				}
		        			}
		        		},{
		        			xtype:'textfield',
		        			name:'endname',
		        			emptyText: '请输入终点或在图区上选点',
		        			triggers: {
                                mytrigger: {
                                	cls:"x-fa fa-circle-o red"

                                }
                            },
                            id:'<%=nameSpaceId%>_endinput',
		        			listeners: {
		        				focus:function(ep, event, eOpts ){
		        					inputval=2;
		        				},
		        				blur:function(ep, event, eOpts ){
		        					inputval=0;
		        				},
		        				change:function(ep,newvalue,oldvalue,opt){
		        					var form = ep.up('form');
		        					var q = new BMap.Autocomplete({
		        			            input: '<%=nameSpaceId%>_endinput',
		        			            location: startmap
		        			        });
		        			        q.setInputValue(ep.getValue());
		        			        q.addEventListener("onconfirm",
		        			                function(v) {
		        			        	 		var elm = Array.prototype.slice.call(document.getElementsByClassName('tangram-suggestion-main'));
			        			                elm.forEach(function (v, i) {
			        			                    v.style.zIndex = -1;
			        			                    v.style.visibility = 'hidden';
			        			                }); 
		        			                    var t = v.item.value;
		        			                    myValue = t.province + t.city + t.district + t.street + t.business;
		        			                    form.getForm().findField("endname").setValue(myValue);
		        			                    function s() {
		        			                        var w = u.getResults().getPoi(0).point;
		        			                        startmap.centerAndZoom(w, 15);
		        			                        startmap.clearOverlays();
		        		                        	endpoint =new BMap.Point(w.lng, w.lat);
		        		                        	var myIcon = new BMap.Icon("js/map/css/end.png", new BMap.Size(30,30));
		        		                    		var markerstart = new BMap.Marker(endpoint ,{icon:myIcon}); // 创建点
		        		                    		startmap.addOverlay(markerstart);  
		        		                    		if(startpoint){
		        		                        		var markerstart = new BMap.Marker(startpoint ,{icon:new BMap.Icon("js/map/css/start.png", new BMap.Size(30,30))}); // 创建点
		        		                        		startmap.addOverlay(markerstart);
		        		                        		ep.up('form').down('button').click();
		        		                    		}
		        			                    }
		        			                    
		        			                    var u = new BMap.LocalSearch(startmap, {
		        			                        onSearchComplete: s
		        			                    });
		        			                    u.search(myValue)
		        			                })
		        				}
		        				
		        			}
		        		},{
		        			xtype: 'radiogroup',
		        			name: 'driving_way',
	        	            items: [
	        	                {boxLabel: '最少时间',inputValue: '0' ,checked: true},
	        	                {boxLabel: '最短距离',inputValue: '1'},
	        	                {boxLabel: '避开高速',inputValue: '2'}
	        	            ]
		        		},{
		        			xtype: 'button',
	        	            text: '查询',
	        	            handler:function(btn){
	        	            	Ext.MessageBox.show({
	        	                    msg: '正在规划路线...',
	        	                    progressText: '提示',
	        	                    width: 300,
	        	                    wait: {
	        	                        interval: 200
	        	                    }
	        	                }); 
	        	            	startmap.clearOverlays();
	        	            	var form = btn.up('form');
	        	            	 var i = form.getForm().findField("driving_way").getValue().driving_way;
	        	            	 //根据坐标查  无坐标根据地址查
	        	            	 if(startpoint&&endpoint){
	        	            		 var driving = new BMap.DrivingRoute(startmap, {    
    	        	                     renderOptions: {    
    	        	                         map: startmap,    
    	        	                         autoViewport: true    
    	        	                     },
    	        	                     policy: routePolicy[i],
    	        	                     onSearchComplete:function(){
    	        	                    	 Ext.MessageBox.hide();
    	        	                     }
    	        	                 }); 
	        	            		 driving.search(startpoint,endpoint);
	        	            		 
	        	            	 }else{
	        	            		 var startname = form.getForm().findField("startname").getValue();
	        	            		 var endname = form.getForm().findField("endname").getValue();
	        	            		 if(startname!=''&&endname!=''){
	        	            			 startmap.clearOverlays();
	        	            			 var driving = new BMap.DrivingRoute(startmap, {    
	    	        	                     renderOptions: {    
	    	        	                         map: startmap,    
	    	        	                         autoViewport: true    
	    	        	                     },
	    	        	                     policy: routePolicy[i],
	    	        	                     onSearchComplete:function(){
	    	        	                    	 Ext.MessageBox.hide();
	    	        	                     }
	    	        	                 }); 
	        	            			 driving.search(startname,endname);
	        	            		 }
	        	            	 }
	        	            	
	        	            }
		        		}]
		        	}]
		        }
		    ]

		}],
		listeners: {  
            'afterrender': function(btn) {
            	Ext.MessageBox.show({
                    msg: '正在加载...',
                    progressText: '提示',
                    width: 300,
                    wait: {
                        interval: 200
                    }
                });
            	var form = btn.down('form');
            	// 创建地图实例
              	startmap = new BMap.Map("<%=nameSpaceId%>_container");
                var point = new BMap.Point(116.331398,39.897445);
                startmap.centerAndZoom(point,12);
                startmap.enableScrollWheelZoom();
                startmap.enableContinuousZoom();
                startmap.addControl(new BMap.NavigationControl());
                var driving = new BMap.DrivingRoute(startmap, {    
                    renderOptions: {    
                        map: startmap,    
                        autoViewport: true    
                    }    
                });    
                var geoc = new BMap.Geocoder();    
                var geolocation = new BMap.Geolocation();
                geolocation.getCurrentPosition(function(r){
                	if(this.getStatus() == BMAP_STATUS_SUCCESS){
                		// 根据坐标得到地址描述    
                		startpoint =new BMap.Point(r.point.lng, r.point.lat);
                		geoc.getLocation(new BMap.Point(r.point.lng, r.point.lat), function(result){      
                		    if (result){   
                		    	form.getForm().findField("startname").setValue(result.address);
                		    	if(JSON.stringify(accPoint) != "{}"){//如果选择了事故  则默认显示当前位置到事故位置的路线
                        			geoc.getLocation(new BMap.Point(accPoint.lng,accPoint.lat), function(rs){
                                        var addComp = rs.addressComponents;
                                        form.getForm().findField("endname").setValue(addComp.province + addComp.city + addComp.district + addComp.street  + addComp.streetNumber);
                                        endpoint =new BMap.Point(rs.point.lng, rs.point.lat);
                                        var driving = new BMap.DrivingRoute(startmap, {    
                   	                     	renderOptions: {    
                   	                         map: startmap,    
                   	                         autoViewport: true    
            	      	                    },
            	      	                  	policy: routePolicy[0],
	     	        	                    onSearchComplete:function(){
	     	        	                    	Ext.MessageBox.hide();
	     	        	                    }
            	      	                 }); 
            	  	            		 driving.search(startpoint,endpoint);
                        			})
                        			
                        		}else{
                        			var myIcon = new BMap.Icon("js/map/css/start.png", new BMap.Size(30,30));
                            		var markerstart = new BMap.Marker(startpoint ,{icon:myIcon}); // 创建点
                            		startmap.addOverlay(markerstart);  
                        			startmap.centerAndZoom(startpoint,12);
                        			Ext.MessageBox.hide();
                        		}
                		    }      
                		});
                		
                	}
                	else {
                		alert('failed'+this.getStatus());
                	}        
                }); 
               
				startmap.addEventListener("click", function(e){        
                    var pt = e.point;
                    geoc.getLocation(pt, function(rs){
                    	var elm = Array.prototype.slice.call(document.getElementsByClassName('tangram-suggestion-main'));
		                elm.forEach(function (v, i) {
		                    v.style.zIndex = -1;
		                    v.style.visibility = 'hidden';
		                });
                        var addComp = rs.addressComponents;
                        if(inputval==1){
                        	startmap.clearOverlays();
                        	form.getForm().findField("startname").setValue(addComp.province + addComp.city + addComp.district + addComp.street  + addComp.streetNumber);
                        	startpoint =new BMap.Point(rs.point.lng, rs.point.lat);
                        	var myIcon = new BMap.Icon("js/map/css/start.png", new BMap.Size(30,30));
                    		var markerstart = new BMap.Marker(startpoint ,{icon:myIcon}); // 创建点
                    		startmap.addOverlay(markerstart);
                    		if(endpoint){
                        		var markerend = new BMap.Marker(endpoint ,{icon:new BMap.Icon("js/map/css/end.png", new BMap.Size(30,30))}); // 创建点
                        		startmap.addOverlay(markerend);  
                        		btn.down('button').click();
                    		}
                        }
                        if(inputval==2){
                        	form.getForm().findField("endname").setValue(addComp.province + addComp.city + addComp.district + addComp.street  + addComp.streetNumber);
                    		startmap.clearOverlays();
                        	endpoint =new BMap.Point(rs.point.lng, rs.point.lat);
                        	var myIcon = new BMap.Icon("js/map/css/end.png", new BMap.Size(30,30));
                    		var markerend = new BMap.Marker(endpoint ,{icon:myIcon}); // 创建点
                    		startmap.addOverlay(markerend); 
                    		if(startpoint){
                        		var markerstart = new BMap.Marker(startpoint ,{icon:new BMap.Icon("js/map/css/start.png", new BMap.Size(30,30))}); // 创建点
                        		startmap.addOverlay(markerstart);  
                        		btn.down('button').click();
                    		}
                        }
                       
                    });        
                });
               

            }
        }
	    
	});
	</script>
  </head>
  
  <body>
  </body>
</html>
