package com.zhongyang.java.zyfyfront.biz;


import javax.servlet.http.HttpServletRequest;

import com.zhongyang.java.zyfyfront.params.RegisteredParams;
import com.zhongyang.java.zyfyfront.params.UserParams;
import com.zhongyang.java.zyfyfront.returndata.UserReturn;

public interface UserBiz {
	
	public UserReturn addUser(HttpServletRequest req,RegisteredParams params);
	
	public UserReturn companyRegist(HttpServletRequest req, RegisteredParams params);
	
	public UserReturn searchUser(UserParams params);
	
	public UserReturn userLogin(HttpServletRequest req,UserParams params);
	
	public UserReturn mobileUnique(HttpServletRequest request,UserParams params);
	
	public UserReturn modifyLoginName(HttpServletRequest request,String loginName);
	
	public UserReturn modifyPassWord(HttpServletRequest request,UserParams params);
	
	public UserReturn resetPassWord(HttpServletRequest request,UserParams params);
}	
