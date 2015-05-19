package com.zte.im.user.execute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.GroupChat;
import com.zte.im.bean.User;
import com.zte.im.protocol.Res;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.IGroupService;
import com.zte.im.service.IUserService;
import com.zte.im.service.impl.GroupServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.Constant;

/**
 * 邀请好友加入群
 * 
 * @author Administrator
 * 
 */
public class RemoveGroupExecute extends AbstractValidatoExecute {
	
	private final static Logger logger = LoggerFactory
			.getLogger(RemoveGroupExecute.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		IGroupService service = GroupServiceImpl.getInstance();
		IUserService userService = UserServiceImpl.getInstance();
		Long groupid = json.getLong("groupid");
		Long tokenuid = super.getContext().getUser().getUid();
		GroupChat group = service.findGroupChatById(groupid);
		if (group != null) {
			// 判断当前用户是否是群的管理员
			if (group.getUid().longValue() == tokenuid.longValue()) {
				group.setVer(System.currentTimeMillis());
				// 删除群组
				service.removeGroup(group);

				for (User u : group.getUserlist()) {

					userService.incUserGroupVer(u.getUid()); // 更新用户群组版本

					// 此处可能需要发送mqtt通知消息
					/*
					 * GroupMqttBean mqtt = new GroupMqttBean();
					 * mqtt.setKeyid(String.valueOf(tokenuid));
					 * mqtt.setMsg("Remove group");
					 * mqtt.setSessionid(String.valueOf(group.getGroupid()));
					 * mqtt.setSessionname(group.getGroupName());
					 * mqtt.setType("6"); mqtt.setUsername(u.getName()); String
					 * j = GsonUtil.gson.toJson(mqtt); MqttService.pub(j,
					 * String.valueOf(u.getUid()));
					 */
				}
			} else {
				return getErrorRes("only owner can remove group");
			}
		} else {
			return getErrorRes("group is null");
		}
		ResponseMain main = new ResponseMain();
		Res res = new Res();
		res.setReCode(Constant.SUCCESS_CODE);
		res.setResMessage("刪除成功");
		main.setRes(res);
		resJson = JSON.toJSONString(main);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("groupid", 75);
		json.put("token", "9325495_XRTH98QO");
		logger.info(json.toJSONString());

		AbstractValidatoExecute e = new RemoveGroupExecute();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("removeGroup");
		String result = e.doProcess(context);
		logger.info(result);
	}
}
