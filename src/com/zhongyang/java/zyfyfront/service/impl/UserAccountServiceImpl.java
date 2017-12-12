package com.zhongyang.java.zyfyfront.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.FundRecordDao;
import com.zhongyang.java.dao.UserAccountDao;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.service.UserAccountService;
import com.zhongyang.java.zyfyfront.vo.useraccount.FundRecordAccount;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInfo;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInvestLoanVo;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInvestMoneyVo;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInvestRecordVo;

@Service
public class UserAccountServiceImpl implements UserAccountService {
	
	@Autowired
	UserAccountDao userAccountDao;

	@Autowired
	FundRecordDao fundRecordDao;
	
	@Override
	public List<UserInvestMoneyVo> getUserAccountInfo(String userid) {
		return userAccountDao.selectUserAccountInfo(userid);
	}
	@Override
	public List<UserInvestRecordVo> selectUserInvestInfo(
			Page<UserInvestRecordVo> page) {
		return userAccountDao.selectUserInvestInfo(page);
	}
	@Override
	public List<FundRecordAccount> queryUserFundRecord(Page<FundRecordAccount> page) {
		return userAccountDao.selectUserFundRecord(page);
	}
	@Override
	public List<UserInvestLoanVo> queryUserLoanInvestInfo(Page<UserInvestLoanVo> page) {
		return userAccountDao.selectUserLoanInvestInfo(page);
	}
	@Override
	public UserInfo queryUserInfo(String userId) {
		return userAccountDao.selectUserInfo(userId);
	}

}
