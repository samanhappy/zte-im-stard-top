package com.zte.im.bean;

import com.google.gson.annotations.Expose;

public class TAttention extends Base {
	@Expose
	private Long attentionId;// 关注标识

	@Expose
	private Long userId;// 用户标识

	@Expose
	private Long attentionUserId;// 关注用户标识

	@Expose
	private String eachOtherFlag;// 互相关注标志：0、否；1、是

	@Expose
	private String attentionStatus;// 关注状态：00、启用，99、停用

	@Expose
	private String attentionType;// 关注类型：01、一般；02、特别

	/**
	 * @return the attentionId
	 */
	public Long getAttentionId() {
		return attentionId;
	}

	/**
	 * @param attentionId
	 *            the attentionId to set
	 */
	public void setAttentionId(Long attentionId) {
		this.attentionId = attentionId;
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
	 * @return the attentionUserId
	 */
	public Long getAttentionUserId() {
		return attentionUserId;
	}

	/**
	 * @param attentionUserId
	 *            the attentionUserId to set
	 */
	public void setAttentionUserId(Long attentionUserId) {
		this.attentionUserId = attentionUserId;
	}

	/**
	 * @return the eachOtherFlag
	 */
	public String getEachOtherFlag() {
		return eachOtherFlag;
	}

	/**
	 * @param eachOtherFlag
	 *            the eachOtherFlag to set
	 */
	public void setEachOtherFlag(String eachOtherFlag) {
		this.eachOtherFlag = eachOtherFlag;
	}

	/**
	 * @return the attentionStatus
	 */
	public String getAttentionStatus() {
		return attentionStatus;
	}

	/**
	 * @param attentionStatus
	 *            the attentionStatus to set
	 */
	public void setAttentionStatus(String attentionStatus) {
		this.attentionStatus = attentionStatus;
	}

	/**
	 * @return the attentionType
	 */
	public String getAttentionType() {
		return attentionType;
	}

	/**
	 * @param attentionType
	 *            the attentionType to set
	 */
	public void setAttentionType(String attentionType) {
		this.attentionType = attentionType;
	}

}
