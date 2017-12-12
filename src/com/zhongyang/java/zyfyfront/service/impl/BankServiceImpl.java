package com.zhongyang.java.zyfyfront.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.BankDao;
import com.zhongyang.java.zyfyfront.pojo.Bank;
import com.zhongyang.java.zyfyfront.service.BankService;

/**
 *@package com.zhongyang.java.zyfyfront.service.impl
 *@filename BankServiceImpl.java
 *@date 2017年7月6日下午1:30:51
 *@author suzh
 */
@Service
public class BankServiceImpl implements BankService {

	@Autowired
	private BankDao bankDao;
	
	@Override
	public List<Bank> queryAllBanks() {
		return bankDao.selectAllBank();
	}

	@Override
	public Bank queryByParams(Bank bank) {
		return bankDao.selestByParams(bank);
	}

}
