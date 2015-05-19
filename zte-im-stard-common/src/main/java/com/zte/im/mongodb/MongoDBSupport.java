package com.zte.im.mongodb;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zte.im.util.Page;

public class MongoDBSupport extends AbstractMongoDB {

	public static MongoDBSupport mongoDBSupport = new MongoDBSupport();

	public static String _users = "imusers";

	public static MongoDBSupport getInstance() {
		if (mongoDBSupport == null)
			mongoDBSupport = new MongoDBSupport();
		return mongoDBSupport;
	}

	@Override
	public DBCollection getCollectionByName(String name) {
		// TODO Auto-generated method stub
		// 根据名称返回集合
		DBCollection dbCollection = db.getCollection(name);
		return dbCollection;
	}

	@Override
	public DBCursor queryCollection(String collectionName) {
		// TODO Auto-generated method stub
		// 根据集合名称，查询集合所有记录
		DBCollection dbCollection = getCollectionByName(collectionName);
		DBCursor cur = dbCollection.find(null, new BasicDBObject("_id", false));
		return cur;
	}

	/**
	 * 按条件分页查询集合
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param page
	 *            分页条件
	 * @param param
	 *            条件
	 * @param sort
	 *            排序
	 */
	@Override
	public DBCursor queryCollectionByParam(String collectionName, Page page,
			BasicDBObject param, BasicDBObject sort) {
		DBCollection collection = getCollectionByName(collectionName);
		DBCursor cur = null;
		if (page.getCurrentpage() == 1) {
			cur = collection.find(param, new BasicDBObject("_id", false))
					.sort(sort).limit(page.getPagesize());
		} else {
			cur = collection.find(param, new BasicDBObject("_id", false))
					.sort(sort)
					.skip((page.getCurrentpage() - 1) * page.getPagesize())
					.limit(page.getPagesize());
		}
		return cur;
	}

	@Override
	public DBCursor queryCollectionByParam(String collectionName,
			BasicDBObject param, BasicDBObject sort) {
		DBCollection collection = getCollectionByName(collectionName);
		DBCursor cur = collection.find(param, new BasicDBObject("_id", false))
				.sort(sort);
		return cur;
	}

	/**
	 * 根据条件查询所有记录
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param param
	 *            条件
	 */
	@Override
	public DBCursor queryCollection(String collectionName, BasicDBObject param) {
		DBCollection collection = getCollectionByName(collectionName);
		DBCursor cur = collection.find(param, new BasicDBObject("_id", false));
		return cur;
	}

	/**
	 * 按条件查询一条数据
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param param
	 *            条件
	 * @param sort
	 *            排序条件
	 * @return
	 */
	@Override
	public DBObject queryOneByParam(String collectionName, BasicDBObject param,
			BasicDBObject sort) {
		DBCollection collection = getCollectionByName(collectionName);
		DBObject obj = null;
		if (sort != null) {
			obj = collection.findOne(param, new BasicDBObject("_id", false),
					sort);

		} else {
			obj = collection.findOne(param, new BasicDBObject("_id", false));
		}
		return obj;
	}

	public DBObject queryOneByParam(String collectionName, BasicDBObject param,
			BasicDBObject param1, BasicDBObject sort) {
		DBCollection collection = getCollectionByName(collectionName);
		DBObject obj = null;
		if (sort != null) {
			obj = collection.findOne(param, param1, sort);

		} else {
			obj = collection.findOne(param, param1);
		}
		return obj;
	}

	/**
	 * 删除一条记录
	 * 
	 * @param collectionName
	 * @param object
	 */
	@Override
	public void removeDBObject(String collectionName, DBObject object) {
		DBCollection collection = getCollectionByName(collectionName);
		collection.remove(object);
	}

	@Override
	public void removeDBObjects(String collectionName, BasicDBObject param) {
		DBCollection collection = getCollectionByName(collectionName);
		List<DBObject> list = collection.find(param).toArray();
		for (DBObject db : list) {
			collection.remove(db);
		}
	}

	/**
	 * 找到并删除一条记录
	 * 
	 * @param collectionName
	 * @param object
	 */
	@Override
	public DBObject findandRemove(String collectionName, DBObject object) {
		DBCollection collection = getCollectionByName(collectionName);
		return collection.findAndRemove(object);
	}

	/**
	 * 删除一个集合
	 * 
	 * @param collectionName
	 */
	@Override
	public void dropOneCollection(String collectionName) {
		DBCollection collection = getCollectionByName(collectionName);
		collection.drop();
	}

	/**
	 * 存数据
	 * 
	 * @param object
	 * @param collectionName
	 */
	@Override
	public void save(DBObject object, String collectionName) {
		DBCollection collection = getCollectionByName(collectionName);
		collection.save(object);
	}

