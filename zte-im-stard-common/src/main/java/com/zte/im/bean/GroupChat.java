package com.zte.im.bean;

import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;

public class GroupChat {
	@Expose
	private Long groupid;// 群唯一主键
	@Expose
	private String token;// 协议用的
	@Expose
	private List<Long> users;// 群中所有用户,客户端协议用的
	@Expose
	private String groupName;// 群名称
	@Expose
	private Integer len;// 当前群总人数
	@Expose
	private Integer maxLen;// 最大人数
	@Expose
	private Long uid;// 创建人
	@Expose
	private String name;// 创建人姓名
	@Expose
	private List<User> userlist;// 群中用户信息
	@Expose
	private Long ver;// 群版本号,客户端判断是否要更新数据使用
	@Expose
	private Integer isTemp;// 是否临时群组
	@Expose
	private Date createTime;// 创建时间
	@Expose
	private Integer userCount;// 用户人数

	public Long getVer() {
		return ver;
	}

	public void setVer(Long ver) {
		this.ver = ver;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsTemp() {
		return isTemp;
	}

	public void setIsTemp(Integer isTemp) {
		this.isTemp = isTemp;
	}

	public List<User> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<User> userlist) {
		this.userlist = userlist;
	}

	public List<Long> getUsers() {
		return users;
	}

	public void setUsers(List<Long> users) {
		this.users = users;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getGroupid() {
		return groupid;
	}

	public void setGroupid(Long groupid) {
		this.groupid = groupid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getLen() {
		return len;
	}

	public void setLen(Integer len) {
		this.len = len;
	}

	public Integer getMaxLen() {
		return maxLen;
	}

	public void setMaxLen(Integer maxLen) {
		this.maxLen = maxLen;
	}
}
