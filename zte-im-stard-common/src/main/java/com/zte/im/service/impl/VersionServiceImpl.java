package com.zte.im.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zte.im.bean.DataVer;
import com.zte.im.bean.User;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.IVersionService;
import com.zte.im.util.Constant;

public class VersionServiceImpl implements IVersionService {

	private static final long INIT_VER = 2L;

	private static final String VER_OF_USER_LIST = "userListVer";

	private static final String VER_OF_DEPT = "deptVer";

	private static final String VER_OF_GROUP = "groupVer";

	private static final String VER_OF_APP_CATE = "appCateVer";

	private static final String VER_OF_APP = "appVer";
	
	private static final String VER_OF_WORK_IMG = "imgCateVer";

	private static final String METHOD_FIND_USER_BY_GID = "findUserByGid";

	private static final String METHOD_ENTERPRISE_CONTACT = "enterpriseContact";

	private static final String METHOD_FIND_GROUP = "findGroup";

	private static final String METHOD_LIST_APP_CATE = "listAppCate";

	private static final String METHOD_LIST_APP = "listApp";
	
	private static final String METHOD_GET_WORK_IMG = "getWorkImg";

	private static Logger logger = LoggerFactory
			.getLogger(VersionServiceImpl.class);

	private static VersionServiceImpl service;

	private VersionServiceImpl() {
		super();
	}

	public static VersionServiceImpl getInstance() {
		if (service == null) {
			service = new VersionServiceImpl();
		}
		return service;
	}

	public static Map<String, String> methodVerParamMap = new HashMap<String, String>();

	static {
		methodVerParamMap.put(METHOD_LIST_APP, VER_OF_APP);
		methodVerParamMap.put(METHOD_LIST_APP_CATE, VER_OF_APP_CATE);
		methodVerParamMap.put(METHOD_FIND_GROUP, VER_OF_GROUP);
		methodVerParamMap.put(METHOD_ENTERPRISE_CONTACT, VER_OF_DEPT);
		methodVerParamMap.put(METHOD_FIND_USER_BY_GID, VER_OF_USER_LIST);
		methodVerParamMap.put(METHOD_GET_WORK_IMG, VER_OF_WORK_IMG);

		// 初始化dataVer
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.DATA_VER_COLLECTION_NAME, null, null);
		if (obj == null) {
			// 版本从2开始递增
			BasicDBObject param = new BasicDBObject();
			param.put(VER_OF_USER_LIST, INIT_VER);
			param.put(VER_OF_DEPT, INIT_VER);
			param.put(VER_OF_APP, INIT_VER);
			param.put(VER_OF_APP_CATE, INIT_VER);
			param.put(VER_OF_WORK_IMG, INIT_VER);
			MongoDBSupport.getInstance().save(param,
					Constant.DATA_VER_COLLECTION_NAME);
		} else {
			BasicDBObject param = new BasicDBObject();
			if (!obj.containsField(VER_OF_USER_LIST)) {
				param.put(VER_OF_USER_LIST, INIT_VER);
			}
			if (!obj.containsField(VER_OF_DEPT)) {
				param.put(VER_OF_DEPT, INIT_VER);
			}
			if (!obj.containsField(VER_OF_APP)) {
				param.put(VER_OF_APP, INIT_VER);
			}
			if (!obj.containsField(VER_OF_APP_CATE)) {
				param.put(VER_OF_APP_CATE, INIT_VER);
			}
			if (!obj.containsField(VER_OF_WORK_IMG)) {
				param.put(VER_OF_WORK_IMG, INIT_VER);
			}
			if (param.size() > 0) {
				MongoDBSupport.getInstance().updateCollection(
						Constant.DATA_VER_COLLECTION_NAME, new BasicDBObject(),
						new BasicDBObject("$set", param));
			}
		}
	}

	/**
	 * 获取数据版本.
	 * 
	 * @param id
	 * @return
	 */
	public DataVer getDataVer() {
		DataVer dataVer = null;
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.DATA_VER_COLLECTION_NAME, null, null);
		if (obj != null) {
			dataVer = JSON.parseObject(obj.toString(), DataVer.class);
		}
		return dataVer;
	}

	private void incDataVer(String verKey) {
		BasicDBObject query = new BasicDBObject();
		query.append(verKey, new BasicDBObject("$exists", true));
		BasicDBObject param = new BasicDBObject();
		param.put("$inc", new BasicDBObject(verKey, 1));
		MongoDBSupport.getInstance().updateCollection(
				Constant.DATA_VER_COLLECTION_NAME, query, param);
	}

	public void incAppVer() {
		incDataVer(VER_OF_APP);
	}

	public void incAppCateVer() {
		incDataVer(VER_OF_APP_CATE);
	}

	public void incDeptVer() {
		incDataVer(VER_OF_DEPT);
	}

	public void incUserListVer() {
		incDataVer(VER_OF_USER_LIST);
	}
	
	public void incWorkImgListVer() {
		incDataVer(VER_OF_WORK_IMG);
	}

	public static void main(String[] args) {
		DataVer date = VersionServiceImpl.getInstance().getDataVer();
		logger.info(date.toString());
	}

	@Override
	public boolean isDataUpdated(String method, JSONObject request, User user) {
		String verName = methodVerParamMap.get(method);
		if (verName != null) {
			if (request.containsKey(verName)) {
				Long ver = request.getLong(verName);
				DataVer dataVer = getDataVer();
				if (method.equals(METHOD_FIND_GROUP) && user != null
						&& user.getGroupVer() != null
						&& ver >= user.getGroupVer()) {
					logger.info("findGroup is latest for user:{}",
							user.getUid());
					return false;
				} else if (dataVer != null) {
					if (method.equals(METHOD_ENTERPRISE_CONTACT)
							&& dataVer.getDeptVer() != null
							&& ver >= dataVer.getDeptVer()) {
						logger.info("enterpriseContact info is latest");
						return false;
					} else if (method.equals(METHOD_FIND_USER_BY_GID)
							&& dataVer.getUserListVer() != null
							&& ver >= dataVer.getUserListVer()) {
						logger.info("findUserByGid info is latest");
						return false;
					} else if (method.equals(METHOD_LIST_APP)
							&& dataVer.getAppVer() != null
							&& ver >= dataVer.getAppVer()) {
						logger.info("listApp info is latest");
						return false;
					} else if (method.equals(METHOD_LIST_APP_CATE)
							&& dataVer.getAppCateVer() != null
							&& ver >= dataVer.getAppCateVer()) {
						logger.info("listAppCate info is latest");
						return false;
					} else if (method.equals(METHOD_GET_WORK_IMG)
							&& dataVer.getImgCateVer() != null
							&& ver >= dataVer.getImgCateVer()) {
						logger.info("listAppCate info is latest");
						return false;
					}
				}
			}
		}
		return true;
	}

}
