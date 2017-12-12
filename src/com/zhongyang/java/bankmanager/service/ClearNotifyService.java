package com.zhongyang.java.bankmanager.service;

import java.util.List;

import com.zhongyang.java.bankmanager.entity.ClearNotify;

/**
 *@package com.zhongyang.java.dao
 *@filename ClearNotifyDao.java
 *@date 2017年10月18日上午9:28:01
 *@author suzh
 */
public interface ClearNotifyService {
	
	int addClearNotify(ClearNotify clearNotify);
	
	ClearNotify queryOneByParams(ClearNotify clearNotify);
	
	List<ClearNotify>queryMoreByParams(ClearNotify clearNotify);
}
