package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.pojo.Loan;

/**
 * 
* @Title: LoanDao.java 
* @Package com.zhongyang.java.dao 
* @Description: 标的DAO 
* @author 苏忠贺   
* @date 2015年12月3日 下午3:46:27 
* @version V1.0
 */
public interface LoanDao {
	
	/**
	 * 分页查询标的信息
	 * @param page
	 * @return
	 */
	public List<Loan> selectLoansByPage(Page<Loan> page);
	/**
	 * 根据参数查询标的信息
	 * @param loan
	 * @return
	 */
	public Loan selectLoanByPrams(Loan loan);
	
	public List<Loan> selectLoanManage(Page<Loan> page);
	
	public int updateLoanByParams(Loan loan);
}
