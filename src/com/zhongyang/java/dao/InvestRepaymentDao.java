package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.pojo.InvestRepayment;

/**
 *@package com.zhongyang.java.dao
 *@filename InvestRepaymentDao.java
 *@date 2017年7月7日下午4:57:05
 *@author suzh
 */
public interface InvestRepaymentDao {
	
	public List<InvestRepayment> selectInvestRepaymentByPage(Page<InvestRepayment> page);

}
