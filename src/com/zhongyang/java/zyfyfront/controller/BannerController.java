package com.zhongyang.java.zyfyfront.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.zyfyfront.biz.BannerBiz;
import com.zhongyang.java.zyfyfront.returndata.BannerReturn;

@Controller
public class BannerController extends BaseController{
	
	@Autowired
	private BannerBiz bannerBiz;
	
	@RequestMapping(value="/front/banner/queryBannerPhotos")
	public @ResponseBody BannerReturn queryBannerListPhotos(String type){
		return bannerBiz.queryBannerPhotos(type);
	}
}
