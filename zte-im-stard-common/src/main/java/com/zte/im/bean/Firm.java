package com.zte.im.bean;

import java.util.List;

import com.google.gson.annotations.Expose;

public class Firm {
	@Expose
	private Long gid;
	@Expose
	private String name;
	@Expose
	private List<User> users;
	@Expose
	private Long ver;
	@Expose
	private Long cid;
	@Expose
	private List<?> composition;
	@Expose
	private Long maxpage;

	public Long getGid() {
		return gid;
	}

	public void setGid(Long gid) {
		this.gid = gid;
	}

	public Long getMaxpage() {
		return maxpage;
	}

	public void setMaxpage(Long maxpage) {
		this.maxpage = maxpage;
	}

	public List<?> getComposition() {
		return composition;
	}

	public void setComposition(List<?> composition) {
		this.composition = composition;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Long getVer() {
		return ver;
	}

	public void setVer(Long ver) {
		this.ver = ver;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
