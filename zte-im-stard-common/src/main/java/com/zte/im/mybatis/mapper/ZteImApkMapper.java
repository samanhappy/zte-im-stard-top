package com.zte.im.mybatis.mapper;

import com.zte.im.mybatis.bean.ZteImApk;
import com.zte.im.mybatis.bean.ZteImApkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ZteImApkMapper {
	int countByExample(ZteImApkExample example);

	int deleteByExample(ZteImApkExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(ZteImApk record);

	int insertSelective(ZteImApk record);

	List<ZteImApk> selectByExample(ZteImApkExample example);

	ZteImApk selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") ZteImApk record,
			@Param("example") ZteImApkExample example);

	int updateByExample(@Param("record") ZteImApk record,
			@Param("example") ZteImApkExample example);

	int updateByPrimaryKeySelective(ZteImApk record);

	int updateByPrimaryKey(ZteImApk record);
}