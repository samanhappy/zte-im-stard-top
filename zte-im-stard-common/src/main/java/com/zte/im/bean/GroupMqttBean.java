package com.zte.im.bean;

import com.google.gson.annotations.Expose;

public class GroupMqttBean {
	@Expose
	private String sessionid;// 群id groupid
	@Expose
	private String sessionname;// 群名字
	@Expose
	private String type;// 消息类型0普通点对点，1普通群聊2系统创建群消息
	@Expose
	private String msg;// 消息内容
	@Expose
	private String username;// 发送者名称
	@Expose
	private String keyid;// 发送者uid
	@Expose
	private String headpicurl;
	@Expose
	private String contenttype;

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getSessionname() {
		return sessionname;
	}

	public void setSessionname(String sessionname) {
		this.sessionname = sessionname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getKeyid() {
		return keyid;
	}

	public void setKeyid(String keyid) {
		this.keyid = keyid;
	}

	public String getHeadpicurl() {
		return headpicurl;
	}

	public void setHeadpicurl(String headpicurl) {
		this.headpicurl = headpicurl;
	}

	public String getContenttype() {
		return contenttype;
	}

	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}
}
