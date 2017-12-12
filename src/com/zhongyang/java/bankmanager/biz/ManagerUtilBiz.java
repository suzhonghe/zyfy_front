package com.zhongyang.java.bankmanager.biz;

import javax.servlet.http.HttpServletRequest;

import com.zhongyang.java.bankmanager.returndata.ManagerUtilReturn;
/**
 * 
 *@package com.zhongyang.java.bankmanager.biz
 *@filename ManagerUtilBiz.java
 *@date 2017年7月6日上午10:08:13
 *@author suzh
 */
public interface ManagerUtilBiz {
	
	public ManagerUtilReturn sendMSgCodeApply(HttpServletRequest request);
}
