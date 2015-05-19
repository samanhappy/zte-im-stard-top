package com.zte.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zte.im.mybatis.bean.UcGroup;
import com.zte.im.mybatis.bean.UcGroupExample;
import com.zte.im.mybatis.bean.UcGroupMember;
import com.zte.im.mybatis.bean.UcTenant;
import com.zte.im.mybatis.bean.my.UcGroupMy;
import com.zte.im.mybatis.mapper.UcGroupMapper;
import com.zte.im.mybatis.mapper.my.UcGroupMapperMy;
import com.zte.im.service.ICompositionService;
import com.zte.im.service.impl.CompositionServiceImpl;
import com.zte.service.IGroupService;

@Service
public class GroupServiceImpl implements IGroupService {

	private static Logger logger = LoggerFactory
			.getLogger(GroupServiceImpl.class);

	@Autowired
	private UcGroupMapperMy groupMapperMy;
	@Autowired
	private UcGroupMapper groupMapper;

	ICompositionService comService = CompositionServiceImpl.getInstance();

	@Override
	public List<UcTenant> findGroupTree(String typeId) {
		return groupMapperMy.selectGroupTree(typeId);
	}

	@Override
	public List<UcGroupMy> findGroupRoot() {
		return groupMapperMy.selectGroupRoot();
	}

	@Override
	public void addGroup(UcGroup record) {
		groupMapper.insertSelective(record);
		comService.saveOrUpdate(record);
	}

	@Override
	public void updateGroup(UcGroup record) {
		groupMapper.updateByPrimaryKeySelective(record);
		comService.saveOrUpdate(record);
	}

	@Override
	public void deleteGroup(String id) {

		UcGroup group = groupMapper.selectByPrimaryKey(id);
		if (group != null && group.getFullId() != null) {
			UcGroupExample example = new UcGroupExample();
			example.createCriteria().andFullIdLike(group.getFullId() + "%");
			List<UcGroup> list = groupMapper.selectByExample(example);
			if (list != null) {
				groupMapper.deleteByExample(example);
				List<String> idList = new ArrayList<String>();
				for (UcGroup ug : list) {
					idList.add(ug.getId());
				}
				comService.deleteBySqlIdList(idList);
				logger.info("success to delete group for id:{}", id);
			} else {
				logger.error("can not find groups by fullid");
			}
		} else {
			logger.error("can not find group or group full id is null");
		}
	}

	@Override
	public UcGroup findGroupById(String id) {
		return groupMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean isNameExists(String tenantId, String pid, String name,
			String id) {

		List<UcGroup> list = null;
		UcGroupExample example = new UcGroupExample();
		if (pid.equals(tenantId)) {
			example.createCriteria().andPidEqualTo(tenantId);
			list = groupMapper.selectByExample(example);
		} else {
			UcGroup group = groupMapper.selectByPrimaryKey(pid);
			if (group != null && group.getFullId() != null) {
				example.createCriteria().andFullIdLike(group.getFullId() + "%");
				list = groupMapper.selectByExample(example);
			} else {
				logger.error("can not find group or group full id is null");
			}
		}

		if (list != null) {
			logger.info(name);
			for (UcGroup ug : list) {
				logger.info(ug.getName());
				// id不为空时表示编辑部门
				if ((id == null || !id.equals(ug.getId()))
						&& ug.getName().equals(name)) {
					return true;
				}
			}
		} else {
			logger.error("can not find groups by fullid");
		}

		return false;
	}

	@Override
	public List<UcGroupMember> getGroupMember4User(String tenantId,
			String typeId, String uid) {
		Map<String, String> params = new HashMap<String, String>();
		if (tenantId != null) {
			params.put("tenantId", tenantId);
		}
		if (typeId != null) {
			params.put("typeId", typeId);
		}
		if (uid != null) {
			params.put("uid", uid);
		}
		return groupMapperMy.getGroupMemberByUid(params);
	}
	
	@Override
	public List<UcGroup> deptList(String tenantId, String typeId) {
		UcGroupExample example = new UcGroupExample();
		example.createCriteria().andTenantIdEqualTo(tenantId).andTypeIdEqualTo(typeId);
		example.setOrderByClause("sequ asc");
		return  groupMapper.selectByExample(example);
	}

}
