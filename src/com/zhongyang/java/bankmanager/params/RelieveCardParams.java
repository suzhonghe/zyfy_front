package com.zhongyang.java.bankmanager.params;

/**
 *@package com.zhongyang.java.bankmanager.params
 *@filename RelieveCardParams.java
 *@date 2017年7月20日上午9:29:25
 *@author suzh
 */
public class RelieveCardParams {
	
	private String detail_no;
	
	private String platcust;
	
	private String mobile;
	
	private String card_no_old;
	
	private String card_type_old;
	
	private String name;
	
	private String pay_code;

	public String getDetail_no() {
		return detail_no;
	}

	public void setDetail_no(String detail_no) {
		this.detail_no = detail_no;
	}

	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCard_no_old() {
		return card_no_old;
	}

	public void setCard_no_old(String card_no_old) {
		this.card_no_old = card_no_old;
	}

	public String getCard_type_old() {
		return card_type_old;
	}

	public void setCard_type_old(String card_type_old) {
		this.card_type_old = card_type_old;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPay_code() {
		return pay_code;
	}

	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}
}
