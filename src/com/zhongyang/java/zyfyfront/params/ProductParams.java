package com.zhongyang.java.zyfyfront.params;

import java.io.Serializable;

import com.zhongyang.java.zyfyfront.pojo.Product;

/**
 *@package com.zhongyang.java.zyfyfront.params
 *@filename ProductParams.java
 *@date 2017年8月2日上午10:30:43
 *@author suzh
 */
public class ProductParams implements Serializable{

	private Product product;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}
