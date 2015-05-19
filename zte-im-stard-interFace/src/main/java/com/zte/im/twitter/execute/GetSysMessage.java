package com.zte.im.twitter.execute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.TSysMessage;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.Constant;

/**
 * 获取系统信息列表
 * 
 * @author kaka
 * 
 */
public class GetSysMessage extends AbstractValidatoExecute {

	private final static Logger logger = LoggerFactory
			.getLogger(GetSysMessage.class);
	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Long userId = json.getLong("userId");
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = Constant.TRUE;
		String msg = ""; 
		Map<String, Object> res2 = new HashMap<String, Object>();
		
		try{
			List<TSysMessage> msgList = TwitterServiceImpl.getInstance()
					.findMsgByUserId(userId);// 所有新消息 
			res.put("resList", msgList);
		}
		catch (Exception e) {
			logger.error("", e);
			resFlag = Constant.ERROR_CODE;
			msg = e.getMessage(); 
		}
		res2.put("resMessage", ""+msg);
		res2.put("resCode", resFlag);
		res2.put("vs", Constant.TRUE);
		res.put("res", res2);
		resJson = JSON.toJSONString(res);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject(); 
		json.put("userId", 1000037);
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new GetSysMessage();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("getSysMessage");
		String result = e.doProcess(context);
		logger.info(result);
	}
}
