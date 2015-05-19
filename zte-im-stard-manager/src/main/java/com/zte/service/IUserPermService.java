package com.zte.service;

import java.util.List;

import com.zte.im.mybatis.bean.UserPermission;

public interface IUserPermService {

	public List<UserPermission> getUserPermList(String uid);

	// 更新用户是否开启保护设置
	public boolean updateUserProtected(String uid, boolean isProtected);
	
	public boolean updateUserPerm(String uid, List<UserPermission> permList);

}
