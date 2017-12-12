package com.zhongyang.java.dao;

import com.zhongyang.java.zyfyfront.pojo.LoanTransRecord;

/**
 *@package com.zhongyang.java.dao
 *@filename LoanTransRecordDao.java
 *@date 2017年7月14日下午3:01:12
 *@author suzh
 */
public interface LoanTransRecordDao {

	public int insertLoanTransRecord(LoanTransRecord record);
	
	public int updateLoanTransRecord(LoanTransRecord record);
	
	public LoanTransRecord selectByParams(LoanTransRecord record);
}
