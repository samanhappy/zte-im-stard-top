package com.zte.im.twitter.execute;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.TGroup;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.Constant;

/**
 * 通过群主ID 更新群主介绍
 * 
 * @author Administrator
 * 
 */
public class GroupIntroductionUpdate extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(GroupIntroductionUpdate.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		String resFlag = Constant.TRUE;
		String msg = "";
		Map<String, Object> res = new HashMap<String, Object>();
		Date date = new Date();

		try {
	  	Long uid = super.getContext().getUser().getUid();
		 
			Long groupId = json.getLong("groupId");
			String groupIntroduction = json.getString("groupIntroduction");

			TwitterServiceImpl gservice = TwitterServiceImpl.getInstance();
			TGroup group = gservice.findGroupDetail(groupId);
			if (group != null) {
				if (groupIntroduction != null) {
					// 更新签名
					group.setGroupIntroduction(groupIntroduction);
					group.setUpdateTime(date.getTime());
					group.setUpdateUserId(uid);
					gservice.UpdateGroupIntroduction(group);
				} else {
					msg = Constant.CODE_7;
					resFlag = Constant.CODE_7;
				}
			} else {
				msg = Constant.CODE_3;
				resFlag = Constant.CODE_3;
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
		resJson = JSON.toJSONString(res);
		return resJson;
	}
	
	public static void main(String[] args) {
		JSONObject json = new JSONObject(); 
		json.put("groupId", 30898);
		json.put("groupIntroduction", "更新");
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new GroupIntroductionUpdate();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("updateGroup");
		String result = e.doProcess(context);
		logger.info(result);
	}
}
