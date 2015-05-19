package com.zte.service;

import com.zte.im.mybatis.bean.UcTenant;
import com.zte.im.mybatis.bean.my.UcTenantMy;

public interface ITenantService {
	
	public UcTenantMy selectTenant(String tenantId);

	public void updateTenant(UcTenant tenant);
}
