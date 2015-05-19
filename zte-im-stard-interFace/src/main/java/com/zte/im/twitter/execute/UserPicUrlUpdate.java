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
import com.zte.im.util.Constant;

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
		String resFlag = Constant.TRUE;
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
						msg = Constant.CODE_11;
						resFlag = Constant.CODE_11;
					}
				} else {
					msg = Constant.CODE_12;
					resFlag = Constant.CODE_12;
				}
			} else {
				msg = Constant.CODE_8;
				resFlag = Constant.CODE_8;
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
}