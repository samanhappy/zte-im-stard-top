package com.zte.im.bean;

import com.google.gson.annotations.Expose;

/**
 * 微博部门对照
 * 
 * @author Administrator
 * 
 */
public class TTDept extends Base {

	@Expose
	private Long twitterId;// 微博ID

	@Expose
	private Long userId;// 用户id

	@Expose
	private Long deptId;// 部门标识

	public Long getTwitterId() {
		return twitterId;
	}

	public void setTwitterId(Long twitterId) {
		this.twitterId = twitterId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

}
