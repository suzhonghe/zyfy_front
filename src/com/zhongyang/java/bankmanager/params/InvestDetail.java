package com.zhongyang.java.bankmanager.params;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *@package com.zhongyang.java.bankmanager.params
 *@filename InvestDetail.java
 *@date 2017年7月6日下午4:52:02
 *@author suzh
 */
public class InvestDetail implements Serializable{

	private String detail_no;
	
	private String platcust;

	private BigDecimal trans_amt;
	
	private String subject_priority="1";
	
	private String remark;
	
	private BigDecimal self_amt;
	
	private BigDecimal coupon_amt;
	
	private BigDecimal experience_amt;
	
	private BigDecimal in_interest;
	
	private Commission commission;
		
	public BigDecimal getCoupon_amt() {
		return coupon_amt;
	}

	public void setCoupon_amt(BigDecimal coupon_amt) {
		this.coupon_amt = coupon_amt;
	}

	public BigDecimal getExperience_amt() {
		return experience_amt;
	}

	public void setExperience_amt(BigDecimal experience_amt) {
		this.experience_amt = experience_amt;
	}

	public BigDecimal getIn_interest() {
		return in_interest;
	}

	public void setIn_interest(BigDecimal in_interest) {
		this.in_interest = in_interest;
	}

	public Commission getCommission() {
		return commission;
	}

	public void setCommission(Commission commission) {
		this.commission = commission;
	}

	public BigDecimal getSelf_amt() {
		return self_amt;
	}

	public void setSelf_amt(BigDecimal self_amt) {
		this.self_amt = self_amt;
	}

	public String getDetail_no() {
		return detail_no;
	}

	public void setDetail_no(String detail_no) {
		this.detail_no = detail_no;
	}

	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}

	public BigDecimal getTrans_amt() {
		return trans_amt;
	}

	public void setTrans_amt(BigDecimal trans_amt) {
		this.trans_amt = trans_amt;
	}

	public String getSubject_priority() {
		return subject_priority;
	}

	public void setSubject_priority(String subject_priority) {
		this.subject_priority = subject_priority;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "InvestDetail [detail_no=" + detail_no + ", platcust=" + platcust + ", trans_amt=" + trans_amt
				+ ", subject_priority=" + subject_priority + ", remark=" + remark + "]";
	}

}
