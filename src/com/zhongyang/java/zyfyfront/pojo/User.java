package com.zhongyang.java.zyfyfront.pojo;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
	
	private String id;

    private Boolean enabled;

    private Date lastLoginDate;
    
    private String strLastLoginDate;
    
    private Date lastModifyDate;
    
    private String loginName;
    
    private String mobile;
    
    private String passphrase;
    
    private Date registerDate;
    
    private String strRegisterDate;
    
    private String referralId;
    
    private String salt;
    
    private Integer userType;
    
    private Integer custType;
    
    private Date allowTime; //允许登录时间
    
    private String userName;
    
    private String idCode;//身份证号
    
    private Date birthDate;
        
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getCustType() {
		return custType;
	}

	public void setCustType(Integer custType) {
		this.custType = custType;
	}

	public String getStrLastLoginDate() {
		return strLastLoginDate;
	}

	public void setStrLastLoginDate(String strLastLoginDate) {
		this.strLastLoginDate = strLastLoginDate;
	}

	public String getStrRegisterDate() {
		return strRegisterDate;
	}

	public void setStrRegisterDate(String strRegisterDate) {
		this.strRegisterDate = strRegisterDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getPassphrase() {
		return passphrase;
	}

	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerdate) {
		this.registerDate = registerdate;
	}

	public String getReferralId() {
		return referralId;
	}

	public void setReferralId(String referralId) {
		this.referralId = referralId;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Date getAllowTime() {
		return allowTime;
	}

	public void setAllowTime(Date allowTime) {
		this.allowTime = allowTime;
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

}