package com.zte.databinder;

public class ChatRecordBinder extends PageBinder {
	
	private int type;
	
	private String keyword;

	private String startTime;

	private String endTime;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
