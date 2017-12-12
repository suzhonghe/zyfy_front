package com.zhongyang.java.zyfyfront.biz.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.system.util.SystemPro;
import com.zhongyang.java.zyfyfront.biz.BannerBiz;
import com.zhongyang.java.zyfyfront.pojo.BannerPhoto;
import com.zhongyang.java.zyfyfront.returndata.BannerReturn;
import com.zhongyang.java.zyfyfront.service.BannerPhotoService;

@Service
public class BannerBizImpl implements BannerBiz{
	
	private static Logger logger=Logger.getLogger(BannerBizImpl.class);
	
	static{
		Map<String, Object> sysMap = SystemPro.getProperties();
		ip=(String) sysMap.get("ZYCF_IP");
		zycfappversion = (String) sysMap.get("ZYCFAPPVERSION");
		zycfappurl = (String) sysMap.get("ZYCFAPPVERSIONURL");
	}
	
	private static String ip;
	private static String zycfappversion;
	private static String zycfappurl;
	
	@Autowired
	private BannerPhotoService bannerPhotoService;
	
	@Override
	public BannerReturn queryBannerPhotos(String type) {
		BannerReturn br=new BannerReturn();
		try {
			List<BannerPhoto> bannerPhotos=new ArrayList<BannerPhoto>();
			BannerPhoto bannerPhoto=new BannerPhoto();
			bannerPhoto.setType(type);
			List<BannerPhoto> queryAllPhotos = bannerPhotoService.queryByParams(bannerPhoto);
			int i=0;
			for (BannerPhoto photo : queryAllPhotos) {
				BannerPhoto bp=new BannerPhoto();
				i++;
				if(type.equals("news")&&i>3||i>5){
					break;
				}
				if(type.equals("advertisement")&&i>2||i>5){
					break;
				}
				bp.setJumpAddress(photo.getJumpAddress());
				bp.setPathAddress(ip.substring(0,ip.lastIndexOf("/"))+photo.getPathAddress());
				bp.setSerialNumber(photo.getSerialNumber());
				bannerPhotos.add(bp);
			}
			br.setBannerPhotos(bannerPhotos);
			br.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"操作成功"));
			return br;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			br.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),"操作成功"));
			return br;
		}
	}


	@Override
	public BannerReturn getAppVersion(){
		BannerReturn br=new BannerReturn();
		Map<String, Object> map= new HashMap<>();
		map.put("zycfurlIp", ip+zycfappurl);
		map.put("zycfappversion", zycfappversion);
		br.setMap(map);
		br.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"操作成功"));
		return br;

     }
}
