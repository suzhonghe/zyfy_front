package com.zhongyang.java.zyfyfront.service;

import java.util.List;

import com.zhongyang.java.zyfyfront.pojo.ProofPhoto;
/**
 * 
 *@package com.zhongyang.java.zyfyfront.service
 *@filename ProofPhotoService.java
 *@date 20172017年6月21日上午10:47:50
 *@author Administrator
 */
public interface ProofPhotoService {
	
	/**
	 * 
	* @Title: queryByProjectId 
	* @Description:根据项目ID查询证明信息 
	* @return List<ProofPhoto>    返回类型 
	* @throws
	 */
	public List<ProofPhoto> queryByParams(ProofPhoto proofPhoto);
}
