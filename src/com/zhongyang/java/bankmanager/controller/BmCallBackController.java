package com.zhongyang.java.bankmanager.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.bankmanager.biz.BmCallBackBiz;
import com.zhongyang.java.bankmanager.entity.ClearNotify;
import com.zhongyang.java.bankmanager.entity.NotifyRefund;
import com.zhongyang.java.zyfyfront.controller.BaseController;

/**
 *@package com.zhongyang.java.bankmanager.controller
 *@filename BmCallBackController.java
 *@date 2017年10月17日下午1:53:29
 *@author suzh
 */
@Controller
public class BmCallBackController extends BaseController{
	
	@Autowired
	private BmCallBackBiz bmCallBackBiz;
	
	@RequestMapping(value="/notify_refund")
	public @ResponseBody Object callBackRefund(HttpServletRequest request){
		NotifyRefund notifyRefund=new NotifyRefund();
		notifyRefund.setPlat_no(request.getParameter("plat_no"));
		notifyRefund.setOrder_no(request.getParameter("order_no"));
		notifyRefund.setPlatcust(request.getParameter("platcust"));
		notifyRefund.setAmt(request.getParameter("amt")==null?null:new BigDecimal(request.getParameter("amt")).setScale(2, BigDecimal.ROUND_HALF_EVEN));
		
		return bmCallBackBiz.callBackRefund(notifyRefund);
	}
	
	@RequestMapping(value="/notify_liquidation")
	public @ResponseBody Object callBackClear(HttpServletRequest request){
		ClearNotify clearNotify=new ClearNotify();
		clearNotify.setPlat_no(request.getParameter("plat_no"));
		clearNotify.setLiquidation_flag(request.getParameter("liquidation_flag"));
		clearNotify.setClearDate(request.getParameter("clear_date"));
		return bmCallBackBiz.callBackClear(clearNotify);
	}
}
