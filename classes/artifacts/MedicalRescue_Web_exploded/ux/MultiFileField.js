Ext.define('ux.MultiFile', {
    extend: 'Ext.panel.Panel',
    alias: ['widget.multifilefield', 'widget.multifileuploadfield'],
    alternateClassName: ['Ext.form.FileUploadField', 'Ext.ux.form.FileUploadField', 'Ext.form.File'],
    requires: [
        'Ext.form.field.File'
    ],
    layout: {
        type: 'hbox',
        pack: 'start',
        align: 'stretch'
    },
   	
   	initComponent: function() {
   		var me = this;
   		if (!me.replicatorId) {
   			me.replicatorId = Ext.id();
        }
   		me.bodyPadding=me.bodyPadding?me.bodyPadding:'0 10 0 0';
   		me.size=1;
   		me.items=[{
	    	flex: 50,
	    	margin: '0 10 0 0',
			xtype: 'filefield',
            name: me.name,
            msgTarget: 'side',
            emptyText: '选择一个文件',
            vtype:me.vtype?me.vtype:'',
            fieldLabel: me.fieldLabel,
            buttonText: '选择文件'
		},{
			flex: 1,
			xtype: 'button',
			margin: '0 10 0 0',
           	iconCls:'x-fa fa-plus',
           	handler:function(e){
           		if(me.maxLength){
           			if(me.size>=me.maxLength){
               			Ext.toast({
    				            html: '<div style="font-size:14px;padding-top:5px;"><span class="x-fa fa-check-circle" style="color:#f5e3aa;font-size:16px;"></span>&nbsp;&nbsp;最多不超过'+me.maxLength+'个文件</div>',
    				            closable: false,
    				            align: 't',
    				            slideInDuration: 200,
    				            minWidth: 400
    				        });
               		}else{
               			var ownerCt = me.ownerCt,
        	            replicatorId = me.replicatorId,
        	            clone, idx;
        	            clone = me.cloneConfig({replicatorId: replicatorId});
        	            idx = ownerCt.items.indexOf(me);
        	            ownerCt.add(idx + 1, clone);
                        me.size=me.size+1;
               		}
           		}else{
           			var ownerCt = me.ownerCt,
    	            replicatorId = me.replicatorId,
    	            clone, idx;
    	            clone = me.cloneConfig({replicatorId: replicatorId});
    	            idx = ownerCt.items.indexOf(me);
    	            ownerCt.add(idx + 1, clone);
                    me.size=me.size+1;
           		}
           		
	           	
           	}
		},{
			flex: 1,
			xtype: 'button',
			iconCls:'x-fa fa-remove',
           	handler:function(e){
           		var ownerCt = me.ownerCt,
	            replicatorId = me.replicatorId,
	            siblings = ownerCt.query('[replicatorId=' + replicatorId + ']'),
	            isLastInGroup = siblings[siblings.length - 1] === me,
	            idx;
           		if(siblings.length-1>=1){
           		 idx = ownerCt.items.indexOf(me);
           		 ownerCt.remove(idx);
           		 me.size=me.size-1;
           		}
	           
           		
               
           	}
		}]
		this.callParent();
   	}
    
});
