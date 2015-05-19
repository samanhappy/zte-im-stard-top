package com.zte.im.service;

import java.util.List;

import com.zte.im.bean.Role;

public interface IRoleService {

	public void saveOrUpdateRole(Role role);

	public List<Role> listRole();
	
	public Role getRole(String roleId);

	public void removeRole(String roleId);

	public Role getRole4User(String uid);

}
