package com.zhongyang.java.bankmanager.biz.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONException;
import com.zhongyang.java.bankmanager.biz.ManagerUtilBiz;
import com.zhongyang.java.bankmanager.entity.BmAccount;
import com.zhongyang.java.bankmanager.returndata.ManagerUtilReturn;
import com.zhongyang.java.bankmanager.service.BmAccountService;
import com.zhongyang.java.bankmanager.util.RequestUtil;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.system.util.SystemPro;
import com.zhongyang.java.zyfyfront.pojo.User;

/**
 *@package com.zhongyang.java.bankmanager.biz.impl
 *@filename ManagerUtilBizImpl.java
 *@date 2017年7月6日上午10:10:50
 *@author suzh
 */
@Service
public class ManagerUtilBizImpl implements ManagerUtilBiz {
	
	static{
		Map<String, Object> sysMap = SystemPro.getProperties();
		ZYCF_SENDMEGCODE_APPLY=(String)sysMap.get("ZYCF_SENDMEGCODE_APPLY");
	}
	
	private static Logger logger=Logger.getLogger(ManagerUtilBizImpl.class);
	
	private static String ZYCF_SENDMEGCODE_APPLY;
	
	@Autowired
	private BmAccountService bmAccountService;
	
	@Override
	public ManagerUtilReturn sendMSgCodeApply(HttpServletRequest request) {
		ManagerUtilReturn res=new ManagerUtilReturn();
		User user=(User)WebUtils.getSessionAttribute(request, "zycfLoginUser");
		try{
			logger.info("===============用户数据查询校验================");
			if(user==null)
				throw new UException(SystemEnum.USER_NOLOGIN,"您没有登录");
			BmAccount account=new BmAccount();
			account.setUserId(user.getId());
			BmAccount queryBmAccount = bmAccountService.queryBmAccountByParams(account);
			if(queryBmAccount==null||queryBmAccount.getPlatcust()==null)
				throw new UException(SystemEnum.UN_AUTHENTICATION,"您没有开通存管账户");
			
			
			logger.info("===============构造短信验证码申请参数================");
			Map<String,Object>map=new HashMap<String,Object>();
			map.put("platcust", queryBmAccount.getPlatcust());
			
			logger.info("===============发送信验证码请求================");
			Map<String, Object> sendRequest=null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_SENDMEGCODE_APPLY);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION,"网络连接异常");
			}
			logger.info(sendRequest);
			if("10000".equals(sendRequest.get("recode").toString()))
				res.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"验证码请求已发送，请注意查收"));
			else
				res.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),sendRequest.get("recode").toString()+sendRequest.get("remsg").toString()));
		}catch(UException e){
			res.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return res;
	}

}
