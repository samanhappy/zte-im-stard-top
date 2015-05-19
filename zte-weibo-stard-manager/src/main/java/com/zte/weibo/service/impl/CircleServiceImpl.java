package com.zte.weibo.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zte.im.bean.TGroup;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.Page;
import com.zte.weibo.common.constant.GroupConstant;
import com.zte.weibo.common.constant.GroupUserConstant;
import com.zte.weibo.common.constant.TwitterGroupConstant;
import com.zte.weibo.common.constant.WeiboConstal;
import com.zte.weibo.common.util.excel.ExcelUtil;
import com.zte.weibo.databinder.CircleBinder;
import com.zte.weibo.service.CircleService;

@Service
public class CircleServiceImpl implements CircleService {

	private static Logger logger = LoggerFactory.getLogger(CircleServiceImpl.class);
	
	@Override
	public List<TGroup> getList(CircleBinder circleBinder, Page page) {
		List<TGroup> groups = null;
		BasicDBObject param1 = new BasicDBObject();// 条件
		if(null != circleBinder){
			if(StringUtils.isNotEmpty(circleBinder.getName())){
				Pattern pattern = Pattern.compile("^.*" + circleBinder.getName()+ ".*$", Pattern.CASE_INSENSITIVE); 
				param1.put(GroupConstant.FILED_NAME_GROUPNAME, pattern);
			}
			if(StringUtils.isNotEmpty(circleBinder.getCircleState())){
				param1.put(GroupConstant.FILED_NAME_GROUPSTATUS, circleBinder.getCircleState());
			}
			if(StringUtils.isNotEmpty(circleBinder.getCircleType())){
				param1.put(GroupConstant.FILED_NAME_GROUPTYPE, circleBinder.getCircleType());
			}
		}
		
		MongoDBSupport mongoDBSupport = MongoDBSupport.getInstance();
		page.setCountdate(mongoDBSupport.getCount(Constant.TWITTER_GROUP, param1).intValue());
		DBCursor cur2 = null;
		try {
			cur2 = mongoDBSupport.queryCollectionConditions(
					Constant.TWITTER_GROUP, param1, page);
			if (cur2 != null) {
				Type type = new TypeToken<List<TGroup>>(){}.getType();
				groups = GsonUtil.gson.fromJson(cur2.toArray().toString(), type);
			}
		} catch (JsonSyntaxException e) {
			logger.error("", e);
			logger.error(e.getMessage());
			throw e;
		}finally{
			if(null != cur2){
				cur2.close();
			}
		}
		return groups;
	}

	@Override
	public void modifyGroupStatus(Long[] groupids, String status,String stopDesc)
			throws Exception {
		
		if(null != groupids &&  groupids.length > 0 && StringUtils.isNotEmpty(status)){
			List<Long> groupIdList = new ArrayList<Long>();
			for (int i = 0;i<groupids.length;i++) {
				if(null != groupids[i]){
					groupIdList.add(groupids[i]);
				}else {
					throw new RuntimeException(String.format("第%d个圈子ID为空！", (i+1)));
				}
			}
			BasicDBObject query = new BasicDBObject();
			query.put(GroupConstant.FILED_NAME_GROUPID, new BasicDBObject("$in",groupIdList));
			BasicDBObject object = new BasicDBObject();
			object.put(GroupConstant.FILED_NAME_GROUPSTATUS, status);
			object.put(GroupConstant.FILED_NAME_STOPDESC, stopDesc);
			MongoDBSupport.getInstance().updateCollection(Constant.TWITTER_GROUP, query, new BasicDBObject("$set",object));
			
		}else {
			throw new RuntimeException("传入的圈子ID为空或者状态为空！");
		}
		
	}

