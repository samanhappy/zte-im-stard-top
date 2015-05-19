package com.zte.weibo.databinder;

import com.zte.databinder.PageBinder;

public class AccountBinder extends PageBinder {

	private String name ;
	private String state;//0：停用；1：启用；
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
