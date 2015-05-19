package com.zte.im.mybatis.mapper;

import com.zte.im.mybatis.bean.ZteImSoftware;
import com.zte.im.mybatis.bean.ZteImSoftwareExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ZteImSoftwareMapper {
	int countByExample(ZteImSoftwareExample example);

	int deleteByExample(ZteImSoftwareExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(ZteImSoftware record);

	int insertSelective(ZteImSoftware record);

	List<ZteImSoftware> selectByExample(ZteImSoftwareExample example);

	ZteImSoftware selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") ZteImSoftware record,
			@Param("example") ZteImSoftwareExample example);

	int updateByExample(@Param("record") ZteImSoftware record,
			@Param("example") ZteImSoftwareExample example);

	int updateByPrimaryKeySelective(ZteImSoftware record);

	int updateByPrimaryKey(ZteImSoftware record);
}