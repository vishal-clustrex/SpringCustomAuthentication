package com.opt.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringHelper {

	private static ApplicationContext context;

	static {
		try {
			context = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");
			System.out.println("Bean file loaded");
		} catch (Exception e) {
			System.out.println("Bean file not loaded for file");
		}
	}

	public static ApplicationContext getAppContext() {
		return context;
	}

}
