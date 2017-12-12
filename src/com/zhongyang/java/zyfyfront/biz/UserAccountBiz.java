package com.zhongyang.java.zyfyfront.biz;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.pojo.Invest;
import com.zhongyang.java.zyfyfront.pojo.InvestRepayment;
import com.zhongyang.java.zyfyfront.pojo.Loan;
import com.zhongyang.java.zyfyfront.pojo.LoanRepayment;
import com.zhongyang.java.zyfyfront.pojo.User;
import com.zhongyang.java.zyfyfront.returndata.UserAccountReturn;
import com.zhongyang.java.zyfyfront.vo.useraccount.FundRecordAccount;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInvestLoanVo;
import com.zhongyang.java.zyfyfront.vo.useraccount.UserInvestRecordVo;

/**
 *@package com.zhongyang.java.zyfyfront.biz
 *@filename UserAccountBiz.java
 *@date 2017年7月17日下午6:24:11
 *@author suzh
 */
public interface UserAccountBiz {
	
	public UserAccountReturn getUserAccountInfo(HttpServletRequest request);
	
	public UserAccountReturn getUserFundRecord(HttpServletRequest request, Page<FundRecordAccount> page) ;
	
	public UserAccountReturn  selectUserInvestInfo(HttpServletRequest request,Page<UserInvestRecordVo> page);
	
	public UserAccountReturn getUserInvestLoanRecord(HttpServletRequest request,Page<UserInvestLoanVo> page);
		
	public ResponseEntity<byte[]> downLoadContract(String investId); 
	
	public UserAccountReturn getUserSetting(HttpServletRequest request);
	
	public UserAccountReturn loanManagement(HttpServletRequest request, Page<Loan>page);

	public UserAccountReturn loanRepaymentPlant(HttpServletRequest request,Page<LoanRepayment>page);
	
	public UserAccountReturn investRepaymentPlant(HttpServletRequest request,Page<InvestRepayment>page);

	public UserAccountReturn queryInviterInvest(HttpServletRequest request,Page<Invest> page);

	public UserAccountReturn queryInviterList(HttpServletRequest request,Page<User>page);
	
	public UserAccountReturn queryFundAccount(HttpServletRequest request);
	
	public UserAccountReturn loanRepaymentCompentsate(HttpServletRequest request,Page<LoanRepayment>page);
}
