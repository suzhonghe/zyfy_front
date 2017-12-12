package com.zhongyang.java.zyfyfront.service;

import java.util.List;

import com.zhongyang.java.zyfyfront.pojo.Bank;

/**
 *@package com.zhongyang.java.zyfyfront.service
 *@filename BankService.java
 *@date 2017年7月6日下午1:30:00
 *@author suzh
 */
public interface BankService {

	public List<Bank> queryAllBanks();
	
	public Bank queryByParams(Bank bank);
}
