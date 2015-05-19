package com.zte.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zte.databinder.DeptBinder;
import com.zte.databinder.GroupBinder;
import com.zte.domain.DeptJson;
import com.zte.domain.GroupJson;
import com.zte.im.mybatis.bean.UcGroup;
import com.zte.im.mybatis.bean.UcTenant;
import com.zte.im.mybatis.bean.my.UcGroupMy;
import com.zte.im.mybatis.mapper.UcTenantMapper;
import com.zte.im.protocol.ResBase;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.util.Constant;
import com.zte.im.util.UUIDGenerator;
import com.zte.service.IGroupService;
import com.zte.util.system.PinYinUtil;

@Controller
public class GroupController extends ResBase {

	private static Logger logger = LoggerFactory
			.getLogger(GroupController.class);

	@Autowired
	private IGroupService gService;
	@Autowired
	private UcTenantMapper tMapper;

	@RequestMapping(value = "groupTree")
	@ResponseBody
	public String groupTree(@RequestParam("isRoot") String isRoot) {
		List<?> list = null;
		if (isRoot != null && "0".equals(isRoot)) {
			list = gService.findGroupTree(Constant.GROUP_DEPT);
		} else {
			list = gService.findGroupRoot();
		}
		return list2Json(list);
	}

	@RequestMapping(value = "deptList")
	@ResponseBody
	public String deptList(HttpServletRequest request, @RequestParam("isSelect") boolean isSelect) {
		if (request.getSession().getAttribute(Constant.TENANT_ID) != null) {
			String tenantId = (String) request.getSession().getAttribute(
					Constant.TENANT_ID);
			UcTenant tenant = tMapper.selectByPrimaryKey(tenantId);
			if (tenant == null) {
				return getErrorRes("获取不到公司");
			}
			List<DeptJson> deptList = new ArrayList<DeptJson>();
			DeptJson tJson = new DeptJson();
			tJson.setId(tenant.getId());
			tJson.setName(tenant.getName());
			tJson.setpId("0");
			tJson.setUrl(!isSelect ? "list.jsp?deptId=" + tenant.getId() + "&deptName=" + tenant.getName() : null);
			deptList.add(tJson);
			List<UcGroup> list = gService.deptList(tenantId,
					Constant.GROUP_DEPT);
			if (list != null && list.size() > 0) {
				for (UcGroup dept : list) {
					DeptJson json = new DeptJson();
					json.setId(dept.getId());
					json.setName(dept.getName());
					json.setpId(dept.getPid());
					// 选择部门时不设置url跳转
					json.setUrl(!isSelect ? "list.jsp?deptId=" + dept.getId() + "&deptName=" + dept.getName() : null);
					deptList.add(json);
				}
			}
			return JSONArray.toJSON(deptList).toString();
		}
		return getErrorRes("User Not Login", "2");
	}

