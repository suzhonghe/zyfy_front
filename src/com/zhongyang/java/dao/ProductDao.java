package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.zyfyfront.pojo.Product;

/**
 * 
 *@package com.zhongyang.java.dao
 *@filename ProductDao.java
 *@date 2017年8月2日上午10:24:19
 *@author suzh
 */
public interface ProductDao {
	
	public List<Product> selectAllProduct();
	
	public Product selectProductByParams(Product product);
}
