package com.zte.im.execute.programe;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.ProgrameServiceImpl;

/**
 * dec 驳回指派日程
 * 
 * @author cy
 * 
 */
public class RejectPrograme extends AbstractValidatoExecute {
	
	private final static Logger logger = LoggerFactory
			.getLogger(RejectPrograme.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Long uid = json.getLong("uid");
		int id = json.getIntValue("id");
		String content = json.getString("content");
		String startDate = json.getString("startDate");

		ProgrameServiceImpl.getInstance().rejectPrograme(uid, id, content,
				startDate);
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> res1 = new HashMap<String, Object>();
		res1.put("reCode", "1");
		res1.put("resMessage", "完成成功");
		res.put("res", res1);

		resJson = JSON.toJSONString(res);
		return resJson;
	}

	public static void main(String[] args) throws ParseException {
		JSONObject json = new JSONObject();
		json.put("id", 1549);
		json.put("content", "无法参与");
		json.put("startDate", "20141105");
		json.put("uid", 1l);

		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new RejectPrograme();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("programe/rejectPrograme");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
