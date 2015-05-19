package com.zte.im.service.impl;

import java.lang.reflect.Type;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.zte.im.bean.News;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.INewsService;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;

public class NewsServiceImpl implements INewsService {

	private final static Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

	MongoDBSupport mongoDBSupport = MongoDBSupport.getInstance();

	private static NewsServiceImpl service;

	private NewsServiceImpl() {
		super();
	}

	public static NewsServiceImpl getInstance() {
		if (service == null) {
			service = new NewsServiceImpl();
		}
		return service;
	}

	@Override
	public void saveNewsObject(News news) {
		mongoDBSupport.save((DBObject) JSON.parse(GsonUtil.gson.toJson(news)), Constant.NEWS);
	}

	@Override
	public List<News> queryNewsByParams(int days, String title, String flag) {
		List<News> news = null;
		BasicDBObject param = new BasicDBObject();
		if (days != 0) {
			long time = System.currentTimeMillis() - days * 24 * 60 * 60 * 1000L;
			param.put("date", new BasicDBObject("$gte", time));
		}
		if (null != title)
			param.put("title", title);
		if (null != flag)
			param.put("flag", flag);
		BasicDBObject sort = new BasicDBObject();
		sort.put("date", -1);
		DBCursor cursor = mongoDBSupport.queryCollectionByParam(Constant.NEWS, param, sort);
		logger.info(cursor.toArray().toString());
		if (cursor != null && cursor.size() > 0) {
			Type type = new TypeToken<List<News>>() {
			}.getType();
			news = GsonUtil.gson.fromJson(cursor.toArray().toString(), type);
		}
		return news;
	}

	@Override
	public void delNewsObject(String id) {
		BasicDBObject param = new BasicDBObject();
		param.put("id", id);
		mongoDBSupport.removeDBObjects(Constant.NEWS, param);
	}

	@Override
	public News getNewsById(String id) {
		BasicDBObject param = new BasicDBObject();
		param.put("id", id);
		DBObject obj = mongoDBSupport.queryOneByParam(Constant.NEWS, param, null);
		News news = GsonUtil.gson.fromJson(obj.toString(), News.class);
		return news;
	}

	@Override
	public void updateNewsObject(News news) {
		BasicDBObject query = new BasicDBObject();
		query.put("id", news.getId());
		mongoDBSupport.findAndModifyCollection(Constant.NEWS, query, (DBObject) JSON.parse(GsonUtil.gson.toJson(news)));
	}

}
