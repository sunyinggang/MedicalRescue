Ext.define('Ext.grid.column.TextAction', {
    extend: 'Ext.grid.column.Column',
    alias: ['widget.textactioncolumn'],
    alternateClassName: 'Ext.grid.TextActionColumn',
    xtype: 'textactioncolumn',
    requires: [
        'Ext.grid.column.ActionProxy',
        'Ext.Glyph'
    ],
 
    actionIdRe: new RegExp(Ext.baseCSSPrefix + 'action-col-(\\d+)'),
 
    /**
     * @cfg {String} altText 
     * The alt text to use for the image element.
     */
    altText: '',
 
    /**
     * @cfg {String} [menuText=<i>Actions</i>]
     * Text to display in this column's menu item if no {@link #text} was specified as a header.
     */
    menuText: '<i>操作</i>',
    
    /**
     * @cfg {Number} [itemTabIndex=0] Default tabIndex attribute value for each action item.
     */
    itemTabIndex: 0,
    
    /**
     * @cfg {String} [itemAriaRole="button"] Default ARIA role for each action item.
     */
    itemAriaRole: 'button',
 
    maskOnDisable: false, // Disable means the action(s) 
 
    ignoreExport: true,
 
    sortable: false,
 
    innerCls: Ext.baseCSSPrefix + 'grid-cell-inner-action-col',
 
    actionIconCls: Ext.baseCSSPrefix + 'action-col-icon',
 
    constructor: function(config) {
        var me = this,
            cfg = Ext.apply({}, config),
            // Items may be defined on the prototype 
            items = cfg.items || me.items || [me],
            hasGetClass,
            i, len, item;
 
        me.origRenderer = cfg.renderer || me.renderer;
        me.origScope = cfg.scope || me.scope;
 
        me.renderer = me.scope = cfg.renderer = cfg.scope = null;
 
        // This is a Container. Delete the items config to be reinstated after construction. 
        cfg.items = null;
        me.callParent([cfg]);
 
        // Items is an array property of ActionColumns 
        me.items = items;
 
        for (i = 0, len = items.length; i < len; ++i) {
            item = items[i];
            if (item.substr && item[0] === '@') {
                item = me.getAction(item.substr(1));
            }
            if (item.isAction) {
                items[i] = item.initialConfig;
 
                // Register an ActinoProxy as a Component with the Action. 
                // Action methods will be relayed down into the targeted item set. 
                item.addComponent(new Ext.grid.column.ActionProxy(me, items[i], i));
            }
            if (item.getClass) {
                hasGetClass = true;
            }
        }
 
        // Also need to check for getClass, since it changes how the cell renders 
        if (me.origRenderer || hasGetClass) {
            me.hasCustomRenderer = true;
        }
    },
 
    initComponent: function() {
        var me = this;
        me.callParent();
        if (me.sortable && !me.dataIndex) {
            me.sortable = false;
        }
    },
 
    // Renderer closure iterates through items creating an <img> element for each and tagging with an identifying 
    // class name x-action-col-{n} 
    defaultRenderer: function(v, cellValues, record, rowIdx, colIdx, store, view) {
        var me = this,
            scope = me.origScope || me,
            items = me.items,
            len = items.length,
            i, item, ret, disabled, tooltip, altText, icon, glyph, tabIndex, ariaRole;
 
        // Allow a configured renderer to create initial value (And set the other values in the "metadata" argument!) 
        // Assign a new variable here, since if we modify "v" it will also modify the arguments collection, meaning 
        // we will pass an incorrect value to getClass/getTip 
        ret = Ext.isFunction(me.origRenderer) ? me.origRenderer.apply(scope, arguments) || '' : '';
 
        cellValues.tdCls += ' ' + Ext.baseCSSPrefix + 'action-col-cell';
        for (i = 0; i < len; i++) {
            item = items[i];
            icon = item.icon;
            glyph = item.glyph;
 
            disabled = item.disabled || (item.isDisabled ? Ext.callback(item.isDisabled, item.scope || me.origScope, [view, rowIdx, colIdx, item, record], 0, me) : false);
            tooltip  = item.tooltip  || (item.getTip     ? Ext.callback(item.getTip, item.scope || me.origScope, arguments, 0, me) : null);
            altText  =                   item.getAltText ? Ext.callback(item.getAltText, item.scope || me.origScope, arguments, 0, me) : item.altText || me.altText;
 
            // Only process the item action setup once. 
            if (!item.hasActionConfiguration) {
                // Apply our documented default to all items 
                item.stopSelection = me.stopSelection;
                item.disable = Ext.Function.bind(me.disableAction, me, [i], 0);
                item.enable = Ext.Function.bind(me.enableAction, me, [i], 0);
                item.hasActionConfiguration = true;
            }
 
            // If the ActionItem is using a glyph, convert it to an Ext.Glyph instance so we can extract the data easily. 
            if (glyph) {
                glyph = Ext.Glyph.fly(glyph);
            }
 
            // Pull in tabIndex and ariarRols from item, unless the item is this, in which case 
            // that would be wrong, and the icon would get column header values. 
            tabIndex = (item !== me && item.tabIndex !== undefined) ? item.tabIndex : me.itemTabIndex;
            ariaRole = (item !== me && item.ariaRole !== undefined) ? item.ariaRole : me.itemAriaRole;
            
            var cls = item.getClass ? Ext.callback(item.getClass, item.scope || me.origScope, arguments, undefined, me):' ';
            if(cls){
            ret += '<' + (icon ? 'img' : 'div') + (item.text?' style="width:auto !important;font-size:14px;margin-right:4px;padding-top:2px;"':'') +
                (typeof tabIndex === 'number' ? ' tabIndex="' + tabIndex + '"' : '') +
                (ariaRole ? ' role="' + ariaRole + '"' : ' role="presentation"') +
                (icon ? (' alt="' + altText + '" src="' + item.icon + '"') : '') +
                ' class="' + me.actionIconCls + ' ' + Ext.baseCSSPrefix + 'action-col-' + String(i) + ' ' +
                (disabled ? me.disabledCls + ' ' : ' ') +
                (item.hidden ? Ext.baseCSSPrefix + 'hidden-display ' : '') +
                (item.getClass ? Ext.callback(item.getClass, item.scope || me.origScope, arguments, undefined, me) : (item.iconCls || me.iconCls || '')) + '"' +
                (tooltip ? ' data-qtip="' + tooltip + '"' : '') + (icon ? '/>' : glyph ? (' style="font-family:' + glyph.fontFamily + '">' + glyph.character + '</div>') : '>'+(item.text?item.text:'')+'</div>');
        
            }
        }
        return ret;
    },
 
    updater: function(cell, value, record, view, dataSource) {
        var cellValues = {};
        Ext.fly(cell).addCls(cellValues.tdCls).down(this.getView().innerSelector, true).innerHTML = this.defaultRenderer(value, cellValues, record, null, null, dataSource, view);
    },
 
    /**
     * Enables this ActionColumn's action at the specified index.
     * @param {Number/Ext.grid.column.Action} index
     * @param {Boolean} [silent=false]
     */
    enableAction: function(index, silent) {
        var me = this;
 
        if (!index) {
            index = 0;
        } else if (!Ext.isNumber(index)) {
            index = Ext.Array.indexOf(me.items, index);
        }
        me.items[index].disabled = false;
        me.up('tablepanel').el.select('.' + Ext.baseCSSPrefix + 'action-col-' + index).removeCls(me.disabledCls);
        if (!silent) {
            me.fireEvent('enable', me);
        }
    },
 
    /**
     * Disables this ActionColumn's action at the specified index.
     * @param {Number/Ext.grid.column.Action} index
     * @param {Boolean} [silent=false]
     */
    disableAction: function(index, silent) {
        var me = this;
 
        if (!index) {
            index = 0;
        } else if (!Ext.isNumber(index)) {
            index = Ext.Array.indexOf(me.items, index);
        }
        me.items[index].disabled = true;
        me.up('tablepanel').el.select('.' + Ext.baseCSSPrefix + 'action-col-' + index).addCls(me.disabledCls);
        if (!silent) {
            me.fireEvent('disable', me);
        }
    },
 
    doDestroy: function() {
        // Action column items property is an array, unlike the normal Container's MixedCollection. 
        // If we don't null it here, parent doDestroy() can blow up. 
        this.renderer = this.items = null;
        
        return this.callParent();
    },
 
    /**
     * @private
     * Process and re-fire events routed from the Ext.panel.Table's processEvent method.
     * Also fires any configured click handlers. By default, cancels the mousedown event to prevent selection.
     * Returns the event handler's status to allow canceling of GridView's bubbling process.
     */
    processEvent: function(type, view, cell, recordIndex, cellIndex, e, record, row) {
        var me = this,
            target = e.getTarget(),
            key = type === 'keydown' && e.getKey(),
            match,
            item,
            disabled,
            cellFly = Ext.fly(cell);
 
        // Flag event to tell SelectionModel not to process it. 
        e.stopSelection = !key && me.stopSelection;
 
        // If the target was not within a cell (ie it's a keydown event from the View), then 
        // IF there's only one action icon, action it. If there is more than one, the user must 
        // invoke actionable mode to navigate into the cell. 
        if (key && (target === cell || !cellFly.contains(target))) {
            target = cellFly.query('.' + me.actionIconCls, true);
            if (target.length === 1) {
                target = target[0];
            } else {
                return;
            }
        }
 
        // NOTE: The statement below tests the truthiness of an assignment. 
        if (target && (match = target.className.match(me.actionIdRe))) {
            item = me.items[parseInt(match[1], 10)];
            disabled = item.disabled || (item.isDisabled ?
                Ext.callback(item.isDisabled, item.scope || me.origScope,
                    [view, recordIndex, cellIndex, item, record], 0, me) : false);
 
            if (item && !disabled) {
                // Do not allow focus to follow from this mousedown unless the grid is already in actionable mode 
                if (type === 'mousedown' && !me.getView().actionableMode) {
                    e.preventDefault();
                }
 
                else if (type === 'click' || (key === e.ENTER || key === e.SPACE)) {
                    Ext.callback(item.handler || me.handler, item.scope || me.origScope, [view, recordIndex, cellIndex, item, e, record, row], undefined, me);
 
                    // Handler could possibly destroy the grid, so check we're still available. 
                    //  
                    // If the handler moved focus outside of the view, do not allow this event to propagate 
                    // to cause any navigation. 
                    if (view.destroyed) {
                        return false;
                    } else {
                        // If the record was deleted by the handler, refresh 
                        // the position based upon coordinates. 
                        if (!e.position.getNode()) {
                            e.position.refresh();
                        }
                        if (!view.el.contains(Ext.Element.getActiveElement())) {
                            return false;
                        }
                    }
                }
            }
        }
 
        return me.callParent(arguments);
    },
 
    cascade: function(fn, scope) {
        fn.call(scope||this, this);
    },
 
    // Private override because this cannot function as a Container, and it has an items property which is an Array, NOT a MixedCollection. 
    getRefItems: function() {
        return [];
    },
 
    privates: {
        getFocusables: function() {
            // Override is here to prevent the default behaviour which tries to access 
            // this.items.items, which will be null. 
            return [];
        },
 
        // Overriden method to always return a bitwise value that will result in a call to this column's updater. 
        shouldUpdateCell: function() {
            return 2;
        }
    }
});