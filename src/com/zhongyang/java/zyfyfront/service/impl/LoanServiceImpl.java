package com.zhongyang.java.zyfyfront.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.dao.LoanDao;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.pojo.Loan;
import com.zhongyang.java.zyfyfront.service.LoanService;
/**
 * 标的业务实现
 * @author suzh
 *
 */
@Service
public class LoanServiceImpl implements LoanService{
	
	private static Logger logger=Logger.getLogger(LoanServiceImpl.class);
	
	@Autowired
	private LoanDao loanDao;
	
	@Override
	public List<Loan> queryLoansByPage(Page<Loan> page){
		return loanDao.selectLoansByPage(page);
	}


	@Override
	public Loan queryLoanByParams(Loan loan){
		return loanDao.selectLoanByPrams(loan);
	}


	@Override
	public List<Loan> queryLoanManage(Page<Loan> page) {
		return loanDao.selectLoanManage(page);
	}


	@Override
	@Transactional
	public int modifyLoanByParams(Loan loan)throws Exception {
		int res=0;
		try{
			res=loanDao.updateLoanByParams(loan);
		}catch(Exception e){
			logger.info("修改标的异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("修改标的第二次操作");
			try{
				res=loanDao.updateLoanByParams(loan);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

}
