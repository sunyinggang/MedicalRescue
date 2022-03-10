package com.jointthinker.framework.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AccessPermisionCheckFilter implements Filter{
	protected FilterConfig filterConfig = null;
    
    public void setFilterConfig(FilterConfig config) {
        this.filterConfig = config;
    }

    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    @Override
	public void init(FilterConfig filterConfig) throws ServletException {
    	this.filterConfig = filterConfig;
    }
    
    @Override
	public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
    	
    	HttpServletRequest castedRequest = (HttpServletRequest) request;
        HttpServletResponse castedResponse = (HttpServletResponse) response;
        HttpSession session = castedRequest.getSession();
        String path = castedRequest.getContextPath();
		String rootPath = castedRequest.getScheme()+"://"+castedRequest.getServerName()+":"+castedRequest.getServerPort()+path;
		String uri = castedRequest.getRequestURI();
        
		chain.doFilter(castedRequest,response);
    }
    
    @Override
	public void destroy() {
        this.filterConfig = null;
    }
}
