package com.zhongyang.java.bankmanager.entity;

import java.io.Serializable;
import java.util.Date;

import com.zhongyang.java.system.enumtype.OrderStatus;

/**
 *@package com.zhongyang.java.bankmanager.entity
 *@filename BindCardRecord.java
 *@date 2017年8月17日上午10:42:42
 *@author suzh
 */
public class BindCardRecord implements Serializable{
	
	private String id;//`ID` varchar(36) NOT NULL,
	
	private String acct_name;//`ACCT_NAME` varchar(255) DEFAULT NULL COMMENT '对公账户名称',
	
	private String acct_no;//`ACCT_NO` varchar(50) DEFAULT NULL,
	
	private Date createTime;//`CREATETIME` datetime DEFAULT NULL COMMENT '创建时间',
	
	private String userId;//`USER_ID
	
	private String org_no;
	
	private String open_branch;
	
	private OrderStatus status;
	
	private String orderId;
	
	private String remark;
	
	private String preMobile;
		
	public String getPreMobile() {
		return preMobile;
	}

	public void setPreMobile(String preMobile) {
		this.preMobile = preMobile;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getOrg_no() {
		return org_no;
	}

	public void setOrg_no(String org_no) {
		this.org_no = org_no;
	}

	public String getOpen_branch() {
		return open_branch;
	}

	public void setOpen_branch(String open_branch) {
		this.open_branch = open_branch;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
