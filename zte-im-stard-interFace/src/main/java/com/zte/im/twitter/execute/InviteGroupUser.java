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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.PushBean;
import com.zte.im.bean.TGroup;
import com.zte.im.bean.TSysMessage;
import com.zte.im.bean.User;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.ITwitterService;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.Constant;
import com.zte.im.util.IDSequence;

/**
 * dec 圈子邀请成员（停用）
 * 
 * @author xubin
 * 
 */
public class InviteGroupUser extends AbstractValidatoExecute {

	private static Logger logger = LoggerFactory.getLogger(InviteGroupUser.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = Constant.TRUE;
		String msg = ""; 
		try {
			Long userId = json.getLong("userId");
			User user = UserServiceImpl.getInstance().getUserbyid(userId);
			ITwitterService twitterService = TwitterServiceImpl.getInstance();
			if (user != null) {
				List<PushBean> list = new ArrayList<PushBean>();
				Date date = new Date();
				JSONArray userArr = json.getJSONArray("uids");
				List<Long> idList = new ArrayList<Long>();
				Long groupId = json.getLong("groupId");
				TGroup group = twitterService
						.findGroupDetail(groupId);
				for (int i = 0; i < userArr.size(); i++) {
					TSysMessage sysMsg = new TSysMessage();
					sysMsg.setSendUserId(userId);
					sysMsg.setReceiveUserId(userArr.getLong(i));
					sysMsg.setMessageType(Constant.TWITTER_SYS_MSG_00);
					sysMsg.setGroupId(groupId);
					sysMsg.setMessageType("02");
					TSysMessage CheckMsg = twitterService.getSysMessage(sysMsg);
					if(null == CheckMsg){
						idList.add(userArr.getLong(i));
					}
				}
				for (int i = 0; i < idList.size(); i++) {
					Long uid = userArr.getLong(i);
					Long messageId = IDSequence.getUID();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userName", user.getName());
					map.put("userId", userId);
					map.put("groupId", groupId);
					map.put("groupName", group.getGroupName());
					map.put("messageId", messageId);
					// 推送
					PushBean bean = new PushBean();
					bean.setTarget(String.valueOf(uid));
					bean.setKeyid(String.valueOf(userId));
					bean.setMsg(JSON.toJSONString(map));
					bean.setModeltype("1");
					bean.setType("13");// 邀请加入圈子
					bean.setSessionid(String.valueOf(userId));
					bean.setSessionname("邀请"); 
					
					list.add(bean);
					
					// 保存系统消息
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("messageId", messageId);
					param.put("groupName", group.getGroupName());
					param.put("groupId", groupId);
					param.put("sendUserId", userId);
					param.put("receiveUserId", uid);
					param.put("messageType", "02");// 02：邀请
					param.put("messageStatus", "00");// 00：未处理
					param.put("createUserId", userId);
					param.put("createTime", date.getTime());
					param.put("updateUserId", userId);
					param.put("updateTime", date.getTime());
					param.put("remark", "");
					twitterService.addSysMessage(param);
				}
				if(list.size()>0){
					MqttPublisher.publish(list);
				}
			} else {
				resFlag = Constant.CODE_8;
				msg = Constant.CODE_8; 
			}
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
		Long[] uids = { (long) 1000002 };
		json.put("userId", 1000037);
		json.put("groupId", 38233);
		json.put("uids", uids);
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new InviteGroupUser();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("inviteGroupUser");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
