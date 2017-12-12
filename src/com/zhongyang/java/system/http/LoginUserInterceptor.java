package com.zhongyang.java.system.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.zyfyfront.pojo.User;

public class LoginUserInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		
	try{
		LoginUserInterface loginUser = AnnotationUtils.findAnnotation(invocation.getMethod(), LoginUserInterface.class);
	
		if(loginUser == null)
			return invocation.proceed();
		
		if(loginUser.login()){
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("zycfLoginUser");
			if(user == null)
				throw new UException(SystemEnum.USER_NOLOGIN,"未登录");
		}
	}catch(Exception ex){
		
	}
		return invocation.proceed();
	}

}
