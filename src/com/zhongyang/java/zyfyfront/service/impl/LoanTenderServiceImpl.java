package com.zhongyang.java.zyfyfront.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.dao.LoanTenderDao;
import com.zhongyang.java.zyfyfront.pojo.LoanTender;
import com.zhongyang.java.zyfyfront.service.LoanTenderService;

/**
 *@package com.zhongyang.java.zyfyback.service.impl
 *@filename LoanTenderServiceImpl.java
 *@date 2017年7月17日下午12:06:44
 *@author suzh
 */
@Service
public class LoanTenderServiceImpl implements LoanTenderService {
	
	private static Logger logger=Logger.getLogger(LoanTenderServiceImpl.class);

	@Autowired
	private LoanTenderDao loanTenderDao;
	
	@Override
	@Transactional
	public int addLoanTender(LoanTender loanTender)throws Exception {
		int res=0;
		try{
			res=loanTenderDao.insertLoanTender(loanTender);
		}catch(Exception e){
			logger.info("添加标的账户异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("添加标的账户第二次操作");
			try{
				res=loanTenderDao.insertLoanTender(loanTender);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

	@Override
	@Transactional
	public int modifyLoanTenderByParams(LoanTender loanTender)throws Exception {
		int res=0;
		try{
			res=loanTenderDao.updateLoanTenderByParams(loanTender);
		}catch(Exception e){
			logger.info("修改标的账户异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("修改标的账户第二次操作");
			try{
				res=loanTenderDao.updateLoanTenderByParams(loanTender);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

	@Override
	public LoanTender queryByParams(LoanTender loanTender) {
		return loanTenderDao.selectByParams(loanTender);
	}

}
