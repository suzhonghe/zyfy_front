package com.zhongyang.java.bankmanager.biz.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.zhongyang.java.bankmanager.biz.BmFundOperationBiz;
import com.zhongyang.java.bankmanager.entity.BmAccount;
import com.zhongyang.java.bankmanager.entity.BmOrder;
import com.zhongyang.java.bankmanager.params.ChargeCallBack;
import com.zhongyang.java.bankmanager.params.WithdrawCallBack;
import com.zhongyang.java.bankmanager.returndata.BmReturnData;
import com.zhongyang.java.bankmanager.service.BmAccountService;
import com.zhongyang.java.bankmanager.service.BmOrderService;
import com.zhongyang.java.bankmanager.util.RequestUtil;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.FundRecordOperation;
import com.zhongyang.java.system.enumtype.FundRecordStatus;
import com.zhongyang.java.system.enumtype.FundRecordType;
import com.zhongyang.java.system.enumtype.OrderStatus;
import com.zhongyang.java.system.enumtype.OrderType;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.util.DESTextCipher;
import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.system.util.SystemPro;
import com.zhongyang.java.system.util.UtilBiz;
import com.zhongyang.java.zyfyfront.pojo.Bank;
import com.zhongyang.java.zyfyfront.pojo.FundRecord;
import com.zhongyang.java.zyfyfront.pojo.User;
import com.zhongyang.java.zyfyfront.pojo.UserFund;
import com.zhongyang.java.zyfyfront.service.BankService;
import com.zhongyang.java.zyfyfront.service.FundRecordService;
import com.zhongyang.java.zyfyfront.service.UserFundService;

/**
 *@package com.zhongyang.java.bankmanager.biz.impl
 *@filename BmFundOperationBizImpl.java
 *@date 2017年7月4日上午11:32:42
 *@author suzh
 */
@Service
public class BmFundOperationBizImpl extends UtilBiz implements BmFundOperationBiz {
	
	static {
		Map<String, Object> sysMap = SystemPro.getProperties();
		ZYCF_CHARGE_PAY_CODE = (String) sysMap.get("ZYCF_CHARGE_PAY_CODE");
		ZYCF_GATEWAY_RECHARGE = (String) sysMap.get("ZYCF_GATEWAY_RECHARGE");
		ZYCF_QUICK_CHARGE = (String) sysMap.get("ZYCF_QUICK_CHARGE");
		ZYCF_GATEWAY_RECHARGE_RETURN_URL=(String) sysMap.get("ZYCF_GATEWAY_RECHARGE_RETURN_URL");
		ZYCF_RECHARGE_NOTIFY_URL=(String) sysMap.get("ZYCF_RECHARGE_NOTIFY_URL");
		ZYCF_WITHDRAW_URL=(String) sysMap.get("ZYCF_WITHDRAW_URL");
		ZYCF_WITHDRAW_NOTIFY_URL=(String) sysMap.get("ZYCF_WITHDRAW_NOTIFY_URL");
		ZYCF_WITHDRAW_PAY_CODE=(String) sysMap.get("ZYCF_WITHDRAW_PAY_CODE");
	}
	
	private static Logger logger=Logger.getLogger(BmFundOperationBizImpl.class);
	
	private static String ZYCF_CHARGE_PAY_CODE;
	
	private static String ZYCF_GATEWAY_RECHARGE;
	
	private static String ZYCF_QUICK_CHARGE;
	
	private static String ZYCF_GATEWAY_RECHARGE_RETURN_URL;
	
	private static String ZYCF_RECHARGE_NOTIFY_URL;
	
	private static String ZYCF_WITHDRAW_URL;
	
	private static String ZYCF_WITHDRAW_NOTIFY_URL;
	
	private static String ZYCF_WITHDRAW_PAY_CODE;

	@Autowired
	private BmOrderService bmOrderService;
	
	@Autowired
	private BmAccountService bmAccountService;
	
	@Autowired
	private UserFundService userFundService;
	
