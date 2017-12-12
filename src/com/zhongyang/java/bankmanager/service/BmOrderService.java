package com.zhongyang.java.bankmanager.service;

import com.zhongyang.java.bankmanager.entity.BmOrder;

/**
 * 
 *@package com.zhongyang.java.bankmanager.service
 *@filename BmAccountOrderService.java
 *@date 20172017年6月26日下午5:03:29
 *@author suzh
 */
public interface BmOrderService {
	
	BmOrder queryBmOrderByParams(BmOrder bmOrder);
	
	public BmOrder bmOrderPersistence(BmOrder bmOrder)throws Exception;
	
	public void bmOrderModify(BmOrder order)throws Exception;

}
