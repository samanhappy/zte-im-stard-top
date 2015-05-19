package com.zte.im.bean;

import com.google.gson.annotations.Expose;

public class ProgrameFinish {
	@Expose
	private Long id;
	@Expose
	private Long userId;
	@Expose
	private Long intervalProgId;
	@Expose
	private String finishTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getIntervalProgId() {
		return intervalProgId;
	}

	public void setIntervalProgId(Long intervalProgId) {
		this.intervalProgId = intervalProgId;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

}
