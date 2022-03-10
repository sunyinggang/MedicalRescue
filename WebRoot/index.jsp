<%@page import="com.jointthinker.framework.common.util.JSONutil"%>
<%@page import="com.jointthinker.framework.common.util.DateUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//String systemMenu = (String)request.getAttribute("systemMenu");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>中国人民武装警察部队突发事件卫生应急指挥模拟教学软件</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<!-- link rel="stylesheet" type="text/css" href="ext6/build/packages/font-awesome/resources/font-awesome-all.css" -->
	<link rel="stylesheet" type="text/css" href="font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="ext6/classic/theme-triton/resources/theme-triton-all.css">
	<link rel="stylesheet" type="text/css" href="ext6/classic/theme-triton/resources/app-all.css">
	<link rel="stylesheet" type="text/css" href="css/app.css">
	<script type="text/javascript" src="ext6/ext-all.js"></script>
	<script type="text/javascript" src="ext6/classic/locale/locale-zh_CN-debug.js"></script>
	<script type="text/javascript" src="ext6/classic/theme-triton/theme-triton.js"></script>
	<script type="text/javascript" src="js/extcommonjs/vtype_tools.js"></script>
	<script type="text/javascript" src="js/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="js/ueditor/ueditor.all.js"></script>
    <script type="text/javascript" src="js/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" src="ux/Ueditor.js"></script>
    <script type="text/javascript" src="ux/Overrides.js"></script>
    <script type="text/javascript" src="ux/TextAction.js"></script>
    <script type="text/javascript" src="ux/MultiSelect.js"></script>
    <script type="text/javascript" src="ux/DateRangePicker.js"></script>
    <script type="text/javascript" src="ux/FilterColumn.js"></script>
    <script type="text/javascript" src="ux/FilterGrid.js"></script>
    <script type="text/javascript" src="ux/MultiFileField.js"></script>
    <script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
    <!-- 文档在线预览 -->
	<script type="text/javascript" src="openoffice/js/flexpaper_flash.js"></script>
	<!-- 图片在线预览 -->
    <link rel="stylesheet" type="text/css" href="js/viewer/viewer.min.css">
    <script type="text/javascript" src="js/viewer/viewer.min.js"></script>
    <script type="text/javascript" src="ux/sys-ext.js"></script>
    <!-- 百度地图 -->
    <script src="http://api.map.baidu.com/api?v=2.0&ak=EZPCgQ6zGu6hZSmXlRrUMTpr"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
	<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" />
	<script src="js/map/map.jquery.min.js"></script>
	<script src="js/map/jquery.baiduMap.min.js"></script>
	
	<script type="text/javascript" src="js/mp4/mp4.js"></script>
	<style type="text/css">
	.red{
	 color: #bb1919
	}
	.green{
	 color: #24904a
	}
	.gray{
	 color:#5fa2dd;
	 font: 14px/1 FontAwesome;
     margin-top: 2px;
	}
	.alert-danger {
	    color: #a94442;
	    background-color: #f2dede;
	    border-color: #ebccd1;
	}
	.alert {
	    padding: 15px;
	    margin-bottom: 20px;
	    border: 1px solid transparent;
	    border-radius: 4px;
	}
	.radio-disabeld{
		cursor: default;
		opacity: 1;
		background-color:#fff;
		-moz-opacity: 1;
		filter: alpha(opacity=100);
	}
	.x-menu-item-text-default.x-menu-item-indent-no-separator {
	    margin-left: 30px;
	}
	
	 .bMap{position: relative;}
	 .bMap .map-warp{position: absolute;left:0;width:100%;height:800px;top:34px;display: none;}
	 .bMap input{width:100%;height:30px;line-height: 30px;border:1px solid #d7d7d7;}
	 .maptitle{margin:0 0 5px 0;padding:0.2em 0;font-size: 16px;font-weight: bold;}
	</style>
	<style type="text/css">
	.hpage_win:hover{background-color:  #D0D8E0;}
	</style>
	<script type="text/javascript">
	Ext.getDoc().on("contextmenu", function(e){
	    e.stopEvent();
	});
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
	function addComboStore(e){
		for(var i=0;i<e.items.length;i++){
			var item = e.items[i];
			if(item.xtype=='combo'||item.xtype=='multicombobox'||item.xtype=='gridpanel'){//涉及到store的
				item.store=Ext.create(item.storename);
				if(item.xtype=='gridpanel'){
					item.bbar.store=Ext.create(item.storename);
				}
			}
			
			if(item.items){
				addComboStore(item);
			}
		}
		
	}
	function deletefile(panelid,id,showfiled,fileType){
		 Ext.MessageBox.confirm('提示', '确定删除吗？', function(btn, text) {
	    		if(btn == 'yes') {
	    			Ext.Ajax.request({
						url: '<%=path%>/upload/deletefile?id='+id+'&fileType='+fileType,
						method: 'POST',
						success:function(o){
							var ret = Ext.decode(o.responseText);
							if(ret.success)
							{
								Ext.toast({
		   				            html: '<div style="font-size:14px;padding-top:5px;"><span class="x-fa fa-info" style="color:green;font-size:16px;"></span>&nbsp;&nbsp;删除成功！</div>',
		   				            closable: false,
		   				            align: 't',
		   				            slideInDuration: 200,
		   				            minWidth: 400
		   				        });
							   var pp = $('#attachment_'+id).parent();
							   $('#attachment_'+id).remove();
							   if(pp.children().length==0){
								  if(Ext.getCmp(panelid)&&Ext.getCmp(panelid).xtype=='form'){
									  var form =  Ext.getCmp(panelid);
									  if(showfiled){
										   form.getForm().findField(showfiled).hide();
									   }else{
										   form.getForm().findField('urlshow').hide();
									   }
								  }else{
									  var form =  Ext.getCmp(panelid).query('form')[0];
									   if(form){
										   if(showfiled){
											   form.getForm().findField(showfiled).hide();
										   }else{
											   form.getForm().findField('urlshow').hide();
										   }
										   
									   }
								  }
								    
								   
								  
							   } 
							}
							else
							{
								Ext.MessageBox.alert("提示","操作有误！");
							}											
						},
						failure:function(o){
							Ext.MessageBox.alert("错误信息",o.responseText);
						}
					}); 
	    		}
	    	}, this);
	
	}
	
	function downloadfile(id,fileType){
		if(!fileType||fileType=="undefined"){
			fileType='';
		}
		var form = $("<form>");
        form.attr('style','display:none');
        form.attr('target','');
        form.attr('method','post');
        form.attr('action','upload/downloadfile?id='+id+'&fileType='+fileType);
        $("body").append(form);    
        form.submit();    
        form.remove();  
		
	}
	
	function showphoto(id){
		var viewer = new Viewer(document.getElementById(id));
	}
	function previewphoto(id,className){
		
		var viewer = new Viewer(document.getElementById(id), {
	    	url: 'data-original',
	    	hidden: function () {
	    		viewer.destroy();
	    	},
	    	shown:function(){
	    		//num=num?num:0;
	    		//viewer.view(num);
	    		var imgArr = $("#"+id+" img");
	    		for(var i =0;i<imgArr.length;i++){
	    			if(imgArr[i].className==className){
	    				viewer.view(i);
	    				return;
	    			}
	    		}
	    	}
	    });
		viewer.show(); 
	}

	
	function openPostWindowArray(url, data, name){      
	   	var tempForm = document.createElement("form");
	    tempForm.id="tempForm1";      
	    tempForm.method="post";  
	    tempForm.action=url;  
	    tempForm.target=name;
	    for(var p in data){ 
		    var hideInput = document.createElement("input");     
		    hideInput.type="hidden";  
		    hideInput.name= p;  
		    hideInput.value= data[p];    
		    tempForm.appendChild(hideInput);
		}
	    if(document.all){        
	       	tempForm.attachEvent("onsubmit",function(){ openWindow(name); });
		    document.body.appendChild(tempForm); 
		    tempForm.fireEvent("onsubmit");   
	    }
	    else{
	        tempForm.addEventListener("onsubmit",function(){openWindow(name);},false); 
		    document.body.appendChild(tempForm); 
	        var   evt   =   document.createEvent('HTMLEvents');
	        evt.initEvent('change',true,true);
	        tempForm.dispatchEvent(   evt   );
	    }  
	    tempForm.submit();    
	    document.body.removeChild(tempForm);    
	} 
	
	var fullWidth,fullHeight,
		hugeWidth=1200, hugeHeight=768,
		medWidth=1000, medHeight=550,
		bigWidth=800, bigHeight=600,
		midWidth=600, midHeight=450,
		smallWidth=400, smallHeight=300;
	var accID='';
	var accPoint={};//选择事故坐标
	var medicalPoint={};//选择医疗资源坐标
	var CampArr=[];//营地标点组
	var medicalArr=[];//医疗资源标点组
	var rescueArr=[];//救援协同标点组
	var PointFlag='';
	var startmapStartPoint,startmapEndPoint,endmapStartPoint,endmapEndPoint;
	var medialShow=false;
	var contentPanel;
	var workflowPanel;
	//各类型标点显示/隐藏  type--类型标识  IsView-true显示-false隐藏
	function showOverlay(type,isview){
		if(type=='medical'){//医疗资源
			if(isview){
				if(medicalArr.length==0){
					Ext.Ajax.request({
						url:'<%=path%>/medical/getMedicalMap',
						success:function(o){
							var ret = Ext.decode(o.responseText);
							var points = ret.json;
							for(var i=0;i<points.length;i++){
								var icon={ // 选填--自定义icon图标
			    					url: "js/map/css/medicine.png",
			    					width: 34,
			    					height: 34
			    				}
								var a = bmap.addMarker(points[i],icon);
								medicalArr.push(a);
								
							}
						}
					});
				}else{
					for(var i=0;i<medicalArr.length;i++){
						medicalArr[i].show();
	      			}
				}
			}else{
				for(var i=0;i<medicalArr.length;i++){
					medicalArr[i].closeInfoWindow();
					medicalArr[i].hide();
      			}
			}
			
		}
		if(type=='camp'){//营地
			if(isview){
				for(var i=0;i<CampArr.length;i++){
					CampArr[i].show();
      			}
			}else{
				for(var i=0;i<CampArr.length;i++){
					if(CampArr[i].window){
						CampArr[i].window.close();
					}
					
					CampArr[i].hide();
      			}
			}
		}
		if(type=='rescue'){//救援协同
			if(isview){
				if(rescueArr.length==0){
					Ext.Ajax.request({
						url:'<%=path%>/rescue/getRescueMap',
						success:function(o){
							var ret = Ext.decode(o.responseText);
							var points = ret.json;
							for(var i=0;i<points.length;i++){
								var icon={ // 选填--自定义icon图标
			    					url: "js/map/css/medicine.png",
			    					width: 34,
			    					height: 34
			    				}
								var a=bmap.addMarker(points[i],icon);
								rescueArr.push(a);
								
							}
						}
					});
				}else{
					for(var i=0;i<rescueArr.length;i++){
						rescueArr[i].show();
	      			}
				}
			}else{
				for(var i=0;i<rescueArr.length;i++){
					rescueArr[i].closeInfoWindow();
					rescueArr[i].hide();
      			}
			}
			
		}
	}
	function choiceMedical(id,lat,lng){
		 Ext.MessageBox.confirm('提示', '是否选择该资源？', function(btn, text) {
	    		if(btn == 'yes') {
	    			medicalPoint={
    					lat:lat,
    					lng:lng
    				}
	    			Ext.toast({
			            html: '<div style="font-size:14px;padding-top:5px;"><span class="x-fa fa-check-circle" style="color:#f5e3aa;font-size:16px;"></span>&nbsp;&nbsp;选择成功！</div>',
			            closable: false,
			            align: 't',
			            slideInDuration: 200,
			            minWidth: 400
			        });
	    		}
		 });
		
	}
	Ext.application({
	     name: 'MyApp',
	     launch: function(){
			Ext.setGlyphFontFamily('FontAwesome');
			Ext.Loader.setPath('Ext.ux', 'ext6/packages/ux/classic/src');
			Ext.require(['Ext.ux.FieldReplicator']);
			Ext.window.Window.override({
				initComponent: function () {
					var me = this;
			        me.callParent();
			        me.on({
			        	show: {
			        		fn: function() {
			        			me.center();
			        		},
			        		scope: me
			        	}
			        });
				}
			});
			var accidentStore = Ext.create('Ext.data.Store', {
			    fields:[ 'id','title'],
			    pageSize:20,
		 	 	autoLoad: true,
				proxy : {
					type : 'ajax',
					url : 'accident/WaitAccidentList',
					reader : {
						type : 'json',
						rootProperty: 'rows',
		 			 	totalProperty:'total'
					}
				}
			});

			contentPanel = Ext.create('Ext.tab.Panel', {
	     		region: 'center',
	     		layout: 'fit',
	     	    collapsible: false,
	     	    defaults: {
	     	    	margin: '5',
	     	    	//scrollable: true,
	     	        closable: true
	     	    },
	     	    plugins:Ext.create('Ext.ux.TabCloseMenu',{
	     	    	 closeTabText : '关闭当前标签',
	                 closeOthersTabsText : '关闭其他标签',
	                 closeAllTabsText : '关闭所有标签'
	     	    }),
	     	    items: [{
	     	    	title: '主页',
	     	    	iconCls: 'x-fa fa-home',
	     	    	closable: false,
	     	    	layout: {
	     	    		type:'hbox',
	     	    		pack: 'start',
	        	        align: 'stretch'
	     	    	},
	     	    	id:'HOMEPAGE',
	     	    	//margin: '10 0 0 0',
	     	    	defaults: {
	     	            bodyPadding: 10,
	     	            height: 580,
	     	            scrollable: true
	     	        },
	     	    	items: [{
	     	    		flex:1,
	     	    		id:'accident_gridpanel',
	     	    		xtype:'gridpanel',
	     	    		title: '最新事件列表',
	     			    store:accidentStore,
	     			    collapsible: false,
	     			    frame:true,
	     			    layout:'fit',
	     			    margin: '0 0 0 0',
	     			    bodyPadding: 0,
	     			    scrollable: true,
	     				viewConfig : {
	     					forceFit : true,
	     					enableTextSelection:true
	     				},
	     			    columns: [{
	    	    			xtype: 'textactioncolumn',
	    	    			text : '操作',
	    	                width: 140,
	    	                layout: 'hbox',
	    	                sortable: false,
	    	                menuDisabled: true,
	    	                items: [{
	    	                	iconCls: 'x-fa fa-edit',
	    	                    tooltip: '编辑',
	    	                    text : '编辑',
	    	                    scope: this,
	    	                    handler: function(grid, index) {
	    	                    	var node = grid.getStore().getAt(index).getData();
	    	                    	contentPanel.addTab({
	    	                    		id:'addaccident',
	    	                    		issys:'yes',
	    		    					params:{
	    		    						node:JSON.stringify(node)
	    		    					},
	    		    					url:'accident/AccidentInfoPanel.jsp',
	    		    					text:"新事件",
	    		    					xtype:'accidentinfopanel',
	    		    					iconCls:'x-fa fa-ambulance'
	    		    				});
	    	                    }
	    	                },{
	    	                	iconCls: 'x-fa fa-search',
	    	                    tooltip: '查看原文',
	    	                    text : '查看原文',
	    	                    scope: this,
	    	                    handler: function(grid, index) {
	    	                    	var url = grid.getStore().getAt(index).getData().url;
	    	                    	window.open(url,"_blank");        
	    	                    }
	    	                }]},
	     			        { text: '标题', dataIndex: 'title',flex:1}
	     			    ],
	     			   bbar : {
	   				     xtype: 'pagingtoolbar',
	        			 displayInfo : true,
	        			 pageSize:20,
	        			 store :accidentStore
	   					}
	     	    	}]
	     	    }]
	     	   
	     	});
			
			contentPanel.addTab = function(configs){
				var n = contentPanel.getComponent(configs.id);
				if (n){
					contentPanel.setActiveTab(n);
					return;
				}
				var loader = new Ext.ComponentLoader({
					url : configs.url? configs.url : 'err.jsp',
					params: configs.params,
					autoLoad : true,
					scripts : true,
					renderer : function( loader, response, active) {
						var html = response.responseText;
						//loader.getTarget().setHtml('The response is ' + text);

						//脚本识别  
						var re = /(?:<script([^>]*)?>)((\n|\r|.)*?)(?:<\/script>)/ig;
						//外部脚本识别  
						var srcRe = /\ssrc=([\'\"])(.*?)\1/i;

						var typeRe = /\stype=([\'\"])(.*?)\1/i;

						var match;
						//html中包含有脚本，提炼出来单独执行  
						while (match = re.exec(html)) {
							var attrs = match[1];
							var srcMatch = attrs ? attrs.match(srcRe) : false;

							//外部脚本，在head中添加dom标签，动态载入脚本  
							if (srcMatch
									&& srcMatch[2]) {
								var s = document.createElement("script");
								s.src = srcMatch[2];
								var typeMatch = attrs.match(typeRe);
								if (typeMatch && typeMatch[2]) {
									s.type = typeMatch[2];
								}
								hd.appendChild(s);
							// 内部脚本直接运行    
							} else if (match[2]
									&& match[2].length > 0) {
								if (window.execScript) {
									window.execScript(match[2]);
								} else {
									window.eval(match[2]);
								}
							}
						}
						return true;
					},
					listeners : {
						load : function( me, response, options, eOpts) {
							var tab = {
								id : configs.id,
								title : configs.text ? configs.text : '错误',
								xtype : configs.xtype ? configs.xtype : 'ErrorPanel',
								iconCls : configs.iconCls,
								issys:configs.issys?configs.issys:'no',
								listeners:{
									destroy: function(){
										var fn = configs.callback;
										if(fn){
											fn();
										}
									}
								}
							};
							contentPanel.setActiveTab(tab);
						},
						exception : function( me, response, options, eOpts) {
						},
						beforeload : function( me, options, eOpts) {
						}
					}
				});
			}
			
			contentPanel.refreshGrid = function(tabid, gridindex){
				var tab = contentPanel.getComponent(tabid);
				var grids = tab.query('grid');
				if(gridindex==undefined){
					Ext.each(grids, function(grid){
						grid.getStore().reload();
					});
				}else{
					var grid = grids[gridindex];
					if(grid){
						grid.getStore().reload();
					}
				}
			}
			
			contentPanel.on('tabchange', function(tp, n, o){
				var tpid = n.getId();
				var menulist = Ext.getCmp('navigation-west-panel').down('treelist');
				if(tpid=='HOMEPAGE'){
					menulist.setSelection(null);
					return;
				}
				/* if(tpid=='treemenu_isaccident'){
					menulist.setSelection(null);
					return;
				} */
				if(n.initialConfig.issys=='yes'){
					menulist.setSelection(null);
					return;
				}
				var re = menulist.getStore().findRecord('id',tpid.split('_')[1]);
				if(re){
					menulist.setSelection(re);
				}else{
					menulist.setSelection(null);
				}
			});
			var viewp = Ext.create('Ext.container.Viewport', {
				layout : 'border',
				items : [{
					region:'north',
					height:84,
					bodyStyle:{'background-image':'url(images/banner8.png);background-size:100% 100%;'},
					//style:'margin:1px 0',
					layout:{
						type: 'hbox',
		    	        pack: 'start',
		    	        align: 'stretch'
					},
					items:[{
						width:70,
						hidden:false,
						bodyStyle:{
							'background':'transparent url(images/top1.png) no-repeat;margin-top:7px'
						}
					}/* ,{
						width:160,
						html:'医学救援',
						bodyStyle:{
							'background':'transparent',
							'color':'#fff',
							'padding-left':'5px',
							'padding-top':'25px',
							'font':'36px 华文行楷'
						}
					} */,{
						flex:1,
						html:'中国人民武装警察部队突发事件卫生应急指挥模拟教学软件',
						bodyStyle:{
							'background':'transparent',
							'color':'#fff',
							'padding-top':'32px',
							'font':'36px 华文行楷'
						}
					},{
						width:230,
						bodyStyle:{'background':'transparent'},
						layout:{
							type: 'vbox',
							align:'end'
						},
						items:[{
							flex:1,
							bodyStyle:{'background':'transparent'},
							items:[{
								xtype:'button',
								text:'系统管理',
								tooltip:'系统管理',
								style: 'border-style:hidden;background:transparent;margin-top:30px',
					            iconCls: 'x-fa fa-gears',
					            handler:function(e){
					            	var menuArr=[];
					            	Ext.Ajax.request({
    	    						    url: 'menu/getSysMenu',
    	    						    method:'post',
    	    						    success: function(response, opts) {
    	    						    	var o = Ext.decode(response.responseText);
    	    						    	for(var i=0;i<o.length;i++){
    	    						    		var mep=o[i];
    	    						    		var obj ={
    	    						    				iconCls:mep.icon,
    	    			            			        text: mep.text,
    	    			            			        nodeid:mep.id,
    	    			            			        entrypoint:mep.entrypoint,
    	    			            			        nodextype:mep.xtype,
    	    			            			        nodeiconCls:mep.icon,
    	    			            			        handler:function(e){
    	    			            			        	var n = contentPanel.getComponent('treemenu_'+e.nodeid);
    				        								if (n){
    				        									contentPanel.setActiveTab(n);
    				        									return;
    				        								}
    				        								var loader = new Ext.ComponentLoader({
    				        									url : e.entrypoint? e.entrypoint : 'err.jsp',
    				        									autoLoad : true,
    				        									scripts : true,
    				        									renderer : function( loader, response, active) {
    				        										var html = response.responseText;
    				        										//loader.getTarget().setHtml('The response is ' + text);

    				        										//脚本识别  
    				        										var re = /(?:<script([^>]*)?>)((\n|\r|.)*?)(?:<\/script>)/ig;
    				        										//外部脚本识别  
    				        										var srcRe = /\ssrc=([\'\"])(.*?)\1/i;

    				        										var typeRe = /\stype=([\'\"])(.*?)\1/i;

    				        										var match;
    				        										//html中包含有脚本，提炼出来单独执行  
    				        										while (match = re.exec(html)) {
    				        											var attrs = match[1];
    				        											var srcMatch = attrs ? attrs.match(srcRe) : false;

    				        											//外部脚本，在head中添加dom标签，动态载入脚本  
    				        											if (srcMatch
    				        													&& srcMatch[2]) {
    				        												var s = document.createElement("script");
    				        												s.src = srcMatch[2];
    				        												var typeMatch = attrs.match(typeRe);
    				        												if (typeMatch && typeMatch[2]) {
    				        													s.type = typeMatch[2];
    				        												}
    				        												hd.appendChild(s);
    				        											// 内部脚本直接运行    
    				        											} else if (match[2]
    				        													&& match[2].length > 0) {
    				        												if (window.execScript) {
    				        													window.execScript(match[2]);
    				        												} else {
    				        													window.eval(match[2]);
    				        												}
    				        											}
    				        										}
    				        										return true;
    				        									},
    				        									listeners : {
    				        										load : function( me, response, options, eOpts) {
    				        											var tabid = 'treemenu_'+e.nodeid;
    				        											console.log(mep.issys);
    				        											var tab = {
    				        												id : tabid,
    				        												title : e.text ? e.text : '错误',
    				        												xtype : e.nodextype ? e.nodextype : 'ErrorPanel',
    				        												issys:mep.issys?mep.issys:'no',
    				        												iconCls : e.iconCls
    				        											};
    				        											contentPanel.setActiveTab(tab);
    				        										},
    				        										exception : function( me, response, options, eOpts) {
    				        										},
    				        										beforeload : function( me, options, eOpts) {
    				        										}
    				        									}
    				        								});
    	    			            			        }	
    	    						    		}
    	    						    		menuArr.push(obj);
    	    						    	}
    	    						    	var contextM = Ext.create('Ext.menu.Menu', {
    				            			    width: 150,
    				            			    plain: true,
    				            			    items:menuArr
    						            	});
    	    						    	var xyArr = e.getXY();
    					        			xyArr[1]=xyArr[1]+30;
    				            			contextM.showAt(xyArr);
    	    						    },
    	    						    failure: function(response, opts) {
    	    						    	Ext.Msg.alert('提交','删除失败！');
    	    						    }
    	    						});
					            
				        			
								}
							}]
						}]
							      
					}]
				},{
					title : '&nbsp;培训目录',
					region : 'west',
					bodyPadding: 10,
					split: true,//伸缩属性  若取消中间间隔  改为false
					collapsible:true,
					width : 450,
					layout: {
			            type: 'vbox',
			            align: 'stretch'
			        },
			        border: false,
			        id:'navigation-west-panel',
			        scrollable: 'y',
			        items: [{
			            xtype: 'treelist',
			            border:false,
			            singleExpand:true,
			            expanderOnly:false,
			            highlightPath: true,
			            store: {
			            	root: {
			                    expanded: true
			                },
					        proxy: {
							    type: 'ajax',
							    url: 'menu/getMenu',
							    extraParams: {
							    	id: '1'
							    },
							    reader: {
							        type: 'json',
							        transform: function(data) {
							        	function applyIconCls(data){
							        		for(var i=0; i<data.length; i++){
							        			data[i].iconCls = data[i].icon;
							        			delete data[i].icon;
							        			if(data[i].leaf=='0'){
							        				applyIconCls(data[i].children)
							        			}
							        		}
							        	}
							        	applyIconCls(data);
					                    return data;
					                }
							    }
							}
			            },
			            listeners:{
			            	itemclick: function(_this,re){
			            		if(re.node.data.id==27930){
			            			new mp4Popup("js/mp4/2.mp4",2);
			            		}
			            		if(re.node.data.id==27937){
			            			new mp4Popup("js/mp4/3.mp4",2);
			            		}
			            		if(re.node.data.id==27941){
			            			new mp4Popup("js/mp4/4.mp4",2);
			            		}
			            		if(!re.node.data.leaf){
			            			setTimeout(function(){
			            				var nwp = Ext.getCmp('navigation-west-panel');
				            			nwp.updateLayout();
				            			var n = contentPanel.getActiveTab();
										var tpid = n.getId();
										var menulist = nwp.down('treelist');
										var re = menulist.getStore().findRecord('id',tpid.split('_')[1]);
										if(re){
											menulist.setSelection(re);
										}
				            		},300);
			            		}
			            	},
			            	selectionchange: function(_this, re){
			            		if(!re){return;}
			            		var node = re.raw;
								if(!node.leaf) return;
								if(node.entrypoint==''||node.xtype==''){
									return;
								}
								var n = contentPanel.getComponent('treemenu_'+node.id);
								if (n){
									contentPanel.setActiveTab(n);
									return;
								}
								if(node.isaccident=='1'){
									var accidenttab = contentPanel.getComponent('treemenu_isaccident');
									if (accidenttab){
										accidenttab.close();
									}
								}
								if(node.accidenttype!=null&&node.accidenttype!=''){
									var accidenttab = contentPanel.getComponent('treemenu_isaccident');
									if (accidenttab){
										contentPanel.setActiveTab(accidenttab);
										if(node.accidenttype=='medical'||node.accidenttype=='medical_mp'){
											//Ext.getCmp('checkpanel').down('form').getForm().reset();
											if(node.accidenttype=='medical_mp'){
					            				new mp4Popup("js/mp4/5.mp4",2);
					            			}
											Ext.getCmp('checkpanel').down('form').getForm().findField("medical_checkbox").setValue(true);
										}
										if(node.accidenttype=='startpath'){
											Ext.getCmp('checkpanel').down('form').getForm().findField("start_path").setValue(true);
										}
										if(node.accidenttype=='camp'){
											Ext.getCmp('checkpanel').down('form').getForm().findField("camp_checkbox").setValue(true);
										}
										if(node.accidenttype=='endpath'){
											Ext.getCmp('checkpanel').down('form').getForm().findField("end_path").setValue(true);
										}
										if(node.accidenttype=='rescue'){
											Ext.getCmp('checkpanel').down('form').getForm().findField("rescue").setValue(true);
										}
										return;
									}
									
									
								}
								
								/* if(node.isaccident=='1'){
									var accidenttab = contentPanel.getComponent('treemenu_isaccident');
									if (accidenttab){
										contentPanel.setActiveTab(accidenttab);
										if(bmap){
											bmap.clearOverlays();//清空所有标点
											medicalPoint={};//选择医疗资源坐标
											CampArr=[];//营地标点组
											medicalArr=[];//医疗资源标点组
											rescueArr=[];//救援协同标点组
											PointFlag='';
											startmapStartPoint=null;
											startmapEndPoint=null;
											endmapStartPoint=null;
											endmapEndPoint=null;
											bmap.addMarker(node);
											bmap.setCenter(node.lng,node.lat,15);
											accPoint={
						            			lng:node.lng,
						            			lat:node.lat
						            		}
											bmap.addEventListener("tilesloaded",function(){//加载完成时,触发 
								    			bmap.setCenter(node.lng,node.lat); 
								    		});
											var cboxs = accidenttab.query('checkbox');
											for(var i=0;i<cboxs.length;i++){
												var box = cboxs[i];
												if(box.xtype=='checkbox'){
													box.reset();
												}
											}
											if(node.radius3!=''){
												var circle = new BMap.Circle(new BMap.Point(node.lng,node.lat),node.radius3,{strokeColor:"red", strokeWeight:1,fillColor: "#FFFFE0",  fillOpacity: 0.6}); //创建圆
												bmap.addOverlay(circle);            //增加圆
												bmap.setCenter(node.lng,node.lat,12);
											}
											if(node.radius2!=''){
												var circle = new BMap.Circle(new BMap.Point(node.lng,node.lat),node.radius2,{strokeColor:"#", strokeWeight:1,fillColor: "#FFA500", fillOpacity:0.5}); //创建圆
												bmap.addOverlay(circle);            //增加圆
												bmap.setCenter(node.lng,node.lat,12);
											}
											
											if(node.radius1!=''){
												var circle = new BMap.Circle(new BMap.Point(node.lng,node.lat),node.radius1,{strokeColor:"#", strokeWeight:3,fillColor: "#F70909",fillOpacity:0.5}); //创建圆
												bmap.addOverlay(circle);            //增加圆
												bmap.setCenter(node.lng,node.lat,12);
												circle.addEventListener('click',function(){
													new mp4Popup("js/mp4/1.mp4",2);
												})
											}
											accID=node.mid;
										}
										return;
									}
								} */
								<%-- if(node.isaccident=='2'){//是否为事故相关界面
									var accidenttab = contentPanel.getComponent('treemenu_isaccident');
									if (accidenttab){
										contentPanel.setActiveTab(accidenttab);
										if(bmap&&!medialShow){
											Ext.Ajax.request({
												url:'<%=path%>/medical/getMedicalMap',
												success:function(o){
													var ret = Ext.decode(o.responseText);
													var points = ret.json;
													for(var i=0;i<points.length;i++){
														var icon={ // 选填--自定义icon图标
									    					url: "js/map/css/medicine.png",
									    					width: 34,
									    					height: 34
									    				}
														bmap.addMarker(points[i],icon);
														
														
													}
													if(accPoint){
														bmap.setCenter(accPoint.lng,accPoint.lat,12);
													}
												}
											});
											if(node.id==27942){
												new mp4Popup("js/mp4/5.mp4",2);
											}
											
										}
										return;
									}
								} --%>
								
								var loader = new Ext.ComponentLoader({
									url : node.entrypoint? node.entrypoint : 'err.jsp',
									autoLoad : true,
									scripts : true,
									renderer : function( loader, response, active) {
										var html = response.responseText;
										//loader.getTarget().setHtml('The response is ' + text);

										//脚本识别  
										var re = /(?:<script([^>]*)?>)((\n|\r|.)*?)(?:<\/script>)/ig;
										//外部脚本识别  
										var srcRe = /\ssrc=([\'\"])(.*?)\1/i;

										var typeRe = /\stype=([\'\"])(.*?)\1/i;

										var match;
										//html中包含有脚本，提炼出来单独执行  
										while (match = re.exec(html)) {
											var attrs = match[1];
											var srcMatch = attrs ? attrs.match(srcRe) : false;

											//外部脚本，在head中添加dom标签，动态载入脚本  
											if (srcMatch
													&& srcMatch[2]) {
												var s = document.createElement("script");
												s.src = srcMatch[2];
												var typeMatch = attrs.match(typeRe);
												if (typeMatch && typeMatch[2]) {
													s.type = typeMatch[2];
												}
												hd.appendChild(s);
											// 内部脚本直接运行    
											} else if (match[2]
													&& match[2].length > 0) {
												if (window.execScript) {
													window.execScript(match[2]);
												} else {
													window.eval(match[2]);
												}
											}
										}
										return true;
									},
									listeners : {
										load : function( me, response, options, eOpts) {
											var tabid = 'treemenu_'+node.id;
											if(node.isaccident=='1'||node.accidenttype!=''){//是否为事故相关界面
												tabid='treemenu_isaccident';
											}
											var tab = {
												id : tabid,
												title : node.text ? node.text : '错误',
												xtype : node.xtype ? node.xtype : 'ErrorPanel',
												iconCls : node.iconCls,
												issys:node.issys?node.issys:'no',
												listeners : {
										     		  beforeclose: function(panel, eOpts) {
										     			  if(panel.id=='treemenu_isaccident'){
										     				medicalPoint={};//选择医疗资源坐标
															CampArr=[];//营地标点组
															medicalArr=[];//医疗资源标点组
															rescueArr=[];//救援协同标点组
															PointFlag='';
															startmapStartPoint=null;
															startmapEndPoint=null;
															endmapStartPoint=null;
															endmapEndPoint=null;
										     			  }
										     			 
														}
										     	   }
											};
											contentPanel.setActiveTab(tab);
										},
										exception : function( me, response, options, eOpts) {
										},
										beforeload : function( me, options, eOpts) {
										}
									}
								});
			            	}
			            }
			        }]
				}, contentPanel]
			});
			
		}
	});
	</script>
  </head>
  
  <body>
  </body>
</html>
