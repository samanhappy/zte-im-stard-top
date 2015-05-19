package com.mongo.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zte.im.bean.GroupChat;
import com.zte.im.bean.Groupids;
import com.zte.im.bean.User;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.MyJSON;

public class TestUserDataRemoveGroup {
	
	private final static Logger logger = LoggerFactory
			.getLogger(TestUserDataRemoveGroup.class);

	User user = null;

	@Before
	public void initData() {
		refreshData();
	}

	public void refreshData() {
		logger.info("------refreshData start---------");
		BasicDBObject param = new BasicDBObject();
		param.put("uid", 6);
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.USER_DATA, param, null);
		if (obj != null) {
			logger.info("userData:" + obj.toString());
			user = GsonUtil.gson.fromJson(obj.toString(), User.class);
			logger.info("groupData:");
			if (user.getGroupids() != null && user.getGroupids().size() > 0) {
				for (GroupChat group : user.getGroupids()) {
					logger.info("id:" + group.getGroupid() + " name:"
							+ group.getGroupName() + " ver:" + group.getVer());
				}
			}
		}
	}

	@Test
	public void testUserDataRemoveGroup() {
		BasicDBObject query = new BasicDBObject();
		query.put("uid", 6);
		BasicDBObject param = new BasicDBObject();
		GroupChat g = new GroupChat();
		g.setGroupid(120l);
		Groupids ids = new Groupids();
		ids.setGroupids(g);
		DBObject dbObject = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(ids));
		param.put("$pull", dbObject); // 从指定文档删除一个内嵌文档
		param.append("$set",
				new BasicDBObject("ver", System.currentTimeMillis()));
		MongoDBSupport.getInstance().updateCollection(Constant.USER_DATA,
				query, param);
	}

	@After
	public void checkData() {
		refreshData();
	}

}
