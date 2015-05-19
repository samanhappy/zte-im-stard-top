package com.zte.im.bean;

import com.google.gson.annotations.Expose;

public class TTopic extends Base {
	@Expose
	private Long topicId;
	@Expose
	private String topicName;// 话题名称
	@Expose
	private String topicStatus;// 话题状态
	@Expose
	private String topicType;// 话题类型
	@Expose
	private Long useNum;// 使用次数

	@Expose
	private String userName;// 发布人姓名

	@Expose
	private String minipicurl;// head portrait 缩写，用户头像

	@Expose
	private String bigpicurl;// source head portrait 缩写，用户头像原图

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getTopicStatus() {
		return topicStatus;
	}

	public void setTopicStatus(String topicStatus) {
		this.topicStatus = topicStatus;
	}

	public String getTopicType() {
		return topicType;
	}

	public void setTopicType(String topicType) {
		this.topicType = topicType;
	}

	public Long getUseNum() {
		return useNum;
	}

	public void setUseNum(Long useNum) {
		this.useNum = useNum;
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

}
