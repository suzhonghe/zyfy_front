package com.zhongyang.java.zyfyfront.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class UserFund {
	
	private String id;
	
    private String userId;

    private BigDecimal availableAmount;//可用金额

    private BigDecimal depositAmount;//充值

    private BigDecimal dueInAmount;//待收

    private BigDecimal dueOutAmount;//待发

    private BigDecimal frozenAmount;//冻结

    private Date timeCreated;

    private Date timeLastUpdate;

    private BigDecimal withdrawAmount;//提现总额

    private String ufStatus;
    
    private BigDecimal allRevenu;//用户总收益
    
    private BigDecimal dueRevenu;//用户预期收益

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(BigDecimal availableAmount) {
		this.availableAmount = availableAmount;
	}

	public BigDecimal getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}

	public BigDecimal getDueInAmount() {
		return dueInAmount;
	}

	public void setDueInAmount(BigDecimal dueInAmount) {
		this.dueInAmount = dueInAmount;
	}

	public BigDecimal getDueOutAmount() {
		return dueOutAmount;
	}

	public void setDueOutAmount(BigDecimal dueOutAmount) {
		this.dueOutAmount = dueOutAmount;
	}

	public BigDecimal getFrozenAmount() {
		return frozenAmount;
	}

	public void setFrozenAmount(BigDecimal frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	public Date getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}

	public BigDecimal getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(BigDecimal withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	public String getUfStatus() {
		return ufStatus;
	}

	public void setUfStatus(String ufStatus) {
		this.ufStatus = ufStatus;
	}

	public Date getTimeLastUpdate() {
		return timeLastUpdate;
	}

	public void setTimeLastUpdate(Date timeLastUpdate) {
		this.timeLastUpdate = timeLastUpdate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getAllRevenu() {
		return allRevenu;
	}

	public void setAllRevenu(BigDecimal allRevenu) {
		this.allRevenu = allRevenu;
	}

	public BigDecimal getDueRevenu() {
		return dueRevenu;
	}

	public void setDueRevenu(BigDecimal dueRevenu) {
		this.dueRevenu = dueRevenu;
	}

}