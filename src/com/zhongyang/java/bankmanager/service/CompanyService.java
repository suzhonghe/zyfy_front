package com.zhongyang.java.bankmanager.service;

import com.zhongyang.java.bankmanager.entity.Company;

/**
 *@package com.zhongyang.java.zyfyfront.service
 *@filename CompanyService.java
 *@date 2017年8月16日下午4:18:39
 *@author suzh
 */
public interface CompanyService {
	
	public int addCompany(Company company)throws Exception;
	
	public int modifyCompanyByParams(Company company)throws Exception;
	
	public Company queryCompanyByParams(Company company);
}
