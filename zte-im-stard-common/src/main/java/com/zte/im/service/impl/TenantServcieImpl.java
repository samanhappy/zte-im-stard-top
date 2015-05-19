package com.zte.im.service.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.mybatis.bean.my.UcTenantMy;
import com.zte.im.service.ITenantService;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;

public class TenantServcieImpl implements ITenantService {
	
	private static Logger logger = LoggerFactory.getLogger(TenantServcieImpl.class);
	private static TenantServcieImpl service;

	public static Map<String, String> scaleMap = new HashMap<String, String>();
	public static Map<String, Map<String, String>> cityMap = new HashMap<String, Map<String, String>>();
	public static Map<String, String> provMap = new HashMap<String, String>();

	private TenantServcieImpl() {
		super();
	}

	public static TenantServcieImpl getInstance() {
		if (service == null) {
			service = new TenantServcieImpl();
		}
		return service;
	}


	public void initPass() {
		UcTenantMy ut = new UcTenantMy();
		ut.setUserName("system");
		ut.setPassword("123456");
		BasicDBObject param = new BasicDBObject();
		param.put("userName", ut.getUserName());
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.TENANT_PASS, param, null);
		if (obj == null) {
			MongoDBSupport.getInstance()
					.getCollectionByName(Constant.TENANT_PASS).drop();
			MongoDBSupport.getInstance()
					.getCollectionByName(Constant.TENANT_PASS)
					.insert(ut.toDBObject());
			logger.info(MongoDBSupport.getInstance().queryOneByParam(
					Constant.TENANT_PASS, null, null).toString());
		}
	}

	public String modifySelect(UcTenantMy ut) {
		BasicDBObject param = new BasicDBObject();
		param.put("id", ut.getId());
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.TENANT_SELECT, param, null);
		if (obj != null) {
			UcTenantMy u = GsonUtil.gson.fromJson(obj.toString(),
					UcTenantMy.class);
			if (!(u.getGuimo() == null && u.getProv() == null)) {
				if (ut.getGuimo() == null)
					ut.setGuimo(u.getGuimo());
				if (ut.getProv() == null) {
					ut.setProv(u.getProv());
					ut.setCity(ut.getCity());
				}
				if (ut.getPhotoImg() == null) {
					ut.setPhotoImg(u.getPhotoImg());
				}
			}
			MongoDBSupport.getInstance()
					.getCollectionByName(Constant.TENANT_SELECT).drop();
			MongoDBSupport.getInstance()
					.getCollectionByName(Constant.TENANT_SELECT)
					.insert(ut.toDBObject());
		} else {
			MongoDBSupport.getInstance()
					.getCollectionByName(Constant.TENANT_SELECT)
					.insert(ut.toDBObject());
		}
		return "1";
	}

	public UcTenantMy findTenantSelect(String id) {
		UcTenantMy u = null;
		BasicDBObject param = new BasicDBObject();
		param.put("id", id);
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.TENANT_SELECT, param, null);
		if (obj != null) {
			u = GsonUtil.gson.fromJson(obj.toString(), UcTenantMy.class);
		}
		return u;
	}

	public void initCity() {
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("Citys.xml");
		if (in != null) {
			SAXReader reader = new SAXReader();
			Document doc = null;
			try {
				doc = reader.read(in);
			} catch (DocumentException e) {
				logger.error("", e);
			}
			@SuppressWarnings("unchecked")
			List<Element> l1 = doc.getRootElement().elements();
			for (Element e1 : l1) {
				provMap.put(e1.attributeValue("id"), e1.attributeValue("name"));
				@SuppressWarnings("unchecked")
				List<Element> l2 = e1.elements();
				Map<String, String> map = new HashMap<String, String>();
				for (Element e2 : l2) {
					map.put(e2.attributeValue("id"), e2.attributeValue("name"));
					cityMap.put(e1.attributeValue("name"), map);
				}
			}
		}
	}

	public void initScale() {
		scaleMap.put("1", "1-50");
		scaleMap.put("2", "50-100");
		scaleMap.put("3", "100-500");
		scaleMap.put("4", "500-2000");
		scaleMap.put("5", "2000-10000");
		scaleMap.put("6", "10000-100000");
	}

	public String getScaleJSON() {
		String str = JSONObject.toJSON(scaleMap).toString();
		return str;
	}

	public String getProvsJSON() {
		String str = JSONObject.toJSON(provMap).toString();
		return str;
	}

	public String getCityJSON(String prov) {
		String str = JSONObject.toJSON(cityMap.get(prov)).toString();
		return str;
	}

}
