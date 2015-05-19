package com.zte.im.service.impl;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zte.im.bean.LicenseInfo;
import com.zte.im.bean.TGroup;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.ILicenseService;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;

public class LicenseServiceImpl implements ILicenseService {

	private static LicenseServiceImpl service;

	private LicenseServiceImpl() {
		super();
	}

	public static LicenseServiceImpl getInstance() {
		if (service == null) {
			service = new LicenseServiceImpl();
		}
		return service;
	}

	MongoDBSupport monDbSupport = MongoDBSupport.getInstance();

	@Override
	public LicenseInfo queryOneLicenseInfo() {
		// TODO Auto-generated method stub
		LicenseInfo licenseInfo = null;
		// List<LicenseInfo> list = null;
		// DBCursor cur = monDbSupport.queryCollection(Constant.LICENSE_INFO);
		// if (cur != null && cur.size() > 0) {
		// Type type = new TypeToken<List<LicenseInfo>>() {
		// }.getType();
		// list = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		// }
		// if(list != null)
		// licenseInfo = list.get(0);
		return licenseInfo;
	}

	@Override
	public void saveLicenseInfo(LicenseInfo licenseInfo) {
		// TODO Auto-generated method stub
		// monDbSupport.save(licenseInfo.toDbObject(), Constant.LICENSE_INFO);
	}

}
