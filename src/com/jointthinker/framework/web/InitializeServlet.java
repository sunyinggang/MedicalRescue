package com.jointthinker.framework.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.jointthinker.framework.business.InitialManager;
import com.jointthinker.framework.common.exceptions.PersistentException;
public class InitializeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public InitializeServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		
		super.destroy(); 
		// Put your code here
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	@Override
	public void init() throws ServletException {
		
		InitialManager.init();
		try {
			InitialManager.initPool();//初始化线程池
			InitialManager.init2();
		}catch (PersistentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
