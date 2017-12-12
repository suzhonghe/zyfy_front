package com.zhongyang.java.zyfyfront.biz.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.zhongyang.java.bankmanager.entity.BmAccount;
import com.zhongyang.java.bankmanager.service.BmAccountService;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.zyfyfront.biz.UserFundBiz;
import com.zhongyang.java.zyfyfront.pojo.User;
import com.zhongyang.java.zyfyfront.pojo.UserFund;
import com.zhongyang.java.zyfyfront.returndata.UserFundReturn;
import com.zhongyang.java.zyfyfront.service.UserFundService;

/**
 *@package com.zhongyang.java.zyfyfront.biz.impl
 *@filename UserFundBizImpl.java
 *@date 2017年8月3日上午9:35:33
 *@author suzh
 */
@Service
public class UserFundBizImpl implements UserFundBiz {

	@Autowired
	private UserFundService userFundService;
	
	@Autowired
	private BmAccountService bmAccountService;
	
	@Override
	public UserFundReturn queryUserFund(HttpServletRequest request) {
		UserFundReturn ufr=new UserFundReturn();
		try {
			User user=(User)WebUtils.getSessionAttribute(request, "zycfLoginUser");
			if(user==null)
				throw new UException(SystemEnum.USER_NOLOGIN,"您没有登录");
			
			BmAccount account=new BmAccount();
			account.setUserId(user.getId());
			
			account = bmAccountService.queryBmAccountByParams(account);
			
			if(account.getPlatcust()==null)
				throw new UException(SystemEnum.UN_AUTHENTICATION,"您没有实名认证");

			UserFund userFund=new UserFund();
			userFund.setUserId(user.getId());
			userFund = userFundService.queryUserFundByParams(userFund);
			if(userFund!=null)
				ufr.setUserFund(userFund);
			
			ufr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
			
		} catch (UException e) {
			e.printStackTrace();
			ufr.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ufr;
	}

}
