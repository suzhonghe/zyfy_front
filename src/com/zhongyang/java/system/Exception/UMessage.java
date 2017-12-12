package com.zhongyang.java.system.Exception;

import com.zhongyang.java.system.enumtype.SystemEnum;


public class UMessage {
	
	private static UMessage uMessage;
	private SystemEnum code;
	private String message;
	
	public SystemEnum getCode() {
		return code;
	}
	public void setCode(SystemEnum code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private UMessage() {}
	
	public static UMessage getuMessage() {
		if(uMessage==null){
			uMessage=new UMessage();
		}
		return uMessage;
	}
	public void setuMessage(UMessage uMessage) {
		this.uMessage = uMessage;
	}
	@Override
	public String toString() {
		return "UMessage [code=" + code + ", message=" + message + "]";
	}
	
	
	

	
}
