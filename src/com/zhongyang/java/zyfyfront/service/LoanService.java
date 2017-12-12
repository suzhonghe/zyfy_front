package com.zhongyang.java.zyfyfront.service;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.pojo.Loan;
/**
 * 
 * @author Administrator
 *
 */
public interface LoanService {
	/**
	 * 分页查询标的信息
	 * @param page
	 * @return
	 */
	public List<Loan> queryLoansByPage(Page<Loan> page);
	
	/**
	 * 根据参数查询标的信息
	 * @param loan
	 * @return
	 */
	public Loan queryLoanByParams(Loan loan);
	
	public List<Loan> queryLoanManage(Page<Loan> page);
	
	public int modifyLoanByParams(Loan loan)throws Exception;
}
