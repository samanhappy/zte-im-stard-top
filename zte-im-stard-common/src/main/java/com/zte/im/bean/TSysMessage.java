package com.zte.im.bean;

import com.google.gson.annotations.Expose;

public class TSysMessage extends Base {
	@Expose
	private Long messageId;// 系统信息标识

	@Expose
	private Long sendUserId;// 发送用户标识

	@Expose
	private Long receiveUserId;// 接收用户标识

	@Expose
	private Long groupId;// 群组标识

	@Expose
	private String groupName;// 群组名称

	@Expose
	private String messageType;// 消息类型：01、申请；02、邀请；03：提示已加入圈子；04：提示圈子创建者，xx加入圈子；
								// 05：提示发出邀请的人，被邀请用户已加入xx圈子；06：提示给申请人，被拒绝加入圈子；
								// 07：提示给发出邀请的人，被邀请用户拒绝加入xx圈子；08：提示圈子创建者，已拒绝xx用户加入xx圈子；
								// 09：提示被邀请人，已拒绝加入圈子；10：提示用户已被移除圈子；11：提示用户xx已退出圈子

	@Expose
	private String messageStatus;// 消息状态：00、未处理；01、同意；02、拒绝

	/**
	 * @return the messageId
	 */
	public Long getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId
	 *            the messageId to set
	 */
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return the sendUserId
	 */
	public Long getSendUserId() {
		return sendUserId;
	}

	/**
	 * @param sendUserId
	 *            the sendUserId to set
	 */
	public void setSendUserId(Long sendUserId) {
		this.sendUserId = sendUserId;
	}

	/**
	 * @return the receiveUserId
	 */
	public Long getReceiveUserId() {
		return receiveUserId;
	}

	/**
	 * @param receiveUserId
	 *            the receiveUserId to set
	 */
	public void setReceiveUserId(Long receiveUserId) {
		this.receiveUserId = receiveUserId;
	}

	/**
	 * @return the groupId
	 */
	public Long getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the messageType
	 */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * @param messageType
	 *            the messageType to set
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	/**
	 * @return the messageStatus
	 */
	public String getMessageStatus() {
		return messageStatus;
	}

	/**
	 * @param messageStatus
	 *            the messageStatus to set
	 */
	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

}
