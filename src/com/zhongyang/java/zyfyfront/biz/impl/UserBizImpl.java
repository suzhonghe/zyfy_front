package com.zhongyang.java.zyfyfront.biz.impl;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;

import com.zhongyang.java.bankmanager.entity.BmAccount;
import com.zhongyang.java.bankmanager.entity.Company;
import com.zhongyang.java.bankmanager.service.BmAccountService;
import com.zhongyang.java.bankmanager.service.CompanyService;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.util.DESTextCipher;
import com.zhongyang.java.system.util.FormatUtils;
import com.zhongyang.java.system.util.GetUUID;
import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.system.util.Password;
import com.zhongyang.java.system.util.Validator;
import com.zhongyang.java.zyfyfront.biz.UserBiz;
import com.zhongyang.java.zyfyfront.params.RegisteredParams;
import com.zhongyang.java.zyfyfront.params.UserParams;
import com.zhongyang.java.zyfyfront.pojo.User;
import com.zhongyang.java.zyfyfront.pojo.UserFund;
import com.zhongyang.java.zyfyfront.returndata.UserReturn;
import com.zhongyang.java.zyfyfront.service.UserFundService;
import com.zhongyang.java.zyfyfront.service.UserService;

@Service
public class UserBizImpl implements UserBiz {
	
	private static Logger logger=Logger.getLogger(UserBizImpl.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserFundService userFundService;
	
	@Autowired
	private BmAccountService bmAccountService;
	
	@Autowired
	private CompanyService companyService;

	@Override
	@Transactional
	public UserReturn addUser(HttpServletRequest req, RegisteredParams params) {
		UserReturn ur=new UserReturn();
		try {
			String regCode = (String) req.getSession().getAttribute("regcode");
			if (params.getRegcode() == null
					|| !params.getRegcode().equals(regCode)) 
				throw new UException(SystemEnum.MESSAGE_ERROR, "验证码输入有误！");
			
			
			if(!Validator.isMobile(params.getMobile()))
				throw new UException(SystemEnum.PARAMS_ERROR, "手机号格式错误");
			
			String salt = Password.getSalt(null);
			String userPassword = Password.getPassphrase(salt,
					params.getPassphrase());
			User user = new User();
			User res=new User();
			Date date=new Date();
			user.setId(GetUUID.getUniqueKey());
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			try {
				user.setMobile(cipher.encrypt(params.getMobile().trim()));
				res.setMobile(cipher.encrypt(params.getReferralMobile()));//推荐人手机号
			} catch (GeneralSecurityException e) {
				throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION, "数据加密异常");
			}
			User referralUser = userService.queryUserByParams(res);
			if(referralUser!=null)
				user.setReferralId(referralUser.getId());
			else
				throw new UException(SystemEnum.USER_NOT_EXISTS, "推荐人不存在");
			
			user.setSalt(salt);
			user.setPassphrase(userPassword);
			user.setRegisterDate(date);
			user.setLastLoginDate(date);
			user.setEnabled(true);
			user.setUserType(params.getUserType());
			user.setLastModifyDate(date);
			user.setCustType(1);
			int status = userService.addUser(user);
			
			UserFund fund=new UserFund();
			fund.setId(GetUUID.getUniqueKey());
			fund.setUserId(user.getId());
			fund.setTimeCreated(date);
			fund.setTimeLastUpdate(date);
			int count=0;
			try {
				count = userFundService.addUserFund(fund);
			} catch (Exception e1) {
				logger.info("数据入库异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "数据入库异常");
			}
			
			//TB_BM_ACCOUNT添加一条记录
			BmAccount account=new BmAccount();
			account.setId(GetUUID.getUniqueKey());
			account.setUserId(user.getId());
			account.setCreateTime(date);
			account.setOpen_ii_account(false);
			
			int acc=0;
			try {
				acc = bmAccountService.addBmAccount(account);
			} catch (Exception e) {
				logger.info("数据入库异常"+e.fillInStackTrace());
				e.printStackTrace();
				throw new UException(SystemEnum.DATA_REFUSED, "数据入库异常");
			}
			
			if (status==1&&count==1&&acc==1) {
				user.setSalt(null);
				user.setPassphrase(null);
				user.setRegisterDate(null);
				user.setLastLoginDate(null);
				ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"平台注册成功"));
				req.getSession().setAttribute("zycfLoginUser", user);
			} else 
				throw new UException(SystemEnum.DATA_REFUSED, "数据入库异常");
			
		} catch (UException e) {
			logger.info(e,e.fillInStackTrace());
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}

