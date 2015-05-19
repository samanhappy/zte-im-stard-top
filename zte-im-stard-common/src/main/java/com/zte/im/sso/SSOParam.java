package com.zte.im.sso;

import com.google.gson.annotations.Expose;

public class SSOParam {

	@Expose
	public String CommandName = "CheckSSOTokenForAPP";
	@Expose
	public String AppName = "MOA";
	@Expose
	public String AppId = "A00154";
	@Expose
	public String Token;
	@Expose
	public String isGetUserInfo = "0";
	@Expose
	public String LangId = "2052";

	public String getCommandName() {
		return CommandName;
	}

	public void setCommandName(String commandName) {
		CommandName = commandName;
	}

	public String getAppName() {
		return AppName;
	}

	public void setAppName(String appName) {
		AppName = appName;
	}

	public String getAppId() {
		return AppId;
	}

	public void setAppId(String appId) {
		AppId = appId;
	}

	public String getToken() {
		return Token;
	}

	public void setToken(String token) {
		Token = token;
	}

	public String getIsGetUserInfo() {
		return isGetUserInfo;
	}

	public void setIsGetUserInfo(String isGetUserInfo) {
		this.isGetUserInfo = isGetUserInfo;
	}

	public String getLangId() {
		return LangId;
	}

	public void setLangId(String langId) {
		LangId = langId;
	}

}
