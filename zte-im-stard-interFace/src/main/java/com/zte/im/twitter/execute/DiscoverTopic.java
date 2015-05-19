package com.zte.im.twitter.execute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.MyPageBean;
import com.zte.im.bean.TTopic;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.Constant;

public class DiscoverTopic extends AbstractValidatoExecute {

	private static Logger logger = LoggerFactory.getLogger(DiscoverTopic.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = Constant.TRUE;
		String msg = "";
		try {
			MyPageBean pageBean = new MyPageBean();
			pageBean.setPageSize(json.getInteger("pageSize"));
			pageBean.setPointId(json.getLong("pointId"));
			pageBean.setPageNum(1);
			pageBean.setQueryType(json.getString("queryType"));
			List<TTopic> topicList = TwitterServiceImpl.getInstance().getTopicsByKw(null, pageBean);
			int count = 0;
			if (null != topicList && !topicList.isEmpty()) {
				if (topicList.size() >= pageBean.getPageSize()) {
					count = pageBean.getPageSize();
				} else {
					count = topicList.size();
				}
				if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
						.getQueryType())) {
					res.put("totalNum", topicList.size());
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
			res.put("topicList", topicList);
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
		//json.put("userId", 1000001);
		json.put("pageSize", 5);
		json.put("pointId", 1);
		json.put("queryType", "01");
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new DiscoverTopic();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("discoverTopic");
		String result = e.doProcess(context);
		logger.info(result);
	}
}
