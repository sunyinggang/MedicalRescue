package com.jointthinker.framework.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.jointthinker.framework.core.BaseException;


public class DBPoolManager implements ConnManager {

	static Logger logger = Logger.getLogger(DBPoolManager.class.getName());
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
	
	public DBPoolManager(){	
		try {
			ds = (DataSource)ctx.lookup("jdbc/callcenter");//
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getDBConnection() {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			logger.info("Exception during getDBConnection! " + e.getErrorCode() + ":" +e.getMessage());
		}		
		return con;
	 }
	

	public void freeConnection(Connection conn) {
		
		try {
			if (conn != null) {
				if (!conn.isClosed()) {
					conn.close();
				}
				conn = null;
			}
		} catch (SQLException ex) {
			logger.info("Exception during freeConnection! " + ex.getErrorCode() + ":" +ex.getMessage());
			throw new BaseException("free connection error:" + ex.getMessage());
		}
		
	}

}
