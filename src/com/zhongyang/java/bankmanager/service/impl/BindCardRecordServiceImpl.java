package com.zhongyang.java.bankmanager.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.bankmanager.entity.BindCardRecord;
import com.zhongyang.java.bankmanager.service.BindCardRecordService;
import com.zhongyang.java.dao.BindCardRecordDao;

/**
 *@package com.zhongyang.java.bankmanager.service.impl
 *@filename BindCardRecordServiceImpl.java
 *@date 2017年8月17日上午10:53:37
 *@author suzh
 */
@Service
public class BindCardRecordServiceImpl implements BindCardRecordService {
	
	private static Logger logger=Logger.getLogger(BindCardRecordServiceImpl.class);

	@Autowired
	private BindCardRecordDao bindCardRecordDao;
	
	@Override
	@Transactional
	public int addBindCardRecord(BindCardRecord record)throws Exception {
		int res=0;
		try{
			res=bindCardRecordDao.insertBindCardRecord(record);
		}catch(Exception e){
			logger.info("添加绑卡记录异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("添加绑卡记录第二次操作");
			try{
				res=bindCardRecordDao.insertBindCardRecord(record);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

	@Override
	@Transactional
	public int modifyBindCardRecord(BindCardRecord record)throws Exception {
		int res=0;
		try{
			res=bindCardRecordDao.updateBindCardRecord(record);
		}catch(Exception e){
			logger.info("修改绑卡记录异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("修改绑卡记录第二次操作");
			try{
				res=bindCardRecordDao.updateBindCardRecord(record);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

	@Override
	public BindCardRecord queryRecordByParams(BindCardRecord record) {
		return bindCardRecordDao.selectRecordByParams(record);
	}

}
