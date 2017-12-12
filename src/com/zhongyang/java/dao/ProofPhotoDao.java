package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.zyfyfront.pojo.ProofPhoto;
/**
 * 
 *@package com.zhongyang.java.zyfyfront.dao
 *@filename ProofPhotoDao.java
 *@date 20172017年6月21日上午10:49:06
 *@author Administrator
 */
public interface ProofPhotoDao {
	/**
	 * 
	 *TODO
	 *@date 2017年6月21日上午10:49:12
	 *@param proofPhoto
	 *@return List<ProofPhoto>
	 *@author Administrator
	 */
	public List<ProofPhoto> selectByParams(ProofPhoto proofPhoto);	
}
