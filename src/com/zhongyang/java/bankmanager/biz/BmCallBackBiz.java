package com.zhongyang.java.bankmanager.biz;

import com.zhongyang.java.bankmanager.entity.ClearNotify;
import com.zhongyang.java.bankmanager.entity.NotifyRefund;

/**
 *@package com.zhongyang.java.bankmanager.biz
 *@filename BmCallBack.java
 *@date 2017年10月17日下午1:54:12
 *@author suzh
 */
public interface BmCallBackBiz {
	
	/**
	 * 
	 *TODO退票回调
	 *@date 下午1:55:59
	 *@return
	 *@author suzh
	 */
	public Object callBackRefund(NotifyRefund notifyRefund);
	
	/**
	 * 
	 *TODO清算回调
	 *@date 下午1:55:48
	 *@return
	 *@author suzh
	 */
	public Object callBackClear(ClearNotify clearNotify);

}
