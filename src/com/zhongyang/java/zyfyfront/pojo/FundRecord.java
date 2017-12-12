package com.zhongyang.java.zyfyfront.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.system.enumtype.FundRecordOperation;
import com.zhongyang.java.system.enumtype.FundRecordStatus;
import com.zhongyang.java.system.enumtype.FundRecordType;

public class FundRecord {
	
    private String id;

    private BigDecimal amount;//金额
    
    private BigDecimal aviAmount;//交易后余额

    private String description;//描述信息

    private FundRecordOperation operation;//操作

    private String orderId;//交易订单号

    private FundRecordStatus status;//资金状态

    private Date timeRecorded;//记录时间

    private FundRecordType type;//资金记录类型

    private String userId;//用户ID	

    private String payOrderNo;//`PAY_ORDER_NO` varchar(50) DEFAULT NULL COMMENT '存管系统支付订单号',
    
    private String payFinishDate;//`PAY_FINISH_DATE` varchar(20) DEFAULT NULL COMMENT '存管系统订单支付订单完成日期',
    
    private String payFinishTime;//`PAY_FINISH_TIME` varchar(20) DEFAULT NULL COMMENT '存管系统支付订单完成时间',
    
    private String payOrderStatus;//`PAY_ORDER_STATUS` varchar(20) DEFAULT NULL COMMENT '存管系统支付订单状态',
    
    private BigDecimal payAmt;//`PAY_AMT` decimal(15,2) DEFAULT NULL COMMENT '存管系统支付订单金额',
    
    private String errorInfo;//`ERROR_INFO` varchar(255) DEFAULT NULL COMMENT '存管系统支付订单失败原因',
    
    private String errorNo;//`ERROR_NO` varchar(8) DEFAULT NULL COMMENT '存管系统支付订单错误编码',
    
    private String hostReqSerialNo;//`HOST_REQ_SERIAL_NO` varchar(55) DEFAULT NULL COMMENT '支付通道流水号',

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAviAmount() {
		return aviAmount;
	}

	public void setAviAmount(BigDecimal aviAmount) {
		this.aviAmount = aviAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FundRecordOperation getOperation() {
		return operation;
	}

	public void setOperation(FundRecordOperation operation) {
		this.operation = operation;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public FundRecordStatus getStatus() {
		return status;
	}

	public void setStatus(FundRecordStatus status) {
		this.status = status;
	}

	public Date getTimeRecorded() {
		return timeRecorded;
	}

	public void setTimeRecorded(Date timeRecorded) {
		this.timeRecorded = timeRecorded;
	}

	public FundRecordType getType() {
		return type;
	}

	public void setType(FundRecordType type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}

	public String getPayFinishDate() {
		return payFinishDate;
	}

	public void setPayFinishDate(String payFinishDate) {
		this.payFinishDate = payFinishDate;
	}

	public String getPayFinishTime() {
		return payFinishTime;
	}

	public void setPayFinishTime(String payFinishTime) {
		this.payFinishTime = payFinishTime;
	}

	public String getPayOrderStatus() {
		return payOrderStatus;
	}

	public void setPayOrderStatus(String payOrderStatus) {
		this.payOrderStatus = payOrderStatus;
	}

	public BigDecimal getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public String getErrorNo() {
		return errorNo;
	}

	public void setErrorNo(String errorNo) {
		this.errorNo = errorNo;
	}

	public String getHostReqSerialNo() {
		return hostReqSerialNo;
	}

	public void setHostReqSerialNo(String hostReqSerialNo) {
		this.hostReqSerialNo = hostReqSerialNo;
	}
}