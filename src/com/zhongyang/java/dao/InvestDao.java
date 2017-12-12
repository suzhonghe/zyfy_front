package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.pojo.Invest;

/**
 *@package com.zhongyang.java.dao
 *@filename InvestDao.java
 *@date 2017年7月6日下午3:48:30
 *@author suzh
 */
public interface InvestDao {
	
	public int insertInvset(Invest invest);
	
	public int updateInvestByParams(Invest invest);
	
	public List<Invest> selectInvestByparams(Invest invest);
	
	public List<Invest> selectInvestByPage(Page<Invest>page);

}
