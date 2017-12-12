package com.zhongyang.java.bankmanager.params;

import com.zhongyang.java.bankmanager.entity.BmAccount;
import com.zhongyang.java.bankmanager.entity.Company;

/**
 *@package com.zhongyang.java.bankmanager.params
 *@filename BmAccountParams.java
 *@date 20172017年6月26日下午5:13:58
 *@author suzh
 */
public class BmAccountParams {
	
	private BmAccount bmaccount;
	
	private BmCompany bmCompany;
	
	private String source;
	
	private String xib_signup;
			
	public String getXib_signup() {
		return xib_signup;
	}

	public void setXib_signup(String xib_signup) {
		this.xib_signup = xib_signup;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public BmCompany getBmCompany() {
		return bmCompany;
	}

	public void setBmCompany(BmCompany bmCompany) {
		this.bmCompany = bmCompany;
	}

	public BmAccount getBmaccount() {
		return bmaccount;
	}

	public void setBmaccount(BmAccount bmaccount) {
		this.bmaccount = bmaccount;
	}
}