	@Override
	public String export(Long[] groupids) throws Exception {
		// 序号、圈子名、状态、圈子成员数、圈子分享数、圈子简介。
		Workbook workbook = null;
		workbook = new XSSFWorkbook(new FileInputStream(new File(this
				.getClass().getResource("/").getPath()
				+ "group template.xlsx")));
		Sheet sheet = workbook.getSheetAt(0);

		int startRow = 1;// 从第二行开始写入数据

		BasicDBObject query = new BasicDBObject();
		if (null != groupids && groupids.length > 0) {
			query.put("groupId",
					new BasicDBObject("$in", Arrays.asList(groupids)));
		}

		DBCursor cur = null;
		List<TGroup> groups = null;
		try {
			cur = MongoDBSupport.getInstance().queryCollectionByParam(
					Constant.TWITTER_GROUP, query, null);
			groups = null;
			if (cur != null) {
				Type type = new TypeToken<List<TGroup>>() {
				}.getType();
				groups = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
			}
		} catch (Exception e) {
			logger.error("", e);
			logger.error(e.getMessage());
			 
		}finally{
			if(null != cur){
				cur.close();
			}
		}
		if (null != groups && !groups.isEmpty()) {
			Map<Long, Long> qzcys = getQZCYS(groupids);
			Map<Long, Long> qzwbs = getQZWBS(groupids);
			for (TGroup group : groups) {
				if(null != group && null != group.getGroupId()){
					//设置默认的圈子状态为启用
					if(StringUtils.isEmpty(group.getGroupStatus())){
						group.setGroupStatus(GroupConstant.STATUS_ON);
					}
					int startCell = 0; // 从第一个单元格开始写入
					Row row = sheet.createRow((short) startRow++);
					// 序号、圈子名、状态、圈子成员数、圈子分享数、圈子简介。
					row.createCell(startCell++).setCellValue(startRow-1);
					row.createCell(startCell++).setCellValue(group.getGroupName());
					row.createCell(startCell++).setCellValue(
							GroupConstant.groupStatusMap.get(group.getGroupStatus()));
					row.createCell(startCell++).setCellValue(null != qzcys.get(group.getGroupId()) ? qzcys.get(group.getGroupId()) : 0);
					row.createCell(startCell++).setCellValue(null != qzwbs.get(group.getGroupId()) ? qzwbs.get(group.getGroupId()) : 0);
					row.createCell(startCell++).setCellValue(group.getGroupIntroduction());
				}
			}
		}
		return ExcelUtil.saveExcel(workbook, "圈子导出");
	}
	
	public long getQZCYS(Long groupId) throws Exception{
		long count = 0;
		BasicDBObject query = new BasicDBObject();
		if(null != groupId){
			query.put("groupId", groupId);
		}
		count = MongoDBSupport.getInstance().getCount(Constant.TWITTER_GROUP_USER, query);
		return count;
	}
	
	public Map<Long, Long> getQZCYS(Long[] groupId) throws Exception{
		Map<Long, Long> result = new HashMap<Long, Long>();
		BasicDBObject query = new BasicDBObject();
		if(null != groupId && groupId.length > 0){
			query.put(GroupUserConstant.FILED_NAME_GROUPID, new BasicDBObject("$in", groupId));
		}
		
		MongoDBSupport mongoDBSupport = MongoDBSupport.getInstance();
		DBCollection collection = mongoDBSupport.getCollectionByName(Constant.TWITTER_GROUP_USER);
		BasicDBList dbList = (BasicDBList) collection.group(new BasicDBObject(
				GroupUserConstant.FILED_NAME_GROUPID, true), query,
				new BasicDBObject("num", 0), WeiboConstal.groupByCountScript);

		if(null != dbList && !dbList.isEmpty()){
			BasicDBObject temp = null;
			for (Object object : dbList) {
				temp = (BasicDBObject)object;
				if(null != temp ){
					result.put(temp.getLong(GroupUserConstant.FILED_NAME_GROUPID), temp.getLong("num"));
				}
			}
		}
		return result;
	}
	public long getQZWBS(Long groupId) throws Exception{
		long count = 0;
		BasicDBObject query = new BasicDBObject();
		if(null != groupId){
			query.put("groupId", groupId);
		}
		count = MongoDBSupport.getInstance().getCount(Constant.TWITTER_GROUP_ID, query);
		return count;
	}

	public Map<Long, Long> getQZWBS(Long[] groupId) throws Exception{
		Map<Long, Long> result = new HashMap<Long, Long>();
		BasicDBObject query = new BasicDBObject();
		if(null != groupId && groupId.length > 0){
			query.put(TwitterGroupConstant.FILED_NAME_GROUPID, new BasicDBObject("$in", groupId));
		}
		
		MongoDBSupport mongoDBSupport = MongoDBSupport.getInstance();
		DBCollection collection = mongoDBSupport.getCollectionByName(Constant.TWITTER_GROUP_ID);
		BasicDBList dbList = (BasicDBList) collection.group(new BasicDBObject(
				TwitterGroupConstant.FILED_NAME_GROUPID, true), query,
				new BasicDBObject("num", 0), WeiboConstal.groupByCountScript);

		if(null != dbList && !dbList.isEmpty()){
			BasicDBObject temp = null;
			for (Object object : dbList) {
				temp = (BasicDBObject)object;
				if(null != temp ){
					result.put(temp.getLong(TwitterGroupConstant.FILED_NAME_GROUPID), temp.getLong("num"));
				}
			}
		}
		return result;
	}
	@Override
	public TGroup getByGroupId(Long groupId) throws Exception {
		TGroup group = null;
		if(null != groupId){
			BasicDBObject basicDBObject = new BasicDBObject();
			basicDBObject.put("groupId", groupId);
			DBObject dbObject = MongoDBSupport.getInstance().queryOneByParam(Constant.TWITTER_GROUP, basicDBObject, null);
			if(null != dbObject){
				group = GsonUtil.gson.fromJson(dbObject.toString(), TGroup.class);
			}
		}
		return group;
	}

}
