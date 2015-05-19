package com.zte.im.mybatis.mapper;

import com.zte.im.mybatis.bean.ZteImView;
import com.zte.im.mybatis.bean.ZteImViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ZteImViewMapper {
	int countByExample(ZteImViewExample example);

	int deleteByExample(ZteImViewExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(ZteImView record);

	int insertSelective(ZteImView record);

	List<ZteImView> selectByExample(ZteImViewExample example);

	ZteImView selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") ZteImView record,
			@Param("example") ZteImViewExample example);

	int updateByExample(@Param("record") ZteImView record,
			@Param("example") ZteImViewExample example);

	int updateByPrimaryKeySelective(ZteImView record);

	int updateByPrimaryKey(ZteImView record);
}