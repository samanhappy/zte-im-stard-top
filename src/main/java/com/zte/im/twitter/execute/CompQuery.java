package com.zte.im.twitter.execute;

import java.util.ArrayList;
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
		String resFlag = "true";
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			String kw = json.getString("kw");
			String searType = json.getString("searType");
			Long userId = json.getLong("userId");
			MyPageBean pageBean = new MyPageBean();
			pageBean.setPageSize(json.getInteger("pageSize"));
			pageBean.setPointId(json.getLong("pointId"));
			pageBean.setQueryType(json.getString("queryType"));
			List<TGroup> glist = null;
			List<TTopic> tlist = null;
			List<User> ulist = null;
			List<TTwitter> twlist = null;
			if ("00".equals(searType)) {
				glist = TwitterServiceImpl.getInstance().getGroupsByKw(userId,
						kw, pageBean) == null ? new ArrayList<TGroup>()
						: TwitterServiceImpl.getInstance().getGroupsByKw(
								userId, kw, pageBean);
				tlist = TwitterServiceImpl.getInstance().getTopicsByKw(kw,
						pageBean) == null ? new ArrayList<TTopic>()
						: TwitterServiceImpl.getInstance().getTopicsByKw(kw,
								pageBean);
				ulist = TwitterServiceImpl.getInstance().getUsersByKw(userId,
						kw, pageBean) == null ? new ArrayList<User>()
						: TwitterServiceImpl.getInstance().getUsersByKw(userId,
								kw, pageBean);
				twlist = TwitterServiceImpl.getInstance().getTwittersByKw(
						userId, kw, null, pageBean) == null ? new ArrayList<TTwitter>()
						: TwitterServiceImpl.getInstance().getTwittersByKw(
								userId, kw, null, pageBean);
				res.put("msg", "搜索全部");
				res.put("groupList", glist);
				res.put("topicList", tlist);
				res.put("userList", ulist);
				res.put("twitterList", twlist);
			} else if ("01".equals(searType)) {
				twlist = TwitterServiceImpl.getInstance().getTwittersByKw(
						userId, kw, null, pageBean) == null ? new ArrayList<TTwitter>()
						: TwitterServiceImpl.getInstance().getTwittersByKw(
								userId, kw, null, pageBean);
				int count = 0;
				if (null != twlist && !twlist.isEmpty()) {
					if (twlist.size() >= pageBean.getPageSize()) {
						count = pageBean.getPageSize();
					} else {
						count = twlist.size();
					}
					if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
							.getQueryType())) {
						res.put("totalNum", twlist.size());
					} else {
						res.put("totalNum", "");
					}
				} else {
					if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
							.getQueryType())) {
						res.put("totalNum", 0);
					} else {
						res.put("totalNum", "");
					}
				}
				res.put("returnNum", count);
				res.put("msg", "搜索微博");
				res.put("twitterList", twlist);
			} else if ("02".equals(searType)) {
				ulist = TwitterServiceImpl.getInstance().getUsersByKw(userId,
						kw, pageBean) == null ? new ArrayList<User>()
						: TwitterServiceImpl.getInstance().getUsersByKw(userId,
								kw, pageBean);
				int count = 0;
				if (null != ulist && !ulist.isEmpty()) {
					if (ulist.size() >= pageBean.getPageSize()) {
						count = pageBean.getPageSize();
					} else {
						count = ulist.size();
					}
					if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
							.getQueryType())) {
						res.put("totalNum", ulist.size());
					} else {
						res.put("totalNum", "");
					}
				} else {
					if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
							.getQueryType())) {
						res.put("totalNum", 0);
					} else {
						res.put("totalNum", "");
					}
				}
				res.put("returnNum", count);
				res.put("msg", "搜索人员");
				res.put("userList", ulist);
			} else if ("03".equals(searType)) {
				tlist = TwitterServiceImpl.getInstance().getTopicsByKw(kw,
						pageBean) == null ? new ArrayList<TTopic>()
						: TwitterServiceImpl.getInstance().getTopicsByKw(kw,
								pageBean);
				int count = 0;
				if (null != tlist && !tlist.isEmpty()) {
					if (tlist.size() >= pageBean.getPageSize()) {
						count = pageBean.getPageSize();
					} else {
						count = tlist.size();
					}
					if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
							.getQueryType())) {
						res.put("totalNum", tlist.size());
					} else {
						res.put("totalNum", "");
					}
				} else {
					if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
							.getQueryType())) {
						res.put("totalNum", 0);
					} else {
						res.put("totalNum", "");
					}
				}
				res.put("returnNum", count);
				res.put("msg", "搜索话题");
				res.put("topicList", tlist);
			} else if ("04".equals(searType)) {
				glist = TwitterServiceImpl.getInstance().getGroupsByKw(userId,
						kw, pageBean) == null ? new ArrayList<TGroup>()
						: TwitterServiceImpl.getInstance().getGroupsByKw(
								userId, kw, pageBean);
				int count = 0;
				if (null != glist && !glist.isEmpty()) {
					if (glist.size() >= pageBean.getPageSize()) {
						count = pageBean.getPageSize();
					} else {
						count = glist.size();
					}
					if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
							.getQueryType())) {
						res.put("totalNum", glist.size());
					} else {
						res.put("totalNum", "");
					}
				} else {
					if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
							.getQueryType())) {
						res.put("totalNum", 0);
					} else {
						res.put("totalNum", "");
					}
				}
				res.put("returnNum", count);
				res.put("msg", "搜索圈子");
				res.put("groupList", glist);
			}
		} catch (Exception e) {
			resFlag = "false";
			res.put("msg", e.getMessage());
			logger.error("", e);
		}
		res.put("resFlag", resFlag);
		resJson = JSON.toJSONString(res);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("pointId", 1);
		json.put("pageSize", 5);
		json.put("queryType", "01");
		json.put("userId", 1000001);
		json.put("kw", "阿里巴巴");
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