	@RequestMapping(value = "addGroup")
	@ResponseBody
	public String addGroup(@ModelAttribute("model") GroupBinder model, HttpServletRequest request) {

		if (gService.isNameExists(model.getGtid(), model.getGpid(),
				model.getGname(), null)) {
			return getErrorRes("部门名称已存在");
		}

		try {
			UcGroup group = binder2Group(model, 1);
			group.setPinyin(PinYinUtil.getT9PinyinStr(group.getName()));
			gService.addGroup(group);
			request.getSession().setAttribute("groupId", group.getId());
		} catch (Exception e) {
			logger.error("", e);
		}
		ResponseMain main = new ResponseMain();
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "addDept")
	@ResponseBody
	public String addDept(@ModelAttribute("model") DeptBinder model,
			HttpServletRequest request) {
		String tenantId = (String) request.getSession().getAttribute(
				Constant.TENANT_ID);
		if (gService.isNameExists(tenantId, model.getDeptId(),
				model.getGname(), null)) {
			return getErrorRes("部门名称已存在");
		}

		try {
			UcGroup group = new UcGroup();
			group.setId(UUIDGenerator.randomUUID());
			group.setTenantId(tenantId);
			group.setPid(model.getDeptId());
			group.setName(model.getGname());
			group.setFullName(model.getDeptDesc());
			group.setPinyin(PinYinUtil.getT9PinyinStr(group.getName()));
			group.setSequ(model.getSequ());
			group.setTypeId(Constant.GROUP_DEPT);
			group.setCreator((String) request.getSession().getAttribute(
					Constant.USERID));
			group.setFullId(getFullId(group.getPid()) + "." + group.getId());
			gService.addGroup(group);
			request.getSession().setAttribute("groupId", group.getId());
		} catch (Exception e) {
			logger.error("", e);
		}
		ResponseMain main = new ResponseMain();
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "updateDept")
	@ResponseBody
	public String updateDept(@ModelAttribute("model") DeptBinder model,
			HttpServletRequest request) {
		String tenantId = (String) request.getSession().getAttribute(
				Constant.TENANT_ID);
		if (gService.isNameExists(tenantId, model.getDeptId(),
				model.getGname(), model.getId())) {
			return getErrorRes("部门名称已存在");
		}

		try {
			UcGroup group = new UcGroup();
			group.setId(model.getId());
			group.setTenantId(tenantId);
			group.setPid(model.getDeptId());
			group.setName(model.getGname());
			group.setFullName(model.getDeptDesc());
			group.setPinyin(PinYinUtil.getT9PinyinStr(group.getName()));
			group.setSequ(model.getSequ());
			group.setFullId(getFullId(group.getPid()) + "." + group.getId());
			gService.updateGroup(group);
		} catch (Exception e) {
			logger.error("", e);
		}
		ResponseMain main = new ResponseMain();
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "delDept")
	@ResponseBody
	public String delDept(@ModelAttribute("model") DeptBinder model) {
		try {
			gService.deleteGroup(model.getId());
		} catch (Exception e) {
			logger.error("", e);
			return getErrorRes("删除部门出错");
		}
		ResponseMain main = new ResponseMain();
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	private String getFullId(String id) {
		UcGroup group = gService.findGroupById(id);
		return group == null ? "" : group.getFullId();
	}

	@RequestMapping(value = "toEditGroup")
	@ResponseBody
	public String toEditGroup(@RequestParam("gid") String gid, Model model) {
		UcGroup group = null;
		UcGroup pgroup = null;
		UcTenant tenant = null;
		if (gid != null) {
			group = gService.findGroupById(gid);
			pgroup = gService.findGroupById(group.getPid());
			tenant = tMapper.selectByPrimaryKey(group.getPid());
		}
		if (group != null) {
			model.addAttribute("gid", gid);
			model.addAttribute("gname", group.getName());
			model.addAttribute("deptId", group.getPid());
			model.addAttribute("sequ", group.getSequ());
			model.addAttribute("fullId", group.getFullId());
			if (pgroup != null) {
				model.addAttribute("deptName", pgroup.getName());
			} else if (tenant != null) {
				model.addAttribute("deptName", tenant.getName());
			}

		}
		JSONObject resultJson = new JSONObject();
		resultJson.put("gid", gid);
		resultJson.put("gname", group.getName());
		resultJson.put("deptId", group.getPid());
		resultJson.put("sequ", group.getSequ());
		resultJson.put("fullId", group.getFullId());
		if (pgroup != null) {
			resultJson.put("deptName", pgroup.getName());
		} else if (tenant != null) {
			resultJson.put("deptName", tenant.getName());
		} else {
			resultJson.put("deptName", "中兴云服务有限公司");
		}
		logger.info(resultJson.toJSONString());
		return resultJson.toJSONString();
	}

	@RequestMapping(value = "getDept")
	@ResponseBody
	public String getDept(@RequestParam("id") String id) {
		UcGroup group = null;
		UcGroup pgroup = null;
		UcTenant tenant = null;
		if (id != null) {
			group = gService.findGroupById(id);
			if (group != null && group.getPid() != null) {
				pgroup = gService.findGroupById(group.getPid());
				tenant = tMapper.selectByPrimaryKey(group.getPid());
			}
		}

		DeptBinder dept = new DeptBinder();
		dept.setId(id);
		dept.setGname(group.getName());
		dept.setSequ(group.getSequ());
		dept.setDeptDesc(group.getFullName());
		
		if (pgroup != null) {
			dept.setDeptId(pgroup.getId());
			dept.setDeptName(pgroup.getName());
		} else if (tenant != null) {
			dept.setDeptId(tenant.getId());
			dept.setDeptName(tenant.getName());
		}

		ResponseMain main = new ResponseMain();
		main.setData(dept);
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "editGroup")
	@ResponseBody
	public String editGroup(@ModelAttribute("model") GroupBinder model) {

		if (gService.isNameExists(model.getGtid(), model.getGpid(),
				model.getGname(), model.getGid())) {
			return getErrorRes("部门名称已存在");
		}

		try {
			UcGroup group = binder2Group(model, 2);
			group.setPinyin(PinYinUtil.getT9PinyinStr(group.getName()));
			gService.updateGroup(group);
		} catch (Exception e) {
			logger.error("", e);
		}
		ResponseMain main = new ResponseMain();
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	private UcGroup binder2Group(GroupBinder model, int operator) {
		UcGroup group = null;
		if (model != null) {
			group = new UcGroup();
			if (3 == operator) {
				group.setId(model.getGid());
				return group;
			}
			group.setId(model.getGid());
			group.setName(model.getGname());
			group.setFullName(model.getGdesc());
			group.setPid(model.getGpid());
			group.setTenantId(model.getGtid());
			group.setSequ(model.getSequ());
			if (1 == operator) {
				if (model.getGid() == null) {
					group.setId(UUIDGenerator.randomUUID());
				}
				group.setTypeId(Constant.GROUP_DEPT);
				group.setPinyin("bm");
				group.setCreator("1");
			}
			group.setFullId(getFullId(group.getPid()) + "." + group.getId());
		}
		return group;
	}

	@RequestMapping(value = "delGroup")
	@ResponseBody
	public String delGroup(@ModelAttribute("model") GroupBinder model) {
		try {
			UcGroup group = binder2Group(model, 3);
			gService.deleteGroup(group.getId());
		} catch (Exception e) {
			logger.error("", e);
		}
		ResponseMain main = new ResponseMain();
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@SuppressWarnings("unchecked")
	private String list2Json(List<?> list) {
		String json = null;
		List<GroupJson> groupList = new ArrayList<GroupJson>();
		List<UcTenant> l1 = null;
		List<UcGroupMy> l2 = null;
		if (list != null) {
			if (list.get(0) instanceof UcTenant) {
				l1 = (List<UcTenant>) list;
			} else if (list.get(0) instanceof UcGroupMy) {
				l2 = (List<UcGroupMy>) list;
			}
		}
		if (l2 != null) {
			for (UcGroupMy ugm : l2) {
				GroupJson gj = new GroupJson();
				gj.setId(ugm.getId());
				gj.setText(ugm.getName());
				gj.setChildren(null);
				if ("".equals(ugm.getPid())) {
					gj.setParent(ugm.getTenantId());
				} else {
					gj.setParent(ugm.getPid());
				}
				gj.setFullId(ugm.getFullId());

				groupList.add(gj);
			}
			json = JSONArray.toJSON(groupList).toString();
		} else if (l1 != null) {
			for (UcTenant ugm : l1) {
				GroupJson gj = new GroupJson();
				gj.setId(ugm.getId());
				gj.setText(ugm.getName());
				gj.setChildren(true);
				groupList.add(gj);
			}
			json = JSONArray.toJSON(groupList).toString();
		}

		return json;
	}

	public static void main(String[] args) {
		List<UcGroupMy> list = new ArrayList<UcGroupMy>();
		UcGroupMy um = new UcGroupMy();
		um.setId("1");
		list.add(um);
		String json = JSONArray.toJSON(list).toString();
		logger.info(json);
	}
}