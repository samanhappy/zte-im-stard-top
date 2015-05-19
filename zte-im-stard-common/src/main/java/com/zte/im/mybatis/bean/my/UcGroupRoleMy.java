package com.zte.im.mybatis.bean.my;

import com.zte.im.mybatis.bean.UcGroup;

public class UcGroupRoleMy extends UcGroup {

	// 所属职位关系ID
	private String roleRelationId;

	public String getRoleRelationId() {
		return roleRelationId;
	}

	public void setRoleRelationId(String roleRelationId) {
		this.roleRelationId = roleRelationId;
	}

}
