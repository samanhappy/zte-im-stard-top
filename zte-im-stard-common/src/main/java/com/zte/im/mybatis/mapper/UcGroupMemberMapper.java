package com.zte.im.mybatis.mapper;

import com.zte.im.mybatis.bean.UcGroupMember;
import com.zte.im.mybatis.bean.UcGroupMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcGroupMemberMapper {
	int countByExample(UcGroupMemberExample example);

	int deleteByExample(UcGroupMemberExample example);

	int deleteByPrimaryKey(String id);

	int insert(UcGroupMember record);

	int insertSelective(UcGroupMember record);

	List<UcGroupMember> selectByExample(UcGroupMemberExample example);

	UcGroupMember selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") UcGroupMember record,
			@Param("example") UcGroupMemberExample example);

	int updateByExample(@Param("record") UcGroupMember record,
			@Param("example") UcGroupMemberExample example);

	int updateByPrimaryKeySelective(UcGroupMember record);

	int updateByPrimaryKey(UcGroupMember record);
}