package com.zhongyang.java.dao;

import com.zhongyang.java.bankmanager.entity.BmAccount;

/**
 *@package com.zhongyang.java.dao
 *@filename BmAccountDao.java
 *@date 20172017年6月22日上午11:29:20
 *@author suzh
 */
public interface BmAccountDao {
	
	int insertBmAccount(BmAccount account);

	int updateBmAccountByParams(BmAccount account);
	
	BmAccount selectBmAccountByParams(BmAccount params);
	
	int relieveCard(BmAccount account);
}
