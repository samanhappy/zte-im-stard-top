package com.zte.im.twitter.execute;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.User;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.IUserService;
import com.zte.im.service.impl.UserServiceImpl;

/**
 * dec 更新用户头像
 * 
 * @author xubin
 * 
 */
public class UserPicUrlUpdate extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(UserPicUrlUpdate.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		String resFlag = "true";
		String msg = "";
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			Long userId = json.getLong("userId");
			String bigpicurl = json.getString("bigpicurl");
			String minipicurl = json.getString("minipicurl");
			IUserService userSercive = UserServiceImpl.getInstance();
			User user = userSercive.getUserbyid(userId);
			if (user != null) {
				if (bigpicurl != null && !"".equals(bigpicurl)) {
					if (bigpicurl != null && !"".equals(bigpicurl)) {
						user.setBigpicurl(bigpicurl);
						user.setMinipicurl(minipicurl);
						userSercive.updateUser(user);
					} else {
						msg = "头像缩略图路径不能为空";
						resFlag = "false";
					}
				} else {
					msg = "头像原图路径不能为空";
					resFlag = "false";
				}
			} else {
				msg = "用户不存在";
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