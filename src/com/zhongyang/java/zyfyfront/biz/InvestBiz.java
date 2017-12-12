package com.zhongyang.java.zyfyfront.biz;

import com.zhongyang.java.zyfyfront.params.InvestParams;
import com.zhongyang.java.zyfyfront.returndata.InvestReturn;

/**
 *@package com.zhongyang.java.zyfyfront.biz
 *@filename InvestBiz.java
 *@date 2017年8月3日上午10:31:38
 *@author suzh
 */
public interface InvestBiz {
	
	public InvestReturn queryInvestRecords(InvestParams params);
}
