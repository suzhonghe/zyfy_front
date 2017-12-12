package com.zhongyang.java.dao;

import com.zhongyang.java.bankmanager.entity.BindCardRecord;

/**
 *@package com.zhongyang.java.dao
 *@filename BindCardRecordDao.java
 *@date 2017年8月17日上午10:44:32
 *@author suzh
 */
public interface BindCardRecordDao {

	public int insertBindCardRecord(BindCardRecord record);
	
	public int updateBindCardRecord(BindCardRecord record);
	
	public BindCardRecord selectRecordByParams(BindCardRecord record);
}
