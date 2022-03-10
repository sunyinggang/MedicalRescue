Ext.define('Ext.form.DateRangePicker', {
    extend: 'Ext.form.field.Picker',
    alias: 'widget.daterangepicker',
    xtype: 'daterangepicker',
    triggerCls:'x-fa fa-calendar',
    matchFieldWidth: false,
    initComponent: function() {
	    this.createPicker = function(){
	    	var me = this;
	    	return Ext.create('Ext.window.Window', {
	    		header:false,
	    		width:660,
	    		border:false,
	    		resizable:false,
    		    layout: 'fit',
    		    closable:false,
    		    items: [{
    		    	layout:'hbox',
    		    	items:[{
    		    		flex:1,
    		    		layout:'form',
    		    		title:'开始时间',
    		    		titleAlign:'center',
    		    		items:{
    		    			format:'Y-m-d',
    		    			xtype: 'datepicker'
    		            }
    		    	},{
    		    		flex:1,
    		    		layout:'form',
    		    		titleAlign:'center',
    		    		title:'结束时间',
    		    		tools: [{
    		    			iconCls: 'x-fa fa-refresh',
    		    		    tooltip:'清空',
    		    		    handler:function(t){
    		    		    	var d = new Date();
    		    		    	var startDP = this.up('window').down('datepicker');
    		    		    	startDP.setValue(d);
    		    		    	this.up('panel').down('datepicker').setValue(d);
    		    		    	me.setValue('');
    		    		    	me.collapse();
    		    		    }
    		    		}],
    		    		items:[{
    		    			xtype: 'datepicker',
    		    			format:'Y-m-d',
		                    handler: function(t2){
		                    	var startDP = t2.up('window').down('datepicker');
		                    	var startD = Ext.Date.format(new Date(startDP.getValue()),'Y-m-d');
		                    	var endD = Ext.Date.format(new Date(t2.getValue()),'Y-m-d');
		                    	if(startD>endD){
		                    		Ext.toast({
		     				            html: '<div style="font-size:14px;padding-top:5px;"><span class="x-fa fa-info" style="color:#f5e3aa;font-size:16px;"></span>&nbsp;&nbsp;开始时间不能大于结束时间</div>',
		     				            closable: false,
		     				            align: 't',
		     				            slideInDuration: 200,
		     				            minWidth: 300
		     				        });
		                    	}else{
		                    		me.setValue(startD+' - '+endD);
		                    		me.collapse();
		                    	}
		                    }
    		    		}]
    		    	}]
    		    }]
    		}).show();
	    },
        this.callParent();
    }
});