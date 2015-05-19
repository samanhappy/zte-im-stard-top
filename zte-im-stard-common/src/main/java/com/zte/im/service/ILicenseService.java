package com.zte.im.service;

import com.zte.im.bean.LicenseInfo;

public interface ILicenseService {

	public LicenseInfo queryOneLicenseInfo();

	public void saveLicenseInfo(LicenseInfo licenseInfo);
}
