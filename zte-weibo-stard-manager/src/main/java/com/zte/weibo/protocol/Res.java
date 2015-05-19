package com.zte.weibo.protocol;

import com.google.gson.annotations.Expose;

public class Res {
	@Expose
	private String reCode;
	@Expose
	private String resMessage;

	public String getReCode() {
		return reCode;
	}

	public void setReCode(String reCode) {
		this.reCode = reCode;
	}

	public String getResMessage() {
		return resMessage;
	}

	public void setResMessage(String resMessage) {
		this.resMessage = resMessage;
	}

}
