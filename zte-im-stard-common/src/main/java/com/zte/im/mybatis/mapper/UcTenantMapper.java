package com.zte.im.mybatis.mapper;

import com.zte.im.mybatis.bean.UcTenant;
import com.zte.im.mybatis.bean.UcTenantExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcTenantMapper {
	int countByExample(UcTenantExample example);

	int deleteByExample(UcTenantExample example);

	int deleteByPrimaryKey(String id);

	int insert(UcTenant record);

	int insertSelective(UcTenant record);

	List<UcTenant> selectByExample(UcTenantExample example);

	UcTenant selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") UcTenant record,
			@Param("example") UcTenantExample example);

	int updateByExample(@Param("record") UcTenant record,
			@Param("example") UcTenantExample example);

	int updateByPrimaryKeySelective(UcTenant record);

	int updateByPrimaryKey(UcTenant record);
}