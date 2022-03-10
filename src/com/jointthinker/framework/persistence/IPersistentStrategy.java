package com.jointthinker.framework.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.jointthinker.framework.bean.JsonBean;
import com.jointthinker.framework.common.exceptions.PersistentException;
import com.jointthinker.framework.web.bean.ViewBean;

public interface IPersistentStrategy {

	/**
     * Save a persistent object to storage. This method is only needed when a
     * new object is to be added to storage.
     * 
     * @param data
     * @return @throws
     *         PersistentException
     */
    public PersistentObject store(PersistentObject data)
            throws PersistentException;
    public PersistentObject storeRefresh(PersistentObject data, Class clazz)throws PersistentException;
    public PersistentObject storeReceive(PersistentObject data) throws PersistentException;
    
    /**
     * Load an persistent object from storage. Object returned is a persistent
     * instance, meaning that changes to it will be automatically saved to
     * storage the next time that commit() is called.
     * 
     * @param id
     * @param clazz
     * @return @throws
     *         PersistentException
     */
    public PersistentObject load(Long id, Class clazz)
            throws PersistentException;

    
    public PersistentObject get(Long id, Class clazz)
            throws PersistentException;
    
    /**
     * Remove an object from storage.
     * 
     * @param poData  需要被删除的持久对象
     * @throws PersistentException
     */
    public void remove(PersistentObject poData) 
            throws PersistentException; 
    
    /**
     * Remove an object from storage
     * 
     * @param id    持久对象的唯一标识
     * @param clazz 持久对象的具体类
     * @throws PersistentException
     */
    public void remove(Long id, Class clazz) throws PersistentException;
    
    /**
     * 对当前session进行flush
     * @throws PersistentException
     */
    public void flush()throws PersistentException;

    /**
     * Release existing resources and start new session and transaction.
     * 
     * @throws PersistentException
     */
    public void begin() throws PersistentException;
    
    
    public void closeSessionFactory();
    
    /**
     * Commit all changes made to persistent objects since last call to release.
     * 
     * @throws PersistentException
     */
    public void commit() throws PersistentException;
    
    /**
     * Rollback all changes since last call to release.
     * Application can't call rollback when autoCommit=true
     * 
     * @throws PersistentException
     */
    public void rollback() throws PersistentException;

    /**
     * Release associated resources (database connection, session, etc.).
     * @throws PersistentException
     */
    public void release() throws PersistentException;

    /**
     * 
     * @return
     */
    //public ISimpleQueryFactory getQueryFactory();

    /** 
     * Query using Hibernate HQL format string.
     * 对于复杂的不分页查询(即需要使用内连接,左外连接，右外连接,全连接或者需要使用统计函数的查询)，
     * 直接调用此接口;简单查询操作通过获取本类的ISimlpeQueryFactory接口来进行
     * 
     * @param query        HQL查询语句
     * @return 
     */
    public List query(String query) throws PersistentException;
    
    public String jdbcQuery(String query) throws PersistentException;
    
    public void jdbcupdate(String query) throws PersistentException;
    /**
     * Pagination Query using Hibernate HQL format string
     * 对于复杂的分页查询(即需要使用内连接,左外连接，右外连接,全连接或者需要使用统计函数的查询)，
     * 直接调用此接口;简单分页查询操作通过获取本类的ISimlpeQueryFactory接口来进行
     * 
     * @param query        HQL查询语句
     * @param firstItemPos 分页查询的起始位置
     * @param maxItemNum   需要从起始位置开始向后查询的总记录条数
     * @return
     */
    public List query(String query, int firstItemPos, int maxItemNum)
            throws PersistentException;
    
    /**
     * 直接sql查询接口
     * @param query
     * @param alias
     * @param clazzs
     * @param firstItemPos
     * @param maxItemNum
     * @return
     * @throws PersistentException
     */
    public List jdbcQuery(String query, Class clazzs, int firstItemPos, int maxItemNum)
		throws PersistentException;
    
    
    /**
     * 直接sql查询接口
     * @param query
     * @param alias
     * @return
     * @throws PersistentException
     */
    public List jdbcQuery(String query, Class clazzs)
		throws PersistentException;
    
    /**
     * 直接sql查询单值
     * @param query
     * @param clazzs
     * @return
     * @throws PersistentException
     */
    public Object jdbcQueryScalar(String query, Class clazz)
		throws PersistentException;
    
    /**
     * 得到jdbc连接;
     * Get the JDBC connection. 
     * @return
     * @throws PersistentException
     */
    public Connection getDBConnection() throws PersistentException;
    
	public ViewBean getViewBean(String query) throws PersistentException;
	public ViewBean getViewBean(String query, String[] args) throws PersistentException;
	public ViewBean getViewBeanByOracle(String query) throws PersistentException;
	public ViewBean getViewBeanByOracle(String query,int start,int limit) throws PersistentException;
	public ViewBean getViewBeanByMysql(String query,int start,int limit) throws PersistentException;
	public ViewBean getViewBeanNo(String query) throws PersistentException;
	public int getCount(String sql) throws PersistentException;
    
    public void execQ(String Q,Vector v,String pro)throws PersistentException;
    public int execQtn(String query)throws PersistentException;
    
    public List findByProperty(Class clazz,String wherestr,String propertyName,Object value);
    public List findByProperty(Class clazz,String wherestr,String propertyName,Object value, String order);
   
    public List jdbcQueryByOracle(String query, Class clazzs,int start,int limit) throws PersistentException;
    
    public int jdbcBatch(List<String> sqls,int batchSize) throws PersistentException;
	List<Map<String, String>> getDataList(String query);
	
	public JsonBean getJsonBean(String query) throws PersistentException;
	
	public JsonBean getJsonBean(String query, Connection conn) throws PersistentException;
	
	public JsonBean getJsonBeanPaging(String query, int start, int limit) throws PersistentException;
	public ResultSet executeQuery(String sql, String string) throws PersistentException, SQLException;
}
