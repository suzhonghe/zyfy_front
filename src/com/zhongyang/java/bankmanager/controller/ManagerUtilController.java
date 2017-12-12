package com.zhongyang.java.bankmanager.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.bankmanager.biz.ManagerUtilBiz;
import com.zhongyang.java.bankmanager.returndata.ManagerUtilReturn;
import com.zhongyang.java.zyfyfront.controller.BaseController;

/**
 *@package com.zhongyang.java.bankmanager.controller
 *@filename ManagerUtilController.java
 *@date 2017年7月6日上午9:52:03
 *@author suzh
 */
@Controller
public class ManagerUtilController extends BaseController{
	
	@Autowired
	private ManagerUtilBiz managerUtilBiz;
	
	/**
	 * 申请存管系统短信验证码
	 *@date 上午10:09:57
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value="/front/util/sendMSgCodeApply")
	public @ResponseBody ManagerUtilReturn sendMSgCodeApply(HttpServletRequest request){
		return managerUtilBiz.sendMSgCodeApply(request); 
	}
}
