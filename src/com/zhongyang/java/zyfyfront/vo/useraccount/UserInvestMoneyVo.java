package com.zhongyang.java.zyfyfront.vo.useraccount;

import java.math.BigDecimal;

public class UserInvestMoneyVo {
	
	private String userid;
    
	private BigDecimal amountInterest;
    
	private BigDecimal amountCapital;

    private String status;

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public BigDecimal getAmountInterest() {
		return amountInterest;
	}
	public void setAmountInterest(BigDecimal amountInterest) {
		this.amountInterest = amountInterest;
	}
	public BigDecimal getAmountCapital() {
		return amountCapital;
	}
	public void setAmountCapital(BigDecimal amountCapital) {
		this.amountCapital = amountCapital;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
