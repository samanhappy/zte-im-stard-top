package com.zte.im.twitter.execute;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;

/**
 * dec 删除微博
 * 
 * @author xubin
 * 
 */
public class DelTwitter extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(DelTwitter.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = "true";
		String msg = "";
		try {
			Long twitterId = json.getLong("twitterId");
			TwitterServiceImpl.getInstance().delTwitter(twitterId);
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
		json.put("twitterId", 11);

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new DelTwitter();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("delTwitter");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
