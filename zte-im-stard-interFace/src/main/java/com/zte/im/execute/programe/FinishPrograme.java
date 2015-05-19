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
 * dec 完成日程
 * 
 * @author cy
 * 
 */
public class FinishPrograme extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(FinishPrograme.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> res1 = new HashMap<String, Object>();
		try {
			Long uid = json.getLong("uid");
			String ids = json.getString("ids");
			String days = json.getString("days");
			String progTypes = json.getString("progTypes");

			ProgrameServiceImpl.getInstance().finishPrograme(uid, ids, days,
					progTypes);

			res1.put("reCode", "1");
			res1.put("resMessage", "完成成功");
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
		json.put("ids", "1779");
		json.put("days", "20141103");
		json.put("progTypes", "02");
		json.put("uid", 1l);

		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new FinishPrograme();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("programe/finfishPrograme");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
