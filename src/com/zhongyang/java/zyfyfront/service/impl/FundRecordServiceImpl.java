package com.zhongyang.java.zyfyfront.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.dao.FundRecordDao;
import com.zhongyang.java.system.util.GetUUID;
import com.zhongyang.java.zyfyfront.pojo.FundRecord;
import com.zhongyang.java.zyfyfront.service.FundRecordService;
import com.zhongyang.java.zyfyfront.vo.FundRecordCalenderVo;

/**
 *@package com.zhongyang.java.zyfyfront.service.impl
 *@filename FundRecordServiceImpl.java
 *@date 2017年7月5日上午10:44:10
 *@author suzh
 */
@Service
public class FundRecordServiceImpl implements FundRecordService {
	
	private static Logger logger=Logger.getLogger(FundRecordServiceImpl.class);
	
	@Autowired
	private FundRecordDao fundRecordDao;
	
	@Override
	@Transactional
	public FundRecord addFundRecord(FundRecord fundRecord)throws Exception{
		fundRecord.setId(GetUUID.getUniqueKey());
		fundRecord.setTimeRecorded(new Date());
		logger.info("资金记录详情：" + fundRecord.toString());
		logger.info("资金记录入库,订单号：" + fundRecord.getOrderId());
		try{
			fundRecordDao.insertFundRecord(fundRecord);
		}catch(Exception e){
			logger.info("数据入库异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("数据第二次入库操作");
			try{
				fundRecordDao.insertFundRecord(fundRecord);
			}catch(Exception e1){
				throw new Exception();
			}
		}
		return fundRecord;
	}

	@Override
	@Transactional
	public void modifyFundRecordByParams(FundRecord fundRecord)throws Exception{
		logger.info("修改资金记录：" + fundRecord.getOrderId());
		fundRecordDao.updateFundRecordByParams(fundRecord);
		try{
			fundRecordDao.updateFundRecordByParams(fundRecord);
		}catch(Exception e){
			logger.info("修改资金记录异常"+e.fillInStackTrace());
			Thread.sleep(100);
			logger.info("修改资金记录第二次操作");
			try{
				fundRecordDao.updateFundRecordByParams(fundRecord);
			}catch(Exception e1){
				throw new Exception();
			}
		}
	}

	@Override
	public FundRecord queryFundRecordByParams(FundRecord fundRecord) {
		return fundRecordDao.selectFundRecordByParams(fundRecord);
	}
	
	@Override
	public List<FundRecord> queryFundRecordListByParams(FundRecord fundRecord) {
		return fundRecordDao.selectFundRecordListByParams(fundRecord);
	}

	@Override
	public List<FundRecordCalenderVo> userFundRecordCalendar(FundRecordCalenderVo fundRecordCalenderVo){
		return fundRecordDao.userFundRecordCalendar(fundRecordCalenderVo);
	}
	
	@Override
	public List<FundRecordCalenderVo> repmentfundRecordCalenderVos(FundRecordCalenderVo fundRecordCalenderVo){
		return fundRecordDao.repmentfundRecordCalenderVos(fundRecordCalenderVo);
	}
}
