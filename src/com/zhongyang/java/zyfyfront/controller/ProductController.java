package com.zhongyang.java.zyfyfront.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.zyfyfront.biz.ProductBiz;
import com.zhongyang.java.zyfyfront.params.ProductParams;
import com.zhongyang.java.zyfyfront.returndata.ProductReturn;
/**
 * 
 *@package com.zhongyang.java.zyfyfront.controller
 *@filename ProductController.java
 *@date 2017年8月2日上午10:43:28
 *@author suzh
 */
@Controller
public class ProductController extends BaseController{
	
	@Autowired
	private ProductBiz productBiz;
	
	@RequestMapping(value="/front/product/queryAllProducts")
	public @ResponseBody ProductReturn queryAllProducts(){
		return productBiz.queryAllProducts();
	}; 

	@RequestMapping(value="/front/product/queryProductById",method=RequestMethod.POST)
	public @ResponseBody ProductReturn queryProductByParams(ProductParams params){
		return productBiz.queryProductByParams(params);
	}
}
