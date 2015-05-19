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
 * 发布新动态
 * 
 * @author Administrator
 * 
 */
public class NewTwitter2 extends AbstractValidatoExecute {

	private static Logger logger = LoggerFactory.getLogger(NewTwitter2.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		String resFlag = Constant.TRUE;
		String msg = "";
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			Long cid = json.getLong("userId");
			String twitterContent = json.getString("twitterContent");
			String visibleRange = json.getString("visibleRange");
			String country = json.getString("country");
			String province = json.getString("province");
			String city = json.getString("city");
			String county = json.getString("county");
			String street = json.getString("street");
			String address = json.getString("address");
			String twitterStatus = json.getString("twitterStatus");
			String locationFlag = json.getString("locationFlag");
			String lng = json.getString("lng");
			String lat = json.getString("lat");
			String imgSrc = json.getString("imgSrc");
			String twitterAttr = null; 

			if(Start.params.get("twitterSysUser").contains((String.valueOf(cid)))) {
				twitterAttr = Constant.TWITTER_ATTR_02;
			}else{
				twitterAttr = Constant.TWITTER_ATTR_01;
			}
			String deptList = json.getString("deptList");
			List<Map<String, Object>> deptlist2 = null;
			List<Map<String, Object>> staffList2 = null;
			visibleRange = "01";
			try {
				deptlist2 = JSON.parseObject(deptList,
						new TypeReference<List<Map<String, Object>>>() {
				});
				String staffList = json.getString("staffList");
				staffList2 = JSON.parseObject(staffList,
						new TypeReference<List<Map<String, Object>>>() {
				});
				
				if (null != deptlist2 && deptlist2.size() > 0) {
					visibleRange = "03";
				}
				if (null != staffList && staffList2.size() > 0) {
					if("01".equals(visibleRange)){
						visibleRange = "02";
					}else{
						visibleRange = "04";
					}
				}
			} catch (Exception e) {

			}

			Date date = new Date();

			TwitterServiceImpl twitterService = TwitterServiceImpl
					.getInstance();
			Long twitterId = IDSequence.getUID();
			param.put("twitterId", twitterId);
			param.put("sourceId", twitterId);
			param.put("userId", cid);
			param.put("twitterContent", twitterContent);

			param.put("twitterType", "01");
			param.put("twitterStatus", twitterStatus);
			param.put("visibleRange", visibleRange);
			param.put("locationFlag", locationFlag);
			param.put("lng", lng);
			param.put("lat", lat);
			param.put("country", country);
			param.put("province", province);
			param.put("city", city);
			param.put("county", county);
			param.put("street", street);
			param.put("address", address);
			param.put("createUserId", cid);
			param.put("createTime", date.getTime());
			param.put("imgSrc", imgSrc);
			param.put("forwardNum", 0);
			param.put("collectionNum", 0);
			param.put("commentNum", 0);
			param.put("supportNum", 0);
			param.put("twitterAttr", twitterAttr);


			// 获取微博ID
			Long twitterid = twitterId;
			if (twitterid != null) {
				// 保存微博圈子对照
				if (null != staffList2 && staffList2.size() > 0) {

					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					for (Map<String, Object> ms : staffList2) {
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("twitterId", twitterid);
						params.put("staffId", ms.get("staffId"));
						params.put("userId", cid);
						params.put("createUserId", cid);
						params.put("createTime", date.getTime());
						list.add(params);
					}
					if (list.size() > 0)
						twitterService.addTwitterStaffId(list);
				}
				// 保存微博部门对照
				if (null != deptlist2 && deptlist2.size() > 0) {
					List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();

					for (Map<String, Object> ms : deptlist2) {
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("twitterId", twitterid);
						params.put("deptId", ms.get("deptId"));
						params.put("userId", cid);
						params.put("createUserId", cid);
						params.put("createTime", date.getTime());
						list2.add(params);
					}
					if (list2.size() > 0)
						twitterService.addTwitterDeptId(list2);
				}
			}

			// 解析微博内容中@的用户
			List<Long> mUserIdList = ParseTagUtil
					.parseMentionUserId(twitterContent);
			// 微博内容
			String content = twitterContent;
			User user = UserServiceImpl.getInstance().getUserbyid(cid);
			if (mUserIdList != null && mUserIdList.size() > 0) {
				String userName = "";
				if (user != null) {
					userName = user.getName();
				}
				for (Long uid : mUserIdList) {
					content = content.replace("mentionUserId="+uid, "");
					// 保存@信息
					Map<String, Object> param2 = new HashMap<String, Object>();
					param2.put("userId", cid);
					param2.put("mentionId", IDSequence.getUID());
					param2.put("twitterId", twitterId);
					param2.put("mentionUserId", uid);
					param2.put("mentionType", "01");// 提到类型：01、微博；02、评论
					param2.put("mentionStatus", "00");
					param2.put("createUserId", cid);
					param2.put("createTime", date.getTime());
					param2.put("updateUserId", cid);
					param2.put("updateTime", date.getTime());
					param2.put("remark", "");
					TwitterServiceImpl.getInstance().addMentionInfo(param2);

					// 推送@
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("userId", cid);
					m.put("userName", userName);
					m.put("twitterId", twitterId);
					m.put("mentionStatus", "01");
					PushBean pushbean = new PushBean();
					pushbean.setTarget(String.valueOf(uid));
					pushbean.setKeyid(String.valueOf(super.getContext()
							.getUser().getUid()));
					pushbean.setMsg(JSON.toJSONString(m));
					pushbean.setType("15");// 17:微博
					pushbean.setSessionid(String.valueOf(cid));
					pushbean.setModeltype("1");
					pushbean.setSessionname("微博中@");
					List<PushBean> list = new ArrayList<PushBean>();
					list.add(pushbean); 
			  		MqttPublisher.publish(list);
				}
			}
			//保存剔除@与话题的标识后的字符串，用于搜索 
			System.out.println(content);
			param.put("searchContent", content);
			// 保存微博
			twitterService.SaveNewTwitter(param);
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
		json.put("twitterStatus", "00");
		json.put("street", "");
		json.put("lng", "0.0");
		json.put("city", "");
		json.put("country", "");
		json.put("groupList", "[{\"groupId\":17168},{\"groupId\":20208}]");
		json.put("twitterAttr", "01");
		json.put("locationFlag", "99");
		json.put("address", "");
		json.put("county", "");
		json.put("imgSrc", "");
		json.put("visibleRange", "");
		json.put("userId", 7649);
		json.put("province", "");
		json.put("twitterContent", "fdsasfd");

		json.put("deptList", "[]");
		json.put("lat", "0.0");
		json.put("token", "8210778_OUYU926Q");
		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new NewTwitter2();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("newTwitter");
		String result = e.doProcess(context);
		logger.info(result); 

	}

}
