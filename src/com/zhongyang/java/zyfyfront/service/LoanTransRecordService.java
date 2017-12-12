package com.zhongyang.java.zyfyfront.service;

import com.zhongyang.java.zyfyfront.pojo.LoanTransRecord;

/**
 *@package com.zhongyang.java.zyfyfront.service
 *@filename LoanTransRecordService.java
 *@date 2017年7月14日下午3:13:22
 *@author suzh
 */
public interface LoanTransRecordService {
	
	public int addLoanTransRecord(LoanTransRecord record)throws Exception;
	
	public int modifyLoanTransRecord(LoanTransRecord record)throws Exception;
	
	public LoanTransRecord queryByParams(LoanTransRecord record);

}
