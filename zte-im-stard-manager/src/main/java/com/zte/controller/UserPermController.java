package com.zte.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zte.databinder.UserPermBinder;
import com.zte.im.bean.User;
import com.zte.im.mybatis.bean.UserPermission;
import com.zte.im.mybatis.bean.my.UcMemberMy;
import com.zte.im.protocol.ResBase;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.service.IUserService;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.Constant;
import com.zte.service.IGroupMemberService;
import com.zte.service.IUserPermService;

@Controller
public class UserPermController extends ResBase {

	private static Logger logger = LoggerFactory
			.getLogger(UserPermController.class);

	@Autowired
	private IUserPermService userPermService;

	@Autowired
	private IGroupMemberService gmService;

	private IUserService userService = UserServiceImpl.getInstance();

	@RequestMapping(value = "listUserPerm")
	@ResponseBody
	public String listUserPerm(@ModelAttribute("user") UserPermBinder user) {
		if (user == null || user.getUid() == null || "".equals(user.getUid())) {
			return getErrorRes("uid can not be null or empty");
		}
		ResponseMain main = new ResponseMain();
		User mUser = userService.getUserbySqlId(user.getUid());
		if (mUser != null && mUser.getIsProtected() != null) {
			main.setProtected(mUser.getIsProtected());
		}

		List<UserPermission> userPermList = userPermService
				.getUserPermList(user.getUid());
		main.setItem(userPermList);
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "updateUserPerm")
	@ResponseBody
	public String updateUserPerm(@ModelAttribute("user") UserPermBinder user) {
		if (user == null || user.getUid() == null || "".equals(user.getUid())) {
			return getErrorRes("uid can not be null or empty");
		}

		if (!userPermService.updateUserProtected(user.getUid(),
				user.getIsProtected())) {
			return getErrorRes("update user protected failed");
		}

		if (!userPermService.updateUserPerm(user.getUid(), user.getPermList())) {
			return getErrorRes("update user permission failed");
		}

		ResponseMain main = new ResponseMain();
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "listUser")
	@ResponseBody
	public String listUser(@ModelAttribute("user") UserPermBinder user, HttpServletRequest request) {
		user.setTenantId((String) request.getSession().getAttribute(
				Constant.TENANT_ID));
		List<UcMemberMy> userList = gmService.getAllUserListByTenantId(user);
		ResponseMain main = new ResponseMain();
		main.setItem(userList);

		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

}
