package com.zhongyang.java.zyfyfront.vo.useraccount;

import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.system.enumtype.LoanStatus;

public class LoanVo {
    
	private String id;//ID varchar(36) not null
	private BigDecimal amount;//   AMOUNT   int(11) comment '贷款金额'
	private String method;//   METHOD    varchar(255) comment '还款方式'
	private BigDecimal rate;//   RATE   int(11) comment '借款费率'
	private LoanStatus status;//   STATUS    varchar(255) comment '借款状态'
	private String title;//   TITLE    varchar(255) default NULL comment '借款标标题'
	private int loanTime;//   LOANTIME in(11) default '10' comment '标的有效期（天）'
	private String allStatus;//   STATUS    varchar(255) comment '借款状态'
	private Date startTime;//   TITLE    varchar(255) default NULL comment '借款标标题'
	private Date endTime;//   LOANTIME in(11) default '10' comment '标的有效期（天）'
	private String startTimes;//   TITLE    varchar(255) default NULL comment '借款标标题'
	private String endTimes;//   LOANTIME in(11) default '10' comment '标的有效期（天）'
	private String loanUserId;//   LOANTIME in(11) default '10' comment '标的有效期（天）'
	private String strStatus;//   STATUS    varchar(255) comment '借款状态'
	private String timeOpen;
	private Integer months;//   MONTHS  int(11) default NULL comment '借款期限(月)'
	private Double addRate;
	
	public Double getAddRate() {
		return addRate;
	}
	public void setAddRate(Double addRate) {
		this.addRate = addRate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	public LoanStatus getStatus() {
		return status;
	}
	public void setStatus(LoanStatus status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getLoanTime() {
		return loanTime;
	}
	public void setLoanTime(int loanTime) {
		this.loanTime = loanTime;
	}
	public String getAllStatus() {
		return allStatus;
	}
	public void setAllStatus(String allStatus) {
		this.allStatus = allStatus;
	}

	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getLoanUserId() {
		return loanUserId;
	}
	public void setLoanUserId(String loanUserId) {
		this.loanUserId = loanUserId;
	}
	public String getStrStatus() {
		return strStatus;
	}
	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}
	public String getTimeOpen() {
		return timeOpen;
	}
	public void setTimeOpen(String timeOpen) {
		this.timeOpen = timeOpen;
	}
	public String getStartTimes() {
		return startTimes;
	}
	public void setStartTimes(String startTimes) {
		this.startTimes = startTimes;
	}
	public String getEndTimes() {
		return endTimes;
	}
	public void setEndTimes(String endTimes) {
		this.endTimes = endTimes;
	}
	public Integer getMonths() {
		return months;
	}
	public void setMonths(Integer months) {
		this.months = months;
	}
	
	
    
	
	
}
