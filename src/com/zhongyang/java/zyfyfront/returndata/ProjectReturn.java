package com.zhongyang.java.zyfyfront.returndata;

import java.io.Serializable;

import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.zyfyfront.vo.ProjectDetail;

/**
 *@package com.zhongyang.java.zyfyfront.returndata
 *@filename ProjectReturn.java
 *@date 20172017年6月21日上午9:48:47
 *@author suzh
 */
public class ProjectReturn implements Serializable{
	
	private ProjectDetail projectDetail;
	
	private Message message;

	public ProjectDetail getProjectDetail() {
		return projectDetail;
	}

	public void setProjectDetail(ProjectDetail projectDetail) {
		this.projectDetail = projectDetail;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
}
