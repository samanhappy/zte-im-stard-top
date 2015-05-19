package com.zte.im.mybatis.bean.my;

import com.google.gson.annotations.Expose;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class UcClientMy {
	@Expose
	private String id;
	@Expose
	private String type;
	@Expose
	private String apkUrl;
	@Expose
	private String name;
	@Expose
	private String iconUrl;
	@Expose
	private String os;
	@Expose
	private String isActive;
	@Expose
	private String updateLog;
	@Expose
	private String version;
	@Expose
	private String versionCode;
	@Expose
	private String apkName;
	@Expose
	private String forceUpdate;

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getForceUpdate() {
		return forceUpdate;
	}

	public void setForceUpdate(String forceUpdate) {
		this.forceUpdate = forceUpdate;
	}

	public String getApkName() {
		return apkName;
	}

	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public DBObject toDBObject() {
		DBObject object = new BasicDBObject();
		if (this.getId() != null) {
			object.put("id", this.getId());
		}
		if (this.getType() != null) {
			object.put("type", this.getType());
		}
		if (this.getApkUrl() != null) {
			object.put("apkUrl", this.getApkUrl());
		}
		if (this.getName() != null) {
			object.put("name", this.getName());
		}
		if (this.getIconUrl() != null) {
			object.put("iconUrl", this.getIconUrl());
		}
		if (this.getOs() != null) {
			object.put("os", this.getOs());
		}
		if (this.getIsActive() != null) {
			object.put("isActive", this.getIsActive());
		}
		if (this.getUpdateLog() != null) {
			object.put("updateLog", this.getUpdateLog());
		}
		if (this.getVersion() != null) {
			object.put("version", this.getVersion());
		}
		if (this.getVersionCode() != null) {
			object.put("versionCode", this.getVersionCode());
		}
		if (this.getApkName() != null) {
			object.put("apkName", this.getApkName());
		}
		if (this.getForceUpdate() != null) {
			object.put("forceUpdate", this.getForceUpdate());
		}
		return object;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getUpdateLog() {
		return updateLog;
	}

	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog;
	}
}
