package com.zhongyang.java.bankmanager.params;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *@package com.zhongyang.java.bankmanager.params
 *@filename Commission.java
 *@date 2017年8月11日下午1:45:44
 *@author suzh
 */
public class Commission implements Serializable{
	
	private String payout_plat_type;//入账的平台账户 (01-现金 11-在途)
	
	private BigDecimal payout_amt;//手续费金额

	public String getPayout_plat_type() {
		return payout_plat_type;
	}

	public void setPayout_plat_type(String payout_plat_type) {
		this.payout_plat_type = payout_plat_type;
	}

	public BigDecimal getPayout_amt() {
		return payout_amt;
	}

	public void setPayout_amt(BigDecimal payout_amt) {
		this.payout_amt = payout_amt;
	}
	
}
