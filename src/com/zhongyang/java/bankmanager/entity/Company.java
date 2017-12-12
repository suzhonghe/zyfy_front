package com.zhongyang.java.bankmanager.entity;

import java.io.Serializable;
import java.util.Date;

import com.zhongyang.java.system.enumtype.OrderStatus;

/**
 * 企业用户
 *@package com.zhongyang.java.zyfyfront.pojo
 *@filename Company.java
 *@date 2017年8月16日下午3:54:59
 *@author suzh
 */
public class Company implements Serializable{
	
	private String id;//`ID` varchar(36) NOT NULL,
	
	private String org_name;//`ORG_NAME` varchar(100) DEFAULT NULL COMMENT '企业名称',
	
	private String business_license;//`BUSINESS_LICENSE` varchar(100) DEFAULT NULL COMMENT '营业执照编号',
	
	private String bank_license;//`BANK_LICENSE` varchar(100) DEFAULT NULL COMMENT '社会信用代码证',
	
	private String acct_name;//`ACCT_NAME` varchar(255) DEFAULT NULL COMMENT '对公账户名称',
	
	private String acct_no;//`ACCT_NO` varchar(50) DEFAULT NULL,
	
	private Date createTime;//`CREATETIME` datetime DEFAULT NULL COMMENT '创建时间',
	
	private String userId;//`USER_ID
	
	private String org_no;
	
	private String platcust;
	
	private String open_branch;

	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}

	public String getOpen_branch() {
		return open_branch;
	}

	public void setOpen_branch(String open_branch) {
		this.open_branch = open_branch;
	}

	public String getOrg_no() {
		return org_no;
	}

	public void setOrg_no(String org_no) {
		this.org_no = org_no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
