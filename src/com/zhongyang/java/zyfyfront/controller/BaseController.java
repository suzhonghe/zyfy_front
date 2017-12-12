package com.zhongyang.java.zyfyfront.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.util.Message;

@Controller
public class BaseController {
	
	@Autowired
	HttpSession session;
	
	@ExceptionHandler(UException.class)
	public @ResponseBody Message exp(HttpServletRequest request,UException uException) {
		Message message =new Message();
		switch (uException.getState()) {
		case 0:
			message.setCode(uException.getCode().value());
			String mes=uException.getMessage();
			message.setMessage(mes);
			break;
		case 1:
		Exception e=uException.getException();
		Throwable th=e.getCause();
			if(th instanceof PersistenceException){
				message.setCode(SystemEnum.SERVER_REFUSED.value());
				message.setMessage("数据库连接失败");
				break;
			}else if(th instanceof NoSuchAlgorithmException){
				message.setCode(SystemEnum.DATA_SECURITY_EXCEPTION.value());
				message.setMessage("数据加密错误");
				break;
			}else if(th instanceof IOException){
				message.setCode(SystemEnum.FILE_READ_WRITE_EXCEPTION.value());
				message.setMessage("文件读写错误");
				break;
			}
			else{
				message.setCode(SystemEnum.UNKNOW_EXCEPTION.value());
				message.setMessage("未知异常,请与管理员联系");
				break;
			}		
		}
		return message;		
	}

    public static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){
        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    public static Cookie getCookieByName(HttpServletRequest request,String name){
        Map<String,Cookie> cookieMap = ReadCookieMap(request);
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            return cookie;
        }else{
            return null;
        }
    }

    public static void addCookie(HttpServletResponse response, String name, String value,int maxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        if(maxAge>0)  cookie.setMaxAge(maxAge*24*60*60);
        response.addCookie(cookie);
    }
	
}
