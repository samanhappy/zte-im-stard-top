package com.zte.im.twitter.execute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.MyPageBean;
import com.zte.im.bean.TAttention;
import com.zte.im.bean.TTwitter;
import com.zte.im.bean.User;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.Constant;

public class PersonalPage extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(PersonalPage.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = Constant.TRUE;
		String msg = "";
		try {
			Long userId = json.getLong("userId");
			Long operUserId = json.getLong("operUserId");
			MyPageBean pageBean = new MyPageBean();
			pageBean.setPageSize(json.getInteger("pageSize"));
			pageBean.setPointId(json.getLong("pointId"));
			pageBean.setQueryType(json.getString("queryType"));
			TAttention attention = null;
			if (userId != operUserId) {
				attention = TwitterServiceImpl.getInstance().getAttention(
						userId, operUserId);
			}

			User user = UserServiceImpl.getInstance().getUserbyid(userId) == null ? new User()
					: UserServiceImpl.getInstance().getUserbyid(userId);
			User u = new User();
			u.setUid(user.getUid());
			u.setName(user.getName());
			u.setGname(user.getGname());
			u.setNickname(user.getNickname());
			u.setCname(user.getCname());
			u.setJid(user.getJid());
			u.setPost(user.getPost());
			u.setMinipicurl(user.getMinipicurl());
			u.setBigpicurl(user.getBigpicurl());
			u.setAutograph(user.getAutograph());
			Long[] uIdArr = { userId };
			List<TTwitter> twitterList = TwitterServiceImpl.getInstance()
					.getTwitters(operUserId, uIdArr, pageBean) ;
			int count = 0;
			if (null != twitterList && !twitterList.isEmpty()) {
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
			if (attention == null) {
				attention = new TAttention();
			}
			res.put("returnNum", count);
			res.put("user", u);
			res.put("eachOtherFlag", attention.getEachOtherFlag());
			res.put("attentionType", attention.getAttentionType());
			res.put("twitterList", twitterList);
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
		json.put("userId", 1000018);
		json.put("operUserId", 1000015);
		json.put("pointId", 1);
		json.put("pageSize", 5);
		json.put("queryType", "01");
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new PersonalPage();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("personalPage");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
