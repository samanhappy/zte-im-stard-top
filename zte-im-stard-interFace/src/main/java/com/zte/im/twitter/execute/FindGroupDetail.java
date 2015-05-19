package com.zte.im.twitter.execute;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.TGroup;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.Constant;

/**
 * 获取圈子详情
 * 
 * @author xubin
 * 
 */
public class FindGroupDetail extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(FindGroupDetail.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = Constant.TRUE;
		String msg = "";
		try {
			Long groupId = json.getLong("groupId");
			TGroup group = TwitterServiceImpl.getInstance().findGroupDetail(groupId);
			if(group == null){
				group = new TGroup();
			}
			res.put("group", group);
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

}
