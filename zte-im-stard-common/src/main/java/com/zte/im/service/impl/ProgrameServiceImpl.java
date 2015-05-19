package com.zte.im.service.impl;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;
import com.zte.im.bean.Programe;
import com.zte.im.bean.ProgrameFinish;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.IProgrameService;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.IDSequence;

public class ProgrameServiceImpl implements IProgrameService {
	
	private static Logger logger = LoggerFactory.getLogger(ProgrameServiceImpl.class);

	private static ProgrameServiceImpl service;
	/** 表里日程类型：01、单次 */
	public static String PROG_TYPE_01 = "01";
	/** 表里日程类型：02、周期 */
	public static String PROG_TYPE_02 = "02";

	/** 请求日程类型，1：单次事件； */
	public static int PROG_TYPE_1 = 1;
	/** 请求日程类型，2：周期事件： */
	public static int PROG_TYPE_2 = 2;
	/** 请求日程类型，3：指派单次事件； */
	public static int PROG_TYPE_3 = 3;
	/** 请求日程类型，4:指派周期事件 */
	public static int PROG_TYPE_4 = 4;

	/** 参与标记：1、是 */
	public static int PARTAKE_FLAG_1 = 1;
	/** 参与标记：0、否 */
	public static int PARTAKE_FLAG_0 = 0;
	/** 指派标记：1、是 */
	public static int APPOINT_FLAG_1 = 1;
	/** 指派标记：0、否 */
	public static int APPOINT_FLAG_0 = 0;

	/** 日程状态：00、启用； */
	public static String PROG_STATUS_00 = "00";
	/** 日程状态：99、停用 */
	public static String PROG_STATUS_99 = "99";
	/** 日程状态：88、不参与 */
	public static String PROG_STATUS_88 = "88";

	/** 日程完成状态：01、完成 */
	public static String FINISH_TYPE_1 = "01";
	/** 日程完成状态：02、拒绝 */
	public static String FINISH_TYPE_2 = "02";

	public static final String PROGRAME = "t_programe";//
	public static final String PROGRAME_INTERVAL = "t_programe_interval";
	public static final String PROGRAME_FINISH = "t_programe_finish";

	private ProgrameServiceImpl() {
		super();
	}

	public static IProgrameService getInstance() {
		if (service == null) {
			service = new ProgrameServiceImpl();
		}
		return service;
	}

