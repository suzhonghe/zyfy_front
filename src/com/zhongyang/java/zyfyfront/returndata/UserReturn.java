package com.zhongyang.java.zyfyfront.returndata;

import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.zyfyfront.pojo.User;

/**
 * 
* @Title: UserRes.java 
* @Package com.zhongyang.java.zyfy.results 
* @Description:用户返回结构扩展类
* @author 苏忠贺   
* @date 2017年6月1日 下午3:15:15 
* @version V1.0
 */
public class UserReturn {
	
	private User user;
	
	private String userName;
	
	private Message message;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
}
