package com.zte.im.mybatis.mapper.my;

import java.util.List;
import java.util.Map;

import com.zte.im.mybatis.bean.UcGroupMember;
import com.zte.im.mybatis.bean.UcTenant;
import com.zte.im.mybatis.bean.my.UcGroupMy;

public interface UcGroupMapperMy {

	public List<UcTenant> selectGroupTree(String typeId);

	public List<UcGroupMy> selectGroupRoot();

	public List<UcGroupMember> getGroupMemberByUid(Map<String, String> params);

}
