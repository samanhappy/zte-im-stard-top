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
import com.zte.im.util.Constant;
import com.zte.im.util.IDSequence;

/**
 * dec 新建圈子
 * 
 * @author xubin
 * 
 */
public class AddTwitterGroup extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(AddTwitterGroup.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = Constant.TRUE;
		String msg = "";
		try {
			Date date = new Date();
			Long userId = json.getLong("userId");
			Map<String, Object> param = new HashMap<String, Object>();
			Long groupId = IDSequence.getUID();
			param.put("groupId", groupId);
			param.put("groupName", json.getString("groupName"));
			param.put("groupIntroduction", json.getString("groupIntroduction"));
			param.put("groupStatus", json.getString("groupStatus"));
			param.put("imgUrl", json.getString("imgUrl"));
			param.put("groupType", json.getString("groupType"));
			param.put("createUserId", userId);
			param.put("createTime", date.getTime());
			param.put("updateUserId", userId);
			param.put("updateTime", date.getTime());
			param.put("remark", "");
	 		TwitterServiceImpl.getInstance().addTwitterGroup(param);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			params.put("groupId", groupId);
			params.put("createUserId", userId);
			params.put("createTime", date.getTime());
			params.put("updateUserId", userId);
			params.put("updateTime", date.getTime());
			params.put("remark", "");
	 		TwitterServiceImpl.getInstance().inviteGroupUser(params);
	 		res.put("groupId",groupId);
		} catch (Exception e) {
			resFlag = Constant.ERROR_CODE;
			msg = e.getMessage();
			logger.error("", e);
		}
		Map<String, Object> res2 = new HashMap<String, Object>();
		res2.put("resMessage", ""+msg);
		res2.put("resCode", resFlag);
		res2.put("vs", Constant.TRUE);
		res.put("res", res2);
		resJson = JSON.toJSONString(res);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("userId", 1000002);
		json.put("groupName", "g1");
		json.put("groupIntroduction", "g1");
		json.put("groupStatus", "00");
		json.put("groupType", "01");// 01：工作圈；02：私密圈
		json.put("imgUrl", "");
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new AddTwitterGroup();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("addTwitterGroup");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
