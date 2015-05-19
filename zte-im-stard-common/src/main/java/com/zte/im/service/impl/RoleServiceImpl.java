package com.zte.im.service.impl;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zte.im.bean.Role;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.IRoleService;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.MyJSON;
import com.zte.im.util.UUIDGenerator;

public class RoleServiceImpl implements IRoleService {

	private static String COLLECTION = "role";

	private static IRoleService service;

	private RoleServiceImpl() {
		super();
	}

	public static IRoleService getInstance() {
		if (service == null) {
			service = new RoleServiceImpl();
		}
		return service;
	}

	public void saveOrUpdateRole(Role role) {
		if (role.getRoleId() == null || "".equals(role.getRoleId())) {
			role.setRoleId(UUIDGenerator.randomUUID());
		}
		MongoDBSupport mongo = MongoDBSupport.getInstance();
		BasicDBObject query = new BasicDBObject("roleId", role.getRoleId());
		DBObject dbObject = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(role));
		mongo.upsertCollection(COLLECTION, query, dbObject);
	}

	@Override
	public List<Role> listRole() {
		List<Role> list = null;
		MongoDBSupport mongo = MongoDBSupport.getInstance();
		DBCursor cur = mongo.queryCollectionByParam(COLLECTION, null,
				new BasicDBObject("roleName", 1));
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<Role>>() {
			}.getType();
			list = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		return list;
	}
	
	@Override
	public Role getRole(String roleId) {
		Role role = null;
		MongoDBSupport mongo = MongoDBSupport.getInstance();
		DBObject obj = mongo.queryOneByParam(COLLECTION, new BasicDBObject("roleId", roleId), null);
		if (obj != null) {
			role = GsonUtil.gson.fromJson(obj.toString(), Role.class);
		}
		return role;
	}

	@Override
	public void removeRole(String roleId) {
		MongoDBSupport mongo = MongoDBSupport.getInstance();
		BasicDBObject query = new BasicDBObject("roleId", roleId);
		mongo.removeDBObject(COLLECTION, query);
	}

	@Override
	public Role getRole4User(String uid) {
		Role role = null;
		MongoDBSupport mongo = MongoDBSupport.getInstance();
		BasicDBObject query = new BasicDBObject("userList.id", uid);
		DBObject obj = mongo.queryOneByParam(COLLECTION, query, null);
		if (obj != null) {
			Type type = new TypeToken<Role>() {
			}.getType();
			role = GsonUtil.gson.fromJson(obj.toString(), type);
		}
		return role;
	}

}
