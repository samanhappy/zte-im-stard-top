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
import com.zte.im.util.Constant;

public class GetAttentions extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(GetAttentions.class);

	@Override
	public String doProcess(JSONObject json) {
		
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = Constant.TRUE;
		String msg = "";
		try {
			Long userId = json.getLong("userId");
			List<TAttention> list = TwitterServiceImpl.getInstance()
					.getAttentsByUserId(userId) == null ? new ArrayList<TAttention>()
					: TwitterServiceImpl.getInstance().getAttentsByUserId(
							userId);
			res.put("resList", list);
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
		json.put("userId", 1000037);
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
