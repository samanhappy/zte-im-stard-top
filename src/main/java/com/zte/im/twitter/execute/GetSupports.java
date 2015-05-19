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
import com.zte.im.bean.TSupport;
import com.zte.im.bean.TTwitter;
import com.zte.im.bean.User;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.Constant;

/**
 * 获取点赞列表
 * 
 * @author kaka
 * 
 */
public class GetSupports extends AbstractValidatoExecute {
	
	private final static Logger logger = LoggerFactory
			.getLogger(GetSupports.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();

		try {
			Long userId = json.getLong("userId");
			MyPageBean pageBean = new MyPageBean();
			pageBean.setPageSize(json.getInteger("pageSize"));
			pageBean.setPointId(json.getLong("pointId"));
			pageBean.setQueryType(json.getString("queryType"));

			List<TTwitter> twitterList = TwitterServiceImpl.getInstance()
					.findTwittersByUserId(userId);// 所有微博
			Map<Long, TTwitter> twitters = new HashMap<Long, TTwitter>();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if (null != twitterList && !twitterList.isEmpty()) {
				List<Long> tIds = new ArrayList<Long>();
				for (TTwitter twitter : twitterList) {
					tIds.add(twitter.getTwitterId());
					twitters.put(twitter.getTwitterId(), twitter);
				}
				Long[] tIdArr = (Long[]) tIds.toArray(new Long[tIds.size()]);
				List<TSupport> supports = TwitterServiceImpl.getInstance()
						.getSupportsByTid(tIdArr, pageBean);
				int count = 0;
				if (null != supports && !supports.isEmpty()) {
					for (TSupport support : supports) {
						Map<String, Object> resSupport = new HashMap<String, Object>();
						dealResData(resSupport, support,
								twitters.get(support.getTwitterId()));
						list.add(resSupport);
						count++;
						if (count >= pageBean.getPageSize()) {
							break;
						}
					}
					if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
							.getQueryType())) {
						res.put("totalNum", supports.size());
					} else {
						res.put("totalNum", "");
					}
					res.put("returnNum", count);
				} else {
					if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
							.getQueryType())) {
						res.put("totalNum", 0);
					} else {
						res.put("totalNum", "");
					}
					res.put("returnNum", 0);
				}
			}

			res.put("resFlag", "true");
			res.put("msg", "请求成功");
			res.put("resList", list);
		} catch (Exception e) {
			res.put("resFlag", "false");
			res.put("msg", e);
		}
		resJson = JSON.toJSONString(res);
		return resJson;
	}

	private void dealResData(Map<String, Object> resSupport, TSupport support,
			TTwitter twitter) {
		if (null == support || null == twitter) {
			return;
		}
		resSupport.put("supportId", getResObject(support.getSupportId()));
		resSupport.put("supportUserId", getResObject(support.getUserId()));
		resSupport.put("supportCreateTime", support.getCreateTime());
		User user = UserServiceImpl.getInstance().getUserbyid(
				support.getUserId());
		if (null != user) {
			resSupport.put("supportUserName", getResObject(user.getName()));
			resSupport.put("supportBigpicurl",
					getResObject(user.getBigpicurl()));
			resSupport.put("supportMinipicurl",
					getResObject(user.getMinipicurl()));
		} else {
			resSupport.put("supportUserName", "");
			resSupport.put("supportBigpicurl", "");
			resSupport.put("supportMinipicurl", "");
		}
		resSupport.put("twitterId", getResObject(twitter.getTwitterId()));
		resSupport.put("sourceId", getResObject(twitter.getSourceId()));
		resSupport.put("tuserId", getResObject(twitter.getUserId()));
		resSupport.put("twitterContent",
				getResObject(twitter.getTwitterContent()));
		resSupport.put("twitterType", getResObject(twitter.getTwitterType()));
		resSupport.put("visibleRange", getResObject(twitter.getVisibleRange()));
		resSupport.put("createTime", getResObject(twitter.getCreateTime()));
		resSupport.put("country", getResObject(twitter.getCountry()));
		resSupport.put("province", getResObject(twitter.getProvince()));
		resSupport.put("city", getResObject(twitter.getCity()));
		resSupport.put("county", getResObject(twitter.getCounty()));
		resSupport.put("street", getResObject(twitter.getStreet()));
		resSupport.put("address", getResObject(twitter.getAddress()));
		resSupport.put("forwardNum", getResObject(twitter.getForwardNum()));
		resSupport.put("collectionNum",
				getResObject(twitter.getCollectionNum()));
		resSupport.put("commentNum", getResObject(twitter.getCommentNum()));
		resSupport.put("supportNum", getResObject(twitter.getSupportNum()));
		resSupport.put("imgSrc", getResObject(twitter.getImgSrc()));
		resSupport.put("twitterStatus",
				getResObject(twitter.getTwitterStatus()));
		resSupport.put("userName", getResObject(twitter.getUserName()));
		resSupport.put("hreflag", getResObject(twitter.getHreflag()));
		resSupport.put("bigpicurl", getResObject(twitter.getBigpicurl()));
		resSupport.put("minipicurl", getResObject(twitter.getMinipicurl()));
		resSupport.put("isCollect", getResObject(twitter.getIsCollect()));
		
		resSupport.put("isSupport", getResObject(twitter.getIsSupport()));
		resSupport.put("sourceUserId", getResObject(twitter.getSourceUserId()));
		resSupport.put("sourceUserName", getResObject(twitter.getSourceUserName()));
		resSupport.put("sourceMinipicurl", getResObject(twitter.getSourceMinipicurl()));
		resSupport.put("sourceBigpicurl", getResObject(twitter.getSourceBigpicurl()));
		resSupport.put("sourceContent", getResObject(twitter.getSourceContent()));
		resSupport.put("sourceCreateTime", getResObject(twitter.getSourceCreateTime()));
		resSupport.put("sourceImgSrc", getResObject(twitter.getSourceImgSrc()));
		resSupport.put("sourceIsCollect", getResObject(twitter.getSourceIsCollect()));
		resSupport.put("sourceIsSupport", getResObject(twitter.getSourceIsSupport()));
		resSupport.put("sourceHreflag", getResObject(twitter.getSourceHreflag()));
		resSupport.put("sourceForwardNum", getResObject(twitter.getSourceForwardNum()));
		resSupport.put("sourceCollectionNum", getResObject(twitter.getSourceCollectionNum()));
		resSupport.put("sourceCommentNum", getResObject(twitter.getSourceCommentNum()));
		resSupport.put("sourceSupportNum", getResObject(twitter.getSourceSupportNum()));
		
		resSupport.put("sourceCountry", getResObject(twitter.getSourceCountry()));
		resSupport.put("sourceProvince", getResObject(twitter.getSourceProvince()));
		resSupport.put("sourceCity", getResObject(twitter.getSourceCity()));
		resSupport.put("sourceCounty", getResObject(twitter.getSourceCounty()));
		resSupport.put("sourceStreet", getResObject(twitter.getSourceStreet()));
		resSupport.put("sourceAddress", getResObject(twitter.getSourceAddress()));
		resSupport.put("sourceTwitterType", getResObject(twitter.getSourceTwitterType()));
		resSupport.put("sourceTwitterStatus", getResObject(twitter.getSourceTwitterStatus()));
		resSupport.put("sourceVisibleRange", getResObject(twitter.getSourceVisibleRange()));
	}

	private Object getResObject(Object ori) {
		return null != ori ? ori : "";
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("userId", 1000001);
		json.put("pointId", 1);
		json.put("pageSize", 4);
		json.put("queryType", "01");
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new GetSupports();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("getSupports");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
