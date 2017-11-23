package com.opt.util;

public class Util {
	
	public static String getCookieExpireTime() {
		return System.getProperty(Constants.COOKIE_EXPIRE_TIME_IN_MIN);
	}
	
	public static String[] getIncludePathPatterns() {
		return System.getProperty(Constants.INCLUDE_PATH_PATTERNS).split(",");
	}
	
	public static String[] getExcludePathPatterns() {
		return System.getProperty(Constants.EXCLUDE_PATH_PATTERNS).split(",");
	}
	
}
