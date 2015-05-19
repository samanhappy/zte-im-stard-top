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
 * dec 删除圈子成员
 * 
 * @author xubin
 * 
 */
public class RemoveGroupUser extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(RemoveGroupUser.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = "true";
		String msg = "";
		try {
			Long groupId = json.getLong("groupId");
			Long userId = json.getLong("userId");
			TwitterServiceImpl.getInstance().removeGroupUser(groupId, userId);
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
		json.put("userId", 695);
		json.put("groupId", 1046);
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new RemoveGroupUser();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("removeGroupUser");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
