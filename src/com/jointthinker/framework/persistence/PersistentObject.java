package com.jointthinker.framework.persistence;



import com.jointthinker.framework.common.exceptions.BusinessLogicException;

import org.apache.commons.beanutils.PropertyUtils;

public abstract class PersistentObject{

	protected Long id;
    
    /** 
     *            @hibernate.id
     *             generator-class="native"
     *             type="java.lang.Long"
     *             column="id"
     *         
     */
   
 
    public void setId(Long identifier) {
        this.id = identifier;
    }
    
    /**
     * 对象间复制时使用.比如将一个<code>UserInfo</code>对象a的数据
     * copy给另外一个<code>UserInfo</code>对象b.则:
     * <code>
     *     b.setData(a);
     * </code>
     * 使用的时候注意b和a都应该是属于同一种子类的，比如都是UserInfo的实例,
     * 否则会抛出<code>BusinessLogicException</code>
     * @param vo
     */
    public abstract void setData(PersistentObject vo) throws BusinessLogicException;
    
    /**
     * 使用apache的BeanUtils工具进行JavaBean对象属性的copy
     * 在子类的<code>setData()</code>中调用
     * 
     * @param vo
     * @throws BusinessLogicException
     */
    protected void copyProperties(PersistentObject vo) throws BusinessLogicException{
        try {
            PropertyUtils.copyProperties(this,vo);  
        } catch (Exception e) {
            //throw new BusinessLogicException(Message.getString("PersistentObject.exceptionBeanPropertiesCopy"),e);
        	e.printStackTrace();
        }
    }

	public Long getId() {
		return id;
	}

	
}
