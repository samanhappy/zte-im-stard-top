package com.zte.im.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.mybatis.bean.my.UcClientMy;
import com.zte.im.service.IClientService;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;

public class ClientServiceImpl implements IClientService {

	private final static Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

	private static ClientServiceImpl service;

	private ClientServiceImpl() {
		super();
	}

	public static ClientServiceImpl getInstance() {
		if (service == null) {
			service = new ClientServiceImpl();
		}
		return service;
	}

	@Override
	public List<UcClientMy> getListByPage() {
		BasicDBObject sort = new BasicDBObject();
		sort.put("versionCode", -1);
		sort.put("version", -1);
		DBCursor cur = MongoDBSupport.getInstance().getCollectionByName(Constant.UC_CLIENT).find().sort(sort);
		List<UcClientMy> list = null;
		Type type = new TypeToken<List<UcClientMy>>() {
		}.getType();
		list = GsonUtil.gson.fromJson(cur.toArray().toString(), type);

		List<UcClientMy> result = new ArrayList<UcClientMy>();

		List<String> cList = new ArrayList<String>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				UcClientMy umy = list.get(i);
				logger.info(umy.getName() + "_" + umy.getOs() + umy.getVersion());
				if (cList.contains(umy.getName() + "_" + umy.getOs()))
					continue;
				result.add(umy);
				cList.add(umy.getName() + "_" + umy.getOs());
			}
		}
		return result;
	}

	@Override
	public void addClient(UcClientMy my) {
		my.setId(getId());
		MongoDBSupport.getInstance().getCollectionByName(Constant.UC_CLIENT).insert(my.toDBObject());

	}

	@Override
	public void modifyClient(UcClientMy my) {
		BasicDBObject param = new BasicDBObject();
		param.put("id", my.getId());
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(Constant.UC_CLIENT, param, null);
		MongoDBSupport.getInstance().getCollectionByName(Constant.UC_CLIENT).remove(obj);
		MongoDBSupport.getInstance().getCollectionByName(Constant.UC_CLIENT).insert(my.toDBObject());
	}

	@Override
	public UcClientMy selectClientById(String id) {
		BasicDBObject param = new BasicDBObject();
		param.put("id", id);
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(Constant.UC_CLIENT, param, null);
		return GsonUtil.gson.fromJson(obj.toString(), UcClientMy.class);
	}

	@Override
	public void deleteClient(String id) {
		BasicDBObject param = new BasicDBObject();
		param.put("id", id);
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(Constant.UC_CLIENT, param, null);
		MongoDBSupport.getInstance().getCollectionByName(Constant.UC_CLIENT).remove(obj);
	}

	public String getId() {
		return UUID.randomUUID().toString();
	}

	@Override
	public void setActive(String id, String isActive) {
		BasicDBObject query = new BasicDBObject();
		query.put("id", id);
		MongoDBSupport.getInstance().updateCollection(Constant.UC_CLIENT, query,
				new BasicDBObject("$set", new BasicDBObject("isActive", isActive)));
	}

	@Override
	public boolean removeClient(List<String> ids) {
		BasicDBObject query = new BasicDBObject();
		query.put("id", new BasicDBObject("$in", ids));
		MongoDBSupport.getInstance().removeDBObjects(Constant.UC_CLIENT, query);
		return true;
	}

}
