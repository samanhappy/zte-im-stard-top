package com.zte.im.twitter.execute;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.paho.sample.mqttv3app.MqttPublisher;
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
import com.zte.im.service.IUserService;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.Constant;
import com.zte.im.util.IDSequence;

/**
 * dec 删除圈子成员
 * 
 * @author xubin
 * 
 */
public class RemoveGroupUser extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(RemoveGroupUser.class);
	
	private static String REMOVE_GROUP_USER_TYPE_01 = "01";	//移除圈子成员
	
	private static String REMOVE_GROUP_USER_TYPE_02 = "02";	//退出圈子

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = Constant.TRUE;
		String msg = "";
		try {
			Long groupId = json.getLong("groupId");
			Long userId = json.getLong("userId");
			String operType = json.getString("operType");
			IUserService userService = UserServiceImpl.getInstance();
			ITwitterService twitterService = TwitterServiceImpl.getInstance();
			User user = userService.getUserbyid(userId);// 圈子成员信息
			User operUser = super.getContext().getUser();
			List<PushBean> list = new ArrayList<PushBean>();
			twitterService.removeGroupUser(groupId, userId);
			TGroup group = twitterService.findGroupDetail(groupId);
			Date date = new Date();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("groupId", groupId);
			map.put("groupName", group.getGroupName());
			map.put("userId", userId);
			map.put("userName", user.getName());
			map.put("operUserId", operUser.getUid());
			map.put("operUserName", operUser.getName());
			
			Long messageId = IDSequence.getUID();
			map.put("messageId", messageId);
			
			Map<String, Object> paramMes = new HashMap<String, Object>();
			paramMes.put("messageId", messageId);
			paramMes.put("groupName", group.getGroupName());
			paramMes.put("groupId", groupId);
			paramMes.put("sendUserId", "");
			paramMes.put("receiveUserId", userId);
			paramMes.put("messageStatus", "01");// 01：已处理
			paramMes.put("createUserId", userId);
			paramMes.put("createTime", date.getTime());
			paramMes.put("updateUserId", userId);
			paramMes.put("updateTime", date.getTime());
			paramMes.put("remark", "");
			
			//移除圈子成员
			if(operType != null && REMOVE_GROUP_USER_TYPE_01.equals(operType)){
				// 推送，提示用户已被移除圈子
				PushBean bean = new PushBean();
				bean.setTarget(String.valueOf(userId));
				bean.setKeyid(String.valueOf(super.getContext().getUser()
						.getUid()));
				bean.setMsg(JSON.toJSONString(map));
				bean.setModeltype("1");
				bean.setType("24");// 提示用户已被移除圈子
				bean.setSessionid(String.valueOf(userId));
				bean.setSessionname("提示用户已被移除圈子"); 
				list.add(bean);
				
				// 推送，提示用户已被移除圈子,保存进系统消息
				paramMes.put("messageType", "10");// 10：提示用户已被移除圈子
				twitterService.addSysMessage(paramMes);
			}else if(operType != null && REMOVE_GROUP_USER_TYPE_02.equals(operType)){	//退出圈子
				// 推送给圈子创建者，提示用户xx已退出圈子
				PushBean bean = new PushBean();
				bean.setTarget(String.valueOf(group.getCreateUserId()));
				bean.setKeyid(String.valueOf(super.getContext().getUser()
						.getUid()));
				bean.setMsg(JSON.toJSONString(map));
				bean.setModeltype("1");
				bean.setType("27");// 27：推送给圈子创建者，提示用户xx已退出圈子
				bean.setSessionid(String.valueOf(userId));
				bean.setSessionname("推送给圈子创建者，提示用户xx已退出圈子"); 
				list.add(bean);
				
				// 推送给圈子创建者，提示用户xx已退出圈子,保存进系统消息
				paramMes.put("messageType", "11");// 11：提示用户xx已退出圈子
				twitterService.addSysMessage(paramMes);
			}
			MqttPublisher.publish(list);
		} catch (Exception e) {
			resFlag = Constant.ERROR_CODE;
			msg = e.getMessage();
			logger.error("", e);
		}
		Map<String, Object> res2 = new HashMap<String, Object>();
		res2.put("resMessage", ""+msg);
		res2.put("resCode", resFlag);
		res2.put("vs", Constant.TRUE);
		res.put("res", res2);
		resJson = JSON.toJSONString(res);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("userId", 695);
		json.put("groupId", 1046);
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new RemoveGroupUser();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("removeGroupUser");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
