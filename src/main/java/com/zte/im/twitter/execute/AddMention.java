package com.zte.im.twitter.execute;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.IDSequence;

public class AddMention extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(AddMention.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = "true";
		String msg = "";
		try {
			Date date = new Date();
			Long userId = json.getLong("userId");
			Map<String, Object> param = new HashMap<String, Object>();
			Long mentionId = IDSequence.getUID();
			param.put("mentionId", mentionId);
			param.put("twitterId", json.getLong("twitterId"));
			param.put("commentId", json.getLong("commentId"));
			param.put("userId", userId);
			param.put("mentionUserId", json.getLong("mentionUserId"));
			param.put("mentionType", json.getString("mentionType"));
			param.put("mentionStatus", "00");
			param.put("createUserId", userId);
			param.put("createTime", date.getTime());
			param.put("updateUserId", userId);
			param.put("updateTime", date.getTime());
			param.put("remark", "");
			TwitterServiceImpl.getInstance().addMention(param);

			res.put("mentionId", mentionId);
		} catch (Exception e) {
			resFlag = "false";
			msg = e.getMessage();
			logger.error("", e);
		}
		res.put("msg", msg);
		res.put("resFlag", resFlag);
		resJson = JSON.toJSONString(res);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("userId", 1000001);
		json.put("twitterId", 2011);
		json.put("commentId", 1111);
		json.put("mentionUserId", 1000002);
		json.put("mentionType", "01");
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new AddMention();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("addMention");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
