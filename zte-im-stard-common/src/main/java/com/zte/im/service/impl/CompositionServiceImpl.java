package com.zte.im.service.impl;

import java.lang.reflect.Type;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zte.im.bean.Composition;
import com.zte.im.bean.Firm;
import com.zte.im.cache.redis.RedisSupport;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.mybatis.bean.UcGroup;
import com.zte.im.service.ICompositionService;
import com.zte.im.service.IFirmService;
import com.zte.im.service.IVersionService;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.MyJSON;

public class CompositionServiceImpl implements ICompositionService {

	private static CompositionServiceImpl service;

	RedisSupport redis = RedisSupport.getInstance();

	private static String COLLECTION = Constant.COM_POSIT;

	IVersionService verService = VersionServiceImpl.getInstance();

	private static Logger logger = LoggerFactory.getLogger(CompositionServiceImpl.class);

	private CompositionServiceImpl() {
		super();
	}

	public static ICompositionService getInstance() {
		if (service == null) {
			service = new CompositionServiceImpl();
		}
		return service;
	}

	@Override
	public Composition getComBySqlId(String id) {
		Composition comp = null;
		DBObject obj = MongoDBSupport.getInstance().queryCollectionCondition(COLLECTION, new BasicDBObject(),
				new BasicDBObject("id", id));
		if (obj != null) {
			comp = com.alibaba.fastjson.JSON.parseObject(obj.toString(), Composition.class);
		}
		return comp;
	}

	@Override
	public Composition getComByCId(Long cid) {
		Composition comp = null;
		DBObject obj = MongoDBSupport.getInstance().queryCollectionCondition(COLLECTION, new BasicDBObject(),
				new BasicDBObject("cid", cid));
		if (obj != null) {
			comp = com.alibaba.fastjson.JSON.parseObject(obj.toString(), Composition.class);
		}
		return comp;
	}

	@Override
	public boolean hasSubComposition(Long pid, Long cid) {
		DBObject param = new BasicDBObject();
		param.put("pid", pid);
		param.put("cid", new BasicDBObject("$ne", cid));
		Long count = MongoDBSupport.getInstance().getCount(COLLECTION, param);
		return count > 0;
	}

	@Override
	public boolean saveOrUpdate(UcGroup group) {
		// 检查企业
		IFirmService firmService = FirmServiceImpl.getInstance();
		String tenantId = group.getTenantId();
		Firm firm = firmService.getFirm(tenantId);
		if (firm == null || firm.getGid() == null) {
			logger.info("firm not exists for:" + tenantId);
			return false;
		}
		boolean exists = true;
		Composition com = getComBySqlId(group.getId());
		if (com == null) {
			exists = false;
			com = new Composition();
		}
		if (com.getCid() == null) {
			com.setCid(redis.inc(COLLECTION));
		}
		com.setGid(firm.getGid());
		com.setId(group.getId());
		com.setName(group.getName());
		com.setSequ(group.getSequ());
		if (tenantId.equals(group.getPid())) {
			com.setIsroot(1);
			com.setPid(0L);
		} else {
			com.setIsroot(0);
			Composition parent = getComBySqlId(group.getPid());
			if (parent != null) {
				Long oldPid = com.getPid();
				if (oldPid != null && oldPid != 0 && oldPid != parent.getCid()) {
					Composition oldParent = getComByCId(oldPid);
					if (oldParent != null && oldParent.getNodeType() == 0) {
						if (!hasSubComposition(oldPid, com.getCid())) {
							oldParent.setNodeType(1); // 更改节点为叶子节点
							updateByCId(oldParent);
						}
					}
				}
				com.setPid(parent.getCid());
				if (parent.getNodeType() == 1) {
					parent.setNodeType(0); // 更改节点为普通节点
					updateByCId(parent);
				}
			} else {
				logger.info("com not exists for:" + group.getPid());
				return false;
			}
		}
		DBObject dbObject = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(com));
		if (exists) {
			BasicDBObject query = new BasicDBObject();
			query.put("id", group.getId());
			MongoDBSupport.getInstance().findAndModifyCollection(COLLECTION, query, dbObject);
		} else {
			MongoDBSupport.getInstance().save(dbObject, COLLECTION);
		}
		// 更新组织机构版本
		verService.incDeptVer();
		return true;
	}

	public void updateByCId(Composition com) {
		if (com != null) {
			MongoDBSupport.getInstance().findAndModifyCollection(COLLECTION, new BasicDBObject("cid", com.getCid()),
					(DBObject) MyJSON.parse(GsonUtil.gson.toJson(com)));
		}
	}

	@Override
	public boolean deleteBySqlIdList(List<String> ids) {
		BasicDBObject query = new BasicDBObject();// 条件
		query.put("id", new BasicDBObject("$in", ids));
		MongoDBSupport.getInstance().removeDBObject(COLLECTION, query);
		// 更新组织机构版本
		verService.incDeptVer();
		return true;
	}

	@Override
	public Composition getComposition(Long gid, String name) {
		List<Composition> compositionList = null;
		BasicDBObject query = new BasicDBObject();// 条件
		query.put("gid", gid);
		query.put("name", name);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionConditions(COLLECTION, new BasicDBObject(), query);
		if (cur != null) {
			if (cur.size() > 0) {
				Type type = new TypeToken<List<Composition>>() {
				}.getType();
				compositionList = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
				return compositionList.get(0);
			}
		}
		return null;
	}

	/**
	 * 根据企业id查询企业结构
	 */
	public List<Composition> getComposition(Long gid) {
		List<Composition> compositionList = null;
		BasicDBObject ref = new BasicDBObject();// 条件
		ref.put("gid", gid);
		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("sequ", 1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(COLLECTION, ref, sort);
		if (cur != null) {
			Type type = new TypeToken<List<Composition>>() {
			}.getType();
			compositionList = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		return compositionList;
	}

}
