package com.zte.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zte.im.bean.User;
import com.zte.im.mybatis.bean.UserPermission;
import com.zte.im.mybatis.bean.UserPermissionExample;
import com.zte.im.mybatis.mapper.UserPermissionMapper;
import com.zte.im.service.IUserService;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.service.impl.VersionServiceImpl;
import com.zte.im.util.UUIDGenerator;
import com.zte.service.IUserPermService;

@Service
public class UserPermServiceImpl implements IUserPermService {
	
	private final static Logger logger = LoggerFactory
			.getLogger(UserPermServiceImpl.class);

	@Autowired
	private UserPermissionMapper userPermMapper;

	private IUserService userService = UserServiceImpl.getInstance();

	@Override
	public List<UserPermission> getUserPermList(String uid) {
		if (uid != null && !"".equals(uid)) {
			UserPermissionExample upExample = new UserPermissionExample();
			upExample.createCriteria().andMemberIdEqualTo(uid);
			return userPermMapper.selectByExample(upExample);
		}
		return null;
	}
	
	@Override
	public boolean updateUserProtected(String uid, boolean isProtected) {
		User user = userService.getUserbySqlId(uid);
		if (user != null) {
			user.setIsProtected(isProtected);
			userService.updateUser(user);
			return true;
		}
		logger.error("user not found by id:{}", uid);
		return false;
	}

	@Override
	public boolean updateUserPerm(String uid, List<UserPermission> newPermList) {

		if (uid != null) {
			List<UserPermission> oldPermList = getUserPermList(uid);
			List<UserPermission> addPermList = new ArrayList<UserPermission>(); // 要添加的权限列表
			List<UserPermission> removePermList = new ArrayList<UserPermission>(); // 要删除的权限列表
			for (UserPermission perm : oldPermList) {
				if (newPermList == null || !newPermList.contains(perm)) {
					removePermList.add(perm);
				}
			}
			if (newPermList != null) {
				for (UserPermission perm : newPermList) {
					if (!oldPermList.contains(perm)) {
						addPermList.add(perm);
					}
				}
			}

			// 添加新权限
			for (UserPermission perm : addPermList) {
				perm.setId(UUIDGenerator.randomUUID());
				userPermMapper.insert(perm);
			}
			// 删除旧权限
			for (UserPermission perm : removePermList) {
				userPermMapper.deleteByPrimaryKey(perm.getId());
			}

			// 更新mongodb中权限数据
			userService.updateUserPerm(uid, newPermList);

			// 更新用户信息列表版本
			VersionServiceImpl.getInstance().incUserListVer();

		}

		return true;
	}

}
