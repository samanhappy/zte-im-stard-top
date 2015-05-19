package com.zte.im.mybatis.bean;

import java.util.Date;

public class TdDeskSoftwareInfo {
	private Integer id;

	private Integer softwareVersion;

	private String updateUrl;

	private Integer forcetwo;

	private String updateContent;

	private Date updateTime;

	private String clientType;

	private Integer userId;

	private Integer isActive;

	private String versionName;

	private Integer isEnable;

	private String software1;

	private String software2;

	private String software3;

	private Integer apkSize;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(Integer softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getUpdateUrl() {
		return updateUrl;
	}

	public void setUpdateUrl(String updateUrl) {
		this.updateUrl = updateUrl;
	}

	public Integer getForcetwo() {
		return forcetwo;
	}

	public void setForcetwo(Integer forcetwo) {
		this.forcetwo = forcetwo;
	}

	public String getUpdateContent() {
		return updateContent;
	}

	public void setUpdateContent(String updateContent) {
		this.updateContent = updateContent;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public String getSoftware1() {
		return software1;
	}

	public void setSoftware1(String software1) {
		this.software1 = software1;
	}

	public String getSoftware2() {
		return software2;
	}

	public void setSoftware2(String software2) {
		this.software2 = software2;
	}

	public String getSoftware3() {
		return software3;
	}

	public void setSoftware3(String software3) {
		this.software3 = software3;
	}

	public Integer getApkSize() {
		return apkSize;
	}

	public void setApkSize(Integer apkSize) {
		this.apkSize = apkSize;
	}
}