package com.zte.weibo.databinder;

import com.zte.databinder.PageBinder;

public class MicroblogBinder extends PageBinder {

	private String type; //搜索类型（0：全部；1：搜分享；2：搜用户）
	private String timeStart;//开始时间
	private String timeEnd;//结束时间
	private String keyword;//关键字
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}
	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
