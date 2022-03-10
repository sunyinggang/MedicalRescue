package com.jointthinker.framework.business;

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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import oracle.jdbc.OracleTypes;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.jointthinker.framework.bean.JsonBean;
import com.jointthinker.framework.common.exceptions.PersistentException;
import com.jointthinker.framework.common.util.JSONutil;
import com.jointthinker.framework.web.bean.ViewBean;
import com.jointthinker.module.dict.bean.SelectItemBean;
import com.jointthinker.module.dict.pojo.SelectItem;


public class BusinessManager {

	static Logger logger = Logger.getLogger(BusinessManager.class.getName());
	private static Context initCtx = null;
	
	private static Context ctx = null;
	
	private DataSource ds = null;
	
	static {
		try {
			initCtx = new InitialContext();
			ctx = (Context)initCtx.lookup("java:comp/env");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public BusinessManager(){	
		try {
			ds = (DataSource)ctx.lookup("jdbc/medicalrescue");//
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getDBConnection() throws PersistentException{
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			logger.info(e.getMessage());
			throw new PersistentException("Exception during getDBConnection!" +
					"<Error_Code>" + e.getErrorCode() + "</Error_Code><SQL_State>" + e.getSQLState() + "</SQL_State>");
		}		
		return con;
	 }
	
	 
	 @SuppressWarnings("unchecked")
	 public List jdbcQuery(String query, Class clazz) throws PersistentException{ 
		 if (query == null) {
			 throw new PersistentException("null not ValidQuery");
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
			stat = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stat.executeQuery();
			rs.beforeFirst();
			java.beans.BeanInfo info = java.beans.Introspector.getBeanInfo(clazz, Object.class);
			java.beans.PropertyDescriptor[] pds = info.getPropertyDescriptors();
			Hashtable htprops = new Hashtable();
			for (int k = 0; k < pds.length; k++) {
				String pname = pds[k].getName().toLowerCase();
				htprops.put(pname, pds[k]);
			}
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				Object bean = clazz.newInstance();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					String name = rsmd.getColumnLabel(i).toLowerCase();
					PropertyDescriptor pd = (PropertyDescriptor) htprops.get(name);
					if (pd == null)continue;
					Object value = rs.getObject(i);
					pd.getWriteMethod().invoke(bean,new Object[] { dbToBean(value, pd.getPropertyType()) });
				}
				result.add(bean);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new PersistentException("Exception during Query");
		} finally {
			try {
				if(rs != null) rs.close();
				if(stat != null) stat.close();
				if(conn != null) conn.close();
			} catch (SQLException ex) {
				logger.info(ex.getMessage());
	    		 throw new PersistentException("Exception during close!" +
	    				"<Error_Code>" + ex.getErrorCode() + "</Error_Code><SQL_State>" + ex.getSQLState() + "</SQL_State>");
			}
		}
		return result;
	 }
	

	 @SuppressWarnings("unchecked")
	 public List jdbcQuery(String query, Class clazz, Connection conn) throws PersistentException{	 
		 if (query == null) {
			 throw new PersistentException("null not ValidQuery");
		 }
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList result = new ArrayList();
		try {
			if (query.indexOf("$") > -1) {
				query = query.replaceAll("\\$\\d+", "\\?");
			}
			stat = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stat.executeQuery();
			rs.beforeFirst();
			java.beans.BeanInfo info = java.beans.Introspector.getBeanInfo(clazz, Object.class);
			java.beans.PropertyDescriptor[] pds = info.getPropertyDescriptors();
			Hashtable htprops = new Hashtable();
			for (int k = 0; k < pds.length; k++) {
				String pname = pds[k].getName().toLowerCase();
				htprops.put(pname, pds[k]);
			}
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				Object bean = clazz.newInstance();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					String name = rsmd.getColumnLabel(i).toLowerCase();
					PropertyDescriptor pd = (PropertyDescriptor) htprops.get(name);
					if (pd == null)continue;
					Object value = rs.getObject(i);
					pd.getWriteMethod().invoke(bean,new Object[] { dbToBean(value, pd.getPropertyType()) });
				}
				result.add(bean);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new PersistentException("Exception during Query");
		} finally {
			try {
				if(rs != null) rs.close();
				if(stat != null) stat.close();
			} catch (SQLException ex) {
				logger.info(ex.getMessage());
	    		 throw new PersistentException("Exception during close!" +
	    				"<Error_Code>" + ex.getErrorCode() + "</Error_Code><SQL_State>" + ex.getSQLState() + "</SQL_State>");
			}
		}
		return result;
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
	 
	 
	 /**
		 * 分页查询 
		 */
		public JsonBean getJsonBeanPaging(String query, int start, int limit) throws PersistentException {
			
			Connection conn = null;
			Statement stat = null;
			ResultSet rs = null;
			JsonBean json = new JsonBean();
			String dbType = Config.getProp("DbType");
			try {
				if (query.indexOf("$") > -1) {
					query = query.replaceAll("\\$\\d+", "\\?");
				}
				conn = getDBConnection();
				stat = conn.createStatement();
				StringBuffer str=new StringBuffer();
				String sql = "";
				StringUtils a;
				if(StringUtils.isEmpty(dbType)) return json;
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

				logger.info(e.getMessage());

			} finally {
				try {
					if(rs != null) rs.close();
					if(stat != null) stat.close();
					if(conn != null) conn.close();
				} catch (Exception ex) {
					logger.info(ex.getMessage());
				}
			}
			return json;
		}
		
		
		public int getCountBySqlServer(String sql) throws PersistentException {

			String newsql=sql.substring(0,sql.toLowerCase().lastIndexOf(" order by ")==-1?sql.length():sql.toLowerCase().lastIndexOf(" order by "));
			sql = "select count(*) as num from (" + newsql + ") ff";
			return execQtn(sql);
		}
		

		public int getCount(String sql) throws PersistentException {
			sql = "select count(*) as num from (" + sql + ") ff";
			return execQtn(sql);
		}
		
		
		public int execQtn(String query) throws PersistentException {
			int num = 0;
			if (query == null) {
				throw new PersistentException("null Not ValidQuery");
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
				rs.beforeFirst(); 
				while (rs.next()) {
					num = rs.getInt("num");
				}
			} catch (Exception e) {
				String msg = "jdbc exception during Query";
				logger.error(msg, e);
				throw new PersistentException(e.getMessage());
			} finally {
				try {
					if(rs != null) rs.close();
					if(stat != null) stat.close();
					if(conn != null) conn.close();
				} catch (Exception ex) {
					logger.info(ex.getMessage());
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
	                	objstr = JSONutil.convertStringJsonStr(objstr);
	                	return new StringReader(objstr);
	                }else {
	                	String objstr = (String)obj;
	                	objstr = JSONutil.convertStringJsonStr(objstr);
	                    return objstr;
	                }
	            }  else if (obj instanceof Clob){
	            	 char[] result = null;
                	 if(obj != null){
                         result = new char[ (int) ((Clob)(obj)).length()];
                     }else{
                         result = new char[1000];
                     }
                     String objstr = new String(result);
                     objstr = JSONutil.convertStringJsonStr(objstr);
	                 return objstr;
	            }else if (obj instanceof Date) {
					if (java.io.Reader.class.isAssignableFrom(returnType)) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						return new StringReader(format.format(format.parse(obj.toString())));
					} else {
						return obj;
					}
				} else {
					return obj;
				}
			} catch (Exception e) {
				throw new IllegalStateException("can't execute dbToBean method for " + obj);
			}

		}
	
	
	
