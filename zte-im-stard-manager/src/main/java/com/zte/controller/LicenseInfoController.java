package com.zte.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zte.im.bean.LicenseInfo;
import com.zte.im.service.ILicenseService;
import com.zte.im.service.impl.LicenseServiceImpl;

@Controller
public class LicenseInfoController {

	private static Logger logger = LoggerFactory
			.getLogger(LicenseInfoController.class);

	private ILicenseService licenseService = LicenseServiceImpl.getInstance();

	@RequestMapping(value = "getLicenseInfo")
	@ResponseBody
	public String getLicenseInfo(Model model) {
		String result = "";
		LicenseInfo licenseInfo = licenseService.queryOneLicenseInfo();
		logger.info(licenseInfo.getLicense_desc());
		return result;
	}
}
