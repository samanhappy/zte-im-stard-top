package com.zte.weibo.service.impl;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.zte.im.cache.redis.RedisSupport;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.UUIDGenerator;
import com.zte.weibo.bean.Dept;
import com.zte.weibo.service.DeptService;

@Service
public class DeptServiceImpl implements DeptService {

	private static Logger logger = LoggerFactory
			.getLogger(DeptServiceImpl.class);
	
	@Override
	public List<Dept> getListByName(String name) throws Exception {
		List<Dept> list = null;
		BasicDBObject query = new BasicDBObject();
		if(StringUtils.isNotEmpty(name)){
			query.put("name", name);
		}
		DBCursor dbCursor = null;
		try {
			dbCursor = MongoDBSupport.getInstance().queryCollection(Constant.T_DEPT_COLLECTION_NAME, query);
			if(null != dbCursor){
				Type type = new TypeToken<List<Dept>>(){}.getType();
				list = GsonUtil.gson.fromJson(dbCursor.toArray().toString(), type);
			}
		} catch (Exception e) {
			logger.error("", e);
			logger.error(e.getMessage());
			throw e;
		}finally{
			if(null != dbCursor){
				dbCursor.close();
			}
		}
		return list;
	}

	@Override
	public void add(Dept dept) throws Exception {
		if(null != dept){
			if(null == dept.getCid()){
				dept.setCid(RedisSupport.getInstance().inc(Constant.T_DEPT_COLLECTION_NAME));
			}
			//判断是否唯一
			if(getCountByCid(dept.getCid()) > 0){
				logger.error(String.format("CID为%s的部门在库中已经存在！", dept.getCid()));
				throw new RuntimeException(String.format("CID为%s的部门在库中已经存在！", dept.getCid()));
			}
			
			//父节点为空时默认先给1
			if(null == dept.getPid()){
				dept.setPid(0l);
			}
			if(StringUtils.isEmpty(dept.getId())){
				dept.setId(UUIDGenerator.randomUUID());
			}
			BasicDBObject dbObject = new BasicDBObject();
			dbObject.put("id", dept.getId());
			dbObject.put("gid", dept.getGid());
			dbObject.put("cid", dept.getCid());
			dbObject.put("pid", dept.getPid());
			dbObject.put("name", dept.getName());
			dbObject.put("isroot", dept.getIsroot());
			dbObject.put("nodeType", dept.getNodeType());
			dbObject.put("sequ", dept.getSequ());
			MongoDBSupport.getInstance().save(dbObject, Constant.T_DEPT_COLLECTION_NAME);
			
		}
	}

	@Override
	public long getCountByCid(Long cid) throws Exception {
		long count = 0;
		BasicDBObject query = new BasicDBObject();
		if(null != cid){
			query.put("cid", cid);
		}
		count = MongoDBSupport.getInstance().getCount(Constant.T_DEPT_COLLECTION_NAME, query);
		return count;
	}

}
