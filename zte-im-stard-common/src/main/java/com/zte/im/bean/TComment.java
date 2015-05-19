package com.zte.im.bean;

import com.google.gson.annotations.Expose;

public class TComment extends Base {
	@Expose
	private Long commentId;// 评论标识

	@Expose
	private Long twitterId;// 微博标识

	@Expose
	private Long userId;// 用户标识

	@Expose
	private String commentContent;// 评论内容

	@Expose
	private String commentStatus;// 评论状态：00、启用，99、停用

	@Expose
	private Long replyCommentId;// 回复评论标识

	@Expose
	private Long replySupportId;// 回复赞标识

	@Expose
	private String commentType;// 评论类型：01、评论微博；02、回复评论；03、回复赞
	
	@Expose
	private Long becomentUserId;//被评论人id
	
	@Expose
	private User user ;//评论用户对象

	/**
	 * @return the replyCommentId
	 */
	public Long getReplyCommentId() {
		return replyCommentId;
	}

	/**
	 * @param replyCommentId
	 *            the replyCommentId to set
	 */
	public void setReplyCommentId(Long replyCommentId) {
		this.replyCommentId = replyCommentId;
	}

	/**
	 * @return the replySupportId
	 */
	public Long getReplySupportId() {
		return replySupportId;
	}

	/**
	 * @param replySupportId
	 *            the replySupportId to set
	 */
	public void setReplySupportId(Long replySupportId) {
		this.replySupportId = replySupportId;
	}

	/**
	 * @return the commentType
	 */
	public String getCommentType() {
		return commentType;
	}

	/**
	 * @param commentType
	 *            the commentType to set
	 */
	public void setCommentType(String commentType) {
		this.commentType = commentType;
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

	/**
	 * @return the commentContent
	 */
	public String getCommentContent() {
		return commentContent;
	}

	/**
	 * @param commentContent
	 *            the commentContent to set
	 */
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	/**
	 * @return the commentStatus
	 */
	public String getCommentStatus() {
		return commentStatus;
	}

	/**
	 * @param commentStatus
	 *            the commentStatus to set
	 */
	public void setCommentStatus(String commentStatus) {
		this.commentStatus = commentStatus;
	}

	public Long getBecomentUserId() {
		return becomentUserId;
	}

	public void setBecomentUserId(Long becomentUserId) {
		this.becomentUserId = becomentUserId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
