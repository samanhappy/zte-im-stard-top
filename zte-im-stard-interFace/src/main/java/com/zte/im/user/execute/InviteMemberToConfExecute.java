package com.zte.im.user.execute;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.paho.sample.mqttv3app.MqttPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.PushBean;
import com.zte.im.bean.User;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.IUserService;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.NullUtil;

/**
 * 邀请人员加入语音或视频会议，只发送Mqtt通知，不保存数据
 * 
 * @author samanhappy
 * 
 */
public class InviteMemberToConfExecute extends AbstractValidatoExecute {

	private final static Logger logger = LoggerFactory.getLogger(InviteMemberToConfExecute.class);

	@Override
	public String doProcess(JSONObject json) {
		String roomno = json.getString("roomno");
		String confname = json.getString("confname");
		String content = json.getString("content");
		JSONArray users = json.getJSONArray("users");
		Integer conftype = json.getInteger("conftype");
		Long sendtime = json.getLong("sendtime");
		if (NullUtil.isAnyNull(roomno, confname, content, users, conftype, sendtime) || users.size() == 0) {
			return getErrorRes("缺少必要的请求参数");
		}

		Long tokenuid = super.getContext().getUser().getUid();
		IUserService userService = UserServiceImpl.getInstance();
		List<PushBean> list = new ArrayList<PushBean>();
		for (Object obj : users) {
			long uid = Long.valueOf(obj.toString());
			User user = userService.getUserbyid(uid);
			if (user != null) {
				PushBean bean = new PushBean();
				bean.setTarget(String.valueOf(uid));
				bean.setKeyid(String.valueOf(tokenuid));

				bean.setRoomno(roomno);
				bean.setConfname(confname);
				bean.setMsg(content);
				bean.setContenttype(conftype.toString());
				bean.setModeltype("2");
				bean.setSendtime(sendtime);

				list.add(bean);
			} else {
				logger.warn("user is null for uid:{}", uid);
			}
		}
		// 发送推送通知
		MqttPublisher.publish(list);
		return getSuccessRes();
	}
}
