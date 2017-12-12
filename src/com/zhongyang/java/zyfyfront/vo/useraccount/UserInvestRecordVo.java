package com.zhongyang.java.zyfyfront.vo.useraccount;

import java.math.BigDecimal;
import java.util.Date;

public class UserInvestRecordVo {

	private BigDecimal talamount;
	private BigDecimal amountInterest;
	private BigDecimal amountPrincipal;
	private String title;
	private String repayStatus;
	private String repayMethod;
	private String dueDate;
	private Date repayDate;
	private String strRepayDate;
	private String investTime;
	private String productName;
	private Integer currentPeriod;
	private String loanStatus;
	private Date timeSettled;
	private Integer days;
	private Integer months;
	private Integer years;
	private String userId;
	private String investId;
	

	public BigDecimal getTalamount() {
		return talamount;
	}
	public void setTalamount(BigDecimal talamount) {
		this.talamount = talamount;
	}
	public String getInvestTime() {
		return investTime;
	}
	public void setInvestTime(String investTime) {
		this.investTime = investTime;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getInvestId() {
		return investId;
	}
	public void setInvestId(String investId) {
		this.investId = investId;
	}
	public String getStrRepayDate() {
		return strRepayDate;
	}
	public void setStrRepayDate(String strRepayDate) {
		this.strRepayDate = strRepayDate;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public BigDecimal getAmountInterest() {
		return amountInterest;
	}
	public void setAmountInterest(BigDecimal amountInterest) {
		this.amountInterest = amountInterest;
	}
	public BigDecimal getAmountPrincipal() {
		return amountPrincipal;
	}
	public void setAmountPrincipal(BigDecimal amountPrincipal) {
		this.amountPrincipal = amountPrincipal;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRepayStatus() {
		return repayStatus;
	}
	public void setRepayStatus(String repayStatus) {
		this.repayStatus = repayStatus;
	}
	public String getRepayMethod() {
		return repayMethod;
	}
	public void setRepayMethod(String repayMethod) {
		this.repayMethod = repayMethod;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public Date getRepayDate() {
		return repayDate;
	}
	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}
	public Integer getCurrentPeriod() {
		return currentPeriod;
	}
	public void setCurrentPeriod(Integer currentPeriod) {
		this.currentPeriod = currentPeriod;
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
