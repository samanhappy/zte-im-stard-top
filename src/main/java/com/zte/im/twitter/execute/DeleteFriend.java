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
import com.zte.im.service.impl.TwitterServiceImpl;

/**
 * 取消好友关注
 * 
 * @author Administrator
 * 
 */
public class DeleteFriend extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(DeleteFriend.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		String resFlag = "true";
		String msg = "";

		Map<String, Object> res = new HashMap<String, Object>();

		try {
			Long userid = json.getLong("userid");
			Long attentionUserId = json.getLong("attentionUserId");
			String delType = json.getString("delType");
			TwitterServiceImpl service = TwitterServiceImpl.getInstance();

			TAttention attention = service.FindAttentionByUid(userid,
					attentionUserId);
			if (attention != null) {
				if (delType != null) {
					if ("01".equals(delType)) {
						// 移除
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("userid", userid);
						param.put("attentionUserId", attentionUserId);
						service.RemoveAttentionUid(param);
						if (attention.getEachOtherFlag().compareTo("1") == 0) {
							// 更新对方互相关注状态 0
							TAttention attentions = service.FindAttentionByUid(
									attentionUserId, userid);
							if (attentions != null) {
								Date date = new Date();
								attentions.setEachOtherFlag("0");
								attentions.setUpdateTime(date.getTime());
								attentions.setUpdateUserId(userid);
								service.UpdateAttentionByUid(attentions);
							}
						}
					} else if ("02".equals(delType)) {
						// 特别关注改为普通关注
						Date date = new Date();
						attention.setAttentionType("01");
						;
						attention.setUpdateTime(date.getTime());
						attention.setUpdateUserId(userid);
						service.UpdateAttentionByUid(attention);
					}
				}

			}
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
