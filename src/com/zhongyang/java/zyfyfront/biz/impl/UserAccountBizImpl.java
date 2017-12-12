package com.zhongyang.java.zyfyfront.biz.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.FundRecordStatus;
import com.zhongyang.java.system.enumtype.FundRecordType;
import com.zhongyang.java.system.enumtype.InvestStatus;
import com.zhongyang.java.system.enumtype.Method;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.util.DESTextCipher;
import com.zhongyang.java.system.util.FormatUtils;
import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.system.util.SystemPro;
import com.zhongyang.java.zyfyfront.biz.UserAccountBiz;
import com.zhongyang.java.zyfyfront.pojo.Bank;
import com.zhongyang.java.zyfyfront.pojo.FundRecord;
import com.zhongyang.java.zyfyfront.pojo.Invest;
import com.zhongyang.java.zyfyfront.pojo.InvestRepayment;
import com.zhongyang.java.zyfyfront.pojo.Loan;
import com.zhongyang.java.zyfyfront.pojo.LoanRepayment;
import com.zhongyang.java.zyfyfront.pojo.User;
import com.zhongyang.java.zyfyfront.pojo.UserFund;
import com.zhongyang.java.zyfyfront.returndata.UserAccountReturn;
import com.zhongyang.java.zyfyfront.service.BankService;
import com.zhongyang.java.zyfyfront.service.FundRecordService;
import com.zhongyang.java.zyfyfront.service.InvestRepaymentService;
import com.zhongyang.java.zyfyfront.service.InvestService;
import com.zhongyang.java.zyfyfront.service.LoanRepaymentService;
import com.zhongyang.java.zyfyfront.service.LoanService;
import com.zhongyang.java.zyfyfront.service.UserAccountService;
import com.zhongyang.java.zyfyfront.service.UserFundService;
import com.zhongyang.java.zyfyfront.service.UserService;
import com.zhongyang.java.zyfyfront.vo.useraccount.FundRecordAccount;
import com.zhongyang.java.zyfyfront.vo.useraccount.LoanRepayMentAccount;
import com.zhongyang.java.zyfyfront.vo.useraccount.LoanStatusAccount;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserAccountVo;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInfo;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInvestLoanVo;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInvestMoneyVo;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInvestRecordVo;

@Service
public class UserAccountBizImpl implements UserAccountBiz {

	private static Logger logger = Logger.getLogger(UserAccountBizImpl.class);

	static {
		Map<String, Object> map = SystemPro.getProperties();
		path = (String) map.get("CONTRACT_HOME");
		secondContractsPath = (String) map.get("SECOND_CONTRACT_HOME");
	}

	private static String path;

	private static String secondContractsPath;

	@Autowired
	UserAccountService userAccountService;

	@Autowired
	UserFundService userFundService;
	
	@Autowired
	private LoanService loanService;
	
	@Autowired
	private LoanRepaymentService loanRepaymentService;
	
	@Autowired
	private InvestRepaymentService investRepaymentService;
	
	@Autowired
	private InvestService investService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	private FundRecordService fundRecordService;
	
