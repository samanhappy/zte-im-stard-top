package com.zte.im.twitter.execute;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.TAttention;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.Constant;

/**
 * 加好友关注
 * 
 * @author Administrator
 * 
 */
public class AddFriend extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(AddFriend.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		String resFlag = Constant.TRUE;
		String msg = "";
		String eachOtherFlag = "0";
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			Long userId = json.getLong("userId");
			Long attentionUserId = json.getLong("attentionUserId");
			String attentionType = json.getString("attentionType");

			Date date = new Date();

			TwitterServiceImpl service = TwitterServiceImpl.getInstance();

			// 查询对方是否关注
			TAttention attention = service.FindAttentionByUid(attentionUserId,
					userId);
			if (null != attention) {
				// 更新 互相关注状态
				attention.setEachOtherFlag("1");
				attention.setUpdateUserId(userId);
				attention.setUpdateTime(date.getTime());
			  	service.UpdateAttentionByUid(attention);
				eachOtherFlag = "1";
			}
			param.put("userId", userId);
			param.put("attentionUserId", attentionUserId);
			param.put("eachOtherFlag", eachOtherFlag);
			param.put("attentionStatus", "00");
			param.put("attentionAttr", "01");
			param.put("attentionType", attentionType);
			param.put("createUserId", userId);
			param.put("createTime", date.getTime());
			param.put("updateUserId", userId);
			param.put("updateTime", date.getTime());
			param.put("remark", "");
	 	 	service.AddAttentionUid(param);
		 
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
		json.put("userId", 1000037);
		json.put("attentionUserId", 1000036);
		json.put("attentionType", "02");
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new AddFriend();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("saveAttent");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
