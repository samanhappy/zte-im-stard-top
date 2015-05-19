package com.zte.im.bean;

import com.google.gson.annotations.Expose;
import com.zte.im.bean.User;

public class Users {
	@Expose
	User users;
	@Expose
	User userlist;

	public User getUserlist() {
		return userlist;
	}

	public void setUserlist(User userlist) {
		this.userlist = userlist;
	}

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}

}
