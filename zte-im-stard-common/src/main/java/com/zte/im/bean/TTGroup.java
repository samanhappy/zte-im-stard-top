package com.zte.im.bean;

import com.google.gson.annotations.Expose;

/**
 * 微博圈子对照
 * 
 * @author Administrator
 * 
 */
public class TTGroup extends Base {

	@Expose
	private Long twitterId;// 微博id

	@Expose
	private Long groupId;// 圈子id

	@Expose
	private Long userId;// 用户标识

	public Long getTwitterId() {
		return twitterId;
	}

	public void setTwitterId(Long twitterId) {
		this.twitterId = twitterId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
