<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MenuPanel.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	Ext.define('admin.system.MenuPanel.MenuInfoPanel', {
		extend: 'Ext.panel.Panel',
		xtype: 'menuinfopanel',
		layout: 'border',
		items: [{
	    	region:'center',
	      	layout:'fit',
	      	items: [{
	    		xtype: 'form',
	    		bodyPadding: 10,
	    		layout: 'anchor',
	    		defaults: {
	    			margin: '10',
	    			anchor: '95%'
	    		},
	    		items:[{xtype: 'hidden',name: 'id'},
	    		       	{xtype: 'hidden',name: 'isview',value:1},
	    		       	{xtype:'hidden',name:'deltime'},
	   		            {xtype:'hidden',name:'deluserid'},
	   		            {xtype:'hidden',name:'createtime'},
	   		            {xtype:'hidden',name:'createuserid'},
	   		         	{xtype:'hidden',name:'accidenttype'},
	   		         
	    			{
	        		allowBlank:false,
	        		fieldLabel: '父菜单',
	        		name: 'parentid',
	    	        xtype: 'combobox',
	    	        displayField: 'menuname',
	    	        valueField: 'id',
	    	        forceSelection: true,
	    	        editable: false,
	    	        store: Ext.create('Ext.data.Store', {
	    	        	autoLoad: true,
    	    	     	proxy: {
    	    	        	type: 'ajax',
    	    	        	url: 'menu/notleaf',
    	    	        	reader: {
    	    	            	type: 'json'
    	    	        	}
    	    	     	}
	    	        })
	    		}, { 
	        		xtype: 'textfield',
	        		allowBlank:false,
	        		fieldLabel: '菜单名称', 
	        		name: 'menuname', 
	        		emptyText: '菜单名称' 
	    		}, {
	            	allowBlank: false,
	    	        fieldLabel: '是否叶子节点',
	    	        name: 'leaf',
	    	        xtype: 'combobox',
	    	        displayField: 'text',
	    	        valueField: 'id',
	    	        editable: false,
	    	        value: 1,
	    	        listeners: {
	    	        	'change': function(me, newVal, oldVal) {
	    	        		var query = this.up('form').queryBy(function(cp) {
	    	        			return cp.name == 'entrypoint' || cp.name == 'xtype';
	    	        		});
    	        			query.forEach(function(i) {
    	        				i.setDisabled(!newVal);
    	        			});
	    	        	}
	    	        },
	    	        store: Ext.create('Ext.data.Store', {
	    	        	data: [{id: 1, text: '是'}, {id: 0, text: '否'}]
	    	        })
	            }, { 
	        		xtype: 'textfield',
	        		fieldLabel: '进入点', 
	        		name: 'entrypoint', 
	        		emptyText: '进入点' 
	    		}, { 
	        		xtype: 'textfield',
	        		fieldLabel: 'XType', 
	        		name: 'xtype', 
	        		emptyText: 'XType' 
	    		}, { 
	        		xtype: 'numberfield',
	        		fieldLabel: '展示序列', 
	        		name: 'sequence',
	        		value: 100,
	                minValue: 1
	    		}, {
	            	allowBlank: false,
	    	        fieldLabel: '是否弹出窗口',
	    	        name: 'ispopwindow',
	    	        xtype: 'combobox',
	    	        displayField: 'text',
	    	        valueField: 'id',
	    	        editable: false,
	    	        value: 0,
	    	        store: Ext.create('Ext.data.Store', {
	    	        	data: [{id: 1, text: '是'}, {id: 0, text: '否'}]
	    	        })
	            }, {
	    			xtype: 'container',
	    			layout: 'hbox',
	    			anchor: '100%',
	    			items: [{ 
		        		xtype: 'textfield',
		        		allowBlank:false,
		        		fieldLabel: '图标样式', 
		        		name: 'icon', 
		        		editable: true,
		        		value: 'x-fa fa-folder',
		        		emptyText: '图标样式',
		        		listeners: {
		        			'change': function(c) {
		        				c.nextSibling().nextSibling().update({
    			      				icon: c.getValue().substr(2)
    			      			});
		        			}
		        		}
		    		}, {
		    			xtype: 'button',
		    			iconCls: 'x-fa fa-plus',
		    			margin: '0 0 0 5',
		    			handler: function(me) {
		    				var btn = me;
		    				var iconView = Ext.create('Ext.window.Window', {
		    					title: '图标库',
		    					width: bigWidth,
		    					height: bigHeight,
		    					modal: true,
		    					maximizable: true,
		    					scrollable: true,
		    					defaults: {
		    						margin: '10',
		    						xtype: 'button',
		    						scale: 'large',
		    						handler: function(me) {
		    							btn.previousSibling().setValue(me.iconCls);
		    							iconView.close();
		    						}
		    					},
		    					listeners: {
		    						'render': function() {
		    							var iconStore = Ext.create('Ext.data.Store', {
		    								autoLoad: true,
		    								fields: ['id', 'text'],
		    								proxy: {
		    						   	         type: 'ajax',
		        								 extraParams: {
		    									 	type: '图标'
		        								 },
		    						   	         url: 'dict/listSelectItem',
		    						   	         reader: {
		    						   	             type: 'json'
		    						   	         }
		    						   	     },
		    						   	     listeners: {
		    						   	    	 'load': function(sto, r, success) {
		    						   	    		if(success) {
		    						   	    			var arr = iconStore.getData().items.map(function(ele, index, array) {
		    						   	    				return {
			    		    									iconCls:　'x-fa ' + ele.get('name')
			    		    								};
		    						   	    			});
			    					   	    	 		iconView.add(arr);
		    						   	    		}
		    						   	    	 }
		    						   	     }
		    							});
		    						}
		    					}
		    				}).show();
		    				
		    			}
		    		}, {
		    			xtype: 'component',
		    			margin: '5 0 0 20',
		    			tpl: new Ext.Template([
							'<i class="{icon} fa-2x" style="color:#4c82b1"></i>'
    			      	]),
    			      	listeners: {
    			      		'afterrender': function(cmp) {
    			      			cmp.update({
    			      				icon: cmp.previousSibling().previousSibling().getValue().substr(2)
    			      			});
    			      		}
    			      	}
		    		}]
	    		},{
	    			xtype:'textarea',
	    			name:'menudesc',
	    			fieldLabel:'功能描述'
	    		}]
	    	}]
		}]
	});
	
	 
	
	Ext.define('admin.system.MenuPanel.MenuManagerPanel', {
		extend: 'Ext.panel.Panel',
		xtype: 'menumanagerpanel',
	    collapsible: false,
	    layout:'border',
	    margin: '0 0 0 0',
	    bodyPadding: 0,
	    initComponent: function() {
	    	var me = this;
	    	Ext.define('admin.system.MenuPanel.MenuInfoForm', {
	    		extend:'Ext.window.Window',
				title: '菜单信息',
				closeAction:'close',
				maximizable: true,
				modal: true,
				width: 520,
				height: 540,
				layout: 'fit',
				floating:true,
			    buttons: [{
			    	type: 'button', text: '确定',handler: function(t) { 
			    		var menumanagerlist = me.getComponent(0).getComponent(0);
			    		var win = t.up('window');
			    		var fm = win.down('form').getForm();
			    		var data = fm.getValues();
			    		fm.submit({
		        	    	clientValidation: true,
		        	    	submitEmptyText: false,
		            	    url: 'menu/save',
		            	    success: function(form, action) {
		            	    	win.close();
		            	    	Ext.toast({
         				            html: '<div style="font-size:14px;padding-top:5px;"><span class="x-fa fa-check-circle" style="color:#f5e3aa;font-size:16px;"></span>&nbsp;&nbsp;保存成功！</div>',
         				            closable: false,
         				            align: 't',
         				            slideInDuration: 200,
         				            minWidth: 400
         				        });
		            	    	var menuTreeStore = menuTree.getStore();
		            	    	var pnode = menuTreeStore.getNodeById(data.parentid);
    					    	if(data.parentid!=1&&!pnode.data.leaf){
    					    		var ppnode = pnode.parentNode;
    					    		if(ppnode!=null){
    					    			menuTreeStore.load({node: ppnode,callback:function(){
        					    			var pn = menuTreeStore.getNodeById(data.parentid);
        					    			if(pn){
        					    				pn.expand();
        					    				menuTree.setSelection(pn);
        					    			}
        					    		}});
    					    		}
    					    		
    					    	}else{
    					    		menuTreeStore.load({node: pnode,callback:function(){
    					    			var pn = menuTreeStore.getNodeById(data.id);
    					    			if(pn){
    					    				pn.expand();
    					    				menuTree.setSelection(pn);
    					    			}
    					    		}});
    					    	}
				    	    },
				    	    failure: function(form, action) {
				    	        switch (action.failureType) {
				    	            case Ext.form.action.Action.CLIENT_INVALID:
				    	            	Ext.toast({
		         				            html: '<div style="font-size:14px;padding-top:5px;"><span class="x-fa fa-info" style="color:#f5e3aa;font-size:16px;"></span>&nbsp;&nbsp;请检查表单信息是否填写完整！</div>',
		         				            closable: false,
		         				            align: 't',
		         				            slideInDuration: 200,
		         				            minWidth: 400
		         				        });
				    	                break;
				    	            case Ext.form.action.Action.CONNECT_FAILURE:
				    	                Ext.Msg.alert('警告', '连接服务器失败');
				    	                break;
				    	            case Ext.form.action.Action.SERVER_INVALID:
				    	               Ext.Msg.alert('警告', action.result.msg);
				    	       }
				    	    }
		        	    });
	            	}
			    }, {
			    	type: 'button', text: '关闭',handler: function(t) {
			    		t.up('window').close();
		    		}
		    	}],
			    items: [{
			    	xtype: 'menuinfopanel'
			    }]
			});
	    	
	    	var menuTree = GetMenuTreePanel({
	    		rootVisible: true,
	    		rootText:'功能菜单',
	    		columns : [{
	    			text : '菜单名称',
	    			xtype: 'treecolumn',
	    			dataIndex : 'text',
	    			flex : 2
	    		}, {
	    			text : '访问地址',
	    			dataIndex : 'entrypoint',
	    			flex : 2
	    		}, {
	    			text : '排序值',
	    			dataIndex : 'sequence',
	    			flex : 1
	    		}, {
	    			text : 'XType',
	    			dataIndex : 'xtype',
	    			flex : 1
	    		}, {
	    			text : '图标样式',
	    			dataIndex : 'iconCls',
	    			flex : 1
	    		}],
	    		listeners:{
	    			itemcontextmenu: function(_t, re, item, index, e){
	        			e.stopEvent();
	        			var contextM = Ext.create('Ext.menu.Menu', {
            			    width: 100,
            			    plain: true,
            			    items: [{
            			    	iconCls:'x-fa fa-plus',
            			        text: '新增菜单',
            			        handler:function(){
            			        	var menuwin = Ext.create('admin.system.MenuPanel.MenuInfoForm');
            			        	var menuwinF = menuwin.down('form').form;
            			        	menuwinF.findField('parentid').setValue(re.data.id);
            			        	menuwin.show();
            			        }
            			    },{
            			    	hidden:re.data.id==1,
            			    	iconCls:'x-fa fa-edit',
            			        text: '修改菜单',
            			        handler:function(t){
            			        	Ext.Ajax.request({
									    url: 'menu/record',
									    params: {
									    	menuid: re.data.id
						    		    },
									    success: function(response, opts) {
									        var menu = Ext.decode(response.responseText);
									        var menuwin = Ext.create('admin.system.MenuPanel.MenuInfoForm');
		            			        	var menuwinF = menuwin.down('form').form;
		            			        	menuwinF.setValues(menu);
		            			        	menuwin.show();
									    },
									    failure: function(response, opts) {
									        console.log('错误代码： ' + response.status);
									    }
									});
            			        }
            			    },{
            			    	hidden:re.data.id==1,
            			    	iconCls:'x-fa fa-minus',
            			        text: '删除菜单',
            			        handler: function(){
            			        	Ext.Msg.confirm('提示', '删除此菜单将会级联删除此菜单下的子菜单，您确认吗?', function(btn){
            			        		if(btn=='yes'){
            			        			Ext.Ajax.request({
            	    						    url: 'menu/remove',
            	    						    params: {
            	    						    	menuid: re.data.id
            	    						    },
            	    						    method:'post',
            	    						    success: function(response, opts) {
            	    						    	var o = Ext.decode(response.responseText);
            	    						    	Ext.toast({
            	    	     				            html: '<div style="font-size:14px;padding-top:5px;"><span class="x-fa fa-check-circle" style="color:#f5e3aa;font-size:16px;"></span>&nbsp;&nbsp;删除成功！</div>',
            	    	     				            closable: false,
            	    	     				            align: 't',
            	    	     				            slideInDuration: 200,
            	    	     				            minWidth: 400
            	    	     				        });
            	    						    	var menuTreeStore = menuTree.getStore();
            	    						    	var pnode = menuTreeStore.getNodeById(re.data.parentid);
            	    						    	menuTreeStore.load({node: pnode,callback:function(){
            	    						    		pnode.expand();
            	    					    		}});
            	    						    },
            	    						    failure: function(response, opts) {
            	    						    	Ext.Msg.alert('提交','删除失败！');
            	    						    }
            	    						});
            			        		}
            			        	});
            			        }
            			    }]
            			});
            			contextM.showAt(e.getXY());
	        		},
	        		itemdblclick:function(tp, re, item){
	        			Ext.Ajax.request({
						    url: 'menu/record',
						    params: {
						    	menuid: re.data.id
			    		    },
						    success: function(response, opts) {
						        var menu = Ext.decode(response.responseText);
						        var menuwin = Ext.create('admin.system.MenuPanel.MenuInfoForm');
        			        	var menuwinF = menuwin.down('form').form;
        			        	menuwinF.setValues(menu);
        			        	var fs = menuwinF.getFields();
        	    	    		fs.each(function(item){
        	    	    			item.setReadOnly(true);
        	    	    		});
        	    	    		menuwin.down('button').setVisible(false);
        			        	menuwin.show();
						    },
						    failure: function(response, opts) {
						        console.log('错误代码： ' + response.status);
						    }
						});
	        		}
	    		}
	    	});
	    	
	    	Ext.apply(me, {
	    		items: [{
	    	    	region:'center',
	    	    	title:'菜单信息树',
	    	    	layout: 'fit',
	    	    	frame: true,
	    	    	items: menuTree/* [{
	    	    		xtype: 'treepanel',
	    	    		columnLines: true,
	    	    		reference: 'tree-panel',
	    	    		rowLines: true,
	    	    		title : '功能菜单',
	    	    		useArrows: true,
	    	    		rootVisible: false,
	    	    		columnLines: true,
	    	    	    rowLines: true,
	    	    		reserveScrollbar: true,

	    	    		tbar : [ '->', {
	    	    			xtype : 'button',
	    	    			iconCls : 'x-fa fa-plus',
	    	    			text : '新增',
	    	    			handler:function(){
	    	    				var menumanagerlist = this.up('treepanel');
	    	    				if(menumanagerlist.getSelectionModel().hasSelection()){
	    	    					var id = menumanagerlist.getSelectionModel().getSelection()[0].get('id');
	    	    					win.query('form')[0].getForm().reset().setValues({
	    	    						parentid: id
	    	    					});
	    	    					win.query('grid')[0].getSelectionModel().deselectAll();
	    	    					win.show();
	    	    				}else{
	    	    					Ext.Msg.alert("提示","请先选择父级菜单!");
	    	    				}
	    	    			}
	    	    		}, '-', {
	    	    			xtype : 'button',
	    	    			iconCls : 'x-fa fa-edit',
	    	    			text : '修改',
	    	    			handler: function(){
	    	    				var menumanagerlist = this.up('treepanel');
	    	    				if(menumanagerlist.getSelectionModel().hasSelection()){
	    	    					var data = menumanagerlist.getSelection()[0].getData();
	    	    					var id = menumanagerlist.getSelection()[0].get('id');
	    	    					Ext.Ajax.request({
									    url: 'menu/record',
									    params: {
									    	menuid: id
						    		    },
									    success: function(response, opts) {
									        var menu = Ext.decode(response.responseText);
									        if(menu.parentid == null || menu.parentid == '') {
									        	Ext.Msg.alert("提示","不能修改根菜单!");
									        	return;
									        }
			    	    					win.query('form')[0].getForm().setValues(menu);
									        var gridStore = win.query('grid')[0].getStore();
									        var gridSelModel = win.query('grid')[0].getSelectionModel();
									        var recodes=[];
									        menu.menuroles.split(",").forEach(function(ele, index, array) {
									        	//gridSelModel.select(gridStore.getById(ele)); 
									        	if(gridStore.getById(ele)!=null){
									        		recodes.push(gridStore.getById(ele));
									        	}
									    	   
									        }); 
									        if(recodes.length>0){
									        	gridSelModel.select(recodes);
									        }
									        
			    	    					win.show();
									    },
									    failure: function(response, opts) {
									        console.log('错误代码： ' + response.status);
									    }
									});
	    	    				}else{
	    	    					Ext.Msg.alert("提示","请先选择要修改的行!");
	    	    				}
	    	    			}
	    	    		}, '-', {
	    	    			xtype : 'button',
	    	    			iconCls : 'x-fa fa-remove',
	    	    			text : '删除',
	    	    			handler: function(){
	    	    				var menumanagerlist = this.up('treepanel');
	    	    				if(menumanagerlist.getSelectionModel().hasSelection()){
	    	    					var selection = menumanagerlist.getSelectionModel().getSelection();
	    	    		    		var name = selection[0].get('text');
	    	    		    		Ext.MessageBox.confirm('提示', '删除菜单： ' + name + '<br>同时会删除所有子菜单，确定删除？', function(btn, text) {
	    	    			    		if(btn == 'yes') {
	    	    			    			Ext.Ajax.request({
	    									    url: 'menu/delete',
	    									    params: {
	    									    	menuid: selection[0].get('id')
	    						    		    },
	    									    success: function(response, opts) {
	    									    	menumanagerlist.getStore().reload();
			    	    			    			Ext.Msg.alert("提示","删除成功!");
	    									    },
	    									    failure: function(response, opts) {
	    									        console.log('错误代码： ' + response.status);
	    									    }
	    									});
	    	    			    		}
	    	    			    	}, this);
	    	    		    	}else {
	    	    					Ext.Msg.alert("提示","请先选择要删除的行!");
	    	    				}
	    	    			}
	    	    		}],

	    	    		store: menuStore,

	    	    		columns : [ {
	    	    			text : '菜单名称',
	    	    			xtype: 'treecolumn',
	    	    			dataIndex : 'text',
	    	    			flex : 2
	    	    		}, {
	    	    			text : '访问地址',
	    	    			dataIndex : 'entrypoint',
	    	    			flex : 2
	    	    		}, {
	    	    			text : 'XType',
	    	    			dataIndex : 'xtype',
	    	    			flex : 1
	    	    		}, {
	    	    			text : '图标样式',
	    	    			dataIndex : 'iconCls',
	    	    			flex : 1
	    	    		}],
	    	    		listeners: {
	    	    			'load': function() {
	    	    				//this.expandAll();
	    	    			}
	    	    		}
	    	    	}] */
	    	    }]
	    	});
	    	this.callParent();
	    }
	});
	</script>
  </head>
  
  <body>
  
  </body>
</html>
