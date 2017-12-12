package com.zhongyang.java.bankmanager.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 退汇回调通知实体
 *@package com.zhongyang.java.bankmanager.entity
 *@filename NotifyRefund.java
 *@date 2017年10月17日下午1:56:48
 *@author suzh
 */
public class NotifyRefund implements Serializable{

	private String id;
	
	private String plat_no;
	
	private String order_no;
	
	private String platcust;
	
	private BigDecimal amt;
	
	private Date createTime;
	
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

	public String getPlat_no() {
		return plat_no;
	}

	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	@Override
	public String toString() {
		return "NotifyRefund [id=" + id + ", plat_no=" + plat_no + ", order_no=" + order_no + ", platcust=" + platcust
				+ ", amt=" + amt + "]";
	}
	
}
