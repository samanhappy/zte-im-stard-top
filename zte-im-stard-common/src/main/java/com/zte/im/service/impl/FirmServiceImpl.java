package com.zte.im.service.impl;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zte.im.bean.Firm;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.IFirmService;
import com.zte.im.util.Constant;

public class FirmServiceImpl implements IFirmService {

	private static FirmServiceImpl service;

	private FirmServiceImpl() {
		super();
	}

	public static IFirmService getInstance() {
		if (service == null) {
			service = new FirmServiceImpl();
		}
		return service;
	}

	/**
	 * 根据企业id查询企业
	 * 
	 * @param gid
	 * @return
	 */
	public Firm getFirm(Long gid) {
		Firm f = null;
		BasicDBObject param = new BasicDBObject();// 要获取的数据
		BasicDBObject param1 = new BasicDBObject();// 条件
		param1.put("gid", gid);
		param.put("gid", 1);
		param.put("name", 1);
		param.put("ver", 1);
		param.put("cid", 1);
		DBObject obj = MongoDBSupport.getInstance().queryCollectionCondition(
				Constant.Firm, param, param1);
		if (obj != null) {
			f = JSON.parseObject(obj.toString(), Firm.class);
		}
		return f;
	}

	/**
	 * 根据企业id查询企业
	 * 
	 * @param gid
	 * @return
	 */
	public Firm getFirm(String id) {
		Firm f = null;
		BasicDBObject param = new BasicDBObject();// 要获取的数据
		BasicDBObject query = new BasicDBObject();// 条件
		query.put("id", id);
		param.put("gid", 1);
		param.put("name", 1);
		param.put("ver", 1);
		param.put("cid", 1);
		DBObject obj = MongoDBSupport.getInstance().queryCollectionCondition(
				Constant.Firm, param, query);
		if (obj != null) {
			f = JSON.parseObject(obj.toString(), Firm.class);
		}
		return f;
	}

}
