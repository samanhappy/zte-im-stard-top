package com.zte.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zte.im.bean.Role;
import com.zte.im.protocol.ResBase;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.service.IRoleService;
import com.zte.im.service.impl.RoleServiceImpl;

@Controller
public class RoleManageController extends ResBase {

	private static Logger logger = LoggerFactory
			.getLogger(RoleManageController.class);

	private IRoleService service = RoleServiceImpl.getInstance();

	@RequestMapping(value = "saveOrUpdateRole")
	@ResponseBody
	public String saveOrUpdateRole(@ModelAttribute("role") Role role) {
		if (role.getRoleName() == null || "".equals(role.getRoleName())) {
			return getErrorRes("角色名称不能为空");
		}
		service.saveOrUpdateRole(role);
		ResponseMain main = new ResponseMain();
		main.setRoleId(role.getRoleId());
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "listRole")
	@ResponseBody
	public String listRole() {
		ResponseMain main = new ResponseMain();
		main.setItem(service.listRole());
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}
	
	@RequestMapping(value = "getRole")
	@ResponseBody
	public String getRole(@RequestParam("id") String id) {
		ResponseMain main = new ResponseMain();
		main.setData(service.getRole(id));
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "removeRole")
	@ResponseBody
	public String removeRole(@ModelAttribute("role") Role role) {
		if (role == null || StringUtils.isEmpty(role.getRoleId())) {
			return getErrorRes("roleId不能为空");
		}
		service.removeRole(role.getRoleId());
		ResponseMain main = new ResponseMain();
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

}
