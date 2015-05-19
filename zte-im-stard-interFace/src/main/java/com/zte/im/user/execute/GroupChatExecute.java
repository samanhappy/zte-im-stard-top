package com.zte.im.user.execute;

import java.util.ArrayList;
import java.util.Date;
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
import com.zte.im.util.IDSequence;

/**
 * 创建群组，成功返回新生成的群组ID ，创建成功会更新所有成员群组版本groupVer并发送mqtt消息通知所有成员
 * 
 * @author Administrator
 * 
 */
public class GroupChatExecute extends AbstractValidatoExecute {

	private final static Logger logger = LoggerFactory
			.getLogger(GroupChatExecute.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		List<Long> users = null;// 群中的用户
		Long uid = super.getContext().getUser().getUid();// 创建人id
		String name = super.getContext().getUser().getName();// 创建人姓名
		GroupChat group = JSON
				.parseObject(json.toJSONString(), GroupChat.class);
		String groupName = "";
		if (group == null) {
			return getErrorRes("create group err.", Constant.ERROR_CODE);
		}
		if (group.getGroupName() != null
				&& !"".equalsIgnoreCase(group.getGroupName())) {
			groupName = group.getGroupName();
		} else {
			return getErrorRes("create group err.", Constant.GROUP_NAME_SPEC);
		}
		if (group.getUsers() != null && group.getUsers().size() > 1) {
			users = group.getUsers();
		} else {
			return getErrorRes("create group err.", Constant.GROUP_NAME_NUM);
		}
		Long groupID = IDSequence.getGroupId();
		if (groupID == null) {
			return getErrorRes("create group err.");
		}

		group.setGroupid(groupID);
		group.setUid(uid);
		group.setName(name);
		Long ver = System.currentTimeMillis();
		IUserService userService = UserServiceImpl.getInstance();
		IGroupService groupService = GroupServiceImpl.getInstance();
		List<User> userList = new ArrayList<User>();
		// 有效的用户
		List<Long> validUsers = new ArrayList<Long>();
		int usernum = 0;
		for (Long u : users) {
			if (u != null) {
				User user = userService.getUserbyid(u);
				if (user != null) {
					user.setPwd(null);
					user.setToken(null);
					user.setGroupids(null);
					GroupChat g = new GroupChat();
					g.setGroupid(groupID);
					g.setGroupName(groupName);
					g.setVer(ver);
					User success = groupService.addGroup(user.getUid(), g);// 将群添加到用户中
					if (success != null) {
						userList.add(user);
						userService.incUserGroupVer(u); // 更新用户群组版本
						usernum++;
						validUsers.add(u);
					}
				}
			}
		}
		if (usernum > 0) {
			group.setUserlist(userList);
			group.setToken(null);
			group.setVer(ver);
			group.setCreateTime(new Date());
			group.setUsers(validUsers);// 更新有效用户
			group.setLen(usernum);
			groupService.saveGroup(group);

			StringBuffer pushMsg = new StringBuffer();
			pushMsg.append(name);
			if (group.getIsTemp() != null && group.getIsTemp() == 1) {
				pushMsg.append("发起了群聊");
			} else {
				pushMsg.append("创建了群组");
			}

			List<PushBean> list = new ArrayList<PushBean>();
			for (Long u : users) {// 入库完成后发送消息
				if (u != null) {
					PushBean bean = new PushBean();
					bean.setTarget(String.valueOf(u));
					bean.setKeyid(String.valueOf(uid));
					bean.setMsg(pushMsg.toString());
					bean.setSessionid(String.valueOf(group.getGroupid()));
					bean.setSessionname(group.getGroupName());
					bean.setType("7");
					bean.setUsername(name);
					bean.setModeltype("0");
					list.add(bean);
				}
			}
			MqttPublisher.publish(list);
		} else {
			return getErrorRes("create group err.", Constant.GROUP_NAME_NUM);
		}
		ResponseMain main = new ResponseMain();
		Res res = new Res();
		res.setReCode(Constant.SUCCESS_CODE);
		res.setResMessage("创建群组成功");
		main.setRes(res);
		GroupChat g = new GroupChat();
		g.setGroupid(groupID);
		g.setGroupName(groupName);
		g.setVer(ver);
		g.setCreateTime(group.getCreateTime());
		g.setUserCount(users.size());
		g.setUid(uid);
		g.setIsTemp(group.getIsTemp());
		// g.setUserlist(userList);
		main.setGroupChat(g);
		resJson = JSON.toJSONString(main);
		return resJson;
	}

	public static void main(String[] args) {
		// MongoDBSupport.getInstance().dropOneCollection(Constant.GROUP_DATA);
		GroupChat g = new GroupChat();
		g.setToken("9325495_XRTH98QO");
		List<Long> users = new ArrayList<Long>();
		// for(DBObject obj:list){
		// User u = JSON.parseObject(obj.toString(), User.class);
		// u.setToken(null);
		// u.setPwd(null);
		// // u.setCid(null);
		// // u.setEmail(null);
		// // u.setGname(null);
		// // u.setJid(null);
		// // u.setMob(null);
		// // u.setName(null);
		// // u.setPost(null);
		// // u.setNickname(null);
		// // u.setGid(null);
		// users.add(u.getUid());
		// }
		users.add(14l);
		users.add(2l);
		g.setUsers(users);
		g.setGroupName("服务器测试用的群");
		g.setIsTemp(1);
		String a = JSON.toJSONString(g);
		logger.info(a);
		AbstractValidatoExecute e = new GroupChatExecute();
		Context context = new Context();
		context.setJsonRequest(a);
		context.setMethod("createGroup");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
