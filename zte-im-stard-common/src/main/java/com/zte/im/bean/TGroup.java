package com.zte.im.bean;

import com.google.gson.annotations.Expose;

public class TGroup extends Base {
	@Expose
	private Long groupId;// 圈子id

	@Expose
	private String groupName;// 圈子名称

	@Expose
	private String groupIntroduction; // 圈子简介

	@Expose
	private String groupType;// 圈子类型

	@Expose
	private String groupStatus;// 圈子状态00可用；01禁用

	@Expose
	private String imgUrl;// 圈子头像url

	@Expose
	private String userName;// 发布人姓名

	@Expose
	private String minipicurl;// head portrait 缩写，用户头像

	@Expose
	private String bigpicurl;// source head portrait 缩写，用户头像原图
	
	@Expose
	private String isJoin;// 当前用户是否加入了该圈子00：未加入；01：已加入
	
	@Expose
	private Long qzcys;//圈子参与人数
	@Expose
	private Long qzwbs;//圈子微博数
	

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupIntroduction() {
		return groupIntroduction;
	}

	public void setGroupIntroduction(String groupIntroduction) {
		this.groupIntroduction = groupIntroduction;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMinipicurl() {
		return minipicurl;
	}

	public void setMinipicurl(String minipicurl) {
		this.minipicurl = minipicurl;
	}

	public String getBigpicurl() {
		return bigpicurl;
	}

	public void setBigpicurl(String bigpicurl) {
		this.bigpicurl = bigpicurl;
	}

	public String getIsJoin() {
		return isJoin;
	}

	public void setIsJoin(String isJoin) {
		this.isJoin = isJoin;
	}

	public Long getQzcys() {
		return qzcys;
	}

	public void setQzcys(Long qzcys) {
		this.qzcys = qzcys;
	}

	public Long getQzwbs() {
		return qzwbs;
	}

	public void setQzwbs(Long qzwbs) {
		this.qzwbs = qzwbs;
	}

	public String getGroupStatus() {
		return groupStatus;
	}

	public void setGroupStatus(String groupStatus) {
		this.groupStatus = groupStatus;
	}

}
