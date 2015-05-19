package com.zte.im.mybatis.mapper;

import com.zte.im.mybatis.bean.UcMember;
import com.zte.im.mybatis.bean.UcMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcMemberMapper {
	int countByExample(UcMemberExample example);

	int deleteByExample(UcMemberExample example);

	int deleteByPrimaryKey(String id);

	int insert(UcMember record);

	int insertSelective(UcMember record);

	List<UcMember> selectByExample(UcMemberExample example);

	UcMember selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") UcMember record,
			@Param("example") UcMemberExample example);

	int updateByExample(@Param("record") UcMember record,
			@Param("example") UcMemberExample example);

	int updateByPrimaryKeySelective(UcMember record);

	int updateByPrimaryKey(UcMember record);
}