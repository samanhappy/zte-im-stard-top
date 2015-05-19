package com.zte.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;
import com.zte.databinder.ChatRecordBinder;
import com.zte.im.bean.ChatRecord;
import com.zte.im.bean.GroupChat;
import com.zte.im.bean.User;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.mybatis.bean.page.ChatRecordPageModel;
import com.zte.im.service.IGroupService;
import com.zte.im.service.IUserService;
import com.zte.im.service.impl.GroupServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.Constant;
import com.zte.im.util.Page;
import com.zte.service.IChatRecordService;

@Service
public class ChatRecordServiceImpl implements IChatRecordService {

	IUserService userService = UserServiceImpl.getInstance();

	IGroupService groupService = GroupServiceImpl.getInstance();

	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String getUserNameByUid(Long uid) {
		User user = userService.getUserbyid(uid);
		return user != null ? user.getName() : "系统";
	}

	private String getUNameOrGNameByUid(Long uid) {
		User user = userService.getUserbyid(uid);
		if (user != null) {
			return user.getName();
		}
		GroupChat group = groupService.findGroupChatById(uid);
		if (group != null) {
			return group.getGroupName();
		}
		return "系统";
	}

	@Override
	public ChatRecordPageModel list(ChatRecordBinder binder) {

		BasicDBObject param = new BasicDBObject();
		if (binder.getType() == 0) {
			// 按内容搜索暂不支持
		} else if (binder.getType() == 1) {

		}

		BasicDBList userParam = new BasicDBList();

		// 按人搜索
		if (StringUtils.isNotBlank(binder.getKeyword())) {
			List<Long> uids = userService.searchUser(binder.getKeyword());
			if (uids.size() > 0) {
				userParam.add(new BasicDBObject("Sender", new BasicDBObject("$in", uids)));
				userParam.add(new BasicDBObject("Receiver", new BasicDBObject("$in", uids)));
			} else {
				// 搜索不到人时直接返回
				return ChatRecordPageModel.newPageModel(binder.getpSize(), binder.getcPage(), 0);
			}
		}

		BasicDBObject timeParam = new BasicDBObject();
		if (StringUtils.isNotBlank(binder.getStartTime())) {
			try {
				long startTime = dateFormat.parse(binder.getStartTime()).getTime() / 1000;
				timeParam.append("$gte", startTime);
			} catch (ParseException e) {
			}
		}
		if (StringUtils.isNotBlank(binder.getEndTime())) {
			try {
				long endTime = dateFormat.parse(binder.getEndTime()).getTime() / 1000;
				timeParam.append("$lte", endTime);
			} catch (ParseException e) {
			}
		}
		param.append("Sender", new BasicDBObject("$gt", 100));
		if (timeParam.size() > 0) {
			param.append("DateTime", timeParam);
		}
		if (userParam.size() > 0) {
			param.append(QueryOperators.OR, userParam);
		}

		Long totalRecord = MongoDBSupport.getInstance().getCount(Constant.CHAT_RECORD, param);
		ChatRecordPageModel pm = ChatRecordPageModel.newPageModel(binder.getpSize(), binder.getcPage(),
				totalRecord.intValue());
		binder.setOffset(pm.getOffset());
		BasicDBObject sort = new BasicDBObject("DateTime", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParamWithId(Constant.CHAT_RECORD,
				new Page(binder.getpSize(), binder.getcPage()), param, sort);
		if (cur != null && cur.size() > 0) {
			List<ChatRecord> recordList = new ArrayList<ChatRecord>();
			for (DBObject obj : cur.toArray()) {
				ChatRecord record = new ChatRecord();
				record.setId(obj.get("_id").toString());
				record.setSender(getUserNameByUid(Long.valueOf(obj.get("Sender").toString())));
				record.setReceiver(getUNameOrGNameByUid(Long.valueOf(obj.get("Receiver").toString())));
				record.setTime(dateFormat.format(new Date(Long.valueOf(obj.get("DateTime").toString()) * 1000)));
				record.setPayload(new String((byte[]) obj.get("Payload")));
				record.handlePayload();
				recordList.add(record);
			}
			pm.setRecordList(recordList);
		}
		return pm;
	}

	public List<ChatRecord> list(Page page) {
		List<ChatRecord> recordList = new ArrayList<ChatRecord>();
		BasicDBObject ref = new BasicDBObject();// 条件
		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("DateTime", -1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(Constant.CHAT_RECORD, page, ref, sort);
		if (cur != null && cur.size() > 0) {
			for (DBObject obj : cur.toArray()) {
				ChatRecord record = new ChatRecord();
				record.setSender(getUserNameByUid(Long.valueOf(obj.get("Sender").toString())));
				record.setReceiver(getUserNameByUid(Long.valueOf(obj.get("Receiver").toString())));
				record.setTime(dateFormat.format(new Date(Long.valueOf(obj.get("DateTime").toString()) * 1000)));
				record.setPayload(new String((byte[]) obj.get("Payload")));
				recordList.add(record);
			}
		}
		System.out.println(recordList.size());
		return recordList;
	}

	private List<ObjectId> str2ObjectId(List<String> ids) {
		if (ids != null && ids.size() > 0) {
			List<ObjectId> list = new ArrayList<ObjectId>();
			for (String id : ids) {
				list.add(new ObjectId(id));
			}
			return list;
		}
		return null;
	}

	@Override
	public List<ChatRecord> listByIds(String ids) {
		List<ChatRecord> recordList = new ArrayList<ChatRecord>();
		if (ids != null && !ids.equals("")) {
			BasicDBObject ref = new BasicDBObject();// 条件
			ref.put("_id", new BasicDBObject("$in", str2ObjectId(Arrays.asList(ids.split(",")))));
			BasicDBObject sort = new BasicDBObject();// 排序
			sort.put("DateTime", -1);
			DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(Constant.CHAT_RECORD, ref, sort);
			if (cur != null && cur.size() > 0) {
				for (DBObject obj : cur.toArray()) {
					ChatRecord record = new ChatRecord();
					record.setSender(getUserNameByUid(Long.valueOf(obj.get("Sender").toString())));
					record.setReceiver(getUNameOrGNameByUid(Long.valueOf(obj.get("Receiver").toString())));
					record.setTime(dateFormat.format(new Date(Long.valueOf(obj.get("DateTime").toString()) * 1000)));
					record.setPayload(new String((byte[]) obj.get("Payload")));
					record.handlePayload();
					recordList.add(record);
				}
			}
		}
		return recordList;
	}

	@Override
	public List<ChatRecord> listByAggregate() {

		// create our pipeline operations, first with the $match
		DBObject matchFields = new BasicDBObject("Sender", new BasicDBObject("$gt", 0));
		matchFields.put("Receiver", new BasicDBObject("$gt", 0));
		DBObject match = new BasicDBObject("$match", matchFields);

		// build the $projection operation
		DBObject fields = new BasicDBObject("Sender", 1);
		fields.put("Receiver", 1);
		BasicDBList dbList = new BasicDBList();
		dbList.add("$Sender");
		dbList.add("$Receiver");
		fields.put("S+R", new BasicDBObject("$add", dbList));
		fields.put("S*R", new BasicDBObject("$multiply", dbList));
		fields.put("DateTime", 1);
		fields.put("Payload", 1);
		fields.put("_id", 0);
		DBObject project = new BasicDBObject("$project", fields);

		// Now the $group operation
		Map<String, Object> dbObjIdMap = new HashMap<String, Object>();
		dbObjIdMap.put("S+R", "$S+R");
		dbObjIdMap.put("S*R", "$S*R");
		DBObject groupFields = new BasicDBObject("_id", dbObjIdMap);
		groupFields.put("DateTime", new BasicDBObject("$max", "$DateTime"));
		groupFields.put("count", new BasicDBObject("$sum", 1));
		groupFields.put("Sender", new BasicDBObject("$max", "$Sender"));
		groupFields.put("Receiver", new BasicDBObject("$min", "$Receiver"));
		groupFields.put("Payload", new BasicDBObject("$first", "$Payload"));
		DBObject group = new BasicDBObject("$group", groupFields);

		// Finally the $sort operation
		DBObject sort = new BasicDBObject("$sort", new BasicDBObject("updateTime", -1));

		// build the $projection operation
		DBObject outputs = new BasicDBObject("Sender", 1);
		outputs.put("Receiver", 1);
		outputs.put("DateTime", 1);
		outputs.put("Payload", 1);
		outputs.put("_id", 0);
		DBObject format = new BasicDBObject("$project", outputs);

		// run aggregation
		List<DBObject> pipeline = Arrays.asList(match, project, sort, group, sort, format);
		AggregationOutput output = MongoDBSupport.getInstance().getCollectionByName(Constant.CHAT_RECORD)
				.aggregate(pipeline);
		int count = 0;
		for (DBObject result : output.results()) {
			System.out.println(result);
			count++;
		}
		System.out.println(count);
		return null;
	}

	public static void main(String[] args) {
		new ChatRecordServiceImpl().list(new ChatRecordBinder());
	}
}
