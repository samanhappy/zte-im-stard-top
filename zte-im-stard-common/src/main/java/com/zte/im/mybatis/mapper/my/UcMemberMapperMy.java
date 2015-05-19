package com.zte.im.mybatis.mapper.my;

import java.util.List;

import com.zte.databinder.MemberBinder;
import com.zte.databinder.UserPermBinder;
import com.zte.im.mybatis.bean.my.UcGroupRoleMy;
import com.zte.im.mybatis.bean.my.UcMemberMy;

public interface UcMemberMapperMy {

	public List<UcMemberMy> getMember4Group(MemberBinder param);

	public int getMemberCount4Group(MemberBinder param);

	public List<UcGroupRoleMy> getRole4Member(String memberId);

	public List<UcMemberMy> getAllUserListByTenantId(UserPermBinder user);
	
	// 查询人员
	public UcMemberMy getUcMemberById(String id);

}
