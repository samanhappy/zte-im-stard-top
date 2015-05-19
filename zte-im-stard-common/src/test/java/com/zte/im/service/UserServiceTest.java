package com.zte.im.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zte.im.bean.User;
import com.zte.im.service.impl.UserServiceImpl;

public class UserServiceTest {
	
	private final static Logger logger = LoggerFactory
			.getLogger(UserServiceTest.class);

	IUserService userService = UserServiceImpl.getInstance();
	Long testUid = 14L;

	@Test
	public void testUpdateUser() {
		User user = userService.getUserbyid(testUid);
		logger.info("autograph:" + user.getAutograph());

		user.setAutograph("我的世界我做主");
		userService.updateUser(user);

		user = userService.getUserbyid(testUid);
		logger.info("autograph:" + user.getAutograph());

	}

}
