<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>字典数据</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	Ext.define('admin.system.DictPanel.DictInfoPanel', {
		extend: 'Ext.panel.Panel',
		xtype: 'dictinfopanel',
		layout: 'fit',
		items: [{
			xtype: 'form',
			bodyPadding: 10,
			defaults: {
				anchor: '95%',
				labelAlign:'right'
			},
			items: [{xtype: 'hidden',name: 'id'},
				{xtype: 'hidden',name: 'isview',value:1},
				{xtype: 'hidden',name: 'parentcode'},
				{xtype: 'hidden',name: 'fullcode'},
				{xtype: 'hidden',name: 'shortname'},
				{
		    		xtype: 'textfield',
		    		allowBlank:false,
		    		fieldLabel: '<i class="fa fa-asterisk input-required"></i>名称',
		    		name: 'name'
				},{
		        	allowBlank: false,
			        fieldLabel: '<i class="fa fa-asterisk input-required"></i>类型',
			        name: 'type',
			        xtype: 'combobox',
			        displayField: 'type',
			        valueField: 'type',
			        editable: true,
			        store: {
	    	        	autoLoad: false,
	    	    	    proxy: {
	    	    	        type: 'ajax',
	    	    	        url: 'dict/listType',
	    	    	        reader: {
	    	    	            type: 'json',
	    	    	            rootProperty: 'rows'
	    	    	        }
	    	    	    }
	    	        }
		        },{ 
		    		xtype: 'textfield',
		    		fieldLabel: '编码', 
		    		name: 'itemcode'
				},{
		    		xtype: 'numberfield',
		    		fieldLabel: '展示序列', 
		    		name: 'sequence',
		    		value: 10,
		            minValue: 1,
		            maxValue: 999
				}]
			}]
		});
	
	Ext.define('admin.system.DictPanel.DictManagerPanel', {
	    extend: 'Ext.panel.Panel',
	    xtype: 'dictmanagerpanel',
	    collapsible: false,
	    layout:'border',
	    margin: '0 0 0 0',
	    bodyPadding: 0,
	    initComponent: function() {
	    	var me = this;
	    	var store = Ext.create('Ext.data.Store', {
	    		autoLoad: true,
	    		groupField: 'type',
	    		proxy: {
	    			type: 'ajax',
	    			url: 'dict/list',
	    			reader: {
	    				type: 'json',
	    				rootProperty: 'rows'
	    			}
	    		}
	    	});
	    	
	    	var win = Ext.create('Ext.window.Window', {
	    		title: '选项信息',
	    		closeAction:'close',
	    	    modal: true,
	    	    width: 450,
	    	    height: 270,
	    	    layout: 'fit',
	    	    fbar: [{
					type: 'button', text: '确定',handler: function() { 
						var dictmanagerlist = me.down('grid');
						var f = win.query('form')[0].getForm();
						if(f.isValid()){
							Ext.MessageBox.show({
				                msg: '正在提交，请稍等...',
				                progressText: '处理中...',
				                width: 300,
				                wait: {
				                    interval: 200
				                }
				            });
						}
		        	    f.submit({
		        	    	clientValidation: true,
		        	    	submitEmptyText: false,
		            	    url: 'dict/saveOrmodify',
		            	    success: function(form, action) {
				    	        Ext.MessageBox.hide();
				    	        Ext.toast({
		    			            html: '<div style="font-size:14px;padding-top:5px;"><span class="x-fa fa-check-circle" style="color:#f5e3aa;font-size:16px;"></span>&nbsp;&nbsp;保存成功！</div>',
		    			            closable: false,
		    			            align: 't',
		    			            slideInDuration: 200,
		    			            minWidth: 400
		    			        });
				    	        win.close();
		        		    	reloadInitData(dictmanagerlist.getStore());
				    	    },
				    	    failure: function(form, action) {
				    	        switch (action.failureType) {
				    	            case Ext.form.action.Action.CLIENT_INVALID:
				    	            	Ext.toast({
	    		    			            html: '<div style="font-size:14px;padding-top:5px;"><span class="x-fa fa-info" style="color:#f5e3aa;font-size:16px;"></span>&nbsp;&nbsp;表单验证不通过，请检查！</div>',
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
					type: 'button', text: '关闭',handler: function() { win.close() }
				}],
	    	    items: Ext.create('admin.system.DictPanel.DictInfoPanel')
	    	});
	    	
	    	var reloadInitData = function(store, successFn) {
	    		Ext.Ajax.request({
				    url: 'dict/reload',
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
				    url: 'dict/dictreload',
				    params: {
				    	id: id
	    		    },
				    success: function(response, opts) {
				        var dict = Ext.decode(response.responseText);
				        view.query('form')[0].getForm().setValues(dict);
				        view.show();
				    },
				    failure: function(response, opts) {
				        console.log('错误代码： ' + response.status);
				    }
				});
	    	};
	    	
	    	function setState(state, ids, store){
	    		Ext.Ajax.request({
				    url: 'dict/setState',
				    params: {
				    	state: state,
				    	ids: ids
	    		    },
				    success: function(response, opts) {
				    	Ext.toast({
    			            html: '<div style="font-size:14px;padding-top:5px;"><span class="x-fa fa-check-circle" style="color:#f5e3aa;font-size:16px;"></span>&nbsp;&nbsp;操作成功!</div>',
    			            closable: false,
    			            align: 't',
    			            slideInDuration: 200,
    			            minWidth: 400
    			        });
				    	reloadInitData(store, function(res) {
	    				});
				    },
				    failure: function(response, opts) {
				    	Ext.toast({
    			            html: '<div style="font-size:14px;padding-top:5px;"><span class="x-fa fa-info" style="color:#f5e3aa;font-size:16px;"></span>&nbsp;&nbsp;操作失败!</div>',
    			            closable: false,
    			            align: 't',
    			            slideInDuration: 200,
    			            minWidth: 400
    			        });
				    }
				});
	    	}
	    	
	    	Ext.apply(me, {
	    		items: [{
	    	    	region:'center',
	    	    	layout: 'fit',
	    	    	frame: true,
	    	    	items: [{
	    	    		xtype: 'filtergrid',
	    	    		title : '字典数据列表',
	    	    		columnLines: true,
	    	    		selModel: {
			                type: 'checkboxmodel',
			                checkOnly: true
			            },
			            viewConfig : {
			    			forceFit : true,
			    			enableTextSelection: true
			    		},
	    	    		store: store,
	    	    		listeners: {
	    	    			'itemdblclick': function(view , record , item , index , e , eOpts) {
	    	    				updateDict(record.get('id'), win);
	    	    				win.down('button').setVisible(false);
	    	    			}
	    	    		},
	    	    		tbar : [{
	    	    			xtype : 'button',
	    	    			iconCls : 'x-fa fa-plus',
	    	    			style: {background: '#d0dce6'},
	    	    			text : '新增',
	    	    			handler: function(){
	    	    				win.down('button').setVisible(true);
    	    					win.query('form')[0].getForm().reset();
    	    					win.show();
	    	    			}
	    	    		}, '-',{
	    	    			xtype : 'button',
	    	    			iconCls : 'x-fa fa-pause',
	    	    			style: {background: '#d0dce6'},
	    	    			text : '停用',
	    	    			handler:  function(){
	    	    				var dictmanagerlist = this.up('grid');
	    	    				var rs = dictmanagerlist.getSelectionModel().getSelection();
	    	    				if(rs.length==0){
	    	    					Ext.toast({
			    			            html: '<div style="font-size:14px;padding-top:5px;"><span class="x-fa fa-info" style="color:#f5e3aa;font-size:16px;"></span>&nbsp;&nbsp;请选择要停用的数据!</div>',
			    			            closable: false,
			    			            align: 't',
			    			            slideInDuration: 200,
			    			            minWidth: 400
			    			        });
	    	    					return;
	    	    				}
	    	    				Ext.MessageBox.confirm('提示', '确认停用吗？', function(btn, text) {
    	    			    		if(btn == 'yes') {
    	    			    			var ids = [];
    	    			    			Ext.each(rs, function(r){
    	    			    				ids.push(r.data.id);
    	    			    			});
    	    			    			setState(0, ids.join(','),dictmanagerlist.getStore());
    	    			    		}
    	    			    	}, this);
	    	    			}
	    	    		}, '-',{
	    	    			xtype : 'button',
	    	    			iconCls : 'x-fa fa-play',
	    	    			style: {background: '#d0dce6'},
	    	    			text : '启用',
	    	    			handler:  function(){
	    	    				var dictmanagerlist = this.up('grid');
	    	    				var rs = dictmanagerlist.getSelectionModel().getSelection();
	    	    				if(rs.length==0){
	    	    					Ext.toast({
			    			            html: '<div style="font-size:14px;padding-top:5px;"><span class="x-fa fa-info" style="color:#f5e3aa;font-size:16px;"></span>&nbsp;&nbsp;请选择要启用的数据!</div>',
			    			            closable: false,
			    			            align: 't',
			    			            slideInDuration: 200,
			    			            minWidth: 400
			    			        });
	    	    					return;
	    	    				}
	    	    				Ext.MessageBox.confirm('提示', '确认启用吗？', function(btn, text) {
    	    			    		if(btn == 'yes') {
    	    			    			var ids = [];
    	    			    			Ext.each(rs, function(r){
    	    			    				ids.push(r.data.id);
    	    			    			});
    	    			    			setState(1, ids.join(','),dictmanagerlist.getStore());
    	    			    		}
    	    			    	}, this);
	    	    			}
	    	    		},{
	    	    			xtype : 'button',
	    	    			hidden:true,
	    	    			iconCls : 'x-fa fa-remove',
	    	    			style: {background: '#d0dce6'},
	    	    			text : '删除',
	    	    			handler:  function(){
	    	    				var dictmanagerlist = this.up('grid');
	    	    				if(dictmanagerlist.getSelectionModel().hasSelection()){
	    	    					var selection = dictmanagerlist.getSelectionModel().getSelection();
	    	    		    		var name = selection[0].get('name');
	    	    		    		var type = selection[0].get('type');
	    	    		    		Ext.MessageBox.confirm('提示', '删除' + type + '： ' + name, function(btn, text) {
	    	    			    		if(btn == 'yes') {
	    	    			    			Ext.Ajax.request({
	    									    url: 'dict/delete',
	    									    params: {
	    									    	id: selection[0].get('id')
	    						    		    },
	    									    success: function(response, opts) {
	    									    	reloadInitData(dictmanagerlist.getStore(), function(res) {
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
	    	    			iconCls : 'x-fa fa-retweet',
	    	    			style: {background: '#d0dce6'},
	    	    			text : '重载',
	    	    			handler: function(){
	    	    				reloadInitData(this.up('grid').getStore(), function(res) {
	    	    					Ext.toast({
			    			            html: '<div style="font-size:14px;padding-top:5px;"><span class="x-fa fa-check-circle" style="color:#f5e3aa;font-size:16px;"></span>&nbsp;&nbsp;重载成功!</div>',
			    			            closable: false,
			    			            align: 't',
			    			            slideInDuration: 200,
			    			            minWidth: 400
			    			        });
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
	    	    		}],
	    	    		features: [{
	    	    	        ftype: 'grouping',
	    	    	        groupHeaderTpl: '<span style="font-weight:bold;">{columnName}: {name} ({rows.length})</span>',
	    	    	        //hideGroupedHeader: true,
	    	    	        startCollapsed: true
	    	    	    }],
	    	    		columns : [{
	    	    			xtype: 'textactioncolumn',
	    	    			text : '操作',
	    	                width: 60,
	    	                align:'center',
	    	                sortable: false,
	    	                menuDisabled: true,
	    	                dataIndex:'id',
	    	                items: [{
	    	                	iconCls: 'x-fa fa-edit',
	    	                    tooltip: '修改',
	    	                    text : '修改',
	    	                    scope: this,
	    	                    getClass : function (v, metadata, r, rowIndex, colIndex, store) {
	    	                    	return 'x-fa fa-edit';
	    	                	},
	    	                    handler: function(g, index ,re,item,e,re ) {
	    	                    	win.down('button').setVisible(true);
	    	    					updateDict(re.data.id, win);
	    	                    }
	    	                }]
	    	    		},{
	    	    			xtype:'filtercolumn',
	    	            	text: '名称', 
	    	            	dataIndex: 'name',
	    	            	flex:2,
	    	            	filterControl:'textfield',
	    	       	        layout: 'hbox'
	    	    		},{
	    	    			text : '类别',
	    	    			xtype:'filtercolumn',
	    	    			filterControl:'multicombo',
	    	    			filterDisplayField:'type',
	    	    			filterValueField:'type',
	    	    			filterType:'string',
	    	    			filterStore:{
	    	       				fields: ['type'],
	    	       				autoLoad: false,
	    	    	    	    proxy: {
	    	    	    	        type: 'ajax',
	    	    	    	        url: 'dict/listType',
	    	    	    	        reader: {
	    	    	    	            type: 'json',
	    	    	    	            rootProperty: 'rows'
	    	    	    	        }
	    	    	    	    }
	    	    			},
	    	       			filterEditable:false,
	    	            	flex:1,
	    	       	        layout: 'hbox',
	    	    			dataIndex : 'type'
	    	    		},{
	    	    			text : '编码',
	    	    			dataIndex : 'itemcode',
	    	    			xtype:'filtercolumn',
	    	            	width:180,
	    	            	filterControl:'textfield',
	    	       	        layout: 'hbox'
	    	    		},{
	    	            	xtype:'filtercolumn',
	    	            	text: '状态', 
	    	            	width:180,
	    	            	dataIndex: 'isview',
	    	       	        layout: 'hbox',
	    	       	        filterControl:'combo',
	    	       			filterEditable:false,
	    	       			filterStore:{
	    	                   	fields: ['val', 'name'],
	    	                   	data : [
	    	                        {'val':'', 'name':''},
	    	                        {'val':'1', 'name':'启用'},
	    	                        {'val':'0', 'name':'停用'}
	    	                   	]
	    	    			},
	    	    			filterDisplayField:'name',
	    	    			filterValueField:'val',
	    	       	        renderer:function(v){
	    	            		return v=='1'?'启用':'停用';
	    	            	}
	    	    		},{
	    	    			text : '展示序列',
	    	    			xtype:'filtercolumn',
	    	    			filterControl:'panel',
	    	    			dataIndex : 'sequence',
	    	            	width:120,
	    	            	layout: 'hbox'
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
