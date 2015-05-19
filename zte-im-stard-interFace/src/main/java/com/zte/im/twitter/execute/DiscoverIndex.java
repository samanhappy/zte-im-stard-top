package com.zte.im.twitter.execute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.MyPageBean;
import com.zte.im.bean.TTopic;
import com.zte.im.bean.TTwitter;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.Constant;

/**
 * 
 * @author Administrator
 * 发现首页查询微博、话题
 *
 */
public class DiscoverIndex extends AbstractValidatoExecute {

	private static Logger logger = LoggerFactory.getLogger(DiscoverIndex.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = Constant.TRUE;
		String msg = "";
		try {
			MyPageBean pageBean = new MyPageBean(); 
			Long userId = super.getContext().getUser().getUid();
		 
			pageBean.setPageSize(json.getInteger("twitterPageSize"));

			pageBean.setPageNum(1);
			//查询类型01：刷新；02：分页
			pageBean.setQueryType(Constant.TWITTER_QUERYTYPE_REFRESH);
			List<TTwitter> twitterList = TwitterServiceImpl.getInstance().getTwittersByKw(userId, null, Constant.TWITTER_TYPE_01, pageBean);

			MyPageBean tpageBean = new MyPageBean();
			tpageBean.setPageSize(json.getInteger("topicPageSize"));
			tpageBean.setPageNum(1);
			List<TTopic> topicList = TwitterServiceImpl.getInstance()
					.getTopicsByKw(null, tpageBean)  ;
			res.put("twitterList", twitterList);
			res.put("topicList", topicList);
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
		//json.put("userId", 1000001);
		json.put("twitterPageSize", 3);
		json.put("topicPageSize", 5);
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new DiscoverIndex();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("discoverIndex");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
