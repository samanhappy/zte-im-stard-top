package com.zte.im.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zte.databinder.SysLogBinder;
import com.zte.im.bean.SysLog;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.mybatis.bean.page.SysLogPageModel;
import com.zte.im.service.ISysLogService;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.MyJSON;
import com.zte.im.util.Page;

public class SysLogServiceImpl implements ISysLogService {

	private static String COLLECTION = "sysLog";

	private static ISysLogService service;

	private SysLogServiceImpl() {
		super();
	}

	public static ISysLogService getInstance() {
		if (service == null) {
			service = new SysLogServiceImpl();
		}
		return service;
	}

	@Override
	public SysLogPageModel queryLog(SysLogBinder binder) {
		BasicDBObject param = new BasicDBObject("type", binder.getType());
		MongoDBSupport mongo = MongoDBSupport.getInstance();

		Long totalRecord = mongo.getCount(COLLECTION, param);
		SysLogPageModel pm = SysLogPageModel.newPageModel(binder.getpSize(),
				binder.getcPage(), totalRecord.intValue());
		binder.setOffset(pm.getOffset());

		BasicDBObject sort = new BasicDBObject();
		sort.put("_id", -1);
		DBCursor cur = mongo.queryCollectionByParam(COLLECTION,
				new Page(binder.getpSize(), binder.getcPage()), param, sort);
		if (cur != null && cur.size() > 0) {
			pm.setLogList(cur.toArray());
		}
		return pm;
	}

	@Override
	public void saveLog(SysLog log) {
		DBObject object = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(log));
		MongoDBSupport.getInstance().save(object, COLLECTION);
	}
}
