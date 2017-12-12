package com.zhongyang.java.zyfyfront.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.zyfyfront.biz.FundRecordBiz;
import com.zhongyang.java.zyfyfront.biz.UserAccountBiz;
import com.zhongyang.java.zyfyfront.pojo.Invest;
import com.zhongyang.java.zyfyfront.pojo.InvestRepayment;
import com.zhongyang.java.zyfyfront.pojo.Loan;
import com.zhongyang.java.zyfyfront.pojo.LoanRepayment;
import com.zhongyang.java.zyfyfront.pojo.User;
import com.zhongyang.java.zyfyfront.returndata.UserAccountReturn;
import com.zhongyang.java.zyfyfront.vo.useraccount.FundRecordAccount;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInvestLoanVo;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInvestRecordVo;

@Controller
public class UserAccountController extends BaseController {

	@Autowired
	private UserAccountBiz userAccountBiz;
	
	@Autowired
	private FundRecordBiz fundRecordBiz;

	/**
	 * 账户信息
	 * 
	 * @date 下午7:23:24
	 * @param request
	 * @return
	 * @author suzh
	 */
	@RequestMapping(value = "/front/userAccount/userAccountInfo", method = RequestMethod.POST)
	public @ResponseBody UserAccountReturn getUserInvestInfo(HttpServletRequest request) {

		return userAccountBiz.getUserAccountInfo(request);
	}

	/**
	 * 资金记录列表
	 * 
	 * @date 下午7:23:41
	 * @param request
	 * @param page
	 * @return
	 * @author suzh
	 */
	@RequestMapping(value = "/front/userAccount/userFundRecordList", method = RequestMethod.POST)
	public @ResponseBody UserAccountReturn getUserFundRecordList(HttpServletRequest request,
			Page<FundRecordAccount> page) {
		return userAccountBiz.getUserFundRecord(request, page);
	}

	/**
	 * 回款计划
	 * 
	 * @date 下午7:26:10
	 * @param request
	 * @param page
	 * @param appRepayStatus
	 * @return
	 * @author suzh
	 */
	@RequestMapping(value = "/front/userAccount/userInvestRepaymentList", method = RequestMethod.POST)
	public @ResponseBody UserAccountReturn userInvestRepaymentList(HttpServletRequest request,
			Page<UserInvestRecordVo> page, String appRepayStatus) {
		if (appRepayStatus != null) {
			page.getParams().put("repayStatus", appRepayStatus);
		}
		return userAccountBiz.selectUserInvestInfo(request, page);
	}

	/**
	 * 最近投资
	 * 
	 * @date 上午9:20:46
	 * @param request
	 * @param page
	 * @param appLoanStatus
	 * @return
	 * @author suzh
	 */
	@RequestMapping(value = "/front/userAccount/userInvestLoanList",method=RequestMethod.POST)
	public @ResponseBody UserAccountReturn getUserInvestLoanList(HttpServletRequest request,
			Page<UserInvestLoanVo> page, String appLoanStatus) {
		if (appLoanStatus != null) {
			page.getParams().put("loanStatus", appLoanStatus);
		}
		return userAccountBiz.getUserInvestLoanRecord(request, page);
	}

	/**
	 * 合同下载
	 * 
	 * @date 上午9:26:19
	 * @param investId
	 * @return
	 * @author suzh
	 */
	@RequestMapping(value = "/front/userAccount/downLoadContract")
	public ResponseEntity<byte[]> downLoadContract(String investId) {
		return userAccountBiz.downLoadContract(investId);
	}

	/**
	 * 个人设置
	 * 
	 * @date 上午9:36:32
	 * @param request
	 * @return
	 * @author suzh
	 */
	@RequestMapping(value = "/front/userAccount/getUserSetting",method=RequestMethod.POST)
	public @ResponseBody UserAccountReturn getUserSetting(HttpServletRequest request) {
		return userAccountBiz.getUserSetting(request);
	}
	
	/**
	 * 借款管理
	 *@date 上午10:36:12
	 *@param request
	 *@param loanVo
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value = "/front/userAccount/loanManagement",method=RequestMethod.POST)
	public @ResponseBody UserAccountReturn loanManagement(HttpServletRequest request, Page<Loan>page) {
		return userAccountBiz.loanManagement(request,page);
	}
	
	/**
	 *还款计划
	 *@date 下午1:32:17
	 *@param request
	 *@param page
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value = "/front/userAccount/loanRepaymentPlant",method=RequestMethod.POST)
	public @ResponseBody UserAccountReturn loanRepaymentPlant(HttpServletRequest request,Page<LoanRepayment>page){
		return userAccountBiz.loanRepaymentPlant(request,page);
	}
	
	/**
	 * 标的代偿列表
	 *TODO
	 *@date 下午1:32:57
	 *@param request
	 *@param page
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value = "/front/userAccount/loanRepaymentCompentsate",method=RequestMethod.POST)
	public @ResponseBody UserAccountReturn loanRepaymentCompentsate(HttpServletRequest request,Page<LoanRepayment>page){
		return userAccountBiz.loanRepaymentCompentsate(request,page);
	}
	
	/**
	 * 回款计划
	 *@date 下午2:49:15
	 *@param request
	 *@param page
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value = "/front/userAccount/investRepaymentPlant",method=RequestMethod.POST)
	public @ResponseBody UserAccountReturn investRepaymentPlant(HttpServletRequest request,Page<InvestRepayment>page){
		return userAccountBiz.investRepaymentPlant(request,page);
	}

	/**
	 * 邀请投资列表
	 *@date 下午3:04:00
	 *@param request
	 *@param page
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value = "/front/userAccount/queryInviterInvest",method=RequestMethod.POST)
	public @ResponseBody UserAccountReturn queryInviterInvest(HttpServletRequest request,Page<Invest> page){
		return userAccountBiz.queryInviterInvest(request,page);
	}
	/**
	 * 邀请列表
	 *@date 下午3:31:32
	 *@param request
	 *@param page
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value="/front/userAccount/queryInviterList",method=RequestMethod.POST)
	public @ResponseBody UserAccountReturn queryInviterList(HttpServletRequest request,Page<User>page){
		return userAccountBiz.queryInviterList(request,page);
	}
	/**
	 * 资金日历
	 *@date 下午3:31:48
	 *@param request
	 *@param newData
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value="/front/userAccount/userFundRecordCalendar",method=RequestMethod.POST)
	public @ResponseBody UserAccountReturn userFundRecordCalendar(HttpServletRequest request,String newData){
		User user = (User)WebUtils.getSessionAttribute(request, "zycfLoginUser");
		UserAccountReturn uar=new UserAccountReturn();
		try {
			if (user== null)
				throw new UException(SystemEnum.USER_NOLOGIN,"您没有登录");
				
			uar.setFundRecordCalenderVo(fundRecordBiz.userFundRecordCalendar(newData,user));
			uar.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"资金日历查询成功"));
		} catch (UException e) {
			uar.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return uar;
	}
	/**
	 * 提现账户查询
	 *@date 上午9:42:11
	 *@param request
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value="/front/userAccount/queryFundAccount",method=RequestMethod.POST)
	public @ResponseBody UserAccountReturn queryFundAccount(HttpServletRequest request){
		return userAccountBiz.queryFundAccount(request);
	}
}
