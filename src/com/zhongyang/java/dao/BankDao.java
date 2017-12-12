package com.zhongyang.java.dao;

import java.util.List;



import com.zhongyang.java.zyfyfront.pojo.Bank;

/**
 *@package com.zhongyang.java.dao
 *@filename BankDao.java
 *@date 2017年7月6日上午11:37:23
 *@author suzh
 */
public interface BankDao {
	
	public List<Bank> selectAllBank();
	
	public Bank selestByParams(Bank bank);
}
