package com.zte.im.bean;

import com.google.gson.annotations.Expose;

public class WorkImg {

	@Expose
	private String imgUrl;
	@Expose
	private Integer type;
	@Expose
	private Integer index;

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}
