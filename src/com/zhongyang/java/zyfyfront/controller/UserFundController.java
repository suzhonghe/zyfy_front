package com.zhongyang.java.zyfyfront.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.zyfyfront.biz.UserFundBiz;
import com.zhongyang.java.zyfyfront.returndata.UserFundReturn;

/**
 *@package com.zhongyang.java.zyfyfront.controller
 *@filename UserFundController.java
 *@date 2017年8月3日上午9:31:07
 *@author suzh
 */
@Controller
public class UserFundController extends BaseController{
	
	@Autowired
	private UserFundBiz userFundBiz;
	
	@RequestMapping(value="/front/userFund/queryUserFund",method=RequestMethod.POST)
	public @ResponseBody UserFundReturn queryUserFund(HttpServletRequest request){
		return userFundBiz.queryUserFund(request);
	}
}
