package com.zhongyang.java.zyfyfront.service;

import java.util.List;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.pojo.User;

public interface UserService {
	 
	 public int addUser(User user);
	 
	 public User queryUserByParams(User user);
	 
	 public void modifyUserByParams(User user);
	 
	 public List<User>queryUserByPage(Page<User>page);
}
