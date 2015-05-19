package com.zte.im.twitter.execute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.MyPageBean;
import com.zte.im.bean.TTopic;
import com.zte.im.bean.TTwitter;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.Constant;

public class DiscoverIndex extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(DiscoverIndex.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = "true";
		String msg = "";
		try {
			MyPageBean pageBean = new MyPageBean();
			
			Long userId = super.getContext().getUser().getUid();
			pageBean.setPageSize(json.getInteger("twitterPageSize"));
			pageBean.setPointId(1L);
			pageBean.setQueryType(Constant.TWITTER_QUERYTYPE_NEXTPAGE);
			List<TTwitter> twitterList = TwitterServiceImpl.getInstance().getTwittersByKw(userId, null, Constant.TWITTER_TYPE_01, pageBean);
			
			MyPageBean tpageBean = new MyPageBean();
			tpageBean.setPageSize(json.getInteger("topicPageSize"));
			tpageBean.setPointId(1L);
			tpageBean.setQueryType(Constant.TWITTER_QUERYTYPE_REFRESH);
			List<TTopic> topicList = TwitterServiceImpl.getInstance()
					.getTopicsByKw(null, tpageBean) == null ? new ArrayList<TTopic>()
					: TwitterServiceImpl.getInstance().getTopicsByKw(null,
							tpageBean);
			res.put("twitterList", twitterList);
			res.put("topicList", topicList);
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
		json.put("twitterPageSize", 3);
		json.put("topicPageSize", 5);
		json.put("token", "3530851_GXD207I5");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new DiscoverIndex();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("discoverIndex");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
