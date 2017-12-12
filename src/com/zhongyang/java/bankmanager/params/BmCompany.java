package com.zhongyang.java.bankmanager.params;

/**
 *@package com.zhongyang.java.bankmanager.params
 *@filename BmCompany.java
 *@date 2017年8月16日下午4:35:14
 *@author suzh
 */
public class BmCompany {

	private String detail_no;
	
	private String org_name;//'企业名称',
	
	private String business_license;// '营业执照编号',
	
	private String bank_license;//'社会信用代码证',
	
	private String acct_name;//'对公账户名称',
	
	private String acct_no;//对公账户
	
	private String open_branch;//开户银行
	
	private String cus_type;
	
	private String mobile;
	
	private String org_no;//组织机构代码
				
	public String getOrg_no() {
		return org_no;
	}

	public void setOrg_no(String org_no) {
		this.org_no = org_no;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCus_type() {
		return cus_type;
	}

	public void setCus_type(String cus_type) {
		this.cus_type = cus_type;
	}

	public String getDetail_no() {
		return detail_no;
	}

	public void setDetail_no(String detail_no) {
		this.detail_no = detail_no;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getBusiness_license() {
		return business_license;
	}

	public void setBusiness_license(String business_license) {
		this.business_license = business_license;
	}

	public String getBank_license() {
		return bank_license;
	}

	public void setBank_license(String bank_license) {
		this.bank_license = bank_license;
	}

	public String getAcct_name() {
		return acct_name;
	}

	public void setAcct_name(String acct_name) {
		this.acct_name = acct_name;
	}

	public String getAcct_no() {
		return acct_no;
	}

	public void setAcct_no(String acct_no) {
		this.acct_no = acct_no;
	}

	public String getOpen_branch() {
		return open_branch;
	}

	public void setOpen_branch(String open_branch) {
		this.open_branch = open_branch;
	}
}
