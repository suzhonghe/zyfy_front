package com.zhongyang.java.bankmanager.biz.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.zhongyang.java.bankmanager.biz.BmLoanManagerBiz;
import com.zhongyang.java.bankmanager.entity.BmAccount;
import com.zhongyang.java.bankmanager.entity.BmOrder;
import com.zhongyang.java.bankmanager.params.Commission;
import com.zhongyang.java.bankmanager.params.InvestDetail;
import com.zhongyang.java.bankmanager.params.RepayDetail;
import com.zhongyang.java.bankmanager.returndata.BmLoanManagerReturn;
import com.zhongyang.java.bankmanager.service.BmAccountService;
import com.zhongyang.java.bankmanager.service.BmOrderService;
import com.zhongyang.java.bankmanager.util.RequestUtil;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.FundRecordOperation;
import com.zhongyang.java.system.enumtype.FundRecordStatus;
import com.zhongyang.java.system.enumtype.FundRecordType;
import com.zhongyang.java.system.enumtype.InvestStatus;
import com.zhongyang.java.system.enumtype.LoanStatus;
import com.zhongyang.java.system.enumtype.OrderStatus;
import com.zhongyang.java.system.enumtype.OrderType;
import com.zhongyang.java.system.enumtype.RepaySource;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.util.FormatUtils;
import com.zhongyang.java.system.util.GetUUID;
import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.system.util.SystemPro;
import com.zhongyang.java.system.util.UtilBiz;
import com.zhongyang.java.zyfyfront.pojo.FundRecord;
import com.zhongyang.java.zyfyfront.pojo.Invest;
import com.zhongyang.java.zyfyfront.pojo.Loan;
import com.zhongyang.java.zyfyfront.pojo.LoanRepayment;
import com.zhongyang.java.zyfyfront.pojo.LoanTender;
import com.zhongyang.java.zyfyfront.pojo.LoanTransRecord;
import com.zhongyang.java.zyfyfront.pojo.User;
import com.zhongyang.java.zyfyfront.pojo.UserFund;
import com.zhongyang.java.zyfyfront.service.FundRecordService;
import com.zhongyang.java.zyfyfront.service.InvestService;
import com.zhongyang.java.zyfyfront.service.LoanRepaymentService;
import com.zhongyang.java.zyfyfront.service.LoanService;
import com.zhongyang.java.zyfyfront.service.LoanTenderService;
import com.zhongyang.java.zyfyfront.service.LoanTransRecordService;
import com.zhongyang.java.zyfyfront.service.UserFundService;

/**
 *@package com.zhongyang.java.bankmanager.biz.impl
 *@filename BmloanManagerBizImpl.java
 *@date 2017年7月6日下午3:16:06
 *@author suzh
 */
@Service
public class BmLoanManagerBizImpl extends UtilBiz implements BmLoanManagerBiz {
	
	static{
		Map<String, Object> sysMap = SystemPro.getProperties();
		ZYCF_BATCH_INVEST = (String) sysMap.get("ZYCF_BATCH_INVEST");
		ZYCF_BATCH_BORROWER_REPAY = (String) sysMap.get("ZYCF_BATCH_BORROWER_REPAY");
		ZYCF_COMPENSATE_REPAY=(String) sysMap.get("ZYCF_COMPENSATE_REPAY");
	}
	
	private static Logger logger=Logger.getLogger(BmLoanManagerBizImpl.class);
	
	private static String ZYCF_BATCH_INVEST;
	
	private static String ZYCF_BATCH_BORROWER_REPAY;
	
	private static String ZYCF_COMPENSATE_REPAY;

	@Autowired
	private BmAccountService bmAccountService;
	
	@Autowired
	private BmOrderService bmOrderService;
	
	@Autowired
	private UserFundService userFundService;
	
	@Autowired
	private LoanService loanService;
	
	@Autowired
	private InvestService investService;
	
	@Autowired
	private FundRecordService fundRecordService;
	
	@Autowired
	private LoanTransRecordService loanTransRecordService;
	
	@Autowired
	private LoanTenderService loanTenderService;
	
	@Autowired
	private LoanRepaymentService loanRepaymentService;
	
