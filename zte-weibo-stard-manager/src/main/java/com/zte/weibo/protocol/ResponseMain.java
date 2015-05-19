package com.zte.weibo.protocol;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.DBObject;
import com.zte.im.mybatis.bean.page.FeedbackPageModel;
import com.zte.im.mybatis.bean.page.MemberPageModel;
import com.zte.im.mybatis.bean.page.SysLogPageModel;
import com.zte.im.util.Constant;
import com.zte.im.util.Page;

public class ResponseMain {
	private Res res;

	private List<?> item;

	private MemberPageModel members;

	private FeedbackPageModel feedbacks;
	
	private List<DBObject> newsList;

	private SysLogPageModel syslog;

	private String fileUrl;

	private String sourceFileUrl;

	private Object data;

	private String roleId;
	
	private boolean result = true;
	
	private boolean isProtected = false;
	
	private String msg;
	
	private Page page;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public List<DBObject> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<DBObject> newsList) {
		this.newsList = newsList;
	}

	public Map<String, Object> initResultMap() {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("res", res);
		return result;
	}

	public boolean isProtected() {
		return isProtected;
	}

	public void setProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ResponseMain() {
		res = new Res();
		res.setReCode(Constant.SUCCESS_CODE);
		res.setResMessage("Operation is successful");
	}

	public Res getRes() {
		return res;
	}

	public void setRes(Res res) {
		this.res = res;
	}

	public List<?> getItem() {
		return item;
	}

	public void setItem(List<?> item) {
		this.item = item;
	}

	public MemberPageModel getMembers() {
		return members;
	}

	public void setMembers(MemberPageModel members) {
		this.members = members;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getSourceFileUrl() {
		return sourceFileUrl;
	}

	public void setSourceFileUrl(String sourceFileUrl) {
		this.sourceFileUrl = sourceFileUrl;
	}

	public FeedbackPageModel getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(FeedbackPageModel feedbacks) {
		this.feedbacks = feedbacks;
	}

	public SysLogPageModel getSyslog() {
		return syslog;
	}

	public void setSyslog(SysLogPageModel syslog) {
		this.syslog = syslog;
	}

}
