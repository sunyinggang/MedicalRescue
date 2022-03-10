Ext.define('Ext.form.MultiSelect', {
    extend: 'Ext.form.ComboBox',
    alias: 'widget.multicombobox',
    xtype: 'multicombobox',
    initComponent: function() {
        this.multiSelect = true;
        this.listConfig = {
            itemTpl: Ext.create('Ext.XTemplate',
                    '<div class="x-field x-form-item x-form-item-default x-form-type-checkbox  x-toolbar-item x-field-default x-hbox-form-item x-form-dirty" >'+
            		'<div  data-ref="bodyEl" class="x-form-item-body x-form-item-body-default x-form-cb-wrap x-form-cb-wrap-default  ">'+
            		/*'<div class="x-form-cb-wrap-inner x-form-cb-wrap-inner-no-box-label" role="presentation">'+
            		'<input type="button"  data-ref="inputEl" role="checkbox" tabindex="0" class="x-form-field x-form-checkbox x-form-checkbox-default x-form-cb x-form-cb-default " autocomplete="off" hidefocus="true">'+
            		'</div>'+*/
            		'<span data-ref="displayEl" role="presentation" class="x-form-field x-form-checkbox x-form-checkbox-default x-form-cb x-form-cb-default " style="margin-left:7px;">'+
            		'<input type="checkbox" name="fav-animal-dog" data-ref="inputEl" class="x-form-cb-input" autocomplete="off" hidefocus="true" aria-hidden="false" aria-disabled="false" aria-invalid="false">'+
            		'</span>'+
            		'</div>'+
            		'<label data-ref="labelEl" class="x-form-item-label x-form-item-label-default  x-unselectable" style="padding-right:5px;width:105px;"><span class="x-form-item-label-inner x-form-item-label-inner-default" style="width:100%">&nbsp;{[values.' + this.displayField + ']}</span></label></div>'),
            onItemSelect: function(record) {
                var node = this.getNode(record);
                if (node) {
                    //添加选中行的蓝色样式
                	//Ext.fly(node).addCls(this.selectedItemCls);
                    //Ext.fly(node).setStyle({borderBottom:'1px solid #fff'})
                    var checkboxs = node.getElementsByTagName("div");
                    if (checkboxs != null)
                    {
                        var checkbox = checkboxs[0];
                        checkbox.className = 'x-field x-form-item x-form-item-default x-form-type-checkbox  x-toolbar-item x-field-default x-hbox-form-item x-form-cb-checked x-form-dirty';
                    }
                }
            },
            onItemDeselect: function(record) {
                var node = this.getNode(record);
                if (node) {
                    //去掉选中行的蓝色样式
                    //Ext.fly(node).removeCls(this.selectedItemCls);
                    var checkboxs = node.getElementsByTagName("div");
                    if (checkboxs != null)
                    {
                        var checkbox = checkboxs[0];
                        checkbox.className = 'x-field x-form-item x-form-item-default x-form-type-checkbox  x-toolbar-item x-field-default x-hbox-form-item x-form-dirty';
                    }
                }
            },
            listeners: {
                itemclick: function(view, record, item, index, e, eOpts) {
                    var isSelected = view.isSelected(item);
                    var checkboxs = item.getElementsByTagName("div");
                    if (checkboxs != null)
                    {
                        var checkbox = checkboxs[0];
                        if (!isSelected)
                        {
                            checkbox.className = 'x-field x-form-item x-form-item-default x-form-type-checkbox  x-toolbar-item x-field-default x-hbox-form-item x-form-cb-checked x-form-dirty';
                        } else {
                            checkbox.className = 'x-field x-form-item x-form-item-default x-form-type-checkbox  x-toolbar-item x-field-default x-hbox-form-item x-form-dirty';
                        }
                    }
                }
            }
        }
        this.callParent();
    }
});