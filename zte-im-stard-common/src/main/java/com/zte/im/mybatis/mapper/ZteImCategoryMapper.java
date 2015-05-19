package com.zte.im.mybatis.mapper;

import com.zte.im.mybatis.bean.ZteImCategory;
import com.zte.im.mybatis.bean.ZteImCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ZteImCategoryMapper {
	int countByExample(ZteImCategoryExample example);

	int deleteByExample(ZteImCategoryExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(ZteImCategory record);

	int insertSelective(ZteImCategory record);

	List<ZteImCategory> selectByExample(ZteImCategoryExample example);

	ZteImCategory selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") ZteImCategory record,
			@Param("example") ZteImCategoryExample example);

	int updateByExample(@Param("record") ZteImCategory record,
			@Param("example") ZteImCategoryExample example);

	int updateByPrimaryKeySelective(ZteImCategory record);

	int updateByPrimaryKey(ZteImCategory record);
}