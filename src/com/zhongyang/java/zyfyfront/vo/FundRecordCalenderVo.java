package com.zhongyang.java.zyfyfront.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.system.enumtype.FundRecordStatus;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月4日 下午5:13:05
* 类说明
*/
public class FundRecordCalenderVo {
	    private static final long serialVersionUID = 20131116L;
	    private String id;
	    private String userId;
	    private String type;
	    private FundRecordStatus status;
	    private String statuss;
	    private BigDecimal amount;
	    private Date timeRecorded;
	    private String timeRecordeds;
	    private String startTime;
	    private String endTime;
	    private String dates;//
	    //回款计划中的资金
	    private String amountInterest;
	    private String amountOutStanding;
	    private String amountPrincipal;
	    private String dudate;
        
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
        
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public FundRecordStatus getStatus() {
			return status;
		}
		public void setStatus(FundRecordStatus status) {
			this.status = status;
		}
		public BigDecimal getAmount() {
			return amount;
		}
		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}
		public Date getTimeRecorded() {
			return timeRecorded;
		}
		public void setTimeRecorded(Date timeRecorded) {
			this.timeRecorded = timeRecorded;
		}
		public String getStartTime() {
			return startTime;
		}
		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public String getTimeRecordeds() {
			return timeRecordeds;
		}
		public void setTimeRecordeds(String timeRecordeds) {
			this.timeRecordeds = timeRecordeds;
		}
		public String getStatuss() {
			return statuss;
		}
		public void setStatuss(String statuss) {
			this.statuss = statuss;
		}
		public String getAmountInterest() {
			return amountInterest;
		}
		public void setAmountInterest(String amountInterest) {
			this.amountInterest = amountInterest;
		}
		public String getAmountOutStanding() {
			return amountOutStanding;
		}
		public void setAmountOutStanding(String amountOutStanding) {
			this.amountOutStanding = amountOutStanding;
		}
		public String getAmountPrincipal() {
			return amountPrincipal;
		}
		public void setAmountPrincipal(String amountPrincipal) {
			this.amountPrincipal = amountPrincipal;
		}
		public String getDudate() {
			return dudate;
		}
		public void setDudate(String dudate) {
			this.dudate = dudate;
		}
		public String getDates() {
			return dates;
		}
		public void setDates(String dates) {
			this.dates = dates;
		}
        
        
	
	    
}