package com.zhongyang.java.zyfyfront.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.zyfyfront.biz.ProductBiz;
import com.zhongyang.java.zyfyfront.params.ProductParams;
import com.zhongyang.java.zyfyfront.pojo.Product;
import com.zhongyang.java.zyfyfront.returndata.ProductReturn;
import com.zhongyang.java.zyfyfront.service.ProductService;

/**
 * 
 * @Title: ProductBizImpl.java
 * @Package com.zhongyang.java.biz.impl
 * @Description: 产品业务处理接口实现
 * @author 苏忠贺
 * @date 2015年12月17日 下午3:59:00
 * @version V1.0
 */
@Service
public class ProductBizImpl implements ProductBiz {

	@Autowired
	private ProductService productService;

	@Override
	public ProductReturn queryAllProducts() {
		ProductReturn pr=new ProductReturn();
		List<Product>ps=new ArrayList<Product>();
		List<Product> products = productService.queryAllProduct();
		Product p=new Product();
		p.setName("全部");
		p.setId("");
		ps.add(p);
		for (Product product : products) {
			product.setDescription(null);
			product.setMaxInvestAmount(null);
			product.setMinInvestAmount(null);
			product.setRate(null);
			product.setTimeCreate(null);
		}
		ps.addAll(products);
		pr.setProducts(ps);
		pr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
		return pr;
	}

	@Override
	public ProductReturn queryProductByParams(ProductParams params) {
		ProductReturn pr=new ProductReturn();
		try {
			if (params == null||params.getProduct()==null) 
				throw new UException(SystemEnum.PARAMS_ERROR, "参数接收错误");
			pr.setProduct(productService.queryProductByParams(params.getProduct()));
			pr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
			
		} catch (UException e) {
			pr.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return pr;
	}

}
