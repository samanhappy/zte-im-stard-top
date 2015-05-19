package com.zte.im.twitter.execute;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.TTwitter;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.ITwitterService;
import com.zte.im.service.impl.TwitterServiceImpl;

public class CancelSupport extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(CancelSupport.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = "true";
		String msg = "";
		ITwitterService twitterServce = TwitterServiceImpl.getInstance();
		try {
			Long userId = json.getLong("userId");
			Long twitterId = json.getLong("twitterId");
			TTwitter t = twitterServce.findTwitterDetail(twitterId, userId);
			twitterServce.cancelSupport(userId, twitterId);
			Long supportNum = t.getSupportNum();
			if (supportNum == null) {
				supportNum = 0L;
			}
			// 更新微博点赞统计数
			twitterServce.updateTwitterNum(twitterId,
					"supportNum", supportNum - 1);
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
}
