package com.zte.im.service.impl;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;
import com.zte.im.bean.MyPageBean;
import com.zte.im.bean.SysLog;
import com.zte.im.bean.TAttention;
import com.zte.im.bean.TComment;
import com.zte.im.bean.TGroup;
import com.zte.im.bean.TMention;
import com.zte.im.bean.TSupport;
import com.zte.im.bean.TSysMessage;
import com.zte.im.bean.TTGroup;
import com.zte.im.bean.TToken;
import com.zte.im.bean.TTopic;
import com.zte.im.bean.TTransUser;
import com.zte.im.bean.TTwitter;
import com.zte.im.bean.User;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.ITwitterService;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.IDSequence;
import com.zte.im.util.MyJSON;
import com.zte.im.util.Page;

public class TwitterServiceImpl implements ITwitterService {

	private static Logger logger = LoggerFactory
			.getLogger(TwitterServiceImpl.class);

	private static String NO_PROGRESS = "00";//未处理的系统消息

	private static String DONE_SYSMESSAGE = "00";//未处理处理过的申请或者邀请的消息

	private static String TWITTER_PUBLISH = "00";// 微博状态：发布

	private static String TWITTER_PUBLIC = "01";// 微博可见范围

	private static String TWITTER_DELETE = "99";// 微博状态：删除

	private static String TWITTER_NOTE = "11";// 微博状态：草稿

	private static String TWITTER_ATTR_SYS = "02";// 系统微博
	/** 普通微博*/
	private static String TWITTER_ATTR_PUB = "01"; 

	private static TwitterServiceImpl service;

	private TwitterServiceImpl() {
		super();
	}

	public static TwitterServiceImpl getInstance() {
		if (service == null) {
			service = new TwitterServiceImpl();
		}
		return service;
	}

	@Override
	public List<TGroup> FindGroupByUserId(Long userId, Long createTime) {
		List<TGroup> groups = null;
		BasicDBObject param1 = new BasicDBObject();// 条件
		param1.put("userId", userId);
		DBCursor cur = MongoDBSupport.getInstance().queryCollection(
				Constant.TWITTER_GROUP_USER, param1);
		List<Long> gIds = new ArrayList<Long>();
		if (cur != null && cur.size() > 0) {
			while (cur.hasNext()) {
				BasicDBObject bdb = (BasicDBObject) cur.next();
				gIds.add(bdb.getLong("groupId"));
			}
			cur.close();
		}
		BasicDBObject param2 = new BasicDBObject();
		Long[] gIdArr = (Long[]) gIds.toArray(new Long[gIds.size()]);
		param2.put("groupId", new BasicDBObject(QueryOperators.IN, gIdArr));
		if(createTime > 0){
			param2.put("createTime", new BasicDBObject(QueryOperators.GT, createTime));
		}
		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("createTime", -1);
		DBCursor cur2 = MongoDBSupport.getInstance().queryCollectionByParam(Constant.TWITTER_GROUP, param2, sort);
		if (cur2 != null && cur2.size() > 0) {
			Type type = new TypeToken<List<TGroup>>() {
			}.getType();
			groups = GsonUtil.gson.fromJson(cur2.toArray().toString(), type);
			cur2.close();
		}
		return groups;
	}

	@Override
	public void collectTwitter(Map<String, Object> param, String type) {
		BasicDBObject query = new BasicDBObject();
		query.put("userId", param.get("userId"));
		query.put("twitterId", param.get("twitterId"));
		if (Constant.TWITTER_ISCOLLECT.equals(type)) {

			DBObject object = new BasicDBObject(param);
			long num = MongoDBSupport.getInstance().getCount(
					Constant.TWITTER_COLLECT, query);
			if (num == 0) {
				MongoDBSupport.getInstance().save(object,
						Constant.TWITTER_COLLECT);
			}
		} else if (Constant.TWITTER_NOTCOLLECT.equals(type)) {
			MongoDBSupport.getInstance().removeDBObjects(
					Constant.TWITTER_COLLECT, query);
		}
	}

	@Override
	public void transTwitter(Map<String, Object> param) {
		DBObject dbObject = new BasicDBObject(param);
		MongoDBSupport.getInstance()
		.save(dbObject, Constant.TWITTER_TRANS_USER);
	}

	@Override
	public void supportTwitter(Map<String, Object> param) {
		DBObject dbObject = new BasicDBObject(param);
		MongoDBSupport.getInstance().save(dbObject, Constant.TWITTER_SUPPORT);
	}

	@Override
	public void updateTwitterNum(Long twitterId, String typeOper, Long numValue) {
		DBObject dbObject = new BasicDBObject();
		dbObject.put("twitterId", twitterId);
		DBObject setValue = new BasicDBObject();
		setValue.put(typeOper, numValue);
		setValue.put("updateTime", new Date().getTime());
		DBObject upsertValue = new BasicDBObject("$set", setValue);
		MongoDBSupport.getInstance().updateCollection(Constant.TWITTER_TWITTER,
				dbObject, upsertValue);
	}

