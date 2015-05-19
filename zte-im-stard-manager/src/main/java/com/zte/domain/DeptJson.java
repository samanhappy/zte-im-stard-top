package com.zte.domain;

import com.google.gson.annotations.Expose;

public class DeptJson {

	@Expose
	private String id;
	@Expose
	public String pId;
	@Expose
	private String name;
	@Expose
	private Boolean open = true;
	@Expose
	private String url = "list.jsp";
	@Expose
	private String target = "pageFrame";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
