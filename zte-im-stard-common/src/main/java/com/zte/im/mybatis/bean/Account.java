package com.zte.im.mybatis.bean;

import com.google.gson.annotations.Expose;

public class Account {

	@Expose
	private String name;
	@Expose
	private String pwd;
	@Expose
	private String tenantId;
	@Expose
	private String privileges;
	@Expose
	private String role;
	
	public Account(String name, String pwd, String tenantId, String privileges,
			String role) {
		super();
		this.name = name;
		this.pwd = pwd;
		this.tenantId = tenantId;
		this.privileges = privileges;
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getPrivileges() {
		return privileges;
	}

	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}

}
