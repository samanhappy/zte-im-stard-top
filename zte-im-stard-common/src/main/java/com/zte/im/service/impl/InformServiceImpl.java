package com.zte.im.service.impl;

import java.util.UUID;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zte.im.bean.Inform;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.InformService;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.MyJSON;

public class InformServiceImpl implements InformService {

	private static InformServiceImpl service;

	private InformServiceImpl() {
		super();
	}

	public static InformServiceImpl getInstance() {
		if (service == null) {
			service = new InformServiceImpl();
		}
		return service;
	}

	/**
	 * 新建通知
	 */
	public Inform createInform(Inform inform) {
		inform.setInformid(UUID.randomUUID().toString());
		DBObject dbObject = (DBObject) MyJSON.parse(GsonUtil.gson
				.toJson(inform));
		// 新增
		MongoDBSupport.getInstance().save(dbObject, Constant.INFORM);
		return inform;
	}

	/**
	 * 通过id查询实体
	 * 
	 * @param informid
	 * @return
	 */
	public DBObject queryInformListById(String informid) {
		BasicDBObject param = new BasicDBObject();
		param.put("informid", informid);
		DBObject dbObj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.INFORM, param, null);
		return dbObj;
	}
}
