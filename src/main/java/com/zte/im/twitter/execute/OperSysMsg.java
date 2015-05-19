package com.zte.im.twitter.execute;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.TSysMessage;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;

/**
 * 处理系统信息
 * 
 * @author kaka
 * 
 */
public class OperSysMsg extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(OperSysMsg.class);

	private static String OPERTYPE_YES = "01";// 同意

	private static String MESSAGETYPE_01 = "01";// 申请

	private static String MESSAGETYPE_02 = "02";// 邀请

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		String resFlag = "true";
		String msg = "请求成功";
		try {
			Long userId = json.getLong("userId");
			Long messageId = json.getLong("messageId");
			String operType = json.getString("operType");
			if (null != operType && OPERTYPE_YES.equals(operType)) {
				TSysMessage sysMsg = TwitterServiceImpl.getInstance()
						.getSysMessage(messageId);
				if (null != sysMsg) {
					Long groupId = sysMsg.getGroupId();
					Date date = new Date();
					String msgType = sysMsg.getMessageType();
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("groupId", groupId);
					param.put("status", "00");
					param.put("createUserId", userId);
					param.put("createTime", date.getTime());
					param.put("updateUserId", userId);
					param.put("updateTime", date.getTime());
					param.put("remark", "");
					if (null != msgType && MESSAGETYPE_01.equals(msgType))// 申请
					{
						param.put("userId", sysMsg.getSendUserId());
					} else if (null != msgType
							&& MESSAGETYPE_02.equals(msgType))// 邀请
					{
						param.put("userId", userId);
					}
					TwitterServiceImpl.getInstance().inviteGroupUser(param);
				}
			}
			TwitterServiceImpl.getInstance().operSysMsg(messageId, operType,
					userId);// 更新状态
		} catch (Exception e) {
			resFlag = "false";
			msg = e.getMessage();
			logger.error("", e);
		}
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("resFlag", resFlag);
		res.put("msg", msg);
		resJson = JSON.toJSONString(res);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("userId", 33);
		json.put("token", "4602658_75JCEGDQ");
		json.put("messageId", 2142);
		json.put("operType", "01");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new OperSysMsg();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("operSysMsg");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
