package com.zte.im.twitter.execute;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
import com.mongodb.DBCursor;
import com.zte.im.bean.TAttention;
import com.zte.im.bean.User;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;

/**
 * 加好友关注
 * 
 * @author Administrator
 * 
 */
public class AddSystemFriend extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(AddSystemFriend.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		String resFlag = Constant.TRUE;
		String msg = "";
		String eachOtherFlag = "0";
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			Long attentionUserId = json.getLong("attentionUserId");
			 
			String attentionType = "01";
			DBCursor cur = MongoDBSupport.getInstance().queryCollection(
					Constant.USER_DATA);
			List<User> groups = null;
			Type type = new TypeToken<List<User>>() {
			}.getType();
			groups = GsonUtil.gson.fromJson(cur.toArray().toString(),type);
	 
		if (groups != null) {
			Iterator<User> it = groups.iterator();
			while (it.hasNext()) {
				User user = it.next();
				long id = user.getUid(); 
				if(id!=attentionUserId.longValue()){
					
					Date date = new Date();

					TwitterServiceImpl service = TwitterServiceImpl.getInstance();

					// 查询对方是否关注
					TAttention attention = service.FindAttentionByUid(attentionUserId,
							id);
					if (null != attention) {
						// 更新 互相关注状态
						attention.setEachOtherFlag("1");
						attention.setUpdateUserId(id);
						attention.setUpdateTime(date.getTime());
						service.UpdateAttentionByUid(attention);
						eachOtherFlag = "1";
					}
					param.put("userId", id);
					param.put("attentionUserId", attentionUserId);
					param.put("eachOtherFlag", eachOtherFlag);
					param.put("attentionStatus", "00");
					param.put("attentionType", attentionType);
					param.put("createUserId", id);
					param.put("createTime", date.getTime());
					param.put("updateUserId", id);
					param.put("updateTime", date.getTime());
					param.put("remark", "");
				 	service.AddAttentionUid(param);
				}
			}
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
		//	json.put("attentionUserId", 10220000008l);  
	//	json.put("attentionUserId", 10220000357l);  
		
		
	  	json.put("attentionUserId", 150);  
	 //	json.put("attentionUserId", 151); 
		json.put("token", "0739772_4V7JCW9V");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new AddSystemFriend();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("saveAttent");
//		String result = e.doProcess(context);
//		logger.info(result);
		Date date = new Date(); 
		System.out.println(	new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(1420713925449l));
	}

}
