package com.zte.im.bean;

import com.google.gson.annotations.Expose;

public class TMention extends Base {
	@Expose
	private Long mentionId;// 提到标识

	@Expose
	private Long twitterId;// 微博标识

	@Expose
	private Long commentId;// 评论标识

	@Expose
	private Long userId;// 用户标识

	@Expose
	private Long mentionUserId;// @用户标识

	@Expose
	private String mentionType;// 提到类型：01、微博；02、评论

	@Expose
	private String mentionStatus;// 提到状态：00、启用，99、停用

	/**
	 * @return the mentionId
	 */
	public Long getMentionId() {
		return mentionId;
	}

	/**
	 * @param mentionId
	 *            the mentionId to set
	 */
	public void setMentionId(Long mentionId) {
		this.mentionId = mentionId;
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
	 * @return the commentId
	 */
	public Long getCommentId() {
		return commentId;
	}

	/**
	 * @param commentId
	 *            the commentId to set
	 */
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
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

	/**
	 * @return the mentionUserId
	 */
	public Long getMentionUserId() {
		return mentionUserId;
	}

	/**
	 * @param mentionUserId
	 *            the mentionUserId to set
	 */
	public void setMentionUserId(Long mentionUserId) {
		this.mentionUserId = mentionUserId;
	}

	/**
	 * @return the mentionType
	 */
	public String getMentionType() {
		return mentionType;
	}

	/**
	 * @param mentionType
	 *            the mentionType to set
	 */
	public void setMentionType(String mentionType) {
		this.mentionType = mentionType;
	}

	/**
	 * @return the mentionStatus
	 */
	public String getMentionStatus() {
		return mentionStatus;
	}

	/**
	 * @param mentionStatus
	 *            the mentionStatus to set
	 */
	public void setMentionStatus(String mentionStatus) {
		this.mentionStatus = mentionStatus;
	}

}
