package com.zte.im.bean;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.gson.annotations.Expose;

public class ChatRecord {
	
	@Expose
	private String id; 
	@Expose
	private String sender;
	@Expose
	private String receiver;
	@Expose
	private String time;
	@Expose
	private String payload;
	@Expose
	private String type;
	@Expose
	private String contentType;
	@Expose
	private String content;

	private static Map<String, String> typeDesc = new HashMap<String, String>();

	private static Map<String, String> contentTypeDesc = new HashMap<String, String>();

	static {
		typeDesc.put("0", "普通点对点");
		typeDesc.put("1", "普通群聊");
		typeDesc.put("2", "修改群信息");
		typeDesc.put("3", "添加成员消息");
		typeDesc.put("4", "退群消息");
		typeDesc.put("5", "删除成员消息");
		typeDesc.put("6", "删除群");
		typeDesc.put("7", "");
		typeDesc.put("8", "");
		typeDesc.put("9", "");
		typeDesc.put("10", "点赞");
		typeDesc.put("11", "评论");
		typeDesc.put("12", "转发");
		typeDesc.put("13", "邀请加入圈子");
		typeDesc.put("14", "关注好友");
		typeDesc.put("15", "@提到我");
		typeDesc.put("16", "系统消息");

		contentTypeDesc.put("0", "文字");
		contentTypeDesc.put("1", "文件");
		contentTypeDesc.put("2", "图片");
		contentTypeDesc.put("3", "语音");
		contentTypeDesc.put("4", "新闻");
		contentTypeDesc.put("5", "视频");
		contentTypeDesc.put("6", "VOIP");
		contentTypeDesc.put("7", "更改头像");
		contentTypeDesc.put("8", "VOIP");
		contentTypeDesc.put("9", "VOIP");
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return sender + "->" + receiver + ":" + payload + "@" + time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void handlePayload() {
		if (payload != null) {
			PushBean bean = new PushBean();
			String[] strs = payload.split("#");
			try {
				bean = JSON.parseObject(strs.length > 0 ? strs[1] : strs[0], PushBean.class);
			} catch (Exception e) {
			}
			this.content = bean.getMsg();
			this.type = bean.getType() != null ? typeDesc.get(bean.getType()) : "";
			this.contentType = bean.getContenttype() != null ? contentTypeDesc.get(bean.getContenttype()) : "";
		}
	}

}
