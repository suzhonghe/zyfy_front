package com.zhongyang.java.zyfyfront.service;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.pojo.InvestRepayment;

/**
 * 
 *@package com.zhongyang.java.zyfyfront.service
 *@filename InvestRepaymentService.java
 *@date 2017年7月18日下午2:43:04
 *@author suzh
 */
public interface InvestRepaymentService {
	
	public List<InvestRepayment> queryInvestRepaymentByPage(Page<InvestRepayment> page);

}
