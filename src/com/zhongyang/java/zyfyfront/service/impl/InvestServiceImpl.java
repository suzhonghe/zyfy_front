package com.zhongyang.java.zyfyfront.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.dao.InvestDao;
import com.zhongyang.java.system.enumtype.InvestStatus;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.util.GetUUID;
import com.zhongyang.java.zyfyfront.pojo.Invest;
import com.zhongyang.java.zyfyfront.service.InvestService;

/**
 *@package com.zhongyang.java.zyfyfront.service.impl
 *@filename InvestServiceImpl.java
 *@date 2017年7月6日下午3:53:55
 *@author suzh
 */
@Service
public class InvestServiceImpl implements InvestService {
	
	private static Logger logger=Logger.getLogger(InvestServiceImpl.class);
	
	@Autowired
	private InvestDao investDao;
	
	@Override
	@Transactional
	public Invest addInvest(Invest invest) throws Exception{
		invest.setId(GetUUID.getUniqueKey());
		invest.setStatus(InvestStatus.PROCESSING);
		invest.setSubmitTime(new Date());
		logger.info("投资记录入库：投资信息："+invest.toString());
		
		try{
			investDao.insertInvset(invest);
		}catch(Exception e){
			logger.info("添加投资记录异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("添加投资记录第二次操作");
			try{
				investDao.insertInvset(invest);
			}catch(Exception e1){
				throw new Exception();
			}
		}
			
		return invest;
	}

	@Override
	@Transactional
	public void modifyInvest(Invest invest)throws Exception{
		
		try{
			investDao.updateInvestByParams(invest);
		}catch(Exception e){
			logger.info("修改投资记录异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("修改投资记录第二次操作");
			try{
				investDao.updateInvestByParams(invest);
			}catch(Exception e1){
				throw new Exception();
			}
		}
			
	}

	@Override
	public List<Invest> queryInvestByParams(Invest invest) {
		return investDao.selectInvestByparams(invest);
	}

	@Override
	public List<Invest> queryInvestByPage(Page<Invest> page) {
		return investDao.selectInvestByPage(page);
	}
	
}
