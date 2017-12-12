package com.zhongyang.java.zyfyfront.service;

import java.util.List;

import com.zhongyang.java.zyfyfront.pojo.FundRecord;
import com.zhongyang.java.zyfyfront.vo.FundRecordCalenderVo;

/**
 *@package com.zhongyang.java.zyfyfront.service
 *@filename FundRecordService.java
 *@date 2017年7月5日上午10:43:23
 *@author suzh
 */
public interface FundRecordService {
	
	public FundRecord addFundRecord(FundRecord fundRecord)throws Exception;
	
	public void  modifyFundRecordByParams(FundRecord fundRecord)throws Exception;
	
	public FundRecord queryFundRecordByParams(FundRecord fundRecord);
	
	public List<FundRecord> queryFundRecordListByParams(FundRecord fundRecord);
	
	public List<FundRecordCalenderVo> userFundRecordCalendar(FundRecordCalenderVo fundRecordCalenderVo);

	public List<FundRecordCalenderVo> repmentfundRecordCalenderVos(FundRecordCalenderVo fundRecordCalenderVo);
}