	/**
	 * 插入或更新数据
	 * @param updateSql
	 * @return
	 * @throws PersistentException
	 */
	 public boolean createOrUpdateObject(String updateSql) throws PersistentException {
		 
		 boolean temp = false;
		 int affectRow = -1;
		 Connection conn = null;
		 Statement stmt = null;
		 try {
			conn = getDBConnection();
			stmt = conn.createStatement();
			affectRow = stmt.executeUpdate(updateSql);
			if(affectRow >0) {
				temp = true;
			}	
		} catch (SQLException e) {
			logger.info(e.getMessage());
			throw new PersistentException("Exception during Update!" +
					"<Error_Code>" + e.getErrorCode() + "</Error_Code><SQL_State>" + e.getSQLState() + "</SQL_State>");
		} finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				logger.info(e.getMessage());
				throw new PersistentException("Exception during close!" +
						"<Error_Code>" + e.getErrorCode() + "</Error_Code><SQL_State>" + e.getSQLState() + "</SQL_State>");
			}
		}
		return temp;
	 }
	 
	 /**
		 * 插入或更新数据
		 * @param updateSql
		 * @return
		 * @throws PersistentException
		 */
		 public boolean createOrUpdateObject(String updateSql, Connection conn) throws PersistentException {
			 
			 boolean temp = false;
			 int affectRow = -1;
			 Statement stmt = null;
			 try {
				stmt = conn.createStatement();
				affectRow = stmt.executeUpdate(updateSql);
				if(affectRow >0) {
					temp = true;
				}	
			} catch (SQLException e) {
				logger.info(e.getMessage());
				throw new PersistentException("Exception during Update!" +
						"<Error_Code>" + e.getErrorCode() + "</Error_Code><SQL_State>" + e.getSQLState() + "</SQL_State>");
			} finally {
				try {
					if(stmt != null) stmt.close();
				} catch (SQLException e) {
					logger.info(e.getMessage());
					throw new PersistentException("Exception during close!" +
							"<Error_Code>" + e.getErrorCode() + "</Error_Code><SQL_State>" + e.getSQLState() + "</SQL_State>");
				}
			}
			return temp;
		 }
 	
		 /**
		  * 插入数据并返回主键ID，
		  * 适用于只提供自增序列的数据库，Oracle\FireBird insertSql="insert into xx_cust(id,cust_type,cust_name,login_name)values(SEQUENCE.Nextval,'ipn','aaa','bb')"
		  * 适用于可定义自增主键方式的数据库，MYSQL\SQLServer\DB2    主銉必须是自增的方式,且字段名为id insertSql="insert into xx_cust(cust_type,cust_name,login_name)values('ipn','aaa','bb')"
		  * @param insertSql
		  * @return
		  * @throws PersistentException
		  */
		 public Long createObjectReturnId(String insertSql) throws PersistentException {
			 
			 if (insertSql == null) {
				  throw new PersistentException("null not Valid Insert");
			 }
			 if (insertSql.indexOf("$") > -1) {
				 insertSql = insertSql.replaceAll("\\$\\d+", "\\?");
		     }
			 Long returnId = null;
			 Connection conn = null;
			 ResultSet rs = null;
			 CallableStatement stmt = null;
			 PreparedStatement pstmt = null;
			 String dbType = Config.getProp("DbType");
			 if(StringUtils.isEmpty(dbType)) return null;
			 try {
				 conn = getDBConnection();
				 if("oracle".equals(dbType)) {
					 insertSql = "begin" + " " + insertSql + " " + "returning id into ?;end;";
					 stmt = conn.prepareCall(insertSql);
					 stmt.registerOutParameter(1, OracleTypes.BIGINT);
					 stmt.execute();
					 if(stmt != null) {				
						 returnId = stmt.getLong(1);
					 }
				 }else if("mysql".equals(dbType) || "sqlserver".equals(dbType) || "db2".equals(dbType)) {
					
					  pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
					  pstmt.execute();
					  rs = pstmt.getGeneratedKeys();
					  if(rs != null) {
						  if(rs.next()) {
							  if("mysql".equals(dbType)) {
								  returnId = rs.getLong("generated_key");
							  }else {
								  returnId = rs.getLong("id");
							  }
						  }
					  }
				 }
			} catch (SQLException e) {
				logger.info(e.getMessage());
				throw new PersistentException("Exception during Update!" +
						"<Error_Code>" + e.getErrorCode() + "</Error_Code><SQL_State>" + e.getSQLState() + "</SQL_State>");
			}catch (IllegalArgumentException e) {
				e.printStackTrace();
			}  finally {
				try {
					if(rs != null) rs.close();
					if(stmt != null) stmt.close();
					if(conn != null) conn.close();
				} catch (SQLException e) {
					logger.info(e.getMessage());
					throw new PersistentException("Exception during close!" +
							"<Error_Code>" + e.getErrorCode() + "</Error_Code><SQL_State>" + e.getSQLState() + "</SQL_State>");
				}
			}
			return returnId;
		 }
		 
	 
	 /**
	  * 批处理
	  * @param updateSql
	  * @param conn
	  * @return
	 * @throws PersistentException 
	  */
	 public boolean executeBatch(String[] updateSql) throws PersistentException {
		 boolean temp = true;
		 if(updateSql == null || updateSql.length == 0) return false;
		 Connection conn = null;
		 Statement stmt = null;
		 try {
			 conn = getDBConnection();
			 if(conn != null) {
				 conn.setAutoCommit(false);
				 stmt = conn.createStatement();
				 for(int i=0; i<updateSql.length; i++) {
					 stmt.addBatch(updateSql[i]);
				 }
				stmt.executeBatch();
				conn.commit();
			}else{
				return false;
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
			temp = false;
			if(conn != null)
				try {
					conn.rollback();
				} catch (SQLException e1) {
					logger.info(e1.getMessage());
				}
		} finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				logger.info(e.getMessage());
			}
		}
		 return temp;
	 }
	 
	 public boolean createNewObjectOnConnection(String insertSql, Connection conn) throws PersistentException {
			
			boolean temp = false;
			int affectRow = -1;
			Statement stmt = null;
			try {
				
				stmt = conn.createStatement();
				affectRow = stmt.executeUpdate(insertSql);
				if(affectRow >0 ) temp = true;
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new PersistentException("Exception during UpdateUnAutoCommit!" +
						"<Error_Code>" + e.getErrorCode() + "</Error_Code><SQL_State>" + e.getSQLState() + "</SQL_State>");
				
			} finally {		
				try {
					if(stmt != null) stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new PersistentException("Exception during close!" +
							"<Error_Code>" + e.getErrorCode() + "</Error_Code><SQL_State>" + e.getSQLState() + "</SQL_State>");
				}
			}
			return temp;
		}
	 
	 public ViewBean getViewBean(String query) throws PersistentException, SQLException {
			Connection conn=this.getDBConnection();
			ViewBean vb=getViewBean(query,conn);
			conn.close();
			return vb;
		}
		
	public ViewBean getViewBean(String query, Connection conn) {
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		ViewBean view = new ViewBean();
		List list1 = null;
		List valuelist = new ArrayList();
		List labellist = new ArrayList();
	    try {
	        if (query.indexOf("$") > -1) {
	            query = query.replaceAll("\\$\\d+", "\\?");
	        }
		 stat = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	     rs = stat.executeQuery();  
         String columnname = "";
		for(int j=1; j<=rs.getMetaData().getColumnCount(); j++) {
				
			columnname = rs.getMetaData().getColumnLabel(j);
			labellist.add(rs.getMetaData().getColumnLabel(j).toLowerCase());
				
		}
		while(rs.next()) {
			
			list1 = new ArrayList();	
			for (int i=1;i<=rs.getMetaData().getColumnCount();i++){
				Object x = rs.getObject(i);
				if(x instanceof Clob){
					char[] result = null;
					if(x != null) {
						result = new char[(int) ((Clob) (rs.getObject(i))).length()];
					}else {
						result = new char[1000];
					}
					list1.add(JSONutil.convertStringJsonStr(new String(result)));
				}else if(x instanceof Date){
					list1.add(x==null?"":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(x));
				}else{
					list1.add(x==null?"":JSONutil.convertStringJsonStr(x.toString()));
				}
			}
			valuelist.add(list1);
		}
		view.setLabellist(labellist);
		view.setValuelist(valuelist);
		view.setSize(valuelist.size());
		
	    } catch (Exception e) {
	    	
	    	logger.info(e.getMessage());
	    	
	    }finally{
	    	try{
	    		if(rs != null) rs.close();
	    		if(stat != null) stat.close();
	    	}catch(Exception ex){
	    		logger.info(ex.getMessage());
	    	}
	    }
	    return view;
	}
	
	 /**
	  * 批处理
	  * @param updateSql
	  * @param conn
	  * @return
	  */
	 public boolean executeBatch(List sqlList , Connection conn) {
		 boolean temp = true;
		 if(sqlList == null || sqlList.size()==0) return false;
		 if(conn == null) return false;
		 Statement stmt = null;
		 try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			for(int i=0; i<sqlList.size(); i++) {
				stmt.addBatch((String)sqlList.get(i));
				System.out.println("sql ["+i+"] " + (String)sqlList.get(i));
			}
			stmt.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			temp = false;
			if(conn != null)
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		} finally {
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		 return temp;
	 }
	 
	/* public List<SelectItemBean> getChildrenList(Long id)throws PersistentException{
			
			List<SelectItemBean> childrenList = new ArrayList<SelectItemBean>();
			SelectItemBean itemBean = null;
			List<SelectItem> childrenItems = this.getChildrenSelectItem();
			for(SelectItem item : childrenItems){
				itemBean = new SelectItemBean();
				if(item.getParentid().longValue()==id){
					BeanUtils.copyProperties(item, itemBean);
					itemBean.setText(item.getName());
					itemBean.setLeaf(item.getIsleaf()==1);
					childrenList.add(itemBean);
					if(item.getIsleaf()==0){
						itemBean.setChildren(getChildrenList(item.getId()));
					}
				}
			}
			return childrenList;
		}
		
		public List<SelectItem> getChildrenSelectItem() throws PersistentException{
			String sql = "select * from selectitem where parentid is not null and parentid <>1";
			List<SelectItem> childrenItems = this.jdbcQuery(sql, SelectItem.class);
			return childrenItems;
		}*/
}