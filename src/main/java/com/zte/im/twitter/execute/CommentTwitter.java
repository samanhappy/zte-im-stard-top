package com.zte.im.twitter.execute;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.PushBean;
import com.zte.im.bean.TComment;
import com.zte.im.bean.TSupport;
import com.zte.im.bean.TTwitter;
import com.zte.im.bean.User;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.CmdExecute;
import com.zte.im.util.IDSequence;
import com.zte.im.util.ParseTagUtil;

/**
 * dec 评论微博
 * 
 * @author kaka
 * 
 */
public class CommentTwitter extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(CommentTwitter.class);

	// 回复评论
	private String COMMENT_TYPE_COMMENT = "02";

	// 回复赞
	private String COMMENT_TYPE_SUPPORT = "03";

	// 同时转发
	private Long COMMENT_IS_TRANS = 1L;

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = "true";
		String msg = "请求成功";
		try {
			Long userId = json.getLong("userId");
			User user = UserServiceImpl.getInstance().getUserbyid(userId);
			Long twitterId = json.getLong("twitterId");
			Long targetId = json.getLong("targetId");
			String commentType = json.getString("commentType");
			Long isTrans = json.getLong("isTrans");
			String comment = json.getString("comment");
			Date date = new Date();
			Map<String, Object> param = new HashMap<String, Object>();
			TTwitter t = TwitterServiceImpl.getInstance().findTwitterDetail(
					twitterId, userId);
			String pushUserId = String.valueOf(t.getUserId());
			Long commentId = IDSequence.getUID();
			param.put("commentId", commentId);
			param.put("userId", userId);
			param.put("twitterId", twitterId);
			param.put("commentContent", comment);
			param.put("commentStatus", "00");
			param.put("becomentUserId", Long.parseLong(pushUserId));
			param.put("commentType", commentType);
			if (COMMENT_TYPE_COMMENT.equals(commentType)) {
				param.put("replyCommentId", targetId);
				TComment c = TwitterServiceImpl.getInstance().findCommentDetail(targetId);
				pushUserId = String.valueOf(c.getUserId());
			}
			if (COMMENT_TYPE_SUPPORT.equals(commentType)) {
				param.put("replySupportId", targetId);
				TSupport support = TwitterServiceImpl.getInstance().findSupportDetail(targetId);
				pushUserId = String.valueOf(support.getUserId());
			}
			param.put("createUserId", userId);
			param.put("createTime", date.getTime());
			param.put("updateUserId", userId);
			param.put("updateTime", date.getTime());
			param.put("remark", "");
			
			if (t != null && "00".equals(t.getTwitterStatus())) {
				Long commentNum = t.getCommentNum();
				if (commentNum == null) {
					commentNum = 0L;
				}
				// 更新微博评论统计数
				TwitterServiceImpl.getInstance().updateTwitterNum(twitterId,
						"commentNum", commentNum + 1);
				TwitterServiceImpl.getInstance().commentTwitter(param);
				// 推送评论
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", userId);
				map.put("userName", user.getName());
				map.put("twitterId", twitterId);
				PushBean bean = new PushBean();
				bean.setTarget(pushUserId);
				bean.setKeyid(String.valueOf(super.getContext().getUser()
						.getUid()));
				bean.setMsg(JSON.toJSONString(map));
				bean.setType("11");// 11：评论
				bean.setSessionid(String.valueOf(userId));
				bean.setModeltype("1");
				bean.setSessionname("评论");
				CmdExecute.exe(bean);

				// 解析评论内容中@的用户
				List<Long> mUserIdList = ParseTagUtil
						.parseMentionUserId(comment);
				if (mUserIdList != null && mUserIdList.size() > 0) {
					for (Long uid : mUserIdList) {
						// 保存@信息
						Map<String, Object> param2 = new HashMap<String, Object>();
						param2.put("userId", userId);
						param2.put("mentionId", IDSequence.getUID());
						param2.put("twitterId", twitterId);
						param2.put("commentId", commentId);
						param2.put("mentionUserId", uid);
						param2.put("mentionType", "02");// 提到类型：01、微博；02、评论
						param2.put("mentionStatus", "00");
						param2.put("createUserId", userId);
						param2.put("createTime", date.getTime());
						param2.put("updateUserId", userId);
						param2.put("updateTime", date.getTime());
						param2.put("remark", "");
						TwitterServiceImpl.getInstance().addMentionInfo(param2);

						// 推送@
						Map<String, Object> m = new HashMap<String, Object>();
						m.put("userId", userId);
						m.put("userName", user.getName());
						m.put("twitterId", twitterId);
						m.put("mentionStatus", "02");
						PushBean pushbean = new PushBean();
						pushbean.setTarget(String.valueOf(uid));
						pushbean.setKeyid(String.valueOf(super.getContext()
								.getUser().getUid()));
						pushbean.setMsg(JSON.toJSONString(m));
						pushbean.setType("11");// 11：评论
						pushbean.setSessionid(String.valueOf(userId));
						pushbean.setModeltype("1");
						pushbean.setSessionname("评论");
						CmdExecute.exe(pushbean);
					}
				}
			} else if ("99".equals(t.getTwitterStatus())) {
				resFlag = "false";
				msg = "微博已删除！";
				logger.info("=======================微博已删除！");
			} else {
				resFlag = "false";
				msg = "微博不存在！";
				logger.info("=======================微博不存在！");
			}
			// 同时转发微博
			if (COMMENT_IS_TRANS.equals(isTrans)) {
				transTwitter(userId, user.getName(), twitterId);
			}
		} catch (Exception e) {
			resFlag = "false";
			msg = e.toString();
			logger.error("", e);
		}
		res.put("msg", msg);
		res.put("resFlag", resFlag);
		resJson = JSON.toJSONString(res);
		return resJson;
	}

	/**
	 * dec 转发微博
	 * 
	 */
	private void transTwitter(Long userId, String userName, Long twitterId) {
		try {
			TTwitter t = TwitterServiceImpl.getInstance().findTwitterDetail(
					twitterId, userId);
			Date date = new Date();
			if (t != null && "00".equals(t.getTwitterStatus())) {
				Long forwardNum = t.getForwardNum();
				if (forwardNum == null) {
					forwardNum = 0L;
				}
				// 更新微博转发统计数
				TwitterServiceImpl.getInstance().updateTwitterNum(twitterId,
						"forwardNum", forwardNum + 1);

				// 保存微博与转发用户关联关系
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("transId", IDSequence.getUID());
				param.put("userId", userId);
				param.put("twitterId", twitterId);
				param.put("twitterContent", "");
				param.put("createUserId", userId);
				param.put("createTime", date.getTime());
				param.put("updateUserId", userId);
				param.put("updateTime", date.getTime());
				param.put("remark", "");
				TwitterServiceImpl.getInstance().transTwitter(param);

				// 新建一条转发的微博
				Map<String, Object> tparam = new HashMap<String, Object>();
				// String visibleRange = json.getString("visibleRange");
				tparam.put("userId", userId);
				tparam.put("twitterId", IDSequence.getUID());
				tparam.put("twitterContent", "");
				tparam.put("twitterType", "02");
				tparam.put("twitterStatus", "00");
				tparam.put("sourceId", twitterId);
				tparam.put("visibleRange", "01");
				tparam.put("createUserId", userId);
				tparam.put("createTime", date.getTime());
				tparam.put("updateUserId", userId);
				tparam.put("updateTime", date.getTime());
				tparam.put("remark", "");
				TwitterServiceImpl.getInstance().SaveNewTwitter(tparam);

				// 保存@信息
				Map<String, Object> param2 = new HashMap<String, Object>();
				param2.put("userId", userId);
				param2.put("mentionId", IDSequence.getUID());
				param2.put("mentionUserId", t.getUserId());
				param2.put("mentionType", "01");// 提到类型：01、微博；02、评论
				param2.put("mentionStatus", "00");
				param2.put("createUserId", userId);
				param2.put("createTime", date.getTime());
				param2.put("updateUserId", userId);
				param2.put("updateTime", date.getTime());
				param2.put("remark", "");
				TwitterServiceImpl.getInstance().addMentionInfo(param2);

				// 推送转发提醒
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", userId);
				map.put("userName", userName);
				PushBean bean = new PushBean();
				bean.setTarget(String.valueOf(t.getUserId()));
				bean.setKeyid(String.valueOf(super.getContext().getUser()
						.getUid()));
				bean.setMsg(JSON.toJSONString(map));
				bean.setType("12");// 12：转发
				bean.setModeltype("1");
				bean.setSessionid(String.valueOf(userId));
				bean.setSessionname("转发");
				CmdExecute.exe(bean);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("userId", 1000001);
		json.put("token", "4602658_75JCEGDQ");
		json.put("twitterId", 2015);
		json.put("comment", "我也这么觉得，好评！");
		json.put("commentType", "02");
		json.put("targetId", 2027);
		json.put("isTrans", 1);

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new CommentTwitter();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("commentTwitter");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
