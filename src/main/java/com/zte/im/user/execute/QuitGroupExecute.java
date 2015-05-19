package com.zte.im.user.execute;

import java.util.ArrayList;
import java.util.List;

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
import com.zte.im.twitter.job.MqttMessager;
import com.zte.im.util.Constant;

public class QuitGroupExecute extends AbstractValidatoExecute {
	
	private final static Logger logger = LoggerFactory
			.getLogger(QuitGroupExecute.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		IGroupService service = GroupServiceImpl.getInstance();
		IUserService userService = UserServiceImpl.getInstance();
		Long groupid = json.getLong("groupid");
		Long newuid = json.getLong("newuid");
		String newname = json.getString("newname");
		Long tokenuid = super.getContext().getUser().getUid();
		GroupChat group = service.findGroupChatById(groupid);
		if (group == null) {
			return getErrorRes("group is null");
		}

		// 群主退群必须指定新的群主
		if (group.getUid().longValue() == tokenuid) {
			if (newuid == null || newname == null || "".equals(newname)) {
				return getErrorRes("newuid and newname can not be null");
			}
			if (newuid.longValue() == tokenuid) {
				return getErrorRes("newuid must be other user");
			}
			if (!group.getUsers().contains(newuid.longValue())) {
				return getErrorRes("newuid is not int this group");
			}
			group.setUid(newuid);
			group.setName(newname);
			service.updateGroup(group);
		}

		// 退群并发送消息
		List<User> users = service.quitGroup(tokenuid, group, null);
		List<PushBean> list = new ArrayList<PushBean>();
		
		StringBuffer pushMsg = new StringBuffer();
		pushMsg.append(super.getContext().getUser().getName());
		pushMsg.append("退出了群组（");
		pushMsg.append(group.getGroupName());
		pushMsg.append("）");
		
		for (User u : users) {

			userService.incUserGroupVer(u.getUid()); // 更新用户群组版本

			PushBean bean = new PushBean();
			bean.setTarget(String.valueOf(u.getUid()));
			bean.setKeyid(String.valueOf(tokenuid));
			bean.setMsg(pushMsg.toString());
			bean.setSessionid(String.valueOf(group.getGroupid()));
			bean.setSessionname(group.getGroupName());
			bean.setType("4");
			if (newuid != null) {
				bean.setAdminid(String.valueOf(newuid));
				bean.setAdminname(newname);
			}
			bean.setUsername(u.getName());
			bean.setModeltype("0");
			list.add(bean);
		}
		new MqttMessager(list).start();

		ResponseMain main = new ResponseMain();
		Res res = new Res();
		res.setReCode(Constant.SUCCESS_CODE);
		res.setResMessage("退群成功");
		main.setRes(res);
		resJson = JSON.toJSONString(main);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("groupid", 41);
		json.put("newuid", 17);
		json.put("token", "2518844_453EWFFH");
		logger.info(json.toJSONString());

		AbstractValidatoExecute e = new QuitGroupExecute();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("quitGroup");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
