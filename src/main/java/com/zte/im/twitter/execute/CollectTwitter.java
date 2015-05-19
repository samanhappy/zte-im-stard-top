package com.zte.im.twitter.execute;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.TTwitter;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.IDSequence;

/**
 * dec 收藏微博
 * 
 * @author xubin
 * 
 */
public class CollectTwitter extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(CollectTwitter.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = "true";
		String msg = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			Long userId = json.getLong("userId");
			Long twitterId = json.getLong("twitterId");
			String type = json.getString("type");

			TTwitter t = TwitterServiceImpl.getInstance().findTwitterDetail(
					twitterId, userId);
			if (t != null && "00".equals(t.getTwitterStatus())) {
				Long collectionNum = t.getCollectionNum();
				if (collectionNum == null) {
					collectionNum = 0L;
				}
				if ("00".equals(type)) {
					collectionNum = collectionNum - 1;
				} else if ("01".equals(type)) {
					collectionNum = collectionNum + 1;
				}
				// 更新微博收藏统计数
				TwitterServiceImpl.getInstance().updateTwitterNum(twitterId,
						"collectionNum", collectionNum);
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("collectId", IDSequence.getUID()); 
				param.put("userId", userId);
				param.put("twitterId", twitterId);
				param.put("createUserId", userId);
				param.put("createTime", sdf.format(date));
				param.put("updateUserId", userId);
				param.put("updateTime", sdf.format(date));
				param.put("remark", "");
				TwitterServiceImpl.getInstance().collectTwitter(param, type);
				
			} else if ("99".equals(t.getTwitterStatus())) {
				resFlag = "false";
				msg = "微博已删除！";
				logger.info("=======================微博已删除！");
			} else {
				resFlag = "false";
				msg = "微博不存在！";
				logger.info("=======================微博不存在！");
			}
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
		json.put("userId", 1000001);
		json.put("twitterId", 2013);
		json.put("type", "01");// 00：取消收藏；01：收藏
		json.put("token", "4602658_75JCEGDQ");

		logger.info("111");
		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new CollectTwitter();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("collectTwitter");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
