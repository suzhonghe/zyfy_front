package com.zhongyang.java.zyfyfront.biz;

import javax.servlet.http.HttpServletRequest;

import com.zhongyang.java.zyfyfront.returndata.UserFundReturn;

/**
 *@package com.zhongyang.java.zyfyfront.biz
 *@filename UserFundBiz.java
 *@date 2017年8月3日上午9:33:05
 *@author suzh
 */
public interface UserFundBiz {
	
	public UserFundReturn queryUserFund(HttpServletRequest request);
}
