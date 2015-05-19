package com.zte.job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zte.databinder.UserBinder;
import com.zte.databinder.UserPermBinder;
import com.zte.im.bean.Param;
import com.zte.im.mdm.HttpClientForMDM;
import com.zte.im.mdm.MDMParam;
import com.zte.im.mdm.MDMResp;
import com.zte.im.mdm.MDMUser;
import com.zte.im.mybatis.bean.UcGroup;
import com.zte.im.mybatis.bean.UcGroupMember;
import com.zte.im.mybatis.bean.UcMember;
import com.zte.im.mybatis.bean.UcMemberExample;
import com.zte.im.mybatis.bean.my.UcMemberMy;
import com.zte.im.mybatis.mapper.UcGroupMapper;
import com.zte.im.mybatis.mapper.UcGroupMemberMapper;
import com.zte.im.mybatis.mapper.UcMemberMapper;
import com.zte.im.service.IParamSettingService;
import com.zte.im.service.impl.ParamSettingServiceImpl;
import com.zte.im.util.Constant;
import com.zte.im.util.IDSequence;
import com.zte.im.util.UUIDGenerator;
import com.zte.service.IGroupMemberService;
import com.zte.service.IGroupService;
import com.zte.util.system.PinYinUtil;

@Service
public class UserSyncJob {

	private static Logger logger = LoggerFactory.getLogger(UserSyncJob.class);

	IParamSettingService service = ParamSettingServiceImpl.getInstance();

	@Autowired
	IGroupMemberService gmService;

	@Autowired
	private UcGroupMapper groupMapper;

	@Autowired
	private UcMemberMapper memberMapper;

	@Autowired
	private UcGroupMemberMapper groupMemberMapper;

	@Autowired
	IGroupService groupService;

	private String tenantId = "c1";

	public void execute() {
		Param param = service.getParamByType("system");
		if (param != null && "THIRD".equals(param.getLoginAuthType())) {
			logger.info("user sync start...");
			MDMParam p = new MDMParam();
			MDMResp resp = HttpClientForMDM.getInstance().request(p);
			if (resp != null) {
				if ("ok".equalsIgnoreCase(resp.result)) {
					if (resp.data != null && resp.data.size() > 0) {
						Set<String> syncUidSet = new HashSet<String>();
						// 根据sort_id排序
						Collections.sort(resp.data);
						for (MDMUser u : resp.data) {
							if (u.longid != null && u.org_account != null
									&& u.org_department != null
									&& u.org_level != null
									&& !"".equals(u.org_level)) {
								try {
									UserBinder ub = trans2UserBinder(u);
									gmService.saveOrUpdateUser(ub, true);
									if (ub.getId() != null) {
										syncUidSet.add(ub.getId());
									}
								} catch (Exception e) {
									logger.error("", e);
								}
								logger.info("handle user by longid:{}",
										u.longid);
							} else {
								logger.info("user field is missing");
							}
						}

						// 删除非同步过来的数据
						logger.info("sync user size:{}", syncUidSet.size());
						if (syncUidSet.size() > 0) {
							UserPermBinder user = new UserPermBinder();
							user.setTenantId(tenantId);
							List<UcMemberMy> userList = gmService
									.getAllUserListByTenantId(user);
							logger.info("db user size:{}", userList.size());
							List<String> removeUids = new ArrayList<String>();
							for (UcMemberMy mbm : userList) {
								if (mbm != null && mbm.getId() != null
										&& !syncUidSet.contains(mbm.getId())) {
									removeUids.add(mbm.getId());
									logger.info(
											"remove user for id{} and name{}",
											mbm.getId(), mbm.getCn());
								}
							}
							if (removeUids.size() > 0) {
								gmService.removeUser(removeUids);
							}
						}
					} else {
						logger.info("resp user is null or size is 0");
					}
				} else {
					logger.info("resp result is not ok");
				}
			} else {
				logger.info("resp from mdm is null");
			}
			logger.info("user sync end...");
		} else {
			logger.info("LoginAuthType is not THIRD, not to do user sync");
		}
	}

