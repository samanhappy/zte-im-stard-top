package com.zte.im.user.execute;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.DataVer;
import com.zte.im.bean.GroupChat;
import com.zte.im.bean.User;
import com.zte.im.protocol.ResponseListMain;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.IGroupService;
import com.zte.im.service.IUserService;
import com.zte.im.service.IVersionService;
import com.zte.im.service.impl.GroupServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.service.impl.VersionServiceImpl;

/**
 * 获取群组信息，传groupids时获取指定群众信息，不传时获取用户所有群组信息，groupVer已经最新时不返回数据
 * 
 * @author Administrator
 * 
 */
public class FindGroupChatExecute extends AbstractValidatoExecute {

	private final static Logger logger = LoggerFactory.getLogger(FindGroupChatExecute.class);

	@Override
	public String doProcess(JSONObject json) {
		IGroupService serive = GroupServiceImpl.getInstance();
		String resJson = null;
		User user = null;
		List<GroupChat> gcArr = new ArrayList<GroupChat>();
		if (json.containsKey("groupids")) {
			JSONArray arr = json.getJSONArray("groupids");
			for (Object obj : arr) {
				GroupChat group = getGroupChatById(serive, Long.parseLong(obj.toString()));
				if (group != null) {
					gcArr.add(group);
				}
			}
		} else {
			IUserService service = UserServiceImpl.getInstance();
			user = service.getUserbyid(super.getContext().getUser().getUid());
			if (user != null && user.getGroupids() != null && user.getGroupids().size() > 0) {
				for (GroupChat gc : user.getGroupids()) {
					GroupChat group = getGroupChatById(serive, gc.getGroupid());
					if (group != null) {
						gcArr.add(group);
					}
				}
			}
		}
		ResponseListMain listmain = new ResponseListMain();
		listmain.setItem(gcArr);
		IVersionService vService = VersionServiceImpl.getInstance();
		DataVer dataVer = vService.getDataVer();
		if (user != null && user.getGroupVer() != null) {
			dataVer.setGroupVer(user.getGroupVer());
		} else {
			dataVer.setGroupVer(0L);
		}
		listmain.setDataVer(dataVer);
		resJson = JSON.toJSONString(listmain);
		return resJson;
	}

	private GroupChat getGroupChatById(IGroupService serive, Long groupid) {
		GroupChat gc = serive.findGroupChatById(groupid);
		if (gc != null) {
			gc.setUsers(null);
			if (gc.getUserlist() != null) {
				for (User u : gc.getUserlist()) {
					u.setGroupids(null);
				}
			}
		}
		return gc;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		JSONArray arr = new JSONArray();
		arr.add("30");
		json.put("token", "9683510_UNB1HQ46");
		logger.info(json.toJSONString());

		AbstractValidatoExecute e = new FindGroupChatExecute();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("findGroup");
		String result = e.doProcess(context);
		logger.info(result);

	}

}
