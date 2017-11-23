package com.opt.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class Interceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("Pre handling the request");
		TokenManager sessionTokenManagement = new TokenManager();
		Cookie[] cookies = request.getCookies();
    	if(cookies == null){
    		response.sendRedirect("/demo.html");
    		return false;
    	}
    	Cookie sessionTokenCookie = sessionTokenManagement.getSessionTokenCookie(cookies);
    	
    	if(sessionTokenCookie != null) {
    		sessionTokenManagement.renewalSessionToken(sessionTokenCookie, request, response);
    	}
    	else {
    		response.sendRedirect("/demo.html");
    		return false;
    	}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		System.out.println("Post handling the request");
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		System.out.println("After completion");
	}
	
}
