package com.zte.im.service.impl;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zte.im.bean.Customization;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.ICustomizationService;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.MyJSON;

public class CustomizationServiceImpl implements ICustomizationService {

	private static String COLLECTION = "customization";

	private static ICustomizationService service;

	private CustomizationServiceImpl() {
		super();
	}

	public static ICustomizationService getInstance() {
		if (service == null) {
			service = new CustomizationServiceImpl();
		}
		return service;
	}

	public void saveOrUpdate(Customization obj) {
		MongoDBSupport mongo = MongoDBSupport.getInstance();
		BasicDBObject query = new BasicDBObject("id", obj.getId());
		DBObject dbObject = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(obj));
		mongo.upsertCollection(COLLECTION, query, dbObject);
	}

	@Override
	public Customization getCustomization(String id) {
		Customization param = null;
		MongoDBSupport mongo = MongoDBSupport.getInstance();
		BasicDBObject query = new BasicDBObject("id", id);
		DBObject dbo = mongo.queryOneByParam(COLLECTION, query, null);
		if (dbo != null) {
			param = JSON.parseObject(dbo.toString(), Customization.class);
		}
		return param;
	}
}
