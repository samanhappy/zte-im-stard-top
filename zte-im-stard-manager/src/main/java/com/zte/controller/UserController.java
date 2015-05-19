package com.zte.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.zte.client.impl.ClientHandleImpl;
import com.zte.databinder.ClientBinder;
import com.zte.databinder.MemberBinder;
import com.zte.databinder.UserBinder;
import com.zte.im.bean.Role;
import com.zte.im.bean.User;
import com.zte.im.mybatis.bean.UcGroup;
import com.zte.im.mybatis.bean.page.MemberPageModel;
import com.zte.im.protocol.Page;
import com.zte.im.protocol.ResBase;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.service.IUserService;
import com.zte.im.service.impl.RoleServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.Constant;
import com.zte.im.util.PathUtil;
import com.zte.service.IGroupMemberService;
import com.zte.service.IGroupService;
import com.zte.util.system.FileSaver;

@Controller
public class UserController extends ResBase {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@Autowired
	private IGroupMemberService gmService;

	@Autowired
	private IGroupService gService;

	@RequestMapping(value = "groupMember")
	@ResponseBody
	public String groupMember(@ModelAttribute("model") MemberBinder model, HttpServletRequest request) {

		String tenantId = (String) request.getSession().getAttribute(Constant.TENANT_ID);
		model.setTenantId(tenantId);

		if (model.getGroupId() != null) {
			request.getSession().setAttribute("groupId", model.getGroupId());
			UcGroup group = gService.findGroupById(model.getGroupId());
			if (group != null) {
				request.getSession().setAttribute("deptName", group.getName());
			}
		} else {
			request.getSession().removeAttribute("groupId");
			request.getSession().removeAttribute("deptName");
		}

		MemberPageModel mpm = gmService.getMemberForGroup(model);
		ResponseMain main = new ResponseMain();
		main.setMembers(mpm);
		Page page = new Page();
		page.setCurrentPage(mpm.getCurrentPage());
		page.setPages(mpm.getTotalPage());
		page.setPernum(mpm.getPageSize());
		page.setStart(mpm.getFirst());
		page.setEnd(mpm.getLast());
		page.setTotal(mpm.getTotalRecord());
		main.setPage(page);
		String resultJson = JSON.toJSONStringWithDateFormat(main, "yyyy-MM-dd");
		logger.info(resultJson);

		return resultJson;
	}

	@RequestMapping(value = "groupMemberCount")
	@ResponseBody
	public String groupMemberCount(@ModelAttribute("model") MemberBinder model, HttpServletRequest request) {
		String tenantId = (String) request.getSession().getAttribute(Constant.TENANT_ID);
		model.setTenantId(tenantId);
		long count = gmService.getMemberCountForGroup(model);
		ResponseMain main = new ResponseMain();
		main.setCount(count);
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "getUser")
	@ResponseBody
	public String getUser(@RequestParam("id") String id, HttpServletRequest request) {
		String tenantId = (String) request.getSession().getAttribute(Constant.TENANT_ID);
		ResponseMain main = new ResponseMain();
		main.setData(gmService.getUcMemberById(id, tenantId));
		String resultJson = JSON.toJSONStringWithDateFormat(main, "yyyy-MM-dd");
		logger.info(resultJson);
		return resultJson;
	}

