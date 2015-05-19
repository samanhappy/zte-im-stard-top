package com.zte.im.twitter.execute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.TAttention;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;

public class GetAttentions extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(GetAttentions.class);

	@Override
	public String doProcess(JSONObject json) {
		
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = "true";
		String msg = "";
		try {
			Long userId = json.getLong("userId");
			List<TAttention> list = TwitterServiceImpl.getInstance()
					.getAttentsByUserId(userId) == null ? new ArrayList<TAttention>()
					: TwitterServiceImpl.getInstance().getAttentsByUserId(
							userId);
			res.put("resList", list);
		} catch (Exception e) {
			resFlag = "false";
			msg = e.getMessage();
			logger.error("", e);
		}
		res.put("resFlag", resFlag);
		res.put("msg", msg);
		resJson = JSON.toJSONString(res);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("userId", 1000001);
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new GetAttentions();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("getAttentions");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
