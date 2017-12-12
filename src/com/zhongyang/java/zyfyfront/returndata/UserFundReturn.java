package com.zhongyang.java.zyfyfront.returndata;

import java.io.Serializable;

import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.zyfyfront.pojo.UserFund;

/**
 *@package com.zhongyang.java.zyfyfront.returndata
 *@filename UserFundReturn.java
 *@date 2017年8月3日上午9:33:18
 *@author suzh
 */
public class UserFundReturn implements Serializable{

	private Message message;
	
	private UserFund userFund;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public UserFund getUserFund() {
		return userFund;
	}

	public void setUserFund(UserFund userFund) {
		this.userFund = userFund;
	}
	
}
