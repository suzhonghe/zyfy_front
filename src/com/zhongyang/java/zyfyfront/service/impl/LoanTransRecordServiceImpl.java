package com.zhongyang.java.zyfyfront.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.dao.LoanTransRecordDao;
import com.zhongyang.java.zyfyfront.pojo.LoanTransRecord;
import com.zhongyang.java.zyfyfront.service.LoanTransRecordService;

/**
 *@package com.zhongyang.java.zyfyfront.service.impl
 *@filename LoanTransRecordServiceImpl.java
 *@date 2017年7月14日下午3:15:19
 *@author suzh
 */
@Service
public class LoanTransRecordServiceImpl implements LoanTransRecordService {
	
	private static Logger logger=Logger.getLogger(LoanTransRecordServiceImpl.class);

	@Autowired
	private LoanTransRecordDao loanTransRecordDao;
	
	@Override
	@Transactional
	public int addLoanTransRecord(LoanTransRecord record)throws Exception {
		int res=0;
		try{
			res=loanTransRecordDao.insertLoanTransRecord(record);
		}catch(Exception e){
			logger.info("添加标的交易记录异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("添加标的交易记录第二次操作");
			try{
				res=loanTransRecordDao.insertLoanTransRecord(record);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

	
	@Override
	@Transactional
	public int modifyLoanTransRecord(LoanTransRecord record)throws Exception {
		int res=0;
		try{
			res=loanTransRecordDao.updateLoanTransRecord(record);
		}catch(Exception e){
			logger.info("修改标的交易记录异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("修改标的交易记录第二次操作");
			try{
				res=loanTransRecordDao.updateLoanTransRecord(record);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}
	
	@Override
	public LoanTransRecord queryByParams(LoanTransRecord record) {
		return loanTransRecordDao.selectByParams(record);
	}

}
