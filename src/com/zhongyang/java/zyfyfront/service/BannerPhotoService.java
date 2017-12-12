package com.zhongyang.java.zyfyfront.service;

import java.util.List;

import com.zhongyang.java.zyfyfront.pojo.BannerPhoto;
/**
 * 
* @Title: BannerPhotoService.java 
* @Package com.zhongyang.java.dao 
* @Description:Banner业务接口
* @author 苏忠贺   
* @date 2016年1月18日 上午11:19:16 
* @version V1.0
 */
public interface BannerPhotoService {
	
	/**
	 * 
	* @Title: queryByParams 
	* @Description:根据条件查询banner 
	* @return BannerPhoto    返回类型 
	* @throws
	 */
	public List<BannerPhoto> queryByParams(BannerPhoto bannerPhoto)throws Exception;
}
