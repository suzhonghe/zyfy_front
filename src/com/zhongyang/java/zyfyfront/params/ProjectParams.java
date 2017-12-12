package com.zhongyang.java.zyfyfront.params;

import java.io.Serializable;

import com.zhongyang.java.zyfyfront.pojo.Loan;

/**
 *@package com.zhongyang.java.zyfyfront.params
 *@filename ProjectParams.java
 *@date 20172017年6月21日上午9:52:12
 *@author Administrator
 */
public class ProjectParams implements Serializable{
	
	private Loan loan;

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}
	
}
