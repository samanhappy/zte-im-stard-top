package com.zte.im.bean;

import java.util.List;

import com.google.gson.annotations.Expose;

public class User {
	@Expose
	private String id; // PostgreSQL数据主键
	@Expose
	private Long uid;// im自己的id
	@Expose
	private Long gid;// 企业id
	@Expose
	private String gname;
	@Expose
	private String name;// 真实姓名
	@Expose
	private String pwd;// 密码
	@Expose
	private String nickname;// 昵称
	@Expose
	private String jid;// jobid工号
	@Expose
	private String mob;
	@Expose
	private String email;
	@Expose
	private String sn; //客户端手机唯一标识
	@Expose
	private String token; // 客户端操作验证
	@Expose
	private Long cid; // 架构表的唯一id关联
	@Expose
	private String cname; // 机构名
	@Expose
	private String post;// 职位
	@Expose
	private String minipicurl;// head portrait 缩写，用户头像
	@Expose
	private String bigpicurl;// source head portrait 缩写，用户头像原图
	@Expose
	private List<GroupChat> groupids;
	@Expose
	private String sex;// 性别，1男0女
	@Expose
	private String autograph;// 个人签名
	@Expose
	private String os;// 用户最近一次登陆的客户端类型，android&iphone
	@Expose
	private Long ver;
	
	@Expose
	private String otherMob;
	@Expose
	private String otherTele;
	
	@Expose
	private String telephone; // 家庭电话
	@Expose
	private String fax; // 传真电话
	@Expose
	private String remark; // 备注

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
	private String extNumber; // 分机号

	@Expose
	private Long groupVer;

	@Expose
	private String loginname;

	/**
	 * 微博用户关注信息
	 */
	@Expose
	private String attentionFlag = "0";// 关注标志：0、否；1、是

	@Expose
	private String attentionType;// 关注类型01、一般；02、特别

	@Expose
	private String eachotherFlag;// 互相关注标志：0、否；1、是

	/*
	 * 用户访问权限字段.
	 */
	@Expose
	private List<String> postPerms;
	@Expose
	private List<String> deptPerms;
	@Expose
	private List<String> userPerms;
	@Expose
	private Boolean isProtected; //是否开启保护设置

	// 所属部门的PostgreSQL数据ID
	@Expose
	private String deptId;

	@Expose
	private Long sequ; // 排序字段

	@Expose
	private String birthday; // 生日
	
	@Expose
	private Integer pwdErrors; //连续输入密码错误
	
	@Expose
	private Boolean usable; // 是否启用

	public Boolean getUsable() {
		return usable;
	}

	public void setUsable(Boolean usable) {
		this.usable = usable;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	public Integer getPwdErrors() {
		return pwdErrors;
	}

	public Boolean getIsProtected() {
		return isProtected;
	}

	public void setIsProtected(Boolean isProtected) {
		this.isProtected = isProtected;
	}

	public void setPwdErrors(Integer pwdErrors) {
		this.pwdErrors = pwdErrors;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public List<String> getPostPerms() {
		return postPerms;
	}

	public void setPostPerms(List<String> postPerms) {
		this.postPerms = postPerms;
	}

	public List<String> getDeptPerms() {
		return deptPerms;
	}

	public void setDeptPerms(List<String> deptPerms) {
		this.deptPerms = deptPerms;
	}

	public List<String> getUserPerms() {
		return userPerms;
	}

	public void setUserPerms(List<String> userPerms) {
		this.userPerms = userPerms;
	}

	public List<GroupChat> getGroupids() {
		return groupids;
	}

	public void setGroupids(List<GroupChat> groupids) {
		this.groupids = groupids;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public Long getSequ() {
		return sequ;
	}

	public void setSequ(Long sequ) {
		this.sequ = sequ;
	}

	public Long getGroupVer() {
		return groupVer;
	}

	public void setGroupVer(Long groupVer) {
		this.groupVer = groupVer;
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

	public String getExtNumber() {
		return extNumber;
	}

	public void setExtNumber(String extNumber) {
		this.extNumber = extNumber;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAutograph() {
		return autograph;
	}

	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}

	public String getSex() {
		return sex;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public String getJid() {
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAttentionFlag() {
		return attentionFlag;
	}

	public void setAttentionFlag(String attentionFlag) {
		this.attentionFlag = attentionFlag;
	}

	public String getAttentionType() {
		return attentionType;
	}

	public void setAttentionType(String attentionType) {
		this.attentionType = attentionType;
	}

	public String getEachotherFlag() {
		return eachotherFlag;
	}

	public void setEachotherFlag(String eachotherFlag) {
		this.eachotherFlag = eachotherFlag;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

    public String getOtherMob() {
        return otherMob;
    }

    public void setOtherMob(String otherMob) {
        this.otherMob = otherMob;
    }

    public String getOtherTele() {
        return otherTele;
    }

    public void setOtherTele(String otherTele) {
        this.otherTele = otherTele;
    }

}
