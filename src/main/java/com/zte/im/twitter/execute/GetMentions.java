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
import com.zte.im.bean.TComment;
import com.zte.im.bean.TMention;
import com.zte.im.bean.TSupport;
import com.zte.im.bean.TTwitter;
import com.zte.im.bean.User;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.Constant;
import com.zte.im.util.IDSequence;

/**
 * 获取@我的微博
 * 
 * @author kaka
 * 
 */
public class GetMentions extends AbstractValidatoExecute {
	
	private final static Logger logger = LoggerFactory
			.getLogger(GetMentions.class);

	private static String MENTIONTYPE_TWITTER = "01";

	private static String MENTIONTYPE_COMMENT = "02";

	// 回复评论
	private static String COMMENT_TYPE_COMMENT = "02";

	// 回复赞
	private static String COMMENT_TYPE_SUPPORT = "03";

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
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<TMention> mentionList = TwitterServiceImpl.getInstance()
					.getMentionByUserId(userId, pageBean);
			int count = 0;
			if (null != mentionList && !mentionList.isEmpty()) {
				for (TMention mention : mentionList) {
					Map<String, Object> resMention = new HashMap<String, Object>();
					String mentionType = mention.getMentionType();
					TTwitter twitter = TwitterServiceImpl.getInstance()
							.findTwitterDetail(mention.getTwitterId(),
									userId);
					if (null != mentionType
							&& MENTIONTYPE_TWITTER.equals(mentionType)) {
						dealResData(resMention, null, twitter, mentionType);
					} else if (null != mentionType
							&& MENTIONTYPE_COMMENT.equals(mentionType)) {
						TComment comment = TwitterServiceImpl.getInstance()
								.findCommentDetail(mention.getCommentId());
						dealResData(resMention, comment, twitter, mentionType);
					}
					resMention.put("mentionId",
							getResObject(mention.getMentionId()));
					list.add(resMention);
					count++;
					if (count >= pageBean.getPageSize()) {
						break;
					}
				}
				if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
						.getQueryType())) {
					res.put("totalNum", mentionList.size());
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

	private void dealResData(Map<String, Object> resMention, TComment comment,
			TTwitter twitter, String mentionType) {
		if (MENTIONTYPE_COMMENT.equals(mentionType)) {
			resMention.put("commentId", getResObject(comment.getCommentId()));
			resMention
					.put("comment", getResObject(comment.getCommentContent()));
			resMention.put("commentUserId", getResObject(comment.getUserId()));
			resMention.put("commentCreateTime",
					getResObject(comment.getCreateTime()));
			User user = UserServiceImpl.getInstance().getUserbyid(
					comment.getUserId());
			if (null != user) {
				resMention.put("commentUserName", getResObject(user.getName()));
				resMention.put("commentBigpicurl",
						getResObject(user.getBigpicurl()));
				resMention.put("commentMinipicurl",
						getResObject(user.getMinipicurl()));
			} else {
				resMention.put("commentUserName", "");
				resMention.put("commentBigpicurl", "");
				resMention.put("commentMinipicurl", "");
			}
			String commentType = comment.getCommentType();
			resMention.put("commentType", getResObject(commentType));
			// 区分评论类型
			try {
				if (COMMENT_TYPE_COMMENT.equals(commentType))// 回复评论
				{
					Long targetId = comment.getReplyCommentId();
					TComment replyComment = TwitterServiceImpl.getInstance()
							.findCommentDetail(targetId);
					User replyUser = UserServiceImpl.getInstance().getUserbyid(
							replyComment.getUserId());
					resMention.put("replyId", getResObject(targetId));
					resMention.put("replyComment",
							getResObject(replyComment.getCommentContent()));
					resMention.put("replyUserId",
							getResObject(replyComment.getUserId()));
					resMention.put("replyUserName",
							getResObject(replyUser.getName()));
				} else if (COMMENT_TYPE_SUPPORT.equals(commentType))// 回复赞
				{
					Long targetId = comment.getReplySupportId();
					TSupport replySupport = TwitterServiceImpl.getInstance()
							.findSupportDetail(targetId);
					User replyUser = UserServiceImpl.getInstance().getUserbyid(
							replySupport.getUserId());
					resMention.put("replyId", getResObject(targetId));
					resMention.put("replyComment", "");
					resMention.put("replyUserId",
							getResObject(replySupport.getUserId()));
					resMention.put("replyUserName",
							getResObject(replyUser.getName()));
				} else {
					resMention.put("replyId", "");
					resMention.put("replyComment", "");
					resMention.put("replyUserId", "");
					resMention.put("replyUserName", "");
				}
			} catch (Exception e) {
				resMention.put("replyId", "");
				resMention.put("replyComment", "");
				resMention.put("replyUserId", "");
				resMention.put("replyUserName", "");
			}
		} else {
			resMention.put("commentId", "");
			resMention.put("comment", "");
			resMention.put("commentUserId", "");
			resMention.put("commentCreateTime", "");
			resMention.put("commentUserName", "");
			resMention.put("commentBigpicurl", "");
			resMention.put("commentMinipicurl", "");
			resMention.put("commentType", "");
			resMention.put("replyId", "");
			resMention.put("replyComment", "");
			resMention.put("replyUserId", "");
			resMention.put("replyUserName", "");
		}
		resMention.put("mentionType", getResObject(mentionType));
		resMention.put("twitterId", getResObject(twitter.getTwitterId()));
		resMention.put("sourceId", getResObject(twitter.getSourceId()));
		resMention.put("tuserId", getResObject(twitter.getUserId()));
		resMention.put("twitterContent",
				getResObject(twitter.getTwitterContent()));
		resMention.put("twitterType", getResObject(twitter.getTwitterType()));
		resMention.put("visibleRange", getResObject(twitter.getVisibleRange()));
		resMention.put("createTime", getResObject(twitter.getCreateTime()));
		resMention.put("country", getResObject(twitter.getCountry()));
		resMention.put("province", getResObject(twitter.getProvince()));
		resMention.put("city", getResObject(twitter.getCity()));
		resMention.put("county", getResObject(twitter.getCounty()));
		resMention.put("street", getResObject(twitter.getStreet()));
		resMention.put("address", getResObject(twitter.getAddress()));
		resMention.put("forwardNum", getResObject(twitter.getForwardNum()));
		resMention.put("collectionNum",
				getResObject(twitter.getCollectionNum()));
		resMention.put("commentNum", getResObject(twitter.getCommentNum()));
		resMention.put("supportNum", getResObject(twitter.getSupportNum()));
		resMention.put("imgSrc", getResObject(twitter.getImgSrc()));
		resMention.put("twitterStatus",
				getResObject(twitter.getTwitterStatus()));
		resMention.put("userName", getResObject(twitter.getUserName()));
		resMention.put("hreflag", getResObject(twitter.getHreflag()));
		resMention.put("bigpicurl", getResObject(twitter.getBigpicurl()));
		resMention.put("minipicurl", getResObject(twitter.getMinipicurl()));
		resMention.put("isCollect", getResObject(twitter.getIsCollect()));
		
		resMention.put("isSupport", getResObject(twitter.getIsSupport()));
		resMention.put("sourceUserId", getResObject(twitter.getSourceUserId()));
		resMention.put("sourceUserName", getResObject(twitter.getSourceUserName()));
		resMention.put("sourceMinipicurl", getResObject(twitter.getSourceMinipicurl()));
		resMention.put("sourceBigpicurl", getResObject(twitter.getSourceBigpicurl()));
		resMention.put("sourceContent", getResObject(twitter.getSourceContent()));
		resMention.put("sourceCreateTime", getResObject(twitter.getSourceCreateTime()));
		resMention.put("sourceImgSrc", getResObject(twitter.getSourceImgSrc()));
		resMention.put("sourceIsCollect", getResObject(twitter.getSourceIsCollect()));
		resMention.put("sourceIsSupport", getResObject(twitter.getSourceIsSupport()));
		resMention.put("sourceHreflag", getResObject(twitter.getSourceHreflag()));
		resMention.put("sourceForwardNum", getResObject(twitter.getSourceForwardNum()));
		resMention.put("sourceCollectionNum", getResObject(twitter.getSourceCollectionNum()));
		resMention.put("sourceCommentNum", getResObject(twitter.getSourceCommentNum()));
		resMention.put("sourceSupportNum", getResObject(twitter.getSourceSupportNum()));
		
		resMention.put("sourceCountry", getResObject(twitter.getSourceCountry()));
		resMention.put("sourceProvince", getResObject(twitter.getSourceProvince()));
		resMention.put("sourceCity", getResObject(twitter.getSourceCity()));
		resMention.put("sourceCounty", getResObject(twitter.getSourceCounty()));
		resMention.put("sourceStreet", getResObject(twitter.getSourceStreet()));
		resMention.put("sourceAddress", getResObject(twitter.getSourceAddress()));
		resMention.put("sourceTwitterType", getResObject(twitter.getSourceTwitterType()));
		resMention.put("sourceTwitterStatus", getResObject(twitter.getSourceTwitterStatus()));
		resMention.put("sourceVisibleRange", getResObject(twitter.getSourceVisibleRange()));
	}

	public static void mention() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mentionId", IDSequence.getUID());
		params.put("twitterId", 2171L);
		params.put("commentId", 2284L);
		params.put("userId", 1000001L);
		params.put("mentionUserId", 1000001L);
		params.put("mentionType", "02");
		params.put("mentionStatus", "00");
		// Map<String, Object> param = new HashMap<String, Object>();
		params.put("createUserId", 1000001L);
		params.put("createTime", System.currentTimeMillis());
		params.put("updateUserId", 1000001L);
		params.put("updateTime", System.currentTimeMillis());
		params.put("remark", "");

		TwitterServiceImpl.getInstance().addMentionInfo(params);
	}

	private Object getResObject(Object ori) {
		return null != ori ? ori : "";
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("userId", 102200018);
		json.put("pointId", 0);
		json.put("pageSize", 3);
		json.put("queryType", "01");
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new GetMentions();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("getMentions");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
