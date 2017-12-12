package com.zhongyang.java.zyfyfront.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.ProofPhotoDao;
import com.zhongyang.java.zyfyfront.pojo.ProofPhoto;
import com.zhongyang.java.zyfyfront.service.ProofPhotoService;

/**
 * 
* @Title: ProofPhotoServiceImpl.java 
* @Package com.zhongyang.java.zyfyback.service.impl 
* @Description:ProofPhotoServiceImpl
* @author 苏忠贺   
* @date 2017年6月9日 下午1:25:52 
* @version V1.0
 */
@Service
public class ProofPhotoServiceImpl implements ProofPhotoService{
	
	@Autowired
	private ProofPhotoDao proofPhotoDao;

	@Override
	public List<ProofPhoto> queryByParams(ProofPhoto proofPhoto){
		return proofPhotoDao.selectByParams(proofPhoto);
	}
}
