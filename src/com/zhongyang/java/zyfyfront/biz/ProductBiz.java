package com.zhongyang.java.zyfyfront.biz;

import com.zhongyang.java.zyfyfront.params.ProductParams;
import com.zhongyang.java.zyfyfront.returndata.ProductReturn;

/**
 * 
 *@package com.zhongyang.java.zyfyfront.biz
 *@filename ProductBiz.java
 *@date 2017年8月2日上午10:28:14
 *@author suzh
 */
public interface ProductBiz {
	
	public ProductReturn queryAllProducts();
	
	public ProductReturn queryProductByParams(ProductParams params);
}
