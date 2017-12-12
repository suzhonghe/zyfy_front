package com.zhongyang.java.zyfyfront.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.zhongyang.java.system.enumtype.FundRecordOperation;
import com.zhongyang.java.system.enumtype.FundRecordStatus;
import com.zhongyang.java.system.enumtype.FundRecordType;

/**
 *@package com.zhongyang.java.zyfyfront.pojo
 *@filename LoanTransRecord.java
 *@date 2017年7月14日下午2:58:22
 *@author suzh
 */
public class LoanTransRecord implements Serializable{
	
	private String id;//`ID` varchar(36) NOT NULL,
	
	private String loanId;//`LOAN_ID` varchar(36) NOT NULL,
	  
	private String orderId;//`ORDER_ID` varchar(50) DEFAULT NULL,
	  
	private BigDecimal amount;
	
	private String transDate;//`TRANS_DATE` varchar(20) DEFAULT NULL,
	  
	private String transTime;//`TRANS_TIME` varchar(20) DEFAULT NULL,
	  
	private FundRecordType type;//`TYPE` varchar(20) DEFAULT NULL COMMENT '操作类型',
	  
	private FundRecordStatus status;//`STATUS` varchar(20) DEFAULT NULL,
	  
	private FundRecordOperation opration;//`OPRATION` varchar(20) DEFAULT NULL COMMENT '资金流向',
	  
	private String userId;//`USER_ID` varchar(36) DEFAULT NULL COMMENT '操作人',
	
	private String remark;
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public FundRecordType getType() {
		return type;
	}

	public void setType(FundRecordType type) {
		this.type = type;
	}

	public FundRecordStatus getStatus() {
		return status;
	}

	public void setStatus(FundRecordStatus status) {
		this.status = status;
	}

	public FundRecordOperation getOpration() {
		return opration;
	}

	public void setOpration(FundRecordOperation opration) {
		this.opration = opration;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
