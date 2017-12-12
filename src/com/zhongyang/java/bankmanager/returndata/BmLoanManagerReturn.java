package com.zhongyang.java.bankmanager.returndata;

import java.io.Serializable;

import com.zhongyang.java.system.util.Message;

/**
 *@package com.zhongyang.java.bankmanager.returndata
 *@filename BmLoanManagerReturn.java
 *@date 2017年7月6日下午2:17:35
 *@author suzh
 */
public class BmLoanManagerReturn implements Serializable{
	
	private Message message;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

}
