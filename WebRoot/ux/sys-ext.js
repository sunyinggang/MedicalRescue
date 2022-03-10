
function isObject(o){
	return o!=null && Object.prototype.toString.apply(o) === "[object Object]";
}

//系统菜单(表格)树-异步树-------------------start------------------------------
function GetMenuTreePanel(config){
	
	var config = config||{},
		rootVisible = config.rootVisible||false,
		authOnly = (config.authOnly==undefined||config.authOnly)?true:false,//取能访问和授权的菜单
		startId = config.startId||'',//非整棵树是根节点id，不穿如这位登录用户的机构id
		listeners = config.listeners||{},
		rootText=config.rootText||'功能菜单',
		columns = config.columns || '';
	Ext.define('admin.organization.MenuPanel.MenuTreeStore', {
		extend: 'Ext.data.TreeStore',
	    xtype: 'menutreestore',
		lazyFill: true,
		root:{
			nodeType: 'async',
			text: rootText,
			expanded:true,
			id: 1
		},
		proxy: {
			type: 'ajax',
			url: 'menu/tree'+(authOnly?'?auth=1':''),
			reader: {
				type: 'json',
				transform: function(data) {
					Ext.each(data, function(d ,i){
						if(config.checkable==undefined||!config.checkable){
							delete d.checked;
						}
						d.iconCls = d.icon;
						delete d.icon;
					});
                    return data;
                }
			}
		}
	});
	if(columns){
		Ext.define('admin.organization.MenuPanel.MenuTreePanel', {
			extend:'Ext.tree.Panel',
			columns: columns,
			columnLines: true,
			animate:true,
		    rowLines: true,
			reserveScrollbar: true,
			rowLines: true,
		    rootVisible: rootVisible,
		    useArrows:true
		});
	}else{
		Ext.define('admin.organization.MenuPanel.MenuTreePanel', {
			extend:'Ext.tree.Panel',
		    rootVisible: rootVisible,
		    animate:true,
		    useArrows:true
		});
	}
	var tp = Ext.create('admin.organization.MenuPanel.MenuTreePanel',{
		store:Ext.create('admin.organization.MenuPanel.MenuTreeStore')
	});
	Ext.each(listeners, function(Ljson){
		for(var key in Ljson){
			tp.on(key, Ljson[key], this);
		}
	});
	return tp;
}
//系统菜单(表格)树-异步树-------------------end------------------------------