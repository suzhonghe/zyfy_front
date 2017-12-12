package com.zhongyang.java.dao;

import com.zhongyang.java.bankmanager.entity.BmOrder;

/**
 *@package com.zhongyang.java.dao
 *@filename BmAccountOrderDao.java
 *@date 20172017年6月26日下午4:46:01
 *@author suzh
 */
public interface BmOrderDao {

	int insertBmOrder(BmOrder bmOrder);
	
	int updateBmOrderByParams(BmOrder bmOrder);
	
	BmOrder selectBmOrderByParams(BmOrder bmOrder);
}
