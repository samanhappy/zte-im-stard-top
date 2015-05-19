package com.mongo.test;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zte.im.bean.GroupChat;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.MyJSON;

public class TestMongo4User {
	
	private final static Logger logger = LoggerFactory
			.getLogger(TestMongo4User.class);

	MongoDBSupport support = MongoDBSupport.getInstance();

	@Test
	public void createTest() {
		long uid = 2;
		String icon_path = "123456.png";
		long time = new Date().getTime();
		updateIcon4UserDate(uid, icon_path, time);
	}

	@Test
	public void createTest2() {
		long uid = 2L;
		BasicDBObject query = new BasicDBObject();
		query.put("uid", uid);
		DBCursor obj = support.queryCollection("user_data", query);
		logger.info(obj.toArray().toString());
	}

	private void updateIcon4UserDate(long uid, String icon_path, long time) {
		logger.info("" + time);
		BasicDBObject query = new BasicDBObject();
		query.put("uid", uid);
		BasicDBObject param = new BasicDBObject();
		param.put("$set", new BasicDBObject("icon", icon_path));
		support.updateCollection("user_data", query, param);
		DBObject object = support.queryOneByParam("user_data", query, null);
		BasicDBList list = (BasicDBList) object.get("groupids");
		logger.info("======" + list.size());
	}

	public void updateIcon4GroupDate(long uid, String icon_path, long time) {
		BasicDBObject query = new BasicDBObject();
		query.put("userlist.uid", uid);
		BasicDBObject param = new BasicDBObject();
		param.put("$set", new BasicDBObject("ver", time).append(
				"userlist.$.icon", icon_path));
		support.updateCollection("group_data", query, param);
	}

	public void updateIcon4ComPosit(long uid, String icon_path, long time) {
		BasicDBObject query = new BasicDBObject();
		query.put("users.uid", uid);
		BasicDBObject param = new BasicDBObject();
		param.put("$set", new BasicDBObject("users.$.icon", icon_path)); // 修改指定内嵌文档的数据
		support.updateCollection("com_posit", query, param);
	}

	@Test
	public void testLong() {
		GroupChat group = new GroupChat();
		group.setGroupid(100000L);
		group.setMaxLen(100);
		DBObject dbObject = (DBObject) MyJSON
				.parse(GsonUtil.gson.toJson(group));
		MongoDBSupport.getInstance().save(dbObject, Constant.GROUP_DATA);

		BasicDBObject param = new BasicDBObject("groupid", 100000L);
		DBObject db = MongoDBSupport.getInstance().queryOneByParam(
				Constant.GROUP_DATA, param, null);
		if (db != null) {
			GroupChat gc = GsonUtil.gson.fromJson(db.toString(),
					GroupChat.class);
			logger.info(gc.getGroupid().toString());
			logger.info(gc.getMaxLen().toString());
		}
	}
}
