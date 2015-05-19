package com.zte.weibo.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zte.im.mongodb.MongoDBSupport;

public class SystemConfig {


	// MDM用户同步服务器访问地址
	public static String mdm_server;


	private final static String CONFIG_COLLECTION = "t_config";

	private static final Logger logger = LoggerFactory
			.getLogger(SystemConfig.class);

	static {
		loadConfig();
	}

	public static void removeConfig(String name) {
		MongoDBSupport.getInstance().updateCollection(CONFIG_COLLECTION,
				new BasicDBObject(),
				new BasicDBObject("$set", new BasicDBObject(name, "")));
		loadConfig();
	}

	public static void loadConfig() {

		DBObject config = MongoDBSupport.getInstance().queryOneByParam(
				CONFIG_COLLECTION, null, null);
		if (config != null) {

			if (config.containsField("mdm_server")){
				mdm_server = config.get("mdm_server").toString();
			}



		}
		logger.info("loading sysconfig...");
		logger.info("mdm_server:{}", mdm_server);

	}

}
