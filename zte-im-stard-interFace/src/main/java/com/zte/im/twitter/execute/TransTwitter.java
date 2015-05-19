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
import com.alibaba.fastjson.TypeReference;
import com.zte.im.bean.PushBean;
import com.zte.im.bean.TTwitter;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.ITwitterService;
import com.zte.im.service.Start;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.Constant;
import com.zte.im.util.IDSequence;
import com.zte.im.util.ParseTagUtil;

/**
 * 转发微博
 * @author Administrator
 *
 */
public class TransTwitter extends AbstractValidatoExecute {

	private static Logger logger = LoggerFactory.getLogger(TransTwitter.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = Constant.TRUE;
		String msg = "";
		try {
			Long userId = json.getLong("userId");
			String userName = json.getString("userName");
			Long twitterId = json.getLong("twitterId");
			// 新建一条转发的微博
		  	Long newTwitterId = IDSequence.getUID();
		  
			String commentStatus = json.getString("commentStatus");// 是否评论
			String commentIds = json.getString("commentIds");// 是否评论
			String twitterContent = json.getString("twitterContent");
			if("".equals(twitterContent)||"转发微博".equals(twitterContent)){
				twitterContent = "转发分享";
			}
			List<PushBean> tList = new ArrayList<PushBean>();
			ITwitterService twitterService = TwitterServiceImpl.getInstance();
			TTwitter t = twitterService.findTwitterDetail(
					twitterId, userId);
			TTwitter sourceTwitter = null;

			Date date = new Date();
			if (t != null && "00".equals(t.getTwitterStatus()) ) {
				// 原微博
				sourceTwitter = twitterService.findTwitter(	t.getSourceId());
				Long forwardNum = t.getForwardNum();
				if (forwardNum == null) {
					forwardNum = 0L;
				}
				// 更新微博转发统计数
				TwitterServiceImpl.getInstance().updateTwitterNum(twitterId,
						"forwardNum", forwardNum + 1);
				logger.info("========forwardNum="+forwardNum + 1);
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
				param.put("twitterContent", twitterContent);
				param.put("createUserId", userId);
				param.put("createTime", date.getTime());
				param.put("updateUserId", userId);
				param.put("updateTime", date.getTime());
				param.put("remark", "");
				twitterService.transTwitter(param);

				// 新建一条转发的微博 
				Map<String, Object> tparam = new HashMap<String, Object>();
				String visibleRange = json.getString("visibleRange");
				tparam.put("userId", userId);
				tparam.put("twitterId", newTwitterId);
				tparam.put("twitterContent", twitterContent);
				String searchContent = twitterContent;
				  
				tparam.put("twitterType", "02");
				tparam.put("twitterStatus", "00");
				tparam.put("sourceId", t.getSourceId());
				tparam.put("visibleRange", visibleRange);
				tparam.put("createUserId", userId);
				tparam.put("createTime", date.getTime());
				tparam.put("updateUserId", userId);
				tparam.put("updateTime", date.getTime());
				tparam.put("remark", ""); 
				tparam.put("forwardNum", 0);
				tparam.put("collectionNum", 0);
				tparam.put("commentNum", 0);
				tparam.put("supportNum", 0);
				String twitterAttr = null; 
				// 判断是否是系统用户
				if(Start.params.get("twitterSysUser").contains((String.valueOf(userId)))) {
					twitterAttr = Constant.TWITTER_ATTR_02;
				}else{
					twitterAttr = Constant.TWITTER_ATTR_01;
				}
				tparam.put("twitterAttr", twitterAttr);
				String deptList = json.getString("deptList");
				List<Map<String, Object>> deptlist2 = null;
				List<Map<String, Object>> grouplist2 = null;
				deptlist2 = JSON.parseObject(deptList,
						new TypeReference<List<Map<String, Object>>>() {
				});
				String groupList = json.getString("groupList");
				grouplist2 = JSON.parseObject(groupList,
						new TypeReference<List<Map<String, Object>>>() {
				});
				 
				if (newTwitterId != null) {
					// 保存微博圈子对照
					if (null != grouplist2 && grouplist2.size() > 0) { 
						List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
						for (Map<String, Object> ms : grouplist2) {
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("twitterId", newTwitterId);
							params.put("groupId", ms.get("groupId"));
							params.put("userId", userId);
							params.put("createUserId", userId);
							params.put("createTime", date.getTime());
							list.add(params);
						}
						if (list.size() > 0)
							twitterService.addTwitterGroupId(list);
					}
					// 保存微博部门对照
					if (null != deptlist2 && deptlist2.size() > 0) {
						List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>(); 
						for (Map<String, Object> ms : deptlist2) {
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("twitterId", newTwitterId);
							params.put("deptId", ms.get("deptId"));
							params.put("userId", userId);
							params.put("createUserId", userId);
							params.put("createTime", date.getTime());
							list2.add(params);
						}
						if (list2.size() > 0)
							twitterService.addTwitterDeptId(list2);
					}
				}
				 
				Long commentId = IDSequence.getUID(); 
				// 保存@信息
				List<Long> mUserIdList = ParseTagUtil
						.parseMentionUserId(twitterContent);
				if (mUserIdList != null && mUserIdList.size() > 0) {
					for (Long uid : mUserIdList) {
						searchContent = twitterContent.replace("mentionUserId="+uid, "");
						// 保存@信息
						Map<String, Object> param2 = new HashMap<String, Object>();
						param2.put("userId", userId);
						param2.put("mentionId", IDSequence.getUID());
						param2.put("twitterId", newTwitterId);
						param2.put("mentionUserId", uid);
						param2.put("mentionType", "01");// 提到类型：01、微博；02、评论
						param2.put("mentionStatus", "00");
						if ("01".equals(commentStatus)) {
							param2.put("commentId", commentId);
						}
						param2.put("createUserId", userId);
						param2.put("createTime", date.getTime());
						param2.put("updateUserId", userId);
						param2.put("updateTime", date.getTime());
						param2.put("remark", "");
						TwitterServiceImpl.getInstance().addMentionInfo(param2);

						// 如果自己被@到、原创微博作者 ，那就不发送推送
						if(userId.longValue()!=uid.longValue() && sourceTwitter.getUserId().longValue()!=uid.longValue()) {
							// 推送@
							Map<String, Object> m = new HashMap<String, Object>();
							m.put("userId", userId);
							m.put("userName", userName);
							m.put("twitterId", newTwitterId);
							m.put("mentionStatus", "02");
							PushBean pushbean = new PushBean();
							pushbean.setTarget(String.valueOf(uid));
							pushbean.setKeyid(String.valueOf(super.getContext()
									.getUser().getUid()));
							pushbean.setMsg(JSON.toJSONString(m));
							pushbean.setType("15");// 15：@提到我
							pushbean.setSessionid(String.valueOf(userId));
							pushbean.setModeltype("1");
							pushbean.setSessionname("@提到我");  
							tList.add(pushbean);
							logger.info(tList.size()+"======1="+JSON.toJSONString(m)+"====="+uid);
						}
					}
				}
				//保存剔除@与话题的标识后的字符串，用于搜索 
				searchContent = ParseTagUtil.filterExpression(searchContent);
				logger.info("======2searchContent="+searchContent);
				
				tparam.put("searchContent", searchContent);
				twitterService.SaveNewTwitter(tparam); 
				// 保存评论
				if ("01".equals(commentStatus)) {
					Map<String, Object> param3 = new HashMap<String, Object>();
					int num =0;
					if(null!=commentIds  && !"".equals(commentIds)){
						for(String id:commentIds.split(",")){ 
							if(Long.parseLong(id) !=userId.longValue()){
								// 推送转发提醒
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("userId", userId);
								map.put("userName", userName);
								map.put("twitterId", newTwitterId);
								map.put("mentionStatus", "02");
								PushBean bean = new PushBean();
								bean.setTarget(String.valueOf(id));
								bean.setKeyid(String.valueOf(super.getContext().getUser()
										.getUid()));
								bean.setMsg(JSON.toJSONString(map));
								bean.setType("11");// 11：评论
								bean.setSessionname("评论"); 
								bean.setModeltype("1");
								bean.setSessionid(String.valueOf(userId));
								tList.add(bean);
								logger.info(tList.size()+"======2="+JSON.toJSONString(map)+"====="+id);
							}
						
							// 增加评论关系
							param3.put("commentId", commentId);
							param3.put("userId", userId);
							param3.put("twitterId", twitterId);
							param3.put("commentContent", twitterContent);
							param3.put("commentStatus", "00");
							param3.put("commentType", "01");
							param3.put("createUserId", userId);
							param3.put("createTime", date.getTime());
							param3.put("updateUserId", userId);
							param3.put("updateTime", date.getTime());
							param3.put("remark", "");
							param3.put("becomentUserId",Long.parseLong(id));
							TwitterServiceImpl.getInstance().commentTwitter(param3); 
							num++;
						}
						Long commentNum = t.getCommentNum();
						if (commentNum == null ) {
							commentNum = 0L;
						}
						// 更新微博评论统计数
						TwitterServiceImpl.getInstance().updateTwitterNum(twitterId,
								"commentNum", commentNum + num);
					}
				}
				// 保存@信息
				Map<String, Object> param2 = new HashMap<String, Object>();
				param2.put("userId", userId);
				param2.put("mentionId", IDSequence.getUID());
				param2.put("twitterId", newTwitterId);
				param2.put("mentionUserId", sourceTwitter.getUserId());
				param2.put("mentionType", "01");// 提到类型：01、微博；02、评论
				param2.put("mentionStatus", "00");
				if ("01".equals(commentStatus)) {
					param2.put("commentId", commentId);
				}
				param2.put("createUserId", userId);
				param2.put("createTime", date.getTime());
				param2.put("updateUserId", userId);
				param2.put("updateTime", date.getTime());
				param2.put("remark", "");
				TwitterServiceImpl.getInstance().addMentionInfo(param2);

				if(sourceTwitter.getUserId().longValue() !=userId.longValue()){
					// 推送@
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("userId", userId);
					m.put("userName", userName);
					m.put("twitterId", newTwitterId);
					m.put("mentionStatus", "02");
					PushBean pushbean = new PushBean();
					pushbean.setTarget(String.valueOf(sourceTwitter.getUserId()));
					pushbean.setKeyid(String.valueOf(super.getContext()
							.getUser().getUid()));
					pushbean.setMsg(JSON.toJSONString(m));
					pushbean.setType("15");// 15：@提到我
					pushbean.setSessionid(String.valueOf(userId));
					pushbean.setModeltype("1");
					pushbean.setSessionname("@提到我");  
					tList.add(pushbean); 
					logger.info(tList.size()+"======3="+JSON.toJSONString(m)+"====="+sourceTwitter.getUserId());
				}
				MqttPublisher.publish(tList);
			} else if ("99".equals(t.getTwitterStatus())) {
				resFlag = Constant.CODE_10;
				msg = Constant.CODE_10;
				logger.info("=======================微博已删除！");
			} else {
				resFlag = Constant.CODE_9;
				msg = Constant.CODE_9;
				logger.info("=======================微博不存在！");
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
		json.put("userId", 39782);
		json.put("userName", "测试17");
		json.put("twitterId", 26725); 
		json.put("twitterContent", "转发微博");
		json.put("commentStatus", "01");
		json.put("commentIds", "1000017");
		json.put("token","0327340_BHU7WLEW");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new TransTwitter();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("transTwitter");
		String result = e.doProcess(context);
		logger.info(result);

	}

}
