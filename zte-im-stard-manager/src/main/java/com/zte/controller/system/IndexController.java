package com.zte.controller.system;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zte.im.bean.Role;
import com.zte.im.bean.User;
import com.zte.im.mybatis.bean.Account;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.service.IAccountService;
import com.zte.im.service.IUserService;
import com.zte.im.service.impl.AccountServiceImpl;
import com.zte.im.service.impl.RoleServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.Constant;
import com.zte.im.util.EncryptionDecryption;
import com.zte.im.util.IPUtil;
import com.zte.pagepath.SystemPagePath;

@Controller
public class IndexController {

	private static final String EMPTY = "";
	private static final String NO_PRIVILEGES = "没有权限访问，请联系管理员";
	private static final String NAMR_OR_PWD_ERROR = "用户名密码不正确";
	private static final String PWD = "password";
	private static final String NAME = "name";
	private static final String CLIENT_IP = "clientIP";
	private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping(value = "login", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String login(HttpServletRequest request) throws Exception {

		String clientIpAddr = IPUtil.getClientIpAddr(request);
		logger.info("client ip:{}", clientIpAddr);
		request.getSession().setAttribute(CLIENT_IP, clientIpAddr);

		String name = ServletRequestUtils.getStringParameter(request, NAME, EMPTY);
		String password = ServletRequestUtils.getStringParameter(request, PWD, EMPTY);
		ResponseMain main = new ResponseMain();
		if (!name.equals(EMPTY) && !password.equals(EMPTY)) {
			IAccountService accService = AccountServiceImpl.getInstance();
			Account acc = accService.get(name);
			if (acc != null && StringUtils.equals(password, acc.getPwd())) {
				// 超级管理员
				request.getSession().setAttribute(Constant.USERNAME, "管理员");
				request.getSession().setAttribute(Constant.USERJID, "");
				request.getSession().setAttribute(Constant.USERID, acc.getName());
				request.getSession().setAttribute(Constant.ROLE_NAME, acc.getRole());
				request.getSession().setAttribute(Constant.PRIVILEGES, acc.getPrivileges());
				request.getSession().setAttribute(Constant.TENANT_ID, acc.getTenantId());
				request.getSession().setAttribute(Constant.ERROR, null);
				main.setResult(true);
				return JSON.toJSONString(main);
			} else {
				IUserService userService = UserServiceImpl.getInstance();
				User user = userService.getUserbyJid(name);
				if (user != null) {
					EncryptionDecryption enc = new EncryptionDecryption();
					if (user.getPwd().equalsIgnoreCase(enc.encrypt(password))) {
						Role role = RoleServiceImpl.getInstance().getRole4User(user.getId());
						if (role != null) {
							request.getSession().setAttribute(Constant.USERNAME, user.getName());
							request.getSession().setAttribute(Constant.USERJID, user.getJid());
							request.getSession().setAttribute(Constant.USERID, name);
							request.getSession().setAttribute(Constant.ROLE_NAME, role.getRoleName());
							request.getSession().setAttribute(Constant.PRIVILEGES, null);
							request.getSession().setAttribute(Constant.ERROR, null);
							request.getSession().setAttribute(Constant.TENANT_ID, "c1");
							main.setResult(true);
							return JSON.toJSONString(main);
						} else {
							request.getSession().setAttribute(Constant.USERNAME, null);
							request.getSession().setAttribute(Constant.USERJID, null);
							request.getSession().setAttribute(Constant.USERID, null);
							request.getSession().setAttribute(Constant.PRIVILEGES, null);
							request.getSession().setAttribute(Constant.ERROR, NO_PRIVILEGES);
							request.getSession().setAttribute(Constant.TENANT_ID, null);
							main.setResult(false);
							main.setMsg(NO_PRIVILEGES);
							return JSON.toJSONString(main);
						}
					}
				}
			}
		}
		request.getSession().setAttribute(Constant.USERNAME, null);
		request.getSession().setAttribute(Constant.USERJID, null);
		request.getSession().setAttribute(Constant.USERID, null);
		request.getSession().setAttribute(Constant.PRIVILEGES, null);
		request.getSession().setAttribute(Constant.ERROR, NAMR_OR_PWD_ERROR);
		request.getSession().setAttribute(Constant.TENANT_ID, null);
		main.setResult(false);
		main.setMsg(NAMR_OR_PWD_ERROR);
		return JSON.toJSONString(main);
	}

	@RequestMapping("logout")
	public String logout(HttpServletRequest request) throws Exception {
		request.getSession().invalidate();
		return SystemPagePath.LOGIN_REDIRECT;
	}

}
