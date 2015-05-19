package com.zte.im.user.execute;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.Feedback;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.UserServiceImpl;

/**
 * dec 新增反馈
 * 
 * @author cy
 * 
 */
public class SaveFeedback extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(SaveFeedback.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> res1 = new HashMap<String, Object>();
		try {
			String name = json.getString("name");
			String mobile = json.getString("mobile");
			String device = json.getString("device");
			String os = json.getString("os");
			String resolution = json.getString("resolution");
			String content = json.getString("content");
			String time = json.getString("time");
			String type = json.getString("type");

			Feedback fb = new Feedback();
			fb.setContent(content);
			fb.setDevice(device);
			fb.setMobile(mobile);
			fb.setName(name);
			fb.setOs(os);
			fb.setResolution(resolution);
			fb.setTime(time);
			fb.setType(type);
			UserServiceImpl.getInstance().saveFeedback(fb);

			res1.put("reCode", "1");
			res1.put("resMessage", "添加成功");
		} catch (Exception e) {
			res1.put("reCode", "0");
			res1.put("resMessage", e.getMessage());
			logger.error("", e);
		}
		res.put("res", res1);
		resJson = JSON.toJSONString(res);
		return resJson;
	}

	public static void main(String[] args) throws ParseException {
		JSONObject json = new JSONObject();
		json.put("name", "测试");
		json.put("mobile", "1825324122");
		json.put("device", "iPhone");
		json.put("os", "ios7.1.0");
		json.put("resolution", "640*1136");
		json.put("content", "内容");
		json.put("time", "2014-11-21 14:25");
		json.put("type", "system");

		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new SaveFeedback();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("programe/addPrograme");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
