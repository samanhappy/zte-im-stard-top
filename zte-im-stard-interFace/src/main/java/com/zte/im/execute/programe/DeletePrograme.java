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
 * dec 删除日程
 * 
 * @author cy
 * 
 */
public class DeletePrograme extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(DeletePrograme.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> res1 = new HashMap<String, Object>();
		try {
			Long uid = json.getLong("uid");
			String ids = json.getString("ids");

			ProgrameServiceImpl.getInstance().removePrograme(uid, ids);
			res1.put("reCode", "1");
			res1.put("resMessage", "删除成功");
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
		json.put("ids", "1768");
		json.put("uid", 1l);

		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new DeletePrograme();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("programe/deletePrograme");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
