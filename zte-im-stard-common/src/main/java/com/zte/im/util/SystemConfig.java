package com.zte.im.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zte.im.mongodb.MongoDBSupport;

public class SystemConfig {

	// fastdfs服务器HTTP访问地址前缀
	public static String fdfs_http_host;

	// fastdfs tracker_server
	public static String tracker_servers;

	public static String redis_servers;

	// SSO服务器访问地址
	public static String sso_server;

	// MDM用户同步服务器访问地址
	public static String mdm_server;

	// mqtt服务器地址
	public static String mqtt_server;
	// mqtt服务器broker地址
	public static String mqtt_broker;
	// mqtt命令调用参数
	public static String mqtt_params;

	public static String voip_id_conf;

	// 许可证开始日期
	public static String licenseStartDate;

	private final static String CONFIG_COLLECTION = "config";

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
			if (config.containsField("fdfs_http_host"))
				fdfs_http_host = config.get("fdfs_http_host").toString();

			if (config.containsField("tracker_servers"))
				tracker_servers = config.get("tracker_servers").toString();

			if (config.containsField("redis_servers"))
				redis_servers = config.get("redis_servers").toString();

			if (config.containsField("sso_server"))
				sso_server = config.get("sso_server").toString();

			if (config.containsField("mdm_server"))
				mdm_server = config.get("mdm_server").toString();

			if (config.containsField("mqtt_server"))
				mqtt_server = config.get("mqtt_server").toString();

			if (config.containsField("mqtt_params"))
				mqtt_params = config.get("mqtt_params").toString();

			if (config.containsField("voip_id_conf"))
				voip_id_conf = config.get("voip_id_conf").toString();

			if (config.containsField("licenseStartDate"))
				licenseStartDate = config.get("licenseStartDate").toString();
			
			if (config.containsField("mqtt_broker"))
				mqtt_broker = config.get("mqtt_broker").toString();


		}
		logger.info("loading sysconfig...");
		logger.info("fdfs_http_host:{}", fdfs_http_host);
		logger.info("tracker_servers:{}", tracker_servers);
		logger.info("redis_servers:{}", redis_servers);
		logger.info("sso_server:{}", sso_server);
		logger.info("mdm_server:{}", mdm_server);
		logger.info("mqtt_server:{}", mqtt_server);
		logger.info("mqtt_params:{}", mqtt_params);
		logger.info("voip_id_conf:{}", voip_id_conf);
		logger.info("licenseStartDate:{}", licenseStartDate);
		logger.info("mqtt_broker:{}", mqtt_broker);

	}

}
