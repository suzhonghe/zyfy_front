package com.zhongyang.java.zyfyfront.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *@package com.zhongyang.java.zyfyfront.pojo
 *@filename Bank.java
 *@date 2017年7月6日上午11:35:10
 *@author suzh
 */
public class Bank implements Serializable{
	
	private String bankId;
	
	private String bankCode;
	
	private String bankName;
	
	private String bankShortName;
	
	private String enName;
	
	private BigDecimal limitAmount;
	
	private BigDecimal limitTotalAmount;

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankShortName() {
		return bankShortName;
	}

	public void setBankShortName(String bankShortName) {
		this.bankShortName = bankShortName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public BigDecimal getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(BigDecimal limitAmount) {
		this.limitAmount = limitAmount;
	}

	public BigDecimal getLimitTotalAmount() {
		return limitTotalAmount;
	}

	public void setLimitTotalAmount(BigDecimal limitTotalAmount) {
		this.limitTotalAmount = limitTotalAmount;
	}
	
}
