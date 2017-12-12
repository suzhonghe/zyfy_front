package com.zhongyang.java.zyfyfront.returndata;

import java.io.Serializable;
import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.zyfyfront.pojo.Invest;
import com.zhongyang.java.zyfyfront.pojo.InvestRepayment;
import com.zhongyang.java.zyfyfront.pojo.Loan;
import com.zhongyang.java.zyfyfront.pojo.LoanRepayment;
import com.zhongyang.java.zyfyfront.pojo.User;
import com.zhongyang.java.zyfyfront.vo.FundRecordCalenderVo;
import com.zhongyang.java.zyfyfront.vo.useraccount.FundRecordAccount;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserAccountVo;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInfo;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInvestLoanVo;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInvestRecordVo;

/**
 *@package com.zhongyang.java.zyfyfront.returndata
 *@filename UserAccountReturn.java
 *@date 2017年7月17日下午6:38:23
 *@author suzh
 */
public class UserAccountReturn implements Serializable{
	
	private int totalPage;
	
	private Message message;
	
	private UserAccountVo userAccountVo;
	
	private Page<FundRecordAccount> pageRecords; 
	
	private Page<UserInvestRecordVo>userInvestRecordVo;
	
	private Page<UserInvestLoanVo>userInvestLoanVo;
	
	private UserInfo userInfo;
	
	private Page<Loan>pageLoan;
	
	private Page<LoanRepayment> pageLoanRepayment;
	
	private Page<InvestRepayment>pageInvestRepayment;
	
	private Page<Invest>pageInvest;
	
	private Page<User>pageUser;
	
	private List<FundRecordCalenderVo>fundRecordCalenderVo;
	
	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<FundRecordCalenderVo> getFundRecordCalenderVo() {
		return fundRecordCalenderVo;
	}

	public void setFundRecordCalenderVo(List<FundRecordCalenderVo> fundRecordCalenderVo) {
		this.fundRecordCalenderVo = fundRecordCalenderVo;
	}

	public Page<User> getPageUser() {
		return pageUser;
	}

	public void setPageUser(Page<User> pageUser) {
		this.pageUser = pageUser;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public UserAccountVo getUserAccountVo() {
		return userAccountVo;
	}

	public void setUserAccountVo(UserAccountVo userAccountVo) {
		this.userAccountVo = userAccountVo;
	}

	public Page<FundRecordAccount> getPageRecords() {
		return pageRecords;
	}

	public void setPageRecords(Page<FundRecordAccount> pageRecords) {
		this.pageRecords = pageRecords;
	}

	public Page<UserInvestRecordVo> getUserInvestRecordVo() {
		return userInvestRecordVo;
	}

	public void setUserInvestRecordVo(Page<UserInvestRecordVo> userInvestRecordVo) {
		this.userInvestRecordVo = userInvestRecordVo;
	}

	public Page<UserInvestLoanVo> getUserInvestLoanVo() {
		return userInvestLoanVo;
	}

	public void setUserInvestLoanVo(Page<UserInvestLoanVo> userInvestLoanVo) {
		this.userInvestLoanVo = userInvestLoanVo;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Page<Loan> getPageLoan() {
		return pageLoan;
	}

	public void setPageLoan(Page<Loan> pageLoan) {
		this.pageLoan = pageLoan;
	}

	public Page<LoanRepayment> getPageLoanRepayment() {
		return pageLoanRepayment;
	}

	public void setPageLoanRepayment(Page<LoanRepayment> pageLoanRepayment) {
		this.pageLoanRepayment = pageLoanRepayment;
	}

	public Page<InvestRepayment> getPageInvestRepayment() {
		return pageInvestRepayment;
	}

	public void setPageInvestRepayment(Page<InvestRepayment> pageInvestRepayment) {
		this.pageInvestRepayment = pageInvestRepayment;
	}

	public Page<Invest> getPageInvest() {
		return pageInvest;
	}

	public void setPageInvest(Page<Invest> pageInvest) {
		this.pageInvest = pageInvest;
	}
	
}
