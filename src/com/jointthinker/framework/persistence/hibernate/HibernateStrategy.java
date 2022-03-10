package com.jointthinker.framework.persistence.hibernate;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.sql.rowset.CachedRowSet;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.jointthinker.framework.bean.JsonBean;
import com.jointthinker.framework.business.Config;
import com.jointthinker.framework.common.exceptions.PersistentException;
import com.jointthinker.framework.common.logging.Log4jlogging;
import com.jointthinker.framework.common.messages.Message;
import com.jointthinker.framework.common.util.JSONutil;
import com.jointthinker.framework.persistence.IPersistentStrategy;
import com.jointthinker.framework.persistence.PersistentObject;
import com.jointthinker.framework.web.bean.ViewBean;
import com.sun.rowset.CachedRowSetImpl;

public class HibernateStrategy implements IPersistentStrategy {

	private SessionFactory sessionFactory = null;

	private Session ses = null;

	public HibernateStrategy(SessionFactory sf) {
		// System.out.println("-------------建立一次sessionfactory-----------");
		sessionFactory = sf;
	}

	/**
	 * Get existing or open new session for current thread if existing session
	 * for current thread is closed,get a new one.
	 * 
	 * @return
	 * 
	 */
	public Session getSession() {

		try {
			if (ses == null) {
				ses = sessionFactory.openSession();

			} else {
				if (ses.isOpen()&&ses.connection().isClosed()) {
					ses = sessionFactory.openSession();

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log4jlogging.error(Message
					.getString("HibernateStrategy.exceptionOpeningSession"), e);

			throw new RuntimeException();
		}
		return ses;
	}

	@Override
	public void closeSessionFactory() {

		sessionFactory.close();
	}

	/**
	 * Store object using an existing transaction.
	 */
	@Override
	public PersistentObject store(PersistentObject data)
			throws PersistentException {
		if (data == null) {
			throw new PersistentException(Message
					.getString("HibernateStrategy.nullPassedIn"));
		}
		Session ses = getSession();
		try {
			boolean b = ses.connection().getAutoCommit();
			if (data.getId() == null || data.getId().intValue() == 0) {
				ses.save(data);
			}
			b = ses.connection().getAutoCommit();
			PersistentObject vo = (PersistentObject) ses.load(data.getClass(),
					data.getId());
			vo.setData(data);
			data = vo;
			//this.commit();
		} catch (Exception e) {
			e.printStackTrace();
			String msg = Message.formatString(
					"HibernateStrategy.exceptionStoring", data.getId()
							.toString());
			Log4jlogging.error(msg, e);
			release();
			throw new PersistentException(msg, e);
		}
		return data;
	}
	
	 @Override
	public PersistentObject storeRefresh(PersistentObject data, Class clazz) throws PersistentException {
			if (data == null) {
			    throw new PersistentException(Message.getString("HibernateStrategy.nullPassedIn"));
			}
			Session ses = getSession();
			try {
			    if (data.getId() == null || data.getId().intValue() == 0) {     
			        ses.save(data);
			    }  
			    PersistentObject vo = (PersistentObject) ses.load(data.getClass(), data.getId());
			    vo.setData(data);
			    data = vo;
			    this.commit();
			    ses.refresh(data);
			    data = this.load(data.getId(), clazz);
			} catch (Exception e) {
			    e.printStackTrace();
			    String msg = Message.formatString("HibernateStrategy.exceptionStoring", data.getId().toString());
			    Log4jlogging.error(msg, e);
			    release();
			    throw new PersistentException(msg, e);
			}
			return data;
		}

	/**
	 * Retrieve object, begins and ends its own transaction.
	 */
	@Override
	public PersistentObject load(Long id, Class clazz)
			throws PersistentException {
		if (id == null) {
			throw new PersistentException(Message
					.getString("HibernateStrategy.nullNotValidId"));
		}

		if (clazz == null) {
			throw new PersistentException(Message
					.getString("HibernateStrategy.nullNotValidClass"));
		}

		Object obj = null;
		Session ses = getSession();
		try {
			obj = ses.load(clazz, id);

		} catch (Exception e) {
			String msg = Message.formatString(
					"HibernateStrategy.exceptionRetrieving", id.toString(),
					clazz.getName());
			Log4jlogging.debug(msg, e);
			release();
		}
		return (PersistentObject) obj;
	}
	
	@Override
	public PersistentObject get(Long id, Class clazz)
			throws PersistentException {
		if (id == null) {
			throw new PersistentException(Message
					.getString("HibernateStrategy.nullNotValidId"));
		}

		if (clazz == null) {
			throw new PersistentException(Message
					.getString("HibernateStrategy.nullNotValidClass"));
		}

		Object obj = null;
		Session ses = getSession();
		try {
			obj = ses.get(clazz, id);

		} catch (Exception e) {
			String msg = Message.formatString(
					"HibernateStrategy.exceptionRetrieving", id.toString(),
					clazz.getName());
			Log4jlogging.debug(msg, e);
			release();
		}
		return (PersistentObject) obj;
	}

	/**
	 * 
	 */
	@Override
	public void remove(PersistentObject poData) throws PersistentException {
		if (poData.getId() == null) {
			throw new PersistentException(
					"Persistent Object's identifier is null,remove failed");
		}
		try {
			getSession().delete(poData);
			this.commit();
		} catch (HibernateException e) {
			String msg = Message
					.getString("HibernateStrategy.exceptionRemoving");
			Log4jlogging.error(msg, e);
			throw new PersistentException(msg, e);
		}
	}

	/**
	 * 根据类的ID删除该对象
	 */
	@Override
	public void remove(Long id, Class clazz) throws PersistentException {
		if (id == null) {
			throw new PersistentException(Message
					.getString("HibernateStrategy.nullNotValidId"));
		}

		if (clazz == null) {
			throw new PersistentException(Message
					.getString("HibernateStrategy.nullNotValidClass"));
		}

		Object obj;
		try {
			obj = getSession().load(clazz, id);
			getSession().delete(obj);
		} catch (Exception e) {
			String msg = Message.formatString(
					"HibernateStrategy.exceptionRemoving", id.toString(), clazz
							.getName());
			Log4jlogging.error(msg, e);
			release();
			throw new PersistentException("remove error", e);
		}
	}

	@Override
	public void flush() throws PersistentException {
		try {
			boolean b = getSession().connection().getAutoCommit();
			getSession().flush();
			b = getSession().connection().getAutoCommit();

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new PersistentException("flush error", ex);
		}

	}

	@Override
	public void begin() throws PersistentException {
		getSession().beginTransaction();
	}

	@Override
	public void commit() throws PersistentException {
		try {

			if (getSession() != null) {
				boolean b = getSession().connection().getAutoCommit();
				getSession().flush();
				b = getSession().connection().getAutoCommit();
				// can't call commit when autocommit is true
				if (!getSession().connection().getAutoCommit()) {
					getSession().connection().commit();
				}
			}
		} catch (Exception e) {// HibernateException or SQLException
			release();
			String msg = Message
					.getString("HibernateStrategy.exceptionCommitting");
			Log4jlogging.error(msg, e);
			throw new PersistentException(msg, e);
		}
	}

	@Override
	public void rollback() throws PersistentException {
		// Can't call rollback when autoCommit=true
		try {
			boolean autoCommit = getSession().connection().getAutoCommit();
			if (!autoCommit) {
				getSession().connection().rollback();
			} else {
				throw new PersistentException(
						"Can't call rollback when autoCommit=true");
			}
		} catch (HibernateException e) {
			release();
			throw new PersistentException(Message
					.getString("HibernateStrategy.exceptionRollBack"), e);
		} catch (SQLException e) {
			release();
			throw new PersistentException(Message
					.getString("HibernateStrategy.exceptionRollBack"), e);
		}
	}

	/**
	 * Release database session, rolls back any uncommitted changes.
	 */
	@Override
	public void release() throws PersistentException {

		Session ses = getSession();
		if (null == ses)
			return; // there is no session to release

		if (ses != null) {
			try {
				if (ses.isOpen()) {
					// ses.flush();
					// ses.clear();
					ses.close();
					// System.out.println("---------release()-------session.close()-----");
				}
			} catch (Exception e) {
				Log4jlogging.error("ERROR cleaning up Hibernate session", e);
				throw new PersistentException(
						"ERROR cleaning up Hibernate session", e);
			}
		}
		ses = null;
	}

	public List query(String query, Object[] args, Object[] types)
			throws PersistentException {
		return query(query, -1, -1);
	}

	/**
	 * 取得分页信息列表
	 */
	@Override
	public List query(String query, int firstItemPos, int maxItemNum)
			throws PersistentException {

		if (query == null) {
			throw new PersistentException(Message
					.getString("HibernateStrategy.nullNotValidQuery"));
		}

		try {
			boolean b = ses.connection().getAutoCommit();
			if (query.indexOf("$") > -1) {
				query = query.replaceAll("\\$\\d+", "\\?");
			}
			if (firstItemPos >= 0 && maxItemNum > 0) {// 需要分页
				Query hbQuery = getSession().createQuery(query);
				hbQuery.setFirstResult(firstItemPos);
				hbQuery.setMaxResults(maxItemNum);
				return hbQuery.list();
			} else {// 不需要分页
				return getSession().createQuery(query).list();
			}
		} catch (Exception e) {

			String msg = Message.getString("HibernateStrategy.duringQuery");
			Log4jlogging.error(msg, e);
			release();
			throw new PersistentException(msg, e);
		}
	}

	// 传入一条SQL/HQL语句,然后执行(hibernate中查询的结果都是返回List型数据)
	@Override
	public List query(String query) throws PersistentException {
		try {
			if (query.indexOf("$") > -1) {
				query = query.replaceAll("\\$\\d+", "\\?");
			}
			List l = getSession().createQuery(query).list();
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			String msg = Message.getString("HibernateStrategy.duringQuery");
			Log4jlogging.error(msg, e);
			release();
			throw new PersistentException(msg, e);
		}
	}

	// 传入一条SQL/HQL语句,然后执行(hibernate中查询的结果都是返回List型数据)

	@Override
	public void jdbcupdate(String query) throws PersistentException {
		try {
			if (query.indexOf("$") > -1) {
				query = query.replaceAll("\\$\\d+", "\\?");
			}
			Connection conn = null;
			PreparedStatement stat = null;
			ResultSet rs = null;
			conn = getDBConnection();
			if(conn!=null){
				conn.setAutoCommit(false);
			}
			stat = conn.prepareStatement(query,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stat.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			String msg = Message.getString("HibernateStrategy.duringQuery");
			Log4jlogging.error(msg, e);
			release();
			throw new PersistentException(msg, e);
		}
	}

	@Override
	public List jdbcQuery(String query, Class clazz, int firstItemPos,
			int maxItemNum) throws PersistentException {

		if (query == null) {
			throw new PersistentException(Message
					.getString("HibernateStrategy.nullNotValidQuery"));
		}
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList result = new ArrayList();
		try {
			if (query.indexOf("$") > -1) {
				query = query.replaceAll("\\$\\d+", "\\?");
			}
			conn = getDBConnection();
			stat = conn.prepareStatement(query,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stat.executeQuery();
			int begin = firstItemPos + 1;
			int end = firstItemPos + maxItemNum;
			if (!(begin == -1 && end == -1)) {
				if (begin < 1 || end < 1) {
					throw new PersistentException(
							"beingIndex and endIndex value range error!");
				} else {
					if (begin == 1) {
						rs.beforeFirst();
					} else {
						rs.absolute(begin - 1);
					}
				}
			}
			java.beans.BeanInfo info = java.beans.Introspector.getBeanInfo(
					clazz, Object.class);
			PropertyDescriptor[] pds = info.getPropertyDescriptors();
			Hashtable htprops = new Hashtable();
			for (int k = 0; k < pds.length; k++) {
				String pname = pds[k].getName().toLowerCase();
				;
				// System.out.println("pname:"+pname);
				htprops.put(pname, pds[k]);
			}
			ResultSetMetaData rsmd = rs.getMetaData();
			int index = 0;
			while (rs.next()) {
				Object bean = clazz.newInstance();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					String name = rsmd.getColumnName(i).toLowerCase();
					// System.out.println("name:"+name);
					Object temppd = htprops.get(name);
					if (temppd == null) {
						continue;
					} else {

					}
					PropertyDescriptor pd = (PropertyDescriptor) temppd;
					Object value = rs.getObject(i);
					pd.getWriteMethod()
							.invoke(
									bean,
									new Object[] { dbToBean(value, pd
											.getPropertyType()) });
				}
				result.add(bean);
				index++;
				if (index >= maxItemNum) {
					break;
				}
			}
		} catch (Exception e) {
			String msg = Message.getString("HibernateStrategy.duringQuery");
			Log4jlogging.error(msg, e);
			release();
			throw new PersistentException(msg, e);
		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
			}
		}
		return result;
	}

	@Override
	public List jdbcQuery(String query, Class clazz) throws PersistentException {

		if (query == null) {
			throw new PersistentException(Message
					.getString("HibernateStrategy.nullNotValidQuery"));
		}

		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList result = new ArrayList();
		try {
			if (query.indexOf("$") > -1) {
				query = query.replaceAll("\\$\\d+", "\\?");
			}
			conn = getDBConnection();

			stat = conn.prepareStatement(query,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stat.executeQuery();

			rs.beforeFirst(); // 将指针移动到此 ResultSet 对象的开头，正好位于第一行之前。
			java.beans.BeanInfo info = java.beans.Introspector.getBeanInfo(
					clazz, Object.class);
			java.beans.PropertyDescriptor[] pds = info.getPropertyDescriptors();
			Hashtable htprops = new Hashtable();
			// System.out.println("length----------"+pds.length);
			for (int k = 0; k < pds.length; k++) {
				String pname = pds[k].getName().toLowerCase();
				// System.out.println("colu"+k+"---------"+pname);
				htprops.put(pname, pds[k]);
			}
			ResultSetMetaData rsmd = rs.getMetaData();// 取得结果集的元对象

			while (rs.next()) {
				Object bean = clazz.newInstance();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {// rsmd.getColunmcount()方法取得所有列的个数
				// System.out.println(rsmd.getColumnLabel(i));
					String name = rsmd.getColumnLabel(i).toLowerCase()/* .toLowerCase() */;

					PropertyDescriptor pd = (PropertyDescriptor) htprops
							.get(name);
					if (pd == null)
						continue;
					Object value = rs.getObject(i);
					// System.out.println("value--------"+Converter.getConvertString(value));
					pd.getWriteMethod()
							.invoke(
									bean,
									new Object[] { dbToBean(value, pd
											.getPropertyType()) });

				}

				result.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String msg = Message.getString("HibernateStrategy.duringQuery");
			Log4jlogging.error(msg, e);
			release();
			throw new PersistentException(msg, e);
		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
			}
		}
		return result;
	}

	@Override
	public Object jdbcQueryScalar(String query, Class clazz)
			throws PersistentException {
		if (query == null) {
			throw new PersistentException(Message
					.getString("HibernateStrategy.nullNotValidQuery"));
		}
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		Object result = null;
		try {
			if (query.indexOf("$") > -1) {
				query = query.replaceAll("\\$\\d+", "\\?");
			}
			conn = getDBConnection();

			stat = conn.prepareStatement(query,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stat.executeQuery();
			rs.beforeFirst();
			if (rs.next()) {
				result = rs.getObject(1);
			}
		} catch (Exception e) {
			String msg = Message.getString("HibernateStrategy.duringQuery");
			Log4jlogging.error(msg, e);
			release();
			throw new PersistentException(msg, e);
		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
			}
		}
		return dbToBean(result, clazz);
	}

	@Override
	public Connection getDBConnection() throws PersistentException {
		try {
			return getSession().connection();

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new PersistentException("HibernateStrategy.getDBConnection",
					ex);
		}
	}

	@Override
	public void execQ(String Q, Vector v, String pro)
			throws PersistentException {
		/**
		 * if (Q == null) { throw new PersistentException(Message
		 * .getString("HibernateStrategy.nullNotValidQuery")); }
		 */
		try {
			int total;
			Session session = getSession();
			Transaction tx = session.beginTransaction();
			Connection con = session.connection();
			con.setAutoCommit(false);
			String procedure = "{" + pro + "(";
			for (int i = 0; i < v.size(); i++) {
				if (i != v.size() - 1) {
					procedure = procedure + "?,";
				} else {
					procedure = procedure + "?)}";
				}
			}
			// System.out.println(procedure);
			CallableStatement cstmt = con.prepareCall(procedure);
			// System.out.println(cstmt);
			for (int i = 1; i <= v.size(); i++) {
				cstmt.setString(i, (String) v.get(i - 1));
			}
			// 设置参数
			// System.out.println(cstmt);
			cstmt.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			String msg = Message.getString("HibernateStrategy.duringQuery");
			Log4jlogging.error(msg, e);
			release();
			throw new PersistentException(msg, e);
		}

	}

	@Override
	public int execQtn(String query) throws PersistentException {
		int num = 0;
		if (query == null) {
			throw new PersistentException(Message
					.getString("HibernateStrategy.nullNotValidQuery"));
		}
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList result = new ArrayList();
		try {
			if (query.indexOf("$") > -1) {
				query = query.replaceAll("\\$\\d+", "\\?");
			}
			conn = getDBConnection();
			stat = conn.prepareStatement(query,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stat.executeQuery();
			rs.beforeFirst(); // 将指针移动到此 ResultSet 对象的开头，正好位于第一行之前。
			while (rs.next()) {
				num = rs.getInt("num");
			}
		} catch (Exception e) {
			String msg = Message.getString("HibernateStrategy.duringQuery");
			Log4jlogging.error(msg, e);
			release();
			throw new PersistentException(msg, e);
		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
			}
		}
		return num;
	}

	public Object dbToBean(Object obj, Class returnType) {
		try {

			if (obj instanceof Short) {
				if (returnType.equals(Integer.class)
						|| returnType.equals(int.class)) {
					return new Integer(((Short) obj).intValue());
				} else {
					return obj;
				}
			} else if (obj instanceof Integer) {

				if (returnType.equals(Long.class)
						|| returnType.equals(long.class)) {
					return new Long(((Integer) obj).intValue());
				} else if (returnType.equals(Double.class)) {
					return new Double(((Integer) obj).intValue());
				} else {
					return obj;
				}
			} else if (obj instanceof Long) {
				if (returnType.equals(Integer.class)
						|| returnType.equals(int.class)) {
					return new Integer(((Long) obj).intValue());
				} else {
					return obj;
				}
			} else if (obj instanceof java.math.BigInteger) {
				if (returnType.equals(java.math.BigInteger.class)) {
					return obj;
				} else if (returnType.equals(Integer.class)
						|| returnType.equals(int.class)) {
					return new Integer(((java.math.BigInteger) obj).intValue());
				} else {
					return new Long(((java.math.BigInteger) obj).longValue());
				}

			} else if (obj instanceof java.math.BigDecimal) {
				if (returnType.equals(java.math.BigDecimal.class)) {
					return obj;
				} else if (returnType.equals(Integer.class)
						|| returnType.equals(int.class)) {
					return new Integer(((java.math.BigDecimal) obj).intValue());
				} else if (returnType.equals(Long.class)
						|| returnType.equals(long.class)) {
					return new Long(((java.math.BigDecimal) obj).longValue());
				} else if (returnType.equals(Double.class)
						|| returnType.equals(double.class)) {
					return new Double(((java.math.BigDecimal) obj)
							.doubleValue());
				} else {
					return new Float(((java.math.BigDecimal) obj).floatValue());
				}
			} else if (obj instanceof Double) {
				if (returnType.equals(Double.class)
						|| returnType.equals(double.class)) {
					return obj;
				} else {
					return new Float(((Double) obj).doubleValue());
				}
			} else if (obj instanceof byte[]) {
				if (java.io.InputStream.class.isAssignableFrom(returnType)) {
					return new ByteArrayInputStream((byte[]) obj);
				} else {
					return obj;
				}
			} else if (obj instanceof String){
            	
                if(java.io.Reader.class.isAssignableFrom(returnType)) {
                	String objstr = (String)obj;
                	objstr = objstr.replace("\r\n", "\\r\\n");
                	return new StringReader(objstr);
                    //return new StringReader((String)obj);
                }else {
                	String objstr = (String)obj;
                	objstr = objstr.replace("\r\n", "\\r\\n");
                    return objstr;
                }
            } else if (obj instanceof Clob){
            	
                //if(java.io.Reader.class.isAssignableFrom(returnType)) {
                	 char[] result = null;
                	 if(obj != null){
                		 //System.out.println("----------------222222222222--------------");
                         result = new char[ (int) ((Clob)(obj)).length()];
                     }else{
                         result = new char[1000];
                     }
                     java.io.Reader reader = ((Clob)(obj)).getCharacterStream();
                     reader.read(result);
                     //System.out.println("----------------33333333333333--------------");
                 	String objstr = new String(result);
                	objstr = objstr.replace("\r\n", "\\r\\n");
                    return objstr;
                     //return new String(result);
                //}else {
                	//System.out.println("----------------44444444444444444--------------");
                    //return obj;
               // }
            }else if (obj instanceof Date) {

				if (java.io.Reader.class.isAssignableFrom(returnType)) {
					return new StringReader(obj.toString());
				} else {
					return obj;
				}
			} else {
				return obj;
			}
		} catch (Exception e) {
			throw new IllegalStateException(
					"can't execute dbToBean method for " + obj);
		}

	}

	@Override
	public String jdbcQuery(String query) throws PersistentException {
		try {
			if (query.indexOf("$") > -1) {
				query = query.replaceAll("\\$\\d+", "\\?");
			}
			Connection conn = null;
			PreparedStatement stat = null;
			ResultSet rs = null;
			conn = getDBConnection();
			String result = null;
			stat = conn.prepareStatement(query,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stat.executeQuery();
			rs.beforeFirst(); // 将指针移动到此 ResultSet 对象的开头，正好位于第一行之前。
			while (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			String msg = Message.getString("HibernateStrategy.duringQuery");
			Log4jlogging.error(msg, e);
			release();
			throw new PersistentException(msg, e);
		}
	}

	@Override
	public int getCount(String sql) throws PersistentException {

		// int i=0;
		// String newsql=sql.toLowerCase();
		// i=newsql.indexOf(" from ");
		// sql = "select count(*) as num "+sql.substring(i);
		sql = "select count(*) as num from (" + sql + ") ff";
		return execQtn(sql);
	}

	@Override
	public ViewBean getViewBean(String query) {

		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		ViewBean view = new ViewBean();
		List list1 = null;
		List valuelist = new ArrayList();
		List labellist = new ArrayList();
		try {
			if (query.indexOf("$") > -1) {
				query = query.replaceAll("\\$\\d+", "\\?");
			}
			conn = getDBConnection();
			stat = conn.createStatement();
			rs = stat.executeQuery(query);
			String columnname = "";
			for (int j = 1; j <= rs.getMetaData().getColumnCount(); j++) {
				columnname = rs.getMetaData().getColumnLabel(j);
				labellist.add(rs.getMetaData().getColumnLabel(j).toLowerCase());
			}
			while (rs.next()) {
				list1 = new ArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {

					if (rs.getObject(i) instanceof Clob) {
						char[] result = null;
						if (rs.getObject(i) != null) {
							result = new char[(int) ((Clob) (rs.getObject(i)))
									.length()];
						} else {
							result = new char[1000];
						}
						java.io.Reader reader = ((Clob) (rs.getObject(i)))
								.getCharacterStream();
						reader.read(result);
						list1.add(new String(result));
					}else if (rs.getObject(i) instanceof Date) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
						if(rs.getString(i).length()==10){
							list1.add(format2.format(format2.parse(rs.getString(i))));
						}else{
							list1.add(format.format(format.parse(rs.getString(i))));
						}
					} else {
						list1.add(rs.getObject(i) == null ? "" : rs.getObject(i));
					}
				}
				valuelist.add(list1);
			}
			view.setLabellist(labellist);
			view.setValuelist(valuelist);
			view.setSize(valuelist.size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stat.close();
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return view;
	}

	@Override
	public ViewBean getViewBeanByOracle(String query) {

		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		ViewBean view = new ViewBean();
		List list1 = null;
		List valuelist = new ArrayList();
		List labellist = new ArrayList();
		try {
			if (query.indexOf("$") > -1) {
				query = query.replaceAll("\\$\\d+", "\\?");
			}
			conn = getDBConnection();
			stat = conn.createStatement();
			rs = stat.executeQuery(query);
			String columnname = "";
			for (int j = 1; j <= rs.getMetaData().getColumnCount(); j++) {
				columnname = rs.getMetaData().getColumnLabel(j);
				labellist.add(rs.getMetaData().getColumnLabel(j).toLowerCase());
			}
			while (rs.next()) {
				list1 = new ArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					if (rs.getObject(i) instanceof Clob) {
						char[] result = null;
						if (rs.getObject(i) != null) {
							result = new char[(int) ((Clob) (rs.getObject(i)))
									.length()];
						} else {
							result = new char[1000];
						}
						java.io.Reader reader = ((Clob) (rs.getObject(i)))
								.getCharacterStream();
						reader.read(result);
						list1.add(new String(result));
					}else if (rs.getObject(i) instanceof Date) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						list1.add(format.format(format.parse(rs.getString(i))));
					} else {
						list1.add(rs.getObject(i) == null ? "" : rs.getObject(i));
					}
				}
				valuelist.add(list1);
			}
			view.setLabellist(labellist);
			view.setValuelist(valuelist);
			view.setSize(valuelist.size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stat.close();
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return view;
	}

	@Override
	public ViewBean getViewBeanByOracle(String query, int start, int limit) {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		ViewBean view = new ViewBean();
		List list1 = null;
		List valuelist = new ArrayList();
		List labellist = new ArrayList();
		try {
			if (query.indexOf("$") > -1) {
				query = query.replaceAll("\\$\\d+", "\\?");
			}
			conn = getDBConnection();
			stat = conn.createStatement();
			String sql = "";
			if (limit != -1) {
				sql = "select * from (select rownum as ro,t.* from( ?1 ) t) where ro<=?2 and ro>?3";
				sql = sql.replace("?1", query);
				sql = sql.replace("?2", Integer.toString(start + limit));
				sql = sql.replace("?3", Integer.toString(start));
			} else {
				if (start != -1) {
					sql = "select * from (select rownum as ro,t.* from( ?1 ) t)";
					sql = sql.replace("?1", query);
				} else {
					sql = "select * from (select rownum as ro,t.* from( ?1 ) t)";
					sql = sql.replace("?1", query);
				}
			}
			// System.out.println(sql);
			rs = stat.executeQuery(sql);
			String columnname = "";
			for (int j = 1; j <= rs.getMetaData().getColumnCount(); j++) {
				columnname = rs.getMetaData().getColumnLabel(j);
				labellist.add(rs.getMetaData().getColumnLabel(j).toLowerCase());
			}
			while (rs.next()) {
				list1 = new ArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					if (rs.getObject(i) instanceof Clob) {
						char[] result = null;
						if (rs.getObject(i) != null) {
							result = new char[(int) ((Clob) (rs.getObject(i)))
									.length()];
						} else {
							result = new char[1000];
						}
						java.io.Reader reader = ((Clob) (rs.getObject(i)))
								.getCharacterStream();
						reader.read(result);
						list1.add(new String(result));
					} else if (rs.getObject(i) instanceof Date) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						list1.add(format.format(format.parse(rs.getString(i))));
					} else {
						list1.add(rs.getObject(i) == null ? "" : rs
								.getObject(i));
					}
				}
				valuelist.add(list1);
			}
			view.setLabellist(labellist);
			view.setValuelist(valuelist);
			view.setSize(getCount(query));

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			try {
				rs.close();
				stat.close();
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return view;
	}
	
	@Override
	public ViewBean getViewBeanByMysql(String query, int start, int limit) {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		ViewBean view = new ViewBean();
		List list1 = null;
		List valuelist = new ArrayList();
		List labellist = new ArrayList();
		try {
			if (query.indexOf("$") > -1) {
				query = query.replaceAll("\\$\\d+", "\\?");
			}
			conn = getDBConnection();
			stat = conn.createStatement();
			String sql = query;
			if (limit != -1) {
				query = query + " limit ?1,?2";
				query = query.replace("?1",Integer.toString(start))
						 	 .replace("?2", Integer.toString(limit));
			}
			rs = stat.executeQuery(query);
			String columnname = "";
			for (int j = 1; j <= rs.getMetaData().getColumnCount(); j++) {
				columnname = rs.getMetaData().getColumnLabel(j);
				labellist.add(rs.getMetaData().getColumnLabel(j).toLowerCase());
			}
			while (rs.next()) {
				list1 = new ArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					if (rs.getObject(i) instanceof Clob) {
						char[] result = null;
						if (rs.getObject(i) != null) {
							result = new char[(int) ((Clob) (rs.getObject(i)))
									.length()];
						} else {
							result = new char[1000];
						}
						java.io.Reader reader = ((Clob) (rs.getObject(i)))
								.getCharacterStream();
						reader.read(result);
						list1.add(new String(result));
					} else if (rs.getObject(i) instanceof Date) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						list1.add(format.format((Date)(rs.getObject(i))));
					} else {
						list1.add(rs.getObject(i) == null ? "" : rs
								.getObject(i));
					}
				}
				valuelist.add(list1);
			}
			view.setLabellist(labellist);
			view.setValuelist(valuelist);
			view.setSize(getCount(sql));

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			try {
				rs.close();
				stat.close();
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return view;
	}

	@Override
	public ViewBean getViewBeanNo(String query) {

		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		ViewBean view = new ViewBean();
		List list1 = null;
		List valuelist = new ArrayList();
		List labellist = new ArrayList();
		try {
			if (query.indexOf("$") > -1) {
				query = query.replaceAll("\\$\\d+", "\\?");
			}
			conn = getDBConnection();
			stat = conn.createStatement();
			rs = stat.executeQuery(query);
			String columnname = "";
			for (int j = 1; j <= rs.getMetaData().getColumnCount(); j++) {

				columnname = rs.getMetaData().getColumnLabel(j);
				// System.out.println("-------colname-----" + columnname);

				labellist.add(rs.getMetaData().getColumnLabel(j));
			}
			while (rs.next()) {

				list1 = new ArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					Object x = rs.getObject(i);
					if (x instanceof Clob) {
						// System.out.println("CLOB");
						String cl = rs.getClob(i).getSubString(1,
								(int) rs.getClob(i).length());
						cl = cl.replace('"', '\'');
						list1.add(cl == null ? "" : cl);
					} else
						list1.add(x == null ? "" : x);

				}
				valuelist.add(list1);
			}
			// System.out.println("-------valuelist.size()-------" +
			// valuelist.size());
			view.setLabellist(labellist);
			view.setValuelist(valuelist);
			view.setSize(valuelist.size());
		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			try {
				rs.close();
				stat.close();
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return view;
	}

	@Override
	public ViewBean getViewBean(String query, String[] args) {

		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		ViewBean view = new ViewBean();
		List list1 = null;
		List valuelist = new ArrayList();
		List labellist = new ArrayList();
		try {
			if (query.indexOf("$") > -1) {
				query = query.replaceAll("\\$\\d+", "\\?");
			}
			conn = getDBConnection();
			stat = conn.createStatement();
			rs = stat.executeQuery(query);
			String columnname = "";
			for (int j = 1; j <= rs.getMetaData().getColumnCount(); j++) {

				columnname = rs.getMetaData().getColumnLabel(j);
				// System.out.println("-------colname-----" + columnname);
				labellist.add(rs.getMetaData().getColumnLabel(j).toLowerCase());

			}
			while (rs.next()) {

				list1 = new ArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					// System.out.print("---第(" + i + ")列值:" + rs.getObject(i)+"
					// ");

					Object value = rs.getObject(i) == null ? "" : rs
							.getObject(i);
					if (value instanceof Clob) {
						// System.out.println("CLOB");
						String cl = rs.getClob(i).getSubString(1,
								(int) rs.getClob(i).length());
						cl = cl.replace('"', '\'');
						value = cl == null ? "" : cl;
						// list1.add(cl==null?"":cl);
					}
					// else{
					//					
					// list1.add(value==null?"":value);
					// }
					if (args != null && args.length > 0) {
						for (int k = 0; k < args.length; k++) {

							String label = args[k];
							if (rs.getMetaData().getColumnLabel(i).toString()
									.compareToIgnoreCase(label) == 0) {

								String regEx = "<.+?>"; // "&lt;.+?&gt;"
														// 这是字符形式的<>
								java.util.regex.Pattern ppp = java.util.regex.Pattern
										.compile(regEx);
								java.util.regex.Matcher mmm = ppp
										.matcher(value == null ? "" : value
												.toString());
								String tempstr = mmm.replaceAll("");
								tempstr = tempstr.replace("&nbsp;", " ");
								tempstr = tempstr.replace("&ldquo;", "\\“");
								tempstr = tempstr.replace("&rdquo;", "\\”");
								tempstr = tempstr.replace("&times;", "×");
								tempstr = tempstr.replace("&mdash;", "—");
								value = tempstr;
							}
						}
						list1.add(value);
					} else {

						list1.add(value);
					}
				}
				valuelist.add(list1);
			}
			// System.out.println("-------valuelist.size()-------" +
			// valuelist.size());
			view.setLabellist(labellist);
			view.setValuelist(valuelist);
		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			try {
				rs.close();
				stat.close();
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return view;
	}
	
	@Override
	public List jdbcQueryByOracle(String query, Class clazz,int start,int limit) throws PersistentException {
		
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
        ArrayList result = new ArrayList();
	    try {
	        if (query.indexOf("$") > -1) {
	            query = query.replaceAll("\\$\\d+", "\\?");
	        }
		     conn = getDBConnection();
	         stat = conn.createStatement();
	
	        String sql = "select * from (select rownum as ro,t.* from( ?1 ) t) where ro<=?2 and ro>?3";
			
			sql = sql.replace("?1", query);
			sql = sql.replace("?2", Integer.toString(start+limit));
			sql = sql.replace("?3", Integer.toString(start));
	
	        rs = stat.executeQuery(sql);
//	        rs.beforeFirst(); //将指针移动到此 ResultSet 对象的开头，正好位于第一行之前。          
	        java.beans.BeanInfo info = java.beans.Introspector.getBeanInfo(clazz, Object.class);
	        java.beans.PropertyDescriptor[] pds = info.getPropertyDescriptors();
	        Hashtable htprops = new Hashtable();
	//        System.out.println("length----------"+pds.length);
	        for(int k=0; k<pds.length; k++){
	        	String pname = pds[k].getName().toLowerCase();
	        	//System.out.println("colu"+k+"---------"+pname);
	        	htprops.put(pname, pds[k]);
	        }
	        ResultSetMetaData rsmd = rs.getMetaData();//取得结果集的元对象
	
	        while(rs.next()){
	        	Object bean = clazz.newInstance();
	        	for(int i=1;i<=rsmd.getColumnCount();i++) {//rsmd.getColunmcount()方法取得所有列的个数
	        		//System.out.println("-----" + i + "-----------" + rsmd.getColumnLabel(i));
	                String name = rsmd.getColumnLabel(i).toLowerCase()/*.toLowerCase()*/;
	
	                PropertyDescriptor pd = (PropertyDescriptor)htprops.get(name);
	                if(pd == null) continue;
	                Object value = rs.getObject(i);
	                //System.out.println("------"+i+"---------value--------"+Converter.getConvertString(value));
	                pd.getWriteMethod().invoke(bean,new Object[] {dbToBean(value,pd.getPropertyType())});
	               
	            }
	        	
	        	result.add(bean);
	        }
	    } catch (Exception e) {
	        String msg = Message.getString("HibernateStrategy.duringQuery");
	        Log4jlogging.error(msg, e);
	        release();
	        throw new PersistentException(msg, e);
	    }finally{
	    	try{
	    		rs.close();
	    		stat.close();
	    		conn.close();
	    	}catch(Exception ex){
	    	}

	    }
	    return result;
    }   
	/**
	 * receive agent data for save
	 */
	@Override
	public PersistentObject storeReceive(PersistentObject data)
			throws PersistentException {

		if (data == null) {
			throw new PersistentException(Message
					.getString("HibernateStrategy.nullPassedIn"));
		}
		Session ses = getSession();
		try {

			if (data.getId() == null || data.getId().intValue() == 0) {

				ses.save(data);
			}
			PersistentObject vo = (PersistentObject) ses.load(data.getClass(),
					data.getId());
			vo.setData(data);
			data = vo;
			if (ses != null) {
				// System.out.println("-------保存完数据，session还在，提交更改--------");
				ses.flush();
				if (!ses.connection().getAutoCommit()) {
					// System.out.println("-------session提交--------");
					ses.connection().commit();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			String msg = Message.formatString(
					"HibernateStrategy.exceptionStoring", data.getId()
							.toString());
			Log4jlogging.error(msg, e);
			release();
			throw new PersistentException(msg, e);
		}
		return data;
	}

	@Override
	public List findByProperty(Class clazz, String wherestr,
			String propertyName, Object value) {
		try {
			// String queryString = "from "+clazz.getName()+" as model where
			// model.";
			String queryString = "from " + clazz.getName() + " where 1=1";
			if (wherestr != null && !"".equals(wherestr)) {

				queryString += " and " + wherestr;
			}
			if (value == null) {
				queryString += " and " + propertyName + " is null ";
			} else {
				queryString += " and " + propertyName + "= ?";
			}
			Query q = getSession().createQuery(queryString);
			if (value != null) {
				q.setParameter(0, value);
			}
			return q.list();
		} catch (RuntimeException re) {
			// log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List findByProperty(Class clazz, String wherestr,String propertyName, Object value, String order) {

		String queryString = "from " + clazz.getName() + " where 1=1";
		if (wherestr != null && !"".equals(wherestr)) {

			queryString += " and " + wherestr;
		}
		if (value == null) {
			queryString += " and " + propertyName + " is null ";
		} else {
			queryString += " and " + propertyName + "= ? ";
		}
		if (order != null) {
			queryString += order;
		}
		Query q = null;
		List result = null;
		try {
			q = getSession().createQuery(queryString);
			if (value != null) {
				q.setParameter(0, value);
			}
			result = q.list();
		} catch (HibernateException e) {
			System.out.println(e.getMessage());
		}
		
		return result;

	}

	@Override
	public int jdbcBatch(List<String> sqls, int batchSize) throws PersistentException {
		Logger logger = Logger.getLogger(HibernateStrategy.class);
		int affectRows = 0;
		if(sqls==null||sqls.size()==0){
			//logger.info("-----execute jdbcBatch method with zero sql sentence!-----");
			return affectRows;
		}
		int i = 0;
		Connection conn = getDBConnection();
		Statement stat = null;
		boolean auto = true;
		try {
			auto = conn.getAutoCommit();
			conn.setAutoCommit(false);
			stat = conn.createStatement();
			for(String sql : sqls){
				stat.addBatch(sql);
				i++;
				if(i==batchSize){
					int[] ret = stat.executeBatch();
					conn.commit();
					//logger.debug("-------------------execute batch sql sentence count("+i+":"+Arrays.toString(ret)+")--------------------------");
					i=0;
					affectRows += ret.length;
				}
			}
			if(i>0){
				stat.executeBatch();
				conn.commit();
				affectRows += i;
			}
			conn.setAutoCommit(auto);
		} catch (SQLException e) {
			if(conn!=null){
				try {
					conn.rollback();
					conn.setAutoCommit(auto);
				} catch (SQLException e1) {
					logger.error(e1.getMessage());
					e1.printStackTrace();
				}
			}
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(stat!=null){stat.close();stat=null;}
				if(conn!=null){conn.close();conn=null;}
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return affectRows;
	}
	@Override
	public List<Map<String,String>> getDataList(String query) {

		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
		Map<String,String> dataMap = null;
		try {
			if (query.indexOf("$") > -1) {
				query = query.replaceAll("\\$\\d+", "\\?");
			}
			conn = getDBConnection();
			stat = conn.createStatement();
			rs = stat.executeQuery(query);
			String columnname = "";
			while (rs.next()) {
				dataMap = new HashMap<String,String>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					columnname = rs.getMetaData().getColumnLabel(i).toLowerCase();
					if (rs.getObject(i) instanceof Clob) {
						char[] result = null;
						if (rs.getObject(i) != null) {
							result = new char[(int) ((Clob) (rs.getObject(i)))
									.length()];
						} else {
							result = new char[1000];
						}
						java.io.Reader reader = ((Clob) (rs.getObject(i)))
								.getCharacterStream();
						reader.read(result);
						dataMap.put(columnname, new String(result));
					}else if (rs.getObject(i) instanceof Date) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						dataMap.put(columnname,format.format(format.parse(rs.getString(i))));
					} else {
						dataMap.put(columnname, rs.getObject(i) == null ? "" : String.valueOf(rs.getObject(i)));
					}
				}
				dataList.add(dataMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stat.close();
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return dataList;
	}
	
	public JsonBean getJsonBean(String query) throws PersistentException {
		
		if (query == null) {
		    throw new PersistentException("null not ValidQuery");
		}
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		JsonBean json = new JsonBean();
	    try {
	        if (query.indexOf("$") > -1) {
	            query = query.replaceAll("\\$\\d+", "\\?");
	        }
		    conn = getDBConnection();
	        stat = conn.createStatement();
	        StringBuffer str=new StringBuffer();
	        rs = stat.executeQuery(query);
	        String columnname = "";
	        String cl = null;
	        str.append("[");
			while(rs.next()) {
				if(!rs.isFirst()) {
					str.append(",");
				}
				str.append("{");
				Map map = new HashMap();
				for (int i=1;i<=rs.getMetaData().getColumnCount();i++)
				{
					columnname = rs.getMetaData().getColumnLabel(i).toLowerCase();
					if(map.containsKey(columnname)) {
						continue;
					}else {
						map.put(columnname, columnname);
					}
					if(i>1) {
						str.append(",");
					}
					str.append("\"");
					str.append(columnname);
					str.append("\"");
					str.append(":");
					str.append("\"");
					Object x = rs.getObject(i);
					if(x instanceof Clob)
					{
						cl = rs.getClob(i).getSubString((long)1,(int)rs.getClob(i).length());
						str.append(cl==null?"":JSONutil.convertStringJsonStr(cl));
					}
					else if(x instanceof Date){
						str.append(x==null?"":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(x));
					}
					else{
						str.append(x==null?"":JSONutil.convertStringJsonStr(x.toString()));
					}
					str.append("\"");		
				}
				str.append("}");
			}
			str.append("]");
			String jsonstr=str.toString();
			JSONArray jsonarray = JSONArray.fromObject(jsonstr);
			json.setJsonarray(jsonarray);
			json.setSize(jsonarray.size());
	    } catch (Exception e) {
	    }finally{
	    	try{
	    		if(rs != null) rs.close();
	    		if(stat != null)stat.close();
	    		if(conn != null)conn.close();
	    	}catch(Exception ex){
	    		throw new PersistentException(ex.getMessage());
	    	}
	    }
	    return json;
	}
	
	public JsonBean getJsonBean(String query, Connection conn) throws PersistentException {
		
		if (query == null) {
		    throw new PersistentException("null not ValidQuery");
		}
		Statement stat = null;
		ResultSet rs = null;
		JsonBean json = new JsonBean();
	    try {
	        if (query.indexOf("$") > -1) {
	            query = query.replaceAll("\\$\\d+", "\\?");
	        }
	        stat = conn.createStatement();
	        StringBuffer str=new StringBuffer();
	        rs = stat.executeQuery(query);
	        String columnname = "";
	        String cl = null;
	        str.append("[");
			while(rs.next()) {
				if(!rs.isFirst()) {
					str.append(",");
				}
				str.append("{");
				Map map = new HashMap();
				for (int i=1;i<=rs.getMetaData().getColumnCount();i++)
				{
					columnname = rs.getMetaData().getColumnLabel(i).toLowerCase();
					if(map.containsKey(columnname)) {
						continue;
					}else {
						map.put(columnname, columnname);
					}
					if(i>1) {
						str.append(",");
					}
					str.append("\"");
					str.append(columnname);
					str.append("\"");
					str.append(":");
					str.append("\"");
					Object x = rs.getObject(i);
					if(x instanceof Clob)
					{
						cl = rs.getClob(i).getSubString((long)1,(int)rs.getClob(i).length());
						str.append(cl==null?"":JSONutil.convertStringJsonStr(cl));
					}
					else if(x instanceof Date){
						str.append(x==null?"":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(x));
					}
					else{
						str.append(x==null?"":JSONutil.convertStringJsonStr(x.toString()));
					}
					str.append("\"");		
				}
				str.append("}");
			}
			str.append("]");
			String jsonstr=str.toString();
			JSONArray jsonarray = JSONArray.fromObject(jsonstr);
			json.setJsonarray(jsonarray);
			json.setSize(jsonarray.size());
	    } catch (Exception e) {
	    }finally{
	    	try{
	    		if(rs != null) rs.close();
	    		if(stat != null)stat.close();
	    	}catch(Exception ex){
	    		throw new PersistentException(ex.getMessage());
	    	}
	    }
	    return json;
	}
	
	/**
	 * 分页查询 
	 */
	public JsonBean getJsonBeanPaging(String query, int start, int limit) throws PersistentException {
		
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		JsonBean json = new JsonBean();
		String dbType = Config.getProp("DbType");
		/* 这个框架，分页start从0开始*/
		if(StringUtils.isEmpty(dbType)) return json;
		try {
			if (query.indexOf("$") > -1) {
				query = query.replaceAll("\\$\\d+", "\\?");
			}
			conn = getDBConnection();
			stat = conn.createStatement();
			StringBuffer str=new StringBuffer();
			String sql = "";
			if("oracle".equals(dbType)) {
				if (limit != -1) {
					sql = "select * from (select rownum as ro,t.* from( ?1 ) t) where ro<?2 and ro>=?3";
					sql = sql.replace("?1", query);
					sql = sql.replace("?2", Integer.toString(start + limit));
					sql = sql.replace("?3", Integer.toString(start));
				} else {
					sql = "select * from (select rownum as ro,t.* from( ?1 ) t)";
					sql = sql.replace("?1", query);
				}
			}else if("mysql".equals(dbType)) {
				sql = query;
				if (limit != -1) {
					sql = query + " limit ?1,?2";
					sql = sql.replace("?1",Integer.toString(start)).replace("?2", Integer.toString(limit));
				} 
			}else if("sqlserver".equals(dbType)) {
				if (limit != -1) {
					sql = "select * from (select row_number() over(order by tempcolumn) ro,* from (select top ?2 tempcolumn=0, ?1 ) t)tt where tt.ro<=?2 and tt.ro>?3";
					sql = sql.replace("?1", query.trim().substring(6));
					sql = sql.replace("?2", Integer.toString(start + limit));
					sql = sql.replace("?3", Integer.toString(start));
				} else {
					sql = query;
				}
			}
			rs = stat.executeQuery(sql);
			String columnname = "";
	        String cl = null;
	        str.append("[");
			while(rs.next()) {
				if(!rs.isFirst()) {
					str.append(",");
				}
				str.append("{");
				Map map = new HashMap();
				for (int i=1;i<=rs.getMetaData().getColumnCount();i++)
				{
					columnname = rs.getMetaData().getColumnLabel(i).toLowerCase();
					if(map.containsKey(columnname)) {
						continue;
					}else {
						map.put(columnname, columnname);
					}
					if(i>1) {
						str.append(",");
					}
					str.append("\"");
					str.append(columnname);
					str.append("\"");
					str.append(":");
					str.append("\"");
					Object x = rs.getObject(i);
					if(x instanceof Clob)
					{
						cl = rs.getClob(i).getSubString((long)1,(int)rs.getClob(i).length());
						str.append(cl==null?"":JSONutil.convertStringJsonStr(cl));
					}
					else if(x instanceof Date){
						str.append(x==null?"":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(x));
					}
					else{
						str.append(x==null?"":JSONutil.convertStringJsonStr(x.toString()));
					}
					str.append("\"");		
				}
				str.append("}");
			}
			str.append("]");
			String jsonstr=str.toString();
			JSONArray jsonarray = JSONArray.fromObject(jsonstr);
			json.setJsonarray(jsonarray);
			json.setSize(getCount(query));

		} catch (Exception e) {

			System.out.println(e.getMessage());

		} finally {
			try {
				if(rs != null) rs.close();
				if(stat != null) stat.close();
				if(conn != null) conn.close();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
		return json;
	}
	
	public ResultSet executeQuery(String sqlQuery, String sqlDesc)throws PersistentException, SQLException {
		CachedRowSet crs = new CachedRowSetImpl();
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		int affectRow = -1;
		try {
			if (sqlQuery.indexOf("$") > -1) {
				sqlQuery = sqlQuery.replaceAll("\\$\\d+", "\\?");
			}
			conn = getDBConnection();
			stat = conn.prepareStatement(sqlQuery,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stat.executeQuery();
			crs.populate(rs);
			affectRow = crs.size();
		} catch (Exception e) {
			throw new PersistentException("Exception during Query");
		} finally {
			try {
				if(rs != null) rs.close();
				if(stat != null) stat.close();
				if(conn != null) conn.close();
			} catch (SQLException ex) {
	    		 throw new PersistentException("Exception during close!" +
	    				"<Error_Code>" + ex.getErrorCode() + "</Error_Code><SQL_State>" + ex.getSQLState() + "</SQL_State>");
			}
		}
		return crs;
	} 
	
}