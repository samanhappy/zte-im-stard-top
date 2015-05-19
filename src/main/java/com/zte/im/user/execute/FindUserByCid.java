package com.zte.im.user.execute;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.DBObject;
import com.zte.im.bean.User;
import com.zte.im.cache.redis.RedisSupport;
import com.zte.im.protocol.ResponseListMain;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.IUserService;
import com.zte.im.service.impl.UserServiceImpl;

/**
 * 根据部门id查询分组下的用户
 * 
 * @author Administrator
 * 
 */
@Deprecated
public class FindUserByCid extends AbstractValidatoExecute {
	
	private final static Logger logger = LoggerFactory
			.getLogger(FindUserByCid.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Long cid = json.getLong("cid");
		Long gid = super.getContext().getUser().getGid();
		IUserService userService = UserServiceImpl.getInstance();
		List<DBObject> obj = userService.getUserbyid(gid, cid);
		ResponseListMain listmain = new ResponseListMain();
		listmain.setItem(obj);
		resJson = JSON.toJSONString(listmain);
		return resJson;
	}

	public static void main(String[] args) {
		AbstractValidatoExecute e = new FindUserByCid();
		JSONObject json = new JSONObject();
		json.put("cid", "2");
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		logger.info(json.toJSONString());
		context.setMethod("findUserByCid");
		String tokenUser = RedisSupport.getInstance().get("7784927_UJZ3AIZN");
		User user = JSON.parseObject(tokenUser, User.class);
		context.setUser(user);
		e.setContext(context);
		String result = e.doProcess(json);
		logger.info(result);
	}

}
