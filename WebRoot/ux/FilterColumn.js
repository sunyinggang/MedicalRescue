Ext.define('Ext.grid.column.FilterColumn', {
    extend: 'Ext.grid.column.Column',
    alias: 'widget.filtercolumn',
    xtype: 'filtercolumn',
    initComponent: function() {
    	var me = this,
    		filterControl = me.filterControl||'textfield',
    		filterEditable = me.filterEditable||(me.filterEditable==undefined),
    		filterDelay = me.filterDelay||500;
    	var grid = me.up('grid');
    	if(filterControl=='textfield'){
    		me.items = {
	        	xtype: 'textfield',
	            flex : 1,
	            margin: 2,
	            enableKeyEvents: true,
	            listeners: {
	            	keyup: function(){
	            		/*grid.getStore().load({
	            		    params: grid.getQueryData()
	            		});*/
	            		grid.getStore().load({start:0,page:1});
	            	},
	                buffer: filterDelay
	            }
	        };
    	}else if(filterControl=='daterangepicker'){
    		me.items = {
	        	xtype: 'daterangepicker',
	            flex : 1,
	            margin: 2,
	            editable: filterEditable,
	            listeners: {
	            	collapse: function(){
	            		/*grid.getStore().load({
	            		    params: grid.getQueryData()
	            		});*/
	            		grid.getStore().load({start:0,page:1});
	            	},
	                buffer: filterDelay
	            }
	        }
    	}else if(filterControl=='combo'||filterControl=='combobox'){
    		me.items = {
	        	xtype: 'combo',
	        	flex:1,
	            margin: 2,
	            editable: filterEditable,
	            hideLabel:true,
	            store: me.filterStore,
			    displayField: me.filterDisplayField,
			    valueField: me.filterValueField,
	            enableKeyEvents: true,
	            tpl: Ext.create('Ext.XTemplate',
	            	'<tpl for=".">',
	            		'<div class="x-boundlist-item" style="height:30px">{'+me.filterDisplayField+'}</div>',
	            	'</tpl>'
	            ),
	            listeners: {
	            	select: function(){
	            		/*grid.getStore().load({
	            		    params: grid.getQueryData()
	            		});*/
	            		grid.getStore().load({start:0,page:1});
	            	},
	                buffer: filterDelay
	            }
	        }
    	}else if(filterControl=='multicombo'||filterControl=='multicombobox'){
    		me.items = {
	        	flex: 1,
	            margin: 2,
	            hideLabel: true,
	            editable: filterEditable,
	            store: me.filterStore,
			    displayField: me.filterDisplayField,
			    valueField: me.filterValueField,
	            enableKeyEvents: true,
				xtype: 'multicombobox',
				listeners: {
	            	select: function(){
	            		/*grid.getStore().load({
	            		    params: grid.getQueryData()
	            		});*/
	            		grid.getStore().load({start:0,page:1});
	            	},
	                buffer: filterDelay
	            }
	        }
    	}else if(filterControl=='panel'){
    		me.items = {
	        	flex: 1,
	            margin: 2,
				xtype: 'panel'
	        }
    	}
        this.callParent();
    }
});