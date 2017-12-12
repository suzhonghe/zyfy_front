package com.zhongyang.java.zyfyfront.params;

import com.zhongyang.java.zyfyfront.pojo.User;

/**
 * 
* @Title: UserParams.java 
* @Package com.zhongyang.java.zyfy.params 
* @Description:用户参数扩展类 
* @author 苏忠贺   
* @date 2017年6月1日 下午3:18:48 
* @version V1.0
 */
public class UserParams {
	
	private String newPassWord;
	
	private String oldPassWord;
	
	private User user;
	
	private UserVo userVo;

	public String getNewPassWord() {
		return newPassWord;
	}

	public void setNewPassWord(String newPassWord) {
		this.newPassWord = newPassWord;
	}

	public String getOldPassWord() {
		return oldPassWord;
	}

	public void setOldPassWord(String oldPassWord) {
		this.oldPassWord = oldPassWord;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}
	
}
