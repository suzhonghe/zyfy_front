package com.zhongyang.java.dao;

import com.zhongyang.java.zyfyfront.pojo.CmsColumn;

/**
 * 
* @Title: CmsColumnDao.java 
* @Package com.zhongyang.java.dao 
* @Description:栏目DAO
* @author 苏忠贺   
* @date 2016年3月2日 下午3:52:08 
* @version V1.0
 */
public interface CmsColumnDao {
	
	public CmsColumn selectByParams(CmsColumn cmsColumn);
	
}
