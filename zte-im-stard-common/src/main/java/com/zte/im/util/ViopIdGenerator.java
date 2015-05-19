package com.zte.im.util;

import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zte.im.mongodb.MongoDBSupport;

/**
 * 分机号码生成
 * 
 * @author samanhappy
 * 
 */
public class ViopIdGenerator {

	private static MongoDBSupport mongo = MongoDBSupport.getInstance();

	private static String COLLECTION = Constant.VOIP_ID;

	private static org.slf4j.Logger logger = LoggerFactory
			.getLogger(ViopIdGenerator.class);

	static {
		if (SystemConfig.voip_id_conf != null) {
			String[] strs = SystemConfig.voip_id_conf.split(",");
			if (strs.length == 2) {
				for (long i = Long.valueOf(strs[0]); i < Long
						.valueOf(strs[1]); i++) {
					put(i);
				}
				SystemConfig.removeConfig("voip_id_conf");
				logger.info("init extNumber from config:{}",
						SystemConfig.voip_id_conf);
			}
		}
	}

	public static String pop() {
		DBObject obj = mongo.queryOneByParam(COLLECTION, null,
				new BasicDBObject("value", 1));
		if (obj != null) {
			if (obj.containsField("value")) {
				long value = Long.valueOf(obj.get("value").toString());
				mongo.removeDBObject(COLLECTION, new BasicDBObject("value",
						value));
				return String.valueOf(value);
			}
		}
		return null;
	}

	public static void put(long value) {
		mongo.save(new BasicDBObject("value", value), COLLECTION);
	}

}
