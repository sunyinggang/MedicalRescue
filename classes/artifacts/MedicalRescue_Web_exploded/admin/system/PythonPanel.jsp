<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
java.util.Random r = new java.util.Random();
String nameSpaceId = "accidentgrid" + r.nextInt();
nameSpaceId = nameSpaceId.replace("-", "");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>事故列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	Ext.define('admin.system.PythonPanel', {
	    extend: 'Ext.panel.Panel',
	    xtype: 'pythonpanel',
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
        	layout:'anchor',
        	bodyPadding: 20,
        	buttonAlign : 'center', 
        	defaults: {
				anchor:'98%'
			},
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
			     	        		form.getForm().submit({
			    						submitEmptyText: false,
			    						url: 'corepath/save',
			    						method: 'POST',
			    						waitMsg: '存储信息，请稍等...',
			    						success: function(simple, o){
			    							var ret = Ext.decode(o.response.responseText);
			    							form.getForm().setValues(ret.pojo);
			    							Ext.toast({
			         				            html: '<div style="font-size:14px;padding-top:5px;"><span class="x-fa fa-check-circle" style="color:#f5e3aa;font-size:16px;"></span>&nbsp;&nbsp;保存成功！</div>',
			         				            closable: false,
			         				            align: 't',
			         				            slideInDuration: 200,
			         				            minWidth: 400
			         				        });
			    							if(Ext.getCmp('accident_gridpanel')){
			    								Ext.getCmp('accident_gridpanel').getStore().reload();
			    							}
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
    	    	xtype:'hidden',
    	    	name:'id'
    	    },{
    	    	xtype: 'textfield',
				fieldLabel: 'URL',
				name: 'urlpath'
			},{
				xtype: 'textarea',
				fieldLabel: '必含关键字<br/><span style="color: #c54747">(以、分开)</span>',
				name: 'must_keyword'
			},{
				xtype: 'textarea',
				fieldLabel: '包含关键字<br/><span style="color: #c54747">(以、分开)</span>',
				name: 'keyword'
			}]
    	}],
    	listeners: {  
            'afterrender': function(btn) {  
            	var form = btn.query('form')[0];
            	Ext.Ajax.request({
					url:'<%=path%>/corepath/getCorePath',
					success:function(o){
						var ret = Ext.decode(o.responseText);
						form.getForm().setValues(ret.pojo);
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