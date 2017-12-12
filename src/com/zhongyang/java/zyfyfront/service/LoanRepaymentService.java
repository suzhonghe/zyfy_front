package com.zhongyang.java.zyfyfront.service;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.pojo.LoanRepayment;

/**
 *@package com.zhongyang.java.zyfyfront.service
 *@filename LoanRepaymentService.java
 *@date 2017年7月18日下午1:46:00
 *@author suzh
 */
public interface LoanRepaymentService {

	public List<LoanRepayment>queryLoanRepaymentsByPage(Page<LoanRepayment> page);
	
	public LoanRepayment queryLoanRepaymentByParams(LoanRepayment params);
	
	public int modifyLoanRepaymentByParams(LoanRepayment params)throws Exception;
}
