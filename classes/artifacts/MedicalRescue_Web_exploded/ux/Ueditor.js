Ext.define('ux.Ueditor', {
	extend:'Ext.form.field.Text',
    alias: ['widget.ueditor'],
    //alternateClassName: 'Ext.form.UEditor',
    requires: [
               'Ext.form.field.VTypes',
               'Ext.form.trigger.Trigger',
               'Ext.util.TextMetrics'
           ],
    fieldSubTpl: [
        '<textarea id="{id}" {inputAttrTpl}',
            '<tpl if="name"> name="{name}"</tpl>',
            '<tpl if="rows"> rows="{rows}" </tpl>',
            '<tpl if="cols"> cols="{cols}" </tpl>',
            '<tpl if="placeholder"> placeholder="{placeholder}"</tpl>',
            '<tpl if="size"> size="{size}"</tpl>',
            '<tpl if="maxLength !== undefined"> maxlength="{maxLength}"</tpl>',
            '<tpl if="readOnly"> readonly="readonly"</tpl>',
            '<tpl if="disabled"> disabled="disabled"</tpl>',
            '<tpl if="tabIdx"> tabIndex="{tabIdx}"</tpl>',
    //            ' class="{fieldCls} {inputCls}" ',
            '<tpl if="fieldStyle"> style="{fieldStyle}"</tpl>',
            ' autocomplete="off">\n',
            '<tpl if="value">{[Ext.util.Format.htmlEncode(values.value)]}</tpl>',
        '</textarea>',
        {
            disableFormats: true
        }
    ],
    ueditorConfig: {},
    initialized: false,
    initComponent: function () {
        var me = this;
        me.addStateEvents('initialize', 'change');
        me.callParent(arguments);
        me.initField();
    },
    afterRender: function () {
        var me = this;
        var toolbarstate= me.toolbarstate;
        var height = me.height;
        me.callParent(arguments);
        UE.registerUI('toggletoolbar',function(editor,uiName){
            //注册按钮执行时的command命令，使用命令默认就会带有回退操作
            editor.registerCommand(uiName,{
            execCommand:function(){
            console.log(editor.options);
            var getEditorId=editor.key;
            if(me.ue){
           	 console.log(me);	
           }
            var getEditorWidth=editor.options.initialFrameWidth;
            var getEditorHeight=editor.options.initialFrameHeight;
            this.destroy();
            if(toolbarstate==0){
                me.ue = UE.getEditor(getEditorId, Ext.apply(me.ueditorConfig, {toolbars:[[
                'source', '|', 'undo', 'redo', '|',
                'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                'directionalityltr', 'directionalityrtl', 'indent', '|',
                'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
                'simpleupload', 'insertimage', 'emotion', 'scrawl', 'insertvideo', 'music', 'attachment', 'map', 'gmap', 'insertframe', 'insertcode', 'webapp', 'pagebreak', 'template', 'background', '|',
                'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
                'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
                'print', 'preview', 'searchreplace', 'help', 'drafts'
                ]],initialFrameWidth:getEditorWidth,initialFrameHeight:getEditorHeight}));
                toolbarstate=1;
                }else{
                me.ue = UE.getEditor(getEditorId, Ext.apply(me.ueditorConfig, {toolbars:[[
                'source', '|', 'undo', 'redo', '|',
                'paragraph','fontfamily','fontsize','|',
                'bold', 'italic', 'underline', 'fontborder',  'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist','|',
                'simpleupload', 'insertimage','|', 'selectall', 'cleardoc'
                ]],initialFrameWidth:getEditorWidth,initialFrameHeight:getEditorHeight}));
                toolbarstate=0;
                }
            me.ue.ready(function () {
                me.UEditorIsReady = true;
                me.initialized = true;
                me.fireEvent('initialize', me);
                me.ue.addListener('contentChange', function () {
                    me.fireEvent('change', me);
                });
            });

            }
            });


            //创建一个button
            var btn = new UE.ui.Button({
            //按钮的名字
            name:uiName,
            //提示
            title:'简单/复杂工具栏',
            //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
            cssRules :'background-position: -400px -40px;',
            //点击时执行的命令
            onclick:function () {
            //这里可以不用执行命令,做你自己的操作也可
            editor.execCommand(uiName);
            }
            });


            //当点到编辑内容上时，按钮要做的状态反射
            editor.addListener('selectionchange', function () {
            var state = editor.queryCommandState(uiName);
            if (state == -1) {
            btn.setDisabled(true);
            btn.setChecked(false);
            } else {
            btn.setDisabled(false);
            btn.setChecked(state);
            }
            });


            //因为你是添加button,所以需要返回这个button
            return btn;
            },[0]/*index 指定添加到工具栏上的那个位置，默认时追加到最后,editorId 指定这个UI是那个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮*/);
        if (!me.ue) {
        	if(toolbarstate==0){
                me.ue = UE.getEditor(me.getInputId(), Ext.apply(me.ueditorConfig, {toolbars:[[
                'source', '|', 'undo', 'redo', '|',
                'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                'directionalityltr', 'directionalityrtl', 'indent', '|',
                'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
                'simpleupload', 'insertimage', 'emotion', 'scrawl', 'insertvideo', 'music', 'attachment', 'map', 'gmap', 'insertframe', 'insertcode', 'webapp', 'pagebreak', 'template', 'background', '|',
                'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
                'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
                'print', 'preview', 'searchreplace', 'help', 'drafts'
                ]],initialFrameHeight:height}));
                toolbarstate=1;
                }else{
                me.ue = UE.getEditor(me.getInputId(), Ext.apply(me.ueditorConfig, {toolbars:[[
                'source', '|', 'undo', 'redo', '|',
                'paragraph','fontfamily','fontsize','|',
                'bold', 'italic', 'underline', 'fontborder', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist','|',
                'simpleupload', 'insertimage','|', 'selectall', 'cleardoc'
                ]],initialFrameHeight:height}));
                toolbarstate=0;
                }
           /* me.ue = UE.getEditor(me.getInputId(), Ext.apply(me.ueditorConfig, {
                initialFrameHeight: height,
                //initialFrameWidth: width
            }));*/
        	
            me.ue.ready(function () {
                me.UEditorIsReady = true;
                me.initialized = true;
                me.fireEvent('initialize', me);
                me.ue.addListener('contentChange', function () {
                    me.fireEvent('change', me);
                });
            });
             
　　　　　　//这块 组件的父容器关闭的时候 需要销毁编辑器 否则第二次渲染的时候会出问题 可根据具体布局调整
            var win = me.up('panel').up('window');
            if (win && win.closeAction == "hide") {
                win.on('beforehide', function () {
                    me.onDestroy();
                });
            } else {
                var panel = me.up('panel');
                if (panel && panel.closeAction == "hide") {
                    panel.on('beforehide', function () {
                        me.onDestroy();
                    });
                }
            }
        } else {
            me.ue.setContent(me.getValue());
        }
    },
   getValue: function () {
	   var me = this;
       if (me.UEditorIsReady) {
          me.ue.sync(me.getInputId());
      }
       //alert(me.rawValue);
      v = (me.inputEl ? me.inputEl.getValue() : Ext.valueFrom(me.value, ''));
      me.value = v;
      return v;
    },
    setValue: function (value) {
        var me = this;
        if (!me.ue) {
            me.setRawValue(me.valueToRaw(value));
        } else {
            me.ue.ready(function () {
                me.ue.setContent(value);
            });
        }
        me.callParent(arguments);
        return me.mixins.field.setValue.call(me, value);
    },
   getRawValue: function () {
        var me = this;
         if (me.UEditorIsReady) {
            me.ue.sync(me.getInputId());
        }
        v = (me.inputEl ? me.inputEl.getValue() : Ext.valueFrom(me.rawValue, ''));
        me.rawValue = v;
        return v;
    },
    
    destroyUEditor: function () {
        var me = this;
        if (me.rendered) {
            try {
                me.ue.destroy();
                var dom = document.getElementById(me.id);
                if (dom) {
                    dom.parentNode.removeChild(dom);
                }
                me.ue = null;
            } catch (e) { }
        }
    },
    onDestroy: function () {
        var me = this;
        me.callParent();
        me.destroyUEditor();
    },
    setReadOnly: function(bool) {
    	this.setDisabled(bool);
    },
    getUEditor:function(){
    	var me = this;
    	return me;
    }
});