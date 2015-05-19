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
import com.zte.im.bean.TSupport;
import com.zte.im.bean.TTwitter;
import com.zte.im.bean.User;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.ITwitterService;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.Constant;

/**
 * 获取评论列表
 * 
 * @author kaka
 * 
 */
public class GetComments extends AbstractValidatoExecute {

	private final static Logger logger = LoggerFactory
			.getLogger(GetComments.class);

	// 回复评论
	private static String COMMENT_TYPE_COMMENT = "02";

	// 回复赞
	private static String COMMENT_TYPE_SUPPORT = "03";

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		String msg = "";
		String resFlag = Constant.TRUE;
		Map<String, Object> res = new HashMap<String, Object>();
		try {
		//	Long userId = super.getContext().getUser().getUid();
			Long userId = json.getLong("userId");
			MyPageBean pageBean = new MyPageBean();
			pageBean.setPageSize(json.getInteger("pageSize"));
			pageBean.setPointId(json.getLong("pointId"));
			pageBean.setQueryType(json.getString("queryType"));
			ITwitterService twitterService = TwitterServiceImpl.getInstance();
			List<TComment> comments = twitterService.findUserComments(userId,
					pageBean);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			int count = 0;
			if (null != comments && !comments.isEmpty()) {
				for (TComment comment : comments) {
					System.out.println(comment.getCommentId());
					Map<String, Object> resComment = new HashMap<String, Object>();
					dealResData(resComment, comment, userId);
					if(resComment!=null){
						list.add(resComment);
						count++;
						if (count >= pageBean.getPageSize()) {
							break;
						}
					}
				}
				if (Constant.TWITTER_QUERYTYPE_REFRESH.equals(pageBean
						.getQueryType())) {
					res.put("totalNum", comments.size());
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
		  
			res.put("resList", list);
		} catch (Exception e) {
			logger.error("", e);
			resFlag = Constant.ERROR_CODE;
			msg = e.getMessage();
		}
		
		Map<String, Object> res2 = new HashMap<String, Object>();
		res2.put("resMessage", ""+msg);
		res2.put("resCode", resFlag);
		res2.put("vs", Constant.TRUE);
		res.put("res", res2);
		resJson = JSON.toJSONString(res);
		return resJson;
	}

	private void dealResData(Map<String, Object> resComment, TComment comment,
			Long userId) {
	 
		TTwitter twitter = TwitterServiceImpl.getInstance().findTwitterDetail(
				comment.getTwitterId(), userId);
		if (null == comment || twitter == null) { 
			resComment = null;
			return;
		}
		resComment.put("commentId", getResObject(comment.getCommentId()));
		resComment.put("comment", getResObject(comment.getCommentContent()));
		resComment.put("commentUserId", getResObject(comment.getUserId()));
		resComment.put("commentCreateTime",
				getResObject(comment.getCreateTime()));
		User user = UserServiceImpl.getInstance().getUserbyid(
				comment.getUserId());
		if (null != user) {
			resComment.put("commentUserName", getResObject(user.getName()));
			resComment.put("commentBigpicurl",
					getResObject(user.getBigpicurl()));
			resComment.put("commentMinipicurl",
					getResObject(user.getMinipicurl()));
		} else {
			resComment.put("commentUserName", "");
			resComment.put("commentBigpicurl", "");
			resComment.put("commentMinipicurl", "");
		}

		// 区分评论类型
		try {
			String commentType = comment.getCommentType();
			resComment.put("commentType", getResObject(commentType));
			if (COMMENT_TYPE_COMMENT.equals(commentType))// 回复评论
			{
				Long targetId = comment.getReplyCommentId();
				TComment replyComment = TwitterServiceImpl.getInstance()
						.findCommentDetail(targetId);
				User replyUser = UserServiceImpl.getInstance().getUserbyid(
						replyComment.getUserId());
				resComment.put("replyId", getResObject(targetId));
				resComment.put("replyComment",
						getResObject(replyComment.getCommentContent()));
				resComment.put("replyUserId",
						getResObject(replyComment.getUserId()));
				resComment.put("replyUserName",
						getResObject(replyUser.getName()));
			} else if (COMMENT_TYPE_SUPPORT.equals(commentType))// 回复赞
			{
				Long targetId = comment.getReplySupportId();
				TSupport replySupport = TwitterServiceImpl.getInstance()
						.findSupportDetail(targetId);
				User replyUser = UserServiceImpl.getInstance().getUserbyid(
						replySupport.getUserId());
				resComment.put("replyId", getResObject(targetId));
				resComment.put("replyComment", "");
				resComment.put("replyUserId",
						getResObject(replySupport.getUserId()));
				resComment.put("replyUserName",
						getResObject(replyUser.getName()));
			} else {
				resComment.put("replyId", "");
				resComment.put("replyComment", "");
				resComment.put("replyUserId", "");
				resComment.put("replyUserName", "");
			}
		} catch (Exception e) {
			resComment.put("replyId", "");
			resComment.put("replyComment", "");
			resComment.put("replyUserId", "");
			resComment.put("replyUserName", "");
		}
		// twitter信息
		 
		resComment.put("twitterId", getResObject(twitter.getTwitterId()));
		resComment.put("sourceId", getResObject(twitter.getSourceId()));
		resComment.put("tuserId", getResObject(twitter.getUserId()));
		resComment.put("twitterContent",
				getResObject(twitter.getTwitterContent()));
		resComment.put("twitterType", getResObject(twitter.getTwitterType()));
		resComment.put("visibleRange", getResObject(twitter.getVisibleRange()));
		resComment.put("createTime", getResObject(twitter.getCreateTime()));
		resComment.put("country", getResObject(twitter.getCountry()));
		resComment.put("province", getResObject(twitter.getProvince()));
		resComment.put("city", getResObject(twitter.getCity()));
		resComment.put("county", getResObject(twitter.getCounty()));
		resComment.put("street", getResObject(twitter.getStreet()));
		resComment.put("address", getResObject(twitter.getAddress()));
		resComment.put("forwardNum", getResObject(twitter.getForwardNum()));
		resComment.put("collectionNum",
				getResObject(twitter.getCollectionNum()));
		resComment.put("commentNum", getResObject(twitter.getCommentNum()));
		resComment.put("supportNum", getResObject(twitter.getSupportNum()));
		resComment.put("imgSrc", getResObject(twitter.getImgSrc()));
		resComment.put("twitterStatus",
				getResObject(twitter.getTwitterStatus()));
		resComment.put("userName", getResObject(twitter.getUserName()));
		resComment.put("hreflag", getResObject(twitter.getHreflag()));
		resComment.put("bigpicurl", getResObject(twitter.getBigpicurl()));
		resComment.put("minipicurl", getResObject(twitter.getMinipicurl()));
		resComment.put("isCollect", getResObject(twitter.getIsCollect()));

		resComment.put("isSupport", getResObject(twitter.getIsSupport()));
		resComment.put("sourceUserId", getResObject(twitter.getSourceUserId()));
		resComment.put("sourceUserName",
				getResObject(twitter.getSourceUserName()));
		resComment.put("sourceMinipicurl",
				getResObject(twitter.getSourceMinipicurl()));
		resComment.put("sourceBigpicurl",
				getResObject(twitter.getSourceBigpicurl()));
		resComment.put("sourceContent",
				getResObject(twitter.getSourceContent()));
		resComment.put("sourceCreateTime",
				getResObject(twitter.getSourceCreateTime()));
		resComment.put("sourceImgSrc", getResObject(twitter.getSourceImgSrc()));
		resComment.put("sourceIsCollect",
				getResObject(twitter.getSourceIsCollect()));
		resComment.put("sourceIsSupport",
				getResObject(twitter.getSourceIsSupport()));
		resComment.put("sourceHreflag",
				getResObject(twitter.getSourceHreflag()));
		resComment.put("sourceForwardNum",
				getResObject(twitter.getSourceForwardNum()));
		resComment.put("sourceCollectionNum",
				getResObject(twitter.getSourceCollectionNum()));
		resComment.put("sourceCommentNum",
				getResObject(twitter.getSourceCommentNum()));
		resComment.put("sourceSupportNum",
				getResObject(twitter.getSourceSupportNum()));

		resComment.put("sourceCountry",
				getResObject(twitter.getSourceCountry()));
		resComment.put("sourceProvince",
				getResObject(twitter.getSourceProvince()));
		resComment.put("sourceCity", getResObject(twitter.getSourceCity()));
		resComment.put("sourceCounty", getResObject(twitter.getSourceCounty()));
		resComment.put("sourceStreet", getResObject(twitter.getSourceStreet()));
		resComment.put("sourceAddress",
				getResObject(twitter.getSourceAddress()));
		resComment.put("sourceTwitterType",
				getResObject(twitter.getSourceTwitterType()));
		resComment.put("sourceTwitterStatus",
				getResObject(twitter.getSourceTwitterStatus()));
		resComment.put("sourceVisibleRange",
				getResObject(twitter.getSourceVisibleRange()));
	}

	private Object getResObject(Object ori) {
		return null != ori ? ori : "";
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("userId", 1000037);
		json.put("pointId", 0);
		json.put("pageSize", 5);
		json.put("queryType", "01");
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new GetComments();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("getComments");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
