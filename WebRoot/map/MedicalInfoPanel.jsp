<%@page import="com.jointthinker.framework.common.util.DateUtil"%>
<%--<%@page import="com.jointthinker.framework.business.UserSessionObject"%>--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//UserSessionObject uso = (UserSessionObject)request.getSession().getAttribute("currentuserSessionObj");
String nowdate = DateUtil.getSystemCurrentDateTime();
java.util.Random r = new java.util.Random();
String nameSpaceId = "medicalinfo" + r.nextInt();
nameSpaceId = nameSpaceId.replace("-", "");
String id = request.getParameter("id");
String tableid = request.getParameter("tableid");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>医疗资源信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	
	Ext.define('map.MedicalInfoPanel', {
	    extend: 'Ext.panel.Panel',
	    xtype: 'medicalinfopanel',
	    border:false,
	    collapsible: false,
	    margin: '0 0 0 0',
	    bodyPadding: 0,
	    layout:'fit',
    	frame: true,
    	items:[{
    		xtype:'form',
    		border:false,
    	    collapsible: false,
    	    margin: '0 0 0 0',
        	layout:'column',
        	bodyPadding: 20,
        	buttonAlign : 'center', 
    	    dockedItems: [{
    	        xtype: 'toolbar',
    	        dock: 'bottom',
    	        defaults: {
    	            minWidth: 100
    	        },
    	        style: {
    				marginBottom:'20px'
    			},
    	        items: ['->',{
    	        	xtype: 'button', 
    	        	text: '保存',
    	        	handler: function(e){
    	        		var form = e.up('panel');
    	        		if (form.form.isValid()){
			      			 Ext.MessageBox.confirm('提示', '确定保存吗？', function(btn, text) {
			 		    		if(btn == 'yes') {
			     	        		if($('#<%=nameSpaceId%>_input').val()=="天津市"){
			     	        			Ext.MessageBox.alert("错误信息","地址不详细，请重新填写完整地址！");
			     	        			return;
			     	        		}
			     	        		
			     	        		form.getForm().findField('haddress').setValue($('#<%=nameSpaceId%>_input').val()); 
			     	        		form.getForm().submit({
			    						submitEmptyText: false,
			    						url: 'medical/save',
			    						method: 'POST',
			    						waitMsg: '存储信息，请稍等...',
			    						success: function(simple, o){
			    							var ret = Ext.decode(o.response.responseText);
			    							form.getForm().setValues(ret.pojo);
			    							if(Ext.getCmp('<%=tableid%>')){
												Ext.getCmp('<%=tableid%>').getStore().reload();
											}
			    							Ext.toast({
			         				            html: '<div style="font-size:14px;padding-top:5px;"><span class="x-fa fa-check-circle" style="color:#f5e3aa;font-size:16px;"></span>&nbsp;&nbsp;保存成功！</div>',
			         				            closable: false,
			         				            align: 't',
			         				            slideInDuration: 200,
			         				            minWidth: 400
			         				        });
			    							var curPanel = contentPanel.getActiveTab();
			    	    					curPanel.destroy();//调用销毁 
			    						}
			    					});
			 		    		}
			      					
			      			});
    	        		}else{
				        	Ext.MessageBox.alert("错误信息","请完整填写信息！");
				        }
    	        		
    	        	}
    	        },{
    	        	 xtype: 'button', 
    	        	 text: '关闭',
    	        	 handler: function(){
    	        		 var curPanel = contentPanel.getActiveTab();
    					 curPanel.destroy();//调用销毁 
    	        	 }
    	        },
    	     	      '->'
    	        ]
    	    }],
    		items:[{
					layout: 'anchor',
					border:false,
					columnWidth: .33,
					defaults: {
						anchor:'98%'
					},
					items:[{
						xtype: 'hidden',
						name:'id'
					},{
						xtype: 'hidden',
						name:'lat'
					},{
						xtype: 'hidden',
						name:'lng'
					},{
						xtype: 'hidden',
						name:'haddress'
					},{
						xtype: 'textfield',
	    				fieldLabel: '<i class="fa fa-asterisk" style="color: #c54747"></i>名称',
	    				name: 'hospitalname',
	    				allowBlank:false
					}]
				},{
					layout: 'anchor',
					border:false,
					columnWidth: .33,
					defaults: {
						anchor:'98%'
					},
					items:[{
						xtype: 'textfield',
	    				fieldLabel: '电话',
	    				name: 'telephone'
					}]
				},{
					layout: 'anchor',
					border:false,
					columnWidth: .33,
					defaults: {
						anchor:'98%'
					},
					items:[{
						xtype: 'numberfield',
	    				fieldLabel: '床位',
	    				name: 'bed'
					}]
				},{
					layout: 'anchor',
					border:false,
					columnWidth: .66,
					defaults: {
						anchor:'98%'
					},
					items:[{
						xtype: 'textfield',
	    				fieldLabel: '技术优势',
	    				name: 'advantage'
					}]
				},{
					layout: 'anchor',
					border:false,
					columnWidth: .33,
					defaults: {
						anchor:'98%'
					},
					items:[{
						fieldLabel: '地理位置',
    	    	        name: 'position',
    	    	        xtype: 'combobox',
    	    	        displayField: 'text',
    	    	        valueField: 'id',
    	    	        editable: false,
    	    	        store: Ext.create('Ext.data.Store', {
    	    	        	data: [
    	    	        	       {id: '东部', text: '东部'},
    	    	        	       {id: '东南', text: '东南'},
    	    	        	       {id: '东北', text: '东北'},
    	    	        	       {id: '南部', text: '南部'},
    	    	        	       {id: '西部', text: '西部'},
    	    	        	       {id: '西南', text: '西南'},
    	    	        	       {id: '西北', text: '西北'},
    	    	        	       {id: '北部', text: '北部'}]
    	    	        })
	    	    	        
					}]
				},{
					layout: 'anchor',
					border:false,
					columnWidth: .075,
					defaults: {
						anchor:'100%'
					},
					
					items:[{
						xtype: 'displayfield',
	    				value: '<i class="fa fa-asterisk" style="color: #c54747"></i>完整地址'
					}]
				},{
					layout: 'anchor',
					border:false,
					columnWidth: .91,
					defaults: {
						anchor:'99.8%'
					},
					
					items:[{
						xtype:'panel',
						height:800,
						html:'<div class="case"><div class="bMap" id="<%=nameSpaceId%>_case1"></div></div>'
					}]
					
				}]
    	}],
    	listeners: {  
            'afterrender': function(btn) {  
            	var form = btn.down('form');
            	<%if(id!=null&&!"".equals(id)){%>
	            	Ext.Ajax.request({
						url:'<%=path%>/medical/getMedicalInfo',
						params:{
							id:'<%=id%>'
						},
						success:function(o){
							var ret = Ext.decode(o.responseText);
							form.getForm().setValues(ret.json);
							$("#<%=nameSpaceId%>_case1").bMap({
			            		address:ret.json.haddress,
			            		name: "haddress", //提交表单的NAME,默认为map
			            		inputid:"<%=nameSpaceId%>_input",
			                    callback: function(address, point) //回调函数，返回地址数组与坐标
			                    {
			                    	form.getForm().findField('lat').setValue(point.lat);
			                    	form.getForm().findField('lng').setValue(point.lng);
			                    }
			            	});
						}
	            	});
            	<%}else{%>
            	$("#<%=nameSpaceId%>_case1").bMap({
            		address:"天津市",
            		name: "haddress", //提交表单的NAME,默认为map
            		inputid:"<%=nameSpaceId%>_input",
                    callback: function(address, point) //回调函数，返回地址数组与坐标
                    {
                    	form.getForm().findField('lat').setValue(point.lat);
                    	form.getForm().findField('lng').setValue(point.lng);
                    }
            	});
            	<%}%>
            }
        }
	});
	
   
	
	</script>
  </head>
  
  <body>
   
  </body>
</html>
