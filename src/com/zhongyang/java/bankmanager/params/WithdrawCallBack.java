package com.zhongyang.java.bankmanager.params;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *@package com.zhongyang.java.bankmanager.params
 *@filename WithdrawCallBack.java
 *@date 2017年7月18日下午3:47:37
 *@author suzh
 */
public class WithdrawCallBack implements Serializable{
	
	private String plat_no;
	
	private String platcust;
	
	private String order_no;
	
	private BigDecimal order_amt;
	
	private String trans_date;
	
	private String trans_time;
	
	private String pay_order_no;
	
	private String pay_finish_date;
	
	private String pay_finish_time;
	
	private String order_status;
	
	private BigDecimal pay_amt;
	
	private String error_info;
	
	private String error_no;
	
	private String signdata;
	
	private String host_req_serial_no;

	public String getPlat_no() {
		return plat_no;
	}

	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}

	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
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

	public String getSigndata() {
		return signdata;
	}

	public void setSigndata(String signdata) {
		this.signdata = signdata;
	}

	public String getHost_req_serial_no() {
		return host_req_serial_no;
	}

	public void setHost_req_serial_no(String host_req_serial_no) {
		this.host_req_serial_no = host_req_serial_no;
	}

	@Override
	public String toString() {
		return "WithdrawCallBack [plat_no=" + plat_no + ", platcust=" + platcust + ", order_no=" + order_no
				+ ", order_amt=" + order_amt + ", trans_date=" + trans_date + ", trans_time=" + trans_time
				+ ", pay_order_no=" + pay_order_no + ", pay_finish_date=" + pay_finish_date + ", pay_finish_time="
				+ pay_finish_time + ", order_status=" + order_status + ", pay_amt=" + pay_amt + ", error_info="
				+ error_info + ", error_no=" + error_no + ", signdata=" + signdata + ", host_req_serial_no="
				+ host_req_serial_no + "]";
	}
	
}
