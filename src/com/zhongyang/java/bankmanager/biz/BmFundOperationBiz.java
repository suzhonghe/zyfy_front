package com.zhongyang.java.bankmanager.biz;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import com.zhongyang.java.bankmanager.params.ChargeCallBack;
import com.zhongyang.java.bankmanager.params.WithdrawCallBack;
import com.zhongyang.java.bankmanager.returndata.BmReturnData;
import com.zhongyang.java.bankmanager.returndata.ResponseData;

/**
 *@package com.zhongyang.java.bankmanager.biz
 *@filename BmFundOperationBiz.java
 *@date 2017年7月4日上午11:25:09
 *@author suzh
 */
public interface BmFundOperationBiz {
	/**
	 * 账户网关充值
	 *TODO
	 *@date 上午11:28:41
	 *@return
	 *@author suzh
	 */
	public BmReturnData accountGatewayRecharge(HttpServletRequest request,String bankcode,BigDecimal amt);
	/**
	 * 账户快捷充值
	 *@date 上午9:48:23
	 *@param request
	 *@param bankcode
	 *@param amt
	 *@return
	 *@author suzh
	 */
	public BmReturnData quickAccountRecharge(HttpServletRequest request,String preMobile,String smsCode,BigDecimal amt);
	
	/**
	 * 充值异步回调
	 *@date 上午11:29:27
	 *@return
	 *@author suzh
	 */
	public Object callAccountRecharge(ChargeCallBack charge);
	
	public BmReturnData personWithdraw(HttpServletRequest request,BigDecimal amount,String sms_code);
	
	public Object callWithdraw(WithdrawCallBack withdraw);
}
