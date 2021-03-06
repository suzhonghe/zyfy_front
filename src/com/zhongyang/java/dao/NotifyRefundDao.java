package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.bankmanager.entity.NotifyRefund;

/**
 *@package com.zhongyang.java.dao
 *@filename NotifyRefundDao.java
 *@date 2017年10月18日上午9:49:16
 *@author suzh
 */
public interface NotifyRefundDao {

	int insertNotifyRefund(NotifyRefund notifyRefund);
	
	NotifyRefund selectOneByParams(NotifyRefund notifyRefund);
	
	List<NotifyRefund>selectMoreByParams(NotifyRefund notifyRefund);
}
