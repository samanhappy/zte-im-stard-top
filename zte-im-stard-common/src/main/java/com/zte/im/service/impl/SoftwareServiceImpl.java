package com.zte.im.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mysql.jdbc.StringUtils;
import com.zte.im.bean.Software;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.mybatis.bean.my.UcClientMy;
import com.zte.im.service.ISoftwareService;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;

/**
 * 
 * @ClassName: SoftwareServiceImpl
 * @Description: 软件升级接口.
 * @author wangpk
 * @date 2014-6-27 下午2:26:10
 * 
 */
public class SoftwareServiceImpl implements ISoftwareService {

	private static Logger logger = LoggerFactory.getLogger(SoftwareServiceImpl.class);

	@Override
	public Software findSoftwareUpdate(String client_type) {
		BasicDBObject sort = new BasicDBObject();
		sort.put("versionCode", -1);
		sort.put("version", -1);
		BasicDBObject param = new BasicDBObject();
		param.put("os", client_type);
		param.put("isActive", "on");
		Software software = null;
		try {
			DBObject dbO = MongoDBSupport.getInstance().queryOneByParam(Constant.UC_CLIENT, param, sort);
			if (dbO != null) {
				UcClientMy ucm = GsonUtil.gson.fromJson(dbO.toString(), new TypeToken<UcClientMy>() {
				}.getType());
				software = ucClient2Software(ucm);
			}
		} catch (JsonSyntaxException e) {
			logger.error("", e);
		}
		return software;
	}

	private Software ucClient2Software(UcClientMy ucm) {
		Software software = new Software();
		software.setId(ucm.getId());
		software.setClient_type(ucm.getOs());
		software.setIconUrl(ucm.getIconUrl());
		software.setUpdateLog(ucm.getUpdateLog());
		software.setUpdate_url(ucm.getApkUrl());
		if (!StringUtils.isNullOrEmpty(ucm.getVersionCode())) {
			software.setVersion(Integer.valueOf(ucm.getVersionCode()));
		}
		software.setVersion_name(ucm.getVersion());
		software.setApkName(ucm.getApkName());
		if (!StringUtils.isNullOrEmpty(ucm.getForceUpdate())) {
			software.setForceUpdate(Integer.valueOf(ucm.getForceUpdate()));
		}
		return software;
	}

	private static SoftwareServiceImpl service;

	private SoftwareServiceImpl() {
		super();
	}

	public static SoftwareServiceImpl getInstance() {
		if (service == null) {
			service = new SoftwareServiceImpl();
		}
		return service;
	}

	public static void main(String[] args) {
		SoftwareServiceImpl.getInstance().findSoftwareUpdate("android");
	}
}
