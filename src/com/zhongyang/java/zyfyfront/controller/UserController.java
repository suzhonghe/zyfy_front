package com.zhongyang.java.zyfyfront.controller;

import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.util.DESTextCipher;
import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.zyfyfront.biz.UserBiz;
import com.zhongyang.java.zyfyfront.params.RegisteredParams;
import com.zhongyang.java.zyfyfront.params.UserParams;
import com.zhongyang.java.zyfyfront.pojo.User;
import com.zhongyang.java.zyfyfront.returndata.UserReturn;


@Controller
public class UserController extends BaseController {

	@Autowired
	private UserBiz userBiz;
	
	/*
	 * 个人用户注册
	 */
	@RequestMapping(value = "/front/user/registeUser", method = RequestMethod.POST)
	public @ResponseBody UserReturn registUser(HttpServletRequest req, RegisteredParams params) {
		return userBiz.addUser(req, params);
	}
	
	/*
	 * 企业用户注册
	 */
	@RequestMapping(value = "/front/user/companyRegist", method = RequestMethod.POST)
	public @ResponseBody UserReturn companyRegist(HttpServletRequest req, RegisteredParams params) {
		return userBiz.companyRegist(req, params);
	}
	
	/*
	 * 用户登录
	 */
	@RequestMapping(value = "/front/user/login", method = RequestMethod.POST)
	public @ResponseBody UserReturn userLogin(HttpServletRequest req, UserParams params) {
		return userBiz.userLogin(req, params);
	}
	
	/**
	 * 获取用户登录状态
	 *@date 上午9:45:43
	 *@param req
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value = "/front/user/loginStatus", method = RequestMethod.POST)
	public @ResponseBody UserReturn loginStatus(HttpServletRequest req) {
		UserReturn ur=new UserReturn();
		User user=(User)WebUtils.getSessionAttribute(req, "zycfLoginUser");
		try{
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			if(user!=null){
				try {
					String mobile = cipher.decrypt(user.getMobile());
					mobile=mobile.substring(0, 3)+"****"+mobile.substring(mobile.length()-4,mobile.length());
					ur.setUserName(user.getUserName()==null?mobile:user.getUserName());
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
				}
				
				ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"已登录"));
			}
			else
				ur.setMessage(new Message(SystemEnum.USER_NOLOGIN.value(),"您未登录"));
		}catch(UException e){
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}
	@RequestMapping(value = "/front/user/logOut", method = RequestMethod.POST)
	public @ResponseBody UserReturn logOut(HttpServletRequest req) {
		UserReturn ur=new UserReturn();
		req.getSession().removeAttribute("zycfLoginUser");
		ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"退出成功"));
		
		return ur;
	}
	
	/**
	 * 用户查询
	 *TODO
	 *@date 上午11:29:09
	 *@param params
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value = "/front/user/searchUser", method = RequestMethod.POST)
	public @ResponseBody UserReturn searchUser(UserParams params) {
		return userBiz.searchUser(params);
	}
	
	/**
	 *手机号唯一
	 *TODO
	 *@date 上午11:29:09
	 *@param params
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value = "/front/user/mobileUnique", method = RequestMethod.POST)
	public @ResponseBody UserReturn mobileUnique(HttpServletRequest request,UserParams params) {
		return userBiz.mobileUnique(request,params);
	}
	/**
	 * 修改用户名
	 *@date 下午4:21:01
	 *@param request
	 *@param loginName
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value = "/front/user/modifyLoginName", method = RequestMethod.POST)
	public @ResponseBody UserReturn modifyLoginName(HttpServletRequest request,String loginName) {
		return userBiz.modifyLoginName(request,loginName);
	}
	/**
	 * 修改密码
	 *@date 下午4:21:17
	 *@param request
	 *@param params
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value = "/front/user/modifyPassWord", method = RequestMethod.POST)
	public @ResponseBody UserReturn modifyPassWord(HttpServletRequest request,UserParams params) {
		return userBiz.modifyPassWord(request,params);
	}
	
	/**
	 * 忘记密码后重置密码
	 *@date 下午4:21:17
	 *@param request
	 *@param params
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value = "/front/user/resetPassWord", method = RequestMethod.POST)
	public @ResponseBody UserReturn resetPassWord(HttpServletRequest request,UserParams params) {
		return userBiz.resetPassWord(request,params);
	}
}