	@Override
	@Transactional
	public UserReturn companyRegist(HttpServletRequest req, RegisteredParams params) {
		UserReturn ur=new UserReturn();
		try {
			String regCode = (String) req.getSession().getAttribute("regcode");
			if (params.getRegcode() == null
					|| !params.getRegcode().equals(regCode)) {
				throw new UException(SystemEnum.MESSAGE_ERROR, "验证码输入有误！");
			}
			
			if(!Validator.isMobile(params.getMobile())){
				throw new UException(SystemEnum.PARAMS_ERROR, "手机号格式错误");
			}
			String salt = Password.getSalt(null);
			String userPassword = Password.getPassphrase(salt,
					params.getPassphrase());
			User user = new User();
			User res=new User();
			Date date=new Date();
			user.setId(GetUUID.getUniqueKey());
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			try {
				user.setMobile(cipher.encrypt(params.getMobile().trim()));
				res.setMobile(cipher.encrypt(params.getReferralMobile()));//推荐人手机号
			} catch (GeneralSecurityException e) {
				ur.setMessage(new Message(SystemEnum.DATA_SECURITY_EXCEPTION.value(),"数据加密异常"));
				return ur;
			}
			
			User referralUser = userService.queryUserByParams(res);
			if(referralUser!=null){
				user.setReferralId(referralUser.getId());
			}
			user.setSalt(salt);
			user.setPassphrase(userPassword);
			user.setRegisterDate(date);
			user.setLastLoginDate(date);
			user.setEnabled(true);
			user.setUserType(2);//企业用户只能作为融资人
			user.setLastModifyDate(date);
			user.setCustType(2);
			int status = userService.addUser(user);
			
			UserFund fund=new UserFund();
			fund.setId(GetUUID.getUniqueKey());
			fund.setUserId(user.getId());
			fund.setTimeCreated(date);
			fund.setTimeLastUpdate(date);
			int count=0;
			try {
				count = userFundService.addUserFund(fund);
			} catch (Exception e1) {
				logger.info("数据入库异常"+e1.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "数据入库异常");
			}
			
			//TB_BM_ACCOUNT添加一条记录
			BmAccount account=new BmAccount();
			account.setId(GetUUID.getUniqueKey());
			account.setUserId(user.getId());
			account.setCreateTime(date);
			account.setOpen_ii_account(false);
			
			int acc=0;
			try {
				acc = bmAccountService.addBmAccount(account);
			} catch (Exception e) {
				logger.info("数据入库异常"+e.fillInStackTrace());
				e.printStackTrace();
				throw new UException(SystemEnum.DATA_REFUSED, "数据入库异常");
			}
						
			Company company=new Company();
			company.setId(GetUUID.getUniqueKey());
			company.setCreateTime(date);
			company.setUserId(user.getId());
			try {
				companyService.addCompany(company);
			} catch (Exception e) {
				logger.info("数据入库异常"+e.fillInStackTrace());
				e.printStackTrace();
				throw new UException(SystemEnum.DATA_REFUSED, "数据入库异常");
			}
			
			
			if (status==1&&count==1&&acc==1) {
				user.setSalt(null);
				user.setPassphrase(null);
				user.setRegisterDate(null);
				user.setLastLoginDate(null);
				ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"平台注册成功"));
				req.getSession().setAttribute("zycfLoginUser", user);
			} else {
				throw new UException(SystemEnum.DATA_REFUSED, "数据入库异常");
			}
			return ur;
		} catch (UException e) {
			logger.info(e,e.fillInStackTrace());
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
			return ur;
		}
	}

	@Override
	public UserReturn searchUser(UserParams params) {
		UserReturn userRes=new UserReturn();
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		Message message=new Message();
		try {
			if(params==null){
				message.setCode(SystemEnum.PARAMS_ERROR.value());
				message.setMessage("未接收到请求参数");
				userRes.setMessage(message);//4001
				return userRes;
			}
			if(params.getUser()!=null&&params.getUser().getMobile()!=null){
				params.getUser().setMobile(cipher.encrypt(params.getUser().getMobile()));
			}
			
			User res = userService.queryUserByParams(params.getUser());
			if(res==null){
				userRes.setMessage(new Message(SystemEnum.USER_NOT_EXISTS.value(),"用户不存在"));
				return userRes;
			}
			res.setMobile(cipher.decrypt(res.getMobile()));
			userRes.setUser(res);
			message.setCode(SystemEnum.OPRARION_SUCCESS.value());//1000
			message.setMessage("查询成功");
			userRes.setMessage(message);
		} catch (GeneralSecurityException e) {
			message.setCode(SystemEnum.DATA_SECURITY_EXCEPTION.value());//9002
			message.setMessage("数据加密异常");
			userRes.setMessage(message);
		}
		return userRes;
	}
	
	
	
	@Override
	public UserReturn mobileUnique(HttpServletRequest request,UserParams params) {
		UserReturn userRes=new UserReturn();
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		try {
			if(params==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"未接收到请求参数");
			
			if(params.getUser()!=null&&params.getUser().getMobile()!=null){
				try{
					params.getUser().setMobile(cipher.encrypt(params.getUser().getMobile()));
				}catch(GeneralSecurityException e){
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"手机号加密异常");
				}
			}
			
			User res = userService.queryUserByParams(params.getUser());
			if(res==null){
				try{
					request.getSession().setAttribute("mobile", cipher.decrypt(params.getUser().getMobile()));
				}catch(GeneralSecurityException e){
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION,"手机号解密异常");
				}
				throw new UException(SystemEnum.USER_NOT_EXISTS,"用户不存在");
			}else{
				userRes.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询用户成功"));
				return userRes;
			}
			
		} catch (UException e) {
			userRes.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return userRes;
	}

	@Override
	public UserReturn userLogin(HttpServletRequest req, UserParams params) {
		UserReturn ur=new UserReturn();
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		Pattern pattern = Pattern.compile("[0-9]*");
		
		if(params==null||params.getUserVo()==null){
			ur.setMessage(new Message(SystemEnum.PARAMS_ERROR.value(),"参数接收异常"));
			return ur;
		}
		

		String loginName = (String) req.getSession().getAttribute("loginName") == null ? null : (String)req.getSession().getAttribute("loginName");
		int loginCount = ((Integer)req.getSession().getAttribute("loginCount")== null ? 0 : (Integer)req.getSession().getAttribute("loginCount") );
		if(loginName != null && params.getUserVo().getLoginName()!=null && loginName.equals(params.getUserVo().getLoginName()) ){
			loginCount++;
		}else{
			loginCount=1;
		}

		User user=new User();
		try {
			if (pattern.matcher(params.getUserVo().getLoginName()).matches()) {
				user.setMobile(cipher.encrypt(params.getUserVo().getLoginName()));
			} else {
				user.setLoginName(params.getUserVo().getLoginName());
			}
			User resUser = userService.queryUserByParams(user);
			User loginUser = new User();
			
			if (resUser != null) {
				loginUser.setId(resUser.getId());
				if(!resUser.getEnabled()){
					ur.setMessage(new Message(SystemEnum.USER_LOCK.value(), "账户已被锁定"));
					return ur;
				}
				if (resUser.getAllowTime() == null || FormatUtils.timeDiff(new Date(), resUser.getAllowTime())) {
					String password = Password.getPassphrase(resUser.getSalt(), params.getUserVo().getPassphrase());
					if (resUser.getPassphrase().equals(password)) {
						params.getUserVo().setLoginCounter(0);
						resUser.setPassphrase(null);
						resUser.setSalt(null);
						resUser.setLastLoginDate(null);
						resUser.setLastModifyDate(null);
						resUser.setReferralId(null);
						resUser.setRegisterDate(null);
						resUser.setIdCode(null);
						req.getSession().setAttribute("zycfLoginUser", resUser);
						
						loginUser.setLastLoginDate(new Date());						
						userService.modifyUserByParams(loginUser);
						ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "登陆成功"));
						return ur;
					} else {
						req.getSession().setAttribute("loginName", params.getUserVo().getLoginName());
						req.getSession().setAttribute("loginCount", loginCount);
						if (loginCount == 5) {
							DateTime dt = new DateTime(new Date());
							loginUser.setAllowTime(dt.plusMinutes(30).toDate());
							userService.modifyUserByParams(loginUser);
							ur.setMessage(new Message(SystemEnum.USER_LOCK.value(), "连续5次输入错误，帐号已被锁定！"));
							return ur;
						} else {
							ur.setMessage(new Message(SystemEnum.USER_PASSWORD_VAILD_FAILURE.value(), "用户名和密码不匹配！还有"+(5-loginCount)+"次机会"));
							return ur;
						}
					}
				}else{
					ur.setMessage(new Message(SystemEnum.LOGIN_ERROR.value(), "用户名账号已被锁定，请稍后重试！"));
					return ur;
				}
			} else {
				ur.setMessage(new Message(SystemEnum.USER_NOT_EXISTS.value(), "用户不存在"));
				return ur;
			}
		} catch (GeneralSecurityException e) {
			logger.info(e,e.fillInStackTrace());
			ur.setMessage(new Message(SystemEnum.UNKNOW_EXCEPTION.value(), "未知异常，请联系客服人员！"));
			return ur;
		}
		
	}

	@Override
	@Transactional
	public UserReturn modifyLoginName(HttpServletRequest request, String loginName) {
		UserReturn ur=new UserReturn();
		try{
			User user=(User)WebUtils.getSessionAttribute(request, "zycfLoginUser");
			if(user==null)
				throw  new UException(SystemEnum.USER_NOLOGIN,"您没有登录");
			
			if(loginName==null||"".equals(loginName))
				throw  new UException(SystemEnum.PARAMS_ERROR,"登录名不能为空");
			
			User updateUser=new User();
			updateUser.setId(user.getId());
			updateUser.setLoginName(loginName);
			userService.modifyUserByParams(updateUser);
			
			ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"修改成功"));
			
			
		}catch(UException e){
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}

	@Override
	@Transactional
	public UserReturn modifyPassWord(HttpServletRequest request, UserParams params) {
		UserReturn ur=new UserReturn();
		try{
			User user=(User)WebUtils.getSessionAttribute(request, "zycfLoginUser");
			if(user==null)
				throw  new UException(SystemEnum.USER_NOLOGIN,"您没有登录");
			
			if(params==null||"".equals(params.getNewPassWord()))
				throw  new UException(SystemEnum.PARAMS_ERROR,"新密码不能为空");
			
			user=userService.queryUserByParams(user);
			String pass=Password.getPassphrase(user.getSalt(), params.getOldPassWord());
			if(!pass.equals(user.getPassphrase()))
				throw  new UException(SystemEnum.PARAMS_ERROR,"旧密码输入错误");
			
			pass=Password.getPassphrase(user.getSalt(), params.getNewPassWord());
			User updateUser=new User();
			updateUser.setId(user.getId());
			updateUser.setPassphrase(pass);
			userService.modifyUserByParams(updateUser);
			
			ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"修改成功"));
			
			
		}catch(UException e){
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}

	@Override
	@Transactional
	public UserReturn resetPassWord(HttpServletRequest request, UserParams params) {
		UserReturn ur=new UserReturn();
		try{
			String mobile=(String)WebUtils.getSessionAttribute(request, "mobile");
			
			if(params==null|| params.getUser()==null||"".equals(params.getUser().getPassphrase()))
				throw  new UException(SystemEnum.PARAMS_ERROR,"新密码不能为空");
			
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			try {
				params.getUser().setMobile(cipher.encrypt(mobile));
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}
			User user=userService.queryUserByParams(params.getUser());
			if(user==null)
				throw  new UException(SystemEnum.PARAMS_ERROR,"用户不存在");
				
			String pass=Password.getPassphrase(user.getSalt(), params.getUser().getPassphrase());
			User updateUser=new User();
			updateUser.setId(user.getId());
			updateUser.setPassphrase(pass);
			userService.modifyUserByParams(updateUser);
			
			ur.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"修改成功"));
			request.getSession().removeAttribute("mobile");
			
		}catch(UException e){
			ur.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ur;
	}
	
}
