package com.zte.im.mongodb;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.zte.im.util.Page;

@SuppressWarnings("deprecation")
public abstract class AbstractMongoDB {

	private static Logger logger = LoggerFactory.getLogger(AbstractMongoDB.class);

	public static Mongo mongo = null;
	public static DB db = null;

	public static int listNum = 80;// 冲浪栏目列表新闻条数
	public static int deskNewsNum = 200;// 快讯栏目列表新闻条数

	static {
		ResourceBundle bundle = ResourceBundle.getBundle("mongodb");
		listNum = Integer.parseInt(bundle.getString("mongodb.listNum"));
		listNum = Integer.parseInt(bundle.getString("mongodb.deskNewsNum"));
		String[] ips = bundle.getString("mongodb.ip").split(",");
		String db_name = bundle.getString("mongodb.db");
		String user = bundle.getString("mongodb.user");
		String password = bundle.getString("mongodb.password");
		int maxActive = Integer.parseInt(bundle.getString("mongodb.maxActive"));
		int maxIdle = Integer.parseInt(bundle.getString("mongodb.maxIdle"));
		int maxWait = Integer.parseInt(bundle.getString("mongodb.maxWait"));
		int readSecond = Integer.parseInt(bundle.getString("mongodb.readSecond"));
		try {
			Builder b = new MongoClientOptions.Builder();
			// 使用mbean
			b.alwaysUseMBeans(true);
			// 控制心跳
			b.heartbeatConnectRetryFrequency(Integer.MAX_VALUE);// 设置心跳的重试次数
			b.heartbeatConnectTimeout(5000);// 设置心跳的链接超时时间
			b.heartbeatFrequency(5000);// 心跳频率
			b.heartbeatThreadCount(1);// 心跳线程数
			// 控制系统在发生连接错误时是否重试 ，默认为false --boolean
			b.autoConnectRetry(true);
			// 每个主机允许的连接数（每个主机的连接池大小），当连接池被用光时，会被阻塞住 ，默认为10 --int
			b.connectionsPerHost(maxActive);
			// 设置等待获取连接池连接的最大数，比如，connectionsPerHost
			// 是10，threadsAllowedToBlockForConnectionMultiplier
			// 是5，则最多有50个线程可以等待获取连接 --int
			b.threadsAllowedToBlockForConnectionMultiplier(maxIdle);
			// 被阻塞线程从连接池获取连接的最长等待时间（ms） --int
			b.threadsAllowedToBlockForConnectionMultiplier(maxWait);
			b.socketKeepAlive(true);
			MongoClientOptions op = b.build();
			List<ServerAddress> addr = new ArrayList<ServerAddress>();
			for (String ipAndPort : ips) {
				String ip = ipAndPort.split(":")[0];
				String port = ipAndPort.split(":")[1];
				addr.add(new ServerAddress(ip, Integer.parseInt(port)));
			}
			MongoCredential credential = MongoCredential.createMongoCRCredential(user, db_name, password.toCharArray());
			MongoClient client = new MongoClient(addr, Arrays.asList(credential), op);
			db = client.getDB(db_name);
			if (readSecond == 1)
				db.setReadPreference(ReadPreference.secondaryPreferred());
		} catch (UnknownHostException e1) {
			logger.error("", e1);
		}

	}

	public static void destory() {
		if (mongo != null)
			mongo = null;
		db = null;
	}

	/**
	 * 添加Index
	 * 
	 * @param collection
	 * @param dbObject
	 */
	public abstract void addIndex(DBCollection collection, DBObject dbObject);

	/**
	 * 根据集合名称查询集合类
	 * 
	 * @param name
	 * @return
	 */
	public abstract DBCollection getCollectionByName(String name);

	/**
	 * 根据集合名称，查询集合的所有记录
	 * 
	 * @param name
	 * @return
	 */
	public abstract DBCursor queryCollection(String name);

	/**
	 * 保存数据
	 * 
	 * @param object
	 * @param collectionName
	 */
	public abstract void save(DBObject object, String collectionName);

	/**
	 * 批量保存数据
	 * 
	 * @param objs
	 * @param collectionName
	 */
	public abstract void saveList(List<DBObject> objs, String collectionName);

	/**
	 * 删除一条记录
	 * 
	 * @param collectionName
	 * @param object
	 */
	public abstract void removeDBObject(String collectionName, DBObject object);

	/**
	 * 删除N条记录
	 * 
	 * @param collectionName
	 * @param dbObjects
	 */
	public abstract void removeDBObjects(String collectionName, BasicDBObject param);

	/**
	 * 删除一个集合
	 * 
	 * @param collectionName
	 */
	public abstract void dropOneCollection(String collectionName);

	/**
	 * 按条件查询一条数据
	 * 
	 * @param collectionName
	 * @param param
	 * @param sort
	 * @return
	 */
	public abstract DBObject queryOneByParam(String collectionName, BasicDBObject param, BasicDBObject sort);

	/**
	 * 根据条件查询所有记录
	 * 
	 * @param collectionName
	 * @param param
	 * @return
	 */
	public abstract DBCursor queryCollection(String collectionName, BasicDBObject param);

	/**
	 * 分页查询记录
	 * 
	 * @param collectionName
	 * @param page
	 * @param param
	 * @param sort
	 * @return
	 */
	public abstract DBCursor queryCollectionByParam(String collectionName, Page page, BasicDBObject param,
			BasicDBObject sort);

	/**
	 * 根据条件查询一个集合
	 * 
	 * @param collectionName
	 * @param param
	 * @return
	 */
	public abstract DBCursor queryCollectionByParam(String collectionName, BasicDBObject param, BasicDBObject sort);

	/**
	 * 提供查询长度的方法
	 * 
	 * @param collectionName
	 * @param param
	 * @return
	 */
	public abstract Long getCount(String collectionName, DBObject param);

	/**
	 * 查询指定返回的结果集
	 * 
	 * @param collectionName
	 * @param param
	 * @return
	 */
	public abstract DBCursor queryCollectionConditions(String collectionName, BasicDBObject param, BasicDBObject param2);

	/**
	 * 更新
	 * 
	 * @param collectionName
	 * @param query
	 * @param object
	 * @return
	 */
	public abstract void findAndModifyCollection(String collectionName, DBObject query, DBObject object);

	/**
	 * 分页查询记录
	 * 
	 * @param collectionName
	 * @param page
	 * @param param
	 *            查询条件
	 * @param param2
	 *            过滤不显示的字段
	 * @param sort
	 *            排序条件
	 * @return
	 */
	public abstract DBCursor queryCollectionByParam(String collectionName, Page page, BasicDBObject param,
			BasicDBObject param2, BasicDBObject sort);

	/**
	 * 找到并删掉一条记录
	 * 
	 * @param collectionName
	 * @param object
	 * @return
	 */
	public abstract DBObject findandRemove(String collectionName, DBObject object);

	/**
	 * 添加唯一Index
	 * 
	 * @param collection
	 * @param dbObject
	 */
	public abstract void ensureIndex(DBCollection collection, DBObject dbObject);

	public abstract DBCursor queryCollectionByParamWithId(String collectionName, Page page, BasicDBObject param,
			BasicDBObject sort);
}
