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
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.Constant;

/**
 * 获取话题列表
 * 
 * @author Administrator
 * 
 */
public class GetTopicList extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(GetTopicList.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		String resFlag = Constant.TRUE;
		String msg = "";
		Map<String, Object> res = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			// Long userId = json.getLong("userId");
			String topicName = json.getString("topicName");
			String queryType = json.getString("queryType");
			Integer pointId = json.getInteger("pointId");
			Integer pageSize = json.getInteger("pageSize");

			TwitterServiceImpl service = TwitterServiceImpl.getInstance();
			MyPageBean page = new MyPageBean();
			page.setPageSize(pageSize);
			page.setPointId(Long.valueOf(pointId));
			page.setQueryType(queryType); 
			List<TTopic> obj = service.getTopicList(topicName, page);

			int count = 0;
			if (null != obj && !obj.isEmpty()) {
				for (TTopic l : obj) {
					Map<String, Object> resComment = new HashMap<String, Object>();
					resComment.put("topicId", String.valueOf(l.getTopicId()));
					resComment.put("topicName", l.getTopicName());
					resComment.put("createUserId",
							String.valueOf(l.getCreateUserId()));
					resComment.put("createTime", l.getCreateTime());
					resComment.put("useNum", String.valueOf(l.getUseNum()));
					list.add(resComment);
					count++;
					if (count >= page.getPageSize()) {
						break;
					}
				}
				if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(page
						.getQueryType())) {
					res.put("totalNum", obj.size());
				} else {
					res.put("totalNum", "");
				}
				res.put("returnNum", count);
			} else {
				if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(page
						.getQueryType())) {
					res.put("totalNum", 0);
				} else {
					res.put("totalNum", "");
				}
				res.put("returnNum", 0);
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
		res.put("topicList", list);
		resJson = JSON.toJSONString(res);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("pointId", 1);
		json.put("pageSize", 4);
		json.put("queryType", "01");
		json.put("topicName", "测试");
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new GetTopicList();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("getTopic");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
