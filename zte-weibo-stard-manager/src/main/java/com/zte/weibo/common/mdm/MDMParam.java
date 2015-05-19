package com.zte.weibo.common.mdm;

import com.google.gson.annotations.Expose;

public class MDMParam {

	@Expose
	public String count;
	@Expose
	public String cursor;

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getCursor() {
		return cursor;
	}

	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

}
