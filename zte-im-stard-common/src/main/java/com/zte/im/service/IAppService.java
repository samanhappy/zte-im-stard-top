package com.zte.im.service;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zte.im.util.Page;

public interface IAppService {

	public List<DBObject> getAppCate();

	public List<DBObject> getAppByCateId(BasicDBObject param, Page page,
			BasicDBObject sort);
}
