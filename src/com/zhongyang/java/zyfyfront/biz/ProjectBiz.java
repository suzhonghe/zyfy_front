package com.zhongyang.java.zyfyfront.biz;

import com.zhongyang.java.zyfyfront.params.ProjectParams;
import com.zhongyang.java.zyfyfront.returndata.ProjectReturn;

/**
 * 
* @Title: LoanRequestBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:  项目管理业务处理接口
* @author 苏忠贺   
* @date 2015年12月2日 上午8:52:29 
* @version V1.0
 */
public interface ProjectBiz {
	/**
	 * 
	 *TODO
	 *@date 2017年6月21日上午10:05:51
	 *@param params
	 *@return
	 *@author Administrator
	 */
	public ProjectReturn queryProjectByParams(ProjectParams params);
}
