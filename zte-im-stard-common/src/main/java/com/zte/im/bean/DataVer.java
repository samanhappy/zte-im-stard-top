package com.zte.im.bean;

import com.google.gson.annotations.Expose;

public class DataVer {

	@Expose
	private Long groupVer;

	@Expose
	private Long deptVer;

	@Expose
	private Long userListVer;

	@Expose
	private Long appVer;

	@Expose
	private Long appCateVer;
	
	@Expose
	private Long imgCateVer;

	public DataVer() {

	}

	/*
	 * public DataVer(Long userListVer, Long appVer, Long appCateVer) {
	 * this.userListVer = userListVer; this.appCateVer = appCateVer; this.appVer
	 * = appVer; }
	 */

	public Long getGroupVer() {
		return groupVer;
	}

	public void setGroupVer(Long groupVer) {
		this.groupVer = groupVer;
	}

	public Long getImgCateVer() {
		return imgCateVer;
	}

	public void setImgCateVer(Long imgCateVer) {
		this.imgCateVer = imgCateVer;
	}

	public Long getUserListVer() {
		return userListVer;
	}

	public void setUserListVer(Long userListVer) {
		this.userListVer = userListVer;
	}

	public Long getAppVer() {
		return appVer;
	}

	public void setAppVer(Long appVer) {
		this.appVer = appVer;
	}

	public Long getAppCateVer() {
		return appCateVer;
	}

	public void setAppCateVer(Long appCateVer) {
		this.appCateVer = appCateVer;
	}

	public Long getDeptVer() {
		return deptVer;
	}

	public void setDeptVer(Long deptVer) {
		this.deptVer = deptVer;
	}

}
