package com.zhongyang.java.zyfyfront.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.dao.UserDao;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.pojo.User;
import com.zhongyang.java.zyfyfront.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private static Logger logger=Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public int addUser(User user) {
		return userDao.insertUser(user);
	}

	@Override
	public User queryUserByParams(User user) {
		return userDao.selectUserByParams(user);
	}

	@Override
	@Transactional
	public void modifyUserByParams(User user) {
		int userCount = userDao.updateUserByParams(user);
		if (userCount <= 0)
			logger.info(user.getId()+"用户数据修改入库操作失败"+user);
	}

	@Override
	public List<User> queryUserByPage(Page<User> page) {
		return userDao.selectUserByPage(page);
	}

	
}
