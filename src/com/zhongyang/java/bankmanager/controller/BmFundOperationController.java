package com.zhongyang.java.bankmanager.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.bankmanager.biz.BmFundOperationBiz;
import com.zhongyang.java.bankmanager.params.ChargeCallBack;
import com.zhongyang.java.bankmanager.params.WithdrawCallBack;
import com.zhongyang.java.bankmanager.returndata.BmReturnData;
import com.zhongyang.java.zyfyfront.controller.BaseController;

/**
 * 资金操作控制器
 *@package com.zhongyang.java.bankmanager.controller
 *@filename BmFundOperationController.java
 *@date 2017年7月4日上午11:19:46
 *@author suzh
 */

@Controller
public class BmFundOperationController extends BaseController{
	
	@Autowired
	private BmFundOperationBiz bmFundOperationBiz;
	
	/**
	 * 网关充值
	 *@date 下午12:15:55
	 *@param bankcode 银行编码
	 *@param amt 充值金额
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value="/front/fund/accountGatewayRecharge",method=RequestMethod.POST)
	public @ResponseBody BmReturnData accountGatewayRecharge(HttpServletRequest request,String bankcode,BigDecimal amt){
		return bmFundOperationBiz.accountGatewayRecharge(request,bankcode,amt);
	}
	
	/**
	 * 快捷充值
	 *@date 上午11:47:07
	 *@param request
	 *@param preMobile
	 *@param smsCode
	 *@param amt
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value="/front/fund/quickAccountRecharge",method=RequestMethod.POST)
	public @ResponseBody BmReturnData quickAccountRecharge(HttpServletRequest request,String preMobile,String smsCode,BigDecimal amt){
		return bmFundOperationBiz.quickAccountRecharge(request,preMobile,smsCode,amt);
	}
	
	/**
	 *充值回调，异步通知
	 *@date 下午1:04:29
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value="/front/fund/callAccountRecharge")
	public synchronized @ResponseBody Object callAccountRecharge(HttpServletRequest request,HttpServletResponse response){
		
		ChargeCallBack charge=new ChargeCallBack();
		charge.setPlat_no(request.getParameter("plat_no"));
		charge.setOrder_no(request.getParameter("order_no"));
		charge.setPlatcust(request.getParameter("platcust"));
		charge.setType(request.getParameter("type"));
		charge.setOrder_amt(request.getParameter("order_amt")==null?null:new BigDecimal(request.getParameter("order_amt")).setScale(2, BigDecimal.ROUND_HALF_EVEN));
		charge.setTrans_date(request.getParameter("trans_date"));
		charge.setTrans_time(request.getParameter("trans_time"));
		charge.setPay_order_no(request.getParameter("pay_order_no"));
		charge.setPay_finish_date(request.getParameter("pay_finish_date"));
		charge.setPay_finish_time(request.getParameter("pay_finish_time"));
		charge.setOrder_status(request.getParameter("order_status"));
		charge.setError_info(request.getParameter("error_info"));
		charge.setError_no(request.getParameter("error_no"));
		charge.setHost_req_serial_no(request.getParameter("host_req_serial_no"));
		charge.setPay_amt(request.getParameter("pay_amt")==null?null:new BigDecimal(request.getParameter("pay_amt")).setScale(2, BigDecimal.ROUND_HALF_EVEN));

		return bmFundOperationBiz.callAccountRecharge(charge);
	}
	/**
	 * 个人提现
	 *@date 下午3:52:05
	 *@param request
	 *@param amount
	 *@param sms_code
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value="/front/fund/personWithdraw",method=RequestMethod.POST)
	public  @ResponseBody BmReturnData personWithdraw(HttpServletRequest request,BigDecimal amount,String sms_code){	
		return bmFundOperationBiz.personWithdraw(request,amount,sms_code);
	}
	
	/**
	 * 提现通知
	 *@date 下午3:52:31
	 *@param withdraw
	 *@return
	 *@author suzh
	 */
	@RequestMapping(value="/front/fund/callWithdraw")
	public  @ResponseBody Object callWithdraw(HttpServletRequest request){	
		WithdrawCallBack withdraw=new WithdrawCallBack();
		
		withdraw.setPlat_no(request.getParameter("plat_no"));
		withdraw.setPlatcust(request.getParameter("platcust"));
		withdraw.setOrder_no(request.getParameter("order_no"));
		withdraw.setOrder_amt(request.getParameter("order_amt")==null?null:new BigDecimal(request.getParameter("order_amt")));
		withdraw.setTrans_date(request.getParameter("trans_date"));
		withdraw.setTrans_time(request.getParameter("trans_time"));
		withdraw.setPay_order_no(request.getParameter("pay_order_no"));
		withdraw.setPay_finish_date(request.getParameter("pay_finish_date"));
		withdraw.setPay_finish_time(request.getParameter("pay_finish_time"));
		withdraw.setOrder_status(request.getParameter("order_status"));
		withdraw.setPay_amt(request.getParameter("pay_amt")==null?null:new BigDecimal(request.getParameter("pay_amt")));
		withdraw.setError_info(request.getParameter("error_info"));
		withdraw.setError_no(request.getParameter("error_no"));
		withdraw.setHost_req_serial_no(request.getParameter("host_req_serial_no"));
		
		return bmFundOperationBiz.callWithdraw(withdraw);
	}
}
