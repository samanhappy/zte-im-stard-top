package com.zte.im.mybatis.bean.my;

import java.util.Date;

import com.google.gson.annotations.Expose;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zte.im.mybatis.bean.UcTenant;

public class UcTenantMy  extends UcTenant{
	@Expose
	private String id;

	private String platformId;

	private String ecid;

	private String name;

	private String catalogId;

	private Long maxUsers;

	private String address;

	private String linkman;

	private String mobile;

	private String mail;

	private String tel;

	private String fax;

	private String orgCode;

	private String licenseFile;

	private Long userCount;

	private String audited;

	private String initialized;

	private String usable;

	private Date pauseTime;

	private String isFixed;

	private String creator;

	private Date createTime;

	private String modifier;

	private Date modifiedTime;
	@Expose
	private String userName;
	@Expose
	private String password;
	@Expose
	private String guimo;
	@Expose
	private String prov;
	@Expose
	private String city;
	@Expose
	private String photoImg;

	public String getPhotoImg() {
		return photoImg;
	}

	public void setPhotoImg(String photoImg) {
		this.photoImg = photoImg;
	}

	public String getGuimo() {
		return guimo;
	}

	public void setGuimo(String guimo) {
		this.guimo = guimo;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getEcid() {
		return ecid;
	}

	public void setEcid(String ecid) {
		this.ecid = ecid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public Long getMaxUsers() {
		return maxUsers;
	}

	public void setMaxUsers(Long maxUsers) {
		this.maxUsers = maxUsers;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getLicenseFile() {
		return licenseFile;
	}

	public void setLicenseFile(String licenseFile) {
		this.licenseFile = licenseFile;
	}

	public Long getUserCount() {
		return userCount;
	}

	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}

	public String getAudited() {
		return audited;
	}

	public void setAudited(String audited) {
		this.audited = audited;
	}

	public String getInitialized() {
		return initialized;
	}

	public void setInitialized(String initialized) {
		this.initialized = initialized;
	}

	public String getUsable() {
		return usable;
	}

	public void setUsable(String usable) {
		this.usable = usable;
	}

	public Date getPauseTime() {
		return pauseTime;
	}

	public void setPauseTime(Date pauseTime) {
		this.pauseTime = pauseTime;
	}

	public String getIsFixed() {
		return isFixed;
	}

	public void setIsFixed(String isFixed) {
		this.isFixed = isFixed;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public DBObject toDBObject() {
		DBObject object = new BasicDBObject();
		if (this.getId() != null) {
			object.put("id", this.getId());
		}
		if (this.getUserName() != null) {
			object.put("userName", this.getUserName());
		}
		if (this.getPassword() != null) {
			object.put("password", this.getPassword());
		}
		if (this.getGuimo() != null) {
			object.put("guimo", this.getGuimo());
		}
		if (this.getProv() != null) {
			object.put("prov", this.getProv());
		}
		if (this.getCity() != null) {
			object.put("city", this.getCity());
		}
		if (this.getPhotoImg() != null) {
			object.put("photoImg", this.getPhotoImg());
		}
		return object;
	}
}
