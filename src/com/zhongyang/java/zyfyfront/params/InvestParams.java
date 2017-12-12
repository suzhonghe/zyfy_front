package com.zhongyang.java.zyfyfront.params;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.pojo.Invest;

/**
 *@package com.zhongyang.java.zyfyfront.params
 *@filename InvestParams.java
 *@date 2017年8月3日上午10:32:54
 *@author suzh
 */
public class InvestParams {
	
	private Page<Invest> page;

	public Page<Invest> getPage() {
		return page;
	}

	public void setPage(Page<Invest> page) {
		this.page = page;
	}
}
