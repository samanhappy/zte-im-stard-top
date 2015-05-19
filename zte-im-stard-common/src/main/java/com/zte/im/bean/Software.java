package com.zte.im.bean;

import java.util.Date;

import com.google.gson.annotations.Expose;

public class Software {
	@Expose
	private String id;
	@Expose
	private Integer version;
	@Expose
	private String version_name;
	@Expose
	private String update_url;
	@Expose
	private String client_type;
	private Date update_time;
	private int is_enable;
	@Expose
	private String host;
	@Expose
	private Integer forceUpdate;
	@Expose
	private String iconUrl;
	@Expose
	private String updateLog;
	private String apkName;

	public Integer getForceUpdate() {
		return forceUpdate;
	}

	public void setForceUpdate(Integer forceUpdate) {
		this.forceUpdate = forceUpdate;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getUpdateLog() {
		return updateLog;
	}

	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog;
	}

	public String getApkName() {
		return apkName;
	}

	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getVersion_name() {
		return version_name;
	}

	public void setVersion_name(String version_name) {
		this.version_name = version_name;
	}

	public String getUpdate_url() {
		return update_url;
	}

	public void setUpdate_url(String update_url) {
		this.update_url = update_url;
	}

	public String getClient_type() {
		return client_type;
	}

	public void setClient_type(String client_type) {
		this.client_type = client_type;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public int getIs_enable() {
		return is_enable;
	}

	public void setIs_enable(int is_enable) {
		this.is_enable = is_enable;
	}

}
