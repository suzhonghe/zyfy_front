package com.zhongyang.java.zyfyfront.biz.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.util.CheckUtil;
import com.zhongyang.java.system.util.DESTextCipher;
import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.system.util.ShortMessage;
import com.zhongyang.java.system.util.SystemPro;
import com.zhongyang.java.system.util.VerificationCode;
import com.zhongyang.java.zyfyfront.biz.UtilBiz;
import com.zhongyang.java.zyfyfront.controller.BaseController;
import com.zhongyang.java.zyfyfront.pojo.User;
import com.zhongyang.java.zyfyfront.returndata.BankReturn;
import com.zhongyang.java.zyfyfront.service.BankService;
import com.zhongyang.java.zyfyfront.service.UserService;
@Service
public class UtilBizImpl implements UtilBiz {

	private static Logger logger = Logger.getLogger(UtilBizImpl.class);
	
	static{
		Map<String, Object> sysMap = SystemPro.getProperties();
		ZYCFAPPVERSION = (String) sysMap.get("ZYCFAPPVERSION");
		ZYCFAPPVERSIONURL = (String) sysMap.get("ZYCFAPPVERSIONURL");
		ZYCF_IP = (String) sysMap.get("ZYCF_IP");
	}
		
	private static String ZYCFAPPVERSION;
	
	private static String ZYCFAPPVERSIONURL;
	
	private static String ZYCF_IP;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BankService bankService;

	@Override
	public void getVerificationImg(HttpServletRequest req, HttpServletResponse resp) {
		VerificationCode vc = CheckUtil.getVerificationCode();
		req.getSession().setAttribute("code", vc.getCode());
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/jpeg");
		try {
			ServletOutputStream sos = resp.getOutputStream();
			ImageIO.write(vc.getBuffImg(), "jpeg", sos);
			sos.flush();
			sos.close();
		} catch (IOException e) {
			throw new UException(e);
		}

	}

	@Override
	public Message getVerificationCode(HttpServletRequest req, String imgCode) {
		Message message = new Message();
		String code = (String) req.getSession().getAttribute("code");
		try{
			if(code==null)
				throw new UException(SystemEnum.MESSAGE_LOSE_EFFICACY,"验证码已失效");
			
			if (code.equalsIgnoreCase(imgCode)) {
				message.setCode(SystemEnum.OPRARION_SUCCESS.value());
				message.setMessage("验证通过");
				return message;
			} else {
				message.setCode(SystemEnum.OPRARION_FAILED.value());
				message.setMessage("验证码错误");
			}
		}catch(UException e){
			logger.info(e.fillInStackTrace());
			message.setCode(e.getCode().value());
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@Override
	public Message mobolileCertWeb(HttpServletRequest request, HttpServletResponse response){
		Message message = new Message();
		Map<String, Cookie> cookies = BaseController.ReadCookieMap(request);
		int mobileNumber = 0;
		if (cookies.get("mobileNumber") == null) {
			mobileNumber = 0;
		} else {
			mobileNumber = Integer.parseInt(cookies.get("mobileNumber").getValue());
		}
		int attemptNum = Integer.parseInt((String) SystemPro.getProperties().get("ATTEMPTNUM"));
		if (mobileNumber >= attemptNum) {
			message.setCode(SystemEnum.MESSAGE_NUM_OUT.value());
			return message;
		}
		long lastTime;
		if (cookies.get("lastTime") == null) {
			lastTime = 0;
		} else {
			lastTime = Long.parseLong(cookies.get("lastTime").getValue());
		}
		long nowTime = System.currentTimeMillis();
		if (nowTime < lastTime) {
			message.setCode(SystemEnum.MESSAGE_TIME_OUT.value());
			return message;
		}
		int intervaltime = Integer.parseInt((String) SystemPro.getProperties().get("INTERVALTIME"));
		BaseController.addCookie(response, "mobileNumber", String.valueOf(mobileNumber++), 1);
		BaseController.addCookie(response, "lastTime", String.valueOf(intervaltime * 1000 + nowTime), 1);
		List<String> list = new ArrayList<String>();
		list.add((String)request.getSession().getAttribute("mobile"));
		String code = ShortMessage.getShortMessage().getVerificationCode(list);
		BaseController.addCookie(response, "regcode", String.valueOf(code), 1);
		request.getSession().setAttribute("regcode", code);
		System.out.print(code);
		message.setCode(SystemEnum.OPRARION_SUCCESS.value());
		return message;
	}

	@Override
	public Message resetPasswd(HttpServletRequest request, String mobile) {
		Message message = new Message();
		HttpSession session = request.getSession();
		User user = new User();
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		try {
			user.setMobile(cipher.encrypt(mobile));
		} catch (GeneralSecurityException e) {
			logger.info("数据加密异常");
			logger.info(e, e.fillInStackTrace());
		}
		User res = userService.queryUserByParams(user);
		if (res == null) {
			message.setCode(SystemEnum.USER_NOT_EXISTS.value());// 2001
			message.setMessage("账户不存在");
			return message;
		}

		Integer counter = null;
		if (session.getAttribute("msgcounter") != null)
			counter = (Integer) session.getAttribute("msgcounter");

		if (counter == null) {
			session.setAttribute("msgcounter", 1);
		} else if (counter.intValue() > 5) {
			message.setCode(SystemEnum.MESSAGE_NUM_OUT.value());// 3001
			message.setMessage("验证码获取次数超过5次");
			return message;
		} else {
			session.setAttribute("msgcounter", counter.intValue() + 1);
		}

		String resetCode = ShortMessage.getShortMessage().getResetPasswdCode(mobile);
		session.setAttribute("resetPasswdCode", resetCode);
		session.setAttribute("mobile", mobile);
		message.setCode(SystemEnum.OPRARION_SUCCESS.value());// 1000
		return message;
	}

	@Override
	public Message resetPasswdValidate(HttpServletRequest request, String resetCode) {
		Message message = new Message();
		String resetPasswdCode = (String) request.getSession().getAttribute("resetPasswdCode");
		
		if (resetPasswdCode ==null) {
			message.setCode(SystemEnum.MESSAGE_LOSE_EFFICACY.value());// 3003
			message.setMessage("验证码已失效，请重新获取");
			return message;
		}
		
		int validTimes = 0;
		if (request.getSession().getAttribute("validTimes") != null)
			validTimes = (Integer) request.getSession().getAttribute("validTimes");

		if (validTimes > 5) {
			message.setCode(SystemEnum.MESSAGE_NUM_OUT.value());// 3001
			message.setMessage("获取验证码超过5次，请稍后再试");
			return message;
		}

		if (resetPasswdCode.equalsIgnoreCase(resetCode)){
			message.setCode(SystemEnum.OPRARION_SUCCESS.value());// 1000
			request.getSession().removeAttribute("validTimes");
		}
		else{
			message.setCode(SystemEnum.MESSAGE_ERROR.value());// 3004
			message.setMessage("验证码错误");
		}
		validTimes++;
		request.getSession().setAttribute("validTimes", validTimes);
		return message;
	}

	@Override
	public Map<String, Object> getAppVersion() {
		Map<String, Object> map = new HashMap<>();
		map.put("zycfurlIp", ZYCF_IP + ZYCFAPPVERSIONURL);
		map.put("zycfappversion", ZYCFAPPVERSION);
		return map;

	}
	
	
	@Override
	public BankReturn searchAllBanks() {
		BankReturn br=new BankReturn();
		
		br.setBanks(bankService.queryAllBanks());
		br.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
		
		return br;
	}
}
