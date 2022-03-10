Ext.define('Ext.filtergrid.Panel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.filtergrid',
    xtype: 'filtergrid',
    initComponent: function() {
    	var me = this;
    	this.getStore().addListener('beforeload', function(st,options){
    		var ep = me.getStore().getProxy().extraParams;
    		me.getStore().getProxy().extraParams = Ext.apply(ep, me.getQueryData());
    	});
    	
    	if(me.bbar&&me.bbar.xtype=='pagingtoolbar'&&!me.bbar.hidePerPageCount){
	    	me.bbar.items = ['-','每页',
	    	    Ext.create('Ext.form.field.ComboBox', {
				    store: {
				    	fields: ['id', 'size'],  
	                    data: [{"id":10, "size":10}, {"id":20, "size":20}, {"id":30, "size":30}, {"id":40, "size":40}, {"id":50, "size":50}, {"id":80, "size":80}, {"id":100, "size":100}]  
				    },
				    editable:false,
				    queryMode: 'local',
				    displayField: 'size',
				    valueField: 'id',
				    hideLabel:true,
	                width: 80,
	                value: me.bbar.pageSize||20,
	                listeners:{
						select: function(cb){
							var size = cb.getValue();  
							me.getStore().setPageSize(size);
							me.getStore().load({limit:size});
							
							cb.blur();
	         			}  
	                }
				})
	    	,'条'];
    	}
        this.callParent();
    },
    getQueryData : function(){
    	var me = this;
    	var queryData = {/*start:0,page:1*/};
    	Ext.each(me.columns, function(c, i){
			if(c.xtype=='filtercolumn'){
				var key = c.filterField||c.dataIndex;
				if(c.filterControl=='textfield'){
					queryData[key] = c.down('textfield').getValue();
				}else if(c.filterControl=='daterangepicker'){
					queryData[key] = c.down('daterangepicker').getValue();
				}else if(c.filterControl=='combo'||c.filterControl=='combobox'){
					queryData[key] = c.down('combo').getValue();
				}else if(c.filterControl=='multicombo'||c.filterControl=='multicombobox'){
					if(c.down('multicombobox').getValue()){
						if(c.filterType && c.filterType=='string' && c.down('multicombobox').getValue().length>0) {
							queryData[key] = c.down('multicombobox').getValue().join('\',\'');
							queryData[key] = "'"+queryData[key]+"'";
						}else{
							queryData[key] = c.down('multicombobox').getValue().join(',');
						}
					}else{
						queryData[key] = '';
					}
				}
			}
		});
    	return queryData;
    },
    clearQueryData: function(){
    	var me = this;
    	Ext.each(me.columns, function(c, i){
			if(c.xtype=='filtercolumn'){
				if(c.filterControl=='textfield'){
					c.down('textfield').reset();
				}else if(c.filterControl=='daterangepicker'){
					c.down('daterangepicker').reset();
				}else if(c.filterControl=='combo'||c.filterControl=='combobox'){
					c.down('combo').reset();
				}else if(c.filterControl=='multicombo'||c.filterControl=='multicombobox'){
					c.down('multicombobox').reset();
				}
			}
		});
    	me.getStore().load({start:0,page:1});
    }
});