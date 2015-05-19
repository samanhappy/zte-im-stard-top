package com.zte.weibo.bean;

import com.google.gson.annotations.Expose;
import com.zte.im.bean.Base;

public class User extends Base {
	
	@Expose
	private String id; // PostgreSQL数据主键
	@Expose
	private Long uid;// im自己的id
	@Expose
	private Long gid;// 企业id
	@Expose
	private String loginname;
	@Expose
	private String name;// 真实姓名
	@Expose
	private String sname;// 源名称
	@Expose
	private String pwd;// 密码
	@Expose
	private String sex;// 性别，1男0女
	@Expose
	private String birthday; // 生日
	@Expose
	private String jid;// jobid工号
	@Expose
	private String mob;
	@Expose
	private String email;
	@Expose
	private Long cid; // 架构表的唯一id关联
	@Expose
	private String cname; // 机构名
	@Expose
	private String post;// 职位
	@Expose
	private String autograph;// 个人签名
	@Expose
	private String telephone; // 家庭电话
	@Expose
	private String fax; // 传真电话
	
	/*
	 * 此处设计人员拼写错误，实际应该为voip*
	 */
	@Expose
	private String viopId;
	@Expose
	private String viopPwd;
	@Expose
	private String viopSid;
	@Expose
	private String viopSidPwd;

	@Expose
	private String minipicurl;// head portrait 缩写，用户头像
	@Expose
	private String bigpicurl;// source head portrait 缩写，用户头像原图
	
	@Expose
	private Integer pwdErrors; //连续输入密码错误
	
	@Expose
	private String state;//用户状态，0：停用；1：启用
	
	@Expose
	private int isDelete;//是否删除
	
	private Long sortId ;//排序
	
	
	@Expose
	private Long ver;
	@Expose
	private Long groupVer;


	// 所属部门的PostgreSQL数据ID
	@Expose
	private String deptId;



	
	
	
	// 这部分字段不存在数据 库中
	@Expose
	private long ycwbs;//原创分享数
	@Expose
	private long zfwbs;//转发分享数
	@Expose
	private long cjqzs;//创建圈子数
	@Expose
	private long cyqzs;//参与圈子数
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getGid() {
		return gid;
	}

	public void setGid(Long gid) {
		this.gid = gid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getJid() {
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	public String getMob() {
		return mob;
	}

	public void setMob(String mob) {
		this.mob = mob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getAutograph() {
		return autograph;
	}

	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}


	public String getViopId() {
		return viopId;
	}

	public void setViopId(String viopId) {
		this.viopId = viopId;
	}

	public String getViopPwd() {
		return viopPwd;
	}

	public void setViopPwd(String viopPwd) {
		this.viopPwd = viopPwd;
	}

	public String getViopSid() {
		return viopSid;
	}

	public void setViopSid(String viopSid) {
		this.viopSid = viopSid;
	}

	public String getViopSidPwd() {
		return viopSidPwd;
	}

	public void setViopSidPwd(String viopSidPwd) {
		this.viopSidPwd = viopSidPwd;
	}

	public String getMinipicurl() {
		return minipicurl;
	}

	public void setMinipicurl(String minipicurl) {
		this.minipicurl = minipicurl;
	}

	public String getBigpicurl() {
		return bigpicurl;
	}

	public void setBigpicurl(String bigpicurl) {
		this.bigpicurl = bigpicurl;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Long getVer() {
		return ver;
	}

	public void setVer(Long ver) {
		this.ver = ver;
	}

	public Long getGroupVer() {
		return groupVer;
	}

	public void setGroupVer(Long groupVer) {
		this.groupVer = groupVer;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Integer getPwdErrors() {
		return pwdErrors;
	}

	public void setPwdErrors(Integer pwdErrors) {
		this.pwdErrors = pwdErrors;
	}


	public long getYcwbs() {
		return ycwbs;
	}

	public void setYcwbs(long ycwbs) {
		this.ycwbs = ycwbs;
	}

	public long getZfwbs() {
		return zfwbs;
	}

	public void setZfwbs(long zfwbs) {
		this.zfwbs = zfwbs;
	}

	public long getCjqzs() {
		return cjqzs;
	}

	public void setCjqzs(long cjqzs) {
		this.cjqzs = cjqzs;
	}

	public long getCyqzs() {
		return cyqzs;
	}

	public void setCyqzs(long cyqzs) {
		this.cyqzs = cyqzs;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Long getSortId() {
		return sortId;
	}

	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


}
