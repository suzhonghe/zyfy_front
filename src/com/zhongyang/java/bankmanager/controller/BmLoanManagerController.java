package com.zhongyang.java.bankmanager.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.bankmanager.biz.BmLoanManagerBiz;
import com.zhongyang.java.bankmanager.returndata.BmLoanManagerReturn;
import com.zhongyang.java.zyfyfront.controller.BaseController;

/**
 *@package com.zhongyang.java.bankmanager.controller
 *@filename BmLoanManagerController.java
 *@date 2017年7月6日下午2:14:21
 *@author suzh
 */
@Controller
public class BmLoanManagerController extends BaseController{
	
	@Autowired
	private BmLoanManagerBiz bmLoanManagerBiz;
	
	/**
	 * 用户投资
	 *@date 下午5:04:38
	 *@param request
	 *@param loanId
	 *@param amount
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value="/front/loan/userInvest",method=RequestMethod.POST)
	public synchronized @ResponseBody BmLoanManagerReturn userInvest(HttpServletRequest request,String loanId,BigDecimal amount){
		return bmLoanManagerBiz.userInvest(request,loanId,amount);
	}

	/**
	 * 借款人还款
	 *@date 下午6:25:35
	 *@param request
	 *@param repaymentId
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value="/front/loan/borrowerRepay",method=RequestMethod.POST)
	public synchronized @ResponseBody BmLoanManagerReturn borrowerRepay(HttpServletRequest request,String repaymentId){
		return bmLoanManagerBiz.borrowerRepay(request,repaymentId);
	}
	
	/**
	 * 代偿还款
	 *@date 下午6:25:35
	 *@param request
	 *@param repaymentId
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value="/front/loan/compensateRepay",method=RequestMethod.POST)
	public synchronized @ResponseBody BmLoanManagerReturn compensateRepay(HttpServletRequest request,String repaymentId){
		return bmLoanManagerBiz.compensateRepay(request,repaymentId);
	}
}
