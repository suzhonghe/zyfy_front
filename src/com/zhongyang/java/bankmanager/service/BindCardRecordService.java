package com.zhongyang.java.bankmanager.service;

import com.zhongyang.java.bankmanager.entity.BindCardRecord;

/**
 *@package com.zhongyang.java.bankmanager.service
 *@filename BindCardRecordService.java
 *@date 2017年8月17日上午10:52:52
 *@author suzh
 */
public interface BindCardRecordService {

	public int addBindCardRecord(BindCardRecord record)throws Exception;
	
	public int modifyBindCardRecord(BindCardRecord record)throws Exception;
	
	public BindCardRecord queryRecordByParams(BindCardRecord record);
}
