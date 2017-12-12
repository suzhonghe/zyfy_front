package com.zhongyang.java.zyfyfront.vo.useraccount;

import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.system.util.Message;

public class UserAccountVo {

	
	private Message message;
	
    private String userid;

    private BigDecimal availableAmount;

    private BigDecimal depositAmount;

    private BigDecimal dueInAmount;

    private BigDecimal dueOutAmount;

    private BigDecimal frozenAmount;
    
    private BigDecimal freshAmount;

    private Date timecreated;

    private Date timelastupdated;

    private BigDecimal transferAmount;

    private BigDecimal withdrawAmount;

    private BigDecimal undueAmountInterest;
    private BigDecimal undueAmountCapital;
    
    private BigDecimal allRevue;
    private BigDecimal allCapital;
    
    private String cardNo;
    private String cardName;
    
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public BigDecimal getFreshAmount() {
		return freshAmount;
	}

	public void setFreshAmount(BigDecimal freshAmount) {
		this.freshAmount = freshAmount;
	}

	public BigDecimal getAllCapital() {
		return allCapital;
	}

	public void setAllCapital(BigDecimal allCapital) {
		this.allCapital = allCapital;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
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

	public Date getTimecreated() {
		return timecreated;
	}

	public void setTimecreated(Date timecreated) {
		this.timecreated = timecreated;
	}

	public Date getTimelastupdated() {
		return timelastupdated;
	}

	public void setTimelastupdated(Date timelastupdated) {
		this.timelastupdated = timelastupdated;
	}

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}

	public BigDecimal getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(BigDecimal withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	public BigDecimal getUndueAmountInterest() {
		return undueAmountInterest;
	}

	public void setUndueAmountInterest(BigDecimal undueAmountInterest) {
		this.undueAmountInterest = undueAmountInterest;
	}

	public BigDecimal getUndueAmountCapital() {
		return undueAmountCapital;
	}

	public void setUndueAmountCapital(BigDecimal undueAmountCapital) {
		this.undueAmountCapital = undueAmountCapital;
	}

	public BigDecimal getAllRevue() {
		return allRevue;
	}

	public void setAllRevue(BigDecimal allRevue) {
		this.allRevue = allRevue;
	}

	public UserAccountVo() {
		super();
		this.allCapital=new BigDecimal(0);
		this.allRevue=new BigDecimal(0);
		this.availableAmount=new BigDecimal(0);
		this.frozenAmount=new BigDecimal(0);
		this.undueAmountCapital=new BigDecimal(0);
		this.undueAmountInterest = new BigDecimal(0);
	}
    
    
}
