<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>级联字典</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	Ext.define('admin.system.DictTreePanel.DictTreeStore', {
		extend: 'Ext.data.TreeStore',
	    xtype: 'dicttreestore',
	    autoLoad: true,
		lazyFill: true,
		proxy: {
			type: 'ajax',
			url: 'listCorecode.do',
			extraParams: {
		    	type: 'lazytree',
		    	rootid: '1'
		    },
			reader: {
				type: 'json'
			}
		},
		listeners: {
			// 异步加载tree节点
			'nodebeforeexpand': function(node, opts) {
				var store = this;
   				if(!node.hasChildNodes() && !node.isLeaf()) {
   					Ext.Ajax.request({
					    url: 'listCorecode.do',
					    params: {
					    	rootid: node.get('id'),
					    	flag: store.proxy.extraParams.flag,
		    		    	type: 'lazytree'
		    		    },
					    success: function(response, opts) {
    	    				node.appendChild(Ext.decode(response.responseText));
    	    				store.loadData(node, true);
					    },
					    failure: function(response, opts) {
					        console.log('错误代码： ' + response.status);
					    }
					});
   				}
   			}
		}
	});
	Ext.define('admin.system.DictTreePanel.DictTreeInfoPanel', {
		extend: 'Ext.panel.Panel',
		xtype: 'dicttreeinfopanel',
		requires: ['Ext.ux.ComboBoxTree'],
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
	    		items:[{
	    			xtype: 'textfield',
	    			name: 'id',
	    			hidden: true
	    		},{
	    			xtype: 'textfield',
	    			name: 'parentid',
	    			hidden: true
	    		}, Ext.create('Ext.ux.ComboBoxTree',{ 
	        		allowBlank:false,
	        		fieldLabel: '上级选项', 
	        		name: 'parentid_view',
	    	        displayField: 'text',
	    	        valueField: 'id',
	    	        editable: false,
	    	        store: Ext.create('admin.system.DictTreePanel.DictTreeStore', {
	    	    		proxy: {
	    	    			extraParams: {
	    	    		    	type: 'lazytree',
	    	    		    	flag: 'notleaf',
	    	    		    	rootid: '1'
	    	    		    }
	    	    		}
	    	        }),
	    	        listeners: {
	    	        	'change': function() {
	    	        		this.previousNode('[name=parentid]').setValue(this.getSubmitValue());
	    	        	}
	    	        }
	    		}), { 
	        		xtype: 'textfield',
	        		allowBlank:false,
	        		fieldLabel: '选项名称', 
	        		name: 'codename', 
	        		emptyText: '选项名称' 
	    		}, {
	            	allowBlank: false,
	    	        fieldLabel: '是否叶子节点',
	    	        name: 'isleaf',
	    	        xtype: 'combobox',
	    	        displayField: 'text',
	    	        valueField: 'id',
	    	        editable: false,
	    	        value: 0,
	    	        store: Ext.create('Ext.data.Store', {
	    	        	data: [{id: 1, text: '是'}, {id: 0, text: '否'}]
	    	        })
	            }, { 
	        		xtype: 'numberfield',
	        		fieldLabel: '展示序列', 
	        		name: 'sequence',
	        		value: 1,
	                minValue: 1,
	                maxValue: 999
	    		}]
	    	}]
		}]
	});
	
	 
	
	Ext.define('admin.system.DictTreePanel.DictTreeManagerPanel', {
		extend: 'Ext.panel.Panel',
		xtype: 'dicttreemanagerpanel',
	    collapsible: false,
	    layout:'border',
	    margin: '0 0 0 0',
	    bodyPadding: 0,
	    initComponent: function() {
	    	var me = this;
	    	var dicttreeStore = Ext.create('admin.system.DictTreePanel.DictTreeStore');
	    	
	    	
	    	var win = Ext.create('Ext.window.Window', {
				title: '选项信息',
				closeAction:'close',
				modal: true,
				width: smallWidth,
				height: smallHeight,
				layout: 'fit',
			    fbar: [{
			    	type: 'button', text: '确定',handler: function() { 
			    		var dicttreemanagerlist = me.getComponent(0).getComponent(0);
			    		Ext.MessageBox.show({
			                msg: '正在提交，请稍等...',
			                progressText: '处理中...',
			                width: 300,
			                wait: {
			                    interval: 200
			                }
			            });
		        	    win.query('form')[0].getForm().submit({
		        	    	clientValidation: true,
		        	    	submitEmptyText: false,
		            	    url: 'saveCorecode.do',
		            	    success: function(form, action) {
				    	        Ext.MessageBox.hide();
				    	        Ext.Msg.alert("提示","操作成功！", function() {
			        		    	win.close();
			        		    	reloadInitData(dicttreemanagerlist.getStore());
				        	    });
				    	    },
				    	    failure: function(form, action) {
				    	        switch (action.failureType) {
				    	            case Ext.form.action.Action.CLIENT_INVALID:
				    	                Ext.Msg.alert('警告', '表单验证不通过，请检查！');
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
			    	type: 'button', text: '关闭',handler: function() {
			    		win.close();
		    		}
		    	}],
			    items: [{
			    	xtype: 'dicttreeinfopanel'
			    }]
			    	//Ext.create('admin.system.DictTreePanel.DictTreeInfoPanel')
			});
	    	
	    	var reloadInitData = function(store, successFn) {
	    		Ext.Ajax.request({
				    url: 'listSelectItem.do',
				    params: {
	    		    	flag: 'reload'
	    		    },
				    success: function(response, opts) {
				    	store.reload();
				    	if(successFn)
				    		successFn(response);
				    },
				    failure: function(response, opts) {
				        console.log('错误代码： ' + response.status);
				    }
				});
	    	};
	    	
	    	var updateDict = function(id, view) {
	    		Ext.Ajax.request({
				    url: 'listCorecode.do',
				    params: {
				    	corecodeid: id,
	    		    	type: 'record'
	    		    },
				    success: function(response, opts) {
				        var dicttree = Ext.decode(response.responseText);
				        if(dicttree.parentid == null || dicttree.parentid == '') {
				        	Ext.Msg.alert("提示","不能修改根菜单!");
				        	return;
				        }
				        view.query('form')[0].getForm().setValues(dicttree);
				        view.down('comboboxtree').hide().allowBlank = true;
				        view.show();
				    },
				    failure: function(response, opts) {
				        console.log('错误代码： ' + response.status);
				    }
				});
	    	};
	    	
	    	Ext.apply(me, {
	    		items: [{
	    	    	region:'center',
	    	    	layout: 'fit',
	    	    	frame: true,
	    	    	items: [{
	    	    		xtype: 'treepanel',
	    	    		columnLines: true,
	    	    		reference: 'tree-panel',
	    	    		rowLines: true,
	    	    		title : '字典树',
	    	    		useArrows: true,
	    	    		rootVisible: false,
	    	    		columnLines: true,
	    	    	    rowLines: true,
	    	    		reserveScrollbar: true,
	    	    		
	    	    		listeners: {
	    	    			'itemdblclick': function(view , record , item , index , e , eOpts) {
	    	    				updateDict(record.get('id'), win);
	    	    			}
	    	    		},

	    	    		tbar : [ '->', {
	    	    			xtype : 'button',
	    	    			iconCls : 'x-fa fa-plus',
	    	    			text : '新增',
	    	    			handler:function(){
	    	    				var dicttreemanagerlist = this.up('treepanel');
	    	    				win.down('comboboxtree').show().allowBlank = false;
    	    					win.down('form').getForm().reset();
    	    					win.show();
	    	    			}
	    	    		}, '-', {
	    	    			xtype : 'button',
	    	    			iconCls : 'x-fa fa-edit',
	    	    			text : '修改',
	    	    			handler: function(){
	    	    				var dicttreemanagerlist = this.up('treepanel');
	    	    				if(dicttreemanagerlist.getSelectionModel().hasSelection()){
	    	    					var id = dicttreemanagerlist.getSelection()[0].get('id');
	    	    					updateDict(id, win);
	    	    				}else{
	    	    					Ext.Msg.alert("提示","请先选择要修改的行!");
	    	    				}
	    	    			}
	    	    		}, '-', {
	    	    			xtype : 'button',
	    	    			iconCls : 'x-fa fa-remove',
	    	    			text : '删除',
	    	    			handler: function(){
	    	    				var dicttreemanagerlist = this.up('treepanel');
	    	    				if(dicttreemanagerlist.getSelectionModel().hasSelection()){
	    	    					var selection = dicttreemanagerlist.getSelectionModel().getSelection();
	    	    		    		var name = selection[0].get('text');
	    	    		    		Ext.MessageBox.confirm('提示', '删除选项： ' + name + '<br>同时会删除所有子选项，确定删除？', function(btn, text) {
	    	    			    		if(btn == 'yes') {
	    	    			    			Ext.Ajax.request({
	    									    url: 'saveCorecode.do',
	    									    params: {
	    									    	corecodeid: selection[0].get('id'),
	    						    		    	type: 'delete'
	    						    		    },
	    									    success: function(response, opts) {
			    	    			    			reloadInitData(dicttreemanagerlist.getStore(), function(res) {
			    	    			    				Ext.Msg.alert("提示","删除成功!");
			    		    	    				});
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
	    	    		}, '-', {
	    	    			xtype : 'button',
	    	    			iconCls : 'x-fa fa-refresh',
	    	    			text : '重载',
	    	    			handler: function(){
	    	    				reloadInitData(this.up('treepanel').getStore(), function(res) {
	    	    					Ext.Msg.alert("提示","重载成功!");
	    	    				});
	    	    			}
	    	    		}],

	    	    		store: dicttreeStore,

	    	    		columns : [ {
	    	    			text : '选项名称',
	    	    			xtype: 'treecolumn',
	    	    			dataIndex : 'text',
	    	    			flex : 1
	    	    		}, {
	    	    			text : '是否叶子节点',
	    	    			dataIndex : 'leaf',
	    	    			flex : 1,
	    	    			renderer: function(value){
	    	    			    return !!value ? '是' : '否';
	    	    			}
	    	    		}]
	    	    	}]
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
