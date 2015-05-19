package com.zte.im.protocol;

import com.google.gson.annotations.Expose;
import com.zte.im.bean.DataVer;
import com.zte.im.bean.GroupChat;
import com.zte.im.bean.Software;
import com.zte.im.bean.User;

public class ResponseMain {
	@Expose
	private Res res;
	@Expose
	private User user;
	@Expose
	private GroupChat groupChat;
	@Expose
	private Software software;
	@Expose
	private String host;
	@Expose
	private DataVer dataVer;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public DataVer getDataVer() {
		return dataVer;
	}

	public void setDataVer(DataVer dataVer) {
		this.dataVer = dataVer;
	}

	public Software getSoftware() {
		return software;
	}

	public void setSoftware(Software software) {
		this.software = software;
	}

	public Res getRes() {
		return res;
	}

	public void setRes(Res res) {
		this.res = res;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public GroupChat getGroupChat() {
		return groupChat;
	}

	public void setGroupChat(GroupChat groupChat) {
		this.groupChat = groupChat;
	}

}
