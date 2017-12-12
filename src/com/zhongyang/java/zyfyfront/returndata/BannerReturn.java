package com.zhongyang.java.zyfyfront.returndata;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.zyfyfront.pojo.BannerPhoto;

public class BannerReturn {
	
	private Message message;
	
	private List<BannerPhoto> bannerPhotos;
	
	private Map<String, Object> map;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public List<BannerPhoto> getBannerPhotos() {
		return bannerPhotos;
	}

	public void setBannerPhotos(List<BannerPhoto> bannerPhotos) {
		this.bannerPhotos = bannerPhotos;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
}
