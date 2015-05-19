package com.zte.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zte.im.mybatis.bean.UcTenant;
import com.zte.im.mybatis.bean.my.UcTenantMy;
import com.zte.im.mybatis.mapper.UcTenantMapper;
import com.zte.im.mybatis.mapper.my.UcTenantMapperMy;
import com.zte.service.ITenantService;

@Service
public class TenantServiceImpl implements ITenantService {
	@Autowired
	private UcTenantMapperMy tenantMapperMy;
	@Autowired
	private UcTenantMapper tenantMapper;

	@Override
	public UcTenantMy selectTenant(String tenantId) {
		return tenantMapperMy.selectTenant(tenantId);
	}

	@Override
	public void updateTenant(UcTenant tenant) {
		tenantMapper.updateByPrimaryKeySelective(tenant);

	}

}
