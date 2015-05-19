package com.zte.weibo.job;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zte.im.util.Constant;
import com.zte.im.util.EncryptionDecryption;
import com.zte.weibo.bean.Dept;
import com.zte.weibo.bean.User;
import com.zte.weibo.common.mdm.HttpClientForMDM;
import com.zte.weibo.common.mdm.MDMParam;
import com.zte.weibo.common.mdm.MDMResp;
import com.zte.weibo.common.mdm.MDMUser;
import com.zte.weibo.service.AccountService;
import com.zte.weibo.service.DeptService;

public class UserSyncJob {

	private static Long gid = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(UserSyncJob.class);

	@Resource
	private AccountService accountService;
	
	@Resource
	private DeptService deptService;
	
	public void execute() {
		logger.info("user sync start...");
		MDMParam param = new MDMParam();
		MDMResp resp = HttpClientForMDM.getInstance().request(param);
		if (resp != null) {
			if ("ok".equalsIgnoreCase(resp.result)) {
				if (resp.data != null && resp.data.size() > 0) {
					for (MDMUser u : resp.data) {
						if (u.longid != null && u.org_account != null && u.org_department != null
								&& u.org_level != null && !"".equals(u.org_level)) {
							try {
								User user = trans2User(u);
								// 此处调用的addUser方法需要保存分享需要的所有字段，并且保证uid唯一
								accountService.addOrUpdate(user);
							} catch (Exception e) {
								logger.error("", e);
							}
							logger.info("handle user by longid:{}", u.longid);
						} else {
							logger.info("user field is missing");
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
	}

	private User trans2User(MDMUser user) throws Exception {

		if(null == user ){
			return null;
		}
		User u = new User();
		// Gid暂时写死
		u.setGid(UserSyncJob.gid);

		/* 工号 :此处需要根据login_name判断用户是否存在，存在使用原来的uid，不存在生成uid */
		/*
		 * UcMemberExample example = new UcMemberExample();
		 * example.createCriteria().andLEqualTo(user.login_name); List<UcMember>
		 * list = memberMapper.selectByExample(example); if (list != null &&
		 * list.size() > 0) { u.setId(list.get(0).getId());
		 * u.setUid(list.get(0).getUid()); } else {
		 * u.setUid(IDSequence.getGroupId()); }
		 */

		/* 姓名 */
		u.setName(user.name);
		u.setSname(user.name);

		/* 部门:此处需要根据org_department判断部门是否存在，不存在生成新的部门 */
		/*
		 * List<UcGroup> deptList = gmService.getGroupByName(tenantId,
		 * user.org_department, Constant.GROUP_DEPT); UcGroup dept = null; if
		 * (deptList == null || deptList.size() == 0) { dept = new UcGroup();
		 * dept.setId(UUIDGenerator.randomUUID());
		 * dept.setName(user.org_department); dept.setPid(tenantId);
		 * dept.setTenantId(tenantId); dept.setSequ(1000L); // 默认靠后排序
		 * dept.setTypeId(Constant.GROUP_DEPT); dept.setCreator("usersync");
		 * dept.setFullId("." + dept.getId());
		 * dept.setPinyin(PinYinUtil.getT9PinyinStr(dept.getName()));
		 * groupService.addGroup(dept); } else { dept = deptList.get(0); }
		 * u.setDeptId(dept.getId());
		 */

		if(StringUtils.isNotEmpty(user.org_department)){
			List<Dept> depts = deptService.getListByName(user.org_department);
			Dept dept = null;
			if(null != depts && !depts.isEmpty()){//部门存在
				for (Dept temp : depts) {//找到第一个不为空的
					if(null != temp ){
						dept = temp;
						break;
					}
				}
				
			}else{//部门不存在,则增加部门
				dept = new Dept();
				dept.setGid(gid);
				dept.setName(user.org_department);
				dept.setIsroot(1);
				dept.setNodeType(0);
				dept.setSequ(10000L);
				
				deptService.add(dept);
			}
			if(null != dept){
				u.setDeptId(dept.getId());
				u.setCid(dept.getCid());
				u.setCname(dept.getName());
			}
		}
		
		/* 性别 */
		u.setSex(user.gender);

		/* 职级 */
		u.setPost(user.org_post);

		/* 生日 */
		u.setBirthday(user.birthday);

		/* 手机 */
		u.setMob(user.tel_number);

		/* 固定电话 */
		u.setTelephone(user.officePhone);

		/* 邮箱 */
		u.setEmail(user.email_address);

		
		u.setPwd(new EncryptionDecryption().encrypt(user.passWord));
		u.setLoginname(user.login_name);
		u.setIsDelete(user.is_deleted);
		u.setSortId(user.sort_id);

		return u;
	}

}
