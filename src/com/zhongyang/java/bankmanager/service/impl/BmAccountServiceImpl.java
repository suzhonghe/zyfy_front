package com.zhongyang.java.bankmanager.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.bankmanager.entity.BmAccount;
import com.zhongyang.java.bankmanager.service.BmAccountService;
import com.zhongyang.java.dao.BmAccountDao;
import com.zhongyang.java.system.Exception.UException;

/**
 *@package com.zhongyang.java.zyfyfront.service.impl
 *@filename BmAccountServiceImpl.java
 *@date 20172017年6月22日下午2:11:33
 *@author suzh
 */
@Service
public class BmAccountServiceImpl implements BmAccountService {
	
	private static Logger logger=Logger.getLogger(BmAccountServiceImpl.class);
	
	@Autowired
	private BmAccountDao bmAccountDao;
	
	/**
	 * 开户信息修改
	 *@date 2017年6月29日下午4:55:28
	 *@param bmAccount
	 *@throws UException
	 *@author suzh
	 */
	@Transactional
	public void bmAccountModify(BmAccount bmAccount)throws Exception{
		try{
			bmAccountDao.updateBmAccountByParams(bmAccount);
		}catch(Exception e){
			logger.info("开户信息修改异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("开户信息修改第二次操作");
			try{
				bmAccountDao.updateBmAccountByParams(bmAccount);
			}catch(Exception e1){
				throw new Exception();
			}
		}
			
	}
	@Override
	@Transactional
	public int addBmAccount(BmAccount account)throws Exception{
		int res=0;
		try{
			res=bmAccountDao.insertBmAccount(account);
		}catch(Exception e){
			logger.info("开户信息添加异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("开户信息添加第二次操作");
			try{
				res=bmAccountDao.insertBmAccount(account);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}

	
	@Override
	@Transactional
	public int modifyBmAccountByParams(BmAccount account)throws Exception {
		int res=0;
		try{
			res=bmAccountDao.updateBmAccountByParams(account);
		}catch(Exception e){
			logger.info("开户信息修改异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("开户信息修改第二次操作");
			try{
				res=bmAccountDao.updateBmAccountByParams(account);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return res;
	}
	
	@Override
	public BmAccount queryBmAccountByParams(BmAccount params) {
		return bmAccountDao.selectBmAccountByParams(params);
	}
	@Override
	@Transactional
	public int relieveCard(BmAccount account) {
		return bmAccountDao.relieveCard(account);
	}

}
