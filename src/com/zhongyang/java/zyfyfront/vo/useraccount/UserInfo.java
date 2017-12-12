package com.zhongyang.java.zyfyfront.vo.useraccount;

import java.io.Serializable;
import java.util.Date;

/**
 *@package com.zhongyang.java.zyfyfront.vo.useraccount
 *@filename UserInfo.java
 *@date 2017年7月18日上午9:40:01
 *@author suzh
 */
public class UserInfo implements Serializable{

    private Boolean enabled;

    private Date lastLoginDate;
    
    private Date lastModifyDate;
    
    private String loginName;
    
    private String mobile;
    
    private String refMobile;
    
    private int userType;
    
	private String userName;
	
	private String idCode;
	
	private String cardNo;
	
	private String userId;
	
	private String bankCode;
	
	private String bankEnName;
	
	private String platcust;
	
	private String custType;
	
	private Boolean open_ii_account;
					
	public Boolean getOpen_ii_account() {
		return open_ii_account;
	}

	public void setOpen_ii_account(Boolean open_ii_account) {
		this.open_ii_account = open_ii_account;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}

	public String getBankEnName() {
		return bankEnName;
	}

	public void setBankEnName(String bankEnName) {
		this.bankEnName = bankEnName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRefMobile() {
		return refMobile;
	}

	public void setRefMobile(String refMobile) {
		this.refMobile = refMobile;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
