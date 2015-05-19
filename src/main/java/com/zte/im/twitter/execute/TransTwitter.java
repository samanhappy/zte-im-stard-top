package com.zte.im.twitter.execute;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.twitter.job.MqttMessager;
import com.zte.im.util.IDSequence;
import com.zte.im.util.ParseTagUtil;

/**
 * dec 转发微博
 * 
 * @author xubin
 * 
 */
public class TransTwitter extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(TransTwitter.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = "true";
		String msg = "";
		try {
			Long userId = json.getLong("userId");
			String userName = json.getString("userName");
			Long twitterId = json.getLong("twitterId");
			String commentStatus = json.getString("commentStatus");// 是否评论
			String twitterContent = json.getString("twitterContent");
			ITwitterService twitterService = TwitterServiceImpl.getInstance();
			TTwitter t = twitterService.findTwitterDetail(
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
				if ("02".equals(t.getTwitterType())) {
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
				Long twitterid = IDSequence.getUID();
				Map<String, Object> tparam = new HashMap<String, Object>();
				String visibleRange = json.getString("visibleRange");
				tparam.put("userId", userId);
				tparam.put("twitterId", twitterid);
				tparam.put("twitterContent", twitterContent);
				tparam.put("twitterType", "02");
				tparam.put("twitterStatus", "00");
				tparam.put("sourceId", t.getSourceId());
				tparam.put("visibleRange", visibleRange);
				tparam.put("createUserId", userId);
				tparam.put("createTime", date.getTime());
				tparam.put("updateUserId", userId);
				tparam.put("updateTime", date.getTime());
				tparam.put("remark", "");
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
				twitterService.SaveNewTwitter(tparam);
				
				if (twitterid != null) {
					// 保存微博圈子对照
					if (null != grouplist2 && grouplist2.size() > 0) {

						List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
						for (Map<String, Object> ms : grouplist2) {
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("twitterId", twitterid);
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
							params.put("twitterId", twitterid);
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
						// 保存@信息
						Map<String, Object> param2 = new HashMap<String, Object>();
						param2.put("userId", userId);
						param2.put("mentionId", IDSequence.getUID());
						param2.put("twitterId", twitterId);
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

						// 推送@
						Map<String, Object> m = new HashMap<String, Object>();
						m.put("userId", userId);
						m.put("userName", userName);
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
						List<PushBean> list = new ArrayList<PushBean>();
						list.add(pushbean);
						new MqttMessager(list).start();
					}
				}

				// 保存评论
				if ("01".equals(commentStatus)) {
					Map<String, Object> param3 = new HashMap<String, Object>();
					param.put("commentId", commentId);
					param.put("userId", userId);
					param.put("twitterId", twitterId);
					param.put("commentContent", twitterContent);
					param.put("commentStatus", "00");
					param.put("commentType", "01");
					param.put("createUserId", userId);
					param.put("createTime", date.getTime());
					param.put("updateUserId", userId);
					param.put("updateTime", date.getTime());
					param.put("remark", "");
					TwitterServiceImpl.getInstance().commentTwitter(param3);
				}

				// 推送转发提醒
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", userId);
				map.put("userName", userName);
				PushBean bean = new PushBean();
				bean.setTarget(String.valueOf(t.getUserId()));
				bean.setKeyid(String.valueOf(super.getContext().getUser()
						.getUid()));
				bean.setMsg(JSON.toJSONString(map));
				bean.setType("12");// 12：提到了
				bean.setModeltype("1");
				bean.setSessionid(String.valueOf(userId));
				bean.setSessionname("@提到了");
				List<PushBean> list = new ArrayList<PushBean>();
				list.add(bean);
				new MqttMessager(list).start();
			} else if ("99".equals(t.getTwitterStatus())) {
				resFlag = "false";
				msg = "微博已删除！";
				logger.info("=======================微博已删除！");
			} else {
				resFlag = "false";
				msg = "微博不存在！";
				logger.info("=======================微博不存在！");
			}
		} catch (Exception e) {
			resFlag = "false";
			msg = e.getMessage();
			logger.error("", e);
		}
		res.put("msg", msg);
		res.put("resFlag", resFlag);
		resJson = JSON.toJSONString(res);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("userId", 1000001);
		json.put("userName", "测试1");
		json.put("twitterId", 2011);
		json.put("visibleRange", "01");
		json.put("twitterContent", "试试转发！");
		json.put("commentStatus", "01");
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new TransTwitter();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("transTwitter");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
