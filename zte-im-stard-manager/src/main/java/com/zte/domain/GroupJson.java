package com.zte.domain;

import com.google.gson.annotations.Expose;

public class GroupJson {
	@Expose
	private String text;
	@Expose
	private String id;
	@Expose
	private String parent;
	@Expose
	private Boolean children;
	@Expose
	private String fullId;

	public String getFullId() {
		return fullId;
	}

	public void setFullId(String fullId) {
		this.fullId = fullId;
	}

	public Boolean getChildren() {
		return children;
	}

	public void setChildren(Boolean children) {
		this.children = children;
	}

	public GroupJson() {
		super();
	}

	public GroupJson(String parent) {
		super();
		this.parent = parent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

}
