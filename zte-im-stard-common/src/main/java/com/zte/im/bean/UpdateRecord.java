package com.zte.im.bean;

import com.google.gson.annotations.Expose;

public class UpdateRecord {
	@Expose
	private Long gid;// 企业id
	@Expose
	private Long uid;//用户唯一标识
	@Expose
	private long userListVer;//用户版本数据
	@Expose
	private String oper;
    public Long getGid() {
        return gid;
    }
    public void setGid(Long gid) {
        this.gid = gid;
    }
    public long getuserListVer() {
        return userListVer;
    }
    public void setUserListVer(long userListVer) {
        this.userListVer = userListVer;
    }
    public Long getUid() {
        return uid;
    }
    public void setUid(Long uid) {
        this.uid = uid;
    }
    public String getOper() {
        return oper;
    }
    public void setOper(String oper) {
        this.oper = oper;
    }
	
}