	@Override
 	public UserAccountReturn getUserAccountInfo(HttpServletRequest request) {
		UserAccountReturn ur=new UserAccountReturn();
		User user = (User) request.getSession().getAttribute("zycfLoginUser");
		try {
			if (user == null || user.getId() == null) 
				throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");
			
			UserFund userFund = new UserFund();
			userFund.setUserId(user.getId());
	
			UserAccountVo userAccountVo = new UserAccountVo();

		
			userFund = userFundService.queryUserFundByParams(userFund);
			if (userFund != null) {
				userAccountVo.setAvailableAmount(userFund.getAvailableAmount());
				userAccountVo.setDueInAmount(userFund.getDueInAmount());
				userAccountVo.setDepositAmount(userFund.getDepositAmount());
				userAccountVo.setWithdrawAmount(userFund.getWithdrawAmount());
			}

		List<UserInvestMoneyVo> userInvests = userAccountService.getUserAccountInfo(user.getId());
		Invest invest=new Invest();
		invest.setUserId(user.getId());
		invest.setStatus(InvestStatus.AUDITING);
		BigDecimal total_frozen_amount=new BigDecimal(0);
		List<Invest> invests = investService.queryInvestByParams(invest);
		if(invests!=null&&invests.size()>0){
			for (Invest inv : invests) {
				total_frozen_amount=total_frozen_amount.add(inv.getAmount());
			}
		}
		
		FundRecord fundRecord=new FundRecord();
		fundRecord.setUserId(user.getId());
		fundRecord.setStatus(FundRecordStatus.AUDITING);
		fundRecord.setType(FundRecordType.WITHDRAW);
		List<FundRecord> records = fundRecordService.queryFundRecordListByParams(fundRecord);
		if(records!=null&&records.size()>0){
			for (FundRecord record : records) {
				total_frozen_amount=total_frozen_amount.add(record.getAmount());
			}
		}
		userAccountVo.setFrozenAmount(total_frozen_amount);
			
		for (UserInvestMoneyVo userInvest : userInvests) {
			if (userInvest.getStatus().equals("UNDUE") || "OVERDUE".equals(userInvest.getStatus())) {
				userAccountVo.setUndueAmountInterest(
						userInvest.getAmountInterest().add(userAccountVo.getUndueAmountInterest()));
				userAccountVo.setUndueAmountCapital(
						userInvest.getAmountCapital().add(userAccountVo.getUndueAmountCapital()));
			} else if (userInvest.getStatus().equals("REPAYED") || "PREPAY".equals(userInvest.getStatus().toString()))
				userAccountVo.setAllRevue(userInvest.getAmountInterest().add(userAccountVo.getAllRevue()));
		}
		userAccountVo.setAllCapital(userAccountVo.getAvailableAmount().add(userAccountVo.getFrozenAmount())
				.add(userAccountVo.getUndueAmountCapital()).add(userAccountVo.getUndueAmountInterest()));
		ur.setUserAccountVo(userAccountVo);
		ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"账户信息查询成功"));
		} catch (UException e) {
			logger.info(e, e.fillInStackTrace());
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}

	@Override
	public UserAccountReturn getUserFundRecord(HttpServletRequest request, Page<FundRecordAccount> page) {
		User user = (User) request.getSession().getAttribute("zycfLoginUser");
		UserAccountReturn ur=new UserAccountReturn();
		try {
			if (user == null || user.getId() == null)
				throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");
	
			if (page == null) {
				page = new Page<FundRecordAccount>();
				page.setPageNo(1);
				page.setPageSize(10);
	
			}
			
			if(page.getStrStart()!=null&&!"".equals(page.getStrStart()))
				page.setStartTime(FormatUtils.timeFormat(page.getStrStart()+" 00:00:00"));
			
			if(page.getStrEnd()!=null&&!"".equals(page.getStrEnd()))
				page.setEndTime(FormatUtils.timeFormat(page.getStrEnd()+" 23:59:59"));
			
			page.getParams().put("userId", user.getId());
			List<FundRecordAccount> fundRecords = userAccountService.queryUserFundRecord(page);
			
			if (fundRecords != null) {
				for (FundRecordAccount record : fundRecords) {
					record.setType(FundRecordType.valueOf(record.getType()).getKey());
					record.setStatus(FundRecordStatus.valueOf(record.getStatus()).getKey());
					record.setStrTimeRecorded(FormatUtils.millisDateFormat(record.getTimerecorded()));
				}
			}
			page.setResults(fundRecords);
			ur.setTotalPage(page.getTotalPage());
			ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"资金记录查询成功"));
			ur.setPageRecords(page);

		} catch (UException e) {
			e.printStackTrace();
			logger.info(e, e.fillInStackTrace());
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}

	@Override
	public UserAccountReturn selectUserInvestInfo(HttpServletRequest request, Page<UserInvestRecordVo> page) {
		User user = (User) request.getSession().getAttribute("zycfLoginUser");
		UserAccountReturn ur=new UserAccountReturn();
		try{
			if (user == null || user.getId() == null)
				throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");
			
			page.getParams().put("userId", user.getId());
			
			List<UserInvestRecordVo> records = userAccountService.selectUserInvestInfo(page);
			for (UserInvestRecordVo record : records) {
				record.setRepayStatus(LoanRepayMentAccount.valueOf(record.getRepayStatus()).getKey());
				record.setLoanStatus(LoanStatusAccount.valueOf(record.getLoanStatus()).getKey());
				Date date = FormatUtils.dateFormat(record.getDueDate());
				record.setDueDate(FormatUtils.simpleFormat(date));
				record.setStrRepayDate(FormatUtils.simpleFormat(record.getRepayDate()));
				record.setInvestTime(record.getInvestTime().substring(0, 10));
			}
			page.setResults(records);
			ur.setTotalPage(page.getTotalPage());
			ur.setUserInvestRecordVo(page);
			ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"回款计划查询成功"));
		}catch(UException e){
			logger.info("回款计划查询失败");
			logger.info(e.fillInStackTrace());
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}

	@Override
	public UserAccountReturn getUserInvestLoanRecord(HttpServletRequest request, Page<UserInvestLoanVo> page) {
		User user = (User) request.getSession().getAttribute("zycfLoginUser");
		UserAccountReturn ur=new UserAccountReturn();
		try{
			if (user == null || user.getId() == null)
				throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");

			page.getParams().put("userId", user.getId());
			
			if(page.getStrStart()!=null&&!"".equals(page.getStrStart()))
				page.setStartTime(FormatUtils.timeFormat(page.getStrStart()+" 00:00:00"));
			
			if(page.getStrEnd()!=null&&!"".equals(page.getStrEnd()))
				page.setEndTime(FormatUtils.timeFormat(page.getStrEnd()+" 23:59:59"));
			
			List<UserInvestLoanVo> list = userAccountService.queryUserLoanInvestInfo(page);
			if (list != null) {
				for (UserInvestLoanVo record : list) {
					BigDecimal temp = record.getAmount().multiply(record.getRate().add(new BigDecimal(record.getAddRate())))
							.divide(new BigDecimal(100), 4, BigDecimal.ROUND_DOWN)
							.divide(new BigDecimal(12), 2, BigDecimal.ROUND_DOWN)
							.multiply(new BigDecimal(record.getMonths()));

					record.setDueInterest(temp);
					record.setLoanStatus(LoanStatusAccount.valueOf(record.getLoanStatus()).getKey());
					record.setRepayMethod(Method.valueOf(record.getRepayMethod()).getKey());
					record.setInvestStatus(InvestStatus.valueOf(record.getInvestStatus()).getKey());

					record.setStrTimeSettled(FormatUtils.simpleFormat(record.getTimeSettled()));
					record.setInvestTime(record.getInvestTime().substring(0, 10));

				}
			}

			page.setResults(list);
			ur.setTotalPage(page.getTotalPage());
			ur.setUserInvestLoanVo(page);
			ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"投资项目查询成功"));
		}catch(UException e){
			logger.info("投资项目查询失败");
			logger.info(e.fillInStackTrace());
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		
		return ur;
	}

	@Override
	public ResponseEntity<byte[]> downLoadContract(String investId) {

		try {
			String contractPath = path + investId + ".pdf";
			String secondContrcatsPath = secondContractsPath + investId + ".pdf";
			File file = new File(contractPath);
			File secondFile = new File(secondContrcatsPath);
			HttpHeaders headers = new HttpHeaders();
			headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + investId + ".pdf");
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			if (file.exists()) {
				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
			} else if (secondFile.exists()) {
				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(secondFile), headers, HttpStatus.OK);
			} else {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.info(investId+"合同下载失败");
			logger.info(e.fillInStackTrace());
		}
		return null;
	}
	
	@Override
	public UserAccountReturn getUserSetting(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("zycfLoginUser");
		UserAccountReturn ur=new UserAccountReturn();
		try{
			if (user == null || user.getId() == null)
				throw new UException(SystemEnum.USER_NOLOGIN, "没有登录");

			UserInfo userInfo = userAccountService.queryUserInfo(user.getId());
			DESTextCipher cipher=DESTextCipher.getDesTextCipher();
			try {
				String mobile = cipher.decrypt(userInfo.getMobile());
				mobile=mobile.substring(0, 3)+"****"+mobile.substring(mobile.length()-4, mobile.length());
				userInfo.setMobile(mobile);
				
				if(userInfo.getRefMobile()!=null){
					String refMobile=cipher.decrypt(userInfo.getRefMobile());
					refMobile=refMobile.substring(0, 3)+"****"+refMobile.substring(refMobile.length()-4, refMobile.length());
					userInfo.setRefMobile(refMobile);
				}
				
				if(userInfo.getIdCode()!=null){
					String idCode=cipher.decrypt(userInfo.getIdCode());
					idCode=idCode.substring(0, 3)+"**********"+idCode.substring(idCode.length()-4, idCode.length());
					userInfo.setIdCode(idCode);
				}
				if(userInfo.getCardNo()!=null){
					String cardNo=cipher.decrypt(userInfo.getCardNo());
					cardNo=cardNo.substring(0, 4)+"**********"+cardNo.substring(cardNo.length()-4, cardNo.length());
					userInfo.setCardNo(cardNo);
					
				}
				if(userInfo.getBankCode()!=null){
					Bank bank=new Bank();
					bank.setBankCode(userInfo.getBankCode());
					bank=bankService.queryByParams(bank);
					userInfo.setBankEnName(bank.getEnName());
				}
			
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
				logger.info("解密异常");
				throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"数据解密异常");
			}
			
			ur.setUserInfo(userInfo);
			ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"投资项目查询成功"));
		}catch(UException e){
			logger.info("投资项目查询失败");
			logger.info(e.fillInStackTrace());
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		
		return ur;
	}

	@Override
	public UserAccountReturn loanManagement(HttpServletRequest request, Page<Loan>page) {

		User user = (User) request.getSession().getAttribute("zycfLoginUser");
		UserAccountReturn ur=new UserAccountReturn();
		try {
			if(user==null)
				throw new UException(SystemEnum.USER_NOLOGIN,"您没有登录");
			
			page.getParams().put("userId", user.getId());
			if(page.getStrStart()!=null&&!"".equals(page.getStrStart()))
			page.setStartTime(FormatUtils.timeFormat(page.getStrStart()+" 00:00:00"));
		
			if(page.getStrEnd()!=null&&!"".equals(page.getStrEnd()))
			page.setEndTime(FormatUtils.timeFormat(page.getStrEnd()+" 23:59:59"));
			
			List<Loan> loans = loanService.queryLoanManage(page);
			for (Loan loan : loans) {
				loan.setStrMethod(loan.getMethod().getKey());
				loan.setStrStatus(loan.getStatus().getKey());
			}
			page.setResults(loans);
			ur.setTotalPage(page.getTotalPage());
			ur.setPageLoan(page);
			ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"操作成功"));
		} catch (UException e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}

	@Override
	public UserAccountReturn loanRepaymentPlant(HttpServletRequest request, Page<LoanRepayment> page) {
		User user = (User) request.getSession().getAttribute("zycfLoginUser");
		UserAccountReturn ur=new UserAccountReturn();
		try {
			if(user==null)
				throw new UException(SystemEnum.USER_NOLOGIN,"您没有登录");
			
			page.getParams().put("userId", user.getId());
			
			List<LoanRepayment> res = loanRepaymentService.queryLoanRepaymentsByPage(page);
			for (LoanRepayment loanRepayment : res) {
				loanRepayment.setSourceId(null);
				loanRepayment.setStrStatus(loanRepayment.getStatus().getKey());
				loanRepayment.setStrDueDate(FormatUtils.simpleFormat(loanRepayment.getDueDate()));
			}
			page.setResults(res);
			ur.setTotalPage(page.getTotalPage());
			ur.setPageLoanRepayment(page);
			ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"操作成功"));
		} catch (UException e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}

	
	@Override
	public UserAccountReturn investRepaymentPlant(HttpServletRequest request, Page<InvestRepayment> page) {
		User user = (User) request.getSession().getAttribute("zycfLoginUser");
		UserAccountReturn ur=new UserAccountReturn();
		try {
			if(user==null)
				throw new UException(SystemEnum.USER_NOLOGIN,"您没有登录");
			
			page.getParams().put("userId", user.getId());
			
			List<InvestRepayment> res = investRepaymentService.queryInvestRepaymentByPage(page);
			for (InvestRepayment investRepayment : res) {
				investRepayment.setId(null);
				investRepayment.setSourceId(null);
				investRepayment.setInvestId(null);
			}
			page.setResults(res);
			ur.setTotalPage(page.getTotalPage());
			ur.setPageInvestRepayment(page);
			ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"操作成功"));
		} catch (UException e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}

	
	@Override
	public UserAccountReturn queryInviterInvest(HttpServletRequest request, Page<Invest> page) {
		User user = (User) request.getSession().getAttribute("zycfLoginUser");
		UserAccountReturn ur=new UserAccountReturn();
		try {
			if(user==null)
				throw new UException(SystemEnum.USER_NOLOGIN,"您没有登录");
			
			page.getParams().put("userId", user.getId());			
			List<Invest> res = investService.queryInvestByPage(page);
			for (Invest invest : res) {
				invest.setUserName(invest.getUserName().substring(0,1)+"**");
				invest.setStrSubmitTime(FormatUtils.millisDateFormat(invest.getSubmitTime()));
				invest.setSubmitTime(null);
			}
			
			page.setResults(res);
			ur.setPageInvest(page);
			ur.setTotalPage(page.getTotalPage());
			ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"操作成功"));
		} catch (UException e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}

	
	@Override
	public UserAccountReturn queryInviterList(HttpServletRequest request, Page<User> page) {
		User user = (User) request.getSession().getAttribute("zycfLoginUser");
		UserAccountReturn ur=new UserAccountReturn();
		try {
			if(user==null)
				throw new UException(SystemEnum.USER_NOLOGIN,"您没有登录");
			
			page.getParams().put("userId", user.getId());			
			List<User> res = userService.queryUserByPage(page);
			DESTextCipher cipher=DESTextCipher.getDesTextCipher();
			for (User us : res) {
				try {
					String mobile = cipher.decrypt(us.getMobile());
					us.setMobile(mobile);
					if(us.getUserName()!=null)
						us.setUserName(us.getUserName().substring(0, 1)+"**");
					
					us.setStrRegisterDate(FormatUtils.millisDateFormat(us.getRegisterDate()));
					us.setStrLastLoginDate(us.getLastLoginDate()==null?"":FormatUtils.millisDateFormat(us.getLastLoginDate()));
					us.setRegisterDate(null);
					us.setLastLoginDate(null);
				
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"数据解密异常");
				}
				
			}
			
			page.setResults(res);
			ur.setPageUser(page);
			ur.setTotalPage(page.getTotalPage());
			ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"操作成功"));
		} catch (UException e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}

	@Override
	public UserAccountReturn queryFundAccount(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("zycfLoginUser");
		UserAccountReturn ur=new UserAccountReturn();
		try {
			if(user==null)
				throw new UException(SystemEnum.USER_NOLOGIN,"您没有登录");
			
			UserFund userFund = new UserFund();
			userFund.setUserId(user.getId());
	
			UserAccountVo userAccountVo = new UserAccountVo();

		
			userFund = userFundService.queryUserFundByParams(userFund);
			if (userFund != null) 
				userAccountVo.setAvailableAmount(userFund.getAvailableAmount());
			
			UserInfo userInfo = userAccountService.queryUserInfo(user.getId());
			DESTextCipher cipher=DESTextCipher.getDesTextCipher();
			if(userInfo.getIdCode()!=null){
				try {
					
					String cardNo = cipher.decrypt(userInfo.getCardNo());
					cardNo=cardNo.substring(0, 3)+"**********"+cardNo.substring(cardNo.length()-4, cardNo.length());
					userAccountVo.setCardNo(cardNo);
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
				}
				
			}
			if(userInfo.getBankCode()!=null){
				Bank bank=new Bank();
				bank.setBankCode(userInfo.getBankCode());
				bank=bankService.queryByParams(bank);
				userAccountVo.setCardName(bank.getEnName());
			}
			ur.setUserAccountVo(userAccountVo);
			ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"成功"));
		}catch(UException e){
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}

	@Override
	public UserAccountReturn loanRepaymentCompentsate(HttpServletRequest request, Page<LoanRepayment> page) {
		User user = (User) request.getSession().getAttribute("zycfLoginUser");
		UserAccountReturn ur=new UserAccountReturn();
		try {
			if(user==null)
				throw new UException(SystemEnum.USER_NOLOGIN,"您没有登录");
			
			page.getParams().put("compentSateUserId", user.getId());
			
			List<LoanRepayment> res = loanRepaymentService.queryLoanRepaymentsByPage(page);
			for (LoanRepayment loanRepayment : res) {
				loanRepayment.setSourceId(null);
				loanRepayment.setStrStatus(loanRepayment.getStatus().getKey());
				loanRepayment.setStrDueDate(FormatUtils.simpleFormat(loanRepayment.getDueDate()));
			}
			page.setResults(res);
			ur.setTotalPage(page.getTotalPage());
			ur.setPageLoanRepayment(page);
			ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"操作成功"));
		} catch (UException e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}
	
	
}
