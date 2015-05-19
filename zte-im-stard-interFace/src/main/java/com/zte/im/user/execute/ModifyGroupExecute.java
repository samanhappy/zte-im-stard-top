package com.zte.im.user.execute;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.paho.sample.mqttv3app.MqttPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.GroupChat;
import com.zte.im.bean.PushBean;
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
 * 修改群信息，目前只限于修改群名，修改成功会发送mqtt消息通知所有成员
 * 
 * @author Administrator
 * 
 */
public class ModifyGroupExecute extends AbstractValidatoExecute {
	
	private final static Logger logger = LoggerFactory
			.getLogger(ModifyGroupExecute.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		IGroupService service = GroupServiceImpl.getInstance();
		IUserService userService = UserServiceImpl.getInstance();
		Long groupid = json.getLong("groupid");
		String groupName = json.getString("groupName");
		if (groupName == null || "".equals(groupName)) {
			return getErrorRes("groupName can not be empty");
		}

		Long tokenuid = super.getContext().getUser().getUid();
		GroupChat group = service.findGroupChatById(groupid);
		if (group != null) {
			// 判断当前用户是否是群的管理员
			if (group.getUid().longValue() == tokenuid.longValue()) {
				group.setVer(System.currentTimeMillis());
				group.setGroupName(groupName);
				service.updateGroup(group);
				
				StringBuffer pushMsg = new StringBuffer();
				pushMsg.append(super.getContext().getUser().getName());
				pushMsg.append("将群名称修改为");
				pushMsg.append(groupName);
				
				List<PushBean> list = new ArrayList<PushBean>();
				for (User u : group.getUserlist()) {

					userService.incUserGroupVer(u.getUid()); // 更新用户群组版本

					// 发送mqtt通知消息
					PushBean bean = new PushBean();
					bean.setTarget(String.valueOf(u.getUid()));
					bean.setKeyid(String.valueOf(tokenuid));
					bean.setMsg(pushMsg.toString());
					bean.setSessionid(String.valueOf(group.getGroupid()));
					bean.setSessionname(group.getGroupName());
					bean.setType("2");
					bean.setUsername(u.getName());
					bean.setModeltype("0");
					list.add(bean);
				}
				MqttPublisher.publish(list);
			} else {
				return getErrorRes("only owner can modify group");
			}
		} else {
			return getErrorRes("group is null");
		}
		ResponseMain main = new ResponseMain();
		Res res = new Res();
		res.setReCode(Constant.SUCCESS_CODE);
		res.setResMessage("修改成功");
		main.setRes(res);
		resJson = JSON.toJSONString(main);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("groupid", 74);
		json.put("token", "9325495_XRTH98QO");
		json.put("groupName", "测试群组");
		logger.info(json.toJSONString());

		AbstractValidatoExecute e = new ModifyGroupExecute();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("modifyGroup");
		String result = e.doProcess(context);
		logger.info(result);
	}
}
