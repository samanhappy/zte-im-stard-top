package com.zte.im.mybatis.mapper;

import com.zte.im.mybatis.bean.UcGroupClass;
import com.zte.im.mybatis.bean.UcGroupClassExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcGroupClassMapper {
	int countByExample(UcGroupClassExample example);

	int deleteByExample(UcGroupClassExample example);

	int deleteByPrimaryKey(String id);

	int insert(UcGroupClass record);

	int insertSelective(UcGroupClass record);

	List<UcGroupClass> selectByExample(UcGroupClassExample example);

	UcGroupClass selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") UcGroupClass record,
			@Param("example") UcGroupClassExample example);

	int updateByExample(@Param("record") UcGroupClass record,
			@Param("example") UcGroupClassExample example);

	int updateByPrimaryKeySelective(UcGroupClass record);

	int updateByPrimaryKey(UcGroupClass record);
}