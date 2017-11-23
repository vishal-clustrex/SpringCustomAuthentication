package com.opt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opt.security.TokenManager;

@Controller
public class DemoController {
	
	@RequestMapping(value="/api/test", method = RequestMethod.GET)
	public @ResponseBody String test(){
		return "test";
	}
	
	@RequestMapping(value="/api1/test", method = RequestMethod.GET)
	public @ResponseBody String test1(){
		return "test1";
	}
	
	@RequestMapping(value="/api2/test", method = RequestMethod.GET)
	public @ResponseBody String test2(){
		return "test2";
	}
	
	@RequestMapping(value="/authenticate", method = RequestMethod.POST)
	@ResponseBody
	public String authenticateUser(HttpServletRequest request, HttpServletResponse response, @RequestParam("userId") String userId) throws Exception{
		TokenManager sessionTokenManagement = new TokenManager();
		sessionTokenManagement.createSessionToken(request, response, userId);
		return "{\"status\" : \"Success\"}";
	}
	
}
