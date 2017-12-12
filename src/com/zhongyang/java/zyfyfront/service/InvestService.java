package com.zhongyang.java.zyfyfront.service;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.pojo.Invest;

/**
 *@package com.zhongyang.java.zyfyfront.service
 *@filename InvestService.java
 *@date 2017年7月6日下午3:42:15
 *@author suzh
 */
public interface InvestService {
	
	public Invest addInvest(Invest invest)throws Exception;
	
	public void modifyInvest(Invest invest)throws Exception;
	
	public List<Invest> queryInvestByParams(Invest invest);
	
	public List<Invest> queryInvestByPage(Page<Invest>page);
}
