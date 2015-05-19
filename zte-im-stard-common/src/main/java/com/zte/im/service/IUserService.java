package com.zte.im.service;

import java.util.List;

import com.mongodb.DBObject;
import com.zte.im.bean.Feedback;
import com.zte.im.bean.User;
import com.zte.im.mybatis.bean.UcMember;
import com.zte.im.mybatis.bean.UserPermission;
import com.zte.im.util.Page;

public interface IUserService {
	/**
	 * 根据企业id和工号获取用户
	 * 
	 * @param id
	 * @return
	 */
	public User getUserbyid(Long gid, String jid);

	/**
	 * 注册
	 * 
	 * @param qid
	 *            企业id
	 * @param jid
	 *            工号
	 * @param pwd
	 *            密码
	 * @return
	 */
	public User regUser(Long gid, String jid, String pwd);

	/**
	 * 根据部门id查询用户分组
	 * 
	 * @param gid
	 * @param cid
	 * @return
	 */
	public List<DBObject> getUserbyid(Long gid, Long cid);

	/**
	 * 根据uid查询用户(user_data)
	 * 
	 * @param uid
	 * @return
	 */
	public User getUserbyid(Long uid);

	/**
	 * 根据uid查询用户(t_user)
	 * 
	 * @param uid
	 * @return
	 */
	public User getTUserbyid(Long uid);
	/**
	 * 根据工号jid查询用户
	 * 
	 * @param uid
	 * @return
	 */
	public User getUserbyJid(String jid);

	/**
	 * 根据loginname查询用户
	 * 
	 * @param uid
	 * @return
	 */
	public User getUserbyLoginname(String loginname);

	/**
	 * 分页查询用户
	 * 
	 * @param gid
	 * @param page
	 * @return
	 */
	public List<User> getUserbyGid(Long gid, Page page, User user);

	/**
	 * 查询用户
	 * 
	 * @param gid
	 * @param page
	 * @return
	 */
	public List<User> getUserbyGid(Long gid);

	/**
	 * 根据企业id查询用户总数
	 * 
	 * @param gid
	 * @return
	 */
	public Long getUserCountbyGid(Long gid);

	/**
	 * 更新用户sn、签名、头像、电话、邮箱、传真和备注
	 * 
	 * @param user
	 */
	public void updateUser(User user);

	public void incUserGroupVer(Long uid);

	public boolean saveOrUpdate(UcMember member, String deptId, String post);

	public boolean saveOrUpdate(UcMember member, String deptId, String post,
			boolean useUid);

	/**
	 * 根据PostgreSQL主键查询用户
	 */
	public User getUserbySqlId(String id);

	public boolean removeUserBySqlId(List<String> ids);

	public List<Long> getUidsbySqlIds(List<String> ids);

	public void updateUserPerm(String id, List<UserPermission> permList);

	/**
	 * 新增反馈
	 * 
	 * @param fb
	 */
	public void saveFeedback(Feedback fb);
	
	public List<Long> searchUser(String name);

}
