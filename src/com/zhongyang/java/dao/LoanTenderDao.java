package com.zhongyang.java.dao;

import com.zhongyang.java.zyfyfront.pojo.LoanTender;

/**
 *@package com.zhongyang.java.dao
 *@filename LoanTenderDao.java
 *@date 2017年7月17日上午11:38:32
 *@author suzh
 */
public interface LoanTenderDao {
	
	int insertLoanTender(LoanTender loanTender);
	
	int updateLoanTenderByParams(LoanTender loanTender);
	
	LoanTender selectByParams(LoanTender loanTender);

}
