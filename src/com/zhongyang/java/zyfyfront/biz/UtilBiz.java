package com.zhongyang.java.zyfyfront.biz;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.zyfyfront.returndata.BankReturn;

public interface UtilBiz {
	
	public void getVerificationImg(HttpServletRequest req, HttpServletResponse resp);
	
	public Message getVerificationCode(HttpServletRequest req,String imgCode);
	
	public Message mobolileCertWeb(HttpServletRequest request,HttpServletResponse response);
	
	public Message resetPasswd(HttpServletRequest request,String mobile);
	
	public Message resetPasswdValidate(HttpServletRequest request,String resetCode);
	
	public Map<String, Object> getAppVersion();
	
	public BankReturn searchAllBanks();
}
