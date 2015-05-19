package com.zte.im.bean;

import com.google.gson.annotations.Expose;

public class Param {

	@Expose
	private String id;
	@Expose
	private String pwdExpire;
	@Expose
	private String pwdPeriod;
	@Expose
	private String pwdLength;
	@Expose
	private String pwdMinLength;
	@Expose
	private String pwdMaxLength;
	@Expose
	private String pwdCheck;
	@Expose
	private String pwdFirstCheck;
	@Expose
	private String loginCheck;
	@Expose
	private String ipCheck;
	@Expose
	private String loginAuthType;
	@Expose
	private String ldapUrl;
	@Expose
	private String baseDN;
	@Expose
	private String domain;

	@Expose
	private String contactOrgName;
	@Expose
	private String syncLDAP;
	@Expose
	private String contactLdapUrl;
	@Expose
	private String contactBaseDN;
	@Expose
	private String protectedPropVals;
	@Expose
	private String protectedPropNames;
	@Expose
	private String editablePropVals;
	@Expose
	private String editablePropNames;

	@Expose
	private String paramName;
	@Expose
	private String paramType;

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getEditablePropNames() {
		return editablePropNames;
	}

	public void setEditablePropNames(String editablePropNames) {
		this.editablePropNames = editablePropNames;
	}

	public String getProtectedPropNames() {
		return protectedPropNames;
	}

	public void setProtectedPropNames(String protectedPropNames) {
		this.protectedPropNames = protectedPropNames;
	}

	public String getPwdExpire() {
		return pwdExpire;
	}

	public void setPwdExpire(String pwdExpire) {
		this.pwdExpire = pwdExpire;
	}

	public String getLdapUrl() {
		return ldapUrl;
	}

	public void setLdapUrl(String ldapUrl) {
		this.ldapUrl = ldapUrl;
	}

	public String getPwdPeriod() {
		return pwdPeriod;
	}

	public String getContactOrgName() {
		return contactOrgName;
	}

	public void setContactOrgName(String contactOrgName) {
		this.contactOrgName = contactOrgName;
	}

	public String getSyncLDAP() {
		return syncLDAP;
	}

	public void setSyncLDAP(String syncLDAP) {
		this.syncLDAP = syncLDAP;
	}

	public String getContactLdapUrl() {
		return contactLdapUrl;
	}

	public void setContactLdapUrl(String contactLdapUrl) {
		this.contactLdapUrl = contactLdapUrl;
	}

	public String getContactBaseDN() {
		return contactBaseDN;
	}

	public void setContactBaseDN(String contactBaseDN) {
		this.contactBaseDN = contactBaseDN;
	}

	public String getProtectedPropVals() {
		return protectedPropVals;
	}

	public void setProtectedPropVals(String protectedPropVals) {
		this.protectedPropVals = protectedPropVals;
	}

	public String getEditablePropVals() {
		return editablePropVals;
	}

	public void setEditablePropVals(String editablePropVals) {
		this.editablePropVals = editablePropVals;
	}

	public void setPwdPeriod(String pwdPeriod) {
		this.pwdPeriod = pwdPeriod;
	}

	public String getPwdLength() {
		return pwdLength;
	}

	public void setPwdLength(String pwdLength) {
		this.pwdLength = pwdLength;
	}

	public String getPwdMinLength() {
		return pwdMinLength;
	}

	public void setPwdMinLength(String pwdMinLength) {
		this.pwdMinLength = pwdMinLength;
	}

	public String getPwdMaxLength() {
		return pwdMaxLength;
	}

	public void setPwdMaxLength(String pwdMaxLength) {
		this.pwdMaxLength = pwdMaxLength;
	}

	public String getPwdCheck() {
		return pwdCheck;
	}

	public void setPwdCheck(String pwdCheck) {
		this.pwdCheck = pwdCheck;
	}

	public String getPwdFirstCheck() {
		return pwdFirstCheck;
	}

	public void setPwdFirstCheck(String pwdFirstCheck) {
		this.pwdFirstCheck = pwdFirstCheck;
	}

	public String getLoginCheck() {
		return loginCheck;
	}

	public void setLoginCheck(String loginCheck) {
		this.loginCheck = loginCheck;
	}

	public String getIpCheck() {
		return ipCheck;
	}

	public void setIpCheck(String ipCheck) {
		this.ipCheck = ipCheck;
	}

	public String getLoginAuthType() {
		return loginAuthType;
	}

	public void setLoginAuthType(String loginAuthType) {
		this.loginAuthType = loginAuthType;
	}

	public String getBaseDN() {
		return baseDN;
	}

	public void setBaseDN(String baseDN) {
		this.baseDN = baseDN;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
