package com.mongo.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zte.im.bean.GroupChat;
import com.zte.im.bean.User;
import com.zte.im.bean.Users;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.MyJSON;

public class TestGroupDataUpdateUser {
	
	private final static Logger logger = LoggerFactory
			.getLogger(TestGroupDataUpdateUser.class);

	GroupChat group = null;
	int groupId = 124;
	long uid = 3L;

	@Before
	public void initData() {
		refreshData();
	}

	public void refreshData() {
		logger.info("------refreshData start---------");
		BasicDBObject param = new BasicDBObject();
		param.put("groupid", groupId);
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.GROUP_DATA, param, null);
		if (obj != null) {
			logger.info("groupData:" + obj.toString());
			group = GsonUtil.gson.fromJson(obj.toString(), GroupChat.class);
			logger.info("usersData:");
			if (group.getUsers() != null && group.getUsers().size() > 0) {
				for (Long userId : group.getUsers()) {
					logger.info("id:" + userId);
				}
			}
			logger.info("userListData:");
			if (group.getUserlist() != null && group.getUserlist().size() > 0) {
				for (User user : group.getUserlist()) {
					if (user != null) {
						logger.info("id:" + user.getUid() + " name:"
								+ user.getName());
					}
				}
			}
		}
	}

	@Test
	public void testGroupDataRemoveUser() {

		BasicDBObject query1 = new BasicDBObject();
		BasicDBObject param1 = new BasicDBObject();
		query1.put("groupid", groupId);
		com.zte.im.util.Users u1 = new com.zte.im.util.Users();
		u1.setUsers(uid);
		DBObject dbObject1 = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(u1));
		param1.put("$pull", dbObject1); // 从指定文档删除一个内嵌文档
		MongoDBSupport.getInstance().updateCollection(Constant.GROUP_DATA,
				query1, param1);

		BasicDBObject param2 = new BasicDBObject();
		Users users1 = new Users();
		User u = new User();
		u.setUid(uid);
		users1.setUserlist(u);
		DBObject dbObject2 = (DBObject) MyJSON.parse(GsonUtil.gson
				.toJson(users1));
		param2.put("$pull", dbObject2); // 从指定文档删除一个内嵌文档
		param2.append("$set",
				new BasicDBObject("ver", System.currentTimeMillis()));
		param2.append("$inc", new BasicDBObject("len", -1));
		MongoDBSupport.getInstance().updateCollection(Constant.GROUP_DATA,
				query1, param2);
	}

	@Test
	public void testGroupDataAddUser() {
		BasicDBObject query1 = new BasicDBObject();
		query1.put("groupid", groupId);
		User u = UserServiceImpl.getInstance().getUserbyid(uid);
		u.setGroupids(null);
		u.setPwd(null);
		u.setToken(null);
		BasicDBObject param2 = new BasicDBObject();
		Users users1 = new Users();
		users1.setUserlist(u);
		DBObject dbObject2 = (DBObject) MyJSON.parse(GsonUtil.gson
				.toJson(users1));
		param2.put("$push", dbObject2); // 为指定文档添加一个内嵌文档
		param2.append("$set",
				new BasicDBObject("ver", System.currentTimeMillis()));
		param2.append("$inc", new BasicDBObject("len", 1));
		MongoDBSupport.getInstance().updateCollection(Constant.GROUP_DATA,
				query1, param2);
	}

	@After
	public void checkData() {
		refreshData();
	}

}
