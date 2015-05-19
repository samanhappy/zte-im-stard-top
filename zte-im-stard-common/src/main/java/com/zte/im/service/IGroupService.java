package com.zte.im.service;

import java.util.List;

import com.zte.im.bean.GroupChat;
import com.zte.im.bean.User;

public interface IGroupService {
	public void saveGroup(GroupChat group);

	public void updateGroup(GroupChat group);

	/**
	 * 根据群id查询群
	 */
	public GroupChat findGroupChatById(Long groupid);

	/**
	 * 为指定用户添加一个群
	 * 
	 * @param uid
	 * @param groupIds
	 */
	public User addGroup(Long uid, GroupChat group);

	/**
	 * 指定用户退群
	 * 
	 * @param uid
	 * @param group
	 * @return
	 */
	public List<User> quitGroup(Long uid, GroupChat group,
			List<User> removeUsers);

	/**
	 * 刪除群
	 * 
	 * @param uid
	 * @param group
	 * @return
	 */
	public boolean removeGroup(GroupChat group);

	/**
	 * 指定用户退群
	 * 
	 * @param uid
	 * @param group
	 * @return
	 */
	public List<User> quitGroup(List<Long> uids, GroupChat group,
			List<User> removeUsers);

	/**
	 * 批量用户添加群
	 * 
	 * @param uids
	 * @param groupid
	 */
	public List<User> addGroup(List<Long> uids, GroupChat group, List<String> unameList);

	/**
	 * 添加用户到所有人的群组.
	 * 
	 * @param uid
	 */
	public void addUserToAllGroup(Long uid);

	/**
	 * 从所有人的群组中删除用户.
	 * 
	 * @param uid
	 */
	public void removeUserFromAllGroup(Long uid);

	/**
	 * 从所有人的群组中删除用户.
	 * 
	 * @param uid
	 */
	public void removeUserFromAllGroup(List<Long> uids);

	/**
	 * 初始化所有人群组.
	 */
	public void initAllGroup();
}