	/**
	 * 返回职位列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "posList")
	@ResponseBody
	public String posList(HttpServletRequest request) {
		List<UcGroup> groupList = gmService.getGroupByTenantId(
				(String) request.getSession().getAttribute(Constant.TENANT_ID), Constant.GROUP_POSITION);
		ResponseMain main = new ResponseMain();
		main.setItem(groupList);
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "addUser", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String addUser(@ModelAttribute("user") UserBinder user, HttpServletRequest request) {
		user.setTenantId((String) request.getSession().getAttribute(Constant.TENANT_ID));
		ResponseMain main = new ResponseMain();
		String error = gmService.saveOrUpdateUser(user);
		request.getSession().setAttribute("groupId", user.getDeptId());
		UcGroup group = gService.findGroupById(user.getDeptId());
		if (group != null) {
			request.getSession().setAttribute("deptName", group.getName());
		}
		if (error != null) {
			return getErrorRes(error);
		}
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "removeUser")
	@ResponseBody
	public String removeUser(@RequestParam("unames") String unames, @RequestParam("uids") String uids) {
		ResponseMain main = new ResponseMain();
		if (uids != null && !"".equals(uids)) {
			if (uids.endsWith(",")) {
				uids = uids.substring(0, uids.length() - 1);
			}
			if (!gmService.removeUser(Arrays.asList(uids.split(",")))) {
				return getErrorRes("操作失败");
			}
		}
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "updateUser")
	@ResponseBody
	public String updateUser(@ModelAttribute("user") UserBinder user) {

		if (user.getId() == null || "".equals(user.getId()) && user.getUsable() == null || "".equals(user.getUsable())) {
			return getErrorRes("user id can not be null");
		}

		ResponseMain main = new ResponseMain();
		if (!gmService.updateUserState(user.getId(), user.getUsable())) {
			return getErrorRes("保存失败");
		}
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "resetUserPwd")
	@ResponseBody
	public String resetUserPwd(@ModelAttribute("user") UserBinder user) {

		if (StringUtils.isBlank(user.getId())) {
			return getErrorRes("user id can not be null");
		}

		ResponseMain main = new ResponseMain();
		if (!gmService.resetUserPwd(user.getId())) {
			return getErrorRes("操作失败");
		}
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "uploadFile", produces = "text/html")
	@ResponseBody()
	public String uploadFile(@RequestParam("photo") MultipartFile photo) {
		if (photo == null) {
			return getErrorRes("file can not be null");
		}
		ResponseMain main = new ResponseMain();
		String fileUrl = FileSaver.save(photo, main);
		if (fileUrl == null || "".equals(fileUrl)) {
			return getErrorRes("fail to save file");
		}

		main.setFileUrl(fileUrl);
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "uploadClient", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String uploadClient(@RequestParam("photo") MultipartFile photo) {
		if (photo == null) {
			return getErrorRes("file can not be null");
		}
		ResponseMain main = new ResponseMain();
		String fileUrl = FileSaver.save(photo, main);
		if (fileUrl == null || "".equals(fileUrl)) {
			return getErrorRes("fail to save file");
		}
		String os = (String) System.getProperties().get("os.name");
		String path = PathUtil.getPath(0) + photo.getOriginalFilename();
		String tmpPath = PathUtil.getPath(0) + System.currentTimeMillis();
		String apktoolPath = "/usr/lib/apk2android";
		if (os.toLowerCase().contains("window")) {
			apktoolPath = "C:\\apk2java\\apk2android";
		}

		ClientBinder cb = ClientHandleImpl.getInstance().handleClientPackage(path, tmpPath, apktoolPath);
		if (photo.getOriginalFilename().toLowerCase().endsWith("apk"))
			cb.setCos("android");
		else if (photo.getOriginalFilename().toLowerCase().endsWith("ipa"))
			cb.setCos("ios");
		cb.setCapk(photo.getOriginalFilename());
		try {
			main.setFileUrl(fileUrl + "?attname=" + URLEncoder.encode(cb.getCname(), "utf-8")
					+ (cb.getCos().equals("android") ? ".apk" : ".ipa"));
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
		}
		main.setData(cb);
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping("getPrivileges")
	@ResponseBody
	public String getPrivileges(HttpServletRequest request) throws Exception {
		if (request.getSession().getAttribute("userid") != null) {
			if (request.getSession().getAttribute("privileges") != null) {
				return JSON.toJSONString(request.getSession().getAttribute("privileges"));
			}
			IUserService service = UserServiceImpl.getInstance();
			User user = service.getUserbyJid(request.getSession().getAttribute("userid").toString());
			if (user != null) {
				Role role = RoleServiceImpl.getInstance().getRole4User(user.getId());
				if (role != null) {
					return JSON.toJSONString(role.getPrivileges());
				}
			}
		}
		return "";
	}

}