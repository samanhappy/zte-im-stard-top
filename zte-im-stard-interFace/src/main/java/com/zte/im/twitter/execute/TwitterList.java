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
import com.zte.im.bean.TTGroup;
import com.zte.im.bean.TTwitter;
import com.zte.im.bean.User;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.ITwitterService;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.Constant;

/**
 * 获取微博列表（首页，特别关注，我的微博）
 * 
 * @author kaka
 * 
 */
public class TwitterList extends AbstractValidatoExecute {

	private final static Logger logger = LoggerFactory
			.getLogger(TwitterList.class);

	private static Long GROUP_INDEX = -100L;// 首页

	private static Long GROUP_SPECATTENTION = -200L;// 特别关注

	private static Long GROUP_MYTWITTER = -300L;// 我的微博

	private static String ATTENTIONTYPE_SPEC = "02";

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		String resFlag = Constant.TRUE;
		String msg = "";
		Map<String, Object> res = new HashMap<String, Object>();
		try {

			Long userId = json.getLong("userId");
			Long groupId = json.getLong("groupId");
			String queryType = json.getString("queryType");
			MyPageBean pageBean = new MyPageBean();
			pageBean.setPageSize(json.getInteger("pageSize"));
			pageBean.setPointId(json.getLong("pointId"));
			pageBean.setQueryType(queryType);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			User user = UserServiceImpl.getInstance().getUserbyid(userId);
			ITwitterService twitterService = TwitterServiceImpl.getInstance();
			// 首页,包含我关注的人发布的微博，同部门下人员发布的微博，同圈子下人发布的微博
			if (GROUP_INDEX.equals(groupId))
			{
				// 我关注的用户id
//				List<Long> uids = TwitterServiceImpl.getInstance()
//						.getAttentionUids(userId, null); 
				//				if (null != uids && !uids.isEmpty()) {
//				Long[] uIdArr = (Long[]) uids
//						.toArray(new Long[uids.size()]);
				Long[] uIdArr = (Long[]) new ArrayList<Long>().toArray(new Long[0]);
				// 部门下发布的微博id
				long deptId = user.getCid();
				List<Long> twitterids = twitterService
						.getTwitterIdByDept(deptId);
				// 圈子内发布的微博id
				List<Long> groupids = twitterService
						.getGroupIdByUserId(userId);
				Long[] groupIdArr = (Long[]) groupids
						.toArray(new Long[groupids.size()]);
				List<Long> twitterids1 = twitterService
						.getTwitterIdByGroups(groupIdArr);
				twitterids.addAll(twitterids1);
				Long[] tids = (Long[]) twitterids
						.toArray(new Long[twitterids.size()]);
				List<TTwitter> twitterList = twitterService.getTwittersIdx(
						userId, uIdArr, tids, pageBean);

				int count = 0;
				if (null != twitterList && !twitterList.isEmpty()) { 
					for (TTwitter twitter : twitterList) {  
						Map<String, Object> resTwitter = new HashMap<String, Object>();
						dealResData(resTwitter, twitter);
						list.add(resTwitter);
						count++;
						if (count >= pageBean.getPageSize()) {
							break;
						}
					}

					if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
							.getQueryType())) {
						res.put("totalNum", twitterList.size());
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
				// 下拉刷新不查询系统微博
				if("01".equals(queryType)){
				// 系统微博
					TTwitter sysTwitter = twitterService.getSysTwitter(userId);
					Map<String, Object> resSysTwitter = new HashMap<String, Object>();
					dealResData(resSysTwitter, null == sysTwitter ? new TTwitter()
					: sysTwitter);
					res.put("sysTwitter", resSysTwitter);
				}

			} else if (GROUP_SPECATTENTION.equals(groupId))// 特别关注，我特别关注的人发的微博
			{
				List<Long> attentionUids = twitterService.getAttentionUids(
						userId, ATTENTIONTYPE_SPEC);
				if (null == attentionUids || attentionUids.isEmpty()) { 
					msg = Constant.CODE_2;
					resFlag = Constant.CODE_2;
					res.put("totalNum", "");
					res.put("returnNum", 0);
					res.put("resList", list);
					 
					
				} else {
					Long[] uIdArr = (Long[]) attentionUids
							.toArray(new Long[attentionUids.size()]);
					List<TTwitter> twitterList = twitterService.getTwittersNew(
							userId, uIdArr, pageBean);
					int count = 0;
					if (null != twitterList && !twitterList.isEmpty()) {
						for (TTwitter twitter : twitterList) {
							Map<String, Object> resTwitter = new HashMap<String, Object>();
							dealResData(resTwitter, twitter);
							list.add(resTwitter);
							count++;
							if (count >= pageBean.getPageSize()) {
								break;
							}
						}
						if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
								.getQueryType())) {
							res.put("totalNum", twitterList.size());
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
				}
			} else if (GROUP_MYTWITTER.equals(groupId))// 我的微博
			{
				Long[] uIdArr = new Long[] { userId };
				int count = 0;
				List<TTwitter> twitterList = twitterService.getTwittersNew(
						userId, uIdArr, pageBean);
				if (null != twitterList && !twitterList.isEmpty()) {
					for (TTwitter twitter : twitterList) {
						Map<String, Object> resTwitter = new HashMap<String, Object>();
						dealResData(resTwitter, twitter);
						list.add(resTwitter);
						count++;
						if (count >= pageBean.getPageSize()) {
							break;
						}
					}
					if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
							.getQueryType())) {
						res.put("totalNum", twitterList.size());
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
			} else// 具体群组，圈子内的微博
			{
				TGroup group = twitterService.findGroupDetail(groupId);
				if (null == group)// 圈子不存在
				{ 
					msg = Constant.CODE_3;
					resFlag = Constant.CODE_3;
					res.put("totalNum", "");
					res.put("returnNum", 0);
					res.put("resList", list);
					  
				}
				// 分享到该圈子的微博
				List<TTGroup> ttGroup = twitterService.findTGroup(groupId,
						pageBean);
				if (null != ttGroup && !ttGroup.isEmpty()) {
					int count = 0;
					for (TTGroup tGroup : ttGroup) {
						TTwitter twitter = twitterService.findTwitterDetail(
								tGroup.getTwitterId(), userId);
						if(twitter!=null){
							Map<String, Object> resTwitter = new HashMap<String, Object>();
							dealResData(resTwitter, twitter);
							list.add(resTwitter);
							count++;
							if (count >= pageBean.getPageSize()) {
								break;
							}
						}
					}
					if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
							.getQueryType())) {
						res.put("totalNum", ttGroup.size());
					} else {
						res.put("totalNum", "");
					}
					res.put("returnNum", count);
				} else {
					 
					msg = Constant.CODE_4;
					resFlag = Constant.CODE_4;
					res.put("resList", list);
					res.put("totalNum", "");
					res.put("returnNum", 0);
				 
				}

			}
		 
			res.put("resList", list);
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

	private void dealResData(Map<String, Object> resTwitter, TTwitter twitter) {
		resTwitter.put("twitterId", getResObject(twitter.getTwitterId()));
		resTwitter.put("sourceId", getResObject(twitter.getSourceId()));
		resTwitter.put("tuserId", getResObject(twitter.getUserId()));
		resTwitter.put("twitterContent",
				getResObject(twitter.getTwitterContent()));
		resTwitter.put("twitterType", getResObject(twitter.getTwitterType()));
		resTwitter.put("visibleRange", getResObject(twitter.getVisibleRange()));
		resTwitter.put("createTime", getResObject(twitter.getCreateTime()));
		resTwitter.put("country", getResObject(twitter.getCountry()));
		resTwitter.put("province", getResObject(twitter.getProvince()));
		resTwitter.put("city", getResObject(twitter.getCity()));
		resTwitter.put("county", getResObject(twitter.getCounty()));
		resTwitter.put("street", getResObject(twitter.getStreet()));
		resTwitter.put("address", getResObject(twitter.getAddress()));
		resTwitter.put("forwardNum", getResObject(twitter.getForwardNum()));
		resTwitter.put("collectionNum",
				getResObject(twitter.getCollectionNum()));
		resTwitter.put("commentNum", getResObject(twitter.getCommentNum()));
		resTwitter.put("supportNum", getResObject(twitter.getSupportNum()));
		resTwitter.put("imgSrc", getResObject(twitter.getImgSrc()));
		resTwitter.put("twitterStatus",
				getResObject(twitter.getTwitterStatus()));
		resTwitter.put("userName", getResObject(twitter.getUserName()));
		resTwitter.put("hreflag", getResObject(twitter.getHreflag()));
		resTwitter.put("bigpicurl", getResObject(twitter.getBigpicurl()));
		resTwitter.put("minipicurl", getResObject(twitter.getMinipicurl()));
		resTwitter.put("isCollect", getResObject(twitter.getIsCollect()));

		resTwitter.put("isSupport", getResObject(twitter.getIsSupport()));
		resTwitter.put("sourceUserId", getResObject(twitter.getSourceUserId()));
		resTwitter.put("sourceUserName",
				getResObject(twitter.getSourceUserName()));
		resTwitter.put("sourceMinipicurl",
				getResObject(twitter.getSourceMinipicurl()));
		resTwitter.put("sourceBigpicurl",
				getResObject(twitter.getSourceBigpicurl()));
		resTwitter.put("sourceContent",
				getResObject(twitter.getSourceContent()));
		resTwitter.put("sourceCreateTime",
				getResObject(twitter.getSourceCreateTime()));
		resTwitter.put("sourceImgSrc", getResObject(twitter.getSourceImgSrc()));
		resTwitter.put("sourceIsCollect",
				getResObject(twitter.getSourceIsCollect()));
		resTwitter.put("sourceIsSupport",
				getResObject(twitter.getSourceIsSupport()));
		resTwitter.put("sourceHreflag",
				getResObject(twitter.getSourceHreflag()));
		resTwitter.put("sourceForwardNum",
				getResObject(twitter.getSourceForwardNum()));
		resTwitter.put("sourceCollectionNum",
				getResObject(twitter.getSourceCollectionNum()));
		resTwitter.put("sourceCommentNum",
				getResObject(twitter.getSourceCommentNum()));
		resTwitter.put("sourceSupportNum",
				getResObject(twitter.getSourceSupportNum()));

		resTwitter.put("sourceCountry",
				getResObject(twitter.getSourceCountry()));
		resTwitter.put("sourceProvince",
				getResObject(twitter.getSourceProvince()));
		resTwitter.put("sourceCity", getResObject(twitter.getSourceCity()));
		resTwitter.put("sourceCounty", getResObject(twitter.getSourceCounty()));
		resTwitter.put("sourceStreet", getResObject(twitter.getSourceStreet()));
		resTwitter.put("sourceAddress",
				getResObject(twitter.getSourceAddress()));
		resTwitter.put("sourceTwitterType",
				getResObject(twitter.getSourceTwitterType()));
		resTwitter.put("sourceTwitterStatus",
				getResObject(twitter.getSourceTwitterStatus()));
		resTwitter.put("sourceVisibleRange",
				getResObject(twitter.getSourceVisibleRange()));
	}

	private Object getResObject(Object ori) {
		return null != ori ? ori : "";
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("userId", 7648);
		json.put("groupId", -100);
		json.put("pointId", 0);
		json.put("pageSize", 10);
		json.put("queryType", "01");
		json.put("token", "3155763_129L2BKD");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new TwitterList();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("twitterList");
		String result = e.doProcess(context);
		logger.info(result);
	}
}
