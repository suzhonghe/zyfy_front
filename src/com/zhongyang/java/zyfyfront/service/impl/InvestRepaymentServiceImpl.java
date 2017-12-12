package com.zhongyang.java.zyfyfront.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.InvestRepaymentDao;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.pojo.InvestRepayment;
import com.zhongyang.java.zyfyfront.service.InvestRepaymentService;

/**
 *@package com.zhongyang.java.zyfyfront.service.impl
 *@filename InvestRepaymentServiceImpl.java
 *@date 2017年7月18日下午2:43:25
 *@author suzh
 */
@Service
public class InvestRepaymentServiceImpl implements InvestRepaymentService {

	@Autowired
	private InvestRepaymentDao investRepaymentDao;
	
	@Override
	public List<InvestRepayment> queryInvestRepaymentByPage(Page<InvestRepayment> page) {
		return investRepaymentDao.selectInvestRepaymentByPage(page);
	}

}