	@Override
	public TGroup findGroupDetail(Long groupId) {
		TGroup group = null;
		BasicDBObject param = new BasicDBObject();
		param.put("groupId", groupId);
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.TWITTER_GROUP, param, null);
		if (obj != null) {
			group = GsonUtil.gson.fromJson(obj.toString(), TGroup.class);
		}
		if (group != null) {
			User user = UserServiceImpl.getInstance().getUserbyid(
					group.getCreateUserId());
			if (user != null) {
				group.setUserName(user.getName());
				group.setBigpicurl(user.getBigpicurl());
				group.setMinipicurl(user.getMinipicurl());
			}
		}
		return group;
	}

	/**
	 * 获取某用户发布的微博信息
	 * 
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<TTwitter> findTwittersByUserId(Long userId) {
		List<TTwitter> twitters = null;
		BasicDBObject param1 = new BasicDBObject();// 条件
		param1.put("userId", userId);
		param1.put("twitterStatus", TWITTER_PUBLISH);
		DBCursor cur = MongoDBSupport.getInstance().queryCollection(
				Constant.TWITTER_TWITTER, param1);
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<TTwitter>>() {
			}.getType();
			twitters = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
			if (twitters != null) {
				BasicDBObject p = new BasicDBObject();// 条件
				p.put("userId", userId);
				DBCursor c = MongoDBSupport.getInstance().queryCollection(
						Constant.TWITTER_COLLECT, p);
				List<Long> tids = new ArrayList<Long>();
				if (c != null && c.size() > 0) {
					while (c.hasNext()) {
						BasicDBObject bdb = (BasicDBObject) c.next();
						tids.add(bdb.getLong("twitterId"));
					}
				}
				// 获取用户对于点赞微博id
				DBCursor cSupport = MongoDBSupport.getInstance()
						.queryCollection(Constant.TWITTER_SUPPORT, p);
				List<Long> tidsupport = new ArrayList<Long>();
				if (cSupport != null && cSupport.size() > 0) {
					while (cSupport.hasNext()) {
						BasicDBObject bdb = (BasicDBObject) cSupport.next();
						tidsupport.add(bdb.getLong("twitterId"));
					}
				}
				Iterator<TTwitter> it = twitters.iterator();
				while (it.hasNext()) {
					TTwitter twitter = it.next();
					User user = UserServiceImpl.getInstance().getUserbyid(
							twitter.getUserId());
					if (user == null) {
						it.remove();
					} else {
						twitter.setUserName(user.getName());
						twitter.setBigpicurl(user.getBigpicurl());
						twitter.setMinipicurl(user.getMinipicurl());
						getSourceTwitter(twitter, tids, tidsupport);
					}
				}
			}
		}
		return twitters;
	}

	/**
	 * 获取某用户被评论信息
	 * 
	 * @param userId
	 * @param page
	 * @return
	 */
	@Override
	public List<TComment> findUserComments(Long becomentUserId, MyPageBean pageBean) {
		List<TComment> comments = null;
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("becomentUserId", becomentUserId); 
		// 刷新
		if (null != pageBean.getPointId()) {
			if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
					.getQueryType())) { 
				param.put("commentId", new BasicDBObject(QueryOperators.GT,
						pageBean.getPointId()));
			}
			// 翻页
			else if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
					.getQueryType())) {
				param.put("commentId", new BasicDBObject(QueryOperators.LT,
						pageBean.getPointId()));
			}
		}
		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("commentId", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.TWITTER_COMMENT, param, sort); 
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<TComment>>() {
			}.getType();
			comments = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}

		List<TComment> returnList = new ArrayList<TComment>();
		if (comments != null) {
			Iterator<TComment> it = comments.iterator();
			int count = 0;
			while (it.hasNext()) {
				TComment comment = it.next();

				returnList.add(comment);
				count++;
				if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
						.getQueryType()) && count >= pageBean.getPageSize()) {
					break;
				}
			}
		}
		return returnList;
	}

	/**
	 * 获取某用户微博的评论信息
	 * 
	 * @param tIdArr
	 * @param pageBean
	 * @return
	 */
	@Override
	public List<TComment> getCommentsByTid(Long[] tIdArr, MyPageBean pageBean) {
		List<TComment> comments = null;
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("twitterId", new BasicDBObject(QueryOperators.IN, tIdArr));

		// 刷新
		if (null != pageBean.getPointId()) {
			if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
					.getQueryType())) {
				param.put("commentId", new BasicDBObject(QueryOperators.GT,
						pageBean.getPointId()));
			}
			// 翻页
			else if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
					.getQueryType())) {
				param.put("commentId", new BasicDBObject(QueryOperators.LT,
						pageBean.getPointId()));
			}
		}

		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("commentId", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.TWITTER_COMMENT, param, sort);

		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<TComment>>() {
			}.getType();
			comments = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}

		List<TComment> returnList = new ArrayList<TComment>();
		if (comments != null) {
			Iterator<TComment> it = comments.iterator();
			int count = 0;
			while (it.hasNext()) {
				TComment comment = it.next();
				returnList.add(comment);
				count++;
				if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
						.getQueryType()) && count >= pageBean.getPageSize()) {
					break;
				}
			}
		}
		return returnList;
	}

	@Override
	public List<User> findUsersByGroupId(Long groupId) {
		List<User> users = new ArrayList<User>();
		List<Long> userIds = new ArrayList<Long>();
		BasicDBObject param1 = new BasicDBObject();// 条件
		param1.put("groupId", groupId);
		DBCursor cur = MongoDBSupport.getInstance().queryCollection(
				Constant.TWITTER_GROUP_USER, param1);
		if (cur != null && cur.size() > 0) {
			while (cur.hasNext()) {
				BasicDBObject bdb = (BasicDBObject) cur.next();
				userIds.add(bdb.getLong("userId"));
			}
		}
		if (userIds.size() > 0) {
			Iterator<Long> it = userIds.iterator();
			while (it.hasNext()) {
				Long uid = it.next();
				User user = UserServiceImpl.getInstance().getUserbyid(uid);
				if (user != null) {
					User u = new User();
					u.setUid(user.getUid());
					u.setName(user.getName());
					u.setGname(user.getGname());
					u.setNickname(user.getNickname());
					u.setCname(user.getCname());
					u.setJid(user.getJid());
					u.setPost(user.getPost());
					u.setMinipicurl(user.getMinipicurl());
					u.setBigpicurl(user.getBigpicurl());
					u.setAutograph(user.getAutograph());
					users.add(u);
				}
			}
		}
		return users;
	}

	@Override
	public void delTwitter(Long twitterId) {
		DBObject dbObject = new BasicDBObject();
		dbObject.put("twitterId", twitterId);
		DBObject setValue = new BasicDBObject();
		setValue.put("twitterStatus", TWITTER_DELETE);
		DBObject upsertValue = new BasicDBObject("$set", setValue);
		MongoDBSupport.getInstance().updateCollection(Constant.TWITTER_TWITTER,
				dbObject, upsertValue);
	}

	@Override
	public void delTwitterGroup(Long groupId) {
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("groupId", groupId);
		MongoDBSupport.getInstance().removeDBObjects(Constant.TWITTER_GROUP,
				dbObject);
		MongoDBSupport.getInstance().removeDBObjects(
				Constant.TWITTER_GROUP_USER, dbObject);
	}

	@Override
	public void removeGroupUser(Long groupId, Long userId) {
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("groupId", groupId);
		dbObject.put("userId", userId);
		MongoDBSupport.getInstance().removeDBObjects(
				Constant.TWITTER_GROUP_USER, dbObject);
	}

	@Override
	public void inviteGroupUser(Map<String, Object> param) {
		DBObject dbObject = new BasicDBObject(param);
		MongoDBSupport.getInstance()
		.save(dbObject, Constant.TWITTER_GROUP_USER);
	}

	@Override
	public TTwitter findTwitter(Long twitterId) {
		TTwitter t = null;
		BasicDBObject param = new BasicDBObject();
		param.put("twitterId", twitterId);
		param.put("twitterStatus", TWITTER_PUBLISH);
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.TWITTER_TWITTER, param, null);
		if (obj != null) {
			t = GsonUtil.gson.fromJson(obj.toString(), TTwitter.class); 

		}
		return t;
	}

	@Override
	public TTwitter findTwitterDetail(Long twitterId, Long userId) {
		TTwitter t = null;
		BasicDBObject param = new BasicDBObject();
		param.put("twitterId", twitterId);
		param.put("twitterStatus", TWITTER_PUBLISH);
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.TWITTER_TWITTER, param, null);
		if (obj != null) {
			t = GsonUtil.gson.fromJson(obj.toString(), TTwitter.class); 
			User user = UserServiceImpl.getInstance()
					.getUserbyid(t.getUserId());
			if (user == null) {
				t.setUserName("用户已被删除！");
				t.setBigpicurl("");
				t.setMinipicurl("");
			} else {
				t.setUserName(user.getName());
				t.setBigpicurl(user.getBigpicurl());
				t.setMinipicurl(user.getMinipicurl());
			}
			BasicDBObject p = new BasicDBObject();// 条件
			p.put("userId", userId);
			DBCursor c = MongoDBSupport.getInstance().queryCollection(
					Constant.TWITTER_COLLECT, p);
			List<Long> tids = new ArrayList<Long>();
			if (c != null && c.size() > 0) {
				while (c.hasNext()) {
					BasicDBObject bdb = (BasicDBObject) c.next();
					tids.add(bdb.getLong("twitterId"));
				}
			}
			// 获取用户对于点赞微博id
			DBCursor cSupport = MongoDBSupport.getInstance().queryCollection(
					Constant.TWITTER_SUPPORT, p);
			List<Long> tidsupport = new ArrayList<Long>();
			if (cSupport != null && cSupport.size() > 0) {
				while (cSupport.hasNext()) {
					BasicDBObject bdb = (BasicDBObject) cSupport.next();
					tidsupport.add(bdb.getLong("twitterId"));
				}
			}
			getSourceTwitter(t, tids, tidsupport);
		}
		return t;
	}

	@Override
	public void addTwitterGroup(Map<String, Object> param) {
		DBObject dbObject = new BasicDBObject(param);
		MongoDBSupport.getInstance().save(dbObject, Constant.TWITTER_GROUP);
	}

	/**
	 * 获取新消息
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public List<TSysMessage> findMsgByUserId(Long userId) {
		List<TSysMessage> messages = null;
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("sendUserId", userId);
		param.put("receiveUserId", new BasicDBObject(QueryOperators.OR, userId));
		param.put("messageStatus", new BasicDBObject(QueryOperators.NE, DONE_SYSMESSAGE));
		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("messageId", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.TWITTER_SYS_MESSAGE, param, sort);

		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<TSysMessage>>() {
			}.getType();
			messages = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		return messages;
	}

	/**
	 * 处理系统消息
	 * 
	 * @param messageId
	 * @param operType
	 * @param userId
	 */
	@Override
	public void operSysMsg(Long messageId, String operType, Long userId) {
		BasicDBObject query = new BasicDBObject();
		query.put("messageId", messageId);
		BasicDBObject set = new BasicDBObject();
		set.put("messageStatus", operType);
		Date date = new Date();
		set.put("updateTime", date.getTime());
		set.put("updateUserId", userId);
		BasicDBObject param = new BasicDBObject();
		param.put("$set", set);
		MongoDBSupport.getInstance().updateCollection(
				Constant.TWITTER_SYS_MESSAGE, query, param);
	}

	/**
	 * 根据消息Id获取系统消息
	 * 
	 * @param messageId
	 * @return
	 */
	@Override
	public TSysMessage getSysMessage(Long messageId) {
		TSysMessage msg = null;
		BasicDBObject param = new BasicDBObject();
		param.put("messageId", messageId);
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.TWITTER_SYS_MESSAGE, param, null);
		if (obj != null) {
			msg = GsonUtil.gson.fromJson(obj.toString(), TSysMessage.class);
		}
		return msg;
	}

	/**
	 * 获取某用户微博的点赞信息
	 * 
	 * @param tIdArr
	 * @param page
	 * @return
	 */
	@Override
	public List<TSupport> getSupportsByTid(Long[] tIdArr, MyPageBean pageBean) {
		List<TSupport> supports = null;
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("twitterId", new BasicDBObject(QueryOperators.IN, tIdArr));
		// 刷新
		if (null != pageBean.getPointId()) {
			if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
					.getQueryType())) {
				param.put("supportId", new BasicDBObject(QueryOperators.GT,
						pageBean.getPointId()));
			}
			// 翻页
			else if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
					.getQueryType())) {
				param.put("supportId", new BasicDBObject(QueryOperators.LT,
						pageBean.getPointId()));
			}
		}

		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("supportId", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.TWITTER_SUPPORT, param, sort);

		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<TSupport>>() {
			}.getType();
			supports = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		List<TSupport> returnList = new ArrayList<TSupport>();
		if (supports != null) {
			Iterator<TSupport> it = supports.iterator();
			int count = 0;
			while (it.hasNext()) {
				TSupport support = it.next();
				returnList.add(support);
				count++;
				if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
						.getQueryType()) && count >= pageBean.getPageSize()) {
					break;
				}
			}
		}
		return returnList;
	}

	/**
	 * 获取@我的信息
	 * 
	 * @param userId
	 * @param page
	 * @return
	 */
	@Override
	public List<TMention> getMentionByUserId(Long userId, MyPageBean pageBean) {
		List<TMention> mentions = null;
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("mentionUserId", userId);

		// 刷新
		if (null != pageBean.getPointId()) {
			if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
					.getQueryType())) {
				param.put("mentionId", new BasicDBObject(QueryOperators.GT,
						pageBean.getPointId()));
			}
			// 翻页
			else if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
					.getQueryType())) {
				param.put("mentionId", new BasicDBObject(QueryOperators.LT,
						pageBean.getPointId()));
			}
		}

		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("mentionId", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.TWITTER_MENTION, param, sort);

		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<TMention>>() {
			}.getType();
			mentions = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		List<TMention> returnList = new ArrayList<TMention>();
		if (mentions != null) {
			Iterator<TMention> it = mentions.iterator();
			int count = 0;
			while (it.hasNext()) {
				TMention mention = it.next();
				if(mention.getTwitterId()!=null){
					returnList.add(mention);
					count++;
					if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
							.getQueryType()) && count >= pageBean.getPageSize()) {
						break;
					}
				}
			}
		}
		return returnList;
	}

	/**
	 * 根据id查询评论
	 * 
	 * @param commentId
	 * @return
	 */
	@Override
	public TComment findCommentDetail(Long commentId) {
		TComment comment = null;
		BasicDBObject param = new BasicDBObject();
		param.put("commentId", commentId);
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.TWITTER_COMMENT, param, null);
		if (obj != null) {
			comment = GsonUtil.gson.fromJson(obj.toString(), TComment.class);
		}
		return comment;
	}

	/**
	 * 根据id查询赞信息
	 * 
	 * @param supportId
	 * @return
	 */
	@Override
	public TSupport findSupportDetail(Long supportId) {
		TSupport support = null;
		BasicDBObject param = new BasicDBObject();
		param.put("supportId", supportId);
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.TWITTER_SUPPORT, param, null);
		if (obj != null) {
			support = GsonUtil.gson.fromJson(obj.toString(), TSupport.class);
		}
		return support;
	}

	/**
	 * 评论微博
	 */
	@Override
	public void commentTwitter(Map<String, Object> param) {
		DBObject dbObject = new BasicDBObject(param);
		MongoDBSupport.getInstance().save(dbObject, Constant.TWITTER_COMMENT);
	}

	/**
	 * 获取微博列表
	 * 
	 * @param uIdArr
	 * @param page
	 * @return
	 */
	@Override
	public List<TTwitter> getTwitters(Long userId, Long[] uIdArr,
			MyPageBean pageBean) {
		List<TTwitter> twitters = null;
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("twitterStatus", TWITTER_PUBLISH);
		param.put("userId", new BasicDBObject(QueryOperators.IN, uIdArr));
		// 刷新
		if (null != pageBean.getPointId()) {
			if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
					.getQueryType())) {
				param.put("twitterId", new BasicDBObject(QueryOperators.GT,
						pageBean.getPointId()));
			}
			// 翻页
			else if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
					.getQueryType())) {
				param.put("twitterId", new BasicDBObject(QueryOperators.LT,
						pageBean.getPointId()));
			}
		}
		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("twitterId", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.TWITTER_TWITTER, param, sort);
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<TTwitter>>() {
			}.getType();
			twitters = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		List<TTwitter> returnList = new ArrayList<TTwitter>();
		if (twitters != null) {
			BasicDBObject p = new BasicDBObject();// 条件
			p.put("userId", userId);
			DBCursor c = MongoDBSupport.getInstance().queryCollection(
					Constant.TWITTER_COLLECT, p);
			List<Long> tids = new ArrayList<Long>();
			if (c != null && c.size() > 0) {
				while (c.hasNext()) {
					BasicDBObject bdb = (BasicDBObject) c.next();
					tids.add(bdb.getLong("twitterId"));
				}
			}
			// 获取用户对于点赞微博id
			DBCursor cSupport = MongoDBSupport.getInstance().queryCollection(
					Constant.TWITTER_SUPPORT, p);
			List<Long> tidsupport = new ArrayList<Long>();
			if (cSupport != null && cSupport.size() > 0) {
				while (cSupport.hasNext()) {
					BasicDBObject bdb = (BasicDBObject) cSupport.next();
					tidsupport.add(bdb.getLong("twitterId"));
				}
			}
			Iterator<TTwitter> it = twitters.iterator();
			int count = 0;
			while (it.hasNext()) {
				TTwitter twitter = it.next();
				User user = UserServiceImpl.getInstance().getUserbyid(
						twitter.getUserId());
				if (user != null) { 
					twitter.setUserName(user.getName());
					twitter.setBigpicurl(user.getBigpicurl());
					twitter.setMinipicurl(user.getMinipicurl());
					getSourceTwitter(twitter, tids, tidsupport);
					returnList.add(twitter);
					count++;
				}
				if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
						.getQueryType()) && count >= pageBean.getPageSize()) {
					break;
				}
			}
		}
		return returnList;
	}

	/**
	 * 获取微博列表
	 * 
	 * @param uIdArr
	 * @param pageBean
	 * @return
	 */
	@Override
	public List<TTwitter> getTwittersNew(Long userId, Long[] uIdArr,
			MyPageBean pageBean) {
		List<TTwitter> twitters = null;
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("twitterStatus", TWITTER_PUBLISH);
		// param.put("visibleRange", TWITTER_PUBLIC);
		param.put("userId", new BasicDBObject(QueryOperators.IN, uIdArr));

		// 刷新
		if (null != pageBean.getPointId()) {
			if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
					.getQueryType())) {
				param.put("twitterId", new BasicDBObject(QueryOperators.GT,
						pageBean.getPointId()));
			}
			// 翻页
			else if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
					.getQueryType())) {
				param.put("twitterId", new BasicDBObject(QueryOperators.LT,
						pageBean.getPointId()));
			}
		}

		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("twitterId", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.TWITTER_TWITTER, param, sort);
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<TTwitter>>() {
			}.getType();
			twitters = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		List<TTwitter> returnList = new ArrayList<TTwitter>();
		if (twitters != null) {
			BasicDBObject p = new BasicDBObject();// 条件
			p.put("userId", userId);
			DBCursor c = MongoDBSupport.getInstance().queryCollection(
					Constant.TWITTER_COLLECT, p);
			List<Long> tids = new ArrayList<Long>();
			if (c != null && c.size() > 0) {
				while (c.hasNext()) {
					BasicDBObject bdb = (BasicDBObject) c.next();
					tids.add(bdb.getLong("twitterId"));
				}
			}
			// 获取用户对于点赞微博id
			DBCursor cSupport = MongoDBSupport.getInstance().queryCollection(
					Constant.TWITTER_SUPPORT, p);
			List<Long> tidsupport = new ArrayList<Long>();
			if (cSupport != null && cSupport.size() > 0) {
				while (cSupport.hasNext()) {
					BasicDBObject bdb = (BasicDBObject) cSupport.next();
					tidsupport.add(bdb.getLong("twitterId"));
				}
			}
			Iterator<TTwitter> it = twitters.iterator();
			int count = 0;
			while (it.hasNext()) {
				TTwitter twitter = it.next();
				User user = UserServiceImpl.getInstance().getUserbyid(
						twitter.getUserId());
				if (user == null) {
					it.remove();
				} else {
					twitter.setUserName(user.getName());
					twitter.setBigpicurl(user.getBigpicurl());
					twitter.setMinipicurl(user.getMinipicurl());
					getSourceTwitter(twitter, tids, tidsupport);
					returnList.add(twitter);
					count++;
				}
				if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
						.getQueryType()) && count >= pageBean.getPageSize()) {
					break;
				}
			}
		}
		return returnList;
	}

	/**
	 * dec 获取部门下微博
	 * 
	 * @param deptId
	 * @return
	 */
	@Override
	public List<Long> getTwitterIdByDept(Long deptId) {
		List<Long> twitterids = new ArrayList<Long>();
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("deptId", deptId);
		DBCursor cur = MongoDBSupport.getInstance().queryCollection(
				Constant.TWITTER_DEPT, param);
		if (cur != null && cur.size() > 0) {
			while (cur.hasNext()) {
				BasicDBObject bdb = (BasicDBObject) cur.next();
				twitterids.add(bdb.getLong("twitterId"));
			}
		}
		return twitterids;
	}

	/**
	 * dec 获取圈子下下微博id
	 * 
	 * @param groupId
	 * @return
	 */
	@Override
	public List<Long> getTwitterIdByGroups(Long[] groupId) {
		List<Long> twitterids = new ArrayList<Long>();
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("groupId", new BasicDBObject(QueryOperators.IN, groupId));
		DBCursor cur = MongoDBSupport.getInstance().queryCollection(
				Constant.TWITTER_GROUP_ID, param);
		if (cur != null && cur.size() > 0) {
			while (cur.hasNext()) {
				BasicDBObject bdb = (BasicDBObject) cur.next();
				twitterids.add(bdb.getLong("twitterId"));
			}
		}
		return twitterids;
	}

	
	/**
	 * dec 获取邀请人下发的微博id
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public List<Long> getTwitterIdByStaff(Long  userId) {
		List<Long> twitterids = new ArrayList<Long>();
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("staffId", userId);
		DBCursor cur = MongoDBSupport.getInstance().queryCollection(
				Constant.TWITTER_STAFF_ID, param);
		if (cur != null && cur.size() > 0) {
			while (cur.hasNext()) {
				BasicDBObject bdb = (BasicDBObject) cur.next();
				twitterids.add(bdb.getLong("twitterId"));
			}
		}
		return twitterids;
	}
	
	
	@Override
	public List<Long> getGroupIdByUserId(Long userId) {
		List<Long> groupids = new ArrayList<Long>();
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("userId", userId);
		DBCursor cur = MongoDBSupport.getInstance().queryCollection(
				Constant.TWITTER_GROUP_USER, param);
		if (cur != null && cur.size() > 0) {
			while (cur.hasNext()) {
				BasicDBObject bdb = (BasicDBObject) cur.next();
				groupids.add(bdb.getLong("groupId"));
			}
		}
		return groupids;
	}
	 
	
	/**
	 * 首页微博列表（关注的微博和自己的微博）
	 * 
	 * @param uIdArr
	 * @param pageBean
	 * @return
	 */
	@Override
	public List<TTwitter> getTwittersIdx(Long userId, Long[] uIdArr,
			Long[] twitterids, MyPageBean pageBean) {
		List<TTwitter> twitters = null;
		BasicDBObject param1 = new BasicDBObject();// 条件
		param1.put("userId", new BasicDBObject(QueryOperators.IN, uIdArr));
		BasicDBList values = new BasicDBList();
		values.add(param1);
		values.add(new BasicDBObject("userId", userId));
		BasicDBObject tparam = new BasicDBObject();
		tparam.put("twitterId",
				new BasicDBObject(QueryOperators.IN, twitterids));
		values.add(tparam);

		BasicDBObject queryCondition = new BasicDBObject();
		queryCondition.put(QueryOperators.OR, values);
		queryCondition.put("twitterStatus", TWITTER_PUBLISH);
		// 查询普通微博
		queryCondition.put("twitterAttr", TWITTER_ATTR_PUB);
		// queryCondition.put("visibleRange", TWITTER_PUBLIC);
		// 刷新
		if (null != pageBean.getPointId()) {
			if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
					.getQueryType())) {
				queryCondition.put("twitterId", new BasicDBObject(
						QueryOperators.GT, pageBean.getPointId()));
			}
			// 翻页
			else if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
					.getQueryType())) {
				queryCondition.put("twitterId", new BasicDBObject(
						QueryOperators.LT, pageBean.getPointId()));
			}
		}

		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("twitterId", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.TWITTER_TWITTER, queryCondition, sort);
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<TTwitter>>() {
			}.getType();
			twitters = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		List<TTwitter> returnList = new ArrayList<TTwitter>();
		if (twitters != null) {
			BasicDBObject p = new BasicDBObject();// 条件
			p.put("userId", userId);
			DBCursor c = MongoDBSupport.getInstance().queryCollection(
					Constant.TWITTER_COLLECT, p);
			List<Long> tids = new ArrayList<Long>();
			if (c != null && c.size() > 0) {
				while (c.hasNext()) {
					BasicDBObject bdb = (BasicDBObject) c.next();
					tids.add(bdb.getLong("twitterId"));
				}
			}
			// 获取用户对于点赞微博id
			DBCursor cSupport = MongoDBSupport.getInstance().queryCollection(
					Constant.TWITTER_SUPPORT, p);
			List<Long> tidsupport = new ArrayList<Long>();
			if (cSupport != null && cSupport.size() > 0) {
				while (cSupport.hasNext()) {
					BasicDBObject bdb = (BasicDBObject) cSupport.next();
					tidsupport.add(bdb.getLong("twitterId"));
				}
			}
			Iterator<TTwitter> it = twitters.iterator();
			int count = 0;
			while (it.hasNext()) {
				TTwitter twitter = it.next();
				User user = UserServiceImpl.getInstance().getUserbyid(
						twitter.getUserId());
				if (user == null) {
					it.remove();
				} else {
					twitter.setUserName(user.getName());
					twitter.setBigpicurl(user.getBigpicurl());
					twitter.setMinipicurl(user.getMinipicurl());
					getSourceTwitter(twitter, tids, tidsupport);
					returnList.add(twitter);
					count++;
				}
				if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
						.getQueryType()) && count >= pageBean.getPageSize()) {
					break;
				}
			}
		}
		return returnList;
	}

	
	/**
	 * 首页微博列表2 
	 * 
	 * @param uIdArr
	 * @param pageBean
	 * @return
	 */
	@Override
	public List<TTwitter> getTwittersIdx2(Long userId, Long[] uIdArr,
			Long[] twitterids, MyPageBean pageBean) {
		List<TTwitter> twitters = null;
		BasicDBObject param1 = new BasicDBObject();// 条件
		param1.put("userId", new BasicDBObject(QueryOperators.IN, uIdArr));
		BasicDBList values = new BasicDBList();
		values.add(param1);
		values.add(new BasicDBObject("userId", userId));
		BasicDBObject tparam = new BasicDBObject();
		tparam.put("twitterId",
				new BasicDBObject(QueryOperators.IN, twitterids));
		values.add(tparam);
		values.add(new BasicDBObject("twitterAttr", TWITTER_ATTR_SYS));
		BasicDBObject queryCondition = new BasicDBObject();
		queryCondition.put(QueryOperators.OR, values);
		queryCondition.put("twitterStatus", TWITTER_PUBLISH); 
		// 刷新
		if (null != pageBean.getPointId()) {
			if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
					.getQueryType())) {
				queryCondition.put("twitterId", new BasicDBObject(
						QueryOperators.GT, pageBean.getPointId()));
			}
			// 翻页
			else if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
					.getQueryType())) {
				queryCondition.put("twitterId", new BasicDBObject(
						QueryOperators.LT, pageBean.getPointId()));
			}
		}

		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("twitterId", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.TWITTER_TWITTER, queryCondition, sort);
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<TTwitter>>() {
			}.getType();
			twitters = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		List<TTwitter> returnList = new ArrayList<TTwitter>();
		if (twitters != null) {
			BasicDBObject p = new BasicDBObject();// 条件
			p.put("userId", userId);
			DBCursor c = MongoDBSupport.getInstance().queryCollection(
					Constant.TWITTER_COLLECT, p);
			List<Long> tids = new ArrayList<Long>();
			if (c != null && c.size() > 0) {
				while (c.hasNext()) {
					BasicDBObject bdb = (BasicDBObject) c.next();
					tids.add(bdb.getLong("twitterId"));
				}
			}
			// 获取用户对于点赞微博id
			DBCursor cSupport = MongoDBSupport.getInstance().queryCollection(
					Constant.TWITTER_SUPPORT, p);
			List<Long> tidsupport = new ArrayList<Long>();
			if (cSupport != null && cSupport.size() > 0) {
				while (cSupport.hasNext()) {
					BasicDBObject bdb = (BasicDBObject) cSupport.next();
					tidsupport.add(bdb.getLong("twitterId"));
				}
			}
			Iterator<TTwitter> it = twitters.iterator();
			int count = 0;
			while (it.hasNext()) {
				TTwitter twitter = it.next();
				User user = UserServiceImpl.getInstance().getUserbyid(
						twitter.getUserId());
				if (user == null) {
					it.remove();
				} else {
					twitter.setUserName(user.getName());
					twitter.setBigpicurl(user.getBigpicurl());
					twitter.setMinipicurl(user.getMinipicurl());
					getSourceTwitter(twitter, tids, tidsupport);
					returnList.add(twitter);
					count++;
				}
				if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
						.getQueryType()) && count >= pageBean.getPageSize()) {
					break;
				}
			}
		}
		return returnList;
	}
	
	/**
	 * 获取user关注的用户ID
	 * 
	 * @param userId
	 * @param attentionType
	 * @return
	 */
	@Override
	public List<Long> getAttentionUids(Long userId, String attentionType) {
		List<Long> attentionUids = new ArrayList<Long>();
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("userId", userId);
		if (null != attentionType) {
			param.put("attentionType", attentionType);
		}
		DBCursor cur = MongoDBSupport.getInstance().queryCollection(
				Constant.TWITTER_ATTENTION, param);
		if (cur != null && cur.size() > 0) {
			while (cur.hasNext()) {
				BasicDBObject bdb = (BasicDBObject) cur.next();
				attentionUids.add(bdb.getLong("attentionUserId"));
			}
		}
		return attentionUids;
	}

	/**
	 * 查询所属圈子ID
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public List<Long> findGroupIdsByUid(Long userId) {
		List<Long> groupIds = new ArrayList<Long>();
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("userId", userId);
		DBCursor cur = MongoDBSupport.getInstance().queryCollection(
				Constant.TWITTER_GROUP_USER, param);
		if (cur != null && cur.size() > 0) {
			while (cur.hasNext()) {
				BasicDBObject bdb = (BasicDBObject) cur.next();
				groupIds.add(bdb.getLong("groupId"));
			}
		}
		return groupIds;
	}

	/**
	 * 查询群组中的所有用户Id
	 * 
	 * @param gIdArr
	 * @return
	 */
	@Override
	public List<Long> getUidsByGroupIds(Long[] gIdArr) {
		List<Long> userIds = new ArrayList<Long>();
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("groupId", new BasicDBObject(QueryOperators.IN, gIdArr));
		DBCursor cur = MongoDBSupport.getInstance().queryCollection(
				Constant.TWITTER_GROUP_USER, param);
		if (cur != null && cur.size() > 0) {
			while (cur.hasNext()) {
				BasicDBObject bdb = (BasicDBObject) cur.next();
				userIds.add(bdb.getLong("userId"));
			}
		}
		return userIds;
	}

	@Override
	public List<TTwitter> getTwittersByKw(Long userId, String kw,
			String twitterType, MyPageBean pageBean) {
		List<TTwitter> twitters = null;
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("twitterStatus", TWITTER_PUBLISH);
	    param.put("visibleRange", TWITTER_PUBLIC);
		if (kw != null && !"".equals(kw)) {
			Pattern pattern = Pattern.compile("^.*" + kw + ".*$");
			param.put("searchContent", pattern);
		}
		if (twitterType != null && !"".equals(twitterType)) {
			param.put("twitterType", twitterType);
		}
		// 刷新
		//		if (null != pageBean.getPointId()) {
		//			if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
		//					.getQueryType())) {
		//				param.put("forwardNum", new BasicDBObject(QueryOperators.GT,
		//						pageBean.getPointId()));
		//			}
		//			// 翻页
		//			else if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
		//					.getQueryType())) {
		//				param.put("forwardNum", new BasicDBObject(QueryOperators.LT,
		//						pageBean.getPointId()));
		//			}
		//		}
		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("commentNum", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.TWITTER_TWITTER, param, sort);
		int pageNum = pageBean.getPageNum();
		int pageSize = pageBean.getPageSize();

		if (cur != null && cur.size() > 0) {

			cur.skip((pageNum-1)*pageSize).limit(30);

			Type type = new TypeToken<List<TTwitter>>() {
			}.getType();
			twitters = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		List<TTwitter> returnList = new ArrayList<TTwitter>();
		if (twitters != null) {
			BasicDBObject p = new BasicDBObject();// 条件
			p.put("userId", userId);
			DBCursor c = MongoDBSupport.getInstance().queryCollection(
					Constant.TWITTER_COLLECT, p);
			List<Long> tids = new ArrayList<Long>();
			if (c != null && c.size() > 0) {
				while (c.hasNext()) {
					BasicDBObject bdb = (BasicDBObject) c.next();
					tids.add(bdb.getLong("twitterId"));
				}
			}
			// 获取用户对于点赞微博id
			DBCursor cSupport = MongoDBSupport.getInstance().queryCollection(
					Constant.TWITTER_SUPPORT, p);
			List<Long> tidsupport = new ArrayList<Long>();
			if (cSupport != null && cSupport.size() > 0) {
				while (cSupport.hasNext()) {
					BasicDBObject bdb = (BasicDBObject) cSupport.next();
					tidsupport.add(bdb.getLong("twitterId"));
				}
			}
			Iterator<TTwitter> it = twitters.iterator();
			int count = 0;
			while (it.hasNext()) {
				TTwitter twitter = it.next();
				User user = UserServiceImpl.getInstance().getUserbyid(
						twitter.getUserId()); 
				if (user == null) {
					it.remove();
				} else {
					twitter.setUserName(user.getName());
					twitter.setMinipicurl(user.getMinipicurl());
					twitter.setBigpicurl(user.getBigpicurl());
					getSourceTwitter(twitter, tids, tidsupport);
					returnList.add(twitter);
					count++;
					if(count==pageSize){
						return returnList;
					}
				} 
			}
		}
		return returnList;
	}

	@Override
	public List<TTwitter> getTwittersByKw2(Long userId, String kw,
			String twitterType, MyPageBean pageBean) {
		List<TTwitter> twitters = null;
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("twitterStatus", TWITTER_PUBLISH);
		// param.put("visibleRange", TWITTER_PUBLIC);
		if (kw != null && !"".equals(kw)) {
			Pattern pattern = Pattern.compile("^.*" + kw + ".*$");
			param.put("twitterContent", pattern);
		}
		if (twitterType != null && !"".equals(twitterType)) {
			param.put("twitterType", twitterType);
		}
		// 刷新
		if (null != pageBean.getPointId()) {
			if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
					.getQueryType())) {
				param.put("forwardNum", new BasicDBObject(QueryOperators.GT,
						pageBean.getPointId()));
			}
			// 翻页
			else if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
					.getQueryType())) {
				param.put("forwardNum", new BasicDBObject(QueryOperators.LT,
						pageBean.getPointId()));
			}
		}
		BasicDBObject sort = new BasicDBObject();// 排序
		//按转发数量进行排序
		sort.put("commentNum", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.TWITTER_TWITTER, param, sort);
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<TTwitter>>() {
			}.getType();
			twitters = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		List<TTwitter> returnList = new ArrayList<TTwitter>();
		if (twitters != null) {
			// 获取用户对应收藏微博id，用于判断请求用户是否已对微博收藏
			BasicDBObject p = new BasicDBObject();// 条件
			p.put("userId", userId);
			DBCursor c = MongoDBSupport.getInstance().queryCollection(
					Constant.TWITTER_COLLECT, p);
			List<Long> tids = new ArrayList<Long>();
			if (c != null && c.size() > 0) {
				while (c.hasNext()) {
					BasicDBObject bdb = (BasicDBObject) c.next();
					tids.add(bdb.getLong("twitterId")); 

				}
			}
			// 获取用户对于点赞微博id，用于判断请求用户是否已对微博点赞
			DBCursor cSupport = MongoDBSupport.getInstance().queryCollection(
					Constant.TWITTER_SUPPORT, p);
			List<Long> tidsupport = new ArrayList<Long>();
			if (cSupport != null && cSupport.size() > 0) {
				while (cSupport.hasNext()) {
					BasicDBObject bdb = (BasicDBObject) cSupport.next();
					tidsupport.add(bdb.getLong("twitterId"));
				}
			}
			Iterator<TTwitter> it = twitters.iterator();
			int count = 0;
			while (it.hasNext()) {
				TTwitter twitter = it.next();
				System.out.println(twitter.getTwitterId());
				User user = UserServiceImpl.getInstance().getUserbyid(
						twitter.getUserId());
				if (user == null) {
					it.remove();
				} else {
					twitter.setUserName(user.getName());
					twitter.setMinipicurl(user.getMinipicurl());
					twitter.setBigpicurl(user.getBigpicurl());
					getSourceTwitter(twitter, tids, tidsupport);
					returnList.add(twitter);
					count++;
				}
				if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
						.getQueryType()) && count >= pageBean.getPageSize()) {
					break;
				}
			}
		}
		return returnList;
	}
	@Override
	public List<TTopic> getTopicsByKw(String kw, MyPageBean pageBean) {
		List<TTopic> topics = null;
		BasicDBObject param = new BasicDBObject();// 条件
		if (kw != null && !"".equals(kw)) {
			Pattern pattern = Pattern.compile("^.*" + kw + ".*$");
			param.put("topicName", pattern);
		} 
		int pageNum = pageBean.getPageNum();
		int pageSize = pageBean.getPageSize();
		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("useNum", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.TWITTER_TOPIC, param, sort);
		if (cur != null && cur.size() > 0) {
			cur.skip((pageNum-1)*pageSize).limit(30); 
			Type type = new TypeToken<List<TTopic>>() {
			}.getType();
			topics = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		int count=0;
		List<TTopic> returnList = new ArrayList<TTopic>();
		if (topics != null) {
			Iterator<TTopic> it = topics.iterator(); 
			while (it.hasNext()) {
				TTopic topic = it.next(); 
				User user = UserServiceImpl.getInstance().getUserbyid(
						topic.getCreateUserId()); 
				if (user == null) {
					it.remove();
				} else {
					topic.setUserName(user.getName());
					topic.setBigpicurl(user.getBigpicurl());
					topic.setMinipicurl(user.getMinipicurl());
					returnList.add(topic); 
					count++;
					if(count==pageSize){
						return returnList;
					}
				} 
			}
		}
		return topics;
	}
	@Override
	public List<TTopic> getTopicsByKw2(String kw, MyPageBean pageBean) {
		List<TTopic> topics = null;
		BasicDBObject param = new BasicDBObject();// 条件
		if (kw != null && !"".equals(kw)) {
			Pattern pattern = Pattern.compile("^.*" + kw + ".*$");
			param.put("topicName", pattern);
		}
		// 刷新
		if (null != pageBean.getPointId()) {
			if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
					.getQueryType())) {
				param.put("useNum", new BasicDBObject(QueryOperators.GT,
						pageBean.getPointId()));
			}
			// 翻页
			else if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
					.getQueryType())) {
				param.put("useNum", new BasicDBObject(QueryOperators.LT,
						pageBean.getPointId()));
			}
		}
		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("useNum", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.TWITTER_TOPIC, param, sort);
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<TTopic>>() {
			}.getType();
			topics = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		List<TTopic> returnList = new ArrayList<TTopic>();
		if (topics != null) {
			Iterator<TTopic> it = topics.iterator();
			int count = 0;
			while (it.hasNext()) {
				TTopic topic = it.next();
				User user = UserServiceImpl.getInstance().getUserbyid(
						topic.getCreateUserId());
				if (user == null) {
					it.remove();
				} else {
					topic.setUserName(user.getName());
					topic.setBigpicurl(user.getBigpicurl());
					topic.setMinipicurl(user.getMinipicurl());
					returnList.add(topic);
					count++;
				}
				if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
						.getQueryType()) && count >= pageBean.getPageSize()) {
					break;
				}
			}
		}
		return topics;
	}
	@Override
	public List<User> getUsersByKw(Long userId, String kw, MyPageBean pageBean) {
		List<User> users = null;
		BasicDBObject param = new BasicDBObject();// 条件
		if (kw != null && !"".equals(kw)) {
			Pattern pattern = Pattern.compile("^.*" + kw + ".*$");
			param.put("name", pattern);
		}
		int pageNum = pageBean.getPageNum();
		int pageSize = pageBean.getPageSize();
		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("uid", -1); 
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.USER_DATA, param, sort);
		if (cur != null && cur.size() > 0) {
			cur.skip((pageNum-1)*pageSize).limit(pageSize);
			Type type = new TypeToken<List<User>>() {
			}.getType();
			users = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		List<User> res = new ArrayList<User>();
		if (users != null && users.size() > 0) {
			for (User temp : users) {
				User u = new User();
				u.setUid(temp.getUid());
				u.setName(temp.getName());
				u.setGname(temp.getGname());
				u.setNickname(temp.getNickname());
				u.setCname(temp.getCname());
				u.setJid(temp.getJid());
				u.setPost(temp.getPost());
				u.setMinipicurl(temp.getMinipicurl());
				u.setBigpicurl(temp.getBigpicurl());
				u.setAutograph(temp.getAutograph());
				TAttention attention = FindAttentionByUid(userId,temp.getUid());
				if (attention != null) {
					u.setAttentionFlag("1");
					u.setAttentionType(attention.getAttentionType());
					u.setEachotherFlag(attention.getEachOtherFlag());
				} else {
					u.setAttentionFlag("0");
					u.setAttentionType("");
					u.setEachotherFlag("");
				}
				res.add(u);

			}
		}
		return res;
	}

	@Override
	public List<TGroup> getGroupsByKw(Long userId, String kw,
			MyPageBean pageBean) {
		List<TGroup> groups = null;
		BasicDBObject param = new BasicDBObject();// 条件
		int pageNum = pageBean.getPageNum();
		int pageSize = pageBean.getPageSize();
		if (kw != null && !"".equals(kw)) {
			Pattern pattern = Pattern.compile("^.*" + kw + ".*$");
			param.put("groupName", pattern);
		}

		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("groupId", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.TWITTER_GROUP, param, sort);
		if (cur != null && cur.size() > 0) {
			cur.skip((pageNum-1)*pageSize).limit(pageSize);
			Type type = new TypeToken<List<TGroup>>() {
			}.getType();
			groups = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		List<TGroup> returnList = new ArrayList<TGroup>();
		if (groups != null) {
			Iterator<TGroup> it = groups.iterator();
			int count = 0;
			while (it.hasNext()) {
				TGroup group = it.next();
				// 获取圈子中的人员
				BasicDBObject p = new BasicDBObject();// 条件
				p.put("userId", userId);
				p.put("groupId", group.getGroupId());
				DBCursor c = MongoDBSupport.getInstance().queryCollection(
						Constant.TWITTER_GROUP_USER, p);
				if (c != null && c.size() > 0) {
					group.setIsJoin(Constant.TWITTER_GROUP_JOIN_01);
				} else {
					group.setIsJoin(Constant.TWITTER_GROUP_JOIN_00);
				}
				User user = UserServiceImpl.getInstance().getUserbyid(
						group.getCreateUserId());
				if (user == null) {
					it.remove();
				} else {
					group.setUserName(user.getName());
					group.setBigpicurl(user.getBigpicurl());
					group.setMinipicurl(user.getMinipicurl());
					returnList.add(group);
					count++;
				}
				//				if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
				//						.getQueryType()) && count >= pageBean.getPageSize()) {
				//					break;
				//				}
			}
		}
		return returnList;
	}

	@Override
	public void RemoveAttentionUid(Map<String, Object> param) {
		BasicDBObject dbObject = new BasicDBObject(param);
		MongoDBSupport.getInstance().removeDBObjects(
				Constant.TWITTER_ATTENTION, dbObject);

	}

	@Override
	public void AddAttentionUid(Map<String, Object> param) { 

		BasicDBObject param1 = new BasicDBObject();
		param1.put("userId", Long.valueOf(param.get("userId").toString()));
		param1.put("attentionUserId", Long.valueOf(param.get("attentionUserId").toString()));

		MongoDBSupport.getInstance().removeDBObject(Constant.TWITTER_ATTENTION, param1);

		DBObject dbObject = new BasicDBObject(param);
		MongoDBSupport.getInstance().save(dbObject, Constant.TWITTER_ATTENTION);

	}

	@Override
	public TAttention FindAttentionByUid(Long uid, Long attentionId) {
		TAttention attention = null;
		BasicDBObject param = new BasicDBObject();
		param.put("userId", uid);
		param.put("attentionUserId", attentionId);
		param.put("attentionStatus", "00");
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.TWITTER_ATTENTION, param, null);
		if (obj != null) {
			attention = GsonUtil.gson
					.fromJson(obj.toString(), TAttention.class);
		}
		return attention;
	}

	@Override
	public void UpdateAttentionByUid(TAttention attention) {
		BasicDBObject query = new BasicDBObject();
		query.put("userId", attention.getUserId());
		query.put("attentionUserId", attention.getAttentionUserId());
		BasicDBObject param = new BasicDBObject();

		BasicDBObject set = new BasicDBObject();
		if (attention.getEachOtherFlag() != null) {
			set.put("eachOtherFlag", attention.getEachOtherFlag());
		}
		if (attention.getAttentionStatus() != null) {
			set.put("attentionStatus", attention.getAttentionStatus());
		}
		if (attention.getAttentionType() != null) {
			set.put("attentionType", attention.getAttentionType());
		}

		if (attention.getUpdateUserId() != null) {
			set.put("updateUserId", attention.getUpdateUserId());
		}
		if (attention.getUpdateTime() != null) {
			set.put("updateTime", attention.getUpdateTime());
		}
		if (attention.getRemark() != null) {
			set.put("remark", attention.getRemark());
		}
		param.put("$set", set);
		MongoDBSupport.getInstance().updateCollection(
				Constant.TWITTER_ATTENTION, query, param);

	}

	@Override
	public void UpdateGroupIntroduction(TGroup group) {
		BasicDBObject query = new BasicDBObject();
		query.put("groupId", group.getGroupId());
		BasicDBObject param = new BasicDBObject();

		BasicDBObject set = new BasicDBObject();
		if (group.getGroupIntroduction() != null) {
			set.put("groupIntroduction", group.getGroupIntroduction());
		}
		if (group.getGroupName() != null) {
			set.put("groupName", group.getGroupName());
		}
		if (group.getUpdateUserId() != null) {
			set.put("updateUserId", group.getUpdateUserId());
		}
		if (group.getUpdateTime() != null) {
			set.put("updateTime", group.getUpdateTime());
		}
		if (group.getRemark() != null) {
			set.put("remark", group.getRemark());
		}
		param.put("$set", set);
		MongoDBSupport.getInstance().updateCollection(Constant.TWITTER_GROUP,
				query, param);
	}

	@Override
	public List<TTransUser> GettransTwitterUId(Long twitterId) {
		List<TTransUser> userIds = new ArrayList<TTransUser>();
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("twitterId", twitterId);
		DBCursor cur = MongoDBSupport.getInstance().queryCollection(
				Constant.TWITTER_TRANS_USER, param);
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<TTransUser>>() {
			}.getType();
			userIds = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		return userIds;
	}

	@Override
	public List<TComment> GetCommentsList(Long twitterId) {
		List<TComment> supports = null;
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("commentStatus", "00");
		param.put("twitterId", twitterId);
		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("createTime", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.TWITTER_COMMENT, param, sort);
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<TComment>>() {
			}.getType();
			supports = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		return supports;
	}

	@Override
	public void AddNewTopic(Map<String, Object> param) {
		DBObject dbObject = new BasicDBObject(param);
		MongoDBSupport.getInstance().save(dbObject, Constant.TWITTER_TOPIC);

	}

	@Override
	public List<TTopic> getTopicList(String topicName, MyPageBean page) {
		List<TTopic> topics = null;
		BasicDBObject param = new BasicDBObject();// 条件
		if (topicName != null && !topicName.equals("")) {
			Pattern pattern = Pattern.compile("^.*" + topicName + ".*$");
			param.put("topicName", pattern);
		}

		//		// 刷新
		//		if (null != page.getPointId()) {
		//			if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(page.getQueryType())) {
		//				param.put("useNum",
		//						new BasicDBObject(QueryOperators.GT, page.getPointId()));
		//			}
		//			// 翻页
		//			else if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(page
		//					.getQueryType())) {
		//				param.put("useNum",
		//						new BasicDBObject(QueryOperators.LT, page.getPointId()));
		//			}
		//		}

		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("useNum", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.TWITTER_TOPIC, param, sort);
		//	int pageNum = page.getPageNum();
		//	int pageSize = page.getPageSize();
		if (cur != null && cur.size() > 0) {
			//cur.skip((pageNum-1)*pageSize).limit(pageSize);
			Type type = new TypeToken<List<TTopic>>() {
			}.getType();
			topics = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}

		List<TTopic> returnList = new ArrayList<TTopic>();
		if (topics != null) {
			Iterator<TTopic> it = topics.iterator();
			int count = 0;
			while (it.hasNext()) {
				TTopic topic = it.next();
				returnList.add(topic);
				count++;
				if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(page
						.getQueryType()) && count >= page.getPageSize()) {
					break;
				}
			}
		}
		return returnList;
	}

	@Override
	public TTopic FindTopicByTopicName(String topicName) {
		TTopic topic = null;
		BasicDBObject param = new BasicDBObject();
		param.put("topicName", topicName);
		param.put("topicStatus", "00");
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.TWITTER_TOPIC, param, null);
		if (obj != null) {
			topic = GsonUtil.gson.fromJson(obj.toString(), TTopic.class);
		}
		return topic;
	}

	@Override
	public void SaveNewTwitter(Map<String, Object> param) {
		DBObject dbObject = new BasicDBObject(param);
		MongoDBSupport.getInstance().save(dbObject, Constant.TWITTER_TWITTER);
	}

	@Override
	public void addMentionInfo(Map<String, Object> params) {
		DBObject dbObject = new BasicDBObject(params);
		MongoDBSupport.getInstance().save(dbObject, Constant.TWITTER_MENTION);
	}

	@Override
	public List<TSupport> GetSupportsList(Long twitterId) {
		List<TSupport> supports = null;
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("twitterId", twitterId);
		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("createTime", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.TWITTER_SUPPORT, param, sort);
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<TSupport>>() {
			}.getType();
			supports = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		return supports;
	}

	@Override
	public void addTwitterGroupId(List<Map<String, Object>> params) {
		List<DBObject> list = new ArrayList<DBObject>();
		for (Map<String, Object> param : params) {
			DBObject dbObject = new BasicDBObject(param);
			list.add(dbObject);
		}
		if (list.size() > 0)
			MongoDBSupport.getInstance().saveList(list,
					Constant.TWITTER_GROUP_ID);

	}
	
	@Override
	public void addTwitterStaffId(List<Map<String, Object>> params) {
		List<DBObject> list = new ArrayList<DBObject>();
		for (Map<String, Object> param : params) {
			DBObject dbObject = new BasicDBObject(param);
			list.add(dbObject);
		}
		if (list.size() > 0)
			MongoDBSupport.getInstance().saveList(list,
					Constant.TWITTER_STAFF_ID);

	}
	
	@Override
	public void addTwitterDeptId(List<Map<String, Object>> params) {
		List<DBObject> list = new ArrayList<DBObject>();
		for (Map<String, Object> param : params) {
			DBObject dbObject = new BasicDBObject(param);
			list.add(dbObject);
		}
		if (list.size() > 0)
			MongoDBSupport.getInstance().saveList(list, Constant.TWITTER_DEPT);

	}

	@Override
	public Long findNewTwitterIdByUserID(Long uId) {
		Long twitterId = null;
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("userId", uId);

		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("twitterId", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.TWITTER_TWITTER, param, sort);
		if (cur != null && cur.size() > 0) {
			if (cur.hasNext()) {
				BasicDBObject bdb = (BasicDBObject) cur.next();
				twitterId = bdb.getLong("twitterId");
			}
		}
		return twitterId;
	}

	@Override
	public List<DBObject> getOrgan(Long gid) {
		List<DBObject> res = new ArrayList<DBObject>();
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("gid", gid);
		param.put("pid", 0);
		DBCursor cur = MongoDBSupport.getInstance().queryCollection(
				Constant.COM_POSIT, param);
		if (cur != null && cur.size() > 0) {
			List<DBObject> dboList = cur.toArray();
			for (DBObject temp : dboList) {
				temp = getReOrgan(gid, temp);
				res.add(temp);
			}
		}
		return res;
	}

	public DBObject getReOrgan(Long gid, DBObject dbo) {
		dbo.removeField("users");
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("gid", gid);
		param.put("pid", dbo.get("cid"));
		DBCursor cursor = MongoDBSupport.getInstance().queryCollection(
				Constant.COM_POSIT, param);
		if (cursor != null && cursor.size() > 0) {
			List<DBObject> c = new ArrayList<DBObject>();
			List<DBObject> dboList = cursor.toArray();
			for (DBObject temp : dboList) {
				c.add(getReOrgan(gid, temp));
			}
			dbo.put("children", c.toArray());
		}
		return dbo;
	}

	/**
	 * 获取系统微博
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public TTwitter getSysTwitter(Long userId) {
		TTwitter t = null;
		BasicDBObject param = new BasicDBObject();
		param.put("twitterAttr", TWITTER_ATTR_SYS);
		param.put("twitterStatus", TWITTER_PUBLISH); 
		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("createTime", -1);
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.TWITTER_TWITTER, param, sort);
		if (obj != null) {
			t = GsonUtil.gson.fromJson(obj.toString(), TTwitter.class);
		}
		if(t != null){
			User user = UserServiceImpl.getInstance().getUserbyid(
					t.getUserId());
			if (user == null) {
				t.setUserName("");
				t.setBigpicurl("");
				t.setMinipicurl("");
			} else {
				BasicDBObject p = new BasicDBObject();// 条件
				p.put("userId", userId);
				DBCursor c = MongoDBSupport.getInstance().queryCollection(
						Constant.TWITTER_COLLECT, p);
				List<Long> tids = new ArrayList<Long>();
				if (c != null && c.size() > 0) {
					while (c.hasNext()) {
						BasicDBObject bdb = (BasicDBObject) c.next();
						tids.add(bdb.getLong("twitterId"));
					}
				}
				// 获取用户对于点赞微博id
				DBCursor cSupport = MongoDBSupport.getInstance().queryCollection(
						Constant.TWITTER_SUPPORT, p);
				List<Long> tidsupport = new ArrayList<Long>();
				if (cSupport != null && cSupport.size() > 0) {
					while (cSupport.hasNext()) {
						BasicDBObject bdb = (BasicDBObject) cSupport.next();
						tidsupport.add(bdb.getLong("twitterId"));
					}
				}

				t.setUserName(user.getName());
				t.setBigpicurl(user.getBigpicurl());
				t.setMinipicurl(user.getMinipicurl());
				getSourceTwitter(t, tids, tidsupport);
			}
		}
		return t;
	}
	
	/**
	 * dec 获取系统微博列表
	 * @param userId
	 * @return
	 */
	public List<TTwitter> getSysTwitters(Long userId) {
		List<TTwitter> ts = null;
		BasicDBObject param = new BasicDBObject();
		param.put("twitterAttr", TWITTER_ATTR_SYS);
		param.put("twitterStatus", TWITTER_PUBLISH);
		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("twitterId", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.TWITTER_TWITTER, param, sort);
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<TTwitter>>() {
			}.getType();
			ts = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		
		if(ts != null && ts.size() > 0){
			BasicDBObject p = new BasicDBObject();// 条件
			p.put("userId", userId);
			DBCursor c = MongoDBSupport.getInstance().queryCollection(
					Constant.TWITTER_COLLECT, p);
			List<Long> tids = new ArrayList<Long>();
			if (c != null && c.size() > 0) {
				while (c.hasNext()) {
					BasicDBObject bdb = (BasicDBObject) c.next();
					tids.add(bdb.getLong("twitterId"));
				}
			}
			// 获取用户对于点赞微博id
			DBCursor cSupport = MongoDBSupport.getInstance().queryCollection(
					Constant.TWITTER_SUPPORT, p);
			List<Long> tidsupport = new ArrayList<Long>();
			if (cSupport != null && cSupport.size() > 0) {
				while (cSupport.hasNext()) {
					BasicDBObject bdb = (BasicDBObject) cSupport.next();
					tidsupport.add(bdb.getLong("twitterId"));
				}
			}
			for(TTwitter t : ts){
				User user = UserServiceImpl.getInstance().getUserbyid(
						t.getUserId());
				if (user == null) {
					t.setUserName("");
					t.setBigpicurl("");
					t.setMinipicurl("");
				} else {
					t.setUserName(user.getName());
					t.setBigpicurl(user.getBigpicurl());
					t.setMinipicurl(user.getMinipicurl());
					getSourceTwitter(t, tids, tidsupport);
				}
			}
		}
		return ts;
	}

	/**
	 * 获取分享到该圈子的微博
	 * 
	 * @param groupId
	 * @return
	 */
	@Override
	public List<TTGroup> findTGroup(Long groupId, MyPageBean pageBean) {
		List<TTGroup> tGroup = null;
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("groupId", groupId);
		// 刷新
		if (null != pageBean.getPointId()) {
			if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
					.getQueryType())) {
				param.put("twitterId", new BasicDBObject(QueryOperators.GT,
						pageBean.getPointId()));
			}
			// 翻页
			else if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
					.getQueryType())) {
				param.put("twitterId", new BasicDBObject(QueryOperators.LT,
						pageBean.getPointId()));
			}
		}

		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("twitterId", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.TWITTER_GROUP_ID, param, sort);
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<TTGroup>>() {
			}.getType();
			tGroup = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		List<TTGroup> returnList = new ArrayList<TTGroup>();
		if (tGroup != null) {
			Iterator<TTGroup> it = tGroup.iterator();
			int count = 0;
			while (it.hasNext()) {
				TTGroup ttgroup = it.next();
				returnList.add(ttgroup);
				count++;
				if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
						.getQueryType()) && count >= pageBean.getPageSize()) {
					break;
				}
			}
		}
		return returnList;
	}

	@Override
	public void addFeedBack(Map<String, Object> param) {
		DBObject dbObject = new BasicDBObject(param);
		MongoDBSupport.getInstance().save(dbObject, Constant.TWITTER_FEEDBACK);
	}

	@Override
	public List<TTwitter> getCollects(Long userId, MyPageBean pageBean) {
		BasicDBObject p = new BasicDBObject();// 条件
		p.put("userId", userId);
		DBCursor c = MongoDBSupport.getInstance().queryCollection(
				Constant.TWITTER_COLLECT, p);
		List<Long> tids = new ArrayList<Long>();
		if (c != null && c.size() > 0) {
			while (c.hasNext()) {
				BasicDBObject bdb = (BasicDBObject) c.next();
				long a = bdb.getLong("twitterId");
				if(a > pageBean.getPointId()){
					tids.add(a);
				}
			}
		}
		List<TTwitter> twitters = null;
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("twitterStatus", TWITTER_PUBLISH);
		param.put("twitterId", new BasicDBObject(QueryOperators.IN, tids));
		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("createTime", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				Constant.TWITTER_TWITTER, param,sort); 
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<TTwitter>>() {
			}.getType();
			twitters = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		List<TTwitter> returnList = new ArrayList<TTwitter>();
		if (twitters != null) {
			Iterator<TTwitter> it = twitters.iterator();
			int count = 0;
			// 获取用户对于点赞微博id
			DBCursor cSupport = MongoDBSupport.getInstance().queryCollection(
					Constant.TWITTER_SUPPORT, p);
			List<Long> tidsupport = new ArrayList<Long>();
			if (cSupport != null && cSupport.size() > 0) {
				while (cSupport.hasNext()) {
					BasicDBObject bdb = (BasicDBObject) cSupport.next();
					tidsupport.add(bdb.getLong("twitterId"));
				}
			}
			while (it.hasNext()) {
				TTwitter twitter = it.next();
				User user = UserServiceImpl.getInstance().getUserbyid(
						twitter.getUserId());
				if (user == null) {
					it.remove();
				} else {
					twitter.setUserName(user.getName());
					twitter.setMinipicurl(user.getMinipicurl());
					twitter.setBigpicurl(user.getBigpicurl());
					getSourceTwitter(twitter, tids, tidsupport);
					returnList.add(twitter);
					count++;
				}
				if (Constant.TWITTER_QUERYTYPE_NEXTPAGE.equals(pageBean
						.getQueryType()) && count >= pageBean.getPageSize()) {
					break;
				}
			}
		}
		return returnList;
	}

	@Override
	public List<TAttention> getAttentsByUserId(Long userId) {
		List<TAttention> attentions = null;
		BasicDBObject param1 = new BasicDBObject();// 条件
		param1.put("userId", userId);
		DBCursor cur = MongoDBSupport.getInstance().queryCollection(
				Constant.TWITTER_ATTENTION, param1);
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<TAttention>>() {
			}.getType();
			attentions = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}

		return attentions;
	}

	@Override
	public void addSysMessage(Map<String, Object> param) {
		DBObject dbObject = new BasicDBObject(param);
		MongoDBSupport.getInstance().save(dbObject,
				Constant.TWITTER_SYS_MESSAGE);
	}

	@Override
	public TAttention getAttention(Long userId, Long operUserId) {
		TAttention attention = null;
		BasicDBObject param = new BasicDBObject();
		param.put("attentionUserId", userId);
		param.put("userId", operUserId);
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.TWITTER_ATTENTION, param, null);
		if (obj != null) {
			attention = GsonUtil.gson
					.fromJson(obj.toString(), TAttention.class);
		}
		return attention;
	}

	@Override
	public void addMention(Map<String, Object> param) {
		DBObject dbObject = new BasicDBObject(param);
		MongoDBSupport.getInstance().save(dbObject, Constant.TWITTER_MENTION);
	}

	/**
	 * dec 将原始微博信息插入子微博
	 * 
	 * @param twitter
	 */
	public void getSourceTwitter(TTwitter twitter, List<Long> collectTids,
			List<Long> supportTids) {
		TTwitter sourTwitter = null;
		BasicDBObject param = new BasicDBObject();
		param.put("twitterId", twitter.getSourceId());
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.TWITTER_TWITTER, param, null);
		if (obj != null) {
			sourTwitter = GsonUtil.gson
					.fromJson(obj.toString(), TTwitter.class);
		}
		if (sourTwitter != null) {
			User user = UserServiceImpl.getInstance().getUserbyid(
					sourTwitter.getUserId());
			if (user != null) {
				twitter.setSourceUserName(user.getName());
				twitter.setSourceBigpicurl(user.getBigpicurl());
				twitter.setSourceMinipicurl(user.getMinipicurl());
				twitter.setSourceContent(sourTwitter.getTwitterContent());
				twitter.setSourceCreateTime(sourTwitter.getCreateTime());
				twitter.setSourceUserId(sourTwitter.getUserId());
				twitter.setSourceImgSrc(sourTwitter.getImgSrc());
				twitter.setSourceCollectionNum(sourTwitter.getCollectionNum());
				twitter.setSourceCommentNum(sourTwitter.getCommentNum());
				twitter.setSourceForwardNum(sourTwitter.getForwardNum());
				twitter.setSourceHreflag(sourTwitter.getHreflag());
				if (collectTids.contains(sourTwitter.getTwitterId())) {
					twitter.setSourceIsCollect(Constant.TWITTER_ISCOLLECT);
				} else {
					twitter.setSourceIsCollect(Constant.TWITTER_NOTCOLLECT);
				}
				if (collectTids.contains(twitter.getTwitterId())) {
					twitter.setIsCollect(Constant.TWITTER_ISCOLLECT);
				} else {
					twitter.setIsCollect(Constant.TWITTER_NOTCOLLECT);
				}
				if (supportTids.contains(sourTwitter.getTwitterId())) {
					twitter.setSourceIsSupport(Constant.TWITTER_ISSUPPORT);
				} else {
					twitter.setSourceIsSupport(Constant.TWITTER_NOTSUPPORT);
				}
				if (supportTids.contains(twitter.getTwitterId())) {
					twitter.setIsSupport(Constant.TWITTER_ISSUPPORT);
				} else {
					twitter.setIsSupport(Constant.TWITTER_NOTSUPPORT);
				}
				twitter.setSourceSupportNum(sourTwitter.getSupportNum());
				twitter.setSourceCountry(sourTwitter.getSourceCountry());
				twitter.setSourceProvince(sourTwitter.getSourceProvince());
				twitter.setSourceCity(sourTwitter.getCity());
				twitter.setSourceCounty(sourTwitter.getCounty());
				twitter.setSourceStreet(sourTwitter.getStreet());
				twitter.setSourceAddress(sourTwitter.getAddress());
				twitter.setSourceTwitterType(sourTwitter.getTwitterType());
				twitter.setSourceTwitterStatus(sourTwitter.getTwitterStatus());
				twitter.setSourceVisibleRange(sourTwitter
						.getSourceVisibleRange());
			}
		}
	}

	/**
	 * 添加token信息
	 * 
	 * @param token
	 */
	@Override
	public void addToken(String token) {
		BasicDBObject param1 = new BasicDBObject();
		param1.put("token", token);
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.TWITTER_TOKEN, param1, null);
		if (obj == null) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("tokenId", IDSequence.getUID());
			param.put("token", token);
			DBObject dbObject = new BasicDBObject(param);
			MongoDBSupport.getInstance().save(dbObject, Constant.TWITTER_TOKEN);
		}
	}

	/**
	 * 删除token信息
	 * 
	 * @param tokenId
	 */
	@Override
	public void delToken(Long tokenId) {
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("tokenId", tokenId);
		MongoDBSupport.getInstance().removeDBObjects(Constant.TWITTER_TOKEN,
				dbObject);
	}

	/**
	 * 获取所有token信息
	 * 
	 * @return
	 */
	@Override
	public List<TToken> getAllToken() {
		List<TToken> tokens = null;
		BasicDBObject param = new BasicDBObject();// 条件
		DBCursor cur = MongoDBSupport.getInstance().queryCollection(
				Constant.TWITTER_TOKEN, param);
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<TToken>>() {
			}.getType();
			tokens = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		return tokens;
	}

	/**
	 * 记录用户日志
	 */
	@Override
	public void saveUserLog(User user, String oper, String content) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Calendar ca = Calendar.getInstance();
			String operDate = df.format(ca.getTime());
			SysLog log = new SysLog();
			log.setType("user");
			log.setCreatime(operDate);
			log.setUname(user.getName());
			log.setUid(String.valueOf(user.getUid()));
			log.setOper(oper);
			log.setContent(content);
			DBObject object = (DBObject) MyJSON
					.parse(GsonUtil.gson.toJson(log));
			MongoDBSupport.getInstance().save(object, Constant.SYS_LOG);
		} catch (Exception e) {
			logger.error("", e);
		}

	}

	/**
	 * 记录安全日志
	 */
	@Override
	public void saveSecureLog(User user, String oper, String type) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Calendar ca = Calendar.getInstance();
			String operDate = df.format(ca.getTime());
			StringBuffer operatingcontent = new StringBuffer();
			if (type.equals("changeDevice")) {
				operatingcontent.append("用户").append(user.getName())
				.append("使用新终端“IMEI:").append(user.getSn())
				.append("”登陆");
			} else if (type.equals("pwdError")) {
				operatingcontent.append("用户").append(user.getName())
				.append("连续输错密码5次");
			}
			SysLog log = new SysLog();
			log.setType("secure");
			log.setCreatime(operDate);
			log.setUname(user.getName());
			log.setUid(String.valueOf(user.getUid()));
			log.setOper(oper);
			log.setContent(operatingcontent.toString());
			DBObject object = (DBObject) MyJSON
					.parse(GsonUtil.gson.toJson(log));
			MongoDBSupport.getInstance().save(object, Constant.SYS_LOG);
		} catch (Exception e) {
			logger.error("", e);
		}

	}

	@Override
	public void cancelSupport(Long userId, Long twitterId) {
		DBObject dbObject = new BasicDBObject();
		dbObject.put("twitterId", twitterId);
		dbObject.put("userId", userId);
		MongoDBSupport.getInstance().removeDBObject(Constant.TWITTER_SUPPORT,
				dbObject);
	}
	
	@Override
	public TSysMessage getSysMessage(TSysMessage sysMsg) {
		TSysMessage msg = null;
		BasicDBObject param = new BasicDBObject();
		param.put("sendUserId", sysMsg.getSendUserId());
		param.put("receiveUserId", sysMsg.getReceiveUserId());
		param.put("groupId", sysMsg.getGroupId());
		param.put("messageType", sysMsg.getMessageType());
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.TWITTER_SYS_MESSAGE, param, null);
		if (obj != null) {
			msg = GsonUtil.gson.fromJson(obj.toString(), TSysMessage.class);
		}
		return msg;
	}

	@Override
	public List<TGroup> FindGroupByUserId(Long userId, Page page) {
		List<TGroup> groups = null;
		BasicDBObject param1 = new BasicDBObject();// 条件
		param1.put("userId", userId);
		DBCursor cur = MongoDBSupport.getInstance().queryCollection(
				Constant.TWITTER_GROUP_USER, param1);
		List<Long> gIds = new ArrayList<Long>();
		if (cur != null && cur.size() > 0) {
			while (cur.hasNext()) {
				BasicDBObject bdb = (BasicDBObject) cur.next();
				gIds.add(bdb.getLong("groupId"));
			}
			cur.close();
		}
		BasicDBObject param2 = new BasicDBObject();
		Long[] gIdArr = (Long[]) gIds.toArray(new Long[gIds.size()]);
		param2.put("groupId", new BasicDBObject(QueryOperators.IN, gIdArr));
		DBCursor cur2 = MongoDBSupport.getInstance().queryCollectionConditions(
				Constant.TWITTER_GROUP, param2, page);
		if (cur2 != null && cur2.size() > 0) {
			Type type = new TypeToken<List<TGroup>>() {
			}.getType();
			groups = GsonUtil.gson.fromJson(cur2.toArray().toString(), type);
			cur2.close();
		}
		return groups;
	}
	
	/**
	 * 查看点赞记录
	 * @param userId
	 * @param twitterId
	 */
	@Override
	public TSupport getSupport(Long userId, Long twitterId) {
		TSupport msg = null;
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("twitterId", twitterId);
		dbObject.put("userId", userId);
		 
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(
				Constant.TWITTER_SUPPORT, dbObject,null);
		if (obj != null) {
			msg = GsonUtil.gson.fromJson(obj.toString(), TSupport.class);
		}
		return msg;
	}
	
}
