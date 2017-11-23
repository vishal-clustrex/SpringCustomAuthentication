package com.opt.security;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opt.util.Constants;
import com.opt.util.EncryptDecrypt;
import com.opt.util.Util;

public class TokenManager {
	
	public HttpServletResponse createSessionToken(HttpServletRequest request, HttpServletResponse response, String userId) throws Exception{
		Message message = new Message(userId, request.getRemoteAddr(), new Date().getTime());
		Cookie myCookie = new Cookie(Constants.COOKIE_NAME, message.getMessage());
		myCookie.setMaxAge(Integer.parseInt(Util.getCookieExpireTime()) * 60);
		response.addCookie(myCookie);
		return response;
	}
	
	public Cookie getSessionTokenCookie(Cookie[] cookies){
		Cookie sessionTokenCookie = null;
		for(Cookie cookie : cookies){
    		if(cookie.getName().equals(Constants.COOKIE_NAME)){
    			sessionTokenCookie = cookie;
    			break;
    		}
    	}
		return sessionTokenCookie;
	}
	
	public boolean renewalSessionToken(Cookie sessionTokenCookie, HttpServletRequest request, HttpServletResponse response)throws Exception{
		EncryptDecrypt encryptDecrypt = new EncryptDecrypt();
		String cookieValue = encryptDecrypt.decrypt(Constants.SECRET_KEY, sessionTokenCookie.getValue());
		String cookieTimeStamp = cookieValue.substring((cookieValue.indexOf("--")+2), cookieValue.lastIndexOf("--"));
		Long cookieExpiryCheck =  Long.parseLong(cookieTimeStamp) + Integer.parseInt(Util.getCookieExpireTime()) - 120;
		Long timeStamp = new Date().getTime();
		
		if(Message.isValid(cookieValue, request)){
			response.sendRedirect("/demo.html");
    		return false;
		}
		
		if(cookieExpiryCheck >= timeStamp) {
			sessionTokenCookie.setMaxAge(Integer.parseInt(Util.getCookieExpireTime()));
			response.addCookie(sessionTokenCookie);
		}
		return true;
	}
	
}
