package com.zte.service;

import java.util.List;

import com.zte.im.mybatis.bean.UcGroup;
import com.zte.im.mybatis.bean.UcGroupMember;
import com.zte.im.mybatis.bean.UcTenant;
import com.zte.im.mybatis.bean.my.UcGroupMy;

public interface IGroupService {
	/**
	 * 获取分组列表
	 * 
	 * @return
	 */
	public List<UcTenant> findGroupTree(String typeId);

	/**
	 * 获取分组列表
	 * 
	 * @return
	 */
	public List<UcGroup> deptList(String tenantId, String typeId);

	/**
	 * 获取分组最上级列表
	 * 
	 * @return
	 */
	public List<UcGroupMy> findGroupRoot();

	/**
	 * 添加分组
	 * 
	 * @param record
	 */
	void addGroup(UcGroup record);

	/**
	 * 更新分组
	 * 
	 * @param record
	 */
	void updateGroup(UcGroup record);

	/**
	 * 删除分组
	 * 
	 * @param id
	 */
	void deleteGroup(String id);

	UcGroup findGroupById(String id);

	/**
	 * 查看部门名是否存在.
	 * 
	 * @param pid
	 * @param name
	 * @param id
	 * @return
	 */
	boolean isNameExists(String tenantId, String pid, String name, String id);

	/**
	 * 查询用户所属的群组，根据typeId区分机构和职位
	 * 
	 * @param tenantId
	 * @param typeId
	 * @param uid
	 * @return
	 */
	public List<UcGroupMember> getGroupMember4User(String tenantId,
			String typeId, String uid);

}
