package com.zhongyang.java.zyfyfront.biz.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.system.util.SystemPro;
import com.zhongyang.java.zyfyfront.biz.ProjectBiz;
import com.zhongyang.java.zyfyfront.params.ProjectParams;
import com.zhongyang.java.zyfyfront.pojo.Loan;
import com.zhongyang.java.zyfyfront.pojo.Project;
import com.zhongyang.java.zyfyfront.pojo.ProofPhoto;
import com.zhongyang.java.zyfyfront.returndata.ProjectReturn;
import com.zhongyang.java.zyfyfront.service.LoanService;
import com.zhongyang.java.zyfyfront.service.ProjectService;
import com.zhongyang.java.zyfyfront.service.ProofPhotoService;
import com.zhongyang.java.zyfyfront.vo.ProjectDetail;

@Service
public class ProjectBizImpl implements ProjectBiz {
	
	static{
		Map<String, Object> sysMap = SystemPro.getProperties();
		ZYCF_IP=(String) sysMap.get("ZYCF_IP");
	}
	
	private static Logger logger=Logger.getLogger(ProjectBizImpl.class);
	
	private static String ZYCF_IP;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ProofPhotoService proofPhotoService;
	
	@Autowired
	private LoanService loanService;

	@Override
	public ProjectReturn queryProjectByParams(ProjectParams params) {
		ProjectReturn pr=new ProjectReturn();
		ProjectDetail projectDetail=new ProjectDetail();
		try {
			if(params==null||params.getLoan()==null)
	        	throw new UException(SystemEnum.PARAMS_ERROR, "参数错误");
			
			Loan loan = loanService.queryLoanByParams(params.getLoan());
	        if(loan==null)
	        	throw new UException(SystemEnum.DATA_REFUSED, "数据查询异常");
	        
	        Project pro=new Project();
	        pro.setId(loan.getProjectId());
			Project project = projectService.queryProjectByParams(pro);
			
			projectDetail.setFirmInfo(project.getFirmInfo());
			projectDetail.setOperationRange(project.getOperationRange());
			projectDetail.setRepaySource(project.getRepaySource());
			projectDetail.setRiskInfo(project.getRiskInfo());
			projectDetail.setDescription(project.getDescription());
			
			ProofPhoto pf=new ProofPhoto();
			pf.setProjectId(project.getId());
			
			//根据项目ID查询的到对应的图片
			List<ProofPhoto> ptoofPhotos = proofPhotoService.queryByParams(pf);
			String photoIp=ZYCF_IP.substring(0,ZYCF_IP.lastIndexOf("/"));
			for (ProofPhoto proofPhoto : ptoofPhotos) {
				//拼接身份证图片存储地址url
				if("法人身份证".equals(proofPhoto.getPhotoName())){
					if(projectDetail.getLegalPersonPhotoUrl()!=null){
						projectDetail.setLegalPersonPhotoUrl(projectDetail.getLegalPersonPhotoUrl()+","+photoIp+proofPhoto.getUrlPath());
					}
					else{
						projectDetail.setLegalPersonPhotoUrl(photoIp+proofPhoto.getUrlPath());
					}
				}
				//拼接企业信心图片存储地址url
				if("企业信息".equals(proofPhoto.getPhotoName())){
					if(projectDetail.getEnterpriseInfoPhotoUrl()!=null){
						projectDetail.setEnterpriseInfoPhotoUrl(projectDetail.getEnterpriseInfoPhotoUrl()+","+photoIp+proofPhoto.getUrlPath());
					}
					else{
						projectDetail.setEnterpriseInfoPhotoUrl(photoIp+proofPhoto.getUrlPath());
					}
				}
				//拼接合同文件存储地址url
				if("合同文件".equals(proofPhoto.getPhotoName())){
					if(projectDetail.getAssetsPhotoUrl()!=null){
						projectDetail.setAssetsPhotoUrl(projectDetail.getAssetsPhotoUrl()+","+photoIp+proofPhoto.getUrlPath());
					}
					else{
						projectDetail.setAssetsPhotoUrl(photoIp+proofPhoto.getUrlPath());
					}
				}
				//拼接资产信息图片存储地址url
				if("资产信息".equals(proofPhoto.getPhotoName())){
					if(projectDetail.getContractPhotoUrl()!=null){
						projectDetail.setContractPhotoUrl(projectDetail.getContractPhotoUrl()+","+photoIp+proofPhoto.getUrlPath());
					}
					else{
						projectDetail.setContractPhotoUrl(photoIp+proofPhoto.getUrlPath());
					}
				}
				//拼接其他资料存储地址url
				if("其他资料".equals(proofPhoto.getPhotoName())){
					if(projectDetail.getOthersPhotoUrl()!=null){
						projectDetail.setOthersPhotoUrl(projectDetail.getOthersPhotoUrl()+","+photoIp+proofPhoto.getUrlPath());
					}
					else{
						projectDetail.setOthersPhotoUrl(photoIp+proofPhoto.getUrlPath());
					}
				}
			}
			pr.setProjectDetail(projectDetail);
			pr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"操作成功"));
			
			return pr; 
		} catch (UException e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			pr.setMessage(new Message(e.getCode().value(),e.getMessage()));
			return pr; 
		}
	}
}
