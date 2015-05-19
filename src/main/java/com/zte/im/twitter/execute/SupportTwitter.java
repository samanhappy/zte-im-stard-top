package com.zte.im.twitter.execute;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.PushBean;
import com.zte.im.bean.TTwitter;
import com.zte.im.bean.User;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.CmdExecute;
import com.zte.im.util.IDSequence;

/**
 * dec 微博点赞
 * 
 * @author xubin
 * 
 */
public class SupportTwitter extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(SupportTwitter.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = "true";
		String msg = "";
		try {
			Long userId = json.getLong("userId");
			Long twitterId = json.getLong("twitterId");
			TTwitter t = TwitterServiceImpl.getInstance().findTwitterDetail(
					twitterId, userId);
			if (t == null) {
				resFlag = "false";
				msg = "微博不存在！";
				logger.info("=======================微博不存在！");
			}
			if (t != null && "00".equals(t.getTwitterStatus())) {
				Long supportNum = t.getSupportNum();
				if (supportNum == null) {
					supportNum = 0L;
				}
				// 更新微博点赞统计数
				TwitterServiceImpl.getInstance().updateTwitterNum(twitterId,
						"supportNum", supportNum + 1);

				// SimpleDateFormat sdf = new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// Date date = new Date();
				// 保存点赞关系
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("supportId", IDSequence.getUID());
				param.put("userId", userId);
				param.put("twitterId", twitterId);
				param.put("createUserId", userId);
				param.put("createTime", System.currentTimeMillis());
				param.put("updateUserId", userId);
				param.put("updateTime", System.currentTimeMillis());
				param.put("remark", "");
				TwitterServiceImpl.getInstance().supportTwitter(param);
				// 推送点赞信息
				User user = UserServiceImpl.getInstance().getUserbyid(userId);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", userId);
				map.put("userName", user.getName());
				map.put("twitterId", twitterId);
				PushBean bean = new PushBean();
				bean.setTarget(String.valueOf(t.getUserId()));
				bean.setKeyid(String.valueOf(super.getContext().getUser()
						.getUid()));
				bean.setMsg(JSON.toJSONString(map));
				bean.setType("10");// 10：点赞
				bean.setSessionid(String.valueOf(userId));
				bean.setModeltype("1");
				bean.setSessionname("点赞");
				CmdExecute.exe(bean);
			} else if ("99".equals(t.getTwitterStatus())) {
				resFlag = "false";
				msg = "微博已删除！";
				logger.info("=======================微博已删除！");
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
		json.put("twitterId", 2015);
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new SupportTwitter();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("supportTwitter");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
