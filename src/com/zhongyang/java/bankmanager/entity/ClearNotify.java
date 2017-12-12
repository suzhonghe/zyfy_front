package com.zhongyang.java.bankmanager.entity;

import java.io.Serializable;

/**
 * 清算回调通知实体
 *@package com.zhongyang.java.bankmanager.entity
 *@filename ClearNotify.java
 *@date 2017年10月17日下午3:38:50
 *@author suzh
 */
public class ClearNotify implements Serializable{

	private String id;
	
	private String plat_no;
	
	private String clearDate;
	
	private String liquidation_flag;

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

	public String getClearDate() {
		return clearDate;
	}

	public void setClearDate(String clearDate) {
		this.clearDate = clearDate;
	}

	public String getLiquidation_flag() {
		return liquidation_flag;
	}

	public void setLiquidation_flag(String liquidation_flag) {
		this.liquidation_flag = liquidation_flag;
	}

	@Override
	public String toString() {
		return "ClearNotify [id=" + id + ", plat_no=" + plat_no + ", clearDate=" + clearDate + ", liquidation_flag="
				+ liquidation_flag + "]";
	}

}
