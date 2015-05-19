package com.zte.im.twitter.execute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.MyPageBean;
import com.zte.im.bean.TGroup;
import com.zte.im.bean.TTopic;
import com.zte.im.bean.TTwitter;
import com.zte.im.bean.User;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.Constant;

public class CompQuery extends AbstractValidatoExecute {

	private static Logger logger = LoggerFactory.getLogger(CompQuery.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		String resFlag = Constant.TRUE;
		String msg = "";
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			String kw = json.getString("kw");
			String searType = json.getString("searType");
			Long userId = json.getLong("userId");
			MyPageBean pageBean = new MyPageBean();
			pageBean.setPageSize(json.getInteger("pageSize"));
			//			pageBean.setPointId(json.getLong("pointId"));
			//			pageBean.setQueryType(json.getString("queryType"));
			pageBean.setPageNum(json.getInteger("pageNum"));
			List<TGroup> glist = null;
			List<TTopic> tlist = null;
			List<User> ulist = null;
			List<TTwitter> twlist = null;
			if ("00".equals(searType)) {
				glist = TwitterServiceImpl.getInstance().getGroupsByKw(userId,
						kw, pageBean) ;
				tlist = TwitterServiceImpl.getInstance().getTopicsByKw(kw,
						pageBean) ;
				ulist = TwitterServiceImpl.getInstance().getUsersByKw(userId,
						kw, pageBean) ;
				twlist = TwitterServiceImpl.getInstance().getTwittersByKw(
						userId, kw, null, pageBean); 
				res.put("groupList", glist);
				res.put("topicList", tlist);
				res.put("userList", ulist);
				res.put("twitterList", twlist);
			} else if ("01".equals(searType)) {
				twlist = TwitterServiceImpl.getInstance().getTwittersByKw(
						userId, kw, null, pageBean) ;
				int count = 0;
				if (null != twlist && !twlist.isEmpty()) {
					count = twlist.size();
				}  
				res.put("totalNum", count);
				res.put("returnNum", count); 
				res.put("twitterList", twlist);
			} else if ("02".equals(searType)) {
				ulist = TwitterServiceImpl.getInstance().getUsersByKw(userId,kw, pageBean) ;
				int count = 0;
				if (null != ulist && !ulist.isEmpty()) {
					count = ulist.size();
				}  
				res.put("totalNum", count);
				res.put("returnNum", count); 
				res.put("userList", ulist);
			} else if ("03".equals(searType)) {
				tlist = TwitterServiceImpl.getInstance().getTopicsByKw(kw,
						pageBean);
				int count = 0;
				if (null != tlist && !tlist.isEmpty()) {
					count = tlist.size();
				}  
				res.put("totalNum", count);
				res.put("returnNum", count); 
				res.put("topicList", tlist);
			} else if ("04".equals(searType)) {
				glist = TwitterServiceImpl.getInstance().getGroupsByKw(userId,
						kw, pageBean) ;
				int count = 0;
				if (null != glist && !glist.isEmpty()) {
					count = glist.size();
				}  
				res.put("totalNum", count);
				res.put("returnNum", count); 
				res.put("groupList", glist);
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
		json.put("pageSize", 5); 
		json.put("pageNum", 1); 
	//	json.put("queryType", "04"); 
		json.put("userId", 1000037); 
		json.put("kw", "16");
		json.put("searType", "01");// 00：全部；01：微博；02：人；03：话题；04：圈子
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new CompQuery();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("compQuery");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