	UserBinder trans2UserBinder(MDMUser user) throws Exception {

		UserBinder ub = new UserBinder();

		/* 公司 */
		ub.setTenantId(tenantId);

		/* 工号 */
		UcMemberExample example = new UcMemberExample();
		example.createCriteria().andLEqualTo(user.login_name);
		// example.createCriteria().andUidEqualTo(user.longid.toString());
		List<UcMember> list = memberMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			ub.setId(list.get(0).getId());
			ub.setUid(list.get(0).getUid());
		} else {
			ub.setUid(String.valueOf(IDSequence.getGroupId()));
		}

		/* 姓名 */
		ub.setCn(user.name);

		/* 部门 */
		List<UcGroup> deptList = gmService.getGroupByName(tenantId,
				user.org_department, Constant.GROUP_DEPT);
		UcGroup dept = null;
		if (deptList == null || deptList.size() == 0) {
			dept = new UcGroup();
			dept.setId(UUIDGenerator.randomUUID());
			dept.setName(user.org_department);
			dept.setPid(tenantId);
			dept.setTenantId(tenantId);
			dept.setSequ(1000L); // 默认靠后排序
			dept.setTypeId(Constant.GROUP_DEPT);
			dept.setCreator("usersync");
			dept.setFullId("." + dept.getId());
			dept.setPinyin(PinYinUtil.getT9PinyinStr(dept.getName()));
			groupService.addGroup(dept);
		} else {
			dept = deptList.get(0);
		}
		ub.setDeptId(dept.getId());

		if (ub.getId() != null) {
			List<UcGroupMember> ugmList = groupService.getGroupMember4User(
					tenantId, Constant.GROUP_DEPT, ub.getId());
			if (ugmList != null && ugmList.size() > 0) {
				ub.setDeptRelationId(ugmList.get(0).getId());
				// 删除可能重复的关系数据
				if (ugmList.size() > 1) {
					for (int i = 1; i < ugmList.size(); i++) {
						groupMemberMapper.deleteByPrimaryKey(ugmList.get(i)
								.getId());
					}
				}
			}
		}

		/* 性别 */
		ub.setSex(user.gender);

		/* 职级 */
		List<UcGroup> postList = gmService.getGroupByName(tenantId,
				user.org_level, Constant.GROUP_POSITION);
		UcGroup pos = null;
		if (postList == null || postList.size() == 0) {
			pos = new UcGroup();
			pos.setId(UUIDGenerator.randomUUID());
			pos.setName(user.org_level);
			pos.setPid("");
			pos.setTenantId(tenantId);
			pos.setSequ(0L);
			pos.setTypeId(Constant.GROUP_POSITION);
			pos.setCreator("usersync");
			pos.setPinyin(PinYinUtil.getT9PinyinStr(user.org_level));
			groupMapper.insertSelective(pos);
		} else {
			pos = postList.get(0);
		}
		ub.setPos(pos.getId());
		ub.setPosDisplay(user.org_level);

		if (ub.getId() != null) {
			List<UcGroupMember> posList = groupService.getGroupMember4User(
					tenantId, Constant.GROUP_POSITION, ub.getId());
			if (posList != null && posList.size() > 0) {
				ub.setRoleRelationId(posList.get(0).getId());
				// 删除可能重复的关系数据
				if (posList.size() > 1) {
					for (int i = 1; i < posList.size(); i++) {
						groupMemberMapper.deleteByPrimaryKey(posList.get(i)
								.getId());
					}
				}
			}
		}

		/* 生日 */
		if (user.birthday != null && !"".equals(user.birthday)) {
			ub.setBirthday(user.birthday);
		}

		/* 手机 */
		if (user.tel_number != null) {
			ub.setMobile(user.tel_number);
		}
		
		/* 固定电话 */
		if (user.officePhone != null) {
			ub.setHometelephone(user.officePhone);
		}

		/* 邮箱 */
		ub.setMail(user.email_address);

		/* 登录账户 */
		ub.setL(user.login_name);

		/* 密码 */
		ub.setPassword("123456");

		/* 排序 */
		ub.setSequ(user.sort_id);

		return ub;
	}

}
