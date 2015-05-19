package com.zte.im.twitter.execute;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.PushBean;
import com.zte.im.bean.TGroup;
import com.zte.im.bean.User;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.ITwitterService;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.CmdExecute;
import com.zte.im.util.IDSequence;

public class ApplyIntoGroup extends AbstractValidatoExecute {
	
	private final static Logger logger = LoggerFactory
			.getLogger(ApplyIntoGroup.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = "true";
		String msg = "";
		try{
			Long userId = json.getLong("userId");
			User user = UserServiceImpl.getInstance().getUserbyid(userId);
			ITwitterService twitterService = TwitterServiceImpl.getInstance();
			if(user != null){
				Date date = new Date();
				Long groupId = json.getLong("groupId");
				TGroup group = twitterService.findGroupDetail(groupId);
				if(group == null){
					resFlag = "false";
					msg = "圈子不存在！";
				}else{
					Long messageId = IDSequence.getUID();
					//推送
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userName", user.getName());
					map.put("userId", userId);
					map.put("groupId", groupId);
					map.put("groupName", group.getGroupName());
					map.put("messageId", messageId);
					PushBean bean = new PushBean();
					bean.setTarget(String.valueOf(group.getCreateUserId()));
					bean.setKeyid(String.valueOf(super.getContext().getUser().getUid()));
					bean.setMsg(JSON.toJSONString(map));
					bean.setModeltype("1");
					bean.setType("18");//请求加入圈子
					bean.setSessionid(String.valueOf(userId));
					bean.setSessionname("申请加入圈子");
					CmdExecute.exe(bean);
					
					//保存系统消息
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("messageId", messageId);
					param.put("groupName", group.getGroupName());
					param.put("groupId", groupId);
					param.put("sendUserId", userId);
					param.put("receiveUserId", group.getCreateUserId());
					param.put("messageType", "01");//01：申请加入圈子
					param.put("messageStatus", "00");//00：未处理
					param.put("createUserId", userId);
					param.put("createTime", date.getTime());
					param.put("updateUserId", userId);
					param.put("updateTime", date.getTime());
					param.put("remark", "");
					twitterService.addSysMessage(param);
				}
				
			}else{
				resFlag = "false";
				msg = "请求用户不存在！";
			}
		}catch(Exception e){
			resFlag = "false";
			msg = e.getMessage();
			logger.error("", e);
		}
		res.put("msg", msg);
		res.put("resFlag", resFlag);
		resJson = JSON.toJSONString(res);
		return resJson;
	}
	
	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("userId", 1000001);
		json.put("groupId", 2094);
		json.put("token", "4602658_75JCEGDQ");
		
		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new ApplyIntoGroup();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("applyIntoGroup");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
