package com.zhongyang.java.zyfyfront.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.BannerPhotoDao;
import com.zhongyang.java.zyfyfront.pojo.BannerPhoto;
import com.zhongyang.java.zyfyfront.service.BannerPhotoService;
@Service
public class BannerPhotoServiceImpl implements BannerPhotoService {
	
	@Autowired
	private BannerPhotoDao bannerPhotoDao;

	@Override
	public List<BannerPhoto> queryByParams(BannerPhoto bannerPhoto) throws Exception {
		return bannerPhotoDao.selectByParams(bannerPhoto);
	}
}
