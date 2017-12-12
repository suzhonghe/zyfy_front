package com.zhongyang.java.zyfyfront.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.system.enumtype.LoanRepayMent;

public class InvestRepayment implements Serializable{
	
    private String id;

    private Integer currentPeriod;

    private BigDecimal repayAmount;

    private Date repayDate;

    private LoanRepayMent status;

    private String sourceId;

    private BigDecimal amountInterest;

    private BigDecimal amountOutStanding;

    private BigDecimal amountPrincipal;

    private Date dueDate;

    private String investId;
    
    private String userId;
    
    private String platCust;//PLATCUST
    
    private BigDecimal remarkAmount;//备用金额字段
    
    private String remark;//备用字段
    
    private String loanTittle;//借款标的

	public String getPlatCust() {
		return platCust;
	}

	public void setPlatCust(String platCust) {
		this.platCust = platCust;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(Integer currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public BigDecimal getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(BigDecimal repayAmount) {
		this.repayAmount = repayAmount;
	}

	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	public LoanRepayMent getStatus() {
		return status;
	}

	public void setStatus(LoanRepayMent status) {
		this.status = status;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public BigDecimal getAmountInterest() {
		return amountInterest;
	}

	public void setAmountInterest(BigDecimal amountInterest) {
		this.amountInterest = amountInterest;
	}

	public BigDecimal getAmountOutStanding() {
		return amountOutStanding;
	}

	public void setAmountOutStanding(BigDecimal amountOutStanding) {
		this.amountOutStanding = amountOutStanding;
	}

	public BigDecimal getAmountPrincipal() {
		return amountPrincipal;
	}

	public void setAmountPrincipal(BigDecimal amountPrincipal) {
		this.amountPrincipal = amountPrincipal;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getInvestId() {
		return investId;
	}

	public void setInvestId(String investId) {
		this.investId = investId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getRemarkAmount() {
		return remarkAmount;
	}

	public void setRemarkAmount(BigDecimal remarkAmount) {
		this.remarkAmount = remarkAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLoanTittle() {
		return loanTittle;
	}

	public void setLoanTittle(String loanTittle) {
		this.loanTittle = loanTittle;
	}
	
}