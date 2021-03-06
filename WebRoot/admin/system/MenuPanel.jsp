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
	        		fieldLabel: 'ηΆθε',
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
	        		fieldLabel: 'θεεη§°', 
	        		name: 'menuname', 
	        		emptyText: 'θεεη§°' 
	    		}, {
	            	allowBlank: false,
	    	        fieldLabel: 'ζ―ε¦εΆε­θηΉ',
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
	    	        	data: [{id: 1, text: 'ζ―'}, {id: 0, text: 'ε¦'}]
	    	        })
	            }, { 
	        		xtype: 'textfield',
	        		fieldLabel: 'θΏε₯ηΉ', 
	        		name: 'entrypoint', 
	        		emptyText: 'θΏε₯ηΉ' 
	    		}, { 
	        		xtype: 'textfield',
	        		fieldLabel: 'XType', 
	        		name: 'xtype', 
	        		emptyText: 'XType' 
	    		}, { 
	        		xtype: 'numberfield',
	        		fieldLabel: 'ε±η€ΊεΊε', 
	        		name: 'sequence',
	        		value: 100,
	                minValue: 1
	    		}, {
	            	allowBlank: false,
	    	        fieldLabel: 'ζ―ε¦εΌΉεΊηͺε£',
	    	        name: 'ispopwindow',
	    	        xtype: 'combobox',
	    	        displayField: 'text',
	    	        valueField: 'id',
	    	        editable: false,
	    	        value: 0,
	    	        store: Ext.create('Ext.data.Store', {
	    	        	data: [{id: 1, text: 'ζ―'}, {id: 0, text: 'ε¦'}]
	    	        })
	            }, {
	    			xtype: 'container',
	    			layout: 'hbox',
	    			anchor: '100%',
	    			items: [{ 
		        		xtype: 'textfield',
		        		allowBlank:false,
		        		fieldLabel: 'εΎζ ζ ·εΌ', 
		        		name: 'icon', 
		        		editable: true,
		        		value: 'x-fa fa-folder',
		        		emptyText: 'εΎζ ζ ·εΌ',
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
		    					title: 'εΎζ εΊ',
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
		    									 	type: 'εΎζ '
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
			    		    									iconCls:γ'x-fa ' + ele.get('name')
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
	    			fieldLabel:'εθ½ζθΏ°'
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
				title: 'θεδΏ‘ζ―',
				closeAction:'close',
				maximizable: true,
				modal: true,
				width: 520,
				height: 540,
				layout: 'fit',
				floating:true,
			    buttons: [{
			    	type: 'button', text: 'η‘?ε?',handler: function(t) { 
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
         				            html: '<div style="font-size:14px;padding-top:5px;"><span class="x-fa fa-check-circle" style="color:#f5e3aa;font-size:16px;"></span>&nbsp;&nbsp;δΏε­ζεοΌ</div>',
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
		         				            html: '<div style="font-size:14px;padding-top:5px;"><span class="x-fa fa-info" style="color:#f5e3aa;font-size:16px;"></span>&nbsp;&nbsp;θ―·ζ£ζ₯θ‘¨εδΏ‘ζ―ζ―ε¦ε‘«εε?ζ΄οΌ</div>',
		         				            closable: false,
		         				            align: 't',
		         				            slideInDuration: 200,
		         				            minWidth: 400
		         				        });
				    	                break;
				    	            case Ext.form.action.Action.CONNECT_FAILURE:
				    	                Ext.Msg.alert('θ­¦ε', 'θΏζ₯ζε‘ε¨ε€±θ΄₯');
				    	                break;
				    	            case Ext.form.action.Action.SERVER_INVALID:
				    	               Ext.Msg.alert('θ­¦ε', action.result.msg);
				    	       }
				    	    }
		        	    });
	            	}
			    }, {
			    	type: 'button', text: 'ε³ι­',handler: function(t) {
			    		t.up('window').close();
		    		}
		    	}],
			    items: [{
			    	xtype: 'menuinfopanel'
			    }]
			});
	    	
	    	var menuTree = GetMenuTreePanel({
	    		rootVisible: true,
	    		rootText:'εθ½θε',
	    		columns : [{
	    			text : 'θεεη§°',
	    			xtype: 'treecolumn',
	    			dataIndex : 'text',
	    			flex : 2
	    		}, {
	    			text : 'θ?Ώι?ε°ε',
	    			dataIndex : 'entrypoint',
	    			flex : 2
	    		}, {
	    			text : 'ζεΊεΌ',
	    			dataIndex : 'sequence',
	    			flex : 1
	    		}, {
	    			text : 'XType',
	    			dataIndex : 'xtype',
	    			flex : 1
	    		}, {
	    			text : 'εΎζ ζ ·εΌ',
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
            			        text: 'ζ°ε’θε',
            			        handler:function(){
            			        	var menuwin = Ext.create('admin.system.MenuPanel.MenuInfoForm');
            			        	var menuwinF = menuwin.down('form').form;
            			        	menuwinF.findField('parentid').setValue(re.data.id);
            			        	menuwin.show();
            			        }
            			    },{
            			    	hidden:re.data.id==1,
            			    	iconCls:'x-fa fa-edit',
            			        text: 'δΏ?ζΉθε',
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
									        console.log('ιθ――δ»£η οΌ ' + response.status);
									    }
									});
            			        }
            			    },{
            			    	hidden:re.data.id==1,
            			    	iconCls:'x-fa fa-minus',
            			        text: 'ε ι€θε',
            			        handler: function(){
            			        	Ext.Msg.confirm('ζη€Ί', 'ε ι€ζ­€θεε°δΌηΊ§θε ι€ζ­€θεδΈηε­θεοΌζ¨η‘?θ?€ε?', function(btn){
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
            	    	     				            html: '<div style="font-size:14px;padding-top:5px;"><span class="x-fa fa-check-circle" style="color:#f5e3aa;font-size:16px;"></span>&nbsp;&nbsp;ε ι€ζεοΌ</div>',
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
            	    						    	Ext.Msg.alert('ζδΊ€','ε ι€ε€±θ΄₯οΌ');
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
						        console.log('ιθ――δ»£η οΌ ' + response.status);
						    }
						});
	        		}
	    		}
	    	});
	    	
	    	Ext.apply(me, {
	    		items: [{
	    	    	region:'center',
	    	    	title:'θεδΏ‘ζ―ζ ',
	    	    	layout: 'fit',
	    	    	frame: true,
	    	    	items: menuTree/* [{
	    	    		xtype: 'treepanel',
	    	    		columnLines: true,
	    	    		reference: 'tree-panel',
	    	    		rowLines: true,
	    	    		title : 'εθ½θε',
	    	    		useArrows: true,
	    	    		rootVisible: false,
	    	    		columnLines: true,
	    	    	    rowLines: true,
	    	    		reserveScrollbar: true,

	    	    		tbar : [ '->', {
	    	    			xtype : 'button',
	    	    			iconCls : 'x-fa fa-plus',
	    	    			text : 'ζ°ε’',
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
	    	    					Ext.Msg.alert("ζη€Ί","θ―·ειζ©ηΆηΊ§θε!");
	    	    				}
	    	    			}
	    	    		}, '-', {
	    	    			xtype : 'button',
	    	    			iconCls : 'x-fa fa-edit',
	    	    			text : 'δΏ?ζΉ',
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
									        	Ext.Msg.alert("ζη€Ί","δΈθ½δΏ?ζΉζ Ήθε!");
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
									        console.log('ιθ――δ»£η οΌ ' + response.status);
									    }
									});
	    	    				}else{
	    	    					Ext.Msg.alert("ζη€Ί","θ―·ειζ©θ¦δΏ?ζΉηθ‘!");
	    	    				}
	    	    			}
	    	    		}, '-', {
	    	    			xtype : 'button',
	    	    			iconCls : 'x-fa fa-remove',
	    	    			text : 'ε ι€',
	    	    			handler: function(){
	    	    				var menumanagerlist = this.up('treepanel');
	    	    				if(menumanagerlist.getSelectionModel().hasSelection()){
	    	    					var selection = menumanagerlist.getSelectionModel().getSelection();
	    	    		    		var name = selection[0].get('text');
	    	    		    		Ext.MessageBox.confirm('ζη€Ί', 'ε ι€θεοΌ ' + name + '<br>εζΆδΌε ι€ζζε­θεοΌη‘?ε?ε ι€οΌ', function(btn, text) {
	    	    			    		if(btn == 'yes') {
	    	    			    			Ext.Ajax.request({
	    									    url: 'menu/delete',
	    									    params: {
	    									    	menuid: selection[0].get('id')
	    						    		    },
	    									    success: function(response, opts) {
	    									    	menumanagerlist.getStore().reload();
			    	    			    			Ext.Msg.alert("ζη€Ί","ε ι€ζε!");
	    									    },
	    									    failure: function(response, opts) {
	    									        console.log('ιθ――δ»£η οΌ ' + response.status);
	    									    }
	    									});
	    	    			    		}
	    	    			    	}, this);
	    	    		    	}else {
	    	    					Ext.Msg.alert("ζη€Ί","θ―·ειζ©θ¦ε ι€ηθ‘!");
	    	    				}
	    	    			}
	    	    		}],

	    	    		store: menuStore,

	    	    		columns : [ {
	    	    			text : 'θεεη§°',
	    	    			xtype: 'treecolumn',
	    	    			dataIndex : 'text',
	    	    			flex : 2
	    	    		}, {
	    	    			text : 'θ?Ώι?ε°ε',
	    	    			dataIndex : 'entrypoint',
	    	    			flex : 2
	    	    		}, {
	    	    			text : 'XType',
	    	    			dataIndex : 'xtype',
	    	    			flex : 1
	    	    		}, {
	    	    			text : 'εΎζ ζ ·εΌ',
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
