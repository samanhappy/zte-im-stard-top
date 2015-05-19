package com.zte.im.app.execute;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zte.im.bean.User;
import com.zte.im.cache.redis.RedisSupport;
import com.zte.im.protocol.ResponseListMain;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.IAppService;
import com.zte.im.service.impl.AppServiceImpl;
import com.zte.im.service.impl.VersionServiceImpl;
import com.zte.im.util.Page;

/**
 * 获取APP列表信息，appVer最新时不返回数据
 * 
 * @author samanhappy
 *
 */
public class ListAppExecute extends AbstractValidatoExecute {
	
	private final static Logger logger = LoggerFactory
			.getLogger(ListAppExecute.class);

	private static final String IS_RECOMMEND = "isRecommend";
	private static final String IS_CHOICE = "isChoice";
	private static final String CATE_ID = "cateId";
	private static final String PAGE = "page";

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Integer cateId = json.getInteger(CATE_ID);
		Integer isChoice = json.getInteger(IS_CHOICE);
		Integer isRecommend = json.getInteger(IS_RECOMMEND);
		Page page = null;
		int cpage = json.getIntValue(PAGE);
		page = new Page(10, cpage);

		BasicDBObject param = new BasicDBObject();
		if (cateId != null) {
			param.put(CATE_ID, cateId);
		}
		if (isChoice != null) {
			param.put(IS_CHOICE, isChoice);
		}
		if (isRecommend != null) {
			param.put(IS_RECOMMEND, isRecommend);
		}

		// 按照推荐置顶排序
		BasicDBObject sort = new BasicDBObject();
		sort.put(IS_RECOMMEND, -1);

		IAppService appService = AppServiceImpl.getInstance();
		List<DBObject> obj = appService.getAppByCateId(param, page, sort);
		ResponseListMain listmain = new ResponseListMain();
		listmain.setItem(obj);
		listmain.getRes().setVs(context.getServerVs());
		listmain.setDataVer(VersionServiceImpl.getInstance().getDataVer());
		resJson = JSON.toJSONString(listmain);
		return resJson;
	}

	public static void main(String[] args) {
		AbstractValidatoExecute e = new ListAppExecute();
		JSONObject json = new JSONObject();
		json.put("page", 1);
		json.put("isChoice", 1);
		json.put(CATE_ID, 3);
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		logger.info(json.toJSONString());
		context.setMethod("listApp");
		String tokenUser = RedisSupport.getInstance().get("7784927_UJZ3AIZN");
		User user = JSON.parseObject(tokenUser, User.class);
		context.setUser(user);
		e.setContext(context);
		String result = e.doProcess(json);
		logger.info(result);
	}
}
