package com.zhongyang.java.zyfyfront.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.dao.UserFundDao;
import com.zhongyang.java.zyfyfront.pojo.UserFund;
import com.zhongyang.java.zyfyfront.service.UserFundService;
@Service
public class UserFundServiceImpl implements UserFundService {
	
	private static Logger logger= Logger.getLogger(UserFundServiceImpl.class);
	
	@Autowired
	private UserFundDao userFundDao;
	
	@Override
	@Transactional
	public void modifyUserFundByParams(UserFund userFund) throws Exception{
		try{
			userFundDao.updateUserFundByParams(userFund);
		}catch(Exception e){
			logger.info("修改账户资金异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("修改账户资金第二次操作");
			try{
				userFundDao.updateUserFundByParams(userFund);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		
	}

	@Override
	@Transactional
	public int addUserFund(UserFund UserFund)throws Exception {
		int res=0;
		try{
			res=userFundDao.insertUserFund(UserFund);
		}catch(Exception e){
			logger.info("添加资金账户异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("添加资金账户第二次操作");
			try{
				res=userFundDao.insertUserFund(UserFund);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

	@Override
	public UserFund queryUserFundByParams(UserFund userFund) {
		return userFundDao.selectUserFundByParams(userFund);
	}

}
