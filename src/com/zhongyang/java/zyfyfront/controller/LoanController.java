package com.zhongyang.java.zyfyfront.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.zyfyfront.biz.LoanBiz;
import com.zhongyang.java.zyfyfront.params.LoanParams;
import com.zhongyang.java.zyfyfront.returndata.LoanReturn;
/**
 * 
 *@package com.zhongyang.java.zyfyfront.controller
 *@filename LoanController.java
 *@date 20172017年6月21日上午9:08:57
 *@author suzh
 */
@Controller
public class LoanController extends BaseController{

	@Autowired
	private LoanBiz loanBiz;
	
	/**
	 * 根据参数分页查询标的信息
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/front/loan/searchLoansByParams",method=RequestMethod.POST)
	public @ResponseBody LoanReturn searchLoansByPage(LoanParams params){
		return loanBiz.queryLoansByPage(params);
	}
	/**
	 * 
	 *@date 2017年6月21日上午8:59:15
	 *@param params
	 *@return LoanReturn
	 *@author suzh
	 */
	@RequestMapping(value="/front/loan/loanDetail",method=RequestMethod.POST)
	public @ResponseBody LoanReturn searchloanByParams(LoanParams params){
		return loanBiz.queryLoanByParams(params);
	}
}
