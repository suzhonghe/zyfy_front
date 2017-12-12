package com.zhongyang.java.zyfyfront.params;

public class RegisteredParams {
	
	private String mobile;
	
	private String passphrase;
	
	private Integer userType;
	
	private String referralMobile;
	
	private String regcode;

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

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getReferralMobile() {
		return referralMobile;
	}

	public void setReferralMobile(String referralMobile) {
		this.referralMobile = referralMobile;
	}

	public String getRegcode() {
		return regcode;
	}

	public void setRegcode(String regcode) {
		this.regcode = regcode;
	}

	@Override
	public String toString() {
		return "RegisteredParams [mobile=" + mobile + ", passphrase=" + passphrase + ", userType=" + userType
				+ ", referralMobile=" + referralMobile + ", regcode=" + regcode + "]";
	}
	
}
