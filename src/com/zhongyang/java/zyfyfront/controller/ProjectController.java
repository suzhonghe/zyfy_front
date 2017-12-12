package com.zhongyang.java.zyfyfront.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.zyfyfront.biz.ProjectBiz;
import com.zhongyang.java.zyfyfront.params.ProjectParams;
import com.zhongyang.java.zyfyfront.returndata.ProjectReturn;


/**
 * 
* @Title: LoanRequestController.java 
* @Package com.zhongyang.java.controller 
* @Description: 项目管理控制器 
* @author 苏忠贺   
* @date 2015年12月2日 上午9:15:47 
* @version V1.0
 */
@Controller
public class ProjectController extends BaseController{
	
	@Autowired
	private ProjectBiz projectBiz;

	/**
	 * 
	 *TODO
	 *@date 2017年6月21日上午10:01:10
	 *@param params
	 *@return ProjectReturn
	 *@author suzh
	 */
	@RequestMapping(value="/front/project/searchProjectByParams")
	public @ResponseBody ProjectReturn searchProjectByParams(ProjectParams params){
		return projectBiz.queryProjectByParams(params);
	}
}
