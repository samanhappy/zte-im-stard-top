package com.zte.service;

import java.util.List;

import com.zte.databinder.MemberBinder;
import com.zte.databinder.UserBinder;
import com.zte.databinder.UserPermBinder;
import com.zte.im.mybatis.bean.UcGroup;
import com.zte.im.mybatis.bean.my.UcMemberMy;
import com.zte.im.mybatis.bean.my.UcTenantMy;
import com.zte.im.mybatis.bean.page.MemberPageModel;

public interface IGroupMemberService {

	// 查询公司的群组，根据typeId区分机构和职位
	public List<UcGroup> getGroupByTenantId(String tenantId, String typeId);

	// 查询公司的群组，根据name和typeId查询职位或机构
	public List<UcGroup> getGroupByName(String tenantId, String name,
			String typeId);

	// 查询公司的群组，根据typeId区分机构和职位
	public UcGroup getGroupByTenantId(String tenantId, String typeId,
			String name);

	// 获取公司组织机构树结构Json
	public String getOrgGroupJsonByTenantId(String tenantId);

	// 获取多公司组织机构树结构Json
	public String getOrgGroupJsonByTenantId();

	// 查询组织机构的人员
	public MemberPageModel getMemberForGroup(MemberBinder mb);
	
	// 查询组织机构的人员
	public long getMemberCountForGroup(MemberBinder mb);
	
	// 查询人员
	public UcMemberMy getUcMemberById(String id, String tenantId);

	// 查询用户职位角色
	public void getRole4Member(UcMemberMy member, UcTenantMy tenant);

	public String saveOrUpdateUser(UserBinder user, boolean isImport);

	public String saveOrUpdateUser(UserBinder user);

	public boolean removeUser(List<String> uids);

	public boolean updateUserState(String uid, String usable);
	
	public boolean resetUserPwd(String uid);

	public List<UcMemberMy> getAllUserListByTenantId(UserPermBinder user);

}
