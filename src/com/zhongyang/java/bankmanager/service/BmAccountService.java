package com.zhongyang.java.bankmanager.service;

import com.zhongyang.java.bankmanager.entity.BmAccount;

/**
 *@package com.zhongyang.java.zyfyfront.service
 *@filename BmAccountService.java
 *@date 20172017年6月22日下午2:10:40
 *@author suzh
 */
public interface BmAccountService {
	
	public int addBmAccount(BmAccount account)throws Exception;

	public int modifyBmAccountByParams(BmAccount account)throws Exception;
	
	public BmAccount queryBmAccountByParams(BmAccount params);
	
	public void bmAccountModify(BmAccount bmAccount)throws Exception;
	
	public int relieveCard(BmAccount account);
}
