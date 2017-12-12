package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.pojo.LoanRepayment;

/**
 *@package com.zhongyang.java.dao
 *@filename LoanRepaymentDao.java
 *@date 2017年7月4日下午1:53:28
 *@author suzh
 */
public interface LoanRepaymentDao {

	List<LoanRepayment>selectLoanRepaymentsByPage(Page<LoanRepayment> page);
	
	LoanRepayment selectLoanRepaymentByParams(LoanRepayment params);
	
	int updateLoanRepaymentByParams(LoanRepayment params);
}
