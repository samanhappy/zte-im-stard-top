package com.zte.im.bean;

import com.google.gson.annotations.Expose;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class LicenseInfo {

	@Expose
	private Long id;
	@Expose
	private String license_desc;// 版本信息
	@Expose
	private String license_days;// 授权总天数
	@Expose
	private String license_left_days;// 剩余天数
	@Expose
	private String license_user_nums;// 总用户数
	@Expose
	private String license_user_used_nums;// 剩余用户数
	@Expose
	private String remind_rule;// 提醒规则
	@Expose
	private String remind_mode;// 提醒方式
	@Expose
	private String remind_email_addr;// 提醒邮箱

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLicense_desc() {
		return license_desc;
	}

	public void setLicense_desc(String license_desc) {
		this.license_desc = license_desc;
	}

	public String getLicense_days() {
		return license_days;
	}

	public void setLicense_days(String license_days) {
		this.license_days = license_days;
	}

	public String getLicense_left_days() {
		return license_left_days;
	}

	public void setLicense_left_days(String license_left_days) {
		this.license_left_days = license_left_days;
	}

	public String getLicense_user_nums() {
		return license_user_nums;
	}

	public void setLicense_user_nums(String license_user_nums) {
		this.license_user_nums = license_user_nums;
	}

	public String getLicense_user_used_nums() {
		return license_user_used_nums;
	}

	public void setLicense_user_used_nums(String license_user_used_nums) {
		this.license_user_used_nums = license_user_used_nums;
	}

	public String getRemind_rule() {
		return remind_rule;
	}

	public void setRemind_rule(String remind_rule) {
		this.remind_rule = remind_rule;
	}

	public String getRemind_mode() {
		return remind_mode;
	}

	public void setRemind_mode(String remind_mode) {
		this.remind_mode = remind_mode;
	}

	public String getRemind_email_addr() {
		return remind_email_addr;
	}

	public void setRemind_email_addr(String remind_email_addr) {
		this.remind_email_addr = remind_email_addr;
	}

	public DBObject toDbObject() {
		DBObject dbObject = new BasicDBObject();
		if (null != this.id)
			dbObject.put("id", this.getId());
		if (null != this.license_desc)
			dbObject.put("license_desc", this.getLicense_desc());
		if (null != this.license_days)
			dbObject.put("license_days", this.getLicense_days());
		if (null != this.license_left_days)
			dbObject.put("license_left_days", this.getLicense_left_days());
		if (null != this.license_user_nums)
			dbObject.put("license_user_nums", this.getLicense_user_nums());
		if (null != this.license_user_used_nums)
			dbObject.put("license_user_used_nums",
					this.getLicense_user_used_nums());
		if (null != this.remind_rule)
			dbObject.put("remind_rule", this.getRemind_rule());
		if (null != this.remind_mode)
			dbObject.put("remind_mode", this.getRemind_mode());
		if (null != this.remind_email_addr)
			dbObject.put("remind_email_addr", this.getRemind_email_addr());
		return dbObject;
	}

}
