package com.zhongyang.java.zyfyfront.returndata;

import java.io.Serializable;
import java.util.List;

import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.zyfyfront.pojo.Product;

/**
 *@package com.zhongyang.java.zyfyfront.returndata
 *@filename ProductReturn.java
 *@date 2017年8月2日上午10:28:53
 *@author suzh
 */
public class ProductReturn implements Serializable{
	
	private Message message;
	
	private Product product;
	
	private List<Product>products;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
}
