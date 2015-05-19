package com.zte.im.bean;

import com.google.gson.annotations.Expose;

public class TSupport extends Base {
	@Expose
	private Long supportId;// 赞标识

	@Expose
	private Long twitterId;// 微博标识

	@Expose
	private Long userId;// 用户标识

	/**
	 * @return the supportId
	 */
	public Long getSupportId() {
		return supportId;
	}

	/**
	 * @param supportId
	 *            the supportId to set
	 */
	public void setSupportId(Long supportId) {
		this.supportId = supportId;
	}

	/**
	 * @return the twitterId
	 */
	public Long getTwitterId() {
		return twitterId;
	}

	/**
	 * @param twitterId
	 *            the twitterId to set
	 */
	public void setTwitterId(Long twitterId) {
		this.twitterId = twitterId;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
