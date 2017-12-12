package com.zhongyang.java.bankmanager.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *@package com.zhongyang.java.zyfyfront.pojo
 *@filename BmAccount.java
 *@date 20172017年6月22日上午11:01:03
 *@author suzh
 */
public class BmAccount implements Serializable{
	
	private String id;//`ID` varchar(36) NOT NULL,
	
	private String platcust;//`PLATCUST` varchar(35) DEFAULT NULL COMMENT '用户在资金账户管理平台的电子账户',
	
	private String userName;//`USER_NAME` varchar(50) DEFAULT NULL COMMENT '用户姓名(个人客户必填)',
	
	private String idType;//`ID_TYPE` int(2) DEFAULT '1' COMMENT '证件类型：1是身份证',
	
	private String idCode;//`ID_CODE` varchar(255) DEFAULT NULL COMMENT '身份证号码（个人客户必填）',
	
	private String cardNo;//`CARD_NO` varchar(32) DEFAULT NULL COMMENT '卡号',
	
	private String cardType;//`CARD_TYPE` varchar(2) DEFAULT NULL COMMENT '卡类型(1:借记卡，2:信用卡)',
	
	private String openBranch;//`OPEN_BRANCH` varchar(255) DEFAULT NULL COMMENT '开户行号',
	
	private String preMobile;//`PRE_MOBILE` varchar(255) DEFAULT NULL COMMENT '预留手机号；四要素验证的是银行预留的手机号。',

	private String identifyingCode;	//短信验证码
	
	private Date createTime;
	
	private String remark;//`REARK` varchar(255) DEFAULT NULL COMMENT '备注信息',
	
	private String userId;//`USER_ID` varchar(36) NOT NULL COMMENT '用户ID',
	
	private Boolean open_ii_account;//OPEN_II_ACCOUNT

	public Boolean getOpen_ii_account() {
		return open_ii_account;
	}

	public void setOpen_ii_account(Boolean open_ii_account) {
		this.open_ii_account = open_ii_account;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getOpenBranch() {
		return openBranch;
	}

	public void setOpenBranch(String openBranch) {
		this.openBranch = openBranch;
	}

	public String getPreMobile() {
		return preMobile;
	}

	public void setPreMobile(String preMobile) {
		this.preMobile = preMobile;
	}

	public String getIdentifyingCode() {
		return identifyingCode;
	}

	public void setIdentifyingCode(String identifyingCode) {
		this.identifyingCode = identifyingCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "BmAccount [id=" + id + ", platcust=" + platcust + ", userName=" + userName + ", idType=" + idType
				+ ", idCode=" + idCode + ", cardNo=" + cardNo + ", cardType=" + cardType + ", openBranch=" + openBranch
				+ ", preMobile=" + preMobile + ", identifyingCode=" + identifyingCode + ", remark=" + remark
				+ ", userId=" + userId + "]";
	}

}
