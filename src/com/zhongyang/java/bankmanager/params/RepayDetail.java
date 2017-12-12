package com.zhongyang.java.bankmanager.params;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *@package com.zhongyang.java.bankmanager.params
 *@filename RepayDetail.java
 *@date 2017年7月18日下午7:43:52
 *@author suzh
 */
public class RepayDetail implements Serializable{
	
	private String detail_no;//	M	C(30)	明细编号
	
	private String prod_id;//	M	C(50)	标的编号
	 
	private Integer repay_num;//	M	N(9)	还款期数，如果一次性还款，repay_num为1
	 
	private String repay_date;//	M	D	计划还款日期(yyyyMMdd)
	 
	private BigDecimal repay_amt;//	M	N(19,2)	计划还款金额
	 
	private String real_repay_date;//	M	D	实际还款日期(yyyyMMdd)
	 
	private BigDecimal real_repay_amt;//	M	N(19,2)	实际还款金额
	 
	private String platcust;//	M	C(32)	平台客户编号（借款人）
	 
	private BigDecimal trans_amt;//	M	N(19,2)	交易金额（实际还款金额+手续费金额）
	
	private BigDecimal fee_amt;//	O	N(19,2)	手续费金额

	public String getDetail_no() {
		return detail_no;
	}

	public void setDetail_no(String detail_no) {
		this.detail_no = detail_no;
	}

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public Integer getRepay_num() {
		return repay_num;
	}

	public void setRepay_num(Integer repay_num) {
		this.repay_num = repay_num;
	}

	public String getRepay_date() {
		return repay_date;
	}

	public void setRepay_date(String repay_date) {
		this.repay_date = repay_date;
	}

	public BigDecimal getRepay_amt() {
		return repay_amt;
	}

	public void setRepay_amt(BigDecimal repay_amt) {
		this.repay_amt = repay_amt;
	}

	public String getReal_repay_date() {
		return real_repay_date;
	}

	public void setReal_repay_date(String real_repay_date) {
		this.real_repay_date = real_repay_date;
	}

	public BigDecimal getReal_repay_amt() {
		return real_repay_amt;
	}

	public void setReal_repay_amt(BigDecimal real_repay_amt) {
		this.real_repay_amt = real_repay_amt;
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

	public BigDecimal getFee_amt() {
		return fee_amt;
	}

	public void setFee_amt(BigDecimal fee_amt) {
		this.fee_amt = fee_amt;
	}
	
}
