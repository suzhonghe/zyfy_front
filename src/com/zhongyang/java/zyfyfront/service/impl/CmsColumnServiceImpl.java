package com.zhongyang.java.zyfyfront.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.CmsColumnDao;
import com.zhongyang.java.zyfyfront.pojo.CmsColumn;
import com.zhongyang.java.zyfyfront.service.CmsColumnService;
@Service
public class CmsColumnServiceImpl implements CmsColumnService {
	
	@Autowired
	private CmsColumnDao cmsColumnDao;

	@Override
	public CmsColumn queryByParams(CmsColumn cmsColumn){
		return cmsColumnDao.selectByParams(cmsColumn);
	}

}
