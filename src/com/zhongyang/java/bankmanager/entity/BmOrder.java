package com.zhongyang.java.bankmanager.entity;

import java.io.Serializable;

import com.zhongyang.java.system.enumtype.OrderStatus;
import com.zhongyang.java.system.enumtype.OrderType;

/**
 *@package com.zhongyang.java.bankmanager.entity
 *@filename BmAccountOrder.java
 *@date 20172017年6月26日下午4:35:34
 *@author suzh
 */
public class BmOrder implements Serializable{
	
	private String id;//`ID` varchar(36) NOT NULL,
	  
	private String orderId;//`ORDER_ID` varchar(50) NOT NULL,
	
	private String orderDate;//`PARTNER_TRANS_DATE` date NOT NULL COMMENT '订单日期',
	
	private String orderTime;//`PARTNER_TRANS_TIME` time NOT NULL COMMENT '订单时间',
	
	private OrderType orderType;//`ORDER_TYPE` varchar(50) NOT NULL COMMENT '订单类型',
	
	private OrderStatus orderStatus;//`ORDER_STATUS` varchar(50) NOT NULL COMMENT '订单状态',
	
	private String record;//`RECODE` varchar(20) NOT NULL COMMENT '返回码',
	
	private String remsg;//`REMSG` varchar(255) NOT NULL COMMENT '返回信息',
	
	private String userId;//`USER_ID` varchar(36) NOT NULL COMMENT '用户ID'
	
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getRemsg() {
		return remsg;
	}

	public void setRemsg(String remsg) {
		this.remsg = remsg;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "BmAccountOrder [id=" + id + ", orderId=" + orderId + ", orderDate=" + orderDate + ", orderTime="
				+ orderTime + ", orderType=" + orderType + ", orderStatus=" + orderStatus + ", record=" + record
				+ ", remsg=" + remsg + ", userId=" + userId + ", remark=" + remark + "]";
	}

	
}
