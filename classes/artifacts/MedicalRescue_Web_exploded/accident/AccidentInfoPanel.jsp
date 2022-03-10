<%@page import="com.jointthinker.framework.common.util.JSONutil"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
java.util.Random r = new java.util.Random();
String nameSpaceId = "accidentinfo" + r.nextInt();
nameSpaceId = nameSpaceId.replace("-", "");
String id=request.getParameter("id");
String node=request.getParameter("node");
String tableid=request.getParameter("tableid");
JSONObject obj =null;
String url="";
if(node!=null&&!"".equals(node)){
	obj=JSONutil.bean2json(node);
	url=obj.get("url")==null?"":obj.get("url").toString();
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'AccidentInfoPanel.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
	</style>
	<script type="text/javascript">
	var imgForm = Ext.create('Ext.form.Panel', {
	    width: 320,
	    items: [{
	        xtype: 'image',
	        style:{
	        	padding:'7px',
	        	border:'1px dashed #bbb'
	        },
	        width: 330,
	        height: 340
	    },{
	        xtype: 'filefield',
	        hideLabel:true,
	        emptyText : '选择照片路径...',
	        buttonText:'浏览...',
	        width: '100%',
	        hidden:${not empty param.view },
	        name: 'faceimg',
	        accept:'image/*',
	        listeners : {
	            'render' : function(ff) {
	               ff.on('change',function(field, newValue, oldValue) {
	                    var file = field.fileInputEl.dom.files.item(0);
	                    var fileReader = new FileReader('file://'+newValue);
	                    fileReader.readAsDataURL(file);
	                    fileReader.onload=function(e){
	                    	imgForm.down('image').setSrc(e.target.result);
	                    }
	                });
	            }
	        }
	    }]
	});
	Ext.define('accident.AccidentInfoPanel', {
		extend: 'Ext.panel.Panel',
		xtype: 'accidentinfopanel',
		layout: 'fit',
		items: [{
			xtype: 'form',
			bodyPadding: 10,
			scrollable :true,
			fileUpload: true,
			defaults: {
				anchor: '98%',
				msgTarget: 'side'
			},
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
    	        		var form = e.up('form');
    	        		if (form.form.isValid()){
			      			 Ext.MessageBox.confirm('提示', '确定保存吗？', function(btn, text) {
			 		    		if(btn == 'yes') {
			     	        		if($('#<%=nameSpaceId%>_input').val()=="天津市"){
			     	        			Ext.MessageBox.alert("错误信息","地址不详细，请重新填写完整地址！");
			     	        			return;
			     	        		}
			     	        		
			     	        		form.getForm().findField('site').setValue($('#<%=nameSpaceId%>_input').val()); 
			     	        		form.getForm().submit({
			    						submitEmptyText: false,
			    						url: 'accident/save',
			    						method: 'POST',
			    						waitMsg: '存储信息，请稍等...',
			    						success: function(simple, o){
			    							var ret = Ext.decode(o.response.responseText);
			    							form.getForm().setValues(ret.pojo);
			    							if(Ext.getCmp('<%=tableid%>')){
												Ext.getCmp('<%=tableid%>').getStore().reload();
											}
			    							if(Ext.getCmp('navigation-west-panel').down('treelist')){
			    								Ext.getCmp('navigation-west-panel').down('treelist').getStore().getRoot().removeAll(false);
			    								Ext.getCmp('navigation-west-panel').down('treelist').getStore().load();
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
			layout: 'anchor',
			items: [{
				xtype:'hidden',
				name:'id'
			},{
				xtype:'hidden',
				name:'site'
			},{
				xtype:'hidden',
				name:'createtime'
			},{
				xtype:'hidden',
				name:'lng'
			},{
				xtype:'hidden',
				name:'lat'
			},{
				xtype:'hidden',
				name:'photopath'
			},{
				layout:'column',
            	anchor: '100%',
	    		items:[{
	    			columnWidth: .75,
	    			layout: 'anchor',
    				border:false,
    				defaults: {
    					anchor:'100%'
    				},
	    			items:[
	    			{
	    	    		xtype:'textfield',
	    	    		allowBlank:false,
	    	    		name:'title',
	    	    		maxLength:25,
	    	    		maxLengthText:'不得超过25个字符',
	    	    		fieldLabel:'<i class="fa fa-asterisk input-required"></i>标题'
	    	    	},{
	    	    		layout:'column',
	                	anchor: '100%',
	                	items:[{
	        				layout: 'anchor',
	        				border:false,
	        				columnWidth: .33,
	        				defaults: {
	        					anchor:'100%'
	        				},
	        				items:[{
	        					xtype: 'numberfield',
	        					name:'radius1',
	        					minValue:0,
	        					maxValue:1000,
	        					minText:'数值范围为0-1000',
	        					maxText:'数值范围为0-1000',
	        					negativeText:'数值范围为0-1000',
	        					fieldLabel: '严重区半径/km'
	        				}]
	        			},{
	        				layout: 'anchor',
	        				border:false,
	        				columnWidth: .33,
	        				defaults: {
	        					anchor:'100%'
	        				},
	        				items:[{
	        					xtype: 'numberfield',
	        					name:'radius2',
	        					minValue:0,
	        					maxValue:1000,
	        					minText:'数值范围为0-1000',
	        					maxText:'数值范围为0-1000',
	        					negativeText:'数值范围为0-1000',
	        					fieldLabel: '中度区半径/km'
	        				}]
	        			},{
	        				layout: 'anchor',
	        				border:false,
	        				columnWidth: .34,
	        				defaults: {
	        					anchor:'100%'
	        				},
	        				items:[{
	        					xtype: 'numberfield',
	        					name:'radius3',
	        					minValue:0,
	        					maxValue:1000,
	        					minText:'数值范围为0-1000',
	        					maxText:'数值范围为0-1000',
	        					negativeText:'数值范围为0-1000',
	        					fieldLabel: '轻度区半径/km'
	        				}]
	        			}]
	    	    	},{
	                	layout:'column',
	                	anchor: '100%',
	                	items:[{
	        				layout: 'anchor',
	        				border:false,
	        				columnWidth: .098,
	        				defaults: {
	        					anchor:'100%'
	        				},
	        				items:[{
	        					xtype: 'displayfield',
	            				value: '<i class="fa fa-asterisk" style="color: #c54747"></i>事故地址'
	        				}]
	        			},{
	        				layout: 'anchor',
	        				border:false,
	        				columnWidth: .902,
	        				defaults: {
	        					anchor:'100%'
	        				},
	        				items:[{
	        					xtype:'panel',
	        					height:300,
	        					style:'border:1px solid #5fa2dd',
	        					html:'<div class="case"><div class="bMap" id="<%=nameSpaceId%>_case1"></div></div>'
	        				}]
	        				
	        			}]
	                }]
	    		},{
	    			columnWidth: .25,
	    			layout: 'anchor',
    				border:false,
    				style:'margin:10',
    				defaults: {
    					anchor:'100%'
    				},
	    			items: imgForm
	    		}]
	    	},{
	    		xtype:'ueditor',
                name:'content',
                fieldLabel: '内容',
                allowBlank: false,
                height:245,
                toolbarstate:1 ,
                ueditorConfig: {
                    //编辑器配置项
                    topOffset:180 
                },
                enableKeyEvents: true,
                listeners: {
                	'change': function(field, e){
                		 field.isValid();
                     }
                 }
	    	}]
		}],
		listeners: {  
            'afterrender': function(btn) {  
            	var form = btn.query('form')[0];
            	<%if(id!=null&&!"".equals(id)){%>
	            	Ext.Ajax.request({
						url:'<%=path%>/accident/getAccidentInfo',
						params:{
							id:'<%=id%>'
						},
						success:function(o){
							var ret = Ext.decode(o.responseText);
							form.getForm().setValues(ret.json);
							if(ret.json.photopath&&ret.json.photopath!=""){
					    		imgForm.down('image').setSrc('accident/getPhoto?path='+ret.json.photopath);
					    	}
							$("#<%=nameSpaceId%>_case1").bMap({
								address:ret.json.site,
								inputid:"<%=nameSpaceId%>_input",
			            		name: "site", //提交表单的NAME,默认为map
			                    callback: function(address, point) //回调函数，返回地址数组与坐标
			                    {
			                    	btn.down('form').getForm().findField('lat').setValue(point.lat);
			                    	btn.down('form').getForm().findField('lng').setValue(point.lng);
			                    }
			            	});
						}
	            	});
            	<%}else{%>
            		if(<%=obj!=null%>){
	            		//通过网址获取事故信息
	            		Ext.Ajax.request({
							url:'<%=path%>/accident/getAccidentInfoByURl',
							params:{
								url:'<%=url%>'
							},
							success:function(o){
								var ret = Ext.decode(o.responseText);
								form.getForm().setValues(ret.json);
							}
		            	});
            		}
            	
            	$("#<%=nameSpaceId%>_case1").bMap({
            		address:"天津市",
            		name: "site", //提交表单的NAME,默认为map
            		inputid:"<%=nameSpaceId%>_input",
                    callback: function(address, point) //回调函数，返回地址数组与坐标
                    {
                    	btn.down('form').getForm().findField('lat').setValue(point.lat);
                    	btn.down('form').getForm().findField('lng').setValue(point.lng);
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