	@Override
	public BmLoanManagerReturn userInvest(HttpServletRequest request,String loanId,BigDecimal amount) {
		BmLoanManagerReturn lmr=new BmLoanManagerReturn();
		try{
			logger.info("===============投资参数校验================");
			User user=(User)WebUtils.getSessionAttribute(request, "zycfLoginUser");
			if(user==null)
				throw new UException(SystemEnum.USER_NOLOGIN,"您没有登录");
			if(user.getUserType()==2)
				throw new UException(SystemEnum.PARAMS_ERROR,"借款人不允许投资");
			
			if(loanId==null||"".equals(loanId))
				throw new UException(SystemEnum.PARAMS_ERROR,"请选择所投标的");
			
			String str=(String)super.getZsessionObject(loanId);
			if(str!=null&&"invest".equals(str))
				throw new UException(SystemEnum.PARAMS_ERROR,"系统繁忙请稍后");
			
			// 订单放入缓存
			super.setZsession(loanId, "invest");
			
			Loan loan=new Loan();
			loan.setId(loanId);
			Loan queryLoan = loanService.queryLoanByParams(loan);
						
			if(queryLoan==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"标的不存在，请刷新重新选择");
			
			if(!LoanStatus.OPENED.equals(queryLoan.getStatus()))
				throw new UException(SystemEnum.PARAMS_ERROR,"标的已是非募集状态，请选择其他标的");
			
			if(queryLoan.getBidAmount().add(amount).subtract(queryLoan.getAmount()).doubleValue()>0)
				throw new UException(SystemEnum.PARAMS_ERROR,"超出标的剩余可投金额");
			
			if(amount==null||amount.intValue()%100!=0||amount.intValue()==0)
				throw new UException(SystemEnum.PARAMS_ERROR,"投资金额不能为0并且是100的整数倍");
			
			UserFund userFund=new UserFund();
			userFund.setUserId(user.getId());
			userFund=userFundService.queryUserFundByParams(userFund);
			if(userFund.getAvailableAmount().subtract(amount).doubleValue()<0)
				throw new UException(SystemEnum.PARAMS_ERROR,"投资金额超出账户余额");
			
			BmAccount account =new BmAccount();
			account.setUserId(user.getId());
			BmAccount userAccount = bmAccountService.queryBmAccountByParams(account);
			
			if(userAccount==null||userAccount.getPlatcust()==null)
				throw new UException(SystemEnum.UN_AUTHENTICATION,"没有开通存管账户");
			
			
			//添加订单记录
			BmOrder order=new BmOrder();
			order.setOrderType(OrderType.INVEST);
			order.setUserId(user.getId());
			try{
				order = bmOrderService.bmOrderPersistence(order);
			}catch(Exception e){
				logger.info("订单入库异常"+e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"数据入库异常");
			}
			
			//添加投资记录
			Invest invest=new Invest();
			invest.setOrderId(order.getOrderId());
			invest.setUserId(user.getId());
			invest.setAmount(amount);
			invest.setLoanId(queryLoan.getId());
			invest.setMonths(queryLoan.getMonths());
			invest.setDays(queryLoan.getDays());
			invest.setYears(queryLoan.getYears());
			invest.setRepayMethod(queryLoan.getMethod());
			try {
				invest=investService.addInvest(invest);
			} catch (Exception e) {
				logger.info("添加投资记录异常"+e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"添加投资记录异常");
			}
			
			
			//添加资金记录
			FundRecord record=new FundRecord();
			record.setUserId(user.getId());
			record.setType(FundRecordType.INVEST);
			record.setAmount(amount);
			record.setAviAmount(userFund.getAvailableAmount().subtract(amount));
			record.setDescription("用户投资");
			record.setOperation(FundRecordOperation.OUT);
			record.setStatus(FundRecordStatus.PROCESSING);
			record.setOrderId(order.getOrderId());
			
			try{
				record=fundRecordService.addFundRecord(record);
			}catch(Exception e){
				logger.info("订单修改异常"+e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"数据入库异常");
			}
			
			//添加标的交易记录
			LoanTransRecord transRecord=new LoanTransRecord();
			transRecord.setId(GetUUID.getUniqueKey());
			transRecord.setOrderId(order.getOrderId());
			transRecord.setLoanId(queryLoan.getId());
			transRecord.setOpration(FundRecordOperation.IN);
			transRecord.setStatus(FundRecordStatus.PROCESSING);
			transRecord.setTransDate(order.getOrderDate());
			transRecord.setTransTime(order.getOrderTime());
			transRecord.setType(FundRecordType.INVEST);
			transRecord.setUserId(user.getId());
			transRecord.setAmount(amount);
			try {
				loanTransRecordService.addLoanTransRecord(transRecord);
			} catch (Exception e) {
				logger.info("添加标的交易记录异常"+e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"添加标的交易记录异常");
			}
			
			
			
			//构建存管平台投标参数
			Map<String,Object>map=new HashMap<String,Object>();
			map.put("order_no", order.getOrderId());
			map.put("partner_trans_date", order.getOrderDate().replace("-", ""));
			map.put("partner_trans_time", order.getOrderTime().replace(":", ""));
			map.put("total_num", 1);
			map.put("prod_id", queryLoan.getId());
			InvestDetail investDetail=new InvestDetail();
			investDetail.setDetail_no(order.getOrderId()+"-001");
			investDetail.setPlatcust(userAccount.getPlatcust());
			investDetail.setTrans_amt(amount.setScale(2,BigDecimal.ROUND_HALF_EVEN));
			investDetail.setRemark("个人用户投资");
			investDetail.setSubject_priority("1");//优先使用在途投资账户里的钱
			investDetail.setSelf_amt(amount.setScale(0,BigDecimal.ROUND_HALF_EVEN));
			investDetail.setExperience_amt(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_EVEN));
			investDetail.setCoupon_amt(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_EVEN));
			investDetail.setIn_interest(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_EVEN));
			Commission commission=new Commission();
			commission.setPayout_plat_type("01");
			commission.setPayout_amt(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_EVEN));
			investDetail.setCommission(commission);
			List<InvestDetail>investDetails=new ArrayList<InvestDetail>();
			investDetails.add(investDetail);
			map.put("data", JSON.toJSONString(investDetails));

			Map<String, Object> sendRequest=null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_BATCH_INVEST);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络连接异常");
			}
			
			logger.info("投资存管系统返回的信息"+sendRequest);
			
			String recode=(String)sendRequest.get("recode");
			String remsg=(String)sendRequest.get("remsg");
			Integer success_num=(Integer)sendRequest.get("success_num");
						
			order.setRecord(recode);
			order.setRemsg(remsg);
			
			FundRecord fr=new FundRecord();
			fr.setType(FundRecordType.INVEST);
			fr.setId(record.getId());
			
			LoanTransRecord transRe=new LoanTransRecord();
			transRe.setOrderId(order.getOrderId());
			
			
			Invest inv=new Invest();
			inv.setId(invest.getId());
			
			order.setRecord(recode);
			order.setRemsg(remsg);
			order.setRemark("个人投资,明细编号："+order.getOrderId()+"-001");
			
			if("10000".equals(recode)&&success_num==1){
				
				order.setOrderStatus(OrderStatus.SUCCESSFUL);
				
				fr.setStatus(FundRecordStatus.SUCCESSFUL);
				
				inv.setStatus(InvestStatus.AUDITING);
				
				transRe.setStatus(FundRecordStatus.SUCCESSFUL);
				transRe.setRemark(recode+remsg);
				
				//投资成功，修改资金状态
				UserFund fund=new UserFund();
				fund.setUserId(user.getId());
				fund.setAvailableAmount(amount.multiply(new BigDecimal(-1)));
				fund.setFrozenAmount(amount);
				fund.setTimeLastUpdate(invest.getSubmitTime());
				try {
					userFundService.modifyUserFundByParams(fund);
				} catch (Exception e) {
					logger.info("修改用户资金异常"+e.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED,"修改用户资金异常");
				}
				
				
				LoanTender tender=new LoanTender();
				tender.setLoanId(loanId);
				tender.setAmount(amount);
				try {
					loanTenderService.modifyLoanTenderByParams(tender);
				} catch (Exception e) {
					logger.info("修改标的账户异常"+e.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED,"修改标的账户异常");
				}
				

				Loan resLoan = loanService.queryLoanByParams(loan);
				loan.setBidAmount(amount);
				loan.setBidNumber(1);
				BigDecimal totalAmount = resLoan.getBidAmount().add(amount);
				if(queryLoan.getAmount().subtract(totalAmount).doubleValue()==0){
					loan.setStatus(LoanStatus.FINISHED);
					loan.setTimeFinished(invest.getSubmitTime());
				}
				try {
					loanService.modifyLoanByParams(loan);
				} catch (Exception e) {
					logger.info("修改标的异常"+e.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED,"修改标的异常");
				}
				
				
				lmr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"投资成功"));
			}else if("10000".equals(recode)&&success_num!=1){
				String error_no=null;
				String error_info=null;
				String detail_no=null;
				if(sendRequest.containsKey("error_data")){
					@SuppressWarnings("unchecked")
					List<Map<String,Object>> error_data=(List<Map<String,Object>>)sendRequest.get("error_data");
					error_no=error_data.get(0).get("error_no").toString();
					error_info=error_data.get(0).get("error_info").toString();
					detail_no=error_data.get(0).get("detail_no").toString();
				}
				order.setOrderStatus(OrderStatus.SUCCESSFUL);
				order.setRemark("订单处理成功，投资失败"+error_no+error_info+" 明细编号："+detail_no);
				
				fr.setStatus(FundRecordStatus.FAILED);
				fr.setAviAmount(amount);
				
				inv.setStatus(InvestStatus.FAILED);
				
				transRe.setStatus(FundRecordStatus.FAILED);
				transRe.setRemark("订单处理成功，投资失败"+error_no+error_info+" 明细编号："+detail_no);
				
				lmr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),error_no+error_info));
				
			}else if(!"10000".equals(recode)){
				
				order.setOrderStatus(OrderStatus.FAILED);
				order.setRemark(recode+remsg);
				
				fr.setStatus(FundRecordStatus.FAILED);
				fr.setAviAmount(amount);
				
				inv.setStatus(InvestStatus.FAILED);
				
				transRe.setStatus(FundRecordStatus.FAILED);
				transRe.setRemark(recode+remsg);
				
				lmr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),recode+remsg));
			}
			try{
				bmOrderService.bmOrderModify(order);
			}catch(Exception e){
				logger.info("数据订单状态异常"+e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"数据订单状态异常");
			}
			
			try{
				investService.modifyInvest(inv);
			}catch(Exception e){
				logger.info("修改资金记录异常"+e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"修改投资记录异常");
			}
			
			try{
				fundRecordService.modifyFundRecordByParams(fr);
			}catch(Exception e){
				logger.info("修改资金记录异常"+e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"修改资金记录异常");
			}
			
			try{
				loanTransRecordService.modifyLoanTransRecord(transRe);
			}catch(Exception e){
				logger.info("修改标的交易记录异常"+e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"修改标的交易记录异常");
			}
			
			
			super.removeZsessionObject(loanId);
		}catch(UException e){
			logger.info("投资异常");
			logger.info(e.fillInStackTrace());
			lmr.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return lmr;
	}

	
	@Override
	public BmLoanManagerReturn borrowerRepay(HttpServletRequest request, String repaymentId) {
		BmLoanManagerReturn bmr=new BmLoanManagerReturn();
		try{
			//判断是否登录
			User user=(User)WebUtils.getSessionAttribute(request, "zycfLoginUser");
			if (user==null) 
				throw new UException(SystemEnum.USER_NOLOGIN,"您没有登录");
			
			//判断账户余额是否充足
			UserFund fund=new UserFund();
			fund.setUserId(user.getId());
			fund=userFundService.queryUserFundByParams(fund);
			
			LoanRepayment loanRepayment=new LoanRepayment();
			loanRepayment.setId(repaymentId);
			loanRepayment=loanRepaymentService.queryLoanRepaymentByParams(loanRepayment);
			logger.info("借款人还款："+JSON.toJSONString(loanRepayment));
			
			BigDecimal totalAmount=(loanRepayment.getAmountInterest().add(loanRepayment.getAmountPrincipal())).setScale(2, BigDecimal.ROUND_CEILING);
			if(fund.getAvailableAmount().subtract(totalAmount).doubleValue()<0)
				throw new UException(SystemEnum.PARAMS_ERROR,"账户余额不足");
			
			//判断是否已经还过款
			if(loanRepayment.getIsRepay())
				throw new UException(SystemEnum.PARAMS_ERROR,"已经还款，不可以重复还款");
			
			
			LoanRepayment repay=(LoanRepayment)super.getZsessionObject(repaymentId);
			
			if(repay!=null)
				throw new UException(SystemEnum.PARAMS_ERROR,"还款处理中，不可以重复还款");
			else
				super.setZsession(repaymentId, loanRepayment);
			
			
			BmAccount account=new BmAccount();
			account.setUserId(user.getId());
			account=bmAccountService.queryBmAccountByParams(account);
			
			
			//做还款订单
			BmOrder order=new BmOrder();
			order.setOrderType(OrderType.BORROWER_REPAY);
			order.setUserId(user.getId());
			order.setRemark("借款人还款");
			try{
				order = bmOrderService.bmOrderPersistence(order);
			}catch(Exception e){
				logger.info("订单入库异常"+e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"数据修改异常");
			}
			logger.info("借款人还款订单："+JSON.toJSONString(order));
			
			//构造资金记录
			FundRecord record=new FundRecord();
			record.setUserId(user.getId());
			record.setType(FundRecordType.BORROWER_REPAY);
			record.setAmount(totalAmount);
			record.setAviAmount(fund.getAvailableAmount().subtract(totalAmount));
			record.setDescription("借款人还款");
			record.setOperation(FundRecordOperation.OUT);
			record.setStatus(FundRecordStatus.PROCESSING);
			record.setOrderId(order.getOrderId());
			try{
				fundRecordService.addFundRecord(record);
			}catch(Exception e){
				logger.info("数据入库异常"+e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"数据入库异常");
			}
			
			logger.info("借款人还款资金记录："+JSON.toJSONString(record));
			
						
			//构造标的进账记录
			LoanTransRecord transRecord=new LoanTransRecord();
			transRecord.setId(GetUUID.getUniqueKey());
			transRecord.setOrderId(order.getOrderId());
			transRecord.setLoanId(loanRepayment.getLoanId());
			transRecord.setOpration(FundRecordOperation.IN);
			transRecord.setStatus(FundRecordStatus.PROCESSING);
			transRecord.setTransDate(order.getOrderDate());
			transRecord.setTransTime(order.getOrderTime());
			transRecord.setType(FundRecordType.BORROWER_REPAY);
			transRecord.setUserId(user.getId());
			transRecord.setAmount(totalAmount);
			try{
				loanTransRecordService.addLoanTransRecord(transRecord);
			}catch(Exception e){
				logger.info("添加标的交易记录异常"+e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"添加标的交易记录异常");
			}
			
			logger.info("标的进账记录："+JSON.toJSONString(transRecord));
			
			//发送存管系统请求
			Map<String,Object>map=new HashMap<String,Object>();
			map.put("order_no", order.getOrderId());
			map.put("partner_trans_date", order.getOrderDate().replace("-", ""));
			map.put("partner_trans_time", order.getOrderTime().replace(":", ""));
			
			List<RepayDetail>repayDetails=new ArrayList<RepayDetail>();
			RepayDetail repayDetail=new RepayDetail();
			repayDetail.setDetail_no(order.getOrderId()+"-001");
			repayDetail.setProd_id(loanRepayment.getLoanId());
			repayDetail.setPlatcust(account.getPlatcust());
			repayDetail.setRepay_num(loanRepayment.getCurrentPeriod());
			repayDetail.setRepay_date(FormatUtils.simpleFormat(loanRepayment.getDueDate()));
			repayDetail.setReal_repay_date(FormatUtils.simpleFormat(new Date()));
			repayDetail.setRepay_amt(totalAmount);
			repayDetail.setReal_repay_amt(totalAmount.setScale(2, BigDecimal.ROUND_HALF_EVEN));
			repayDetail.setTrans_amt(totalAmount);
			repayDetail.setFee_amt(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_EVEN));
			repayDetails.add(repayDetail);
			
			map.put("data", JSON.toJSONString(repayDetails));
			map.put("total_num", 1);
			logger.info("借款人批量还款存管系统参数："+JSON.toJSONString(map));
			
			Map<String, Object> sendRequest=null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_BATCH_BORROWER_REPAY);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络连接异常");
			}

			logger.info("存管系统返回信息："+sendRequest);
			
			String recode=(String)sendRequest.get("recode");
			String remsg=(String)sendRequest.get("remsg");
			Integer success_num=(Integer)sendRequest.get("success_num");
			
			FundRecord fundRecord=new FundRecord();
			fundRecord.setOrderId(order.getOrderId());
			
			order.setRecord(recode);
			order.setRemsg(remsg);
			
			LoanTransRecord trRecord=new LoanTransRecord();
			trRecord.setOrderId(order.getOrderId());
			
			if("10000".equals(recode)&&success_num==1){
				
				fundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
				
				order.setOrderStatus(OrderStatus.SUCCESSFUL);

				trRecord.setStatus(FundRecordStatus.SUCCESSFUL);
				
				//根据返回情况修改借款人资金账户
				logger.info("修改借款人资金账户,USERID:"+user.getId());
				UserFund userFund=new UserFund();
				userFund.setUserId(user.getId());
				userFund.setAvailableAmount(new BigDecimal(-1).multiply(totalAmount));
				userFund.setDueOutAmount(new BigDecimal(-1).multiply(totalAmount));
				try{
					userFundService.modifyUserFundByParams(userFund);
				}catch(Exception e){
					logger.info("修改资金账户异常"+e.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED,"修改资金账户异常");
				}
				
				logger.info("资金账户修改"+JSON.toJSONString(userFund));
				
				//修改还款计划借款人还款状态
				logger.info("修改还款计划借款人已还款，还款计划ID："+loanRepayment.getId());
				LoanRepayment lr=new LoanRepayment();
				lr.setId(loanRepayment.getId());
				lr.setIsRepay(true);
				lr.setSourceId(user.getId());
				lr.setRepaySource(RepaySource.NORMAL_PAYMENT);
				try{
					loanRepaymentService.modifyLoanRepaymentByParams(lr);
				}catch(Exception e){
					logger.info("修改还款计划异常"+e.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED,"修改还款计划异常");
				}
				
				logger.info("还款计划修改"+JSON.toJSONString(lr));
				
				//修改标的账户余额
				logger.info("修改标的账户，loanID："+loanRepayment.getLoanId());
				LoanTender tender=new LoanTender();
				tender.setLoanId(loanRepayment.getLoanId());
				tender.setAmount(loanRepayment.getAmountInterest().add(loanRepayment.getAmountPrincipal()));
				try{
					loanTenderService.modifyLoanTenderByParams(tender);
				}catch(Exception e){
					logger.info("修改标的账户异常"+e.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED,"修改标的账户异常");
				}
				
				
				logger.info("标的账户修改"+JSON.toJSONString(tender));
				
				bmr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"还款成功"));
			}else if("10000".equals(recode)&&success_num!=1){
				
				String error_no=null;
				String error_info=null;
				String detail_no=null;
				if(sendRequest.containsKey("error_data")){
					@SuppressWarnings("unchecked")
					List<Map<String,Object>> error_data=(List<Map<String,Object>>)sendRequest.get("error_data");
					error_no=error_data.get(0).get("error_no").toString();
					error_info=error_data.get(0).get("error_info").toString();
					detail_no=error_data.get(0).get("detail_no").toString();
				}
				order.setOrderStatus(OrderStatus.SUCCESSFUL);
				order.setRemark("订单处理成功，还款失败"+error_no+error_info+" 明细编号："+detail_no);
				
				fundRecord.setStatus(FundRecordStatus.FAILED);
				fundRecord.setAmount(totalAmount);
				
				trRecord.setStatus(FundRecordStatus.FAILED);
				trRecord.setRemark("订单处理成功，还款失败"+error_no+error_info+" 明细编号："+detail_no);
				
				
				bmr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),error_no+error_info));
			}else if(!"10000".equals(recode)){
				
				fundRecord.setStatus(FundRecordStatus.FAILED);
				fundRecord.setAviAmount(totalAmount);
				
				order.setOrderStatus(OrderStatus.FAILED);
				order.setRemark(recode+remsg);

				trRecord.setStatus(FundRecordStatus.FAILED);
				trRecord.setRemark(recode+remsg);

				bmr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),remsg));
			}
			try{
				fundRecordService.modifyFundRecordByParams(fundRecord);
				bmOrderService.bmOrderModify(order);
				loanTransRecordService.modifyLoanTransRecord(trRecord);
			}catch(Exception e){
				logger.info("数据修改异常"+e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"数据修改异常");
			}
			
			
			super.removeZsessionObject(repaymentId);
		}catch(UException e){
			logger.info("借款人还款出现异常");
			logger.info(e.fillInStackTrace());
			bmr.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return bmr;
	}


	@Override
	public BmLoanManagerReturn compensateRepay(HttpServletRequest request, String repaymentId) {
		BmLoanManagerReturn bmr=new BmLoanManagerReturn();
		try{
			//判断是否登录
			User user=(User)WebUtils.getSessionAttribute(request, "zycfLoginUser");
			if (user==null) 
				throw new UException(SystemEnum.USER_NOLOGIN,"您没有登录");
			
			//判断账户余额是否充足
			UserFund fund=new UserFund();
			fund.setUserId(user.getId());
			fund=userFundService.queryUserFundByParams(fund);
			
			LoanRepayment loanRepayment=new LoanRepayment();
			loanRepayment.setId(repaymentId);
			loanRepayment=loanRepaymentService.queryLoanRepaymentByParams(loanRepayment);
			logger.info("代偿（委托）还款："+JSON.toJSONString(loanRepayment));
			
			BigDecimal totalAmount=(loanRepayment.getAmountInterest().add(loanRepayment.getAmountPrincipal())).setScale(2, BigDecimal.ROUND_CEILING);
			if(fund.getAvailableAmount().subtract(totalAmount).doubleValue()<0)
				throw new UException(SystemEnum.PARAMS_ERROR,"账户余额不足");
			
			//判断是否已经还过款
			if(loanRepayment.getIsRepay())
				throw new UException(SystemEnum.PARAMS_ERROR,"已经还款，不可以重复还款");
			
			
			LoanRepayment repay=(LoanRepayment)super.getZsessionObject(repaymentId);
			
			if(repay!=null)
				throw new UException(SystemEnum.PARAMS_ERROR,"还款处理中，不可以重复还款");
			else
				super.setZsession(repaymentId, loanRepayment);
			
			
			BmAccount account=new BmAccount();
			account.setUserId(user.getId());
			account=bmAccountService.queryBmAccountByParams(account);//代偿人
			
			Loan loan=new Loan();
			loan.setId(loanRepayment.getLoanId());
			loan=loanService.queryLoanByParams(loan);
			
			BmAccount loanAccount=new BmAccount();
			loanAccount.setUserId(loan.getLoanUserId());
			loanAccount=bmAccountService.queryBmAccountByParams(loanAccount);//借款人
			
			//做还款订单
			BmOrder order=new BmOrder();
			order.setOrderType(OrderType.COMPENSATE_REPAY);
			order.setUserId(user.getId());
			order.setRemark("代偿（委托）还款");
			try{
				order = bmOrderService.bmOrderPersistence(order);
			}catch(Exception e){
				logger.info("订单入库异常"+e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"数据修改异常");
			}
			logger.info("代偿（委托）还款："+JSON.toJSONString(order));
			
			//构造资金记录
			FundRecord record=new FundRecord();
			record.setUserId(user.getId());
			record.setType(FundRecordType.COMPENSATE_REPAY);
			record.setAmount(totalAmount);
			record.setAviAmount(fund.getAvailableAmount().subtract(totalAmount));
			record.setDescription("代偿（委托）还款");
			record.setOperation(FundRecordOperation.OUT);
			record.setStatus(FundRecordStatus.PROCESSING);
			record.setOrderId(order.getOrderId());
			try{
				fundRecordService.addFundRecord(record);
			}catch(Exception e){
				logger.info("数据入库异常"+e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"数据入库异常");
			}
			
			logger.info("代偿（委托）还款资金记录："+JSON.toJSONString(record));
			
						
			//构造标的进账记录
			LoanTransRecord transRecord=new LoanTransRecord();
			transRecord.setId(GetUUID.getUniqueKey());
			transRecord.setOrderId(order.getOrderId());
			transRecord.setLoanId(loanRepayment.getLoanId());
			transRecord.setOpration(FundRecordOperation.IN);
			transRecord.setStatus(FundRecordStatus.PROCESSING);
			transRecord.setTransDate(order.getOrderDate());
			transRecord.setTransTime(order.getOrderTime());
			transRecord.setType(FundRecordType.COMPENSATE_REPAY);
			transRecord.setUserId(user.getId());
			transRecord.setAmount(totalAmount);
			try{
				loanTransRecordService.addLoanTransRecord(transRecord);
			}catch(Exception e){
				logger.info("添加标的交易记录异常"+e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"添加标的交易记录异常");
			}
			
			logger.info("标的进账记录："+JSON.toJSONString(transRecord));
			
			//发送存管系统请求
			Map<String,Object>map=new HashMap<String,Object>();
			map.put("order_no", order.getOrderId());
			map.put("partner_trans_date", order.getOrderDate().replace("-", ""));
			map.put("partner_trans_time", order.getOrderTime().replace(":", ""));
			map.put("prod_id", loanRepayment.getLoanId());
			map.put("repay_num", loanRepayment.getCurrentPeriod());
			map.put("repay_date", FormatUtils.dateFormat(loanRepayment.getDueDate()));
			map.put("repay_amt", totalAmount);
			map.put("real_repay_date",FormatUtils.dateFormat(new Date()));
			map.put("real_repay_amt", totalAmount);
			map.put("platcust",loanAccount.getPlatcust() );//借款人的
			map.put("compensation_platcust",account.getPlatcust() );//代偿人的
			map.put("trans_amt",totalAmount);
			map.put("fee_amt", "0.00");
			map.put("repay_type","0");
			
			logger.info("代偿（委托）还款存管系统参数："+JSON.toJSONString(map));
			
			Map<String, Object> sendRequest=null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_COMPENSATE_REPAY);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络连接异常");
			}

			logger.info("存管系统返回信息："+sendRequest);
			
			String recode=(String)sendRequest.get("recode");
			String remsg=(String)sendRequest.get("remsg");
			String order_status=null;
			if(sendRequest.containsKey("data")){
				@SuppressWarnings("unchecked")
				Map<String,Object> data=(Map<String,Object>)sendRequest.get("data");
				order_status=data.get("order_status").toString();
			}
			
			FundRecord fundRecord=new FundRecord();
			fundRecord.setOrderId(order.getOrderId());
			
			order.setRecord(recode);
			order.setRemsg(remsg);
			
			LoanTransRecord trRecord=new LoanTransRecord();
			trRecord.setOrderId(order.getOrderId());
			
			if("10000".equals(recode)&&"1".equals(order_status)){
				
				fundRecord.setStatus(FundRecordStatus.SUCCESSFUL);
				
				order.setOrderStatus(OrderStatus.SUCCESSFUL);

				trRecord.setStatus(FundRecordStatus.SUCCESSFUL);
				
				//根据返回情况修改借款人资金账户
				logger.info("修改代偿（委托）人资金账户,USERID:"+user.getId());
				UserFund userFund=new UserFund();
				userFund.setUserId(user.getId());
				userFund.setAvailableAmount(new BigDecimal(-1).multiply(totalAmount));
				try{
					userFundService.modifyUserFundByParams(userFund);
				}catch(Exception e){
					logger.info("修改账户资金异常"+e.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED,"修改账户资金异常");
				}
				
				logger.info("资金账户修改"+JSON.toJSONString(userFund));
				
				//修改还款计划借款人还款状态
				logger.info("修改还款计划代偿（委托）还款，还款计划ID："+loanRepayment.getId());
				LoanRepayment lr=new LoanRepayment();
				lr.setId(loanRepayment.getId());
				lr.setIsRepay(true);
				lr.setSourceId(user.getId());
				lr.setRepaySource(RepaySource.COMPENSATORY_PAYMENT);
				try{
					loanRepaymentService.modifyLoanRepaymentByParams(lr);
				}catch(Exception e){
					logger.info("修改还款计划异常"+e.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED,"修改还款计划异常");
				}
				
				logger.info("还款计划修改"+JSON.toJSONString(lr));
				
				//修改标的账户余额
				logger.info("修改标的账户，loanID："+loanRepayment.getLoanId());
				LoanTender tender=new LoanTender();
				tender.setLoanId(loanRepayment.getLoanId());
				tender.setAmount(loanRepayment.getAmountInterest().add(loanRepayment.getAmountPrincipal()));
				try{
					loanTenderService.modifyLoanTenderByParams(tender);
				}catch(Exception e){
					logger.info("修改标的账户异常"+e.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED,"修改标的账户异常");
				}
				
				logger.info("标的账户修改"+JSON.toJSONString(tender));
				
				bmr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"还款成功"));
			}else if("10000".equals(recode)&&!"1".equals(order_status)){
				
				order.setOrderStatus(OrderStatus.SUCCESSFUL);
				order.setRemark("订单处理成功，还款失败"+remsg);
				
				fundRecord.setStatus(FundRecordStatus.FAILED);
				fundRecord.setAmount(totalAmount);
				
				trRecord.setStatus(FundRecordStatus.FAILED);
				trRecord.setRemark("订单处理成功，还款失败"+remsg);
				
				
				bmr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),remsg));
			}else if(!"10000".equals(recode)){
				
				fundRecord.setStatus(FundRecordStatus.FAILED);
				fundRecord.setAviAmount(totalAmount);
				
				order.setOrderStatus(OrderStatus.FAILED);
				order.setRemark(recode+remsg);

				trRecord.setStatus(FundRecordStatus.FAILED);
				trRecord.setRemark(recode+remsg);

				bmr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),recode+remsg));
			}
			try{
				fundRecordService.modifyFundRecordByParams(fundRecord);
				bmOrderService.bmOrderModify(order);
				loanTransRecordService.modifyLoanTransRecord(trRecord);
			}catch(Exception e){
				logger.info("数据修改异常"+e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"数据修改异常");
			}
			
			
			super.removeZsessionObject(repaymentId);
		}catch(UException e){
			logger.info("代偿（委托）还款出现异常");
			logger.info(e.fillInStackTrace());
			bmr.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return bmr;
	}
	
}
