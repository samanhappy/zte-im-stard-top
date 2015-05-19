package com.zte.im.app.execute;

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
import com.zte.im.service.IAppService;
import com.zte.im.service.impl.AppServiceImpl;
import com.zte.im.service.impl.VersionServiceImpl;

public class ListAppCateExecute extends AbstractValidatoExecute {
	
	private final static Logger logger = LoggerFactory
			.getLogger(ListAppCateExecute.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		IAppService appService = AppServiceImpl.getInstance();
		List<DBObject> obj = appService.getAppCate();
		ResponseListMain listmain = new ResponseListMain();
		listmain.setDataVer(VersionServiceImpl.getInstance().getDataVer());
		listmain.setItem(obj);
		resJson = JSON.toJSONString(listmain);
		return resJson;
	}

	public static void main(String[] args) {
		AbstractValidatoExecute e = new ListAppCateExecute();
		JSONObject json = new JSONObject();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		logger.info(json.toJSONString());
		context.setMethod("listAppCate");
		String tokenUser = RedisSupport.getInstance().get("7784927_UJZ3AIZN");
		User user = JSON.parseObject(tokenUser, User.class);
		context.setUser(user);
		e.setContext(context);
		String result = e.doProcess(json);
		logger.info(result);
	}

}
