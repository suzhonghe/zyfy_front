package com.zhongyang.java.zyfyfront.params;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.pojo.Loan;

public class LoanParams {
	
	private Loan loan;
	
	private Page<Loan>page;

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public Page<Loan> getPage() {
		return page;
	}

	public void setPage(Page<Loan> page) {
		this.page = page;
	}

}
