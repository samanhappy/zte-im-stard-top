package com.zte.databinder;

import java.util.List;

import com.zte.im.mybatis.bean.UserPermission;

public class UserPermBinder {

	private String uid;
	
	private String uname;

	private String tenantId;
	
	private Boolean isProtected; //是否开启保护设置

	String sortStr; // 排序sql

	private List<UserPermission> permList;

	public List<UserPermission> getPermList() {
		return permList;
	}

	public void setPermList(List<UserPermission> permList) {
		this.permList = permList;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	public String getSortStr() {
		return sortStr;
	}

	public Boolean getIsProtected() {
		return isProtected;
	}

	public void setIsProtected(Boolean isProtected) {
		this.isProtected = isProtected;
	}

	public void setSortStr(String sortStr) {
		this.sortStr = sortStr;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

}
