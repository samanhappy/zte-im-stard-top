package com.zte.im.twitter.execute;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.TSupport;
import com.zte.im.bean.TTwitter;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.ITwitterService;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.Constant;

public class CancelSupport extends AbstractValidatoExecute {

	private static Logger logger = LoggerFactory.getLogger(CancelSupport.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = Constant.TRUE;
		String msg = "";
		ITwitterService twitterServce = TwitterServiceImpl.getInstance();
		try {
			Long userId = json.getLong("userId");
			Long twitterId = json.getLong("twitterId");
			TTwitter t = twitterServce.findTwitterDetail(twitterId, userId);
			TSupport tSupport = twitterServce.getSupport(userId, twitterId);
			if(tSupport!=null){
				twitterServce.cancelSupport(userId, twitterId);
				Long supportNum = t.getSupportNum();
				if (supportNum == null) {
					supportNum = 0L;
				}
				if(supportNum > 0l){
					// 更新微博点赞统计数
					twitterServce.updateTwitterNum(twitterId,
							"supportNum", supportNum - 1);
				}
			}
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
		json.put("twitterId", 46977);
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new CancelSupport();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("applyIntoGroup");
		String result = e.doProcess(context);
		logger.info(result);
	}
}
