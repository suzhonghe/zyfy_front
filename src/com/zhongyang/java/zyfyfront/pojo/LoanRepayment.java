package com.zhongyang.java.zyfyfront.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.system.enumtype.LoanRepayMent;
import com.zhongyang.java.system.enumtype.RepaySource;

public class LoanRepayment implements Serializable{
	
    private String id;

    private Integer currentPeriod;

    private BigDecimal repayAmount;

    private Date repayDate;

    private LoanRepayMent status;
    
    private String strStatus;

    private String sourceId;

    private BigDecimal amountInterest;

    private BigDecimal amountOutStanding;

    private BigDecimal amountPrincipal;

    private Date dueDate;
    
    private String strDueDate;

    private String loanId;
    
    private boolean flag;//是否提前还款，true提前还款，false不提前还款
    
    private String loanTitle;
    
    private Boolean isRepay;
    
    private RepaySource repaySource;//还款情况：代偿（委托）还款
    
	public RepaySource getRepaySource() {
		return repaySource;
	}

	public void setRepaySource(RepaySource repaySource) {
		this.repaySource = repaySource;
	}

	public String getStrDueDate() {
		return strDueDate;
	}

	public void setStrDueDate(String strDueDate) {
		this.strDueDate = strDueDate;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public Boolean getIsRepay() {
		return isRepay;
	}

	public void setIsRepay(Boolean isRepay) {
		this.isRepay = isRepay;
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

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getLoanTitle() {
		return loanTitle;
	}

	public void setLoanTitle(String loanTitle) {
		this.loanTitle = loanTitle;
	}
    
}