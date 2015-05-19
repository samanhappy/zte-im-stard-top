package com.zte.im.twitter.execute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.TSysMessage;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.impl.TwitterServiceImpl;

/**
 * 获取系统信息列表
 * 
 * @author kaka
 * 
 */
public class GetSysMessage extends AbstractValidatoExecute {

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Long userId = json.getLong("userId");

		List<TSysMessage> msgList = TwitterServiceImpl.getInstance()
				.findMsgByUserId(userId);// 所有新消息
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("resFlag", "true");
		res.put("msg", "");
		res.put("resList", msgList);
		resJson = JSON.toJSONString(res);
		return resJson;
	}

}
