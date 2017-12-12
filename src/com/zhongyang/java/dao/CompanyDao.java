package com.zhongyang.java.dao;

import com.zhongyang.java.bankmanager.entity.Company;

/**
 *@package com.zhongyang.java.dao
 *@filename CompanyDao.java
 *@date 2017年8月16日下午4:03:59
 *@author suzh
 */
public interface CompanyDao {
	
	public int insertCompany(Company company);
	
	public int updateCompanyByParams(Company company);
	
	public Company selectCompanyByParams(Company company);
	
}
