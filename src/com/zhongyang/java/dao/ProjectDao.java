package com.zhongyang.java.dao;
import com.zhongyang.java.zyfyfront.pojo.Project;
/**
 * 
 *@package com.zhongyang.java.zyfyfront.dao
 *@filename ProjectDao.java
 *@date 20172017年6月21日上午10:16:07
 *@author Administrator
 */
public interface ProjectDao {
	/**
	 * 
	 *TODO
	 *@date 2017年6月21日上午10:16:14
	 *@param project
	 *@return Project
	 *@throws Exception
	 *@author Administrator
	 */
	public Project selectProjectByParams(Project project);
}
