package com.zte.im.twitter.execute;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.TTwitter;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.ITwitterService;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.Constant;

/**
 * dec 获取系统微博列表
 * @author xubin
 *
 */
public class GetSysTwitter extends AbstractValidatoExecute {

	private final static Logger logger = LoggerFactory.getLogger(GetSysTwitter.class);
	
	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		String resFlag = Constant.TRUE;
		String msg = "";
		Map<String, Object> res = new HashMap<String, Object>();
		ITwitterService twitterService = TwitterServiceImpl.getInstance();
		try {
			Long userId = json.getLong("userId");
			Long timestamp = json.getLong("timeStamp");
			TTwitter twitter = twitterService.getSysTwitter(userId);
			if(twitter != null){ 
				//判断是否是最新的系统微博
                if (twitter.getUpdateTime() != null && twitter.getUpdateTime().longValue() != timestamp) {
                    res.put("resTwitter", twitter);
                } else if (twitter.getCreateTime().longValue() != timestamp) {
                    res.put("resTwitter", twitter);
                } else {
                    res.put("resTwitter", "");
                }
			}
		}catch (Exception e) {
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
        json.put("userId", 49590);
		json.put("timeStamp", -1); 
        json.put("token", "3839625_K6CUQET6");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new GetSysTwitter();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("getSupports");
		String result = e.doProcess(context);
		logger.info(result);
	}
}
