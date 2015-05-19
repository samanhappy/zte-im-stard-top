package com.zte.im.twitter.execute;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.paho.sample.mqttv3app.MqttPublisher;
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
import com.zte.im.service.Start;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.Constant;
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

	/**回复评论*/
	private String COMMENT_TYPE_COMMENT = "02";

	/** 回复赞*/
	private String COMMENT_TYPE_SUPPORT = "03";

	/** 同时转发*/
	private Long COMMENT_IS_TRANS = 1L;

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = Constant.TRUE;
		String msg = "";

		String searchContent = "";
		try {
			List<PushBean> list = new ArrayList<PushBean>();
			Long userId = json.getLong("userId"); 
			User user = UserServiceImpl.getInstance().getUserbyid(userId);
			Long twitterId = json.getLong("twitterId");
			Long targetId = json.getLong("targetId");
			String commentType = json.getString("commentType");
			Long isTrans = json.getLong("isTrans");
			String comment = json.getString("comment"); 
			if("".equals(comment)||"转发微博".equals(comment)){
				comment = "转发分享";
			}
			searchContent = comment;
			Date date = new Date();
			Map<String, Object> param = new HashMap<String, Object>();
			TTwitter t = TwitterServiceImpl.getInstance().findTwitterDetail(
					twitterId, userId);
			TTwitter sourceTwitter = null;
			sourceTwitter = TwitterServiceImpl.getInstance().findTwitter(t.getSourceId());
			String pushUserId = String.valueOf(t.getUserId());
		 	Long commentId = IDSequence.getUID();
	 
			param.put("commentId", commentId);
			param.put("userId", userId);
			param.put("twitterId", twitterId);
			param.put("commentContent", comment);
			param.put("commentStatus", "00");
			param.put("commentType", commentType);
			if (COMMENT_TYPE_COMMENT.equals(commentType)) {

				TComment c = TwitterServiceImpl.getInstance().findCommentDetail(targetId);
				if(c!=null){
					param.put("replyCommentId", targetId);
					pushUserId = String.valueOf(c.getUserId()); 
				}
			}
			if (COMMENT_TYPE_SUPPORT.equals(commentType)) {

				TSupport support = TwitterServiceImpl.getInstance().findSupportDetail(targetId);
				if(support!=null){
					param.put("replySupportId", targetId);
					pushUserId = String.valueOf(support.getUserId());
				}
			}
			param.put("becomentUserId", Long.parseLong(pushUserId));
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
				
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", userId);
				map.put("userName", user.getName());
				map.put("twitterId", twitterId);
				
				logger.info("=======t.getUserId()="+t.getUserId());
				logger.info("=======userId="+userId);
				logger.info("=======pushUserId="+pushUserId);
				logger.info("=======sourceTwitter.userid="+sourceTwitter.getUserId());
				// 被评论人不等于用户,并且回复的是微博情况下发推送
				if(t.getUserId().longValue()!=userId.longValue() &&!COMMENT_TYPE_COMMENT.equals(commentType)&&!COMMENT_TYPE_SUPPORT.equals(commentType)){
					// 推送评论 
					PushBean bean = new PushBean();
					bean.setTarget(t.getUserId().toString());
					bean.setKeyid(String.valueOf(super.getContext().getUser()
							.getUid()));
					 
					bean.setMsg(JSON.toJSONString(map));
					bean.setType("11");// 11：评论
					bean.setSessionid(String.valueOf(userId));
					bean.setModeltype("1");
					bean.setSessionname("评论");
					list.add(bean); 
					logger.info("=======type=11,map="+JSON.toJSONString(map)+"=target="+t.getUserId());
				}
				
				// 回复评论人时，评论人不等于用户
				if( Long.parseLong(pushUserId)!=t.getUserId().longValue()){
					PushBean	bean = new PushBean();
					bean.setTarget(String.valueOf(pushUserId));
					bean.setKeyid(String.valueOf(super.getContext().getUser()
							.getUid()));
					bean.setMsg(JSON.toJSONString(map));
					bean.setType("11");// 11：评论
					bean.setSessionid(String.valueOf(userId));
					bean.setModeltype("1");
					bean.setSessionname("评论");
					logger.info("=======type=11,map="+JSON.toJSONString(map)+"=target="+pushUserId);
					list.add(bean); 
				}

				// 解析评论内容中@的用户
				List<Long> mUserIdList = ParseTagUtil
						.parseMentionUserId(comment);
				if (mUserIdList != null && mUserIdList.size() > 0) {
					for (Long uid : mUserIdList) {
						searchContent = searchContent.replace("mentionUserId="+uid, "");
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
						// 如果自己被@到，那就不发送推送
						if( sourceTwitter.getUserId().longValue()!=uid.longValue() && userId.longValue()!=uid.longValue()) {
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
							pushbean.setType("15");// 11：评论
							pushbean.setSessionid(String.valueOf(userId));
							pushbean.setModeltype("1");
							pushbean.setSessionname("评论中@");
							list.add(pushbean); 
						}
					}
				} 

				// 同时转发微博
				if (COMMENT_IS_TRANS.equals(isTrans)) {  
					transTwitter(userId, user.getName(), twitterId, searchContent, sourceTwitter.getUserId(), list); 
				} 

			} else if ("99".equals(t.getTwitterStatus())) {
				resFlag = Constant.ERROR_CODE;
				msg = "微博已删除！";
				logger.info("=======================微博已删除！");
			} else {
				resFlag = Constant.ERROR_CODE;
				msg = "微博不存在！";
				logger.info("=======================微博不存在！");
			}

			logger.info("============5,list="+list.size());
			MqttPublisher.publish(list); 
			
		} catch (Exception e) {
			resFlag = Constant.ERROR_CODE;
			msg = e.toString();
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

	/**
	 * dec 转发微博
	 * 
	 */
	private void transTwitter(Long userId, String userName, Long twitterId, String comment, Long sourceTwitterUserId ,List<PushBean> list) {
		try {
			TTwitter t = TwitterServiceImpl.getInstance().findTwitterDetail(
					twitterId, userId);
			Date date = new Date();
			String searchContent = comment;
			// 如果不是原创微博需要
			if(t.getTwitterId().longValue()!=t.getSourceId().longValue()){
				comment = comment +"//@"+t.getUserName()+" mentionUserId=" + t.getUserId() + " :"+t.getTwitterContent();
			}
			logger.info("============1");
			if (t != null && "00".equals(t.getTwitterStatus())) {
				Long forwardNum = t.getForwardNum();
				if (forwardNum == null) {
					forwardNum = 0L;
				}
				// 更新微博转发统计数
				TwitterServiceImpl.getInstance().updateTwitterNum(twitterId,
						"forwardNum", forwardNum + 1); 
				
				if ("02".equals(t.getTwitterType())&&t.getSourceId().longValue()!=twitterId) {
					// 更新原始微博转发统计数
					TwitterServiceImpl.getInstance().updateTwitterNum(
							t.getSourceId(), "forwardNum", forwardNum + 1);
				}
				// 保存微博与转发用户关联关系
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("transId", IDSequence.getUID());
				param.put("userId", userId);
				param.put("twitterId", twitterId);
				param.put("twitterContent", comment );
				param.put("createUserId", userId);
				param.put("createTime", date.getTime());
				param.put("updateUserId", userId);
				param.put("updateTime", date.getTime());
				param.put("remark", "");
				TwitterServiceImpl.getInstance().transTwitter(param);

				// 新建一条转发的微博
				long   newTwitterId= IDSequence.getUID();
				logger.info("========newTwitterId="+newTwitterId);
				Map<String, Object> tparam = new HashMap<String, Object>();
				// String visibleRange = json.getString("visibleRange");
				tparam.put("userId", userId);
				tparam.put("twitterId", newTwitterId);
				tparam.put("twitterContent", comment);
				tparam.put("twitterType", "02");
				tparam.put("twitterStatus", "00");
				tparam.put("sourceId", t.getSourceId());
				tparam.put("visibleRange", "01");
				tparam.put("createUserId", userId);
				String twitterAttr = null; 
				
				searchContent = ParseTagUtil.filterExpression(searchContent);
				tparam.put("searchContent", searchContent);
				if(Start.params.get("twitterSysUser").contains((String.valueOf(userId)))) {
					twitterAttr = Constant.TWITTER_ATTR_02;
				}else{
 					twitterAttr = Constant.TWITTER_ATTR_01;
				}
				tparam.put("twitterAttr", twitterAttr);
				tparam.put("createTime", date.getTime());
				tparam.put("updateUserId", userId);
				tparam.put("updateTime", date.getTime());
				tparam.put("remark", "");
				tparam.put("forwardNum", 0);
				tparam.put("collectionNum", 0);
				tparam.put("commentNum", 0);
				tparam.put("supportNum", 0);
				TwitterServiceImpl.getInstance().SaveNewTwitter(tparam);

				// 保存@信息
				// 自己不推送自己
				logger.info("============t.getUserId().longValue()="+t.getUserId().longValue());
				logger.info("============userId.longValue()="+userId.longValue());
				logger.info("=========="+String.valueOf(t.getUserId().longValue()!=userId.longValue()));

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
				param2.put("twitterId", newTwitterId);
				param2.put("remark", "");
				TwitterServiceImpl.getInstance().addMentionInfo(param2);

				if(t.getUserId().longValue()!=userId.longValue()&&t.getUserId().longValue()!=sourceTwitterUserId.longValue()){
					// 推送转发提醒  
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("userId", userId);
					m.put("userName", userName);
					m.put("twitterId", newTwitterId);
					m.put("mentionStatus", "02");
					PushBean pushbean = new PushBean();
					pushbean.setTarget(String.valueOf(t.getUserId()));
					pushbean.setKeyid(String.valueOf(super.getContext()
							.getUser().getUid()));
					pushbean.setMsg(JSON.toJSONString(m));
					pushbean.setType("15");// 15：@提到我
					pushbean.setSessionid(String.valueOf(userId));
					pushbean.setModeltype("1");
					pushbean.setSessionname("@提到我");   
					list.add(pushbean);
					logger.info("============2,"+JSON.toJSONString(m)+"====="+"t.getUserId()="+t.getUserId());
					
					param2 = new HashMap<String, Object>();
					param2.put("userId", userId);
					param2.put("mentionId", IDSequence.getUID());
					param2.put("mentionUserId", sourceTwitterUserId);
					param2.put("mentionType", "01");// 提到类型：01、微博；02、评论
					param2.put("mentionStatus", "00");
					param2.put("createUserId", userId);
					param2.put("createTime", date.getTime());
					param2.put("updateUserId", userId);
					param2.put("updateTime", date.getTime());
					param2.put("twitterId", newTwitterId);
					param2.put("remark", "");
					TwitterServiceImpl.getInstance().addMentionInfo(param2);
				
				} 
				
				
				if(userId.longValue()!=sourceTwitterUserId.longValue()){
					// 推送转发提醒  
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("userId", userId);
					m.put("userName", userName);
					m.put("twitterId", newTwitterId);
					m.put("mentionStatus", "02");
					PushBean pushbean = new PushBean();
					pushbean.setTarget(String.valueOf(sourceTwitterUserId));
					pushbean.setKeyid(String.valueOf(super.getContext()
							.getUser().getUid()));
					pushbean.setMsg(JSON.toJSONString(m));
					pushbean.setType("15");// 15：@提到我
					pushbean.setSessionid(String.valueOf(userId));
					pushbean.setModeltype("1");
					pushbean.setSessionname("@提到我");   
					list.add(pushbean);
					logger.info("============3,"+JSON.toJSONString(m)+"====="+"t.getUserId()="+t.getUserId());
				}

			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public static void main(String[] args) {
		Long a = 1000036l;
		Long b = 1000036l;
		System.out.println(a.longValue()!=b.longValue());
		JSONObject json = new JSONObject();
		json.put("userId", 7653); 
		json.put("token", "0222068_Y0OEJ1F5");
		json.put("twitterId", 27402);
		json.put("comment", "大概呵呵");
		json.put("commentType", "01"); 
		json.put("targetId", "");
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