	/**
	 * 批量存储
	 * 
	 * @param objs
	 * @param collectionName
	 */
	@Override
	public void saveList(List<DBObject> objs, String collectionName) {
		DBCollection collection = getCollectionByName(collectionName);
		collection.insert(objs);
	}

	@Override
	public void addIndex(DBCollection collection, DBObject dbObject) {
		// TODO Auto-generated method stub
		collection.createIndex(dbObject);
	}

	@Override
	public Long getCount(String collectionName, DBObject param) {
		DBCollection collection = getCollectionByName(collectionName);
		if (param != null) {
			return collection.getCount(param);
		} else {
			return collection.getCount();
		}
	}

	@Override
	public DBCursor queryCollectionConditions(String collectionName,
			BasicDBObject param, BasicDBObject param2) {
		DBCollection collection = getCollectionByName(collectionName);
		param.put("_id", false);
		return collection.find(param2, param);
	}

	public DBCursor queryCollectionConditions(String collectionName,
			BasicDBObject param, BasicDBObject param2, Page page) {
		DBCollection collection = getCollectionByName(collectionName);
		param.put("_id", false);
		DBCursor cur = null;
		if (page.getCurrentpage() == 1) {
			cur = collection.find(param2, param).limit(page.getPagesize());
		} else {
			cur = collection.find(param2, param).limit(page.getPagesize())
					.skip((page.getCurrentpage() - 1) * page.getPagesize())
					.limit(page.getPagesize());
		}
		return cur;
	}

	public DBCursor queryCollectionConditions(String collectionName,
			BasicDBObject param2, Page page) {
		DBCollection collection = getCollectionByName(collectionName);
		DBCursor cur = null;
		if (page.getCurrentpage() == 1) {
			cur = collection.find(param2).limit(page.getPagesize());
		} else {
			cur = collection.find(param2).limit(page.getPagesize())
					.skip((page.getCurrentpage() - 1) * page.getPagesize())
					.limit(page.getPagesize());
		}
		return cur;
	}

	public DBObject queryCollectionCondition(String collectionName,
			BasicDBObject param, BasicDBObject param2) {
		DBCollection collection = getCollectionByName(collectionName);
		param.put("_id", false);
		return collection.findOne(param2, param);
	}

	@Override
	public void findAndModifyCollection(String collectionName, DBObject query,
			DBObject object) {
		// TODO Auto-generated method stub
		DBCollection collection = getCollectionByName(collectionName);
		collection.findAndModify(query, object);
	}

	public DBCursor queryCollectionByParam(String collectionName, Page page,
			BasicDBObject ref, BasicDBObject keys, BasicDBObject sort) {
		DBCollection collection = getCollectionByName(collectionName);

		DBCursor cur = null;
		if (page.getCurrentpage() == 1) {
			cur = collection.find(ref, keys).sort(sort)
					.limit(page.getPagesize());
		} else {
			cur = collection.find(ref, keys).sort(sort)
					.skip((page.getCurrentpage() - 1) * page.getPagesize())
					.limit(page.getPagesize());
		}
		return cur;
	}

	public DBCursor queryCollectionByParam(String collectionName,
			BasicDBObject keys, BasicDBObject ref, BasicDBObject sort) {
		DBCollection collection = getCollectionByName(collectionName);
		return collection.find(ref, keys).sort(sort);
	}

	public void updateCollection(String collectionName, DBObject query,
			DBObject param) {
		DBCollection collection = getCollectionByName(collectionName);
		collection.update(query, param, false, true);
	}

	public void upsertCollection(String collectionName, DBObject query,
			DBObject param) {
		DBCollection collection = getCollectionByName(collectionName);
		collection.update(query, param, true, false);
	}

	@SuppressWarnings("deprecation")
	public void ensureIndex(DBCollection collection, DBObject dbObject) {
		collection.ensureIndex(dbObject);
	}
	
	/**
	 * 按条件分页查询集合
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param page
	 *            分页条件
	 * @param param
	 *            条件
	 * @param sort
	 *            排序
	 */
	@Override
	public DBCursor queryCollectionByParamWithId(String collectionName, Page page, BasicDBObject param,
			BasicDBObject sort) {
		DBCollection collection = getCollectionByName(collectionName);
		DBCursor cur = null;
		if (page.getCurrentpage() == 1) {
			cur = collection.find(param).sort(sort).limit(page.getPagesize());
		} else {
			cur = collection.find(param).sort(sort).skip((page.getCurrentpage() - 1) * page.getPagesize())
					.limit(page.getPagesize());
		}
		return cur;
	}
}
