package com.zte.im.twitter.execute;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.TTopic;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.Constant;
import com.zte.im.util.IDSequence;

/**
 * 新建话题
 * 
 * @author Administrator
 * 
 */
public class AddTopic extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(AddTopic.class);

	@Override
	public String doProcess(JSONObject json) {

		String resJson = null;
		String resFlag = Constant.TRUE;
		String msg = "";
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			Long userId = super.getContext().getUser().getUid();
			String topicName = json.getString("topicName");

			Date date = new Date();

			TwitterServiceImpl service = TwitterServiceImpl.getInstance();

			// 查询是否存在相同的内容
			TTopic topic = service.FindTopicByTopicName(topicName);
			if (null != topic) {
				// 更新 互相关注状态
				resFlag = Constant.CODE_13;
				msg = Constant.CODE_13;
			} else {
				Long topicId = IDSequence.getUID();
				param.put("topicId", topicId);
				param.put("topicName", topicName);
				param.put("topicStatus", "00");
				param.put("topicType", "01");
				param.put("useNum", 0);
				param.put("createUserId", userId);
				param.put("createTime", date.getTime());
				param.put("updateUserId", userId);
				param.put("updateTime", date.getTime());
				param.put("remark", "");
				service.AddNewTopic(param);
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
		json.put("userid", "11");
		json.put("topicName", "测试5");
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new AddTopic();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("saveTopic");
		String result = e.doProcess(context);
		logger.info(result);
	}
}
