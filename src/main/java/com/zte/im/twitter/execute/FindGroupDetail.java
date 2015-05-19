package com.zte.im.twitter.execute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.TGroup;
import com.zte.im.bean.User;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;

/**
 * 获取圈子详情
 * 
 * @author xubin
 * 
 */
public class FindGroupDetail extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(FindGroupDetail.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = "true";
		String msg = "";
		try {
			Long groupId = json.getLong("groupId");
			TGroup group = TwitterServiceImpl.getInstance().findGroupDetail(
					groupId) == null ? new TGroup() : TwitterServiceImpl
					.getInstance().findGroupDetail(groupId);
			List<User> users = TwitterServiceImpl.getInstance()
					.findUsersByGroupId(groupId) == null ? new ArrayList<User>()
					: TwitterServiceImpl.getInstance().findUsersByGroupId(
							groupId);
			res.put("group", group);
			res.put("users", users);
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
		json.put("groupId", 1046);
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new FindGroupDetail();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("FindGroupDetail");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
