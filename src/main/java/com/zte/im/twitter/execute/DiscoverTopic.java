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
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.Constant;

public class DiscoverTopic extends AbstractValidatoExecute {

	private static Logger logger = LoggerFactory.getLogger(DiscoverTopic.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = "true";
		String msg = "";
		try {
			MyPageBean pageBean = new MyPageBean();
			pageBean.setPageSize(json.getInteger("pageSize"));
			pageBean.setPointId(json.getLong("pointId"));
			pageBean.setQueryType(json.getString("queryType"));
			List<TTopic> topicList = TwitterServiceImpl.getInstance()
					.getTopicsByKw(null, pageBean) == null ? new ArrayList<TTopic>()
					: TwitterServiceImpl.getInstance().getTopicsByKw(null,
							pageBean);
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
			msg = "获取发现首页话题";
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
}
