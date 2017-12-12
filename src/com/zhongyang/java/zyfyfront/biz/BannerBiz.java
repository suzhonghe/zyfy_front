package com.zhongyang.java.zyfyfront.biz;

import com.zhongyang.java.zyfyfront.returndata.BannerReturn;

public interface BannerBiz {
	
	public BannerReturn queryBannerPhotos(String type);

	public BannerReturn getAppVersion();

}
