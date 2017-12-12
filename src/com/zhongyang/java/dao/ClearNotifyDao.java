package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.bankmanager.entity.ClearNotify;

/**
 *@package com.zhongyang.java.dao
 *@filename ClearNotifyDao.java
 *@date 2017年10月18日上午9:28:01
 *@author suzh
 */
public interface ClearNotifyDao {
	
	int insertClearNotify(ClearNotify clearNotify);
	
	ClearNotify selectOneByParams(ClearNotify clearNotify);
	
	List<ClearNotify>selectMoreByParams(ClearNotify clearNotify);
}
