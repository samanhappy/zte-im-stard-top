package com.zte.im.bean;

public class PushBean {

	private String title;

	private String content;

	private String imgurl;
	
	private String newsurl;

	private String publishtime;
	
	private String publisher;

	private String contenttype;

	private String minipicurl; // 头像缩略图

	private String bigpicurl; // 头像原图

	private String uid; // 用户ID

	private String target; // 消息接收方，g开头表示群组

	private String sessionid;// 群id groupid
	private String sessionname;// 群名字
	private String type;// 消息类型0普通点对点，1普通群聊2系统创建群消息
						// 10：点赞;11：评论;12：转发;13：邀请加入圈子;14：关注好友;15：@提到我;16：系统消息;17：微博中的@;18:申请加入圈子;
						// 19：提示用户已加入圈子；20：提示圈子创建者xx用户已加入圈子；21：推送给发出邀请的人，被邀请用户已加入xx圈子；22：推送给发出邀请的人，被邀请用户拒绝加入xx圈子；
						// 23：推送给申请人，提示被拒绝加入圈子；24：提示用户已被移除圈子；25:推送圈子创建者，提示已拒绝xx用户加入xx圈子；26：推送给被邀请人，提示已拒绝加入圈子；
						// 27：推送给圈子创建者，提示用户xx已退出圈子
	private String msg;// 消息内容
	private String username;// 发送者名称
	private String keyid;// 发送者uid
	private String headpicurl;

	private String modeltype;// 区别推送模块

	private String adminid; // 群主ID
	private String adminname; // 群主名

	private String operatorid; // 被操作ID，如被删除的用户
	
	private long sendtime;
	
	private String roomno; // 会议房间号
	private String confname; // 会议名称
	
	private String inform;
	
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public long getSendtime() {
		return sendtime;
	}

	public void setSendtime(long sendtime) {
		this.sendtime = sendtime;
	}

	public String getRoomno() {
		return roomno;
	}

	public void setRoomno(String roomno) {
		this.roomno = roomno;
	}

	public String getConfname() {
		return confname;
	}

	public void setConfname(String confname) {
		this.confname = confname;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getAdminid() {
		return adminid;
	}

	public void setAdminid(String adminid) {
		this.adminid = adminid;
	}

	public String getOperatorid() {
		return operatorid;
	}

	public void setOperatorid(String operatorid) {
		this.operatorid = operatorid;
	}

	public String getAdminname() {
		return adminname;
	}

	public void setAdminname(String adminname) {
		this.adminname = adminname;
	}

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getContent() {
		return content;
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

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(String publishtime) {
		this.publishtime = publishtime;
	}

	public String getContenttype() {
		return contenttype;
	}

	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}

	public String getModeltype() {
		return modeltype;
	}

	public void setModeltype(String modeltype) {
		this.modeltype = modeltype;
	}

	public String getNewsurl() {
		return newsurl;
	}

	public void setNewsurl(String newsurl) {
		this.newsurl = newsurl;
	}

    public String getInform() {
        return inform;
    }

    public void setInform(String inform) {
        this.inform = inform;
    }
	

}
