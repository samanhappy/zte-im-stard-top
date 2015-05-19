package com.mongo.test;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zte.im.bean.LicenseInfo;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.ILicenseService;
import com.zte.im.service.impl.LicenseServiceImpl;

public class TestLicenseInfo {
	
	private final static Logger logger = LoggerFactory
			.getLogger(TestLicenseInfo.class);

	ILicenseService licenseService = LicenseServiceImpl.getInstance();
	MongoDBSupport mongoDBSupport = MongoDBSupport.getInstance();

	@Before
	public void init() {
	}

	@Test
	public void createTest() {
		LicenseInfo licenseInfo = licenseService.queryOneLicenseInfo();
		logger.info(licenseInfo.getLicense_desc());
		logger.info("=======3=======");
	}
}
