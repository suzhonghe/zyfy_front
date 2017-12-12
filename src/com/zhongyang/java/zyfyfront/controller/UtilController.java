package com.zhongyang.java.zyfyfront.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.zyfyfront.biz.UtilBiz;
import com.zhongyang.java.zyfyfront.returndata.BankReturn;

@Controller
public class UtilController extends BaseController {

	@Autowired
	private UtilBiz utilBiz;

	/**
	 * 
	 * @Title: getVerificationImg 
	 * @Description:获取图片验证码
	 * @return void 返回类型 
	 */
	@RequestMapping(value = "/front/util/imgValalidate", method = RequestMethod.GET)
	public void getVerificationImg(HttpServletRequest req, HttpServletResponse resp) {
		utilBiz.getVerificationImg(req, resp);
	}

	/**
	 * 
	 * @Title: getVerificationCod
	 * @Description:图片验证码验证
	 * @return Message
	 */
	@RequestMapping(value = "/front/util/codeValalidate", method = RequestMethod.POST)
	public @ResponseBody Message getVerificationCode(HttpServletRequest req, String imgCode) {
		return utilBiz.getVerificationCode(req, imgCode);
	}

	/**
	 * 
	 * @Title: mobolileCertWeb 
	 * @Description:手机短信验证 
	 * @return Message 
	 */
	@RequestMapping(value = "/front/util/mobolileCertWeb", method = RequestMethod.POST)
	public @ResponseBody Message mobolileCertWeb(HttpServletRequest request, HttpServletResponse response) {
		return utilBiz.mobolileCertWeb(request, response);
	}

	/**
	 * 
	 * @Title: resetPasswd 
	 * @Description:重置密码发送短信 
	 * @return Message 
	 * 返回类型 @throws
	 */
	@RequestMapping(value = "/front/util/resetPasswdMsg", method = RequestMethod.POST)
	public @ResponseBody Message resetPasswd(HttpServletRequest request, String mobile) {
		return utilBiz.resetPasswd(request, mobile);
	}

	/**
	 * 
	 * @Title: resetPasswdValidate 
	 * @Description:验证短信验证码
	 *  @return Message
	 * 返回类型 @throws
	 */
	@RequestMapping(value = "/front/util/resetPasswdValidate", method = RequestMethod.POST)
	public @ResponseBody Message resetPasswdValidate(HttpServletRequest request, String resetCode) {
		return utilBiz.resetPasswdValidate(request, resetCode);
	}
	
	/**
	 * 
	* @Title: getAppVersion 
	* @Description: 安卓APP版本号的获得
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/front/util/getAppVersion")
	public  @ResponseBody Map<String,Object> getAppVersion(HttpServletRequest req) {
		return utilBiz.getAppVersion();
	}
	
	@RequestMapping(value="/front/util/searchAllBanks",method=RequestMethod.POST)
	public @ResponseBody BankReturn searchAllBanks(){
		return utilBiz.searchAllBanks();
	}
}
