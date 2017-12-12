package com.zhongyang.java.zyfyfront.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.system.enumtype.LoanStatus;
import com.zhongyang.java.system.enumtype.Method;
/**
 * 
* @Title: ProjectApply.java 
* @Package com.zhongyang.java.vo 
* @Description: 项目申请扩展类
* @author 苏忠贺   
* @date 2015年12月2日 下午12:23:31 
* @version V1.0
 */
public class LoanDetail {
	
	private Integer pageNo;
	
	private Integer pageSize;
	
	private String productId;
	
	private String loanId;
	
	private String loanName;
	
	private String productName;
	
	private Double rate;
	
	private Double addRate;
		
	private BigDecimal amount;
	
	private Integer months;
	
	private Method method;
	
	private double planing;
	
	private BigDecimal suplusAmount;
	
	private BigDecimal bidAmount;
	
	private LoanStatus status;
	
	private Date timeOpen;
	
	private Long divTime;
	
	private BigDecimal minAmount;
	
	private BigDecimal maxAmount;
	
	private BigDecimal stepAmount;
				
	public double getAddRate() {
		return addRate;
	}

	public void setAddRate(double addRate) {
		this.addRate = addRate;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public BigDecimal getStepAmount() {
		return stepAmount;
	}

	public void setStepAmount(BigDecimal stepAmount) {
		this.stepAmount = stepAmount;
	}

	public Long getDivTime() {
		return divTime;
	}

	public void setDivTime(Long divTime) {
		this.divTime = divTime;
	}

	public Date getTimeOpen() {
		return timeOpen;
	}

	public void setTimeOpen(Date timeOpen) {
		this.timeOpen = timeOpen;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public LoanStatus getStatus() {
		return status;
	}

	public void setStatus(LoanStatus status) {
		this.status = status;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public BigDecimal getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(BigDecimal bidAmount) {
		this.bidAmount = bidAmount;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public double getPlaning() {
		return planing;
	}

	public void setPlaning(double planing) {
		this.planing = planing;
	}

	public BigDecimal getSuplusAmount() {
		return suplusAmount;
	}

	public void setSuplusAmount(BigDecimal suplusAmount) {
		this.suplusAmount = suplusAmount;
	}
}
