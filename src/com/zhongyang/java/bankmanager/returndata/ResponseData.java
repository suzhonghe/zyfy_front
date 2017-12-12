package com.zhongyang.java.bankmanager.returndata;

/**
 *@package com.zhongyang.java.bankmanager.returndata
 *@filename ResponseData.java
 *@date 2017年7月4日上午11:35:00
 *@author suzh
 */
public class ResponseData {
	
	private String recode;

	public String getRecode() {
		return recode;
	}

	public void setRecode(String recode) {
		this.recode = recode;
	}

	@Override
	public String toString() {
		return "ResponseData [recode=" + recode + "]";
	}
}
