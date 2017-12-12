package com.zhongyang.java.bankmanager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.bankmanager.entity.ClearNotify;
import com.zhongyang.java.bankmanager.service.ClearNotifyService;
import com.zhongyang.java.dao.ClearNotifyDao;

/**
 *@package com.zhongyang.java.bankmanager.service.impl
 *@filename ClearNotifyServiceImpl.java
 *@date 2017年10月18日上午10:05:22
 *@author suzh
 */
@Service
public class ClearNotifyServiceImpl implements ClearNotifyService {

	@Autowired
	private ClearNotifyDao clearNotifyDao;
	
	@Override
	public int addClearNotify(ClearNotify clearNotify) {
		return clearNotifyDao.insertClearNotify(clearNotify);
	}

	
	@Override
	public ClearNotify queryOneByParams(ClearNotify clearNotify) {
		return clearNotifyDao.selectOneByParams(clearNotify);
	}

	
	@Override
	public List<ClearNotify> queryMoreByParams(ClearNotify clearNotify) {
		return clearNotifyDao.selectMoreByParams(clearNotify);
	}

}
