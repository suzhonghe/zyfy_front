package com.zhongyang.java.zyfyfront.service;
import java.util.List;

import com.zhongyang.java.zyfyfront.pojo.Product;


/**
 * 
 *@package com.zhongyang.java.zyfyfront.service
 *@filename ProductService.java
 *@date 2017年8月2日上午10:25:39
 *@author suzh
 */
public interface ProductService {
	
	public List<Product> queryAllProduct();
	
	public Product queryProductByParams(Product product);
}
