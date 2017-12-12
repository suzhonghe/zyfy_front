package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.pojo.User;

public interface UserDao {	
		
	public int insertUser(User user);
	
	public User selectUserByParams(User user);
	
	public int updateUserByParams(User user);
	
	public List<User>selectUserByPage(Page<User>page);
}
