package com.zte.im.protocol;

import com.google.gson.annotations.Expose;

public class Res {
	@Expose
	private String reCode;
	@Expose
	private String resMessage;
	@Expose
	private String resCheckMessage;
	@Expose
	private int resCheckCode;

	public int getResCheckCode() {
		return resCheckCode;
	}

	public void setResCheckCode(int resCheckCode) {
		this.resCheckCode = resCheckCode;
	}

	@Expose
	private long vs = 1;

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

	public long getVs() {
		return vs;
	}

	public void setVs(long vs) {
		this.vs = vs;
	}

	public String getResCheckMessage() {
		return resCheckMessage;
	}

	public void setResCheckMessage(String resCheckMessage) {
		this.resCheckMessage = resCheckMessage;
	}

}