	/**
	 * 新增日程
	 */
	@Override
	public void addPrograme(Programe programe) {
		BasicDBObject query = new BasicDBObject();

		Long id = IDSequence.getGroupId();
		query.put("id", id);
		query.put("userId", programe.getUserId());
		query.put("progTitle", programe.getProgTitle());
		query.put("progStartDate", programe.getProgStartDate());
		query.put("progStartTime", programe.getProgStartTime());
		query.put("progEndDate", programe.getProgEndDate());
		query.put("progEndTime", programe.getProgEndTime());
		query.put("isDay", programe.getIsDay());
		query.put("reminderType", programe.getReminderType());
		query.put("reminderLong", programe.getReminderLong());

		query.put("progKind", programe.getProgKind());
		query.put("progStatus", PROG_STATUS_00);
		query.put("sortTime",
				Integer.parseInt(programe.getProgStartTime().replace(":", "")));
		query.put("progContent", programe.getProgContent());
		query.put("address", programe.getAddress());
		query.put("remindFlag", programe.getRemindFlag());
		// 提醒标记：1、是；0、否
		if (1 == programe.getRemindFlag()) {
			// 提醒时间
			String remindTime = getTime(programe.getProgStartTime(),
					programe.getRemindAhead());
			programe.setRemindTime(remindTime);
			query.put("remindTime", programe.getRemindTime());
		}
		// 1：单次事件；2：周期事件：3：指派单次事件；4:指派周期事件
		if (PROG_TYPE_1 == programe.getType()) {
			query.put("progType", PROG_TYPE_01);
			query.put("appointFlag", APPOINT_FLAG_0);
			query.put("partakeFlag", PARTAKE_FLAG_1);
			MongoDBSupport.getInstance().save(query, PROGRAME);
		} else if (PROG_TYPE_2 == programe.getType()) {
			query.put("progType", PROG_TYPE_02);
			query.put("appointFlag", APPOINT_FLAG_0);
			query.put("partakeFlag", PARTAKE_FLAG_1);
			query.put("intervalTimeList", programe.getIntervalTimeList());
			MongoDBSupport.getInstance().save(query, PROGRAME);
			// 插入周期
			if (null != programe.getIntervalTimeList()
					&& !"".equals(programe.getIntervalTimeList())) {
				for (String interval : programe.getIntervalTimeList()
						.split(",")) {
					BasicDBObject interavlQuery = new BasicDBObject();
					interavlQuery.put("id", IDSequence.getGroupId());
					interavlQuery.put("intervalProgId", id);
					interavlQuery.put("intervalTime", interval);
					MongoDBSupport.getInstance().save(interavlQuery,
							PROGRAME_INTERVAL);
				}
			}
		} else if (PROG_TYPE_3 == programe.getType()) {
			query.put("progType", PROG_TYPE_01);
			query.put("appointFlag", APPOINT_FLAG_1);
			query.put("partakeFlag", PARTAKE_FLAG_1);
			MongoDBSupport.getInstance().save(query, PROGRAME);
			// 插入指派用户
			if (null != programe.getAssignedUid()
					&& !"".equals(programe.getAssignedUid())) {
				for (int i = 0; i < programe.getAssignedUid().split(",").length; i++) {
					BasicDBObject interavlQuery = new BasicDBObject();
					interavlQuery.put("id", IDSequence.getGroupId());
					interavlQuery.put("mProgId", id);
					interavlQuery.put("userId", Long.parseLong(programe
							.getAssignedUid().split(",")[i]));
					interavlQuery.put("appointUserId", programe.getUserId());
					interavlQuery.put("userName", programe.getAssignedName()
							.split(",")[i]);
					interavlQuery.put("userMobile", programe
							.getAssignedMobile().split(",")[i]);
					interavlQuery.put("progTitle", programe.getProgTitle());
					interavlQuery.put("progStartDate",
							programe.getProgStartDate());
					interavlQuery.put("progStartTime",
							programe.getProgStartTime());
					interavlQuery.put("progEndDate", programe.getProgEndDate());
					interavlQuery.put("progEndTime", programe.getProgEndTime());
					interavlQuery.put("isDay", programe.getIsDay());
					interavlQuery.put("reminderType",
							programe.getReminderType());
					interavlQuery.put("reminderLong",
							programe.getReminderLong());
					interavlQuery.put("progKind", programe.getProgKind());
					interavlQuery.put("progContent", programe.getProgContent());
					interavlQuery.put("address", programe.getAddress());
					interavlQuery.put("progStatus", PROG_STATUS_00);
					interavlQuery.put("progType", PROG_TYPE_01);
					interavlQuery.put("appointFlag", APPOINT_FLAG_1);
					interavlQuery.put("partakeFlag", PARTAKE_FLAG_1);
					interavlQuery.put("remindFlag", programe.getRemindFlag());
					interavlQuery.put("remindTime", programe.getRemindTime());
					MongoDBSupport.getInstance().save(interavlQuery, PROGRAME);
				}
			}
		} else if (PROG_TYPE_4 == programe.getType()) {
			query.put("progType", PROG_TYPE_02);
			query.put("appointFlag", APPOINT_FLAG_1);
			query.put("partakeFlag", PARTAKE_FLAG_1);
			MongoDBSupport.getInstance().save(query, PROGRAME);
			// 插入指派用户
			if (null != programe.getAssignedUid()
					&& !"".equals(programe.getAssignedUid())) {
				for (int i = 0; i < programe.getAssignedUid().split(",").length; i++) {
					BasicDBObject interavlQuery = new BasicDBObject();
					interavlQuery.put("id", IDSequence.getGroupId());
					interavlQuery.put("mProgId", id);
					interavlQuery.put("userId", Long.parseLong(programe
							.getAssignedUid().split(",")[i]));
					interavlQuery.put("appointUserId", programe.getUserId());
					interavlQuery.put("userName", programe.getAssignedName()
							.split(",")[i]);
					interavlQuery.put("userMobile", programe
							.getAssignedMobile().split(",")[i]);
					interavlQuery.put("progTitle", programe.getProgTitle());
					interavlQuery.put("progStartDate",
							programe.getProgStartDate());
					interavlQuery.put("progStartTime",
							programe.getProgStartTime());
					interavlQuery.put("progEndDate", programe.getProgEndDate());
					interavlQuery.put("progEndTime", programe.getProgEndTime());
					interavlQuery.put("isDay", programe.getIsDay());
					interavlQuery.put("reminderType",
							programe.getReminderType());
					interavlQuery.put("reminderLong",
							programe.getReminderLong());
					interavlQuery.put("progKind", programe.getProgKind());
					interavlQuery.put("progContent", programe.getProgContent());
					interavlQuery.put("address", programe.getAddress());
					interavlQuery.put("remindFlag", programe.getRemindFlag());
					interavlQuery.put("remindTime", programe.getRemindTime());
					interavlQuery.put("progStatus", PROG_STATUS_00);
					interavlQuery.put("progType", PROG_TYPE_02);
					interavlQuery.put("appointFlag", APPOINT_FLAG_1);
					interavlQuery.put("partakeFlag", PARTAKE_FLAG_1);

					MongoDBSupport.getInstance().save(interavlQuery, PROGRAME);
				}
			}

			// 插入周期
			if (null != programe.getIntervalTimeList()
					&& !"".equals(programe.getIntervalTimeList())) {
				for (String interval : programe.getIntervalTimeList()
						.split(",")) {
					BasicDBObject interavlQuery = new BasicDBObject();
					interavlQuery.put("id", IDSequence.getGroupId());
					interavlQuery.put("intervalProgId", id);
					interavlQuery.put("intervalTime", interval);
					interavlQuery.put("intervalTimeList",
							programe.getIntervalTimeList());
					MongoDBSupport.getInstance().save(interavlQuery,
							PROGRAME_INTERVAL);
				}
			}
		}

	}

