package com.zte.im.service.impl;

import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.mybatis.bean.Account;
import com.zte.im.service.IAccountService;
import com.zte.im.util.GsonUtil;

public class AccountServiceImpl implements IAccountService {

	private static IAccountService service;

	private static final String collectionName = "account";

	// 初始化插入一条管理员账号
	static {
		getInstance();
		if (service.get("system") == null) {
			Account acc = new Account("system", "123456", "c1", "all", "系统管理员");
			service.add(acc);
		}
	}

	private AccountServiceImpl() {
		super();
	}

	public static IAccountService getInstance() {
		if (service == null) {
			service = new AccountServiceImpl();
		}
		return service;
	}

	@Override
	public boolean add(Account account) {
		DBObject object = (DBObject) JSON.parse(GsonUtil.gson.toJson(account));
		MongoDBSupport.getInstance().save(object, collectionName);
		return true;
	}

	@Override
	public boolean remove(String name) {
		Account acc = get(name);
		if (acc != null) {
			MongoDBSupport.getInstance().removeDBObjects(collectionName,
					new BasicDBObject("name", name));
			return true;
		}
		return false;
	}

	@Override
	public boolean update(Account acc) {
		if (acc != null && acc.getName() != null) {
			MongoDBSupport.getInstance().upsertCollection(collectionName,
					new BasicDBObject("name", acc.getName()),
					(DBObject) JSON.parse(GsonUtil.gson.toJson(acc)));
			return true;
		}
		return false;
	}

	@Override
	public List<Account> list() {
		DBCursor cur = MongoDBSupport.getInstance().queryCollection(
				collectionName);
		if (cur != null) {
			return GsonUtil.gson.fromJson(cur.toArray().toString(),
					new TypeToken<List<Account>>() {
					}.getType());
		}
		return null;
	}

	@Override
	public Account get(String name) {
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				collectionName, new BasicDBObject("name", name), null);
		return obj != null ? GsonUtil.gson.fromJson(obj.toString(),
				Account.class) : null;
	}

}
