package com.zhongyang.java.zyfyfront.returndata;

import java.util.List;

import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.zyfyfront.pojo.Bank;

/**
 *@package com.zhongyang.java.zyfyfront.returndata
 *@filename BankReturn.java
 *@date 2017年7月6日下午1:32:35
 *@author suzh
 */
public class BankReturn {

	private Message message;
	
	private List<Bank>banks;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public List<Bank> getBanks() {
		return banks;
	}

	public void setBanks(List<Bank> banks) {
		this.banks = banks;
	}
	
}
