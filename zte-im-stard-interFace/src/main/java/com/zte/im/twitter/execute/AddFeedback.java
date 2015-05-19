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

public class AddFeedback extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(AddFeedback.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> res2 = new HashMap<String, Object>();
		String reCode = Constant.TRUE;
		String msg = "";
		try {
			Date date = new Date();
			Long userId = json.getLong("userId");
			String feedbackContent = json.getString("feedbackContent");
			Map<String, Object> param = new HashMap<String, Object>();
			Long feedbackId = IDSequence.getUID();
			param.put("feedbackId", feedbackId);
			param.put("userId", userId);
			param.put("feedbackContent", feedbackContent);
			param.put("feedbackStatus", Constant.TWITTER_FEED_01);
			param.put("createUserId", userId);
			param.put("createTime", date.getTime());
			param.put("updateUserId", userId);
			param.put("updateTime", date.getTime());
			param.put("remark", "");
		 	TwitterServiceImpl.getInstance().addFeedBack(param);
		} catch (Exception e) {
			reCode= Constant.ERROR_CODE;
			msg = e.getMessage();
			logger.error("", e);
		}
		res2.put("resMessage", ""+msg);
		res2.put("resCode", reCode);
		res2.put("vs", Constant.TRUE);
		res.put("res", res2);
		resJson = JSON.toJSONString(res);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("userId", 1000037);
		json.put("feedbackContent", "好棒啊");
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new AddFeedback();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("addFeedback");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
