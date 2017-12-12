package com.zhongyang.java.zyfyfront.service;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.vo.useraccount.FundRecordAccount;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInfo;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInvestLoanVo;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInvestMoneyVo;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInvestRecordVo;

public interface UserAccountService {

	public List<UserInvestMoneyVo> getUserAccountInfo(String userid);
	
	public List<UserInvestRecordVo> selectUserInvestInfo(Page<UserInvestRecordVo> page); 
	
	public List<FundRecordAccount> queryUserFundRecord(Page<FundRecordAccount> page);

	public List<UserInvestLoanVo> queryUserLoanInvestInfo(Page<UserInvestLoanVo> page);

	public UserInfo queryUserInfo(String userId);
}
