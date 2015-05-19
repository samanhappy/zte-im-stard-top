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
import com.zte.im.service.impl.TwitterServiceImpl;

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
		String resFlag = "true";
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
					msg = "圈子简介不能为空";
					resFlag = "false";
				}
			} else {
				msg = "圈子不存在";
				resFlag = "false";
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
