package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.zyfyfront.pojo.BannerPhoto;
/**
 * 
* @Title: BannerPhotoDao.java 
* @Package com.zhongyang.java.dao 
* @Description:BannerDAO 
* @author 苏忠贺   
* @date 2016年1月18日 上午11:19:16 
* @version V1.0
 */
public interface BannerPhotoDao {
	/**
	 * 
	* @Title: selectByParams 
	* @Description:根据条件查询某一条banner 
	* @return BannerPhoto    返回类型 
	* @throws
	 */
	public List<BannerPhoto> selectByParams(BannerPhoto bannerPhoto)throws Exception;

}
