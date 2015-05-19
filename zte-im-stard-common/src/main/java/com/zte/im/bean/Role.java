package com.zte.im.bean;

import java.util.List;

import com.google.gson.annotations.Expose;

public class Role {

	@Expose
	private String roleId;
	@Expose
	private String roleName;
	@Expose
	private String desc;
	@Expose
	private List<User> userList;
	@Expose
	private List<String> privileges;

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<String> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<String> privileges) {
		this.privileges = privileges;
	}

}
