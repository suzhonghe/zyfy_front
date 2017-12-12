package com.zhongyang.java.dao;
import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.vo.useraccount.FundRecordAccount;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInfo;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInvestLoanVo;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInvestMoneyVo;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInvestRecordVo;

public interface UserAccountDao {

	public List<UserInvestMoneyVo> selectUserAccountInfo(String userid);
	
	public List<FundRecordAccount> selectUserFundRecord(Page<FundRecordAccount> page);
	
	public List<UserInvestRecordVo> selectUserInvestInfo(Page<UserInvestRecordVo> page);
	
	public List<UserInvestLoanVo> selectUserLoanInvestInfo(Page<UserInvestLoanVo> page);
	
	public UserInfo selectUserInfo(String userId);
}
