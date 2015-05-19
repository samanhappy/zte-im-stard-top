package com.zte.im.user.execute;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

/**
 * 邀请好友加入群
 * 
 * @author Administrator
 * 
 */
public class InviteGroupExecute extends AbstractValidatoExecute {
	
	private final static Logger logger = LoggerFactory
			.getLogger(InviteGroupExecute.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		IGroupService service = GroupServiceImpl.getInstance();
		IUserService userService = UserServiceImpl.getInstance();
		JSONArray arr = json.getJSONArray("users");
		Long groupid = json.getLong("groupid");
		String name = super.getContext().getUser().getName();
		Long tokenuid = super.getContext().getUser().getUid();
		GroupChat group = service.findGroupChatById(groupid);
		if (group != null) {
			group.setVer(System.currentTimeMillis());
			List<Long> uids = new ArrayList<Long>();
			StringBuffer operatoruids = new StringBuffer();
			for (Object obj : arr) {
				if (obj != null) {
					operatoruids.append(obj.toString()).append(",");
					Long uid = Long.parseLong(obj.toString());
					uids.add(uid);
				}
			}

			if (uids.size() > 0) {
				List<PushBean> list = new ArrayList<PushBean>();
				List<String> unames = new ArrayList<String>();
				List<User> users = service.addGroup(uids, group, unames);
				
				
				StringBuffer pushMsg = new StringBuffer();
				pushMsg.append(name);
				pushMsg.append("邀请了");
				for (int i = 0; i < unames.size(); i++) {
					pushMsg.append(unames.get(i));
					if (i < unames.size() - 1) {
						pushMsg.append("、");
					}
				}
				pushMsg.append("加入群组（");
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
					bean.setType("3");
					bean.setUsername(name);
					bean.setOperatorid(operatoruids.substring(0,
							operatoruids.length() - 1));
					bean.setModeltype("0");
					list.add(bean);
				}
				new MqttMessager(list).start();
			} else {
				return getErrorRes("no user to remove");
			}
		} else {
			getErrorRes("group is null");
		}
		ResponseMain main = new ResponseMain();
		Res res = new Res();
		res.setReCode(Constant.SUCCESS_CODE);
		res.setResMessage("添加成功");
		main.setRes(res);
		resJson = JSON.toJSONString(main);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("groupid", 88l);
		json.put("token", "7956344_VL6GESL2");
		JSONArray users = new JSONArray();
		users.add(7l);
		users.add(8l);
		json.put("users", users);
		logger.info(json.toJSONString());

		AbstractValidatoExecute e = new InviteGroupExecute();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("inviteGroup");
		String result = e.doProcess(context);
		logger.info(result);
	}
}
