package com.zhongyang.java.zyfyfront.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.ProductDao;
import com.zhongyang.java.zyfyfront.pojo.Product;
import com.zhongyang.java.zyfyfront.service.ProductService;

/**
 * 
 *@package com.zhongyang.java.zyfyfront.service.impl
 *@filename ProductServiceImpl.java
 *@date 2017年8月2日上午10:27:17
 *@author suzh
 */
@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductDao productDao;

	@Override
	public List<Product> queryAllProduct(){
		return productDao.selectAllProduct();
	}

	@Override
	public Product queryProductByParams(Product product){
		return productDao.selectProductByParams(product);
	}

}
