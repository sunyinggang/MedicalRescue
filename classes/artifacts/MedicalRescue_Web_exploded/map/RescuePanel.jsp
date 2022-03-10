<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
java.util.Random r = new java.util.Random();
String nameSpaceId = "rescuegrid" + r.nextInt();
nameSpaceId = nameSpaceId.replace("-", "");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>救援协同列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	var rescueStore = Ext.create('Ext.data.Store', {
	    fields:[ 'id','name', 'address', 'telephone','lat','lng'],
	    pageSize:20,
 	 	autoLoad: true,
		proxy : {
			type : 'ajax',
			url : 'rescue/getRescueList',
			reader : {
				type : 'json',
				rootProperty: 'rows',
 			 	totalProperty:'total'
			}
		}
	});
	Ext.define('map.RescuePanel.RescueList', {
		extend : 'Ext.filtergrid.Panel',
		xtype : 'rescuelist',
		title : '救援协同列表',
		scrollable: true,
		viewConfig : {
			forceFit : true,
			enableTextSelection:true
		},
		requires :[
		   "Ext.toolbar.Toolbar",
		   "Ext.ux.grid.filter.*",
		   "Ext.ux.grid.menu.*"
		],
		id:'<%=nameSpaceId%>_grid',
		initComponent:function(){
			Ext.apply(this,{
				store: rescueStore,
				columns : [{
	    			xtype: 'textactioncolumn',
	    			text : '操作',
	                width: 100,
	                layout: 'hbox',
	                sortable: false,
	                menuDisabled: true,
	                items: [{
	                	iconCls: 'x-fa fa-edit',
	                    tooltip: '修改',
	                    text : '修改',
	                    scope: this,
	                    handler: function(grid, index) {
	                    	var id = grid.getStore().getAt(index).getData().id;
	                    	var node = grid.getStore().getAt(index).getData();
	                    	contentPanel.addTab({
	                    		id:'rescueinfo_'+id,
	                    		issys:'yes',
		    					params:{
		    						id:id,
		    						tableid:'<%=nameSpaceId%>_grid'
		    					},
		    					url:'map/RescueInfoPanel.jsp',
		    					text:node.name+"的信息",
		    					xtype:'rescueinfopanel',
		    					iconCls:'x-fa fa-ambulance'
		    				});
	                    }
	                }, {
	                	iconCls: 'x-fa fa-remove',
	                    tooltip: '删除',
	                    text : '删除',
	                    scope: this,
	                    handler:function(grid, index){
	                    	var id = grid.getStore().getAt(index).getData().id;
	                    	var name = grid.getStore().getAt(index).getData().name;
	                    	Ext.MessageBox.confirm('提示', '确定删除: ' + name+'?', function(btn, text) {
	        		    		if(btn == 'yes') {
	        		    			Ext.Ajax.request({
	        							url:'rescue/delRescueInfo?id='+id,
	        							success:function(o){
	        								var ret = Ext.decode(o.responseText);
	        								if(ret.success)
	        								{
	        									Ext.toast({
				         				            html: '<div style="font-size:14px;padding-top:5px;"><span class="x-fa fa-check-circle" style="color:#f5e3aa;font-size:16px;"></span>&nbsp;&nbsp;删除成功！</div>',
				         				            closable: false,
				         				            align: 't',
				         				            slideInDuration: 200,
				         				            minWidth: 400
				         				        });
	        									rescueStore.reload();
	        								}
	        								else
	        								{
	        									Ext.toast({
				         				            html: '<div style="font-size:14px;padding-top:5px;"><span class="x-fa fa-check-circle" style="color:#f5e3aa;font-size:16px;"></span>&nbsp;&nbsp;保存成功！</div>',
				         				            closable: false,
				         				            align: 't',
				         				            slideInDuration: 200,
				         				            minWidth: 400
				         				        });
	        								}											
	        							},
	        							failure:function(o){
	        								Ext.MessageBox.alert("错误信息",o.responseText);
	        							}
	        						});
	        		    		}
	        		    	}, this);
	                    }
	                }]
	    		},{
	    			xtype:'filtercolumn',
					text : '名称',
					dataIndex : 'name',
					width : 300,
					layout: 'hbox',
					filterControl:'textfield',
					renderer:function (value, metaData, record, rowIdx, colIdx, store){
			        	metaData.tdAttr = 'data-qtip="' + Ext.String.htmlEncode(value) + '"';
			        	return value;
			        }
				}, {
					xtype:'filtercolumn',
					text : '地址',
					dataIndex : 'address',
					flex : 1,
					layout: 'hbox',
					filterControl:'textfield',
					renderer:function (value, metaData, record, rowIdx, colIdx, store){
			        	metaData.tdAttr = 'data-qtip="' + Ext.String.htmlEncode(value) + '"';
			        	return value;
			        }
				}, {
					xtype:'filtercolumn',
					text : '电话',
					dataIndex : 'telephone',
					width : 180,
					layout: 'hbox',
					filterControl:'textfield',
					renderer:function (value, metaData, record, rowIdx, colIdx, store){
			        	metaData.tdAttr = 'data-qtip="' + Ext.String.htmlEncode(value) + '"';
			        	return value;
			        }
				}],

				bbar : {
					xtype: 'pagingtoolbar',
     				displayInfo : true,
     				pageSize:20,
     				store :rescueStore
				},
				tbar : [{
					xtype : 'button',
					iconCls : 'x-fa fa-plus',
					style: {
     				    background: '#d0dce6'
     				},
					text : '新增救援协助',
					handler: function(){
						contentPanel.addTab({
                    		id:'addrescueinfo',
	    					params:{
	    						tableid:'<%=nameSpaceId%>_grid'
	    					},
	    					url:'map/RescueInfoPanel.jsp',
	    					text:"新救援协助",
	    					xtype:'rescueinfopanel',
	    					iconCls:'x-fa fa-ambulance'
	    				});
					}
				},'->',{
     				xtype : 'button',
     				style: {
     				    background: '#d0dce6'
     				},
     				iconCls : 'x-fa fa-refresh',
     				text : '重置',
     				handler: function(b){
     					b.up('grid').clearQueryData();
     				}
     			}]
			});
			 this.callParent();
		}
		
	});
	
	Ext.define('map.RescuePanel.RescueListPanel', {
	    extend: 'Ext.panel.Panel',
	    xtype: 'rescuelistpanel',
	    collapsible: false,
	    frame:true,
	    layout:'fit',
	    margin: '0 0 0 0',
	    bodyPadding: 0,
	    items: Ext.create('map.RescuePanel.RescueList')
	});
	</script>
  </head>
  
  <body>
    
  </body>
</html>