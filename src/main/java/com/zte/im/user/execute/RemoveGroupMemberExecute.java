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
public class RemoveGroupMemberExecute extends AbstractValidatoExecute {

	private final static Logger logger = LoggerFactory
			.getLogger(RemoveGroupMemberExecute.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		IGroupService service = GroupServiceImpl.getInstance();
		IUserService userService = UserServiceImpl.getInstance();
		JSONArray arr = json.getJSONArray("users");
		Long groupid = json.getLong("groupid");
		Long tokenuid = super.getContext().getUser().getUid();
		GroupChat group = service.findGroupChatById(groupid);
		if (group != null) {
			// 判断当前用户是否是群的管理员
			if (group.getUid().longValue() == tokenuid.longValue()) {
				group.setVer(System.currentTimeMillis());
				List<Long> uids = new ArrayList<Long>();
				StringBuffer operatoruids = new StringBuffer();
				for (Object obj : arr) {
					if (obj != null) {
						operatoruids.append(obj.toString()).append(",");
						Long uid = Long.parseLong(obj.toString());
						if (uid == tokenuid.longValue()) {
							return getErrorRes("can not remove oneself");
						}
						uids.add(uid);
					}
				}

				if (uids.size() > 0) {
					// 删除群组成员并发送消息
					List<User> removeUsers = new ArrayList<User>();
					List<User> users = service.quitGroup(uids, group,
							removeUsers);
					List<PushBean> list = new ArrayList<PushBean>();

					StringBuffer pushMsg = new StringBuffer();
					for (int i = 0; i < removeUsers.size(); i++) {
						pushMsg.append(removeUsers.get(i).getName());
						if (i < removeUsers.size() - 1) {
							pushMsg.append("、");
						}
					}
					pushMsg.append("被移出群组（");
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
						bean.setType("5");
						bean.setUsername(u.getName());
						bean.setOperatorid(operatoruids.substring(0,
								operatoruids.length() - 1));
						bean.setModeltype("0");
						list.add(bean);
					}

					// 通知删除的用户
					for (User u : removeUsers) {
						userService.incUserGroupVer(u.getUid()); // 更新用户群组版本
						PushBean bean = new PushBean();
						bean.setTarget(String.valueOf(u.getUid()));
						bean.setKeyid(String.valueOf(tokenuid));
						bean.setMsg("您被移出群组（" + group.getGroupName() + "）");
						bean.setSessionid(String.valueOf(group.getGroupid()));
						bean.setSessionname(group.getGroupName());
						bean.setType("5");
						bean.setUsername(u.getName());
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
				return getErrorRes("only owner can remove members");
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
		JSONArray users = new JSONArray();
		users.add(14);
		// users.add(8l);
		json.put("users", users);
		logger.info(json.toJSONString());

		AbstractValidatoExecute e = new RemoveGroupMemberExecute();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("removeGroupMember");
		String result = e.doProcess(context);
		logger.info(result);
	}
}
