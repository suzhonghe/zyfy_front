package com.zhongyang.java.bankmanager.returndata;

import com.zhongyang.java.system.util.Message;

/**
 *@package com.zhongyang.java.bankmanager.returndata
 *@filename BmReturnData.java
 *@date 20172017年6月26日下午5:12:05
 *@author suzh
 */
public class BmReturnData {
	
	private  Message message;
	
	private String rep;
	
	public String getRep() {
		return rep;
	}

	public void setRep(String rep) {
		this.rep = rep;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
}
