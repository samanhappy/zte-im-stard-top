package com.zte.im.twitter.execute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.TComment;
import com.zte.im.bean.TSupport;
import com.zte.im.bean.TTransUser;
import com.zte.im.bean.User;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.IUserService;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.Constant;

/**
 * 通过微博ID获取转发、点赞、评论 列表
 * 
 * @author Administrator
 * 
 */
public class GetForwardingPraiseReviewListById extends AbstractValidatoExecute {

	private static Logger logger = LoggerFactory.getLogger(GetForwardingPraiseReviewListById.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		String resFlag = Constant.TRUE;
		String msg = "";
		List<Map<String, Object>> forwardList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> commentList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> supportList = new ArrayList<Map<String, Object>>();
		try {
			Long twitterId = json.getLong("twitterId");

			TwitterServiceImpl commentService = TwitterServiceImpl
					.getInstance();

			IUserService userService = UserServiceImpl.getInstance();

			// 获取转发，
			List<TTransUser> obj = commentService.GettransTwitterUId(twitterId);
			if (null != obj) {
				for (TTransUser l : obj) {
					Map<String, Object> resComment = new HashMap<String, Object>();
					resComment.put("userId", l.getUserId());
					resComment.put("createTime", l.getCreateTime());
					resComment.put("transId", l.getTransId());
					resComment.put("twitterContent", l.getTwitterContent());


					// 获取用户信息
					User use = userService.getUserbyid(l.getUserId());
					if (null != use) {
						resComment.put("userName", getResObject(use.getName()));
						resComment.put("minipicurl", getResObject(use.getMinipicurl()));
						resComment.put("bigpicurl", getResObject(use.getBigpicurl()));
					} else {
						resComment.put("userName", "");
						resComment.put("minipicurl", "");
						resComment.put("bigpicurl", "");
					}
					forwardList.add(resComment);
				}
			}
			// 获取评论，
			List<TComment> comments = commentService.GetCommentsList(twitterId);
			if (null != comments) {
				for (TComment comment : comments) {
					Map<String, Object> resComment = new HashMap<String, Object>();
					resComment.put("userId", comment.getUserId());
					resComment.put("comment", comment.getCommentContent());
					resComment.put("createTime", comment.getCreateTime());
					resComment.put("commentId", comment.getCommentId());
					resComment.put("commentType", comment.getCommentType());
					User user = UserServiceImpl.getInstance().getUserbyid(comment.getBecomentUserId());
					 
					if(user!=null){
						resComment.put("becomentUserId",comment.getBecomentUserId());
						resComment.put("becomentUserName",user.getName());
						// 获取用户信息
						User use = userService.getUserbyid(comment.getUserId());
						if (null != use) {
							resComment.put("userName", getResObject(use.getName()));
							resComment.put("minipicurl", getResObject(use.getMinipicurl()));
							resComment.put("bigpicurl", getResObject(use.getBigpicurl()));
						} else {
							resComment.put("userName", "");
							resComment.put("minipicurl", "");
							resComment.put("bigpicurl", "");
						}
						commentList.add(resComment);
					}
				}
			}
			// 获取点赞，
			List<TSupport> supports = commentService.GetSupportsList(twitterId);

			if (null != supports) {
				for (TSupport support : supports) {
					Map<String, Object> resComment = new HashMap<String, Object>();
					resComment.put("userId", getResObject(support.getUserId()));
					resComment.put("createTime", getResObject(support.getCreateTime()));
					resComment.put("supportId", getResObject(support.getSupportId()));
					// 获取用户信息
					User use = userService.getUserbyid(support.getUserId());
					if (null != use) {
						resComment.put("userName", getResObject(use.getName()));
						resComment.put("minipicurl", getResObject(use.getMinipicurl()));
						resComment.put("bigpicurl", getResObject(use.getBigpicurl()));
					} else {
						resComment.put("userName", "");
						resComment.put("minipicurl", "");
						resComment.put("bigpicurl", "");
					}
					supportList.add(resComment);
				}
			}
		} catch (Exception e) {
			resFlag = Constant.ERROR_CODE;
			msg = e.getMessage();
			logger.error("", e);
		}

		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> res2 = new HashMap<String, Object>();
		res2.put("resMessage", ""+msg);
		res2.put("resCode", resFlag);
		res2.put("vs", Constant.TRUE);
		res.put("res", res2);
		res.put("forwardList", forwardList);
		res.put("commentList", commentList);
		res.put("supportList", supportList);

		resJson = JSON.toJSONString(res);
		return resJson;

	}
	private Object getResObject(Object ori) {
		return null != ori ? ori : "";
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		// json.put("pointId", 1);
		// json.put("pageSize", 4);
		// json.put("queryType", "01");
		json.put("twitterId", 1000037);
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new GetForwardingPraiseReviewListById();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("getTwitterOper");
		String result = e.doProcess(context);
		logger.info(result);
	}
}
