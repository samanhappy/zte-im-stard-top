package com.zte.im.mybatis.mapper;

import com.zte.im.mybatis.bean.TdDeskSoftwareInfo;
import com.zte.im.mybatis.bean.TdDeskSoftwareInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TdDeskSoftwareInfoMapper {
	int countByExample(TdDeskSoftwareInfoExample example);

	int deleteByExample(TdDeskSoftwareInfoExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(TdDeskSoftwareInfo record);

	int insertSelective(TdDeskSoftwareInfo record);

	List<TdDeskSoftwareInfo> selectByExample(TdDeskSoftwareInfoExample example);

	TdDeskSoftwareInfo selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") TdDeskSoftwareInfo record,
			@Param("example") TdDeskSoftwareInfoExample example);

	int updateByExample(@Param("record") TdDeskSoftwareInfo record,
			@Param("example") TdDeskSoftwareInfoExample example);

	int updateByPrimaryKeySelective(TdDeskSoftwareInfo record);

	int updateByPrimaryKey(TdDeskSoftwareInfo record);
}