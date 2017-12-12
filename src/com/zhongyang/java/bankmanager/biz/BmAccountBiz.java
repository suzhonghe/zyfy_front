package com.zhongyang.java.bankmanager.biz;

import javax.servlet.http.HttpServletRequest;

import com.zhongyang.java.bankmanager.params.BmAccountParams;
import com.zhongyang.java.bankmanager.returndata.BmReturnData;

/**
 *@package com.zhongyang.java.bankmanager.biz
 *@filename BankAccountBiz.java
 *@date 20172017年6月26日下午5:09:21
 *@author suzh
 */
public interface BmAccountBiz {
	
	public BmReturnData bankManagerRegisteApply(HttpServletRequest request,BmAccountParams params);
	
	public BmReturnData bankManagerRegisteAffirm(HttpServletRequest request,BmAccountParams params);

	public BmReturnData relieveCard(HttpServletRequest request);
	
	public BmReturnData companyOpenAccont(HttpServletRequest request,BmAccountParams params);
	
	public BmReturnData companybindCard(HttpServletRequest request,BmAccountParams params);
	
	public Object companyBindCardCall(HttpServletRequest request);
	
	public BmReturnData openIIAccountApp(HttpServletRequest request);
	
	public BmReturnData openIIAccountPc(HttpServletRequest request);
	
	public BmReturnData openIIAccountResult(HttpServletRequest request,BmAccountParams params);
}
