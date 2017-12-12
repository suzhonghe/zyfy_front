package com.zhongyang.java.zyfyfront.service;

import com.zhongyang.java.zyfyfront.pojo.UserFund;
/**
 * 
* @Title: UserFundService.java 
* @Package com.zhongyang.java.service 
* @Description: UserFundService 
* @author 苏忠贺   
* @date 2017年6月2日 下午1:44:11 
* @version V1.0
 */
public interface UserFundService {
    
	/*
	 * 修改用户资金信息
	 */
	public void modifyUserFundByParams(UserFund userFund)throws Exception;
	/*
	 * 添加用户资金信息
	 */
	public int addUserFund(UserFund userFund)throws Exception;
	
	/*
	 * 查询用户资金信息
	 */
	public UserFund queryUserFundByParams(UserFund userFund);
	
} 
