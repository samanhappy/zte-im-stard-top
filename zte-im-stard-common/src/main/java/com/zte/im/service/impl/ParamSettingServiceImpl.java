package com.zte.im.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zte.im.bean.Param;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.IParamSettingService;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.MyJSON;

public class ParamSettingServiceImpl implements IParamSettingService {

	private static String COLLECTION = "paramSetting";

	private static IParamSettingService service;

	private ParamSettingServiceImpl() {
		super();
	}

	public static IParamSettingService getInstance() {
		if (service == null) {
			service = new ParamSettingServiceImpl();
		}
		return service;
	}

	@Override
	public void saveOrUpdate(Param param) {
		MongoDBSupport mongo = MongoDBSupport.getInstance();
		BasicDBObject query = new BasicDBObject("id", param.getId());
		DBObject dbObject = (DBObject) MyJSON
				.parse(GsonUtil.gson.toJson(param));
		mongo.upsertCollection(COLLECTION, query, dbObject);
	}

	@Override
	public Param getParamByType(String type) {
		Param param = null;
		MongoDBSupport mongo = MongoDBSupport.getInstance();
		BasicDBObject query = new BasicDBObject("id", type);
		DBObject dbo = mongo.queryOneByParam(COLLECTION, query, null);
		if (dbo != null) {
			param = JSON.parseObject(dbo.toString(), Param.class);
		}
		return param;
	}

	@Override
	public List<Param> loadParam(boolean userDefindOnly) {
		List<Param> list = null;
		MongoDBSupport mongo = MongoDBSupport.getInstance();
		BasicDBObject query = new BasicDBObject();
		if (userDefindOnly) {
			List<String> l = new ArrayList<String>();
			l.add("system");
			l.add("contact");
			query.put("id", new BasicDBObject("$nin", l));
		}
		DBCursor cur = mongo.queryCollection(COLLECTION, query);
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<Param>>() {
			}.getType();
			list = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		return list;
	}

	@Override
	public void delete(String id) {
		MongoDBSupport mongo = MongoDBSupport.getInstance();
		BasicDBObject query = new BasicDBObject("id", id);
		mongo.removeDBObject(COLLECTION, query);
	}
}
