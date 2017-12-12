package com.zhongyang.java.bankmanager.biz;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import com.zhongyang.java.bankmanager.returndata.BmLoanManagerReturn;

/**
 *@package com.zhongyang.java.bankmanager.biz
 *@filename BmloanManagerBiz.java
 *@date 2017年7月6日下午2:42:11
 *@author suzh
 */
public interface BmLoanManagerBiz {

	public BmLoanManagerReturn userInvest(HttpServletRequest request,String loanId,BigDecimal amount);

	public BmLoanManagerReturn borrowerRepay(HttpServletRequest request,String repaymentId);
	
	public BmLoanManagerReturn compensateRepay(HttpServletRequest request,String repaymentId);

}
