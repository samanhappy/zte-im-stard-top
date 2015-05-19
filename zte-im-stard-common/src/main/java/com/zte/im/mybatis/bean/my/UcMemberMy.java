package com.zte.im.mybatis.bean.my;

import com.zte.im.mybatis.bean.UcMember;

public class UcMemberMy extends UcMember {

	// 所属部门关系Id
	private String deptRelationId;

	// 所属部门ID
	private String deptId;

	// 所属部门
	private String deptName;

	// 所属职位关系ID
	private String roleRelationId;

	// 所属职位
	private String roleName;

	// 所属职位ID
	private String roleId;

	public String getDeptRelationId() {
		return deptRelationId;
	}

	public void setDeptRelationId(String deptRelationId) {
		this.deptRelationId = deptRelationId;
	}

	public String getRoleRelationId() {
		return roleRelationId;
	}

	public void setRoleRelationId(String roleRelationId) {
		this.roleRelationId = roleRelationId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
