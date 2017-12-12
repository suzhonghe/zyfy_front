package com.zhongyang.java.system.Exception;

import com.zhongyang.java.system.enumtype.SystemEnum;

public class UException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Exception exception;
	private SystemEnum code;
	private String message;
	private int state;
	
	public Exception getException() {
		return exception;
	}
	public void setE(Exception e) {
		this.exception = e;
	}
	public SystemEnum getCode() {
		return code;
	}
	public void setCoid(SystemEnum code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessige(String message) {
		this.message = message;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	public UException(Exception e) {
		this.exception = e;
		this.state=1;
	}
	
	public UException(SystemEnum code, String message) {
		this.code = code;
		this.message = message;
		this.state=0;
	}
	
	public UException(SystemEnum code) {
		this.code = code;
		this.state=0;
	}
}
