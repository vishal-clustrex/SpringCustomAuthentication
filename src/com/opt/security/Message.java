package com.opt.security;

import javax.servlet.http.HttpServletRequest;

import com.opt.util.Constants;
import com.opt.util.EncryptDecrypt;

public class Message {
	
	public static String MESSAGE_SEPARATOR = "--";
	
	private String userId;
	private String ipAddress;
	private long timestamp;
	private String message;
	
	public Message(String userId, String ipAddress, long timestamp) throws Exception {
		this.userId  = userId;
		this.ipAddress = ipAddress;
		this.timestamp = timestamp;
		this.message = this.ipAddress + Message.MESSAGE_SEPARATOR + this.timestamp + Message.MESSAGE_SEPARATOR + this.userId;
		this.setMessage(this.message);
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) throws Exception {
		EncryptDecrypt encryptDecrypt = new EncryptDecrypt();
		this.message = encryptDecrypt.encrypt(Constants.SECRET_KEY, message);
	}
	
	
	public boolean isValidIPAddress(String ipAddress){
		if(this.ipAddress == ipAddress){
			return true;
		}
		return false;
	}
	
	public boolean isValidUserId(String userId){
		if(this.userId == userId){
			return true;
		}
		return false;
	}
	
	public boolean isValidTimeStamp(long timeStamp){
		if(this.timestamp <= timeStamp){
			return true;
		}
		return false;
	}
	
	public static boolean isValid(String message, HttpServletRequest request){
		String cookieIpAddress = message.substring(0, message.indexOf("--"));
		String cookieUserId = message.substring((message.lastIndexOf("--")+2), message.length());
		if((cookieIpAddress != request.getRemoteAddr()) && (cookieUserId != Constants.USER_ID)){
			return false;
		}
		return true;
	}
	
}
