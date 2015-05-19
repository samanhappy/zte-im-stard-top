package com.zte.im.twitter.execute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.MyPageBean;
import com.zte.im.bean.TTwitter;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.Constant;

public class DiscoverTwitter extends AbstractValidatoExecute {

	private static Logger logger = LoggerFactory.getLogger(DiscoverTwitter.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = "true";
		String msg = "";
		try {
			MyPageBean pageBean = new MyPageBean();
			Long userId = json.getLong("userId");
			pageBean.setPageSize(json.getInteger("pageSize"));
			pageBean.setPointId(json.getLong("pointId"));
			pageBean.setQueryType(json.getString("queryType"));
			List<TTwitter> twlist = TwitterServiceImpl.getInstance()
					.getTwittersByKw(userId, null, Constant.TWITTER_TYPE_01,
							pageBean);
			int count = 0;
			if (null != twlist && !twlist.isEmpty()) {
				if (twlist.size() >= pageBean.getPageSize()) {
					count = pageBean.getPageSize();
				} else {
					count = twlist.size();
				}
				if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
						.getQueryType())) {
					res.put("totalNum", twlist.size());
				} else {
					res.put("totalNum", "");
				}
			} else {
				if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
						.getQueryType())) {
					res.put("totalNum", 0);
				} else {
					res.put("totalNum", "");
				}
			}
			
			res.put("returnNum", count);
			msg = "获取发现首页微博";
			res.put("twitterList", twlist);
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
		json.put("pageSize", 5);
		json.put("token", "3530851_GXD207I5");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new DiscoverTwitter();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("discoverTwitter");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
