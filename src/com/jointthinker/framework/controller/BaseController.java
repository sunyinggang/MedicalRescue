package com.jointthinker.framework.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jointthinker.framework.business.ServiceContext;
import com.jointthinker.framework.business.ServiceContextManager;
import com.jointthinker.framework.common.exceptions.PersistentException;
import com.jointthinker.framework.persistence.IPersistentStrategy;
import com.jointthinker.framework.persistence.PersistentFactory;
/**
 * 基础控制器
 * 继承HandlerInterceptorAdapter利用preHandle和afterCompletion方法做手动事务控制，
 * 实现HandlerExceptionResolver做全局异常处理.
 * @author kangwei
 *
 */
public class BaseController extends HandlerInterceptorAdapter implements HandlerExceptionResolver{
	
	protected static Logger logger = Logger.getLogger(BaseController.class);
	
	//private ServiceContext sc;
	
/*	protected HttpServletRequest request;

	protected HttpServletResponse response;

   	protected HttpSession session;*/
   	
//   	protected UserSessionObject uso;
   	
   	private Pattern p;
   	
   	protected void sendResponse(HttpServletResponse response, String responseText){
   		response.setHeader("Cache-Control", "no-cache");
   		response.setContentType("text/json;charset=utf-8");
		PrintWriter printWriter = null;
		try {
	    	printWriter = response.getWriter();
	    	printWriter.print(responseText);
	    } catch (IOException ex) {
	    	ex.printStackTrace();
	    } finally {
	    	if (null != printWriter) {
	    		printWriter.flush();
	    		printWriter.close();
	    	}
	    }
   	}
   	
   	protected void sendResponse(HttpServletResponse response, boolean success){
   		response.setHeader("Cache-Control", "no-cache");
   		response.setContentType("text/json;charset=utf-8");
   		PrintWriter printWriter = null;
   		try {
   			printWriter = response.getWriter();
   			printWriter.print("{\"success\":"+success+"}");
   		} catch (IOException ex) {
   			ex.printStackTrace();
   		} finally {
   			if (null != printWriter) {
   				printWriter.flush();
   				printWriter.close();
   			}
   		}
   	}
   	/*
   	protected HttpServletRequest getServletRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }
   	
   	protected HttpServletResponse getServletResponse(){
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
    }*/
   	/*
   	@ModelAttribute
   	public void init(HttpServletRequest request, HttpServletResponse response){
   		this.request = request;
   		this.response = response;
   		this.session = request.getSession();
   		this.uso = (UserSessionObject)session.getAttribute("currentuserSessionObj");
   	}
	*/
	public void setMethodReg(String methodReg) {
		p = Pattern.compile(methodReg, Pattern.CASE_INSENSITIVE);
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
		String forwardAttr = (String) request.getAttribute("javax.servlet.forward.request_uri");
		if(forwardAttr==null || "".equals(forwardAttr)){
			HandlerMethod hm = (HandlerMethod)handler;
			String methodName = hm.getMethod().getName();
			logger.info("preHandle >> "+ hm.getMethod().toString());
			ServiceContext sc = ServiceContextManager.createServiceContext();
			IPersistentStrategy pStrategy = null;
			try {
				// 存入持久化对象
				pStrategy = PersistentFactory.getInstance().getRawPersistentStrategy();//建立SessionFactory
				if(p.matcher(methodName).matches()){
					pStrategy.begin();//session.beginTransaction();
				}
				sc.setContextPersistentStrategy(pStrategy);
				// 存入线程
				sc.setContextId(String.valueOf(Thread.currentThread().hashCode()));//存入线程
				//Thread.currentThread()返回对当前正在执行的线程对象的引用
				ServiceContextManager.storeServiceContext(sc);
				
			} catch (PersistentException e) {
				e.printStackTrace();
				try {
					if(pStrategy!=null){
						if(p.matcher(methodName).matches()){
							pStrategy.rollback();
						}
						pStrategy.release();
					}
				} catch (PersistentException ex) {
					ex.printStackTrace();
				}
			}
		}
		return true;
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String forwardAttr = (String) request.getAttribute("javax.servlet.forward.request_uri");
		if(forwardAttr==null || "".equals(forwardAttr)){
			ServiceContext sc = ServiceContextManager.getServiceContext();
			IPersistentStrategy pStrategy = sc.getContextPersistentStrategy();
			HandlerMethod hm = (HandlerMethod)handler;
			String methodName = hm.getMethod().getName();
			logger.info("afterCompletion >> " + hm.getMethod().toString());
			if(ex==null){
				if(p.matcher(methodName).matches()) {
					pStrategy.commit();
				}
			}else{
				if(p.matcher(methodName).matches()) {
					pStrategy.rollback();
				}
			}
			pStrategy.release();
			ServiceContextManager.storeServiceContext(null);
		}
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void afterConcurrentHandlingStarted(
			HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ex.printStackTrace();
		ModelAndView mav = new ModelAndView();
	    mav.addObject("exception", ex.getMessage());
	    mav.addObject("url", request.getRequestURL());
	    mav.setViewName("exception");
		return mav;
	}
}
