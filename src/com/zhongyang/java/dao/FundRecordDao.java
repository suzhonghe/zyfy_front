package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.zyfyfront.pojo.FundRecord;
import com.zhongyang.java.zyfyfront.vo.FundRecordCalenderVo;

/**
 * 
 *@package com.zhongyang.java.dao
 *@filename FundRecordDao.java
 *@date 2017年7月5日上午9:58:09
 *@author suzh
 */
public interface FundRecordDao {
	
	public int insertFundRecord(FundRecord fundRecord);
	
	public int  updateFundRecordByParams(FundRecord fundRecord);
	
	public FundRecord selectFundRecordByParams(FundRecord fundRecord);
	
	public List<FundRecord> selectFundRecordListByParams(FundRecord fundRecord);
	
	public List<FundRecordCalenderVo> userFundRecordCalendar(FundRecordCalenderVo fundRecordCalenderVo);

	public List<FundRecordCalenderVo> repmentfundRecordCalenderVos(FundRecordCalenderVo fundRecordCalenderVo);
}
