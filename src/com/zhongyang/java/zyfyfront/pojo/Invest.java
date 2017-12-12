package com.zhongyang.java.zyfyfront.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.system.enumtype.InvestStatus;
import com.zhongyang.java.system.enumtype.Method;

public class Invest implements Serializable{
	
	private String id;
	
	private String orderId;

    private BigDecimal amount;

    private String loanId;

    private Method repayMethod;
    
    private InvestStatus status;

    private Date submitTime;
    
    private String strSubmitTime;

    private String userId;

    private Integer days;

    private Integer months;

    private Integer years;
    
    private String userName;
    
    private String loanTitle;
    
	public String getStrSubmitTime() {
		return strSubmitTime;
	}

	public void setStrSubmitTime(String strSubmitTime) {
		this.strSubmitTime = strSubmitTime;
	}

	public String getLoanTitle() {
		return loanTitle;
	}

	public void setLoanTitle(String loanTitle) {
		this.loanTitle = loanTitle;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public Method getRepayMethod() {
		return repayMethod;
	}

	public void setRepayMethod(Method repayMethod) {
		this.repayMethod = repayMethod;
	}

	public InvestStatus getStatus() {
		return status;
	}

	public void setStatus(InvestStatus status) {
		this.status = status;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "Invest [id=" + id + ", orderId=" + orderId + ", amount=" + amount + ", loanId=" + loanId
				+ ", repayMethod=" + repayMethod + ", status=" + status + ", submitTime=" + submitTime + ", userId="
				+ userId + ", days=" + days + ", months=" + months + ", years=" + years + ", userName=" + userName
				+ "]";
	}
	
}