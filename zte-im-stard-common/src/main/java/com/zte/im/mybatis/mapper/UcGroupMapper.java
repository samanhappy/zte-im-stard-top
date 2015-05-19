package com.zte.im.mybatis.mapper;

import com.zte.im.mybatis.bean.UcGroup;
import com.zte.im.mybatis.bean.UcGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcGroupMapper {
	int countByExample(UcGroupExample example);

	int deleteByExample(UcGroupExample example);

	int deleteByPrimaryKey(String id);

	int insert(UcGroup record);

	int insertSelective(UcGroup record);

	List<UcGroup> selectByExample(UcGroupExample example);

	UcGroup selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") UcGroup record,
			@Param("example") UcGroupExample example);

	int updateByExample(@Param("record") UcGroup record,
			@Param("example") UcGroupExample example);

	int updateByPrimaryKeySelective(UcGroup record);

	int updateByPrimaryKey(UcGroup record);
}