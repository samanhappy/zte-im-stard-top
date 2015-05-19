package com.zte.im.execute.programe;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.Programe;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.ProgrameServiceImpl;

/**
 * dec 修改日程
 * 
 * @author cy
 * 
 */
public class UpdatePrograme extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(UpdatePrograme.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> res1 = new HashMap<String, Object>();

		try {
			Long uid = json.getLong("uid");
			Long id = json.getLong("id");
			String title = json.getString("title");
			String progStartDate = json.getString("progStartDate");
			String progStartTime = json.getString("progStartTime");
			String progEndDate = json.getString("progEndDate");
			String progEndTime = json.getString("progEndTime");
			String progKind = json.getString("progKind");
			int isDay = json.getIntValue("isDay");
			int reminderType = json.getIntValue("reminderType");
			int reminderLong = json.getIntValue("reminderLong");
			int remindAhead = json.getIntValue("remindAhead");
			int remindFlag = json.getIntValue("remindFlag");
			String address = json.getString("address");
			String progContent = json.getString("progContent");
			String assignedUid = json.getString("assignedUid");
			String assignedName = json.getString("assignedName");
			String assignedMobile = json.getString("assignedMobile");
			String interval = json.getString("interval");
			int type = json.getIntValue("type");

			Programe programe = new Programe();
			programe.setId(id);
			programe.setProgTitle(title);
			programe.setUserId(uid);
			programe.setProgStartDate(progStartDate);
			programe.setProgEndTime(progEndTime);
			programe.setProgEndDate(progEndDate);
			programe.setProgStartTime(progStartTime);
			programe.setIsDay(isDay);
			programe.setReminderLong(reminderLong);
			programe.setReminderType(reminderType);
			programe.setProgKind(progKind);
			programe.setRemindAhead(remindAhead);
			programe.setAddress(address);
			programe.setProgContent(progContent);
			programe.setAssignedUid(assignedUid);
			programe.setAssignedName(assignedName);
			programe.setAssignedMobile(assignedMobile);
			programe.setIntervalTimeList(interval);
			programe.setType(type);
			programe.setRemindFlag(remindFlag);

			ProgrameServiceImpl.getInstance().updatePrograme(programe);

			res1.put("reCode", "1");
			res1.put("resMessage", "修改成功");
		} catch (Exception e) {
			res1.put("reCode", "0");
			res1.put("resMessage", e.getMessage());
			logger.error("", e);
		}

		res.put("res", res1);
		resJson = JSON.toJSONString(res);
		return resJson;
	}

	public static void main(String[] args) throws ParseException {
		JSONObject json = new JSONObject();
		json.put("id", 1779l);
		json.put("uid", 1l);
		json.put("title", "买菜432432");
		json.put("progStartDate", "20141102");
		json.put("progStartTime", "08:00");
		json.put("progEndDate", "20141102");
		json.put("progEndTime", "23:59");
		json.put("isDay", 1);
		json.put("reminderType", 1);
		json.put("reminderLong", 15);
		json.put("progKind", "02");
		json.put("remindFlag", "1");
		json.put("remindType", "01");
		json.put("remindAhead", -15);
		json.put("progContent", "内容1");
		json.put("address", "地址1");
		json.put("type", "2");
		json.put("interval", "3,4");
		json.put("assignedUid", "2,3");
		json.put("assignedName", "张一,张二");
		json.put("assignedMobile", "123456,223456");

		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new UpdatePrograme();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("programe/updatePrograme");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
