package com.zte.weibo.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

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
import com.mongodb.util.JSON;
import com.zte.im.bean.TAttention;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.IDSequence;
import com.zte.im.util.Page;
import com.zte.weibo.bean.Dept;
import com.zte.weibo.bean.User;
import com.zte.weibo.common.constant.AttentionConstant;
import com.zte.weibo.common.constant.AttentionConstant.AttentionType;
import com.zte.weibo.common.constant.CommentConstant;
import com.zte.weibo.common.constant.GroupConstant;
import com.zte.weibo.common.constant.GroupUserConstant;
import com.zte.weibo.common.constant.TwitterConstant;
import com.zte.weibo.common.constant.UserConstant;
import com.zte.weibo.common.constant.WeiboConstal;
import com.zte.weibo.common.util.excel.ExcelUtil;
import com.zte.weibo.protocol.ResponseMain;
import com.zte.weibo.service.AccountService;
import com.zte.weibo.service.DeptService;

@Service
public class AccountServiceImpl implements AccountService{

	private static Logger logger = LoggerFactory
			.getLogger(AccountServiceImpl.class);
	
	@Resource
	private DeptService deptService;
	
	@Override
	public List<User> getList(User user, Page page) {
		List<User> users = new ArrayList<User>();
		BasicDBObject param1 = new BasicDBObject();// 条件
		if(null != user){
			if(StringUtils.isNotEmpty(user.getName())){
				Pattern pattern = Pattern.compile("^.*" + user.getName()+ ".*$", Pattern.CASE_INSENSITIVE); 
				param1.put("name", pattern);
			}
			if(StringUtils.isNotEmpty(user.getState())){
				if("1".equals(user.getState())){
					List<BasicDBObject> orWhere = new ArrayList<BasicDBObject>();
					orWhere.add( new BasicDBObject("state",null));
					orWhere.add(new BasicDBObject("state",user.getState()));
					param1.put("$or", orWhere);
				}else {
					param1.put("state", user.getState());
				}
			}
		}
		
		MongoDBSupport mongoDBSupport = MongoDBSupport.getInstance();
		page.setCountdate(mongoDBSupport.getCount(Constant.T_USER_COLLECTION_NAME, param1).intValue());
		DBCursor cur2 = null;
		try {
			cur2 = mongoDBSupport.queryCollectionConditions(
					Constant.T_USER_COLLECTION_NAME, param1, page);
			if (cur2 != null) {
				Type type = new TypeToken<List<User>>(){}.getType();
				users = GsonUtil.gson.fromJson(cur2.toArray().toString(), type);
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
		
		if(null != users && !users.isEmpty()){
			for (User u : users) {
				if(StringUtils.isEmpty(u.getState())){
					u.setState("1");// 默认用户状态为启用
				}
			}
		}
		return users;
	}
	
	public List<User> getListByCid(Long cid) throws Exception{
		List<User> users = new ArrayList<User>();
		BasicDBObject query = new BasicDBObject();
		if(null != cid){
			query.put("cid", cid);
		}
		MongoDBSupport mongoDBSupport = MongoDBSupport.getInstance();
		DBCursor cur2 = null;
		try {
			cur2 = mongoDBSupport.queryCollection(Constant.T_USER_COLLECTION_NAME, query);
			if (cur2 != null) {
				Type type = new TypeToken<List<User>>(){}.getType();
				users = GsonUtil.gson.fromJson(cur2.toArray().toString(), type);
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
		
		if(null != users && !users.isEmpty()){
			for (User u : users) {
				if(StringUtils.isEmpty(u.getState())){
					u.setState("1");// 默认用户状态为启用
				}
			}
		}
		return users;
	}
	@Override
	public void addUser(User user) throws Exception {
		if(null != user && StringUtils.isNotEmpty(user.getName()) && StringUtils.isNotEmpty(user.getLoginname())){
			if(null == user.getUid()){
				if(StringUtils.isEmpty(user.getJid())){
//					user.setJid(RedisSupport.getInstance().inc(Constant.T_USER_COLLECTION_NAME).toString());
					user.setJid(IDSequence.getUID().toString());
				}
				user.setUid(Long.parseLong("1"+user.getJid()));
			}
			//判断UID是否唯一
			if(null != getByUid(user.getUid())){
				logger.error(String.format("用户的UID%s在库中已经存在！", user.getUid()));
				throw new RuntimeException(String.format("用户的UID%s在库中已经存在！", user.getUid()));
			}
			
			//判断登录名是否有重复的
			if(getCountByLoginName(user.getLoginname()) > 0){
				logger.error(String.format("登录名为%s的用户已经存在", user.getLoginname()));
				throw new RuntimeException(String.format("登录名为%s的用户已经存在", user.getLoginname()));
			}
			
			//如果sname为空时，默认设置为当前的name 
			if(StringUtils.isEmpty(user.getSname())){
				user.setSname(user.getName());
			}
	
			//处理name出现同名的情况，名称的后面加序号
			long nameCount = getCountBySname(user.getName());
			if(nameCount > 0){
				user.setName(user.getName()+nameCount);
			}
			
			if(null == user.getState()){
				user.setState(UserConstant.STATE_ON);
			}
			
			BasicDBObject dbObject = new BasicDBObject();
			dbObject.put("uid", user.getUid());
			dbObject.put("gid", user.getGid());
			dbObject.put("loginname", user.getLoginname());
			dbObject.put("name", user.getName());
			dbObject.put("sname", user.getSname());
			dbObject.put("pwd", user.getPwd());
			dbObject.put("sex", user.getSex());
			dbObject.put("birthday", user.getBirthday());
			dbObject.put("jid", user.getJid());
			dbObject.put("mob", user.getMob());
			dbObject.put("email", user.getEmail());
			dbObject.put("cid", user.getCid());
			dbObject.put("cname", user.getCname());
			dbObject.put("post", user.getPost());
			dbObject.put("autograph", user.getAutograph());
			dbObject.put("telephone", user.getTelephone());
			dbObject.put("fax", user.getFax());
			dbObject.put("remark", user.getRemark());
			dbObject.put("viopId", user.getViopId());
			dbObject.put("viopPwd", user.getViopPwd());
			dbObject.put("viopSid", user.getViopSid());
			dbObject.put("viopSidPwd", user.getViopSidPwd());
			dbObject.put("minipicurl", user.getMinipicurl());
			dbObject.put("bigpicurl", user.getBigpicurl());
			dbObject.put("pwdErrors", user.getPwdErrors());
			dbObject.put("state", user.getState());
			dbObject.put("isDelete", user.getIsDelete());
			dbObject.put("sortId", user.getSortId());
			dbObject.put("ver", user.getVer());
			dbObject.put("groupVer", user.getGroupVer());
			dbObject.put("deptId", user.getDeptId());
			dbObject.put("createUserId", user.getCreateUserId());
			dbObject.put("createTime", new Date().getTime());
			dbObject.put("updateUserId", user.getUpdateUserId());
			dbObject.put("updateTime", new Date().getTime());
			dbObject.put("remark", user.getRemark());
			MongoDBSupport.getInstance().save(dbObject, Constant.T_USER_COLLECTION_NAME);
		}
	}

	@Override
	public User getByUid(Long uid) {
		User user = null;
		if(null != uid){
			BasicDBObject basicDBObject = new BasicDBObject();
			basicDBObject.put("uid", uid);
			DBObject dbObject = MongoDBSupport.getInstance().queryOneByParam(Constant.T_USER_COLLECTION_NAME, basicDBObject, null);
			if(null != dbObject){
				user = GsonUtil.gson.fromJson(dbObject.toString(), User.class);
			}
		}
		return user;
	}

	@Override
	public ResponseMain modifyUserState(Long[] uids, String states,String stopDesc,ResponseMain main) {
		if(null == main){
			main = new ResponseMain();
		}
		
		if(null != uids && uids.length > 0  && null != states){
			for (int i = 0;i<uids.length;i++) {
				if(null != uids[i] ){
					BasicDBObject query = new BasicDBObject();
					query.put("uid", uids[i]);
					BasicDBObject object = new BasicDBObject();
					object.put("state", states);
					object.put("stopDesc", stopDesc);
					MongoDBSupport.getInstance().updateCollection(Constant.T_USER_COLLECTION_NAME, query, new BasicDBObject("$set",object));
				}else {
					main.setResult(false);
					main.setMsg(String.format("第%d个用户ID为空！", (i+1)) );
				}
			}
		}else {
			main.setMsg("传入的用户ID为空或者状态为空！");
			main.setResult(false);
		}
		
		return main;
	}

	
	@Override
	public String export(Long[] uid) throws Exception{
		//序号、分享名、状态、原创分享数、转发分享数、参与评论次数、创建圈子数、参与圈子数
		Workbook workbook = null;
		workbook = new XSSFWorkbook(new FileInputStream(new File(this.getClass()
				.getResource("/").getPath()
				+ "user template.xlsx")));
		Sheet sheet = workbook.getSheetAt(0);

		int startRow = 1;// 从第二行开始写入数据
		
		BasicDBObject query = new BasicDBObject();
		if(null != uid && uid.length > 0){
			query.put("uid", new BasicDBObject("$in", Arrays.asList(uid)));
		}
		
		DBCursor cur = null;
		List<User> users = null;
		try {
			cur = MongoDBSupport.getInstance().queryCollectionByParam(
					Constant.T_USER_COLLECTION_NAME, query, null);
			users = null;
			if (cur != null) {
				Type type = new TypeToken<List<User>>() {
				}.getType();
				users = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
			}
		} catch (Exception e) {
			logger.error("", e);
			logger.error(e.getMessage());
			throw e;
		}finally{
			if(null != cur ){
				cur.close();
			}
		}
		if(null != users && !users.isEmpty()){
			Map<Long, Long> ycwbs = getYCWBS(uid);
			Map<Long, Long> zfwbs = getZFWFB(uid);
			Map<Long, Long> cypls = getCYPLS(uid);
			Map<Long, Long> cjqzs = getCJQZS(uid);
			Map<Long, Long> cyqzs = getCYPLS(uid);
			for (User user : users) {
				int startCell = 0; // 从第一个单元格开始写入
				if(null != user && null != user.getUid()){//用户为空或者UID为空时跳过
					Row row = sheet.createRow((short) startRow++);
					//序号、分享名、状态、原创分享数、转发分享数、参与评论次数、创建圈子数、参与圈子数
					row.createCell(startCell++).setCellValue(startRow-1);
					row.createCell(startCell++).setCellValue(user.getName());
					row.createCell(startCell++).setCellValue(UserConstant.stateMap.get(user.getState()));
					row.createCell(startCell++).setCellValue(null != ycwbs.get(user.getUid()) ? ycwbs.get(user.getUid()) : 0);
					row.createCell(startCell++).setCellValue(null != zfwbs.get(user.getUid()) ? zfwbs.get(user.getUid()) : 0);
					row.createCell(startCell++).setCellValue(null != cypls.get(user.getUid()) ? cypls.get(user.getUid()) : 0);
					row.createCell(startCell++).setCellValue(null != cjqzs.get(user.getUid()) ? cjqzs.get(user.getUid()) : 0);
					row.createCell(startCell++).setCellValue(null != cyqzs.get(user.getUid()) ? cyqzs.get(user.getUid()) : 0);
				}
			}
		}
		logger.info("数据已经导出完成。。。");
		return ExcelUtil.saveExcel(workbook, "账号导出");
	}

	
	@Override
	public long getYCWBS(Long uid) throws Exception{
		return getWBSByTwitterType(uid, TwitterConstant.TWITTER_TYPE_YC);
	}
	
	@Override
	public long getZFWFB(Long uid) throws Exception{
		return getWBSByTwitterType(uid, TwitterConstant.TWITTER_TYPE_ZF);
	}
	
	@Override
	public Map<Long, Long> getYCWBS(Long[] uid) throws Exception{
		return getWBSByTwitterType(uid, TwitterConstant.TWITTER_TYPE_YC);
	}
	
	@Override
	public Map<Long, Long> getZFWFB(Long[] uid) throws Exception{
		return getWBSByTwitterType(uid, TwitterConstant.TWITTER_TYPE_ZF);
	}
	
	@Override
	public long getCYPLS(Long uid) throws Exception{
		long count = getCommentCountByCommentType(uid, CommentConstant.TYPE_CYPL);
		count += getCommentCountByCommentType(uid, CommentConstant.TYPE_PL);
		return count;
	}
	@Override
	public Map<Long, Long> getCYPLS(Long[] uids) throws Exception{
		Map<Long, Long> result = getCommentCountByCommentType(uids, CommentConstant.TYPE_CYPL);
		return result;
	}
	@Override
	public long getCJQZS(Long uid) throws Exception{
		long count = 0;
		BasicDBObject query = new BasicDBObject();
		if(null != uid){
			query.put("createUserId", uid);
		}
		count = MongoDBSupport.getInstance().getCount(Constant.TWITTER_GROUP, query);
		
		return count;
	}
	
	@Override
	public Map<Long, Long> getCJQZS(Long[] uid) throws Exception{
		Map<Long, Long> result = new HashMap<Long, Long>();
		BasicDBObject query = new BasicDBObject();
		if(null != uid){
			query.put(GroupConstant.FILED_NAME_CREATEUSERID, new BasicDBObject("$in", uid));
		}
		BasicDBList dbList = (BasicDBList) MongoDBSupport
				.getInstance()
				.getCollectionByName(Constant.TWITTER_GROUP)
				.group(new BasicDBObject(
						GroupConstant.FILED_NAME_CREATEUSERID, true), query,
						new BasicDBObject("num", 0),
						WeiboConstal.groupByCountScript);
		if(null != dbList && !dbList.isEmpty()){
			BasicDBObject temp = null;
			for (Object object : dbList) {
				temp = (BasicDBObject)object;
				if(null != temp ){
					result.put(temp.getLong(GroupConstant.FILED_NAME_CREATEUSERID), temp.getLong("num"));
				}
			}
		}
		return result;
	}
	
	@Override
	public long getCYQZS(Long uid) throws Exception{
		long count = 0;
		BasicDBObject query = new BasicDBObject();
		if(null != uid){
			query.put(GroupUserConstant.FILED_NAME_USERID, uid);
		}
		count = MongoDBSupport.getInstance().getCount(Constant.TWITTER_GROUP_USER, query);
		return count;
	}
	
	@Override
	public Map<Long, Long> getCYQZS(Long[] uid) throws Exception{
		Map<Long, Long> result = new HashMap<Long, Long>();
		BasicDBObject query = new BasicDBObject();
		if(null != uid){
			query.put(GroupUserConstant.FILED_NAME_USERID, new BasicDBObject("$in", uid));
		}
		BasicDBList dbList = (BasicDBList) MongoDBSupport
				.getInstance()
				.getCollectionByName(Constant.TWITTER_GROUP_USER)
				.group(new BasicDBObject(
						GroupUserConstant.FILED_NAME_USERID, true), query,
						new BasicDBObject("num", 0),
						WeiboConstal.groupByCountScript);
		if(null != dbList && !dbList.isEmpty()){
			BasicDBObject temp = null;
			for (Object object : dbList) {
				temp = (BasicDBObject)object;
				if(null != temp ){
					result.put(temp.getLong(GroupUserConstant.FILED_NAME_USERID), temp.getLong("num"));
				}
			}
		}
		return result;
	}

	@Override
	public long getWBSByTwitterType(Long uid, String twitterType)
			throws Exception {
		long count = 0;
		BasicDBObject query = new BasicDBObject();
		if(null != uid){
			query.put(TwitterConstant.FILED_NAME_USERID, uid);
		}
		if(StringUtils.isNotEmpty(twitterType)){
			query.put(TwitterConstant.FILED_NAME_TWITTERTYPE, twitterType);
		}
		count = MongoDBSupport.getInstance().getCount(Constant.TWITTER_TWITTER, query);
		return count;
	}
	
	@Override
	public Map<Long, Long> getWBSByTwitterType(Long[] uid, String twitterType) throws Exception{
		Map<Long, Long> result = new HashMap<Long, Long>();
		BasicDBObject query = new BasicDBObject();
		if(null != uid && uid.length > 0){
			query.put(TwitterConstant.FILED_NAME_USERID, new BasicDBObject("$in", uid));
		}
		if(StringUtils.isNotEmpty(twitterType)){
			query.put(TwitterConstant.FILED_NAME_TWITTERTYPE, twitterType);
		}
		
		MongoDBSupport mongoDBSupport = MongoDBSupport.getInstance();
		DBCollection collection = mongoDBSupport.getCollectionByName(Constant.TWITTER_TWITTER);
		BasicDBList dbList = (BasicDBList) collection.group(new BasicDBObject(
				TwitterConstant.FILED_NAME_USERID, true), query,
				new BasicDBObject("num", 0), WeiboConstal.groupByCountScript);

		if(null != dbList && !dbList.isEmpty()){
			BasicDBObject temp = null;
			for (Object object : dbList) {
				temp = (BasicDBObject)object;
				if(null != temp ){
					result.put(temp.getLong(TwitterConstant.FILED_NAME_USERID), temp.getLong("num"));
				}
			}
		}
		
		return result;
	}

	@Override
	public long getCommentCountByCommentType(Long uid, String commentType)
			throws Exception {
		long count = 0;
		BasicDBObject query = new BasicDBObject();
		if(null != uid){
			query.put(CommentConstant.FILED_NAME_USERID, uid);
		}
		if(StringUtils.isNotEmpty(commentType)){
			query.put(CommentConstant.FILED_NAME_COMMENTTYPE, commentType);
		}
		count = MongoDBSupport.getInstance().getCount(Constant.TWITTER_COMMENT, query);
		return count;
	}

	@Override
	public Map<Long, Long> getCommentCountByCommentType(Long[] uid, String commentType)
			throws Exception {
		Map<Long, Long> result = new HashMap<Long, Long>();
		BasicDBObject query = new BasicDBObject();
		if(null != uid && uid.length > 0){
			query.put(CommentConstant.FILED_NAME_USERID, new BasicDBObject("$in", uid));
		}
		if(StringUtils.isNotEmpty(commentType)){
			query.put(CommentConstant.FILED_NAME_COMMENTTYPE, commentType);
		}
		
		MongoDBSupport mongoDBSupport = MongoDBSupport.getInstance();
		DBCollection collection = mongoDBSupport.getCollectionByName(Constant.TWITTER_COMMENT);
		BasicDBList dbObject = (BasicDBList)collection.group(new BasicDBObject(CommentConstant.FILED_NAME_USERID,true), query, new BasicDBObject("num", 0), WeiboConstal.groupByCountScript);
		
		if(null != dbObject && !dbObject.isEmpty()){
			BasicDBObject temp = null;
			for (Object object : dbObject) {
				temp = (BasicDBObject)object;
				if(null != temp ){
					result.put(temp.getLong(CommentConstant.FILED_NAME_USERID), temp.getLong("num"));
				}
			}
		}
		return result;
	}
	@Override
	public long getCountBySname(String sname) throws Exception {
		long count = 0;
		if(StringUtils.isNotEmpty(sname)){
			BasicDBObject query = new BasicDBObject();
			query.put("sname", sname);
			count = MongoDBSupport.getInstance().getCount(Constant.T_USER_COLLECTION_NAME, query);
		}
		return count;
	}

	@Override
	public long getCountByLoginName(String loginName) throws Exception {
		long count = 0;
		if(StringUtils.isNotEmpty(loginName)){
			BasicDBObject query = new BasicDBObject();
			query.put("loginname", loginName);
			count = MongoDBSupport.getInstance().getCount(Constant.T_USER_COLLECTION_NAME, query);
		}
		return count;
	}

	@Override
	public void addOrUpdate(User user) throws Exception {
		if(null != user && StringUtils.isNotEmpty(user.getLoginname()) && getCountByLoginName(user.getLoginname()) > 0){
			update(user);
		}else {
			addUser(user);
		}
	}

	@Override
	public void update(User user) throws Exception {
		if(null == user || StringUtils.isEmpty(user.getLoginname())){
			logger.error("更新用户时，传入的用户对象为空或者登录名为空");
			throw new RuntimeException("更新用户时，传入的用户对象为空或者登录名为空");
		}
		BasicDBObject query = new BasicDBObject();
		query.put("loginname", user.getLoginname());
		
		//处理name出现同名的情况，名称的后面加序号
		long nameCount = getCountBySname(user.getName());
		if(nameCount > 1){
			user.setName(user.getName()+nameCount);
		}
		
		if(null == user.getState()){
			user.setState(UserConstant.STATE_ON);
		}
		
		BasicDBObject dbObject = new BasicDBObject();
		if(null != user.getName()){
			dbObject.put("name", user.getName());
		}
		if(null != user.getPwd()){
			dbObject.put("pwd", user.getPwd());
		}		
		if(null != user.getSex()){
			dbObject.put("sex", user.getSex());
		}
		if(null != user.getBirthday()){
			dbObject.put("birthday", user.getBirthday());
		}
		if(null != user.getJid()){
			dbObject.put("jid", user.getJid());
		}
		if(null != user.getMob()){
			dbObject.put("mob", user.getMob());
		}
		if(null != user.getEmail()){
			dbObject.put("email", user.getEmail());
		}
		if(null != user.getCid()){
			dbObject.put("cid", user.getCid());
		}
		if(null != user.getCname()){
			dbObject.put("cname", user.getCname());
		}
		if(null != user.getPost()){
			dbObject.put("post", user.getPost());
		}
		if(null != user.getAutograph()){
			dbObject.put("autograph", user.getAutograph());
		}
		if(null != user.getTelephone()){
			dbObject.put("telephone", user.getTelephone());
		}
		if(null != user.getFax()){
			dbObject.put("fax", user.getFax());
		}
		if(null != user.getRemark()){
			dbObject.put("remark", user.getRemark());
		}
		if(null != user.getViopId()){
			dbObject.put("viopId", user.getViopId());
		}
		if(null != user.getViopPwd()){
			dbObject.put("viopPwd", user.getViopPwd());
		}
		if(null != user.getViopSid()){
			dbObject.put("viopSid", user.getViopSid());
		}
		if(null != user.getViopSidPwd()){
			dbObject.put("viopSidPwd", user.getViopSidPwd());
		}
		if(null != user.getMinipicurl()){
			dbObject.put("minipicurl", user.getMinipicurl());
		}
		if(null != user.getBigpicurl()){
			dbObject.put("bigpicurl", user.getBigpicurl());
		}
		if(null != user.getPwdErrors()){
			dbObject.put("pwdErrors", user.getPwdErrors());
		}
		if(null != user.getState()){
			dbObject.put("state", user.getState());
		}
		if(null != user.getSortId()){
			dbObject.put("sortId", user.getSortId());
		}
		if(null != user.getVer()){
			dbObject.put("ver", user.getVer());
		}
		if(null != user.getGroupVer()){
			dbObject.put("groupVer", user.getGroupVer());
		}
		if(null != user.getDeptId()){
			dbObject.put("deptId", user.getDeptId());
		}
		dbObject.put("updateUserId", user.getUpdateUserId());
		dbObject.put("updateTime", new Date().getTime());
		if(StringUtils.isNotEmpty(user.getRemark())){
			dbObject.put("remark", user.getRemark());
		}
//		dbObject.put("isDelete", user.getIsDelete());
		
		MongoDBSupport.getInstance().updateCollection(Constant.T_USER_COLLECTION_NAME, query, new BasicDBObject("$set",dbObject));
		
	}

	@Override
	public void setAllAttention(Long[] uids) throws Exception {
		if(null == uids || uids.length <1){
			return ;
		}
		//查询所有用户
		List<User> allUsers = getList(null , new Page(Integer.MAX_VALUE,1));
		for (Long uid : uids) {
			if(null != uid){
				for (User user : allUsers) {
					if(null != user && null != user.getUid()){
						addAttention(user.getUid(), uid,AttentionType.normal,AttentionConstant.ATTR_DEFAULT);
					}
				}
			}
		}
		
	}
	
	@Override
	public void delAllAttention(Long[] uids) throws Exception{
		if(null == uids || uids.length <1){
			return ;
		}
		BasicDBObject query = new BasicDBObject();
		query.put(AttentionConstant.FILED_NAME_ATTENTIONUSERID, new BasicDBObject("$in", uids));
		query.put(AttentionConstant.FILED_NAME_ATTENTIONATTR, AttentionConstant.ATTR_DEFAULT);
		MongoDBSupport.getInstance().removeDBObject(Constant.TWITTER_ATTENTION, query);
		
	}
	
	public void setAttentionDept()throws Exception{
		List<Dept> depts = deptService.getListByName(null);
		if(null == depts || depts.isEmpty()){
			return ;
		}
		for (Dept dept : depts) {
			if(null != dept && null != dept.getCid()){
				//查询部门下的所有用户
				List<User> users = getListByCid(dept.getCid());
				if(null != users && !users.isEmpty()){
					for (User user : users) {
						if(null != user && null != user.getUid()){
							for (User user2 : users) {
								// 排除自己
								if(null != user2 && null != user2.getUid() && !user.getUid().equals(user2.getUid())){
									logger.info(String.format("正在设置用户%s部门关注用户%s", user.getUid(),user2.getUid()));
									addAttention(user.getUid(), user2.getUid(), AttentionType.normal, AttentionConstant.ATTR_DEPT);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void delAttentionDept()throws Exception{
		BasicDBObject query = new BasicDBObject();
		query.put(AttentionConstant.FILED_NAME_ATTENTIONATTR, AttentionConstant.ATTR_DEPT);
		MongoDBSupport.getInstance().removeDBObject(Constant.TWITTER_ATTENTION, query);
	}
	@Override
	public void addAttention(Long uid,Long attentionUid,AttentionType attentionType,String attentionAttr) throws Exception{
		if(null == uid || null == attentionUid){
			logger.error("设置用户关注时，传入的用户ID为空！");
			throw new RuntimeException("设置用户关注时，传入的用户ID为空！");
		}
		//判断uid是否已经关注attentionId
		if(!checkIsAttention(uid, attentionUid)){
			Date date = new Date();
			String eachOtherFlag = "0";
			TwitterServiceImpl service = TwitterServiceImpl.getInstance();
			//判断是否被对方关注
			TAttention attention = service.FindAttentionByUid(attentionUid,uid);
			if(null != attention){//已被对方关注，更新对方的互相关注状态
				attention.setEachOtherFlag("1");
				attention.setUpdateUserId(uid);
				attention.setUpdateTime(date.getTime());
				service.UpdateAttentionByUid(attention);
				eachOtherFlag = "1";
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userId", uid);
			param.put("attentionUserId", attentionUid);
			param.put("eachOtherFlag", eachOtherFlag);
			param.put("attentionStatus", AttentionConstant.ATTENTIONSTATUS_ON);
			if(attentionType == AttentionType.normal){
				param.put("attentionType", AttentionConstant.ATTENTIONTYPE_NORMAL);
			}else if(attentionType == AttentionType.special) {
				param.put("attentionType", AttentionConstant.ATTENTIONTYPE_SPECIAL);
			}else {
				param.put("attentionType", AttentionConstant.ATTENTIONTYPE_NORMAL);
			}
			param.put("attentionAttr", attentionAttr);
			param.put("createUserId", uid);
			param.put("createTime", date.getTime());
			param.put("updateUserId", uid);
			param.put("updateTime", date.getTime());
			param.put("remark", "");
			service.AddAttentionUid(param);
			
		}else {
			logger.info(String.format("用户%s已经关注%s", uid,attentionUid));
		}
	}

	@Override
	public boolean checkIsAttention(Long uid,Long attentionUid) throws Exception{
		boolean result = false;
		TwitterServiceImpl service = TwitterServiceImpl.getInstance();
		TAttention attention = service.FindAttentionByUid(uid,
				attentionUid);
		if(null != attention){
			result = true;
		}
		return result;
	}

	@Override
	public String importUserData(String fileUrl) throws Exception {
		// TODO 导入用户的实现
		return null;
	}

	@Override
	public List<User> getByUid(Long[] uid) throws Exception {
		List<User> list = null;
		if(null != uid){
			BasicDBObject query = new BasicDBObject();
			query.put("uid",new BasicDBObject("$in", Arrays.asList(uid)));
			
			MongoDBSupport mongoDBSupport = MongoDBSupport.getInstance();
			DBCursor cur2 = null;
			try {
				cur2 = mongoDBSupport.queryCollection(Constant.T_USER_COLLECTION_NAME, query);
				if (cur2 != null) {
					Type type = new TypeToken<List<User>>(){}.getType();
					list = GsonUtil.gson.fromJson(cur2.toArray().toString(), type);
				}
			} catch (Exception e) {
				logger.error("", e);
				logger.error(e.getMessage());
				throw e;
			}finally{
				if(null != cur2){
					cur2.close();
				}
			}
			
			if(null != list && !list.isEmpty()){
				for (User u : list) {
					if(StringUtils.isEmpty(u.getState())){
						u.setState(UserConstant.STATE_ON);// 默认用户状态为启用
					}
				}
			}
			
		}
		return list;
	}
	@Override
	public Map<Long, User> getByUidForMap(Long[] uid) throws Exception {
		List<User> users = getByUid(uid);
		Map<Long, User> map = null;
		if(null != users && !users.isEmpty()){
			map = new HashMap<Long, User>();
			for (User user : users) {
				if(null != user && null != user.getUid()){
					map.put(user.getUid(), user);
				}
			}
		}
		return map;
	}

	@Override
	public void initData() throws Exception {
		MongoDBSupport mongoDBSupport= MongoDBSupport.getInstance();
		DBCursor dbCursor = null;
		try {
			mongoDBSupport.dropOneCollection(Constant.T_DEPT_COLLECTION_NAME);
			mongoDBSupport.dropOneCollection(Constant.T_USER_COLLECTION_NAME);
			//初始化单位数据
			dbCursor = mongoDBSupport.queryCollection(Constant.COM_POSIT);
			List<DBObject> dbObjects = new ArrayList<DBObject>();
			if(null != dbCursor){
				logger.info(dbCursor.toArray().toString());
				Type type = new TypeToken<List<Dept>>(){}.getType();
				List<Dept> depts = GsonUtil.gson.fromJson(dbCursor.toArray().toString(), type);
				if(null != depts && !depts.isEmpty()){
					for (Dept dept : depts) {
						dbObjects.add((BasicDBObject)JSON.parse(GsonUtil.gson.toJson(dept)));
					}
				}
				mongoDBSupport.saveList(dbObjects, Constant.T_DEPT_COLLECTION_NAME);
				dbCursor.close();
			}
			//初始化用户数据
			dbObjects.clear();
			dbCursor = mongoDBSupport.queryCollection(Constant.USER_DATA);
			if(null != dbCursor){
				logger.info(dbCursor.toArray().toString());
				Type type = new TypeToken<List<User>>(){}.getType();
				List<User> users = GsonUtil.gson.fromJson(dbCursor.toArray().toString(), type);
				if(null != users && !users.isEmpty()){
					for (User user : users) {
						dbObjects.add((BasicDBObject)JSON.parse(GsonUtil.gson.toJson(user)));
					}
				}
				mongoDBSupport.saveList(dbObjects, Constant.T_USER_COLLECTION_NAME);
				//将用户的状态全部改为启用
				BasicDBObject update = new BasicDBObject();
				update.put("state", UserConstant.STATE_ON);
				update.put("createUserId", Long.MAX_VALUE);
				update.put("createTime", new Date().getTime());
				update.put("updateUserId", Long.MAX_VALUE);
				update.put("updateTime", new Date().getTime());
				mongoDBSupport.updateCollection(Constant.T_USER_COLLECTION_NAME, new BasicDBObject(), new BasicDBObject("$set",update));
			}
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}finally{
			if(null != dbCursor){
				dbCursor.close();
			}
		}
	}

	
}
