package com.zte.im.bean;

import java.util.Date;

import com.google.gson.annotations.Expose;

public class Inform {

	@Expose
	private String informid;
	@Expose
	private String creator;
	@Expose
	private String theme;
	@Expose
	private String address;
	@Expose
	private String time;
	@Expose
	private Object[] users;
	@Expose
	private String remarks;
	@Expose
	private Date create_time = new Date();

	public String getInformid() {
		return informid;
	}

	public void setInformid(String informid) {
		this.informid = informid;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Object[] getUsers() {
		return users;
	}

	public void setUsers(Object[] users) {
		this.users = users;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

}
