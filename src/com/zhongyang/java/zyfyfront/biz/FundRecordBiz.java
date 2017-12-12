package com.zhongyang.java.zyfyfront.biz;

import java.util.List;

import com.zhongyang.java.zyfyfront.pojo.User;
import com.zhongyang.java.zyfyfront.vo.FundRecordCalenderVo;

public interface FundRecordBiz {

	/*
	 * 用户资金日历
	 */
	public List<FundRecordCalenderVo> userFundRecordCalendar(String newData, User user);
 
}
