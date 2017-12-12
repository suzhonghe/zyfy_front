package com.zhongyang.java.system.util;

public class Message {
	
	private Integer code;
	
	private String message;
	
	public Message() {
		super();
	}

	public Message(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
