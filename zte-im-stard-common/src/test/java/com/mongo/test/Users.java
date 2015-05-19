package com.mongo.test;

import com.google.gson.annotations.Expose;
import com.zte.im.bean.User;

public class Users {
	@Expose
	User users;

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}

}
