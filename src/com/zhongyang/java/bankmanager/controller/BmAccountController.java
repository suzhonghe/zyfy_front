package com.zhongyang.java.bankmanager.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.bankmanager.biz.BmAccountBiz;
import com.zhongyang.java.bankmanager.params.BmAccountParams;
import com.zhongyang.java.bankmanager.returndata.BmReturnData;
import com.zhongyang.java.zyfyfront.controller.BaseController;

/**
 * 账户操作控制器
 *@package com.zhongyang.java.bankmanager.controller
 *@filename BankAccountController.java
 *@date 20172017年6月26日下午5:08:32
 *@author suzh
 */
@Controller
public class BmAccountController extends BaseController{
	
	@Autowired
	private BmAccountBiz bankAccountBiz;
	/**
	 * 短验开户申请
	 *@date 上午9:05:40
	 *@param request
	 *@param params
	 *@return
	 *@author suzh
	 */
	
	@RequestMapping(value="/front/bmAccount/registeApply")
	@ResponseBody
	public BmReturnData bankManagerRegisteApply(HttpServletRequest request,BmAccountParams params){
		return bankAccountBiz.bankManagerRegisteApply(request,params);
	}
	/**
	 * 短验开户确认
	 *@date 上午9:05:57
	 *@param request
	 *@param params
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value="/front/bmAccount/registeAffirm",method=RequestMethod.POST)
	@ResponseBody
	public BmReturnData bankManagerRegisteAffirm(HttpServletRequest request,BmAccountParams params){
		return bankAccountBiz.bankManagerRegisteAffirm(request,params);
	}
	/**
	 * 解绑银行卡
	 *@date 上午9:11:02
	 *@param request
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value="/front/bmAccount/relieveCard",method=RequestMethod.POST)
	public @ResponseBody BmReturnData relieveCard(HttpServletRequest request){
		return bankAccountBiz.relieveCard(request);
	}
	
	/**
	 * 企业开户
	 *@date 下午4:29:14
	 *@param request
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value="/front/bmAccount/companyOpenAccont",method=RequestMethod.POST)
	public @ResponseBody BmReturnData companyOpenAccont(HttpServletRequest request,BmAccountParams params){
		return bankAccountBiz.companyOpenAccont(request,params);
	}
	
	/**
	 * 企业用户绑卡
	 *@date 上午9:29:46
	 *@param request
	 *@param params
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value="/front/bmAccount/companybindCard",method=RequestMethod.POST)
	public @ResponseBody BmReturnData companybindCard(HttpServletRequest request,BmAccountParams params){
		return bankAccountBiz.companybindCard(request,params);
	}
	
	/**
	 * 企业绑卡回调
	 *@date 上午10:10:18
	 *@param request
	 *@return
	 *@author suzh
	 */                    
	@RequestMapping(value="/front/bmAccount/companyBindCardCall")
	public @ResponseBody Object companyBindCardCall(HttpServletRequest request){
		return bankAccountBiz.companyBindCardCall(request);
	}
	
	@RequestMapping(value="/front/bmAccount/openIIAccountApp",method=RequestMethod.POST)
	public @ResponseBody BmReturnData openIIAccountApp(HttpServletRequest request){
		return bankAccountBiz.openIIAccountApp(request);
	}
	
	@RequestMapping(value="/front/bmAccount/openIIAccountPc")
	public @ResponseBody BmReturnData openIIAccountPc(HttpServletRequest request){
		return bankAccountBiz.openIIAccountPc(request);
	}
	
	/**
	 * II类账户开通结果
	 *@date 下午3:15:31
	 *@param request
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value="/front/bmAccount/openIIAccountResult",method=RequestMethod.POST)
	public @ResponseBody BmReturnData openIIAccountResult(HttpServletRequest request,BmAccountParams params){
		return bankAccountBiz.openIIAccountResult(request,params);
	}
}
