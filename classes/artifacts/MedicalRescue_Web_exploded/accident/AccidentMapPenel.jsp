<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
java.util.Random r = new java.util.Random();
String nameSpaceId = "accidentmap" + r.nextInt();
nameSpaceId = nameSpaceId.replace("-", "");
String id = request.getParameter("id");
String pageflag = request.getParameter("pageflag");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'accidentMapPanel.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	var bmap;
	var rPolicy = [BMAP_DRIVING_POLICY_LEAST_TIME,BMAP_DRIVING_POLICY_LEAST_DISTANCE,BMAP_DRIVING_POLICY_AVOID_HIGHWAYS];
	
	//取当前浏览器窗口大小
	var windowWidth=$(window).width();
	var windowHeight=$(window).height();
	Ext.define('accident.AccidentMapPenel', {
		extend: 'Ext.panel.Panel',
		xtype: 'accidentmappanel',
		layout:'fit',
		items:[{
			xtype:'form',
			layout: 'absolute',
			width: '100%',
		    height: '100%',
		    items:[{
		        	layout:'fit',
			        frame: true,
			        id:'<%=nameSpaceId%>_map',
			        height: windowHeight-140,
			        html:'<div class="box"><div id="treemenu_isaccident_container" style="height:100%" class="container fl"></div></div>',
		        },{
		        	xtype:'panel',
		        	id:'checkpanel',
		        	width:150,
		        	items:[{
		        		xtype:'form',
		        		defaults: {
			    			margin: '5',
			    			anchor: '100%'
			    		},
			    		items:[{
			    			xtype:'checkbox',
			        		boxLabel:'医疗资源分布',
			        		name:'medical_checkbox',
			        		listeners: {
			        			change:function( e, newValue, oldValue, eOpts ){
			        				showOverlay('medical',newValue);
			        			}
			        		}
			        	},{
			        		xtype:'checkbox',
			        		boxLabel:'开进路径选择',
			        		name:'start_path',
			        		listeners: {
			        			change:function( e, newValue, oldValue, eOpts ){
			        				if(newValue){
			        					Ext.getCmp('<%=nameSpaceId%>_startpanel').show();
			        					Ext.getCmp('<%=nameSpaceId%>_startform').show();
			        					var form = Ext.getCmp('<%=nameSpaceId%>_startform');
			        					if(startmapStartPoint==null){
			        						Ext.MessageBox.show({
				        	                    msg: '正在规划路线...',
				        	                    progressText: '提示',
				        	                    width: 300,
				        	                    wait: {
				        	                        interval: 200
				        	                    }
				        	                });  
			        						var geoc = new BMap.Geocoder();    
				        	                var geolocation = new BMap.Geolocation();
				        	                geolocation.getCurrentPosition(function(r){
				        	                	if(this.getStatus() == BMAP_STATUS_SUCCESS){
				        	                		// 根据坐标得到地址描述    
				        	                		var startpoint =new BMap.Point(r.point.lng, r.point.lat);
				        	                		geoc.getLocation(new BMap.Point(r.point.lng, r.point.lat), function(result){      
				        	                		    if (result){   
				        	                		    	form.getForm().findField("startname").setValue(result.address);
				        	                		    	var myIcon = new BMap.Icon("js/map/css/start.png", new BMap.Size(30,30));
			        	                            		var markerstart = new BMap.Marker(startpoint ,{icon:myIcon}); // 创建点
			        	                            		startmapStartPoint=markerstart;
				        	                		    	if(JSON.stringify(accPoint) != "{}"){//如果选择了事故  则默认显示当前位置到事故位置的路线
				        	                        			geoc.getLocation(new BMap.Point(accPoint.lng,accPoint.lat), function(rs){
				        	                                        var addComp = rs.addressComponents;
				        	                                        form.getForm().findField("endname").setValue(addComp.province + addComp.city + addComp.district + addComp.street  + addComp.streetNumber);
				        	                                        var endpoint =new BMap.Point(rs.point.lng, rs.point.lat);
				        	                                        var myIcon = new BMap.Icon("js/map/css/start.png", new BMap.Size(30,30));
					        	                            		var markerend = new BMap.Marker(endpoint ,{icon:myIcon}); // 创建点
					        	                            		startmapEndPoint=markerend;
					        	                            		Ext.getCmp('<%=nameSpaceId%>_startform').down('button').click();
				        	                        			})
				        	                        			
				        	                        		}else{
				        	                            		bmap.addOverlay(markerstart);  
				        	                        			//startmap.centerAndZoom(startpoint,12);
				        	                        			Ext.MessageBox.hide();
				        	                        		}
				        	                		    }      
				        	                		});
				        	                		
				        	                	}
				        	                	else {
				        	                		alert('failed'+this.getStatus());
				        	                	}        
				        	                }); 
			        					}else if(startmapStartPoint!=null&&startmapEndPoint!=null){
			        						Ext.getCmp('<%=nameSpaceId%>_startform').down('button').click();
			        					}else{
			        						if(startmapStartPoint!=null){
				        						startmapStartPoint.show();
					            			}
				        					if(startmapEndPoint!=null){
				        						startmapEndPoint.show();
					            			}
			        					} 
			        					
			        					
			        					
			        						
			        					
			        					
			        				}else{
			        					Ext.getCmp('<%=nameSpaceId%>_startform').hide();
			        					
			        					if(Ext.getCmp('<%=nameSpaceId%>_endform').hidden){
			        						Ext.getCmp('<%=nameSpaceId%>_startpanel').hide();
			        					}
			        					if(startmapStartPoint!=null){
			        						startmapStartPoint.hide();
				            			}
			        					if(startmapEndPoint!=null){
			        						startmapEndPoint.hide();
				            			}
			        					var d = bmap.getDriving();	
			        					d.clearResults()
			        				}
			        			}
			        		}
			        	},{
			        		xtype:'checkbox',
			        		boxLabel:'营地选择',
			        		id:'camp_checkbox',
			        		name:'camp_checkbox',
			        		listeners: {
			        			change:function( e, newValue, oldValue, eOpts ){
			        				showOverlay('camp',newValue);
			        			}
			        		}
			        	},{
			        		xtype:'checkbox',
			        		boxLabel:'后进路径选择',
			        		name:'end_path',
			        		listeners: {
			        			change:function( e, newValue, oldValue, eOpts ){
			        				if(newValue){
			        					Ext.getCmp('<%=nameSpaceId%>_startpanel').show();
			        					Ext.getCmp('<%=nameSpaceId%>_endform').show();
			        					var form = Ext.getCmp('<%=nameSpaceId%>_endform');
			        					if(endmapStartPoint==null||endmapEndPoint==null){
			        						var geoc = new BMap.Geocoder();    
				        	                var geolocation = new BMap.Geolocation();
				        	                if(JSON.stringify(accPoint) != "{}"){//如果选择了事故  则默认显示事故位置为开始路径
				        	        			geoc.getLocation(new BMap.Point(accPoint.lng,accPoint.lat), function(rs){
				        	                        var addComp = rs.addressComponents;
				        	                        form.getForm().findField("startname").setValue(addComp.province + addComp.city + addComp.district + addComp.street  + addComp.streetNumber);
				        	                        var startp =new BMap.Point(rs.point.lng, rs.point.lat);
				        	                        var myIcon = new BMap.Icon("js/map/css/start.png", new BMap.Size(30,30));
				        	                		var markerstart = new BMap.Marker(startp ,{icon:myIcon}); // 创建点
				        	                		bmap.addOverlay(markerstart);  
				        	            			//endmap.centerAndZoom(startp,12);
				        	            			endmapStartPoint=markerstart;
				        	        			})
				        	        			
				        	        		}else{//否则默认当前位置
				        	                    geolocation.getCurrentPosition(function(r){
				        	                    	if(this.getStatus() == BMAP_STATUS_SUCCESS){
				        	                    		// 根据坐标得到地址描述    
				        	                    		var startp =new BMap.Point(r.point.lng, r.point.lat);
				        	                    		geoc.getLocation(new BMap.Point(r.point.lng, r.point.lat), function(result){      
				        	                    		    if (result){   
				        	                    		    	form.getForm().findField("startname").setValue(result.address);
				        	                    		    	var myIcon = new BMap.Icon("js/map/css/start.png", new BMap.Size(30,30));
				        	                            		var markerstart = new BMap.Marker(startp ,{icon:myIcon}); // 创建点
				        	                            		bmap.addOverlay(markerstart);  
				        	                            		endmapStartPoint=markerstart;
				        	                        			//endmap.centerAndZoom(startp,12);
				        	                    		    }      
				        	                    		});
				        	                    		
				        	                    	}
				        	                    	else {
				        	                    		alert('failed'+this.getStatus());
				        	                    	}        
				        	                    }); 
				        	        		}
				        	                if(JSON.stringify(medicalPoint) != "{}"){//如果选择了医疗资源  则默认显示位置为结束路径
				        	        			geoc.getLocation(new BMap.Point(medicalPoint.lng,medicalPoint.lat), function(rs){
				        	                        var addComp = rs.addressComponents;
				        	                        form.getForm().findField("endname").setValue(addComp.province + addComp.city + addComp.district + addComp.street  + addComp.streetNumber);
				        	                        var endp =new BMap.Point(rs.point.lng, rs.point.lat);
				        	                        var myIcon = new BMap.Icon("js/map/css/end.png", new BMap.Size(30,30));
				        	                		var markerstart = new BMap.Marker(endp ,{icon:myIcon}); // 创建点
				        	                		endmapEndPoint=markerstart;
				        	                		bmap.addOverlay(markerstart); 
				        	            			Ext.getCmp('<%=nameSpaceId%>_endform').down('button').click();
				        	        			})
				        	        			
				        	        		}
			        					}else if(endmapStartPoint!=null&&endmapEndPoint!=null){
			        						Ext.getCmp('<%=nameSpaceId%>_endform').down('button').click();
			        					}else{
			        						if(endmapStartPoint!=null){
				        						endmapStartPoint.show();
					            			}
				        					if(endmapEndPoint!=null){
				        						endmapEndPoint.show();
					            			}
			        					} 
			        					
			        					
			        					
			        				}else{
			        					Ext.getCmp('<%=nameSpaceId%>_endform').hide();
			        					if(Ext.getCmp('<%=nameSpaceId%>_startform').hidden){
			        						Ext.getCmp('<%=nameSpaceId%>_startpanel').hide();
			        					}
			        					if(endmapStartPoint!=null){
			        						endmapStartPoint.hide();
				            			}
			        					if(endmapEndPoint!=null){
			        						endmapEndPoint.hide();
				            			}
			        					var d = bmap.getEDriving();	
			        					d.clearResults()
			        				}
			        			}
			        		}
			        	},{
			        		xtype:'checkbox',
			        		boxLabel:'救援协助分布',
			        		name:'rescue',
			        		listeners: {
			        			change:function( e, newValue, oldValue, eOpts ){
			        				showOverlay('rescue',newValue);
			        			}
			        		}
			        	}]
		        	}]
		        },
		        {
		        	xtype:'panel',
		        	x:5,
		        	id:'<%=nameSpaceId%>_startpanel',
		        	y:5,
		        	hidden:true,
		        	width:300,
		        	items:[{
		        		xtype:'form',
		        		id:'<%=nameSpaceId%>_startform',
		        		hidden:true,
		        		defaults: {
			    			margin: '5',
			    			anchor: '100%'
			    		},
		        		items:[{
		        			xtype:'panel',
		        			html:'<span style="color: #4c82b1">前进路径>></span>'
		        		},{
		        			margin: '0 5 5 5',
		        			xtype:'textfield',
		        			name:'startname',
		        			triggers: {
                                mytrigger: {
                                	cls:"x-fa fa-circle-o green"

                                }
                            },
                            id:'startmapStartPoint',
                            enableKeyEvents:true,
		        			emptyText: '请输入起点或在图区上选点',
		        			listeners: {
		        				focus:function(ep, event, eOpts ){
		        					PointFlag='startmapStartPoint';
		        				},
		        				blur:function(ep, event, eOpts ){
		        					PointFlag='';
		        				},
		        				change:function(ep,newvalue,oldvalue,opt){
		        					var form = ep.up('form');
		        					var q = new BMap.Autocomplete({
		        			            input: 'startmapStartPoint',
		        			            location: bmap
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
		        			                        //bmap.centerAndZoom(w, 15);
		        			                        //bmap.clearOverlays();
		        		                        	var startp =new BMap.Point(w.lng, w.lat);
		        		                        	var myIcon = new BMap.Icon("js/map/css/start.png", new BMap.Size(30,30));
		        		                    		var markerstart = new BMap.Marker(startp ,{icon:myIcon}); // 创建点
		        		                    		startmapStartPoint=markerstart;
		        		                    		bmap.addOverlay(markerstart); 
		        		                    		if(startmapEndPoint){
		        		                        		bmap.addOverlay(startmapEndPoint);
		        		                        		ep.up('form').down('button').click();
		        		                    		}
		        			                    }
		        			                    
		        			                    var u = new BMap.LocalSearch(bmap.getMap(), {
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
                            id:'startmapEndPoint',
		        			listeners: {
		        				focus:function(ep, event, eOpts ){
		        					PointFlag='startmapEndPoint';
		        				},
		        				blur:function(ep, event, eOpts ){
		        					PointFlag='';
		        				},
		        				change:function(ep,newvalue,oldvalue,opt){
		        					var form = ep.up('form');
		        					var q = new BMap.Autocomplete({
		        			            input: 'startmapEndPoint',
		        			            location: bmap
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
		        			                        //bmap.centerAndZoom(w, 15);
		        			                        //bmap.clearOverlays();
		        		                        	var endp =new BMap.Point(w.lng, w.lat);
		        		                        	var myIcon = new BMap.Icon("js/map/css/end.png", new BMap.Size(30,30));
		        		                    		var markerstart = new BMap.Marker(endp ,{icon:myIcon}); // 创建点
		        		                    		startmapEndPoint=markerstart;
		        		                    		bmap.addOverlay(markerstart);  
		        		                    		if(startmapStartPoint){
		        		                        		bmap.addOverlay(startmapStartPoint);
		        		                        		ep.up('form').down('button').click();
		        		                    		}
		        			                    }
		        			                    
		        			                    var u = new BMap.LocalSearch(bmap.getMap(),{
		        			                		onSearchComplete:s
		        			                	});
		        			                    u.search(myValue)
		        			                })
		        				}
		        				
		        			}
		        		},{
		        			xtype: 'radiogroup',
		        			name: 'driving_way',
		        			id:'start_driving_way',
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
	        	            	 var form = btn.up('form');
	        	            	 var i = form.getForm().findField("driving_way").getValue().driving_way;
	        	            	 //根据坐标查  无坐标根据地址查
	        	            	 if(startmapStartPoint&&startmapEndPoint){
									 var d = bmap.getDriving();	        	
									 d.setPolicy(rPolicy[i]);
	        	            		 d.search(startmapStartPoint.point,startmapEndPoint.point,{
		    			            	 onSearchComplete:function(){
		    		                    	 Ext.MessageBox.hide();
		    		                    	 bmap.removeOverlay(startmapStartPoint);
			        	            		 bmap.removeOverlay(startmapEndPoint);
			        	            		 PointFlag='';
		    		                     }
	        	            		 });
	        	            		
	        	            		 
	        	            	 }else{
	        	            		 var startname = form.getForm().findField("startname").getValue();
	        	            		 var endname = form.getForm().findField("endname").getValue();
	        	            		 if(startname!=''&&endname!=''){
	        	            			 
	        	            			 var d = bmap.getDriving();	        	
										 d.setPolicy(rPolicy[i]);
		        	            		 d.search(startname,endname,{
			    			            	 onSearchComplete:function(){
			    		                    	 Ext.MessageBox.hide();
			    		                    	 if(startmapStartPoint!=null){
			    		                    		 bmap.removeOverlay(startmapStartPoint);
			    		                    	 }
			    		                    	 if(startmapEndPoint!=null){
			    		                    		 bmap.removeOverlay(startmapEndPoint);
			    		                    	 }
			    		                    	 PointFlag='';
				        	            		 
			    		                     }
		        	            		 });
	        	            		 }
	        	            	 }
	        	            	
	        	            }
		        		}]
		        	},{
		        		xtype:'form',
		        		hidden:true,
		        		id:'<%=nameSpaceId%>_endform',
		        		defaults: {
			    			margin: '5',
			    			anchor: '100%'
			    		},
		        		items:[{
		        			xtype:'panel',
		        			html:'<span style="color: red">后进路径>></span>' 
		        		},{
		        			margin: '0 5 5 5',
		        			xtype:'textfield',
		        			name:'startname',
		        			triggers: {
                                mytrigger: {
                                	cls:"x-fa fa-circle-o green"

                                }
                            },
                            id:'endmapStartPoint',
                            enableKeyEvents:true,
		        			emptyText: '请输入起点或在图区上选点',
		        			listeners: {
		        				focus:function(ep, event, eOpts ){
		        					PointFlag='endmapStartPoint';
		        				},
		        				blur:function(ep, event, eOpts ){
		        					PointFlag='';
		        				},
		        				change:function(ep,newvalue,oldvalue,opt){
		        					var form = ep.up('form');
		        					var q = new BMap.Autocomplete({
		        			            input: 'endmapStartPoint',
		        			            location: bmap
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
		        			                        //bmap.centerAndZoom(w, 15);
		        			                        //bmap.clearOverlays();
		        		                        	var startp =new BMap.Point(w.lng, w.lat);
		        		                        	var myIcon = new BMap.Icon("js/map/css/start.png", new BMap.Size(30,30));
		        		                    		var markerstart = new BMap.Marker(startp ,{icon:myIcon}); // 创建点
		        		                    		endmapStartPoint=markerstart;
		        		                    		bmap.addOverlay(markerstart); 
		        		                    		if(endmapEndPoint){
		        		                        		bmap.addOverlay(endmapEndPoint);
		        		                        		ep.up('form').down('button').click();
		        		                    		}
		        			                    }
		        			                    
		        			                    var u = new BMap.LocalSearch(bmap.getMap(), {
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
                            id:'endmapEndPoint',
		        			listeners: {
		        				focus:function(ep, event, eOpts ){
		        					PointFlag='endmapEndPoint';
		        				},
		        				blur:function(ep, event, eOpts ){
		        					PointFlag='';
		        				},
		        				change:function(ep,newvalue,oldvalue,opt){
		        					var form = ep.up('form');
		        					var q = new BMap.Autocomplete({
		        			            input: 'endmapEndPoint',
		        			            location: bmap
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
		        			                        //bmap.centerAndZoom(w, 15);
		        			                        //bmap.clearOverlays();
		        		                        	var endp =new BMap.Point(w.lng, w.lat);
		        		                        	var myIcon = new BMap.Icon("js/map/css/end.png", new BMap.Size(30,30));
		        		                    		var markerstart = new BMap.Marker(endp ,{icon:myIcon}); // 创建点
		        		                    		endmapEndPoint=markerstart;
		        		                    		bmap.addOverlay(markerstart);  
		        		                    		if(endmapStartPoint){
		        		                        		bmap.addOverlay(endmapStartPoint);
		        		                        		ep.up('form').down('button').click();
		        		                    		}
		        			                    }
		        			                    
		        			                    var u = new BMap.LocalSearch(bmap.getMap(),{
		        			                		onSearchComplete:s
		        			                	});
		        			                    u.search(myValue)
		        			                })
		        				}
		        				
		        			}
		        		},{
		        			xtype: 'radiogroup',
		        			name: 'driving_way',
		        			id:'end_driving_way',
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
	        	            	 var form = btn.up('form');
	        	            	 var i = form.getForm().findField("driving_way").getValue().driving_way;
	        	            	 //根据坐标查  无坐标根据地址查
	        	            	 if(endmapStartPoint&&endmapEndPoint){
									 var d = bmap.getEDriving();	        	
									 d.setPolicy(rPolicy[i]);
	        	            		 d.search(endmapStartPoint.point,endmapEndPoint.point,{
		    			            	 onSearchComplete:function(){
		    		                    	 Ext.MessageBox.hide();
		    		                    	 bmap.removeOverlay(endmapStartPoint);
		    		                    	 bmap.removeOverlay(endmapEndPoint);
			        	            		 PointFlag='';
		    		                     }
	        	            		 });
	        	            		
	        	            		 
	        	            	 }else{
	        	            		 var startname = form.getForm().findField("startname").getValue();
	        	            		 var endname = form.getForm().findField("endname").getValue();
	        	            		 if(startname!=''&&endname!=''){
	        	            			 
	        	            			 var d = bmap.getEDriving();	        	
										 d.setPolicy(rPolicy[i]);
		        	            		 d.search(startname,endname,{
			    			            	 onSearchComplete:function(){
			    		                    	 Ext.MessageBox.hide();
			    		                    	 if(endmapStartPoint!=null){
			    		                    		 bmap.removeOverlay(endmapStartPoint);
			    		                    	 }
			    		                    	 if(endmapEndPoint!=null){
			    		                    		 bmap.removeOverlay(endmapEndPoint);
			    		                    	 }
			    		                    	 PointFlag='';
				        	            		 
			    		                     }
		        	            		 });
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
            	$(window).resize(function() { 
            		if(Ext.getCmp('<%=nameSpaceId%>_map')){
            			Ext.getCmp('<%=nameSpaceId%>_map').setHeight($(window).height()-140);
            		}
            		
            	 });
            	var cenp = { // 中心点经纬度
    					lng: 117.24968,
    					lat: 39.10976
    				};
            	if(<%=id!=null%>){
            		Ext.Ajax.request({
    					url:'<%=path%>/accident/getAccidentMap',
    					params:{
    						id:'<%=id%>'
    					},
    					success:function(o){
    						var ret = Ext.decode(o.responseText);
    						
    						var accArr = ret.json;
    						if(accArr.length>0){
    		            		accPoint={
    		            			lng:accArr[0].lng,
    		            			lat:accArr[0].lat
    		            		}
    		            		cenp={
    			            			lng:accArr[0].lng,
    			            			lat:accArr[0].lat
    			            		}
    						}
    						bmap = new BaiduMap({
    		    				id: "treemenu_isaccident_container",
    		    				level: 14, //  选填--地图级别--(默认15)
    		    				zoom: true, // 选填--是否启用鼠标滚轮缩放功能--(默认false)
    		    				type: ["地图", "卫星"], // 选填--显示地图类型--(默认不显示)
    		    				//width: 320, // 选填--信息窗口width--(默认自动调整)
    		    				//height: 70, // 选填--信息窗口height--(默认自动调整)
    		    				titleClass: "maptitle",
    		    				contentClass: "mapcontent",
    		    				showPanorama: false, // 是否显示全景控件(默认false)
    		    				showMarkPanorama: false, // 是否显示标注点全景图(默认false)
    		    				showLabel: true, // 是否显示文本标注(默认false)
    		    				mapStyle: "normal", // 默认normal,可选dark,light
    		    				centerPoint: cenp,
    		    				index: 3, // 开启对应的信息窗口，默认-1不开启
    		    				animate: true, // 是否开启坠落动画，默认false
    		    				points: accArr,
    		    				callback: function(id) {
    		    					
    		    				}
    		    			});
    		            	if(accArr.length>0){
    							if(accArr[0].radius3!=''){
    								var cvalue=accArr[0].radius3*1000;
    								var circle = new BMap.Circle(new BMap.Point(accArr[0].lng,accArr[0].lat),cvalue,{strokeColor:"red", strokeWeight:1,fillColor: "#FFFFE0",  fillOpacity: 0.6}); //创建圆
    								bmap.addOverlay(circle);            //增加圆
    								bmap.setCenter(accArr[0].lng,accArr[0].lat,12);
    							}
    							if(accArr[0].radius2!=''){
    								var cvalue=accArr[0].radius2*1000;
    								var circle = new BMap.Circle(new BMap.Point(accArr[0].lng,accArr[0].lat),cvalue,{strokeColor:"#", strokeWeight:1,fillColor: "#FFA500", fillOpacity:0.5}); //创建圆
    								bmap.addOverlay(circle);            //增加圆
    								bmap.setCenter(accArr[0].lng,accArr[0].lat,12);
    							}
    							if(accArr[0].radius1!=''){
    								var cvalue=accArr[0].radius1*1000;
    								var circle = new BMap.Circle(new BMap.Point(accArr[0].lng,accArr[0].lat),cvalue,{strokeColor:"#", strokeWeight:3,fillColor: "#F70909",fillOpacity:0.5}); //创建圆
    								circle.addEventListener('click',function(){
    									new mp4Popup("js/mp4/1.mp4",2);
    								})
    								bmap.addOverlay(circle);            //增加圆
    								bmap.setCenter(accArr[0].lng,accArr[0].lat,12);
    								
    							}
    		            	}
    		            	accID='<%=id%>';
    		            	
    					},
    					failure:function(o){
    						Ext.MessageBox.alert("错误信息","操作有误!");
    					}
    				});
            	}else{
            		bmap = new BaiduMap({
	    				id: "treemenu_isaccident_container",
	    				level: 14, //  选填--地图级别--(默认15)
	    				zoom: true, // 选填--是否启用鼠标滚轮缩放功能--(默认false)
	    				type: ["地图", "卫星"], // 选填--显示地图类型--(默认不显示)
	    				//width: 320, // 选填--信息窗口width--(默认自动调整)
	    				//height: 70, // 选填--信息窗口height--(默认自动调整)
	    				titleClass: "maptitle",
	    				contentClass: "mapcontent",
	    				showPanorama: false, // 是否显示全景控件(默认false)
	    				showMarkPanorama: false, // 是否显示标注点全景图(默认false)
	    				showLabel: true, // 是否显示文本标注(默认false)
	    				mapStyle: "normal", // 默认normal,可选dark,light
	    				centerPoint: cenp,
	    				index: 3, // 开启对应的信息窗口，默认-1不开启
	    				animate: true, // 是否开启坠落动画，默认false
	    				callback: function(id) {
	    					
	    				}
	    			});
            		
            		if(<%="medical".equals(pageflag)%>){
						Ext.getCmp('checkpanel').down('form').getForm().findField("medical_checkbox").setValue(true);
					}else if(<%="medical_mp".equals(pageflag)%>){
						new mp4Popup("js/mp4/5.mp4",2);
						Ext.getCmp('checkpanel').down('form').getForm().findField("medical_checkbox").setValue(true);
					}else if(<%="startpath".equals(pageflag)%>){
						Ext.getCmp('checkpanel').down('form').getForm().findField("start_path").setValue(true);
					}else if(<%="camp".equals(pageflag)%>){
						Ext.getCmp('checkpanel').down('form').getForm().findField("camp_checkbox").setValue(true);
					}else if(<%="endpath".equals(pageflag)%>){
						Ext.getCmp('checkpanel').down('form').getForm().findField("end_path").setValue(true);
					}else if(<%="rescue".equals(pageflag)%>){
						Ext.getCmp('checkpanel').down('form').getForm().findField("rescue").setValue(true);
					}
            	}
            	

            },
            resize:function( e, width, height, oldWidth, oldHeight, eOpts ){
            	if(Ext.getCmp('checkpanel')){
        			Ext.getCmp('checkpanel').setX(windowWidth-160);
            		Ext.getCmp('checkpanel').setY(windowHeight-220);
        		}
            }
        }
	});
	</script>
  </head>
  
  <body>
    
  </body>
</html>
