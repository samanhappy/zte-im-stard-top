package com.zte.im.mybatis.mapper;

import com.zte.im.mybatis.bean.UserPermission;
import com.zte.im.mybatis.bean.UserPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPermissionMapper {
	int countByExample(UserPermissionExample example);

	int deleteByExample(UserPermissionExample example);

	int deleteByPrimaryKey(String id);

	int insert(UserPermission record);

	int insertSelective(UserPermission record);

	List<UserPermission> selectByExample(UserPermissionExample example);

	UserPermission selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") UserPermission record,
			@Param("example") UserPermissionExample example);

	int updateByExample(@Param("record") UserPermission record,
			@Param("example") UserPermissionExample example);

	int updateByPrimaryKeySelective(UserPermission record);

	int updateByPrimaryKey(UserPermission record);
}