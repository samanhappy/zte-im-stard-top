package com.zte.im.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;

import com.zte.im.bean.User;

public class Context {

	private long serverVs;

	private String method;

	private String jsonRequest;

	private User user;
	
	public ApplicationContext applicationContext;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setRequest(HttpServletRequest request) {
		this.jsonRequest = request.getParameter("jsonRequest");
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getJsonRequest() {
		return jsonRequest;
	}

	public void setJsonRequest(String jsonRequest) {
		this.jsonRequest = jsonRequest;
	}

	public long getServerVs() {
		return serverVs;
	}

	public void setServerVs(long serverVs) {
		this.serverVs = serverVs;
	}

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}
