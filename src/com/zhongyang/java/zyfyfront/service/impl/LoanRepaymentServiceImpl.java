package com.zhongyang.java.zyfyfront.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.dao.LoanRepaymentDao;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.pojo.LoanRepayment;
import com.zhongyang.java.zyfyfront.service.LoanRepaymentService;

/**
 *@package com.zhongyang.java.zyfyfront.service.impl
 *@filename LoanRepaymentServiceImpl.java
 *@date 2017年7月18日下午1:46:24
 *@author suzh
 */
@Service
public class LoanRepaymentServiceImpl implements LoanRepaymentService {
	
	private static Logger logger=Logger.getLogger(LoanRepaymentServiceImpl.class);

	@Autowired
	private LoanRepaymentDao loanRepaymentDao;
	
	@Override
	public List<LoanRepayment> queryLoanRepaymentsByPage(Page<LoanRepayment> page) {
		return loanRepaymentDao.selectLoanRepaymentsByPage(page);
	}

	@Override
	public LoanRepayment queryLoanRepaymentByParams(LoanRepayment params) {
		return loanRepaymentDao.selectLoanRepaymentByParams(params);
	}

	@Override
	@Transactional
	public int modifyLoanRepaymentByParams(LoanRepayment params)throws Exception {
		int res=0;
		try{
			res=loanRepaymentDao.updateLoanRepaymentByParams(params);
		}catch(Exception e){
			logger.info("修改还款计划异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("修改还款计划第二次操作");
			try{
				res=loanRepaymentDao.updateLoanRepaymentByParams(params);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

}
