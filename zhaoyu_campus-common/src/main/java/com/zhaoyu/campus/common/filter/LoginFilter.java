package com.zhaoyu.campus.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter{
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		String uri = request.getRequestURI();
		System.out.println("loginFilter:"+uri);
		filterChain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		System.out.println("loginFilter destroy");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("loginFilter init");
	}

}