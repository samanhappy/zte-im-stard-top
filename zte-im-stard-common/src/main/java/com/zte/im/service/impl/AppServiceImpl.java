package com.zte.im.service.impl;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.IAppService;
import com.zte.im.util.Constant;
import com.zte.im.util.Page;

public class AppServiceImpl implements IAppService {

	private static AppServiceImpl service;

	private AppServiceImpl() {
		super();
	}

	public static AppServiceImpl getInstance() {
		if (service == null) {
			service = new AppServiceImpl();
		}
		return service;
	}

	@Override
	public List<DBObject> getAppByCateId(BasicDBObject param, Page page,
			BasicDBObject sort) {
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.APP, page, param, sort);
		if (cur != null && cur.size() > 0) {
			return cur.toArray();
		}
		return null;
	}

	@Override
	public List<DBObject> getAppCate() {
		BasicDBObject param = new BasicDBObject();
		DBCursor cur = MongoDBSupport.getInstance().queryCollection(
				Constant.APP_CATE, param);
		if (cur != null && cur.size() > 0) {
			return cur.toArray();
		}
		return null;
	}
}
