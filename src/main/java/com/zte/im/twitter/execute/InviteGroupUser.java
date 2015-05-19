package com.zte.im.twitter.execute;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.PushBean;
import com.zte.im.bean.TGroup;
import com.zte.im.bean.User;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.twitter.job.MqttMessager;
import com.zte.im.util.CmdExecute;
import com.zte.im.util.IDSequence;

/**
 * dec 圈子邀请成员
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
		String resFlag = "true";
		String msg = "";
		try {
			Long userId = json.getLong("userId");
			User user = UserServiceImpl.getInstance().getUserbyid(userId);
			if (user != null) {
				Date date = new Date();
				JSONArray userArr = json.getJSONArray("uids");
				Long groupId = json.getLong("groupId");
				TGroup group = TwitterServiceImpl.getInstance()
						.findGroupDetail(groupId);
				for (int i = 0; i < userArr.size(); i++) {
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
					bean.setKeyid(String.valueOf(super.getContext().getUser()
							.getUid()));
					bean.setMsg(JSON.toJSONString(map));
					bean.setModeltype("1");
					bean.setType("13");// 请求加入圈子
					bean.setSessionid(String.valueOf(userId));
					bean.setSessionname("邀请"); 
					List<PushBean> list = new ArrayList<PushBean>();
					list.add(bean);
					new MqttMessager(list).start();

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
					TwitterServiceImpl.getInstance().addSysMessage(param);
				}
			} else {
				resFlag = "false";
				msg = "请求用户不存在！";
			}
		} catch (Exception e) {
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
		Long[] uids = { (long) 1000002 };
		json.put("userId", 1000001);
		json.put("groupId", 2094);
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
