package com.zhongyang.java.bankmanager.params;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 充值回调参数
 *@package com.zhongyang.java.bankmanager.params
 *@filename ChargeCallBack.java
 *@date 2017年7月5日下午1:06:20
 *@author suzh
 */
public class ChargeCallBack implements Serializable{
	
	private String plat_no;//	M	C(32)	商户平台在资金账户管理平台注册的平台编号
	
	private String order_no;//	M	C(50)	订单号
	
	private String platcust;//	M	C(32)	电子账户
	
	private String type;//	M	C(1)	充值类型（1-用户充值 ）
	
	private BigDecimal order_amt;//	M	N(19,2)	订单金额
	
	private String trans_date;//	M	C(10)	订单日期
	
	private String trans_time;//	M	C(8)	订单时间
	
	private String pay_order_no;//	M	C(50)	支付订单号
	
	private String pay_finish_date;//	M	C(10)	支付完成日期
	
	private String pay_finish_time;//	M	C(8)	支付完成时间
	
	private String order_status;//	M	C(1)	订单状态0:处理中, 1:处理成功 2:处理失败
	
	private BigDecimal pay_amt;//	M	N(19,2)	支付金额
	
	private String error_info;//	O	C(40)	失败原因
	
	private String error_no;//	O	C(8)	失败编码
	
	private String host_req_serial_no;
	
	private String signdata;//	M		签名数据

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getOrder_amt() {
		return order_amt;
	}

	public void setOrder_amt(BigDecimal order_amt) {
		this.order_amt = order_amt;
	}

	public String getTrans_date() {
		return trans_date;
	}

	public void setTrans_date(String trans_date) {
		this.trans_date = trans_date;
	}

	public String getTrans_time() {
		return trans_time;
	}

	public void setTrans_time(String trans_time) {
		this.trans_time = trans_time;
	}

	public String getPay_order_no() {
		return pay_order_no;
	}

	public void setPay_order_no(String pay_order_no) {
		this.pay_order_no = pay_order_no;
	}

	public String getPay_finish_date() {
		return pay_finish_date;
	}

	public void setPay_finish_date(String pay_finish_date) {
		this.pay_finish_date = pay_finish_date;
	}

	public String getPay_finish_time() {
		return pay_finish_time;
	}

	public void setPay_finish_time(String pay_finish_time) {
		this.pay_finish_time = pay_finish_time;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public BigDecimal getPay_amt() {
		return pay_amt;
	}

	public void setPay_amt(BigDecimal pay_amt) {
		this.pay_amt = pay_amt;
	}

	public String getError_info() {
		return error_info;
	}

	public void setError_info(String error_info) {
		this.error_info = error_info;
	}

	public String getError_no() {
		return error_no;
	}

	public void setError_no(String error_no) {
		this.error_no = error_no;
	}

	public String getHost_req_serial_no() {
		return host_req_serial_no;
	}

	public void setHost_req_serial_no(String host_req_serial_no) {
		this.host_req_serial_no = host_req_serial_no;
	}

	public String getSigndata() {
		return signdata;
	}

	public void setSigndata(String signdata) {
		this.signdata = signdata;
	}

	@Override
	public String toString() {
		return "ChargeCallBack [plat_no=" + plat_no + ", order_no=" + order_no + ", platcust=" + platcust + ", type="
				+ type + ", order_amt=" + order_amt + ", trans_date=" + trans_date + ", trans_time=" + trans_time
				+ ", pay_order_no=" + pay_order_no + ", pay_finish_date=" + pay_finish_date + ", pay_finish_time="
				+ pay_finish_time + ", order_status=" + order_status + ", pay_amt=" + pay_amt + ", error_info="
				+ error_info + ", error_no=" + error_no + ", host_req_serial_no=" + host_req_serial_no + ", signdata="
				+ signdata + "]";
	}

}