	@Autowired
	private FundRecordService fundRecordService;
	
	@Autowired
	private BankService bankService;
	
	@Override
	public BmReturnData accountGatewayRecharge(HttpServletRequest request,String bankcode,BigDecimal amt) {
		BmReturnData brd=new BmReturnData();
		try{
			logger.info("====================充值申请参数校验====================");
			User user=(User)WebUtils.getSessionAttribute(request, "zycfLoginUser");
			if(user==null)
				throw new UException(SystemEnum.USER_NOLOGIN,"您没有登录");
			
			BmAccount account =new BmAccount();
			account.setUserId(user.getId());
			BmAccount userAccount = bmAccountService.queryBmAccountByParams(account);
			if(userAccount.getPlatcust()==null)
				throw new UException(SystemEnum.UN_AUTHENTICATION,"您没有开通银行存管账户，不能充值");
				
			if(userAccount.getCardNo()==null&&user.getUserType()==1)
				throw new UException(SystemEnum.UN_AUTHENTICATION,"您没有绑定银行卡 ，不能充值");
			
			if(bankcode==null||"".equals(bankcode))
				throw new UException(SystemEnum.PARAMS_ERROR,"未选择银行");
			
			if(amt==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"充值金额不能为空");
			
			if(amt.subtract(new BigDecimal(100)).doubleValue()<0)
				throw new UException(SystemEnum.PARAMS_ERROR,"充值金额必须大于100");
			
			logger.info("====================充值参数校验通过====================");
			
			logger.info("====================数据库添加充值订单====================");
			amt=amt.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			
			BmOrder order=new BmOrder();
			order.setOrderType(OrderType.DEPOSIT);
			order.setUserId(user.getId());
			order.setRemark("个人用户网关充值");
			try {
				order = bmOrderService.bmOrderPersistence(order);
			} catch (Exception e1) {
				logger.info("订单记录添加异常"+e1.fillInStackTrace());
				e1.printStackTrace();
				throw new UException(SystemEnum.DATA_REFUSED,"数据入库异常");
			}
			
			
			logger.info("充值订单信息："+order.toString());
			logger.info("====================数据库添加充值订单完成====================");
			
			UserFund userFund=new UserFund();
			userFund.setUserId(user.getId());
			UserFund fund = userFundService.queryUserFundByParams(userFund);
			
			//资金记录
			logger.info("====================添加资金记录====================");
			FundRecord fundRecord=new FundRecord();
			fundRecord.setAmount(amt);
			fundRecord.setAviAmount(fund.getAvailableAmount().add(amt));
			fundRecord.setDescription("网关充值金额："+amt+"元");
			fundRecord.setOperation(FundRecordOperation.IN);
			fundRecord.setStatus(FundRecordStatus.PROCESSING);
			fundRecord.setType(FundRecordType.DEPOSIT);
			fundRecord.setUserId(user.getId());
			fundRecord.setOrderId(order.getOrderId());
			try {
				fundRecord = fundRecordService.addFundRecord(fundRecord);
			} catch (Exception e1) {
				logger.info("资金记录添加异常"+e1.fillInStackTrace());
				e1.printStackTrace();
				throw new UException(SystemEnum.DATA_REFUSED,"数据入库异常");
			}
			logger.info("====================完成资金记录添加====================");
			
			logger.info("====================构造存管系统充值订单参数====================");
			Map<String,Object>map=new HashMap<String,Object>();
			map.put("order_no", order.getOrderId());
			map.put("partner_trans_date", order.getOrderDate().replace("-", ""));
			map.put("partner_trans_time", order.getOrderTime().replace(":", ""));
			map.put("platcust", userAccount.getPlatcust());
			map.put("type", 1);//充值类型（1-用户充值）
			map.put("charge_type", user.getUserType());//
			map.put("bankcode", bankcode);//银行编码(在banks.sql里提供)
			map.put("card_no", "");//卡号，无卡号填空:""
			map.put("client_property", user.getCustType()==1?0:1);//0-个人，1-企业（默认0-个人）
			
			map.put("amt", amt);//金额
			map.put("return_url", ZYCF_GATEWAY_RECHARGE_RETURN_URL);
			map.put("notify_url", ZYCF_RECHARGE_NOTIFY_URL);
			map.put("pay_code", ZYCF_CHARGE_PAY_CODE);
						
			String sendRequest=null;
			try {
				sendRequest = RequestUtil.sendReqResponse(map, ZYCF_GATEWAY_RECHARGE);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络连接异常");
			}
			logger.info(sendRequest);
			if(sendRequest.contains("recode")){
				Map<String, Object> res=JSON.parseObject(sendRequest);
				order.setRecord(res.get("recode").toString());
				order.setRemsg(res.get("remsg").toString());
				brd.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),res.get("recode").toString()+res.get("remsg").toString()));
			}else{
				order.setRecord("10000");
				order.setRemsg("请求成功");
				
				
				brd.setRep(sendRequest);	
				brd.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"充值申请成功"));
			}
		}catch(UException e){
			System.out.println(e);
			logger.info("充值异常");
			logger.info(e.fillInStackTrace());
			brd.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		
		return brd;
	}

	@Override
	public BmReturnData quickAccountRecharge(HttpServletRequest request, String preMobile,String smsCode, BigDecimal amt) {
		BmReturnData brd=new BmReturnData();
		try{
			logger.info("====================充值申请参数校验====================");
			User user=(User)WebUtils.getSessionAttribute(request, "zycfLoginUser");
			if(user==null)
				throw new UException(SystemEnum.USER_NOLOGIN,"您没有登录");
			if(user.getCustType()==2)
				throw new UException(SystemEnum.PARAMS_ERROR,"不支持企业用户快捷充值");
				
			amt=amt.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			
			BmAccount account =new BmAccount();
			account.setUserId(user.getId());
			BmAccount userAccount = bmAccountService.queryBmAccountByParams(account);
			if(userAccount.getPlatcust()==null)
				throw new UException(SystemEnum.UN_AUTHENTICATION,"您没有开通银行存管账户，不能充值");
				
			if(userAccount.getPreMobile()==null||"".equals(userAccount.getPreMobile()))
				throw new UException(SystemEnum.PARAMS_ERROR,"银行预留手机号必须填写");
			
			if(amt==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"充值金额不能为空");
			
			if(amt.subtract(new BigDecimal(100)).doubleValue()<0)
				throw new UException(SystemEnum.PARAMS_ERROR,"充值金额必须大于100");
					
			logger.info("====================充值参数校验通过====================");
			
			logger.info("====================数据库添加充值订单====================");
			BmOrder order=new BmOrder();
			order.setOrderType(OrderType.DEPOSIT);
			order.setUserId(user.getId());
			order.setRemark("个人用户快捷充值");
			try {
				order = bmOrderService.bmOrderPersistence(order);
			} catch (Exception e1) {
				logger.info("订单记录添加异常"+e1.fillInStackTrace());
				e1.printStackTrace();
				throw new UException(SystemEnum.DATA_REFUSED,"数据入库异常");
			}
			logger.info("充值订单信息："+order.toString());
			logger.info("====================数据库添加充值订单完成====================");
			
			UserFund userFund=new UserFund();
			userFund.setUserId(user.getId());
			UserFund fund = userFundService.queryUserFundByParams(userFund);
			
			//资金记录
			logger.info("====================添加资金记录====================");
			FundRecord fundRecord=new FundRecord();
			fundRecord.setAmount(amt);
			fundRecord.setAviAmount(fund.getAvailableAmount().add(amt));
			fundRecord.setDescription("快捷充值金额："+amt+"元");
			fundRecord.setOperation(FundRecordOperation.IN);
			fundRecord.setStatus(FundRecordStatus.PROCESSING);
			fundRecord.setType(FundRecordType.DEPOSIT);
			fundRecord.setUserId(user.getId());
			fundRecord.setOrderId(order.getOrderId());
			try {
				fundRecord = fundRecordService.addFundRecord(fundRecord);
			} catch (Exception e1) {
				logger.info("资金记录添加异常"+e1.fillInStackTrace());
				e1.printStackTrace();
				throw new UException(SystemEnum.DATA_REFUSED,"数据入库异常");
			}
			logger.info("====================完成资金记录添加====================");
			
			logger.info("====================构造存管系统充值订单参数====================");
			Map<String,Object>map=new HashMap<String,Object>();
			map.put("order_no", order.getOrderId());
			map.put("partner_trans_date", order.getOrderDate().replace("-", ""));
			map.put("partner_trans_time", order.getOrderTime().replace(":", ""));
			map.put("platcust", userAccount.getPlatcust());
			map.put("name", userAccount.getUserName());
			map.put("account_type",1);//卡类型(1-个人 2-企业)
			map.put("id_type", 1);//id_type	M	C(1)	证件类型
			try{
				DESTextCipher cipher = DESTextCipher.getDesTextCipher();
				map.put("id_code", cipher.decrypt(userAccount.getIdCode()));//id_code	M	C(20)	证件号
				map.put("mobile", cipher.decrypt(userAccount.getPreMobile()));
				if(userAccount.getCardNo()!=null)
					map.put("card_no", cipher.decrypt(userAccount.getCardNo()));
			}catch(GeneralSecurityException e){
				throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"身份证号解密异常");
			}
			map.put("amt", amt);//金额
			map.put("pay_code", ZYCF_CHARGE_PAY_CODE);
			map.put("smsCode",smsCode);
			map.put("charge_type", user.getUserType());
			map.put("notify_url", ZYCF_RECHARGE_NOTIFY_URL);
			
						
			Map<String, Object> sendRequest=null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_QUICK_CHARGE);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络连接异常");
			}
			logger.info("存管系统返回数据："+sendRequest);

			String recode=(String)sendRequest.get("recode");
			String remsg=(String)sendRequest.get("remsg");
			Map<String,Object> res=null;
			if(sendRequest.containsKey("data")){
				@SuppressWarnings("unchecked")
				Map<String,Object> data=(Map<String,Object>)sendRequest.get("data");
				res=data;
			}
			
			order.setRecord(recode);
			order.setRemsg(remsg);
			
			FundRecord fr=new FundRecord();
			fr.setOrderId(order.getOrderId());
			fr.setType(FundRecordType.DEPOSIT);
			
			if("10000".equals(recode)&&res!=null&&"1".equals(res.get("order_status").toString())){
				order.setOrderStatus(OrderStatus.SUCCESSFUL);
				fr.setStatus(FundRecordStatus.SUCCESSFUL);
				
				fund.setUserId(user.getId());
				fund.setAvailableAmount(amt);
				fund.setDepositAmount(amt);
				fund.setTimeLastUpdate(new Date());
				try {
					userFundService.modifyUserFundByParams(fund);
				} catch (Exception e) {
					logger.info("修改账户资金异常"+e.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED,"修改账户资金异常");
				}
				
				brd.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"充值成功"));	
			
			}else if("10000".equals(recode)&&res!=null&&"0".equals(res.get("order_status").toString())){
				order.setOrderStatus(OrderStatus.PROCESSING);
				fr.setStatus(FundRecordStatus.PROCESSING);				
				brd.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"订单处理中"));	
			
			}else if("10000".equals(recode)&&res!=null&&"2".equals(res.get("order_status").toString())){
				order.setOrderStatus(OrderStatus.FAILED);
				fr.setStatus(FundRecordStatus.FAILED);
				brd.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),remsg));
			}else{
				order.setOrderStatus(OrderStatus.FAILED);
				fr.setStatus(FundRecordStatus.FAILED);
				brd.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),remsg));
			}
			try{
				bmOrderService.bmOrderModify(order);
				fundRecordService.modifyFundRecordByParams(fr);
			}catch(Exception e){
				logger.info("订单修改异常"+e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"数据修改异常");
			}
			
		}catch(UException e){
			logger.info("充值异常");
			logger.info(e.fillInStackTrace());
			brd.setMessage(new Message(e.getCode().value(),e.getMessage()));
			
		}
		
		return brd;
	}

	@Override
	public Object callAccountRecharge(ChargeCallBack charge) {
		logger.info("======================充值订单回调："+charge.toString()+"=================");
		Map<String,Object>map=new HashMap<>();
		String str=(String)super.getZsessionObject(charge.getOrder_no());
		if(str!=null&&"chargeCall".equals(str)){
			map.put("recode", "success");
			return JSON.toJSON(map);
		}
		// 订单放入缓存
		super.setZsession(charge.getOrder_no(), "chargeCall");
		
		
		if(charge==null||charge.getOrder_no()==null)
			return null;
		
		BmOrder order=new BmOrder();
		FundRecord record=new FundRecord();
		order.setOrderId(charge.getOrder_no());
		record.setOrderId(charge.getOrder_no());
		record.setType(FundRecordType.DEPOSIT);
		
		FundRecord res = fundRecordService.queryFundRecordByParams(record);
		
		//如果充值成功修改资金账户
		if("1".equals(charge.getOrder_status())){
			
			if(res!=null&&!FundRecordStatus.SUCCESSFUL.equals(res.getStatus())){
				BmAccount account=new BmAccount();
				account.setPlatcust(charge.getPlatcust());
				account=bmAccountService.queryBmAccountByParams(account);
				UserFund fund=new UserFund();
				fund.setUserId(account.getUserId());
				fund.setAvailableAmount(res.getAmount());
				fund.setDepositAmount(res.getAmount());
				fund.setTimeLastUpdate(new Date());
				
				try {
					userFundService.modifyUserFundByParams(fund);
				} catch (Exception e) {
					logger.info("修改账户资金异常"+e.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED,"修改账户资金异常");
				}
			}else{
				map.put("recode", "success");
				return JSON.toJSON(map);
			}
		}		
		
		switch (charge.getOrder_status()) {
		case "0":break;
		case "1":order.setOrderStatus(OrderStatus.SUCCESSFUL);
				 record.setStatus(FundRecordStatus.SUCCESSFUL);
				 break;
		case "2":order.setOrderStatus(OrderStatus.FAILED);
				 record.setStatus(FundRecordStatus.FAILED);
				 record.setAviAmount(res.getAmount().multiply(new BigDecimal(-1)));
				 break;
		}
		record.setPayOrderNo(charge.getPay_order_no());
		record.setPayFinishDate(charge.getPay_finish_date());
		record.setPayFinishTime(charge.getPay_finish_time());
		record.setPayOrderStatus(charge.getOrder_status());
		record.setErrorInfo(charge.getError_info());
		record.setErrorNo(charge.getError_no());
		record.setHostReqSerialNo(charge.getHost_req_serial_no());
		record.setPayAmt(charge.getPay_amt());
		
		try{
			bmOrderService.bmOrderModify(order);
			fundRecordService.modifyFundRecordByParams(record);
		}catch(Exception e){
			logger.info("数据修改异常"+e.fillInStackTrace());
		}
		
		map.put("recode", "success");
		super.removeZsessionObject(charge.getOrder_no());
		return JSON.toJSON(map);
	}
	@Override
	public BmReturnData personWithdraw(HttpServletRequest request, BigDecimal amount, String sms_code) {
		BmReturnData brd=new BmReturnData();
		try{
			//判断是否登录
			logger.info("====================充值申请参数校验====================");
			User user=(User)WebUtils.getSessionAttribute(request, "zycfLoginUser");
			if(user==null)
				throw new UException(SystemEnum.USER_NOLOGIN,"您没有登录");
			
			if(amount==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"提现金额不能为空");
			
			if(sms_code==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"提现短信验证码不能为空");
			
			BmAccount account=new BmAccount();
			account.setUserId(user.getId());
			account=bmAccountService.queryBmAccountByParams(account);
			
			UserFund userFund=new UserFund();
			userFund.setUserId(user.getId());
			userFund=userFundService.queryUserFundByParams(userFund);
			//判断账户余额是否足够
			if(userFund.getAvailableAmount().subtract(amount).doubleValue()<0)
				throw new UException(SystemEnum.PARAMS_ERROR,"提现金额超出账户余额");
			
			//构造提现订单
			BmOrder order=new BmOrder();
			order.setOrderType(OrderType.WITHDRAW);
			order.setUserId(user.getId());
			try {
				order = bmOrderService.bmOrderPersistence(order);
			} catch (Exception e1) {
				logger.info("订单记录添加异常"+e1.fillInStackTrace());
				e1.printStackTrace();
				throw new UException(SystemEnum.DATA_REFUSED,"数据入库异常");
			}
			
			logger.info("====================添加资金记录====================");
			FundRecord fundRecord=new FundRecord();
			fundRecord.setAmount(amount);
			fundRecord.setAviAmount(userFund.getAvailableAmount().subtract(amount));
			fundRecord.setDescription("提现金额："+amount+"元");
			fundRecord.setOperation(FundRecordOperation.OUT);
			fundRecord.setStatus(FundRecordStatus.PROCESSING);
			fundRecord.setType(FundRecordType.WITHDRAW);
			fundRecord.setUserId(user.getId());
			fundRecord.setOrderId(order.getOrderId());
			try {
				fundRecord = fundRecordService.addFundRecord(fundRecord);
			} catch (Exception e1) {
				logger.info("资金记录添加异常"+e1.fillInStackTrace());
				e1.printStackTrace();
				throw new UException(SystemEnum.DATA_REFUSED,"数据入库异常");
			}
			logger.info("====================完成资金记录添加====================");
			
			
			
			//提现订单发往存管系统
			Map<String,Object>map=new HashMap<String,Object>();
			map.put("order_no",order.getOrderId());
			map.put("partner_trans_date",order.getOrderDate().replace("-", ""));
			map.put("partner_trans_time",order.getOrderTime().replace(":", ""));
			map.put("sms_code",sms_code);
			map.put("platcust",account.getPlatcust());
			map.put("amt",amount);
			map.put("is_advance",1);
			if(user.getCustType()==2){
				Bank bank=new Bank();
				bank.setBankCode(account.getOpenBranch());
				bank=bankService.queryByParams(bank);
				map.put("bank_id", bank.getBankId());
			}
			map.put("pay_code",ZYCF_WITHDRAW_PAY_CODE);//只能使用这个通道
			map.put("withdraw_type",user.getUserType()==1?0:1);
			map.put("notify_url",ZYCF_WITHDRAW_NOTIFY_URL);
		
			Map<String, Object> sendRequest=null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_WITHDRAW_URL);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络连接异常");
			}
			
			logger.info("提现申请返回结果"+sendRequest);
			String recode=(String)sendRequest.get("recode");
			String remsg=(String)sendRequest.get("remsg");
			order.setRecord(recode);
			order.setRemsg(remsg);
			
			FundRecord record=new FundRecord();
			record.setOrderId(order.getOrderId());
			
			if("10000".equals(recode)){
				
				//根据返回结果修改订单状态
				order.setOrderStatus(OrderStatus.AUDITING);
				
				//根据返回结果修改资金记录
				record.setStatus(FundRecordStatus.AUDITING);
				
				//根据返回结果修改资金账户,余额减少，冻结金额增加
				UserFund fund=new UserFund();
				fund.setUserId(user.getId());
				fund.setAvailableAmount(new BigDecimal(-1).multiply(amount));
				fund.setFrozenAmount(amount);
				fund.setTimeLastUpdate(new Date());
				try {
					userFundService.modifyUserFundByParams(fund);
				} catch (Exception e) {
					logger.info("修改账户资金异常"+e.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED,"修改账户资金异常");
				}
				
				brd.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"提现申请成功"));
			}else{
				//根据返回结果修改订单状态
				order.setOrderStatus(OrderStatus.FAILED);
				
				//根据返回结果修改资金记录
				record.setStatus(FundRecordStatus.FAILED);
				record.setAviAmount(amount);
				
				brd.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),recode+remsg));
			}
			try{
				bmOrderService.bmOrderModify(order);
				fundRecordService.modifyFundRecordByParams(record);
			}catch(Exception e){
				logger.info("订单修改异常"+e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"数据修改异常");
			}	
			
			
		}catch(UException e){
			logger.info("提现出现异常");
			logger.info(e.fillInStackTrace());
			brd.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return brd;
	}
	

	@Override
	public Object callWithdraw(WithdrawCallBack withdraw) {
		Map<String,Object>map=new HashMap<>();
		try{
			logger.info("提现回调"+withdraw.toString());
			String orderId=withdraw.getOrder_no();
			String order_status = withdraw.getOrder_status();
			
			BmOrder order=new BmOrder();
			order.setOrderId(orderId);
			
			FundRecord record=new FundRecord();
			record.setOrderId(orderId);
			record.setPayOrderNo(withdraw.getPay_order_no());
			record.setPayFinishDate(withdraw.getPay_finish_date());
			record.setPayFinishTime(withdraw.getPay_finish_time());
			record.setPayOrderStatus(order_status);
			record.setPayAmt(withdraw.getPay_amt());
			
			
			BmAccount account=new BmAccount();
			account.setPlatcust(withdraw.getPlatcust());
			account=bmAccountService.queryBmAccountByParams(account);
			
			UserFund fund=new UserFund();
			fund.setUserId(account.getUserId());
			fund.setTimeLastUpdate(new Date());
			
			FundRecord res = fundRecordService.queryFundRecordByParams(record);
			if("1".equals(order_status)&&FundRecordStatus.AUDITING.equals(res.getStatus())){
				
				order.setOrderStatus(OrderStatus.SUCCESSFUL);
				
				record.setStatus(FundRecordStatus.SUCCESSFUL);
				
				record.setHostReqSerialNo(withdraw.getHost_req_serial_no());
				
				fund.setFrozenAmount(new BigDecimal(-1).multiply(withdraw.getOrder_amt()));
				fund.setWithdrawAmount(withdraw.getOrder_amt());
				
			}else if("2".equals(order_status)&&FundRecordStatus.AUDITING.equals(res.getStatus())){
				
				order.setOrderStatus(OrderStatus.FAILED);
				
				record.setStatus(FundRecordStatus.FAILED);
				
				record.setErrorInfo(withdraw.getError_info());
				record.setErrorNo(withdraw.getError_no());
				
				fund.setFrozenAmount(new BigDecimal(-1).multiply(withdraw.getOrder_amt()));
				fund.setAvailableAmount(withdraw.getOrder_amt());
			}else if("0".equals(order_status)&&FundRecordStatus.AUDITING.equals(res.getStatus())){
				order.setOrderStatus(OrderStatus.PAY_PENDING);
				order.setRemark("提现申请成功，交易结果待查");
				record.setStatus(FundRecordStatus.PAY_PENDING);
			}else
				return null;
			try{
				bmOrderService.bmOrderModify(order);
				fundRecordService.modifyFundRecordByParams(record);
				userFundService.modifyUserFundByParams(fund);
			}catch(Exception e){
				logger.info("订单修改异常"+e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"数据修改异常");
			}
			
			map.put("recode", "success");
			
		}catch(UException e){
			logger.info("提现回调出现异常");
			logger.info(e.fillInStackTrace());
		}
		return JSON.toJSON(map);
	}
}
