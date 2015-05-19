package com.zte.im.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zte.databinder.FeedbackBinder;
import com.zte.im.bean.Feedback;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.mybatis.bean.page.FeedbackPageModel;
import com.zte.im.service.IFeebackService;
import com.zte.im.util.Constant;
import com.zte.im.util.Page;

public class FeedbackServiceImpl implements IFeebackService {
	
	private final static Logger logger = LoggerFactory
			.getLogger(FeedbackServiceImpl.class);

	private static String COLLECTION = Constant.FEEDBACK;

	private static IFeebackService service;

	private FeedbackServiceImpl() {
		super();
	}

	public static IFeebackService getInstance() {
		if (service == null) {
			service = new FeedbackServiceImpl();
		}
		return service;
	}

	@Override
	public FeedbackPageModel list(FeedbackBinder binder) {
		BasicDBObject param = new BasicDBObject();
		if (binder.getType() != null && !binder.getType().equals("")) {
			param.put("type", binder.getType());
		}
		if (binder.getKey() != null && !binder.getKey().equals("")) {
			// 用于搜索
		}
		logger.info(param.toString());
		Long totalRecord = MongoDBSupport.getInstance().getCount(COLLECTION,
				param);
		FeedbackPageModel pm = FeedbackPageModel.newPageModel(
				binder.getpSize(), binder.getcPage(), totalRecord.intValue());
		binder.setOffset(pm.getOffset());
		BasicDBObject sort = new BasicDBObject();
		sort.put("time", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				COLLECTION, new Page(binder.getpSize(), binder.getcPage()),
				param, sort);
		if (cur != null && cur.size() > 0) {
			pm.setFeedbackList(cur.toArray());
		}
		return pm;
	}

	@Override
	public Feedback getFeedback(Long id) {
		Feedback fb = null;
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(COLLECTION,
				new BasicDBObject("id", id), null);
		if (obj != null) {
			fb = com.alibaba.fastjson.JSON.parseObject(obj.toString(),
					Feedback.class);
		}
		return fb;
	}
}
