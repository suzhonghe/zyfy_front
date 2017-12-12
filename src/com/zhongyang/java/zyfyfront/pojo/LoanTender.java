package com.zhongyang.java.zyfyfront.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 标的账户实体
 *@package com.zhongyang.java.zyfyback.pojo
 *@filename LoanTender.java
 *@date 2017年7月17日上午11:33:09
 *@author suzh
 */
public class LoanTender implements Serializable{

	private String loanId;
	
	private BigDecimal amount;

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
