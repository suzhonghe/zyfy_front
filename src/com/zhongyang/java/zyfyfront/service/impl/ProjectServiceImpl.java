package com.zhongyang.java.zyfyfront.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.ProjectDao;
import com.zhongyang.java.zyfyfront.pojo.Project;
import com.zhongyang.java.zyfyfront.service.ProjectService;
/**
 * 
 *@package com.zhongyang.java.zyfyfront.service.impl
 *@filename ProjectServiceImpl.java
 *@date 20172017年6月21日上午10:15:38
 *@author Administrator
 */
@Service
public class ProjectServiceImpl implements ProjectService{
	
	@Autowired
	private ProjectDao projectDao;
	
	@Override
	public Project queryProjectByParams(Project project){
		
		return projectDao.selectProjectByParams(project);
	}

}
