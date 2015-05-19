package com.zte.im.twitter.execute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.TGroup;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.ITwitterService;
import com.zte.im.service.impl.TwitterServiceImpl;

/**
 * dec 获取微博圈子列表
 * 
 * @author xubin
 * 
 */
public class GetGroupByUserId extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(GetGroupByUserId.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ITwitterService twitterService = TwitterServiceImpl.getInstance();
			Long userId = json.getLong("userId");
			Long markTime = json.getLong("markTime");
			List<TGroup> list = twitterService.FindGroupByUserId(userId, markTime);
			if(list == null){
				list = new ArrayList<TGroup>();
				res.put("markTime", markTime);
			}else{
				res.put("markTime", list.get(0).getCreateTime());
			}
			res.put("resList", list);
			map.put("resCode", "1");
			map.put("resMessage", "访问正常");
			map.put("vs", "");
			res.put("res", map);
		} catch (Exception e) {
			logger.error("", e);
		}
		resJson = JSON.toJSONString(res);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("pageNum", 1);
		json.put("pageSize", 10);
		json.put("userId", 1000016);
		json.put("token", "4454503_IYIMHNPZ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new GetGroupByUserId();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("findGroupByUserId");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
