package com.zte.im.bean;

import com.google.gson.annotations.Expose;

public class TTransUser extends Base {

	@Expose
	private Long transId;// 转发标识

	@Expose
	private Long userId;// 用户标识

	@Expose
	private Long twitterId;// 转发微博标识

	@Expose
	private String twitterContent;//

	public Long getTransId() {
		return transId;
	}

	public void setTransId(Long transId) {
		this.transId = transId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTwitterId() {
		return twitterId;
	}

	public void setTwitterId(Long twitterId) {
		this.twitterId = twitterId;
	}

	public String getTwitterContent() {
		return twitterContent;
	}

	public void setTwitterContent(String twitterContent) {
		this.twitterContent = twitterContent;
	}

}
