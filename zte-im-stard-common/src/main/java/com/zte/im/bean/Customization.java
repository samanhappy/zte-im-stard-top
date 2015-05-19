package com.zte.im.bean;

import com.google.gson.annotations.Expose;

public class Customization {

	@Expose
	private String id;
	@Expose
	private String serverLogo;
	@Expose
	private String serverLoginLogo;
	@Expose
	private String serverManagerName;
	@Expose
	private String serverCopyright;
	@Expose
	private String clientLogo;
	@Expose
	private String clientName;
	@Expose
	private String clientCopyright;
	@Expose
	private String clientHomePage;
	@Expose
	private String clientContact;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServerLogo() {
		return serverLogo;
	}

	public String getServerLoginLogo() {
		return serverLoginLogo;
	}

	public void setServerLoginLogo(String serverLoginLogo) {
		this.serverLoginLogo = serverLoginLogo;
	}

	public void setServerLogo(String serverLogo) {
		this.serverLogo = serverLogo;
	}

	public String getServerManagerName() {
		return serverManagerName;
	}

	public void setServerManagerName(String serverManagerName) {
		this.serverManagerName = serverManagerName;
	}

	public String getServerCopyright() {
		return serverCopyright;
	}

	public void setServerCopyright(String serverCopyright) {
		this.serverCopyright = serverCopyright;
	}

	public String getClientLogo() {
		return clientLogo;
	}

	public void setClientLogo(String clientLogo) {
		this.clientLogo = clientLogo;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientCopyright() {
		return clientCopyright;
	}

	public void setClientCopyright(String clientCopyright) {
		this.clientCopyright = clientCopyright;
	}

	public String getClientHomePage() {
		return clientHomePage;
	}

	public void setClientHomePage(String clientHomePage) {
		this.clientHomePage = clientHomePage;
	}

	public String getClientContact() {
		return clientContact;
	}

	public void setClientContact(String clientContact) {
		this.clientContact = clientContact;
	}

}
