package com.zte.im.service.impl;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.mysql.jdbc.StringUtils;
import com.zte.im.bean.WorkImg;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.IWorkImgService;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;

public class WorkImgServiceImpl implements IWorkImgService {

	private static IWorkImgService service;

	private WorkImgServiceImpl() {
		super();
	}

	public static IWorkImgService getInstance() {
		if (service == null) {
			service = new WorkImgServiceImpl();
		}
		return service;
	}

	@Override
	public List<DBObject> listWorkImg() {
		BasicDBObject param = new BasicDBObject();
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(Constant.WORK_IMG, param,
				new BasicDBObject("index", 1));
		if (cur != null && cur.size() > 0) {
			return cur.toArray();
		}
		return null;
	}

	@Override
	public void saveWorkImg(String imgList) {
		MongoDBSupport mongo = MongoDBSupport.getInstance();
		mongo.removeDBObjects(Constant.WORK_IMG, new BasicDBObject());
		if (!StringUtils.isNullOrEmpty(imgList)) {
			String[] strs = imgList.split(",");
			for (int i = 0; i < strs.length; i++) {
				WorkImg img = new WorkImg();
				img.setImgUrl(strs[i]);
				img.setIndex(i);
				DBObject dbObject = (DBObject) JSON.parse(GsonUtil.gson.toJson(img));
				mongo.save(dbObject, Constant.WORK_IMG);
			}
		}
		VersionServiceImpl.getInstance().incWorkImgListVer();
	}
}
