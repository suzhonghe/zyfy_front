package com.zhongyang.java.zyfyfront.vo.useraccount;

import java.math.BigDecimal;
import java.util.Date;

public class UserInvestLoanVo {

	private BigDecimal amount;
	private String investId;
	private String title;
	private String investStatus;
	private Integer days;
	private Integer months;
	private Integer years;
	private String repayMethod;
	private String loanStatus;
	private Date timeSettled;
	private String strTimeSettled;
	private String loanId;
	private BigDecimal rate;
	private Double addRate;
	private BigDecimal dueInterest;
	private String investTime;
	private String productName;
	private boolean isDel;
	
	public boolean isDel() {
		return isDel;
	}
	public void setDel(boolean isDel) {
		this.isDel = isDel;
	}
	public String getInvestId() {
		return investId;
	}
	public void setInvestId(String investId) {
		this.investId = investId;
	}
	public String getInvestStatus() {
		return investStatus;
	}
	public void setInvestStatus(String investStatus) {
		this.investStatus = investStatus;
	}
	
	public String getStrTimeSettled() {
		return strTimeSettled;
	}
	public void setStrTimeSettled(String strTimeSettled) {
		this.strTimeSettled = strTimeSettled;
	}
	
	public Double getAddRate() {
		return addRate;
	}
	public void setAddRate(Double addRate) {
		this.addRate = addRate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getInvestTime() {
		return investTime;
	}
	public void setInvestTime(String investTime) {
		this.investTime = investTime;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}


	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getDueInterest() {
		return dueInterest;
	}
	public void setDueInterest(BigDecimal dueInterest) {
		this.dueInterest = dueInterest;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getRepayMethod() {
		return repayMethod;
	}
	public void setRepayMethod(String repayMethod) {
		this.repayMethod = repayMethod;
	}

	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public Date getTimeSettled() {
		return timeSettled;
	}
	public void setTimeSettled(Date timeSettled) {
		this.timeSettled = timeSettled;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public Integer getMonths() {
		return months;
	}
	public void setMonths(Integer months) {
		this.months = months;
	}
	public Integer getYears() {
		return years;
	}
	public void setYears(Integer years) {
		this.years = years;
	}
}