	@Override
	public List<Programe> findList(Programe programe) {
		List<Programe> programes = null;
		BasicDBObject param1 = new BasicDBObject();// 条件
		// param1.put("gid", gid);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionConditions(
				PROGRAME, param1, new BasicDBObject());
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<Programe>>() {
			}.getType();
			programes = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		return programes;
	}

	/**
	 * 得到指定时间
	 * 
	 * @param currentTime
	 *            指定时间
	 * @param remindAhead
	 *            提醒时长
	 * @return
	 */
	public static String getTime(String currentTime, int remindAhead) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		try {
			Date date = format.parse(currentTime);
			long time = date.getTime() + (remindAhead * 60 * 1000);
			Date date1 = new Date(time);
			return format.format(date1);
		} catch (ParseException e) {
			logger.error("", e);
		}
		return currentTime;
	}

	/**
	 * 得到指定时间是周几
	 * 
	 * @param currentTime
	 *            指定时间
	 * @return
	 */
	public static String getWeek(String dateTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		try {
			Date date = format.parse(dateTime);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if (w <= 0) {
				w = 7;
			}
			return String.valueOf(w);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		}
		return String.valueOf("");
	}

	/**
	 * 删除日程
	 * 
	 * @param uid
	 *            用户id
	 * @param ids
	 *            日程id数组
	 */
	@Override
	public void removePrograme(Long uid, String ids) {
		BasicDBObject param = new BasicDBObject();
		BasicDBObject set = new BasicDBObject();
		set.put("progStatus", PROG_STATUS_99);
		param.put("$set", set);

		for (String id : ids.split(",")) {
			BasicDBObject query = new BasicDBObject();
			query.put("id", Long.parseLong(id));
			BasicDBObject query2 = new BasicDBObject();
			query.put("mProgId", Long.parseLong(id));
			List<BasicDBObject> orQuery = new ArrayList<BasicDBObject>();
			orQuery.add(query);
			orQuery.add(query2);
			BasicDBObject overQuery = new BasicDBObject("$or", orQuery);

			MongoDBSupport.getInstance().updateCollection(PROGRAME, overQuery,
					param);
		}
	}

	/**
	 * 修改日程
	 * 
	 * @param programe
	 */
	@Override
	public void updatePrograme(Programe programe) {
		BasicDBObject query = new BasicDBObject();
		Long id = programe.getId();
		removeProgrameAll(id);
		query.put("id", id);
		query.put("userId", programe.getUserId());
		query.put("progTitle", programe.getProgTitle());
		query.put("progStartDate", programe.getProgStartDate());
		query.put("progStartTime", programe.getProgStartTime());
		query.put("progEndDate", programe.getProgEndDate());
		query.put("progEndTime", programe.getProgEndTime());
		query.put("isDay", programe.getIsDay());
		query.put("reminderType", programe.getReminderType());
		query.put("reminderLong", programe.getReminderLong());
		query.put("progKind", programe.getProgKind());

		query.put("progStatus", PROG_STATUS_00);
		query.put("sortTime",
				Integer.parseInt(programe.getProgStartTime().replace(":", "")));
		query.put("progContent", programe.getProgContent());
		query.put("address", programe.getAddress());
		query.put("remindFlag", programe.getRemindFlag());

		if (null != programe.getProgContent()
				&& !"".equals(programe.getProgContent())) {
			query.put("progContent", programe.getProgContent());
		}
		if (null != programe.getAddress() && !"".equals(programe.getAddress())) {
			query.put("address", programe.getAddress());
		}
		// 提醒标记：1、是；0、否
		if (1 == programe.getRemindFlag()) {
			// 提醒时间
			String remindTime = getTime(programe.getProgStartTime(),
					programe.getRemindAhead());
			programe.setRemindTime(remindTime);
		}
		// 1：单次事件；2：周期事件：3：指派单次事件；4:指派周期事件
		if (PROG_TYPE_1 == programe.getType()) {
			query.put("progType", PROG_TYPE_01);
			query.put("appointFlag", APPOINT_FLAG_0);
			query.put("partakeFlag", PARTAKE_FLAG_1);
		} else if (PROG_TYPE_2 == programe.getType()) {
			query.put("progType", PROG_TYPE_02);
			query.put("appointFlag", APPOINT_FLAG_0);
			query.put("partakeFlag", PARTAKE_FLAG_1);
			query.put("intervalTimeList", programe.getIntervalTimeList());

			// 插入周期
			if (null != programe.getIntervalTimeList()
					&& !"".equals(programe.getIntervalTimeList())) {
				for (String interval : programe.getIntervalTimeList()
						.split(",")) {
					BasicDBObject interavlQuery = new BasicDBObject();
					interavlQuery.put("id", IDSequence.getGroupId());
					interavlQuery.put("intervalProgId", id);
					interavlQuery.put("intervalTime", interval);
					MongoDBSupport.getInstance().save(interavlQuery,
							PROGRAME_INTERVAL);
				}
			}
		} else if (PROG_TYPE_3 == programe.getType()) {
			query.put("progType", PROG_TYPE_01);
			query.put("appointFlag", APPOINT_FLAG_1);
			query.put("partakeFlag", PARTAKE_FLAG_1);

			// 插入指派用户
			if (null != programe.getAssignedUid()
					&& !"".equals(programe.getAssignedUid())) {
				for (int i = 0; i < programe.getAssignedUid().split(",").length; i++) {
					BasicDBObject interavlQuery = new BasicDBObject();
					interavlQuery.put("id", IDSequence.getGroupId());
					interavlQuery.put("mProgId", id);
					interavlQuery.put("userId", programe.getAssignedUid()
							.split(",")[i]);
					interavlQuery.put("userName", programe.getAssignedName()
							.split(",")[i]);
					interavlQuery.put("userMobile", programe
							.getAssignedMobile().split(",")[i]);
					interavlQuery.put("progTitle", programe.getProgTitle());
					interavlQuery.put("progStartDate",
							programe.getProgStartDate());
					interavlQuery.put("progStartTime",
							programe.getProgStartTime());
					interavlQuery.put("progEndDate", programe.getProgEndDate());
					interavlQuery.put("progEndTime", programe.getProgEndTime());
					interavlQuery.put("isDay", programe.getIsDay());
					interavlQuery.put("reminderType",
							programe.getReminderType());
					interavlQuery.put("reminderLong",
							programe.getReminderLong());
					interavlQuery.put("progKind", programe.getProgKind());
					interavlQuery.put("progStatus", PROG_STATUS_00);
					interavlQuery.put("progType", PROG_TYPE_01);
					interavlQuery.put("appointFlag", APPOINT_FLAG_1);
					interavlQuery.put("partakeFlag", PARTAKE_FLAG_1);

					MongoDBSupport.getInstance().save(interavlQuery, PROGRAME);
				}
			}
		} else if (PROG_TYPE_4 == programe.getType()) {
			query.put("progType", PROG_TYPE_02);
			query.put("appointFlag", APPOINT_FLAG_1);
			query.put("partakeFlag", PARTAKE_FLAG_1);
			query.put("intervalTimeList", programe.getIntervalTimeList());

			// 插入指派用户
			if (null != programe.getAssignedUid()
					&& !"".equals(programe.getAssignedUid())) {
				for (int i = 0; i < programe.getAssignedUid().split(",").length; i++) {
					BasicDBObject interavlQuery = new BasicDBObject();
					interavlQuery.put("id", IDSequence.getGroupId());
					interavlQuery.put("intervalProgId", id);
					interavlQuery.put("assignedUserId", programe
							.getAssignedUid().split(",")[i]);
					interavlQuery.put("assignedUserName", programe
							.getAssignedName().split(",")[i]);
					interavlQuery.put("assignedUserMobile", programe
							.getAssignedMobile().split(",")[i]);
					interavlQuery.put("progTitle", programe.getProgTitle());
					interavlQuery.put("progStartDate",
							programe.getProgStartDate());
					interavlQuery.put("progStartTime",
							programe.getProgStartTime());
					interavlQuery.put("progEndDate", programe.getProgEndDate());
					interavlQuery.put("progEndTime", programe.getProgEndTime());
					interavlQuery.put("isDay", programe.getIsDay());
					interavlQuery.put("reminderType",
							programe.getReminderType());
					interavlQuery.put("reminderLong",
							programe.getReminderLong());
					interavlQuery.put("progKind", programe.getProgKind());
					interavlQuery.put("progStatus", PROG_STATUS_00);
					interavlQuery.put("progType", PROG_TYPE_02);
					interavlQuery.put("appointFlag", APPOINT_FLAG_1);
					interavlQuery.put("partakeFlag", PARTAKE_FLAG_1);

					MongoDBSupport.getInstance().save(interavlQuery, PROGRAME);
				}
			}

			// 插入周期
			if (null != programe.getIntervalTimeList()
					&& !"".equals(programe.getIntervalTimeList())) {
				for (String interval : programe.getIntervalTimeList()
						.split(",")) {
					BasicDBObject interavlQuery = new BasicDBObject();
					interavlQuery.put("id", IDSequence.getGroupId());
					interavlQuery.put("intervalProgId", id);
					interavlQuery.put("intervalTime", interval);
					interavlQuery.put("intervalTimeList",
							programe.getIntervalTimeList());
					MongoDBSupport.getInstance().save(interavlQuery,
							PROGRAME_INTERVAL);
				}
			}
		}
		MongoDBSupport.getInstance().save(query, PROGRAME);
	}

	/**
	 * 物理删除日程及相关表
	 * 
	 * @param programe
	 */
	public void removeProgrameAll(long id) {
		BasicDBObject deleteQuery = new BasicDBObject();
		deleteQuery.put("id", id);
		// 删除主键=id的日程
		MongoDBSupport.getInstance().removeDBObjects(PROGRAME, deleteQuery);
		deleteQuery = new BasicDBObject();
		deleteQuery.put("intervalProgId", id);
		// 删除主键=id的日程周期
		MongoDBSupport.getInstance().removeDBObjects(PROGRAME_INTERVAL,
				deleteQuery);
		deleteQuery = new BasicDBObject();
		deleteQuery.put("mProgId", id);
		// 删除主键=id的日程指派
		MongoDBSupport.getInstance().removeDBObjects(PROGRAME, deleteQuery);
		deleteQuery = new BasicDBObject();
		deleteQuery.put("intervalProgId", id);
		// 删除已经完成的日程
		MongoDBSupport.getInstance().removeDBObjects(PROGRAME_FINISH,
				deleteQuery);

	}

	/**
	 * 完成日程
	 * 
	 * @param uid
	 *            用户id
	 * @param ids
	 *            日程id数组
	 * @param days
	 *            日程开始时间数组
	 * @param progTypes
	 *            日程类型数组
	 */
	@Override
	public void finishPrograme(Long uid, String ids, String days,
			String progTypes) {
		for (int i = 0; i < ids.split(",").length; i++) {
			String id = ids.split(",")[i];
			BasicDBObject deleteQuery = new BasicDBObject();
			deleteQuery.put("intervalProgId", id);
			deleteQuery.put("userId", uid);
			if (PROG_TYPE_02.equals(progTypes.split(",")[i])) {
				deleteQuery.put("finishIntervalTime",
						getWeek(days.split(",")[i]));
			}
			// 删除已经完成的日程
			MongoDBSupport.getInstance().removeDBObjects(PROGRAME_FINISH,
					deleteQuery);

			BasicDBObject interavlQuery = new BasicDBObject();
			interavlQuery.put("id", IDSequence.getGroupId());
			interavlQuery.put("userId", uid);
			interavlQuery.put("intervalProgId", id);
			interavlQuery.put("progStartDay", days.split(",")[i]);
			if (PROG_TYPE_02.equals(progTypes.split(",")[i])) {
				interavlQuery.put("finishIntervalTime",
						getWeek(days.split(",")[i]));
			}
			interavlQuery.put("type", FINISH_TYPE_1);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式

			interavlQuery.put("finishTime", df.format(new Date()));
			MongoDBSupport.getInstance().save(interavlQuery, PROGRAME_FINISH);
		}
	}

	/**
	 * 查询当天日程
	 * 
	 * @param programe
	 * @return
	 */
	@Override
	public List<Programe> findDayList(Programe programe) {
		List<Programe> programes = null;
		List<ProgrameFinish> finish = new ArrayList<ProgrameFinish>();
		// 查询已经完成或者拒绝的日程
		BasicDBObject query1 = new BasicDBObject();// 条件
		query1.put("userId", programe.getUserId());
		query1.put("finishIntervalTime", getWeek(programe.getProgStartDate()));
		query1.put("progStartDay", programe.getProgStartDate());
		DBCursor cur1 = MongoDBSupport.getInstance().queryCollection(
				PROGRAME_FINISH, query1);
		if (cur1 != null && cur1.size() > 0) {
			Type type = new TypeToken<List<ProgrameFinish>>() {
			}.getType();
			finish = GsonUtil.gson.fromJson(cur1.toArray().toString(), type);
			cur1.close();
		}
		int size = finish.size();
		Long[] ids = new Long[size];
		for (int i = 0; i < size; i++) {
			ids[i] = finish.get(i).getIntervalProgId();
		}

		BasicDBObject query = new BasicDBObject();// 条件
		query.put("progStartDate", programe.getProgStartDate());
		query.put("userId", programe.getUserId());
		query.put("progType", PROG_TYPE_01);
		// query.put("progStatus", PROG_STATUS_00);

		BasicDBObject query2 = new BasicDBObject();
		query2.put("userId", programe.getUserId());
		// query2.put("progStatus", PROG_STATUS_00);

		query2.put("progType", PROG_TYPE_02);
		Pattern pattern = Pattern.compile(
				"^.*" + getWeek(programe.getProgStartDate()) + ".*$",
				Pattern.CASE_INSENSITIVE);
		query2.put("intervalTimeList", pattern);
		query2.put("progStartDate",
				new BasicDBObject("$lte", programe.getProgStartDate()));

		BasicDBObject query3 = new BasicDBObject();// 条件
		query3.put("id", new BasicDBObject(QueryOperators.IN, ids));

		List<BasicDBObject> orQuery = new ArrayList<BasicDBObject>();
		orQuery.add(query);
		orQuery.add(query2);
		if (size > 0) {
			orQuery.add(query3);
		}
		BasicDBObject param = new BasicDBObject("$or", orQuery);

		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("sortTime", 1);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(
				PROGRAME, param, sort);
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<Programe>>() {
			}.getType();
			programes = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
			cur.close();
		}

		return programes;
	}

	/**
	 * 查询日程详情
	 * 
	 * @param programe
	 * @return
	 */
	@Override
	public Programe findProgrameDetail(Programe programe) {
		Programe rPrograme = null;
		BasicDBObject param = new BasicDBObject();
		param.put("id", programe.getId());
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(PROGRAME,
				param, null);
		if (obj != null) {
			rPrograme = GsonUtil.gson.fromJson(obj.toString(), Programe.class);
		}
		return rPrograme;
	}

	/**
	 * 查询被指派人
	 * 
	 * @param programe
	 * @return
	 */
	@Override
	public List<Programe> findProgrameAppointList(Programe programe) {
		List<Programe> programes = null;
		BasicDBObject query = new BasicDBObject();// 条件
		query.put("mProgId", programe.getId());

		DBCursor cur = MongoDBSupport.getInstance().queryCollection(PROGRAME,
				query);
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<Programe>>() {
			}.getType();
			programes = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
			cur.close();
		}
		return programes;
	}

	/**
	 * 查询多天日程
	 * 
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @param uid
	 *            用户id
	 * @return
	 */
	@Override
	public List<Map<String, List<Programe>>> findMoreDayList(String startDate,
			String endDate, Long uid) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
		List<Map<String, List<Programe>>> list = new ArrayList<Map<String, List<Programe>>>();
		try {
			java.util.Date dateBegin = formater.parse(startDate);
			java.util.Date dateEnd = formater.parse(endDate);
			Calendar ca = Calendar.getInstance();

			while (dateBegin.compareTo(dateEnd) <= 0) {
				ca.setTime(dateBegin);
				ca.add(ca.DATE, 1);// 把dateBegin加上1天然后重新赋值给date1
				dateBegin = ca.getTime();
				Programe p = new Programe();
				p.setUserId(uid);
				p.setProgStartDate(formater.format(dateBegin));
				List<Programe> ls = findDayList(p);

				if (ls != null) {
					Map<String, List<Programe>> map = new HashMap<String, List<Programe>>();
					map.put(formater.format(dateBegin), ls);
					list.add(map);
				}
			}
		} catch (ParseException e) {
			logger.error("", e);
		}

		return list;
	}

	/**
	 * 查询列表日程
	 * 
	 * @param date
	 *            时间
	 * @param type
	 *            类型 1 代办 2已完成 3指派 4全部
	 * @param uid
	 *            用户id
	 * @return
	 */
	@Override
	public List<Map<String, List<Programe>>> findDayPageList(String date,
			int type, Long uid) {
		List<ProgrameFinish> finishList = null;
		List<Programe> pList = null;
		List<Programe> rList = new ArrayList<Programe>();
		if (type == 2) {
			finishList = getFinishPrograme(date, uid, FINISH_TYPE_1);
			Programe p = new Programe();
			p.setProgStartDate(date);
			p.setUserId(uid);
			pList = findDayList(p);
			if (finishList != null && pList != null) {
				for (ProgrameFinish pf : finishList) {
					for (Programe prog : pList) {
						if (pf.getIntervalProgId().longValue() == prog.getId()
								.longValue()) {
							rList.add(prog);
						}
					}
				}
			}
		}

		return null;
	}

	public List<ProgrameFinish> getFinishPrograme(String date, Long uid,
			String t) {
		List<ProgrameFinish> programes = null;
		BasicDBObject query = new BasicDBObject();// 条件
		// query.put("finishTime",date);
		query.put("userId", uid);
		query.put("type", t);

		DBCursor cur = MongoDBSupport.getInstance().queryCollection(
				PROGRAME_FINISH, query);
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<ProgrameFinish>>() {
			}.getType();
			programes = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
			cur.close();
		}
		return programes;
	}

	/**
	 * 驳回指派日程
	 * 
	 * @param uid
	 *            用户id
	 * @param id
	 *            日程id
	 * @param content
	 *            驳回原因
	 */
	@Override
	public void rejectPrograme(Long uid, int id, String content,
			String startDate) {
		BasicDBObject deleteQuery = new BasicDBObject();
		deleteQuery.put("intervalProgId", id);
		deleteQuery.put("userId", uid);
		// 删除已经完成的日程
		MongoDBSupport.getInstance().removeDBObjects(PROGRAME_FINISH,
				deleteQuery);

		BasicDBObject interavlQuery = new BasicDBObject();
		interavlQuery.put("id", id);
		interavlQuery.put("userId", uid);
		interavlQuery.put("intervalProgId", id);
		interavlQuery.put("type", FINISH_TYPE_2);
		interavlQuery.put("content", content);
		interavlQuery.put("finishTime", getCurrentDay());
		MongoDBSupport.getInstance().save(interavlQuery, PROGRAME_FINISH);

		BasicDBObject query = new BasicDBObject();
		query.put("id", id);
		BasicDBObject param = new BasicDBObject();
		param.put("progStatus", PROG_STATUS_88);
		MongoDBSupport.getInstance().updateCollection(PROGRAME_FINISH, query,
				param);
	}

	public String getCurrentDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
		return df.format(new Date());
	}
}
